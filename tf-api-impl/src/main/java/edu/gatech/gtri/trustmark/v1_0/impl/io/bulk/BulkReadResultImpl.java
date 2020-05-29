package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadResult;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.util.Collections;
import java.util.List;

/**
 * Created by Nicholas on 9/6/2016.
 */
public class BulkReadResultImpl implements BulkReadResult {
    
    // Instance Fields
    protected final List<TrustmarkDefinition> resultingTrustmarkDefinitions;
    protected final List<TrustInteroperabilityProfile> resultingTrustInteroperabilityProfiles;
    
    // Constructor 
    public BulkReadResultImpl(
        List<TrustmarkDefinition> _resultingTrustmarkDefinitions,
        List<TrustInteroperabilityProfile> _resultingTrustInteroperabilityProfiles
    ) {
        this.resultingTrustmarkDefinitions = Collections.unmodifiableList(_resultingTrustmarkDefinitions);
        this.resultingTrustInteroperabilityProfiles = Collections.unmodifiableList(_resultingTrustInteroperabilityProfiles);
    }
    
    // Instance Methods
    @Override
    public List<TrustmarkDefinition> getResultingTrustmarkDefinitions() {
        return this.resultingTrustmarkDefinitions;
    }

    @Override
    public List<TrustInteroperabilityProfile> getResultingTrustInteroperabilityProfiles() {
        return this.resultingTrustInteroperabilityProfiles;
    }
}
