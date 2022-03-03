package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * An exception that occurs when the system attempts to resolve an artifact.
 *
 * @author GTRI Trustmark Team
 */
public class ResolveException extends Exception {

    public ResolveException() {
    }

    public ResolveException(final String message) {
        super(message);
    }

    public ResolveException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResolveException(final Throwable cause) {
        super(cause);
    }

    public ResolveException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
