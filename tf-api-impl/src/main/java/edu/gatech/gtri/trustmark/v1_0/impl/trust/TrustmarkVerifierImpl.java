package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifier;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
import edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidator;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.Try;

import javax.xml.crypto.dsig.XMLSignature;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureUtility.certificateList;
import static edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode.ACTIVE;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureCertificateAbsent;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureCertificateCommonName;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureCertificateEncoding;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureIdentifier;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureNonExpiration;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureNonRevocationResolveException;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureNonRevocationStatus;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureProviderIdentifier;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureRecipientIdentifier;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureXMLSignature;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.NonEmptyList.fromList;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.condition;
import static org.gtri.fj.data.Validation.success;

public class TrustmarkVerifierImpl implements TrustmarkVerifier {

    private final XmlSignatureValidator xmlSignatureValidator;
    private final TrustmarkStatusReportResolver trustmarkStatusReportResolver;
    private final F1<Entity, Boolean> trustmarkProviderFilter;
    private final F1<Entity, Boolean> trustmarkRecipientFilter;

    public TrustmarkVerifierImpl(
            final XmlSignatureValidator xmlSignatureValidator,
            final TrustmarkStatusReportResolver trustmarkStatusReportResolver,
            final F1<Entity, Boolean> trustmarkProviderFilter,
            final F1<Entity, Boolean> trustmarkRecipientFilter) {

        requireNonNull(xmlSignatureValidator);
        requireNonNull(trustmarkStatusReportResolver);
        requireNonNull(trustmarkProviderFilter);
        requireNonNull(trustmarkRecipientFilter);

        this.xmlSignatureValidator = xmlSignatureValidator;
        this.trustmarkStatusReportResolver = trustmarkStatusReportResolver;
        this.trustmarkProviderFilter = trustmarkProviderFilter;
        this.trustmarkRecipientFilter = trustmarkRecipientFilter;
    }

