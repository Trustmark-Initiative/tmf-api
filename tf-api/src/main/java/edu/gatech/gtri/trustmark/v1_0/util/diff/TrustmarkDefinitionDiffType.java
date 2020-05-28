package edu.gatech.gtri.trustmark.v1_0.util.diff;

/**
 * Created by brad on 4/12/16.
 */
public enum TrustmarkDefinitionDiffType {
    /**
     * Indicates the text found in one field does not match the text in another field.  The difference in strings is
     * measured, and if they are close but not identical (ie, typo) then this is thrown.
     */
    TEXT_SLIGHTLY_DIFFERENT,
    /**
     * Similar to TEXT_SLIGHTLY_DIFFERENT, this is given when the text in two fields is completely different.
     */
    TEXT_COMPLETELY_DIFFERENT,
    /**
     * Indicates that a metadata field exists on one TD that doesn't exist on the other.  This only applies to those
     * fields that are not required, such as the ProviderExtension.
     */
    NOT_REQUIRED_METADATA_FIELD_MISSING,
    /**
     * Indicates that one of the major required metadata fields (such as identifier or version) is different.
     */
    MAJOR_METADATA_ID_FIELD_DOES_NOT_MATCH,

    /**
     * Indicates that a Term exists in one TD but not the other.
     */
    TERM_DOES_NOT_EXIST,

    /**
     * Indicates that a source exists in one TD but not the other.
     */
    SOURCE_DOES_NOT_EXIST,




    /**
     * Indicates that the count of criteria is not identical.
     */
    CRITERIA_COUNT_MISMATCH,
    /**
     * Indicates that no similarities were found in criterion at all.
     */
    CRITERIA_COMPLETELY_DIFFERENT,
    /**
     * Indicates that a criterion was found in one TD and not the other.
     */
    CRITERIA_NOT_FOUND,
    /**
     * Indicates that 2 criteria which are substantially similar have different numbers.
     */
    CRITERIA_NUMBER_MISALIGNED,
    /**
     * Indicates that the citations in two criteria are not identical.
     */
    CRITERIA_CITATIONS_MISMATCH,





    /**
     * Indicates that the Assessment Step Count is not the same.
     */
    STEP_COUNT_MISMATCH,
    /**
     * Indicates that a step was found in one TD and not found in another.
     */
    STEP_NOT_FOUND,
    /**
     * Indicates that 2 assessment steps which are substantially similar have different numbers.
     */
    STEP_NUMBER_MISALIGNED,
    /**
     * Indicates that the artifacts in two assessment steps are not identical.
     */
    STEP_ARTIFACTS_MISMATCH,
    
    
    /**
     * Indicates that the Assessment Step Parameter Count is not the same.
     */
    STEP_PARAMETER_COUNT_MISMATCH,
    /**
     * Indicates that a parameter was found in one TD step and not found in another.
     */
    STEP_PARAMETER_NOT_FOUND,
    /**
     * Indicates that two assessment step parameters are of different kinds.
     */
    STEP_PARAMETERS_KIND_MISMATCH,
    /**
     * Indicates that the enum values in two assessment step parameters are not identical.
     */
    STEP_PARAMETERS_ENUM_VALUES_MISMATCH,
    /**
     * Indicates that the requirement levels of two assessment step parameters are not identical.
     */
    STEP_PARAMETERS_REQUIREMENT_MISMATCH;
}