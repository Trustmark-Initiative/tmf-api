package edu.gatech.gtri.trustmark.v1_0.tip;


/**
 * Knows how to create instances of the {@link TIPEvaluator} class.
 * 
 * @author GTRI Trustmark Team
 */
public interface TIPEvaluatorFactory {

	/**
	 * Constructs a TIP Evaluator which is the simple, default flavor.  It does not support satisfaction gap analysis
	 * or anything fancy.
	 */
	public TIPEvaluator createDefaultEvaluator();

}// end IssuanceCriteriaEvaluatorFactory