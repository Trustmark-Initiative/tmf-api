package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.TrustInteroperabilityProfileSnapshot;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class TrustInteroperabilityProfileSnapshotImpl extends AbstractAgreementSnapshotAdapter implements TrustInteroperabilityProfileSnapshot {
    
    ////// Instance Fields //////
    protected TrustInteroperabilityProfile trustInteroperabilityProfile;
    
    ////// Instance Methods //////
    @Override
    public TrustInteroperabilityProfile getTrustInteroperabilityProfile() { return this.trustInteroperabilityProfile; }
    public void setTrustInteroperabilityProfile(TrustInteroperabilityProfile trustInteroperabilityProfile) {
        this.trustInteroperabilityProfile = trustInteroperabilityProfile;
    }
    
}
