package edu.gatech.gtri.trustmark.v1_0.trust;

/**
 * Represents an error that occurred when validating a trustmark.
 * 
 * @author GTRI Trustmark Team
 *
 */
@SuppressWarnings("serial")
public class TrustVerificationException extends Exception {

    public TrustVerificationException() {
    }

    public TrustVerificationException(String message) {
        super(message);
    }

    public TrustVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrustVerificationException(Throwable cause) {
        super(cause);
    }

    public TrustVerificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
