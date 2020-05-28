package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffResultCollection;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.Collection;

/**
 * Created by Nicholas on 9/16/2016.
 */
public class JsonDiffResultCollectionImpl implements JsonDiffResultCollection {
    
    // Instance Properties
    private final MultiValuedMap<DiffSeverity, JsonDiffResult> diffResultsBySeverity = new ArrayListValuedHashMap<>();
    
    public void put(JsonDiffResult result) {
        this.diffResultsBySeverity.put(result.getSeverity(), result);
    }
    
    public void putAll(Collection<? extends JsonDiffResult> results) {
        for (JsonDiffResult result : results) {
            this.put(result);
        }
    }
    
    // Constructor
    public JsonDiffResultCollectionImpl() {
        // nothing here right now
    }
    
    // Instance Methods
    @Override
    public Collection<JsonDiffResult> getResultsForSeverity(DiffSeverity severity) {
        return this.diffResultsBySeverity.get(severity);
    }
}
