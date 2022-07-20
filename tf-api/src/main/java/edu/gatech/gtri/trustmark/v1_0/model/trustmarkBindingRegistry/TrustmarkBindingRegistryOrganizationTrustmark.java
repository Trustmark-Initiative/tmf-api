package edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;

import java.net.URI;

/**
 * Represents a trustmark associated with an organization in a trustmark binding
 * registry.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkBindingRegistryOrganizationTrustmark {

    /**
     * Returns the comment.
     *
     * @return the comment
     */
    String getComment();

    /**
     * Returns the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Returns whether provisional.
     *
     * @return whether provisional
     */
    boolean isProvisional();

    /**
     * Returns the status.
     *
     * @return the status
     */
    TrustmarkStatusCode getStatus();

    /**
     * Returns the trustmark definition identifier.
     *
     * @return the trustmark definition identifier
     */
    URI getTrustmarkDefinitionIdentifier();

    /**
     * Returns the trustmark identifier.
     *
     * @return the trustmark identifier
     */
    URI getTrustmarkIdentifier();
}