    public Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verify(
            final Trustmark trustmark) {

        // 1. Verification of the Trustmark Provider Identifier: Verify that the Trustmark Provider Identifier on the Trustmark is a valid URL and identifies one of the Trustmark Providers trusted by the Trustmark Relying Party.

        // 2. Verification of the Trustmark’s Digital Signature: Verify that the digital signature on the Trustmark is cryptographically consistent with the Trustmark’s contents.

        // 3. Verification of the Trustmark Signing Certificate’s Common Name: Verify that the Trustmark Signing Certificate used to sign the Trustmark contains a Common Name that is consistent with the Trustmark Provider Identifier in the Trustmark.

        // 4. Verification of the Trustmark Signing Certificate’s Status: Verify that the Trustmark Signing Certificate used to sign the Trustmark has not expired or been revoked. Note, as per Section 5.4, item 9, that a Trustmark Provider is required to revoke the Trustmark if its Trustmark Signing Certificate expires or is revoked.

        // 5. Verification of the Trustmark’s Identifier: Verify that the Trustmark’s identifier is a valid URL that indicates a sub-path of the URL specified for the Trustmark Provider Identifier for the Trustmark.

        // 6. Verification of Trustmark Non-Expiration: Verify, via the expiration date-time on the Trustmark, that the Trustmark is not yet expired.

        // 7. Verification of Trustmark Non-Revocation: Verify, through the Trustmark Status Report at the Trustmark’s Status URL, that the Trustmark has not been revoked. Note that a Trustmark Relying Party MAY perform additional revocation checks periodically or as needed throughout the duration of its period of reliance upon the Trustmark. Trustmark Framework Technical Specification Version 1.4 62

        // 8. Verification of Proper Organizational Scope via the Trustmark Recipient Identifier: Verify that the Trustmark Recipient Identifier matches and/or logically corresponds to a known URL for the entity about which the Trustmark was (or is assumed to have been) issued, and for which the Trustmark conveys trust.

        // 9. Verification of Proper Operational Scope via the Trustmark Definition: Verify that the purpose for which the Trustmark will be used, or the purpose for which it will be relied upon, is consistent with the Trustmark’s meaning and intended usage as per its Trustmark Definition. In addition to completing the steps listed previously, the Trustmark Relying Party SHOULD determine whether the Trustmark contains any exceptions by checking for the presence of an <ExceptionInfo> element. If such an element is present, the Trustmark Relying Party SHOULD make a risk-based decision about whether to trust the Trustmark, in accordance with its risk profile.

        return accumulate(
                verifyTrustmarkProviderIdentifier(trustmarkProviderFilter, trustmark),
                xmlSignatureValidator.validate(trustmark)
                        .f().map(nel -> nel(failureXMLSignature(trustmark, nel)))
                        .bind(xmlSignature -> verifyTrustmarkSigningCertificate(trustmark, xmlSignature)
                                .bind(x509CertificateList -> verifyTrustmarkSigningCertificateEncoding(trustmark, x509CertificateList.head()))
                                .bind(jcaX509CertificateHolder -> accumulate(
                                        verifyTrustmarkSigningCertificateCommonName(trustmark, jcaX509CertificateHolder),
                                        verifyTrustmarkSigningCertificateStatus(trustmark, jcaX509CertificateHolder),
                                        (t1, t2) -> t1))),
                verifyTrustmarkIdentifier(trustmark),
                verifyTrustmarkNonExpiration(trustmark),
                verifyTrustmarkNonRevocation(trustmarkStatusReportResolver, trustmark),
                verifyTrustmarkRecipientIdentifier(trustmarkRecipientFilter, trustmark),
                verifyTrustmarkExceptionInfo(trustmark),
                (t1, t2, t3, t4, t5, t6, t7) -> t1);
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verifyTrustmarkProviderIdentifier(
            final F1<Entity, Boolean> trustmarkProviderFilter,
            final Trustmark trustmark) {

        return condition(
                trustmarkProviderFilter.f(trustmark.getProvider()),
                nel(failureProviderIdentifier(trustmark)),
                trustmark);
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, NonEmptyList<X509Certificate>> verifyTrustmarkSigningCertificate(
            final Trustmark trustmark,
            final XMLSignature xmlSignature) {

        return fromList(certificateList(xmlSignature))
                .toValidation(nel(failureCertificateAbsent(trustmark, xmlSignature)));
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, JcaX509CertificateHolder> verifyTrustmarkSigningCertificateEncoding(
            final Trustmark trustmark,
            final X509Certificate x509Certificate) {

        return Try.<JcaX509CertificateHolder, CertificateEncodingException>f(() -> new JcaX509CertificateHolder(x509Certificate))._1()
                .f().map(certificateEncodingException -> nel(failureCertificateEncoding(trustmark, x509Certificate, certificateEncodingException)));
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verifyTrustmarkSigningCertificateCommonName(
            final Trustmark trustmark,
            final JcaX509CertificateHolder jcaX509CertificateHolder) {

        return condition(
                IETFUtils.valueToString(jcaX509CertificateHolder.getSubject().getRDNs(BCStyle.CN)[0].getFirst().getValue()).equals(trustmark.getProvider().getIdentifier().getHost()),
                nel(failureCertificateCommonName(trustmark, jcaX509CertificateHolder)),
                trustmark);
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verifyTrustmarkSigningCertificateStatus(
            final Trustmark trustmark,
            final JcaX509CertificateHolder jcaX509CertificateHolder) {

        return success(trustmark);
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verifyTrustmarkIdentifier(
            final Trustmark trustmark) {

        return condition(
                trustmark.getIdentifier().toString().startsWith(trustmark.getProvider().getIdentifier().toString()),
                nel(failureIdentifier(trustmark)),
                trustmark);
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verifyTrustmarkNonExpiration(
            final Trustmark trustmark) {

        return condition(
                trustmark.getExpirationDateTime().toInstant().isAfter(Instant.now()),
                nel(failureNonExpiration(trustmark)),
                trustmark);
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verifyTrustmarkNonRevocation(
            final TrustmarkStatusReportResolver trustmarkStatusReportResolver,
            final Trustmark trustmark) {

        return Try.<TrustmarkStatusReport, ResolveException>f(() -> trustmarkStatusReportResolver.resolve(trustmark))._1()
                .f().map(resolveException -> nel(failureNonRevocationResolveException(trustmark, resolveException)))
                .bind(trustmarkStatusReport -> condition(
                        trustmarkStatusReport.getStatus() == ACTIVE,
                        nel(failureNonRevocationStatus(trustmark, trustmarkStatusReport)),
                        trustmark));
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verifyTrustmarkRecipientIdentifier(
            final F1<Entity, Boolean> trustmarkRecipientFilter,
            final Trustmark trustmark) {

        return condition(
                trustmarkRecipientFilter.f(trustmark.getRecipient()),
                nel(failureRecipientIdentifier(trustmark)),
                trustmark);
    }

    private static Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verifyTrustmarkExceptionInfo(
            final Trustmark trustmark) {

        return success(trustmark);
    }
}
