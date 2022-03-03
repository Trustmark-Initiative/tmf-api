package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.v1_0.model.HasIdentifier;
import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import org.gtri.fj.data.TreeMap;

import java.net.URI;

/**
 * Represents a list of systems in a trustmark binding registry.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkBindingRegistrySystemMap extends HasSource, HasIdentifier {

    /**
     * Returns the systems.
     *
     * @return the systems
     */
    TreeMap<String, TrustmarkBindingRegistrySystem> getSystemMap();
}
