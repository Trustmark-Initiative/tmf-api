package edu.gatech.gtri.trustmark.v1_0.util.diff;

/**
 * Created by brad on 4/12/16.
 */
public enum TrustInteroperabilityProfileDiffType {
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
    NOT_REQUIRED_FIELD_MISSING,
    /**
     * Indicates that one of the major required metadata fields (such as identifier or version) is different.
     */
    FIELD_DOES_NOT_MATCH,
    /**
     * Indicates that a reference was found on one TIP that was not on the other.
     */
    REFERENCE_MISMATCH;

}