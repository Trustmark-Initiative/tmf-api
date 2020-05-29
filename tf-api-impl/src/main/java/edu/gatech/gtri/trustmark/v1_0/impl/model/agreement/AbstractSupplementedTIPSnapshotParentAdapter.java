package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AbstractSupplementedTIPSnapshotParent;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.SupplementedTIPSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AbstractSupplementedTIPSnapshotParentAdapter implements AbstractSupplementedTIPSnapshotParent {
    
    ////// Instance Fields //////
    protected List<SupplementedTIPSnapshot> supplementedTIPSnapshots;
    
    ////// Instance Methods //////
    @Override
    public List<SupplementedTIPSnapshot> getSupplementedTIPSnapshots() {
        if (this.supplementedTIPSnapshots == null) { this.supplementedTIPSnapshots = new ArrayList<>(); }
        return this.supplementedTIPSnapshots;
    }
    public void setSupplementedTIPSnapshots(Collection<? extends SupplementedTIPSnapshot> supplementedTIPSnapshots) {
        this.getSupplementedTIPSnapshots().clear();
        this.getSupplementedTIPSnapshots().addAll(supplementedTIPSnapshots);
    }
    public void addToSupplementedTIPSnapshots(SupplementedTIPSnapshot supplementedTIPSnapshot) {
        this.getSupplementedTIPSnapshots().add(supplementedTIPSnapshot);
    }
    
}
