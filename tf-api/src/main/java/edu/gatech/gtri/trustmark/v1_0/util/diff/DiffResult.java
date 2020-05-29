package edu.gatech.gtri.trustmark.v1_0.util.diff;

import java.util.Map;

/**
 * Created by brad on 4/14/16.
 */
public interface DiffResult {

    /**
     * Indicates the severity of this result.
     */
    public DiffSeverity getSeverity();

    /**
     * Returns the location in terms of the model of where the error occurred.  This is presumably only human readable,
     * not automatically machine processable.
     */
    public String getLocation();

    /**
     * Provides the code author's interpretation of this error.
     */
    public String getDescription();

    /**
     * A generic "bag" to hold the different information relating to this DiffResult.
     */
    public Map getData();


}
