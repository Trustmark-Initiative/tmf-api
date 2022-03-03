package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.v1_0.model.HasIdentifier;
import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import org.gtri.fj.data.TreeMap;

import java.net.URI;

/**
 * Represents a list of organizations in a trustmark binding registry.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkBindingRegistryOrganizationMap extends HasSource, HasIdentifier {

    /**
     * Returns the organizations in a trustmark binding registry.
     *
     * @return the organizations in a trustmark binding registry
     */
    TreeMap<String, TrustmarkBindingRegistryOrganization> getOrganizationMap();
}
