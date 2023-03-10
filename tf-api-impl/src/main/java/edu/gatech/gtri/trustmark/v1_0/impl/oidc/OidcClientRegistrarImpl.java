package edu.gatech.gtri.trustmark.v1_0.impl.oidc;

import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistrar;
import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistration;
import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistrationException;
import org.keycloak.OAuth2Constants;
import org.keycloak.TokenVerifier;
import org.keycloak.client.registration.Auth;
import org.keycloak.client.registration.ClientRegistration;
import org.keycloak.client.registration.ClientRegistrationException;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.oidc.OIDCClientRepresentation;

import java.time.Instant;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;

public class OidcClientRegistrarImpl implements OidcClientRegistrar {

    @Override
    public OidcClientRegistration register(
            final String initialAccessToken,
            final String clientName,
            final String clientUri) throws OidcClientRegistrationException {

        try {
            final String issuerUri = TokenVerifier.create(initialAccessToken, AccessToken.class).getToken().getIssuer();
            final String realmUrl = issuerUri + "/clients-registrations";
            // TODO: Retrieve the following from issuerUri + "/.well-known/openid-configuration"
            final String jwkSetUri = issuerUri + "/protocol/openid-connect/certs";
            final String tokenUri = issuerUri + "/protocol/openid-connect/token";
            final String userInfoUri = issuerUri + "/protocol/openid-connect/userinfo";

            OIDCClientRepresentation oidcClientRepresentation = new OIDCClientRepresentation();
            oidcClientRepresentation.setClientName(clientName);
            oidcClientRepresentation.setClientUri(clientUri);
            oidcClientRepresentation.setGrantTypes(singletonList(OAuth2Constants.CLIENT_CREDENTIALS));

            oidcClientRepresentation = ClientRegistration
                    .create()
                    .url(realmUrl)
                    .build()
                    .auth(Auth.token(initialAccessToken))
                    .oidc()
                    .create(oidcClientRepresentation);

            return new OidcClientRegistrationImpl(
                    oidcClientRepresentation.getClientName(),
                    oidcClientRepresentation.getClientUri(),
                    oidcClientRepresentation.getClientId(),
                    oidcClientRepresentation.getClientSecret(),
                    Instant.ofEpochSecond(oidcClientRepresentation.getClientIdIssuedAt()),
                    oidcClientRepresentation.getClientSecretExpiresAt() == 0 ? none() : some(Instant.ofEpochSecond(oidcClientRepresentation.getClientSecretExpiresAt())),
                    oidcClientRepresentation.getRegistrationAccessToken(),
                    oidcClientRepresentation.getRegistrationClientUri(),
                    iterableList(oidcClientRepresentation.getGrantTypes()),
                    oidcClientRepresentation.getTokenEndpointAuthMethod(),
                    iterableList(asList(oidcClientRepresentation.getScope().split(" +"))),
                    iterableList(oidcClientRepresentation.getRedirectUris()),
                    issuerUri,
                    jwkSetUri,
                    tokenUri,
                    userInfoUri);

        } catch (final ClientRegistrationException clientRegistrationException) {
            throw new OidcClientRegistrationException(clientRegistrationException);
        } catch (final VerificationException verificationException) {
            throw new OidcClientRegistrationException(verificationException);
        }
    }
}
