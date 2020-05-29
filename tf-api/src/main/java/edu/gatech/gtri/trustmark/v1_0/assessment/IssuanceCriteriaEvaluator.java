package edu.gatech.gtri.trustmark.v1_0.assessment;

/**
 * Evaluates the issuance criteria of a TD against assessment results.
 * 
 * @author GTRI Trustmark Team
 */
public interface IssuanceCriteriaEvaluator {

	/**
	 * Evaluates the supplied issuance criteria against the supplied
	 * AssessmentResults.
	 * 
	 * @param issuanceCriteria
	 *            The issuance criteria to evaluate. Must not be null.
	 * @param results
	 *            The AssessmentResults to evaluate against. If null, then this
	 *            method assumes there is no input to evaluate against.
	 * @return The evaluation result
	 */
	public IssuanceCriteriaEvaluation evaluate(String issuanceCriteria,
			AssessmentResults results)
			throws IssuanceCriteriaEvaluationException;

}// end IssuanceCriteriaEvaluator