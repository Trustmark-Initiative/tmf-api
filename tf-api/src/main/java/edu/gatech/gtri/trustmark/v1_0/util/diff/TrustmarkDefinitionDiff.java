package edu.gatech.gtri.trustmark.v1_0.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.util.Collection;

/**
 * Calculates differences between {@link TrustmarkDefinition} objects.  This interface is used to calculate all
 * of the differences between two trustmark definitions, so you can implement your own extensions and use ServiceLoader
 * to load them into the system.
 * <br/><br/>
 * Created by brad on 4/15/16.
 */
public interface TrustmarkDefinitionDiff {

    /**
     * This routine is called to calculate the actual difference.
     */
    Collection<TrustmarkDefinitionDiffResult> doDiff(TrustmarkDefinition td1, TrustmarkDefinition td2);

}/* end TrustmarkDefinitionDiff */