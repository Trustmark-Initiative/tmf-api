package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkStatusReportResolverImpl;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifier;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFactory;
import edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidator;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;
import org.kohsuke.MetaInfServices;

import java.net.URI;

@MetaInfServices
public class TrustmarkVerifierFactoryImpl implements TrustmarkVerifierFactory {

    @Override
    public TrustmarkVerifier createDefaultVerifier() {

        return createVerifier(
                new XmlSignatureValidatorImpl(),
                new TrustmarkStatusReportResolverImpl(),
                entity -> true,
                entity -> true);
    }

    @Override
    public TrustmarkVerifier createVerifier(
            final List<URI> trustmarkProviderURIList,
            final List<URI> trustmarkRecipientURIList) {

        return createVerifier(
                new XmlSignatureValidatorImpl(),
                new TrustmarkStatusReportResolverImpl(),
                trustmarkProviderURIList,
                trustmarkRecipientURIList);
    }

    @Override
    public TrustmarkVerifier createVerifier(
            final F1<Entity, Boolean> trustmarkProviderFilter,
            final F1<Entity, Boolean> trustmarkRecipientFilter) {

        return createVerifier(
                new XmlSignatureValidatorImpl(),
                new TrustmarkStatusReportResolverImpl(),
                trustmarkProviderFilter,
                trustmarkRecipientFilter);
    }

    @Override
    public TrustmarkVerifier createVerifier(
            final XmlSignatureValidator xmlSignatureValidator,
            final TrustmarkStatusReportResolver trustmarkStatusReportResolver,
            final List<URI> trustmarkProviderURIList,
            final List<URI> trustmarkRecipientURIList) {

        return createVerifier(
                xmlSignatureValidator,
                trustmarkStatusReportResolver,
                entity -> trustmarkProviderURIList.isEmpty() || trustmarkProviderURIList.exists(trustmarkProviderURI -> trustmarkProviderURI.equals(entity.getIdentifier())),
                entity -> trustmarkRecipientURIList.isEmpty() || trustmarkRecipientURIList.exists(trustmarkRecipientURI -> trustmarkRecipientURI.equals(entity.getIdentifier())));
    }

    @Override
    public TrustmarkVerifier createVerifier(
            final XmlSignatureValidator xmlSignatureValidator,
            final TrustmarkStatusReportResolver trustmarkStatusReportResolver,
            final F1<Entity, Boolean> trustmarkProviderFilter,
            final F1<Entity, Boolean> trustmarkRecipientFilter) {

        return new TrustmarkVerifierImpl(
                xmlSignatureValidator,
                trustmarkStatusReportResolver,
                trustmarkProviderFilter,
                trustmarkRecipientFilter);
    }
}
