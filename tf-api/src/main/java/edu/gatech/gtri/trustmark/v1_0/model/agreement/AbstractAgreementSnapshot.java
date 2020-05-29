package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import java.util.Date;

/**
 * A snapshot of a TMF object referenced in an Agreement.
 *
 * @author Nicholas Saney
 */
public interface AbstractAgreementSnapshot {
    
    /**
     * The timestamp of this snapshot.
     */
    public Date getSnapshotDateTime();
    
    /**
     * The index of the stored snapshot (TD or TIP) in its parent TIP,
     * or zero if this is a top-level TIP snapshot.
     */
    public int getIndex();
    
    /**
     * The ID of the stored snapshot (TD or TIP) as referenced from its parent TIP's trust expression,
     * or null if this is a snapshot of a top-level TIP.
     */
    public String getExpressionId();
    
}
