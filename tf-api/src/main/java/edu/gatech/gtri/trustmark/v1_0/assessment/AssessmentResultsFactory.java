package edu.gatech.gtri.trustmark.v1_0.assessment;


/**
 * Knows how to create instances of the {@link AssessmentResults} class.
 * 
 * @author GTRI Trustmark Team
 */
public interface AssessmentResultsFactory {

	/**
	 * Responsible for building the {@link AssessmentResults} object.
	 * 
	 */
	public AssessmentResults createAssessmentResults();

}// end AssessmentResultsFactory