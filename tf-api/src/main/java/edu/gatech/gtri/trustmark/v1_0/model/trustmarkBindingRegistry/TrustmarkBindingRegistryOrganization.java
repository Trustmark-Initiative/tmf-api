package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;

import java.net.URI;

/**
 * Represents an organization in a trustmark binding registry.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkBindingRegistryOrganization extends HasSource {

    /**
     * Returns the identifier; for example, "https://trustmarkinitiative.org/".
     *
     * @return the identifier
     */
    URI getIdentifier();

    /**
     * Returns the name; for example, "Trustmark Initiative".
     *
     * @return the name
     */
    String getName();

    /**
     * Returns the display name; for example, "ti".
     *
     * @return the display name
     */
    String getDisplayName();

    /**
     * Returns a description.
     *
     * @return a description
     */
    String getDescription();

    /**
     * Returns the identifiers of the entities to whom the organization has
     * issued trustmarks.
     *
     * @return the identifiers of the entities to whom the organization has
     * issued trustmarks
     */
    List<URI> getTrustmarkRecipientIdentifiers();

    /**
     * Returns the URI for the list of trustmarks the organization possesses.
     *
     * @return the URI for the list of trustmarks the organization possesses
     */
    URI getOrganizationTrustmarkMapURI();

    /**
     * Returns the trustmarks the organization possesses.
     *
     * @return the trustmarks the organization possesses
     */
    TreeMap<URI, TrustmarkBindingRegistryOrganizationTrustmark> getOrganizationTrustmarkMap();
}
