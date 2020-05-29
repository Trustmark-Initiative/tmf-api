package edu.gatech.gtri.trustmark.v1_0.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.util.TypeSpecificComponent;

/**
 * Created by Nicholas on 9/16/2016.
 */
public interface JsonDiff<T> extends TypeSpecificComponent<T> {
    
    /**
     * Performs a JSON diff on the two given objects.
     * @param expectedName the name of the object with the expected values
     * @param expected the object with the expected values
     * @param actualName the name of the object that is being compared against the expected values
     * @param actual   the object that is being compared against the expected values
     * @return a collections of the differences between the expected and actual values
     */
    public JsonDiffResultCollection doDiff(String expectedName, T expected, String actualName, T actual);
    
}
