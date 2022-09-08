package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.v1_0.model.HasIdentifier;
import org.gtri.fj.data.TreeMap;

/**
 * Represents a list of systems in a trustmark binding registry.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkBindingRegistrySystemMap extends HasIdentifier {

    /**
     * Returns the systems.
     *
     * @return the systems
     */
    TreeMap<String, TrustmarkBindingRegistrySystem> getSystemMap();
}
