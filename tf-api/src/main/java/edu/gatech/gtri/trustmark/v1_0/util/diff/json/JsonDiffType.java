package edu.gatech.gtri.trustmark.v1_0.util.diff.json;

/**
 * Created by Nicholas on 09/19/2016.
 */
public enum JsonDiffType {
    /**
     * Indicates that a field is present on both compared objects, but the values differ.
     */
    MISMATCHED,
    
    /**
     * Indicates that a field is present on the first object, but absent on the second.
     */
    MISSING,
    
    /**
     * Indicates that a field is absent on the first object, but present on the second.
     */
    UNEXPECTED,
    
    /**
     * Indicates that a field is present on one or both compared objects, but the field was
     * not compared because it was not found in the diff specification.
     */
    NOT_COMPARED
}
