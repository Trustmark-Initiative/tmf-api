package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.v1_0.model.HasIdentifier;
import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import org.gtri.fj.data.TreeMap;

import java.net.URI;

/**
 * Represents a list of trustmarks an organization in a trustmark binding
 * registry possesses.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkBindingRegistryOrganizationTrustmarkMap extends HasSource, HasIdentifier {

    /**
     * Returns the trustmarks the organization possesses.
     *
     * @return the trustmarks the organization possesses
     */
    TreeMap<URI, TrustmarkBindingRegistryOrganizationTrustmark> getTrustmarkMap();
}
