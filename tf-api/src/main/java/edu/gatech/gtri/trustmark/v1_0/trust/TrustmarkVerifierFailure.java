package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;

import javax.xml.crypto.dsig.XMLSignature;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Objects.requireNonNull;

public abstract class TrustmarkVerifierFailure {

    private TrustmarkVerifierFailure() {
    }

    public abstract Trustmark getTrustmark();

    public abstract <T1> T1 match(
            final F1<Trustmark, T1> fFailureProviderIdentifier,
            final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
            final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
            final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
            final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
            final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
            final F1<Trustmark, T1> fFailureIdentifier,
            final F1<Trustmark, T1> fFailureNonExpiration,
            final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
            final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
            final F1<Trustmark, T1> fFailureRecipientIdentifier,
            final F1<Trustmark, T1> fFailureExceptionInfo);

    private static final class TrustmarkVerifierFailureProviderIdentifier extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;

        public TrustmarkVerifierFailureProviderIdentifier(final Trustmark trustmark) {

            requireNonNull(trustmark);

            this.trustmark = trustmark;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureProviderIdentifier.f(trustmark);
        }
    }

    private static final class TrustmarkVerifierFailureXMLSignature extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;
        private final NonEmptyList<XmlSignatureValidatorFailure> xmlSignatureValidationFailureNonEmptyList;

        public TrustmarkVerifierFailureXMLSignature(final Trustmark trustmark, final NonEmptyList<XmlSignatureValidatorFailure> xmlSignatureValidationFailureNonEmptyList) {

            requireNonNull(trustmark);
            requireNonNull(xmlSignatureValidationFailureNonEmptyList);

            this.trustmark = trustmark;
            this.xmlSignatureValidationFailureNonEmptyList = xmlSignatureValidationFailureNonEmptyList;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public NonEmptyList<XmlSignatureValidatorFailure> getXmlSignatureValidationFailureNonEmptyList() {
            return xmlSignatureValidationFailureNonEmptyList;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureXMLSignature.f(trustmark, xmlSignatureValidationFailureNonEmptyList);
        }
    }

    private static final class TrustmarkVerifierFailureCertificateAbsent extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;
        private final XMLSignature xmlSignature;

        public TrustmarkVerifierFailureCertificateAbsent(final Trustmark trustmark, final XMLSignature xmlSignature) {

            requireNonNull(trustmark);
            requireNonNull(xmlSignature);

            this.trustmark = trustmark;
            this.xmlSignature = xmlSignature;
        }

        @Override
        public Trustmark getTrustmark() {
            return trustmark;
        }

        public XMLSignature getXmlSignature() {
            return xmlSignature;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureCertificateAbsent.f(trustmark, xmlSignature);
        }
    }

    private static final class TrustmarkVerifierFailureCertificateEncoding extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;
        private final X509Certificate x509Certificate;
        private final CertificateEncodingException certificateEncodingException;

        public TrustmarkVerifierFailureCertificateEncoding(final Trustmark trustmark, final X509Certificate x509Certificate, final CertificateEncodingException certificateEncodingException) {

            requireNonNull(trustmark);
            requireNonNull(x509Certificate);
            requireNonNull(certificateEncodingException);

            this.trustmark = trustmark;
            this.x509Certificate = x509Certificate;
            this.certificateEncodingException = certificateEncodingException;
        }

        @Override
        public Trustmark getTrustmark() {
            return trustmark;
        }

        public X509Certificate getX509Certificate() {
            return x509Certificate;
        }

        public CertificateEncodingException getCertificateEncodingException() {
            return certificateEncodingException;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureCertificateEncoding.f(trustmark, x509Certificate, certificateEncodingException);
        }
    }

    private static final class TrustmarkVerifierFailureCertificateCommonName extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;
        private final JcaX509CertificateHolder jcaX509CertificateHolder;

        public TrustmarkVerifierFailureCertificateCommonName(final Trustmark trustmark, final JcaX509CertificateHolder jcaX509CertificateHolder) {

            requireNonNull(trustmark);
            requireNonNull(jcaX509CertificateHolder);

            this.trustmark = trustmark;
            this.jcaX509CertificateHolder = jcaX509CertificateHolder;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public JcaX509CertificateHolder getJcaX509CertificateHolder() {
            return jcaX509CertificateHolder;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureCertificateCommonName.f(trustmark, jcaX509CertificateHolder);
        }
    }

    private static final class TrustmarkVerifierFailureCertificateStatus extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;
        private final XMLSignature xmlSignature;

        public TrustmarkVerifierFailureCertificateStatus(final Trustmark trustmark, final XMLSignature xmlSignature) {

            requireNonNull(trustmark);
            requireNonNull(xmlSignature);

            this.trustmark = trustmark;
            this.xmlSignature = xmlSignature;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public XMLSignature getXmlSignature() {
            return xmlSignature;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureCertificateStatus.f(trustmark, xmlSignature);
        }
    }

    private static final class TrustmarkVerifierFailureIdentifier extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;

        public TrustmarkVerifierFailureIdentifier(final Trustmark trustmark) {

            requireNonNull(trustmark);

            this.trustmark = trustmark;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureIdentifier.f(trustmark);
        }
    }

    private static final class TrustmarkVerifierFailureNonExpiration extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;

        public TrustmarkVerifierFailureNonExpiration(final Trustmark trustmark) {

            requireNonNull(trustmark);

            this.trustmark = trustmark;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureNonExpiration.f(trustmark);
        }
    }

    private static final class TrustmarkVerifierFailureNonRevocationResolveException extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;
        private final ResolveException resolveException;

        public TrustmarkVerifierFailureNonRevocationResolveException(final Trustmark trustmark, final ResolveException resolveException) {

            requireNonNull(trustmark);
            requireNonNull(resolveException);

            this.trustmark = trustmark;
            this.resolveException = resolveException;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public ResolveException getResolveException() {
            return resolveException;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureNonRevocationResolveException.f(trustmark, resolveException);
        }
    }

    private static final class TrustmarkVerifierFailureNonRevocationStatus extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;
        private final TrustmarkStatusReport trustmarkStatusReport;

        public TrustmarkVerifierFailureNonRevocationStatus(final Trustmark trustmark, final TrustmarkStatusReport trustmarkStatusReport) {

            requireNonNull(trustmark);
            requireNonNull(trustmarkStatusReport);

            this.trustmark = trustmark;
            this.trustmarkStatusReport = trustmarkStatusReport;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public TrustmarkStatusReport getTrustmarkStatusReport() {
            return trustmarkStatusReport;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureNonRevocationStatus.f(trustmark, trustmarkStatusReport);
        }
    }

    private static final class TrustmarkVerifierFailureRecipientIdentifier extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;

        public TrustmarkVerifierFailureRecipientIdentifier(final Trustmark trustmark) {

            requireNonNull(trustmark);

            this.trustmark = trustmark;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureRecipientIdentifier.f(trustmark);
        }
    }

    private static final class TrustmarkVerifierFailureExceptionInfo extends TrustmarkVerifierFailure {
        private final Trustmark trustmark;

        public TrustmarkVerifierFailureExceptionInfo(final Trustmark trustmark) {

            requireNonNull(trustmark);

            this.trustmark = trustmark;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public <T1> T1 match(
                final F1<Trustmark, T1> fFailureProviderIdentifier,
                final F2<Trustmark, NonEmptyList<XmlSignatureValidatorFailure>, T1> fFailureXMLSignature,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateAbsent,
                final F3<Trustmark, X509Certificate, CertificateEncodingException, T1> fFailureCertificateEncoding,
                final F2<Trustmark, JcaX509CertificateHolder, T1> fFailureCertificateCommonName,
                final F2<Trustmark, XMLSignature, T1> fFailureCertificateStatus,
                final F1<Trustmark, T1> fFailureIdentifier,
                final F1<Trustmark, T1> fFailureNonExpiration,
                final F2<Trustmark, ResolveException, T1> fFailureNonRevocationResolveException,
                final F2<Trustmark, TrustmarkStatusReport, T1> fFailureNonRevocationStatus,
                final F1<Trustmark, T1> fFailureRecipientIdentifier,
                final F1<Trustmark, T1> fFailureExceptionInfo) {

            requireNonNull(fFailureProviderIdentifier);
            requireNonNull(fFailureXMLSignature);
            requireNonNull(fFailureCertificateAbsent);
            requireNonNull(fFailureCertificateEncoding);
            requireNonNull(fFailureCertificateCommonName);
            requireNonNull(fFailureCertificateStatus);
            requireNonNull(fFailureIdentifier);
            requireNonNull(fFailureNonExpiration);
            requireNonNull(fFailureNonRevocationResolveException);
            requireNonNull(fFailureNonRevocationStatus);
            requireNonNull(fFailureRecipientIdentifier);
            requireNonNull(fFailureExceptionInfo);

            return fFailureExceptionInfo.f(trustmark);
        }
    }

    public static TrustmarkVerifierFailure failureProviderIdentifier(final Trustmark trustmark) {
        return new TrustmarkVerifierFailureProviderIdentifier(trustmark);
    }

    public static TrustmarkVerifierFailure failureXMLSignature(final Trustmark trustmark, final NonEmptyList<XmlSignatureValidatorFailure> xmlSignatureValidationFailureNonEmptyList) {
        return new TrustmarkVerifierFailureXMLSignature(trustmark, xmlSignatureValidationFailureNonEmptyList);
    }

    public static TrustmarkVerifierFailure failureCertificateAbsent(final Trustmark trustmark, final XMLSignature xmlSignature) {
        return new TrustmarkVerifierFailureCertificateAbsent(trustmark, xmlSignature);
    }

    public static TrustmarkVerifierFailure failureCertificateEncoding(final Trustmark trustmark, final X509Certificate x509Certificate, final CertificateEncodingException certificateEncodingException) {
        return new TrustmarkVerifierFailureCertificateEncoding(trustmark, x509Certificate, certificateEncodingException);
    }

    public static TrustmarkVerifierFailure failureCertificateCommonName(final Trustmark trustmark, final JcaX509CertificateHolder jcaX509CertificateHolder) {
        return new TrustmarkVerifierFailureCertificateCommonName(trustmark, jcaX509CertificateHolder);
    }

    public static TrustmarkVerifierFailure failureCertificateStatus(final Trustmark trustmark, final XMLSignature xmlSignature) {
        return new TrustmarkVerifierFailureCertificateStatus(trustmark, xmlSignature);
    }

    public static TrustmarkVerifierFailure failureIdentifier(final Trustmark trustmark) {
        return new TrustmarkVerifierFailureIdentifier(trustmark);
    }

    public static TrustmarkVerifierFailure failureNonExpiration(final Trustmark trustmark) {
        return new TrustmarkVerifierFailureNonExpiration(trustmark);
    }

    public static TrustmarkVerifierFailure failureNonRevocationResolveException(final Trustmark trustmark, final ResolveException resolveException) {
        return new TrustmarkVerifierFailureNonRevocationResolveException(trustmark, resolveException);
    }

    public static TrustmarkVerifierFailure failureNonRevocationStatus(final Trustmark trustmark, final TrustmarkStatusReport trustmarkStatusReport) {
        return new TrustmarkVerifierFailureNonRevocationStatus(trustmark, trustmarkStatusReport);
    }

    public static TrustmarkVerifierFailure failureRecipientIdentifier(final Trustmark trustmark) {
        return new TrustmarkVerifierFailureRecipientIdentifier(trustmark);
    }

    public static TrustmarkVerifierFailure failureExceptionInfo(final Trustmark trustmark) {
        return new TrustmarkVerifierFailureExceptionInfo(trustmark);
    }

    public static String messageFor(final TrustmarkVerifierFailure trustmarkVerifierFailure) {

        return trustmarkVerifierFailure.match(
                (trustmark) -> format("%s: The trustmark provider identifier ('%s') is not among the trusted trustmark provider identifiers.", trustmark.getIdentifier().toString(), trustmark.getProvider().getIdentifier().toString()),
                (trustmark, xmlSignatureValidationFailureNonEmptyList) -> format("%s: The system could not verify the trustmark signing certificate; message: %s", trustmark.getIdentifier().toString(), join(System.lineSeparator(), xmlSignatureValidationFailureNonEmptyList.map(XmlSignatureValidatorFailure::messageFor).toList().toJavaList())),
                (trustmark, xMLSignature) -> format("%s: The system could not find the trustmark signing certificate.", trustmark.getIdentifier().toString()),
                (trustmark, x509Certificate, certificateEncodingException) -> format("%s: The system could not decode the trustmark signing certificate; message: %s", trustmark.getIdentifier().toString(), certificateEncodingException.getMessage()),
                (trustmark, jcaX509CertificateHolder) -> format("%s: The host for the trustmark provider identifier ('%s') does not match the trustmark signing certificate common name ('%s').", trustmark.getIdentifier().toString(), trustmark.getProvider().getIdentifier().toString(), jcaX509CertificateHolder.getSubject().getRDNs(BCStyle.CN)[0].getFirst().getValue()),
                (trustmark, xmlSignature) -> format("%s: The trustmark signing certificate has been revoked.", trustmark.getIdentifier().toString()),
                (trustmark) -> format("%s: The trustmark identifier does not start with the trustmark provider identifier ('%s').", trustmark.getIdentifier().toString(), trustmark.getProvider().getIdentifier()),
                (trustmark) -> format("%s: The trustmark has expired ('%s').", trustmark.getIdentifier().toString(), trustmark.getExpirationDateTime()),
                (trustmark, resolveException) -> format("%s: The system could not resolve the trustmark status report ('%s'); message: %s", trustmark.getIdentifier().toString(), trustmark.getStatusURL().toString(), resolveException.getMessage()),
                (trustmark, trustmarkStatusReport) -> format("%s: The trustmark status report ('%s') shows trustmark is not active ('%s')", trustmark.getIdentifier().toString(), trustmark.getStatusURL().toString(), trustmarkStatusReport.getStatus().toString()),
                (trustmark) -> format("%s: The trustmark recipient identifier ('%s') is not among the expected trustmark recipient identifiers.", trustmark.getIdentifier().toString(), trustmark.getRecipient().getIdentifier().toString()),
                (trustmark) -> format("%s: The trustmark's purpose is not consistent with the trustmark's meaning and intended usage.", trustmark.getIdentifier().toString()));
    }
}
