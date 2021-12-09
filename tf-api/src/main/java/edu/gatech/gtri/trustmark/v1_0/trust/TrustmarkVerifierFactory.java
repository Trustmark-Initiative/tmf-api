package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;

import java.net.URI;

public interface TrustmarkVerifierFactory {

    TrustmarkVerifier createDefaultVerifier();

    TrustmarkVerifier createVerifier(
            final List<URI> trustmarkProviderURIList,
            final List<URI> trustmarkRecipientURIList);

    TrustmarkVerifier createVerifier(
            final F1<Entity, Boolean> trustmarkProviderFilter,
            final F1<Entity, Boolean> trustmarkRecipientFilter);

    TrustmarkVerifier createVerifier(
            final XmlSignatureValidator xmlSignatureValidator,
            final TrustmarkStatusReportResolver trustmarkStatusReportResolver,
            final List<URI> trustmarkProviderURINonEmptyList,
            final List<URI> trustmarkRecipientURINonEmptyList);

    TrustmarkVerifier createVerifier(
            final XmlSignatureValidator xmlSignatureValidator,
            final TrustmarkStatusReportResolver trustmarkStatusReportResolver,
            final F1<Entity, Boolean> trustmarkProviderFilter,
            final F1<Entity, Boolean> trustmarkRecipientFilter);
}
