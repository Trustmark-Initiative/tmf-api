package edu.gatech.gtri.trustmark.v1_0.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.util.List;

/**
 * The result of a bulk read, which may contain TDs, TIPs, or both.
 * @author Nicholas Saney
 * @date 2016-09-07
 */
public interface BulkReadResult {

    /**
     * Gets the TDs that resulted from a bulk read.
     * @return a {@link java.util.List} of {@link edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition} 
     *         objects from the bulk read.
     */
    List<TrustmarkDefinition> getResultingTrustmarkDefinitions();

    /**
     * Gets the TIPs that resulted from a bulk read.
     * @return a {@link java.util.List} of {@link edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile} 
     *         objects from the bulk read.
     */
    List<TrustInteroperabilityProfile> getResultingTrustInteroperabilityProfiles();

    /**
     * list of the invalid parameters found in the parsed tips
     * @return
     */
    List<String>  getResultingInvalidParameters();
}
