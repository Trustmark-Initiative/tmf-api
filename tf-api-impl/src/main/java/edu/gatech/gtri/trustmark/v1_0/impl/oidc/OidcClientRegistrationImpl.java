package edu.gatech.gtri.trustmark.v1_0.impl.oidc;

import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistration;
import org.gtri.fj.data.List;
import org.gtri.fj.data.Option;

import java.time.Instant;

import static java.util.Objects.requireNonNull;


/**
 * Oauth 2 Client registration metadata.
 */
public class OidcClientRegistrationImpl implements OidcClientRegistration {

    private final String clientName;
    private final String clientUri;
    private final String clientId;
    private final String clientSecret;
    private final Instant clientIdIssuedAt;
    private final Option<Instant> clientSecretExpiresAt;
    private final String registrationAccessToken;
    private final String registrationClientUri;
    private final List<String> grantTypeList;
    private final String tokenEndpointAuthMethod;
    private final List<String> scopeList;
    private final List<String> redirectUriList;
    private final String issuerUri;
    private final String jwkSetUri;
    private final String tokenUri;
    private final String userInfoUri;

    public OidcClientRegistrationImpl(
            final String clientName,
            final String clientUri,
            final String clientId,
            final String clientSecret,
            final Instant clientIdIssuedAt,
            final Option<Instant> clientSecretExpiresAt,
            final String registrationAccessToken,
            final String registrationClientUri,
            final List<String> grantTypeList,
            final String tokenEndpointAuthMethod,
            final List<String> scopeList,
            final List<String> redirectUriList,
            final String issuerUri,
            final String jwkSetUri,
            final String tokenUri,
            final String userInfoUri) {

        requireNonNull(clientName);
        requireNonNull(clientUri);
        requireNonNull(clientId);
        requireNonNull(clientSecret);
        requireNonNull(clientIdIssuedAt);
        requireNonNull(clientSecretExpiresAt);
        requireNonNull(registrationAccessToken);
        requireNonNull(registrationClientUri);
        requireNonNull(grantTypeList);
        requireNonNull(tokenEndpointAuthMethod);
        requireNonNull(scopeList);
        requireNonNull(redirectUriList);
        requireNonNull(issuerUri);
        requireNonNull(jwkSetUri);
        requireNonNull(tokenUri);
        requireNonNull(userInfoUri);

        this.clientName = clientName;
        this.clientUri = clientUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientIdIssuedAt = clientIdIssuedAt;
        this.clientSecretExpiresAt = clientSecretExpiresAt;
        this.registrationAccessToken = registrationAccessToken;
        this.registrationClientUri = registrationClientUri;
        this.grantTypeList = grantTypeList;
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
        this.scopeList = scopeList;
        this.redirectUriList = redirectUriList;
        this.issuerUri = issuerUri;
        this.jwkSetUri = jwkSetUri;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public String getClientUri() {
        return clientUri;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public Instant getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    @Override
    public Option<Instant> getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    @Override
    public String getRegistrationAccessToken() {
        return registrationAccessToken;
    }

    @Override
    public String getRegistrationClientUri() {
        return registrationClientUri;
    }

    @Override
    public List<String> getGrantTypeList() {
        return grantTypeList;
    }

    @Override
    public String getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    public List<String> getScopeList() {
        return scopeList;
    }

    @Override
    public List<String> getRedirectUriList() {
        return redirectUriList;
    }

    @Override
    public String getIssuerUri() {
        return issuerUri;
    }

    @Override
    public String getJwkSetUri() {
        return jwkSetUri;
    }

    @Override
    public String getTokenUri() {
        return tokenUri;
    }

    @Override
    public String getUserInfoUri() {
        return userInfoUri;
    }
}
