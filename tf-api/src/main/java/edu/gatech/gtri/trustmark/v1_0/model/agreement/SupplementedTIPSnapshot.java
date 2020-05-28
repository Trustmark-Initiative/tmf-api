package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import java.util.List;

/**
 * A TIP snapshot and its supplemental legal text (if any), as well as its referenced dependencies,
 * as required by a Responsibility in an Agreement.
 *
 * @author Nicholas Saney
 */
public interface SupplementedTIPSnapshot extends AbstractSupplementedTIPSnapshotParent {
    
    /**
     * Legally substantive text which supplements the definition of the TIP snapshot.
     */
    public String getSupplementalLegalText();
    
    /**
     * The TIP snapshot.
     */
    public TrustInteroperabilityProfileSnapshot getTrustInteroperabilityProfileSnapshot();
    
    /**
     * The TDs referenced by the TIP snapshot.
     */
    public List<TrustmarkDefinitionSnapshot> getTrustmarkDefinitionSnapshots();
    
}
