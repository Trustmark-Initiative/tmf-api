package edu.gatech.gtri.trustmark.v1_0.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.util.TypeSpecificComponentManager;

/**
 * Manages JSON diffing for complex types.
 * Created by Nicholas on 9/19/2016.
 */
public interface JsonDiffManager extends TypeSpecificComponentManager<JsonDiff> {
    
    @Override
    public <T> JsonDiff<? super T> getComponent(Class<T> type);
    
}
