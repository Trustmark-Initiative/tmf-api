package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Defines the result as being SATISFIED, NOT_SATISFIED or NOT_APPLICABLE.
 */
public enum AssessmentStepResultType {
    /**
     * The recipient has met the criteria.
     */
    YES,
    /**
     * The recipient has failed to meet a criteria.
     */
    NO,
    /**
     * The step is not applicable, in the assessed context.
     */
    NA,
    /**
     * Indicates that the state of the Result is not known at all.
     */
    UNKNOWN;

    public static AssessmentStepResultType fromString(String typeString){
        AssessmentStepResultType type = AssessmentStepResultType.UNKNOWN;
        if( typeString != null ) {
            for (AssessmentStepResultType cur : AssessmentStepResultType.values()) {
                if (cur.toString().equalsIgnoreCase(typeString.trim())) {
                    type = cur;
                    break;
                }
            }
        }
        return type;
    }
}
