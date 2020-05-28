package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

/**
 * A snapshot of a TIP, as required by a Responsibility in an Agreement.
 *
 * @author Nicholas Saney
 */
public interface TrustInteroperabilityProfileSnapshot extends AbstractAgreementSnapshot {
    
    /**
     * The TIP stored in this snapshot.
     */
    public TrustInteroperabilityProfile getTrustInteroperabilityProfile();
    
}
