package edu.gatech.gtri.trustmark.v1_0.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Provides contextual information about a bulk read operation.
 * @author Nicholas Saney
 * @date 2016-09-12
 */
public interface BulkReadContext {
    
    /**
     * Gets an Entity that describes the defining organization that will be applied for an entire bulk read operation.
     * @return a valid Trustmark Defining Organization
     */
    Entity getTrustmarkDefiningOrganization();
    
    /**
     * Gets an Entity that describes the issuing organization that will be applied for an entire bulk read operation.
     * @return a valid Trust Interoperability Profile Issuer
     */
    Entity getTrustInteroperabilityProfileIssuer();
    
    /**
     * Gets a list of Entity objects that describe the providers that will be applied for an entire bulk read operation.
     * @return a list of valid Trustmark Providers
     */
    List<Entity> getTrustmarkProviderReferences();
    
    /**
     * Creates a valid TD identifier from the given information.
     * @param moniker the moniker string to include in the identifier
     * @param version the version string to include in the identifier
     * @return a valid TD identifier
     */
    URI generateIdentifierForTrustmarkDefinition(String moniker, String version) throws URISyntaxException;
    
    /**
     * Creates a valid TD identifier from the given information.
     * @param moniker the moniker string to include in the identifier
     * @param version the version string to include in the identifier
     * @return a valid TD identifier
     */
    URI generateIdentifierForTrustInteroperabilityProfile(String moniker, String version) throws URISyntaxException;
    
    /**
     * Resolves the given external TD reference to a URI if possible.
     * @param tdReference the text of the TD reference to resolve
     * @return the URI of the given external TD reference, or null if unavailable.
     */
    TrustmarkFrameworkIdentifiedObject resolveReferencedExternalTrustmarkDefinition(String tdReference);
    
    /**
     * Resolves the given external TD reference to a URI if possible.
     * @param tipReference the text of the TIP reference to resolve
     * @return the URI of the given external TIP reference, or null if unavailable.
     */
    TrustmarkFrameworkIdentifiedObject resolveReferencedExternalTrustInteroperabilityProfile(String tipReference);
    
    /**
     * Gets the default version that will be used during an entire bulk read operation.
     * @return a valid version String
     */
    String getDefaultVersion();
    
    /**
     * Gets the default legal notice that will be used during an entire bulk read operation.
     * @return a valid legal notice String
     */
    String getDefaultLegalNotice();
    
    /**
     * Gets the default notes that will be used during an entire bulk read operation.
     * @return a String with notes
     */
    String getDefaultNotes();
    
    /**
     * Gets the default issuance criteria that will be used during an entire bulk read operation.
     * @return a valid issuance criteria String.
     */
    String getDefaultIssuanceCriteria();
    
    /**
     * Gets the default revocation criteria that will be used during an entire bulk read operation.
     * @return a valid revocation criteria String
     */
    String getDefaultRevocationCriteria();
}
