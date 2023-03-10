package edu.gatech.gtri.trustmark.v1_0.impl.oidc;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistrar;
import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistration;
import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistrationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class TestOidcClientRegistrarImpl extends AbstractTest {

    public static final String initialAccessToken = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwYzBkYmI3Mi03NDA0LTQ5OWUtOTNiMi0yMWI4NjZlZTkyZjEifQ.eyJleHAiOjE2NzcwOTYzMzksImlhdCI6MTY3NzAwOTkzOSwianRpIjoiNDYzNGM5MjYtZjdjZC00ZjRmLThmYTgtNWZlODE4ZTdiNGVkIiwiaXNzIjoiaHR0cHM6Ly9rZXkudHJ1c3RtYXJraW5pdGlhdGl2ZS5vcmcvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiaHR0cHM6Ly9rZXkudHJ1c3RtYXJraW5pdGlhdGl2ZS5vcmcvYXV0aC9yZWFsbXMvbWFzdGVyIiwidHlwIjoiSW5pdGlhbEFjY2Vzc1Rva2VuIn0.r6vqbZ2nOkkxDF3f3VTadq7E8dVuIbXVXUlXfZMBq7o";

    @Test
    public void testDynamicClientRegistrationObjectCreation() {
        OidcClientRegistrar oidcClientRegistrar = FactoryLoader.getInstance(OidcClientRegistrar.class);

        assertThat(oidcClientRegistrar, notNullValue());
    }

    @Test
    public void testDynamicClientRegistration() {
        try {
            OidcClientRegistrar oidcClientRegistrar = FactoryLoader.getInstance(OidcClientRegistrar.class);

            assertThat(oidcClientRegistrar, notNullValue());

            String clientName = "Portal 6000";
            String clientUri = "http://localhost:2023/portal6000";

            OidcClientRegistration oidcClientRegistration = oidcClientRegistrar.register(initialAccessToken, clientName, clientUri);

            System.out.println("getClientId                " + oidcClientRegistration.getClientId());
            System.out.println("getClientSecret            " + oidcClientRegistration.getClientSecret());
            System.out.println("getClientName              " + oidcClientRegistration.getClientName());
            System.out.println("getClientUri               " + oidcClientRegistration.getClientUri());
            System.out.println("getRegistrationAccessToken " + oidcClientRegistration.getRegistrationAccessToken());
            System.out.println("getRegistrationClientUri   " + oidcClientRegistration.getRegistrationClientUri());
            System.out.println("getClientIdIssuedAt        " + oidcClientRegistration.getClientIdIssuedAt());
            System.out.println("getClientSecretExpiresAt   " + oidcClientRegistration.getClientSecretExpiresAt());
            System.out.println("getGrantTypeList           " + oidcClientRegistration.getGrantTypeList());
            System.out.println("getTokenEndpointAuthMethod " + oidcClientRegistration.getTokenEndpointAuthMethod());
            System.out.println("getScopeList               " + oidcClientRegistration.getScopeList());
            System.out.println("getRedirectUriList         " + oidcClientRegistration.getRedirectUriList());
            System.out.println("getIssuerUri               " + oidcClientRegistration.getIssuerUri());
            System.out.println("getJwkSetUri               " + oidcClientRegistration.getJwkSetUri());
            System.out.println("getTokenUri                " + oidcClientRegistration.getTokenUri());
            System.out.println("getUserInfoUri             " + oidcClientRegistration.getUserInfoUri());

            assertThat(oidcClientRegistration.getClientId(), notNullValue());
            assertThat(oidcClientRegistration.getClientSecret(), notNullValue());
            assertThat(oidcClientRegistration.getClientName(), notNullValue());
            assertThat(oidcClientRegistration.getClientUri(), notNullValue());
            assertThat(oidcClientRegistration.getRegistrationAccessToken(), notNullValue());
            assertThat(oidcClientRegistration.getRegistrationClientUri(), notNullValue());
            assertThat(oidcClientRegistration.getClientIdIssuedAt(), notNullValue());
            assertThat(oidcClientRegistration.getClientSecretExpiresAt(), notNullValue());
            assertThat(oidcClientRegistration.getGrantTypeList(), notNullValue());
            assertThat(oidcClientRegistration.getTokenEndpointAuthMethod(), notNullValue());
            assertThat(oidcClientRegistration.getScopeList(), notNullValue());
            assertThat(oidcClientRegistration.getRedirectUriList(), notNullValue());
            assertThat(oidcClientRegistration.getIssuerUri(), notNullValue());
            assertThat(oidcClientRegistration.getJwkSetUri(), notNullValue());
            assertThat(oidcClientRegistration.getTokenUri(), notNullValue());
            assertThat(oidcClientRegistration.getUserInfoUri(), notNullValue());

        } catch (final OidcClientRegistrationException oidcClientRegistrationException) {
            oidcClientRegistrationException.printStackTrace();
        }
    }
}
