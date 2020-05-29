package edu.gatech.gtri.trustmark.v1_0.util.diff;

/**
 * Represents the difference result between two trustmark definitions.
 * <br/><br/>
 * Created by brad on 4/12/16.
 */
public interface TrustmarkDefinitionDiffResult extends DiffResult {

    /**
     * Returns the type of this error.
     */
    public TrustmarkDefinitionDiffType getDiffType();


}//end TrustmarkDefinitionDiffResult