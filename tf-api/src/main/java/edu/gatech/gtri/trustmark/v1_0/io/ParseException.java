package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * Represents an error that occurred when parsing a trustmark framework
 * artifact.
 * 
 * @author GTRI Trustmark Team
 *
 */
@SuppressWarnings("serial")
public class ParseException extends ResolveException {

	public ParseException() {
	}

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

	public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
