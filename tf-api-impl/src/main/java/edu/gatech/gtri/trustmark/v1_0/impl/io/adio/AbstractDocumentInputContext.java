package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicholas on 02/01/2017.
 */
public class AbstractDocumentInputContext {
    
    //// Instance Fields ////
    protected final Map<Class<?>, Map<String, Object>> idResolutionMap = new HashMap<>();
    
    //// Instance Methods ////
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getEncounteredObjectsMapForType(Class<T> type) {
        Map<String, Object> result = this.idResolutionMap.get(type);
        if (result == null) {
            result = new HashMap<>();
            this.idResolutionMap.put(type, result);
        }
        return (Map<String, T>) result;
    }
    
}
