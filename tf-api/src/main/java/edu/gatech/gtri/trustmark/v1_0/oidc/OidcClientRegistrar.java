package edu.gatech.gtri.trustmark.v1_0.oidc;

/**
 * Implementations register an OIDC client.
 *
 * @author GTRI Trustmark Team
 */
public interface OidcClientRegistrar {

    OidcClientRegistration register(
            final String initialAccessToken,
            final String clientName,
            final String clientUri) throws OidcClientRegistrationException;
}
