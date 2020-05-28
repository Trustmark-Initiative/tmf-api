package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffType;

/**
 * Created by brad on 4/12/16.
 */
public class TrustmarkDefinitionDiffResultImpl extends AbstractDiffResult implements TrustmarkDefinitionDiffResult {
    //==================================================================================================================
    //  Constructors
    //==================================================================================================================
    public TrustmarkDefinitionDiffResultImpl(){}
    public TrustmarkDefinitionDiffResultImpl(TrustmarkDefinitionDiffType diffType, DiffSeverity severity, String location, String description){
        super(severity, location, description);
        this.diffType = diffType;
    }
    //==================================================================================================================
    //  Private Instance Variables
    //==================================================================================================================
    private TrustmarkDefinitionDiffType diffType;
    //==================================================================================================================
    //  Getters
    //==================================================================================================================
    @Override
    public TrustmarkDefinitionDiffType getDiffType() {
        return diffType;
    }
    //==================================================================================================================
    //  Setters
    //==================================================================================================================
    public void setDiffType(TrustmarkDefinitionDiffType diffType){
        this.diffType = diffType;
    }

}
