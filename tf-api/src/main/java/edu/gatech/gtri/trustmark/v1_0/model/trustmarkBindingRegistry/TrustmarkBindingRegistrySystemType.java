package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import org.gtri.fj.function.F1;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.arrayList;

public enum TrustmarkBindingRegistrySystemType {
    SAML_IDP("SAML Identity Provider", "/public/systems/SAML_IDP", "SAML IDP"),
    SAML_SP("SAML Service Provider", "/public/systems/SAML_SP", "SAML SP"),
    OIDC_OP("OpenID Connect (OIDC) OpenID Provider", "/public/systems/OIDC_OP", "OIDC Provider"),
    OIDC_RP("OpenID Connect (OIDC) Relying Party", "/public/systems/OIDC_RP", "OIDC Relying Party"),
    CERTIFICATE("Certificate", "/public/systems/certificate", "Certificate");

    private final String name;
    private final String uriRelative;
    private final String nameForTrustmarkBindingRegistry;

    TrustmarkBindingRegistrySystemType(
            final String name,
            final String uriRelative,
            final String nameForTrustmarkBindingRegistry) {

        this.name = name;
        this.uriRelative = uriRelative;
        this.nameForTrustmarkBindingRegistry = nameForTrustmarkBindingRegistry;
    }

    public String getName() {
        return name;
    }

    public String getUriRelative() {
        return uriRelative;
    }

    public String getNameForTrustmarkBindingRegistry() {
        return nameForTrustmarkBindingRegistry;
    }

    public static TrustmarkBindingRegistrySystemType fromNameForTrustmarkBindingRegistry(final String nameForTrustmarkBindingRegistry) {

        requireNonNull(nameForTrustmarkBindingRegistry);

        return arrayList(TrustmarkBindingRegistrySystemType.values())
                .find(partnerSystemCandidateType -> partnerSystemCandidateType.getNameForTrustmarkBindingRegistry().equals(nameForTrustmarkBindingRegistry))
                .valueE(format("The system did not recognize '%s'", nameForTrustmarkBindingRegistry));
    }

    public <T1> T1 match(
            F1<TrustmarkBindingRegistrySystemType, T1> fSAMLIdentityProvider,
            F1<TrustmarkBindingRegistrySystemType, T1> fSAMLServiceProvider,
            F1<TrustmarkBindingRegistrySystemType, T1> fOIDCOpenIDProvider,
            F1<TrustmarkBindingRegistrySystemType, T1> fOIDCRelyingParty,
            F1<TrustmarkBindingRegistrySystemType, T1> fCertificate,
            F1<TrustmarkBindingRegistrySystemType, T1> fOther) {

        requireNonNull(fSAMLIdentityProvider);
        requireNonNull(fSAMLServiceProvider);
        requireNonNull(fOIDCOpenIDProvider);
        requireNonNull(fOIDCRelyingParty);
        requireNonNull(fCertificate);
        requireNonNull(fOther);

        return this == SAML_IDP ?
                fSAMLIdentityProvider.f(this) :
                this == SAML_SP ?
                        fSAMLServiceProvider.f(this) :
                        this == OIDC_OP ? fOIDCOpenIDProvider.f(this) :
                                this == OIDC_RP ? fOIDCRelyingParty.f(this) :
                                        this == CERTIFICATE ?
                                                fCertificate.f(this) :
                                                fOther.f(this);
    }
}
