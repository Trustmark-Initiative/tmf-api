package edu.gatech.gtri.trustmark.v1_0.oidc;

import org.gtri.fj.data.List;
import org.gtri.fj.data.Option;

import java.time.Instant;

/**
 * Implementations represent an OIDC client registration.
 *
 * @author GTRI Trustmark Team
 */
public interface OidcClientRegistration {

    String getClientName();

    String getClientUri();

    String getClientId();

    String getClientSecret();

    Instant getClientIdIssuedAt();

    Option<Instant> getClientSecretExpiresAt();

    String getRegistrationAccessToken();

    String getRegistrationClientUri();

    List<String> getGrantTypeList();

    String getTokenEndpointAuthMethod();

    List<String> getScopeList();

    List<String> getRedirectUriList();

    String getIssuerUri();

    String getJwkSetUri();

    String getTokenUri();

    String getUserInfoUri();
}
