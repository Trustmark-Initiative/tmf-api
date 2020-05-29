package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Created by brad on 4/28/16.
 */
public interface AssessmentStepResult {

    /**
     * @return The unique identifier for the assessment step.
     */
    String getAssessmentStepId();

    /**
     * @return The number of this assessment step, 1-indexed.
     */
    Integer getAssessmentStepNumber();

    /**
     * Indicates what the actual result is.
     */
    AssessmentStepResultType getResult();

}
