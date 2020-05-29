package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.SupplementedTIPSnapshot;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.TrustInteroperabilityProfileSnapshot;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.TrustmarkDefinitionSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class SupplementedTIPSnapshotImpl extends AbstractSupplementedTIPSnapshotParentAdapter implements SupplementedTIPSnapshot {
    
    ////// Instance Fields //////
    protected String supplementalLegalText;
    protected TrustInteroperabilityProfileSnapshot trustInteroperabilityProfileSnapshot;
    protected List<TrustmarkDefinitionSnapshot> trustmarkDefinitionSnapshots;
    
    
    ////// Instance Methods //////
    @Override
    public String getSupplementalLegalText() { return this.supplementalLegalText; }
    public void setSupplementalLegalText(String supplementalLegalText) { this.supplementalLegalText = supplementalLegalText; }
    
    @Override
    public TrustInteroperabilityProfileSnapshot getTrustInteroperabilityProfileSnapshot() { return this.trustInteroperabilityProfileSnapshot; }
    public void setTrustInteroperabilityProfileSnapshot(TrustInteroperabilityProfileSnapshot trustInteroperabilityProfileSnapshot) { this.trustInteroperabilityProfileSnapshot = trustInteroperabilityProfileSnapshot; }
    
    @Override
    public List<TrustmarkDefinitionSnapshot> getTrustmarkDefinitionSnapshots() {
        if (this.trustmarkDefinitionSnapshots == null) { this.trustmarkDefinitionSnapshots = new ArrayList<>(); }
        return this.trustmarkDefinitionSnapshots;
    }
    public void setTrustmarkDefinitionSnapshots(Collection<? extends TrustmarkDefinitionSnapshot> trustmarkDefinitionSnapshots) {
        this.getTrustmarkDefinitionSnapshots().clear();
        this.getTrustmarkDefinitionSnapshots().addAll(trustmarkDefinitionSnapshots);
    }
    public void addToTrustmarkDefinitionSnapshots(TrustmarkDefinitionSnapshot trustmarkDefinitionSnapshot) {
        this.getTrustmarkDefinitionSnapshots().add(trustmarkDefinitionSnapshot);
    }
    
}
