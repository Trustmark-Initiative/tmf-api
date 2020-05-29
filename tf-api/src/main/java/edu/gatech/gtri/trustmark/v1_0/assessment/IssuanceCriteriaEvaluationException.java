package edu.gatech.gtri.trustmark.v1_0.assessment;

/**
 * Represents an error that occurred when evaluating the issuance criteria of a
 * trustmark definition.
 * 
 * @author GTRI Trustmark Team
 *
 */
@SuppressWarnings("serial")
public class IssuanceCriteriaEvaluationException extends Exception {

	public IssuanceCriteriaEvaluationException(String msg) {
		super(msg);
	}

	public IssuanceCriteriaEvaluationException(Throwable cause) {
		super(cause);
	}
}
