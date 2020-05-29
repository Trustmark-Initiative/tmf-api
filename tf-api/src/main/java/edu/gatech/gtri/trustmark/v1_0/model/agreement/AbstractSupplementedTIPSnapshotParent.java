package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import java.util.List;

/**
 * The parent of a list of supplemented TIP snapshots.
 *
 * @author Nicholas Saney
 */
public interface AbstractSupplementedTIPSnapshotParent {
    
    /**
     * The contained supplemented TIP snapshots.
     */
    public List<SupplementedTIPSnapshot> getSupplementedTIPSnapshots();
    
}
