package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;

/**
 * A snapshot of a TD, as required by a TIP referenced in an Agreement.
 *
 * @author Nicholas Saney
 */
public interface TrustmarkDefinitionSnapshot extends AbstractAgreementSnapshot {
    
    /**
     * The reference to the TD for this snapshot.
     */
    public TrustmarkDefinitionRequirement getReference();
    
}
