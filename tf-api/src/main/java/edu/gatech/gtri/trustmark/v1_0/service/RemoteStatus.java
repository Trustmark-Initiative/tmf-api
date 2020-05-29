package edu.gatech.gtri.trustmark.v1_0.service;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by brad on 2/4/16.
 */
public interface RemoteStatus {

    /**
     * Returns the date/time this status was generated.  Which also means when it was last cached (may not match
     * the date it is downloaded).
     */
    public Date getTimestamp();

    /**
     * Returns a listing of all base URLs which are supported by this TFAM.
     */
    public List<URL> getBaseURLs();

    /**
     * Indicates the TrustmarkFramework API version that this system is using.  You should NOT try to mix and match
     * versions.  The client will refuse to parse if this version doesn't match the API version in the system.
     */
    public String getTrustmarkFrameworkVersion();

    /**
     * Returns the remote organization's information.
     */
    public RemoteOrganization getOrganization();

    /**
     * Returns the currently selected version set of the remote TFAM.
     */
    public RemoteVersionSet getCurrentVersionSet();

    /**
     * If authenticated, this method will return all the version sets in the remote TFAM.
     */
    public List<RemoteVersionSet> listVersionSets(); // this will only populate more than 1 if you are authenticated with the TFAM

    /**
     * Returns how many keywords are in the system.
     */
    public Integer getKeywordCount();
    public Map<String, URL> getKeywordLocations();
    //==================================================================================================================
    //  Trustmark Definition Status Methods
    //==================================================================================================================
    public Boolean getSupportsTrustmarkDefinitions();
    public Integer getTrustmarkDefinitionCountAll();
    public Integer getTrustmarkDefinitionCountNotDeprecated();
    public Date getMostRecentTrustmarkDefinitionDate();
    public Map<String, URL> getTrustmarkDefinitionLocations();
    public Map<String, URL> getTrustmarkDefinitionByNameLocations();
    //==================================================================================================================
    //  Trust Interoperability Profile Status Methods
    //==================================================================================================================
    public Boolean getSupportsTrustInteroperabilityProfiles();
    public Integer getTrustInteroperabilityProfileCountAll();
    public Date getMostRecentTrustInteroperabilityProfileDate();
    public Map<String, URL> getTrustInteroperabilityProfileLocations();
    public Map<String, URL> getTrustInteroperabilityProfileByNameLocations();
    //==================================================================================================================
    //  Trustmark Status Methods
    //==================================================================================================================
    public Boolean getSupportsTrustmarks();

    // TODO What else here?


}
