package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import java.util.Map;

/**
 * An artifact that is created by the bulk read process.
 * Created by Nicholas on 12/06/2016.
 */
public interface BulkReadArtifact {
    
    /**
     * Transient data used for auxiliary purposes immediately after this artifact is created.
     * This may contain contextual information gathered by the bulk read process.
     */
    public Map<String, Object> getTransientDataMap();
    
}
