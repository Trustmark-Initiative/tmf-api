package edu.gatech.gtri.trustmark.v1_0.util.diff;

/**
 * Provides basic abstraction of Diff comparison logic, so code utilizing this library can customize results.
 * <br/><br/>
 * @user brad
 * @date 12/6/16
 */
public interface DiffComparator {


    /**
     * Calculates the "difference" of the two strings.  Ideally, something like levenshtein is used here.
     */
    int getStringDistance(String f1, String f2);

    /**
     * Given the levenshtein distance and the 2 strings on which it was calculated, this method will return "true" if
     * the strings are significantly different and "false" if they are mostly the same.
     */
    boolean isTextSignficantlyDifferent(int distance, String f1, String f2);



}/* end DiffComparator */