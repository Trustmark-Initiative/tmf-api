package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.TrustmarkDefinitionSnapshot;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class TrustmarkDefinitionSnapshotImpl extends AbstractAgreementSnapshotAdapter implements TrustmarkDefinitionSnapshot {
    
    ////// Instance Fields //////
    protected TrustmarkDefinitionRequirement reference;
    
    ////// Instance Methods //////
    @Override
    public TrustmarkDefinitionRequirement getReference() { return this.reference; }
    public void setReference(TrustmarkDefinitionRequirement reference) { this.reference = reference; }
    
}
