package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AbstractAgreementSnapshot;

import java.util.Date;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AbstractAgreementSnapshotAdapter implements AbstractAgreementSnapshot {
    
    ////// Instance Fields //////
    protected Date snapshotDateTime;
    protected int index;
    protected String expressionId;
    
    ////// Instance Methods //////
    @Override
    public Date getSnapshotDateTime() { return this.snapshotDateTime; }
    public void setSnapshotDateTime(Date snapshotDateTime) { this.snapshotDateTime = snapshotDateTime; }
    
    @Override
    public int getIndex() { return this.index; }
    public void setIndex(int index) { this.index = index; }
    
    @Override
    public String getExpressionId() { return this.expressionId; }
    public void setExpressionId(String expressionId) { this.expressionId = expressionId; }
    
}
