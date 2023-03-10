package edu.gatech.gtri.trustmark.v1_0.oidc;

public class OidcClientRegistrationException extends Exception {

    public OidcClientRegistrationException() {
    }

    public OidcClientRegistrationException(
            final String message) {

        super(message);
    }

    public OidcClientRegistrationException(
            final String message,
            final Throwable cause) {

        super(message, cause);
    }

    public OidcClientRegistrationException(
            final Throwable cause) {

        super(cause);
    }

    public OidcClientRegistrationException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
