package edu.gatech.gtri.trustmark.v1_0.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;

/**
 * This interface allows calling code to customize how comparisons between parts of a trustmark definition should occur.
 * How this class is used is still internal code, but you should be able to tailor the diff to your needs by implementing
 * this class.  For example, if you are unhappy with the way string diff calculations occur, then you can fix them here.
 * <br/><br/>
 * Created by brad on 4/14/16.
 */
public interface TrustmarkDefinitionComparator extends DiffComparator {

    /**
     * Examines the criteria individually and determines if they are the same or if they are different. Returns a
     * numeric value to express the "distance" between the criterion.
     */
    int getCriteriaSimilarity(ConformanceCriterion crit1, ConformanceCriterion crit2);

    /**
     * Answers the question of whether or not the distance is too far to consider these two the "same" for diff purposes.
     */
    boolean isCriteriaSimilar(int distance, ConformanceCriterion crit1, ConformanceCriterion crit2);

    /**
     * Examines the AssessmentSteps individually and determines if they are the same or if they are different.  Returns a
     * numeric value to express the "distance" between the steps.
     */
    int getAssessmentStepSimilarity(AssessmentStep step1, AssessmentStep step2);

    /**
     * Answers the question of whether or not the distance is too far to consider these two the "same" for diff purposes.
     */
    boolean isAssessmentStepSimilar(int distance, AssessmentStep step1, AssessmentStep step2);
    
    /**
     * Examines the parameters individually and determines if they are the same or if they are different.  Returns a
     * numeric value to express the "distance" between the parameters.
     */
    int getParametersSimilarity(TrustmarkDefinitionParameter param1, TrustmarkDefinitionParameter param2);
    
    /**
     * Answers the question of whether or not the distance is too far to consider these two the "same" for diff purposes.
     */
    boolean isParameterSimilar(int distance, TrustmarkDefinitionParameter param1, TrustmarkDefinitionParameter param2);

}
