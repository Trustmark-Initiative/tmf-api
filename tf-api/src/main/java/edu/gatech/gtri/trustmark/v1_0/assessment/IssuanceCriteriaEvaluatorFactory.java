package edu.gatech.gtri.trustmark.v1_0.assessment;


/**
 * Knows how to create instances of the {@link IssuanceCriteriaEvaluator} class.
 * 
 * @author GTRI Trustmark Team
 */
public interface IssuanceCriteriaEvaluatorFactory {

	/**
	 * Responsible for building the {@link IssuanceCriteriaEvaluator} object.
	 * 
	 */
	public IssuanceCriteriaEvaluator createEvaluator();


}// end IssuanceCriteriaEvaluatorFactory