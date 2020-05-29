package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * Represents any exception encountered from resolving an artifact, such as a parsing error or network error.
 * @author GTRI Trustmark Team
 *
 */
@SuppressWarnings("serial")
public class ResolveException extends Exception {

	public ResolveException() {
	}

	public ResolveException(String message) {
		super(message);
	}

	public ResolveException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResolveException(Throwable cause) {
		super(cause);
	}

	public ResolveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
