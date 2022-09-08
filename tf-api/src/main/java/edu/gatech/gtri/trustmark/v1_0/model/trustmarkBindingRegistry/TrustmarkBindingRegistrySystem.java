package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import org.gtri.fj.data.List;

import java.net.URI;

/**
 * Represents a system in a trustmark binding registry.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkBindingRegistrySystem {

    /**
     * Returns the entity id.
     *
     * @return the entity id
     */
    String getIdentifier();

    /**
     * Returns the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Returns the type.
     *
     * @return the type
     */
    TrustmarkBindingRegistrySystemType getSystemType();

    /**
     * Returns the metadata.
     *
     * @return the metadata
     */
    URI getMetadata();

    /**
     * Returns the identifiers of the entities to whom the system has issued
     * trustmarks.
     *
     * @return the identifiers of the entities to whom the system has issued
     * trustmarks
     */
    List<URI> getTrustmarkRecipientIdentifiers();

    /**
     * Returns the identifiers of the trustmarks the system possesses.
     *
     * @return the identifiers of the trustmarks the system possesses
     */
    List<URI> getTrustmarks();
}
