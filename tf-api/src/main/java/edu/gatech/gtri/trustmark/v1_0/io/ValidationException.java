package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * Represents an error that occurred when validating the structure and content
 * of a Trustmark Framework artifact.
 * 
 * @author GTRI Trustmark Team
 *
 */
@SuppressWarnings("serial")
public class ValidationException extends Exception {

	public ValidationException() {
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
