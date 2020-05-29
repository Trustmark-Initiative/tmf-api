package edu.gatech.gtri.trustmark.v1_0.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

import java.util.Collection;

/**
 * Created by Nicholas on 9/16/2016.
 */
public interface JsonDiffResultCollection {
    
    public Collection<JsonDiffResult> getResultsForSeverity(DiffSeverity severity);
    
}
