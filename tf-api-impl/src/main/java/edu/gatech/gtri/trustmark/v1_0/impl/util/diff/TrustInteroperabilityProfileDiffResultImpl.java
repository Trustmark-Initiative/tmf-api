package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffType;

/**
 * Simple straightforward implementation of a {@link TrustInteroperabilityProfileDiffResult}.
 * <br/><br/>
 * @user brad
 * @date 12/6/16
 */
public class TrustInteroperabilityProfileDiffResultImpl extends AbstractDiffResult implements TrustInteroperabilityProfileDiffResult {

    //==================================================================================================================
    //  Constructors
    //==================================================================================================================
    public TrustInteroperabilityProfileDiffResultImpl(){}
    public TrustInteroperabilityProfileDiffResultImpl(TrustInteroperabilityProfileDiffType diffType, DiffSeverity severity, String location, String description){
        super(severity, location, description);
        this.diffType = diffType;
    }

    private TrustInteroperabilityProfileDiffType diffType;


    @Override
    public TrustInteroperabilityProfileDiffType getDiffType() {
        return diffType;
    }

    public void setDiffType(TrustInteroperabilityProfileDiffType diffType) {
        this.diffType = diffType;
    }


}/* end TrustInteroperabilityProfileDiffResultImpl */