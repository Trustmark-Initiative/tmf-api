package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.v1_0.model.HasIdentifier;
import org.gtri.fj.data.TreeMap;

/**
 * Represents a list of organizations in a trustmark binding registry.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkBindingRegistryOrganizationMap extends HasIdentifier {

    /**
     * Returns the organizations in a trustmark binding registry.
     *
     * @return the organizations in a trustmark binding registry
     */
    TreeMap<String, TrustmarkBindingRegistryOrganization> getOrganizationMap();
}
