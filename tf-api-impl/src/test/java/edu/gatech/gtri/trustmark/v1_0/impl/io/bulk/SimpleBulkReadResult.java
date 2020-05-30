package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadResult;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * An example implementation for the purpose of doing diffs.
 * <br/><br/>
 * @author brad
 * @date 2016-09-08
 */
public class SimpleBulkReadResult implements BulkReadResult {


    private List<TrustmarkDefinition> resultingTrustmarkDefinitions;
    private List<TrustInteroperabilityProfile> resultingTrustInteroperabilityProfiles;
    private List<String> resultingInvalidParameters;

    @Override
    public List<TrustmarkDefinition> getResultingTrustmarkDefinitions() {
        if( resultingTrustmarkDefinitions == null )
            resultingTrustmarkDefinitions = new ArrayList<>();
        return resultingTrustmarkDefinitions;
    }

    @Override
    public List<TrustInteroperabilityProfile> getResultingTrustInteroperabilityProfiles() {
        if( resultingTrustInteroperabilityProfiles == null )
            resultingTrustInteroperabilityProfiles = new ArrayList<>();
        return resultingTrustInteroperabilityProfiles;
    }

    @Override
    public List<String> getResultingInvalidParameters() {
        if( this.resultingInvalidParameters == null )
            this.resultingInvalidParameters = new ArrayList<>();
        return this.resultingInvalidParameters;
    }

    public void setResultingTrustmarkDefinitions(List<TrustmarkDefinition> resultingTrustmarkDefinitions) {
        this.resultingTrustmarkDefinitions = resultingTrustmarkDefinitions;
    }

    public void setResultingTrustInteroperabilityProfiles(List<TrustInteroperabilityProfile> resultingTrustInteroperabilityProfiles) {
        this.resultingTrustInteroperabilityProfiles = resultingTrustInteroperabilityProfiles;
    }

    public void setResultingInvalidParameters(List<String> resultingInvalidParameters) {
        this.resultingInvalidParameters = resultingInvalidParameters;
    }
}
