package edu.gatech.gtri.trustmark.v1_0.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffResult;

/**
 * Created by Nicholas on 9/16/2016.
 */
public interface JsonDiffResult extends DiffResult {
    
    /**
     * The JSON field from the internal comparison.
     */
    public String getJsonField();
    
    /**
     * The original expected object from the internal comparison.
     */
    public Object getJsonExpected();
    
    /**
     * The original actual object the internal comparison.
     */
    public Object getJsonActual();
    
    /**
     * The type of the diff result.
     */
    public JsonDiffType getJsonDiffType();
    
}
