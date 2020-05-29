package edu.gatech.gtri.trustmark.v1_0.service;

import java.net.URL;
import java.util.List;

/**
 * Provides operations supported by compatible trustmark framework servers.
 */
public interface TrustmarkFrameworkService {

    /**
     * Returns the internet location for this service.
     */
    URL getBaseUrl();

    /**
     * Returns all information about the given organization that is known by this remote service (which may be null if nothing
     * is known).
     * <br/><br/>
     * @param orgIdOrName
     * @return
     */
    RemoteOrganization getOrganizationInformation(String orgIdOrName) throws RemoteException;

    /**
     * Returns all of the keywords of TDs on this server.
     */
    List<RemoteKeyword> getKeywords() throws RemoteException;

    /**
     * Lists the taxonomy known to this server.  May return null if the remote server has no taxonomy.
     */
    RemoteTaxonomy getTaxonomy() throws RemoteException;

    /**
     * A quick, high-level query about the status of this server.  Check here for object support or counts, as well as
     * last modified dates.
     */
    RemoteStatus getStatus() throws RemoteException;

    /**
     * A method which will alert the TFAM that all artifacts need to be downloaded.  Note that this is a long-running Asynchronous
     * method, and that your listener will be passed the list of artifacts AFTER they are downloaded.
     * <br/><br/>
     * @return the {@link Thread} which is managing the download
     */
    Thread downloadAll(DownloadAllListener ... listeners);

    /**
     * A method which will alert the TFAM that all artifacts need to be downloaded FOR THE GIVEN VERSION SET.  Note that
     * this is a long-running Asynchronous method, and that your listener will be passed the list of artifacts AFTER they
     * are downloaded.  This method MAY require authentication, depending on how the remote server is configured.
     * <br/><br/>
     * @return the {@link Thread} which is managing the download
     */
    Thread downloadAll(String verionSetName, DownloadAllListener ... listeners);

    /**
     * Lists all all TrustmarkDefinitions on this server, this includes all TDs, even invalid ones.
     */
    Page<RemoteTrustInteroperabilityProfile> listTrustInteroperabilityProfiles() throws RemoteException;
    /**
     * Returns the list of all TrustInteroperabilityProfiles in the given keyword.
     */
    Page<RemoteTrustInteroperabilityProfile> listTrustInteroperabilityProfilesByKeyword(String keyword) throws RemoteException;
    /**
     * Returns the list of all TrustInteroperabilityProfiles matching the given name regex and version regex.
     */
    Page<RemoteTrustInteroperabilityProfile> listTrustInteroperabilityProfilesByNameAndVersionRegex(String nameRegex, String versionRegex) throws RemoteException;
    /**
     * Returns the TrustInteroperabilityProfile with the given name.
     * The returned object is null if there is no match.
     */
    RemoteTrustInteroperabilityProfile getTrustInteroperabilityProfileByName(String name) throws RemoteException;

    RemoteTrustInteroperabilityProfile getTrustInteroperabilityProfileByUrl(String url) throws RemoteException;

    /**
     * Lists all TrustmarkDefinitions on this server, this includes all TDs, even deprecated ones.
     */
    Page<RemoteTrustmarkDefinition> listTrustmarkDefinitions() throws RemoteException;
    /**
     * Returns the list of all TrustmarkDefinitions in the given keyword.
     */
    Page<RemoteTrustmarkDefinition> listTrustmarkDefinitionsByKeyword(String keyword) throws RemoteException;
    /**
     * Returns the list of all TrustmarkDefinition matching the given name regex and version regex.
     */
    Page<RemoteTrustmarkDefinition> listTrustmarkDefinitionByNameAndVersionRegex(String nameRegex, String versionRegex) throws RemoteException;
    /**
     * Returns the TrustmarkDefinition with the given name.
     * The returned object is null if there is no match.
     */
    RemoteTrustmarkDefinition getTrustmarkDefinitionByName(String name) throws RemoteException;

    RemoteTrustmarkDefinition getTrustmarkDefinitionByUrl(String url) throws RemoteException;

    /**
     * Perform a search on the remote server, and return the results.
     */
    RemoteSearchResult search(String text) throws RemoteException;
    //==================================================================================================================
    //  Authenticated Methods
    //     These methods will only work if you are authenticated with the TFAM you are using.
    //==================================================================================================================
    /**
     * Alerts the remote TFAM (in the current session) to switch to the given Version Set.
     * SIDE EFFECT: Causes any transient cached objects to be lost (affects the scope of the TFAM web service client, not the DAO)
     */
    void setVersionSet(String name) throws RemoteException;


    // TODO Remote Publishing Methods?  Ie, a TMF API Client could create verison sets or push TDs/TIPs/Trustmarks to the server remotely using this API.
}/* end TrustmarkFrameworkService */
