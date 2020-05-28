package edu.gatech.gtri.trustmark.v1_0.tip;

/**
 * Represents an error that occurred when evaluating a trust interoperability
 * profile.
 * 
 * @author GTRI Trustmark Team
 *
 */
@SuppressWarnings("serial")
public class TIPEvaluationException extends Exception {

	/**
	 * Sets the exception message.
	 * 
	 * @param message
	 *            Exception message.
	 */
	public TIPEvaluationException(String message) {
		super(message);
	}

	/**
	 * Sets the Throwable that's the cause of this exception.
	 * 
	 * @param cause
	 *            A Throwable that's the cause of this exception.
	 */
	public TIPEvaluationException(Throwable cause) {
		super(cause);
	}

}
