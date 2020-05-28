package edu.gatech.gtri.trustmark.v1_0.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

import java.util.Collection;

/**
 * Calculates differences between {@link TrustInteroperabilityProfile} objects.  This interface is used to calculate all
 * of the differences between two TrustInteroperabilityProfiles, so you can implement your own extensions and use ServiceLoader
 * to load them into the system.
 * <br/><br/>
 * Created by brad on 12/6/16.
 */
public interface TrustInteroperabilityProfileDiff {

    /**
     * This routine is called to calculate the actual difference.
     */
    Collection<TrustInteroperabilityProfileDiffResult> doDiff(TrustInteroperabilityProfile tip1, TrustInteroperabilityProfile tip2);

}/* end TrustmarkDefinitionDiff */