package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteOrganization;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteStatus;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteVersionSet;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readEntity;

/**
 * Created by brad on 2/4/16.
 */
public class RemoteStatusImpl extends RemoteObjectImpl implements RemoteStatus {
    private static final Logger log = LoggerFactory.getLogger(RemoteStatusImpl.class);

    private String getLocalAPIVersion() {
        return FactoryLoader.getInstance(TrustmarkFramework.class).getTrustmarkFrameworkVersion();
    }

    public RemoteStatusImpl() {
    }

    public RemoteStatusImpl(JSONObject statusJson) throws RemoteException {
        if (statusJson.optString(ATTRIBUTE_KEY_JSON_TMF_VERSION) != null)
            this.setTrustmarkFrameworkVersion(statusJson.optString(ATTRIBUTE_KEY_JSON_TMF_VERSION).trim());

        log.debug("Checking remote framework version...");
        Double localApiVersion = Double.parseDouble(getLocalAPIVersion());
        Double remoteApiVersion = Double.parseDouble(this.getTrustmarkFrameworkVersion());
        if (localApiVersion < remoteApiVersion) {
//        if( !this.getTrustmarkFrameworkVersion().equalsIgnoreCase(getLocalAPIVersion()) ){
            log.error("The remote server is supporting a different version of the TrustmarkFramework API[" + this.getTrustmarkFrameworkVersion() + "].  This tool will only work with version [" + this.getLocalAPIVersion() + "]!");
            throw new RemoteException("The remote server is supporting a different version of the TrustmarkFramework API[" + this.getTrustmarkFrameworkVersion() + "].  This tool will only work with version [" + this.getLocalAPIVersion() + "]!");
        }

        try {
            log.debug("Parsing remote timestamp....");
            this.setTimestamp(xmlStringToDate(statusJson.getString("timestamp")));

            log.debug("Parsing out RemoteOrg info...");
            RemoteOrganizationImpl ro = new RemoteOrganizationImpl();
            if (statusJson.optJSONObject("organization") == null) {
                log.error("Error cannot parse remote status - JSON does not contain any object named '@|yellow organization|@'");
                throw new RemoteFormatException("Cannot build status when JSON object 'organization' is not present in response.");
            } else {
                JSONObject orgJson = statusJson.getJSONObject("organization");
                Entity e = null;
                try {
                    e = readEntity(orgJson);
                } catch (ParseException pe) {
                    log.error("The remote JSON 'organization' in the status response did not successfully parse as an Entity.", pe);
                    throw new RemoteFormatException("Expecting JSON for 'organization' to be an entity, but failed to parse it.", pe);
                }
                ro.setContacts(e.getContacts());
                ro.setName(e.getName());
                ro.setIdentifier(e.getIdentifier());
                ro.setAbbreviation(orgJson.optString("Abbreviation", ""));
                ro.setLogoImagePath(orgJson.optString("LogoPath", ""));

            }
            this.setOrganization(ro);

            log.debug("Parsing out the base URLs...");
            if (statusJson.has("baseUrl")) {
                try {
                    this.addBaseURL(new URL(statusJson.optString("baseUrl")));
                } catch (MalformedURLException malUrle) {
                    log.error("Error parsing base URL: " + statusJson.optString("baseUrl"), malUrle);
                    throw new RemoteFormatException("Error parsing base URL: " + statusJson.optString("baseUrl"), malUrle);
                }
            } else if (statusJson.has("baseUrls")) {
                JSONArray baseUrls = statusJson.optJSONArray("baseUrls");
                for (int i = 0; i < baseUrls.length(); i++) {
                    String nextUrl = baseUrls.optString(i);
                    try {
                        this.addBaseURL(new URL(nextUrl));
                    } catch (MalformedURLException malUrle) {
                        log.error("Error parsing base URL: " + nextUrl, malUrle);
                        throw new RemoteFormatException("Error parsing base URL: " + nextUrl, malUrle);
                    }
                }
            } else {
                log.error("The server's response did not include 'baseUrl' or 'baseUrls', so this client couldn't detect which URL base the server represents.");
                throw new RemoteFormatException("Unable to find the baseUrl or baseUrls supported by the remote server.");
            }

            log.debug("Parsing out version set information...");
            String currentVsName = statusJson.optString("versionSet");
            if (statusJson.optJSONArray("VersionSets") != null) {
                JSONArray vsArray = statusJson.optJSONArray("VersionSets");
                for (int i = 0; i < vsArray.length(); i++) {
                    JSONObject versionSetJson = vsArray.optJSONObject(i);
                    RemoteVersionSetImpl rvs = new RemoteVersionSetImpl(versionSetJson);
                    this.addVersionSet(rvs);
                    if (rvs.getName().equalsIgnoreCase(currentVsName))
                        this.setCurrentVersionSet(rvs);
                }
            }


            log.debug("Parsing trustmark definition information...");
            this.setSupportsTrustmarkDefinitions(statusJson.getJSONObject("TrustmarkDefinitions").getBoolean("supported"));
            if (this.getSupportsTrustmarkDefinitions()) {
                this.setTrustmarkDefinitionCountAll(statusJson.getJSONObject("TrustmarkDefinitions").optInt("countAll"));
                this.setTrustmarkDefinitionCountNotDeprecated(statusJson.getJSONObject("TrustmarkDefinitions").optInt("countNotDeprecated"));
                String mostRecentDate = statusJson.getJSONObject("TrustmarkDefinitions").optString("mostRecentDate");
                if (mostRecentDate != null && mostRecentDate.trim().length() > 0) {
                    log.debug("Parsing TD most recent date: " + mostRecentDate);
                    this.setMostRecentTrustmarkDefinitionDate(xmlStringToDate(mostRecentDate));
                } else {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(0l);
                    this.setMostRecentTrustmarkDefinitionDate(c.getTime());
                }
                try {
                    this.setTrustmarkDefinitionLocations(buildFormats(statusJson.optJSONObject("TrustmarkDefinitions").optJSONObject("_links")));
                } catch (MalformedURLException male) {
                    log.error("Unable to parse the TrustmarkDefinition URLs", male);
                    throw new RemoteFormatException("Cannot parse the remote TrustmarkDefinition URLs!", male);
                }
                try {
                    if (statusJson.optJSONObject("TrustmarkDefinitions").optJSONObject("ByName") != null) {
                        this.setTrustmarkDefinitionByNameLocations(buildFormats(statusJson.optJSONObject("TrustmarkDefinitions").optJSONObject("ByName").optJSONObject("_links")));
                    }
                } catch (MalformedURLException male) {
                    log.error("Unable to parse the TrustmarkDefinition By Name URLs", male);
                }
            }

            log.debug("Parsing trust interoperability profile information...");
            this.setSupportsTrustInteroperabilityProfiles(statusJson.getJSONObject("TrustInteroperabilityProfiles").getBoolean("supported"));
            if (this.getSupportsTrustInteroperabilityProfiles()) {
                this.setTrustInteroperabilityProfileCountAll(statusJson.getJSONObject("TrustInteroperabilityProfiles").optInt("countAll"));
                String mostRecentDate = statusJson.getJSONObject("TrustInteroperabilityProfiles").optString("mostRecentDate");
                if (mostRecentDate != null && mostRecentDate.trim().length() > 0) {
                    log.debug("Parsing TIP most recent date: " + mostRecentDate);
                    this.setMostRecentTrustInteroperabilityProfileDate(xmlStringToDate(mostRecentDate));
                } else {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(0l);
                    this.setMostRecentTrustInteroperabilityProfileDate(c.getTime());
                }
                try {
                    this.setTrustInteroperabilityProfileLocations(buildFormats(statusJson.optJSONObject("TrustInteroperabilityProfiles").optJSONObject("_links")));
                } catch (MalformedURLException male) {
                    log.error("Unable to parse the TrustInteroperabilityProfile URLs", male);
                    throw new RemoteFormatException("Cannot parse the remote TrustInteroperabilityProfile URLs!", male);
                }
                try {
                    if (statusJson.optJSONObject("TrustInteroperabilityProfiles").optJSONObject("ByName") != null) {
                        this.setTrustInteroperabilityProfileByNameLocations(buildFormats(statusJson.optJSONObject("TrustInteroperabilityProfiles").optJSONObject("ByName").optJSONObject("_links")));
                    }
                } catch (MalformedURLException male) {
                    log.error("Unable to parse the TrustInteroperabilityProfile By Name URLs", male);
                }
            }

            log.debug("Parsing trustmark information...");
            this.setSupportsTrustmarks(statusJson.getJSONObject("Trustmarks").getBoolean("supported"));
            if (this.getSupportsTrustmarks()) {
                // TODO What to parse here?
            }

            log.debug("Parsing keywords...");
            if (statusJson.optJSONObject("Keywords") != null) {
                this.setKeywordCount(statusJson.optJSONObject("Keywords").optInt("countAll"));
                if (statusJson.optJSONObject("Keywords").optJSONObject("_links") != null) {
                    try {
                        this.setKeywordLocations(buildFormats(statusJson.optJSONObject("Keywords").optJSONObject("_links")));
                    } catch (MalformedURLException male) {
                        log.error("Unable to parse the Keyword URLs", male);
                        throw new RemoteFormatException("Cannot parse the remote Keyword URLs!", male);
                    }
                }
            } else {
                this.setKeywordCount(0);
            }

        } catch (JSONException jsone) {
            throw new RemoteException("Invalid JSON response from server!", jsone);
        }
    }


    private Date timestamp;
    private List<URL> baseURLs;
    private RemoteOrganization organization;
    private RemoteVersionSet currentVersionSet;
    private List<RemoteVersionSet> versionSetList = new ArrayList<>();
    private String trustmarkFrameworkVersion = "<UNKNOWN>";
    private Boolean supportsTrustmarkDefinitions;
    private Integer trustmarkDefinitionCountAll;
    private Integer trustmarkDefinitionCountNotDeprecated;
    private Date mostRecentTrustmarkDefinitionDate;
    private Map<String, URL> trustmarkDefinitionLocations;
    private Map<String, URL> trustmarkDefinitionByNameLocations;

    private Integer keywordCount;
    private Map<String, URL> keywordLocations;
    //==================================================================================================================
    //  Trust Interoperability Profile Status Methods
    //==================================================================================================================
    private Boolean supportsTrustInteroperabilityProfiles;
    private Integer trustInteroperabilityProfileCountAll;
    private Date mostRecentTrustInteroperabilityProfileDate;
    private Map<String, URL> trustInteroperabilityProfileLocations;
    private Map<String, URL> trustInteroperabilityProfileByNameLocations;
    //==================================================================================================================
    //  Trustmark Status Methods
    //==================================================================================================================
    private Boolean supportsTrustmarks;

    //==================================================================================================================
    //  GETTERS/SETTERS
    //==================================================================================================================
    @Override
    public List<URL> getBaseURLs() {
        if (baseURLs == null)
            baseURLs = new ArrayList<>();
        return baseURLs;
    }

    public void setBaseURIs(List<URL> baseURLs) {
        this.baseURLs = baseURLs;
    }

    public void addBaseURL(URL url) {
        this.getBaseURLs().add(url);
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getTrustmarkFrameworkVersion() {
        return trustmarkFrameworkVersion;
    }

    public void setTrustmarkFrameworkVersion(String trustmarkFrameworkVersion) {
        this.trustmarkFrameworkVersion = trustmarkFrameworkVersion;
    }

    @Override
    public Boolean getSupportsTrustmarkDefinitions() {
        return supportsTrustmarkDefinitions;
    }

    public void setSupportsTrustmarkDefinitions(Boolean supportsTrustmarkDefinitions) {
        this.supportsTrustmarkDefinitions = supportsTrustmarkDefinitions;
    }

    @Override
    public Integer getTrustmarkDefinitionCountAll() {
        return trustmarkDefinitionCountAll;
    }

    public void setTrustmarkDefinitionCountAll(Integer trustmarkDefinitionCountAll) {
        this.trustmarkDefinitionCountAll = trustmarkDefinitionCountAll;
    }

    @Override
    public Integer getTrustmarkDefinitionCountNotDeprecated() {
        return trustmarkDefinitionCountNotDeprecated;
    }

    public void setTrustmarkDefinitionCountNotDeprecated(Integer trustmarkDefinitionCountNotDeprecated) {
        this.trustmarkDefinitionCountNotDeprecated = trustmarkDefinitionCountNotDeprecated;
    }

    @Override
    public Date getMostRecentTrustmarkDefinitionDate() {
        return mostRecentTrustmarkDefinitionDate;
    }

    public void setMostRecentTrustmarkDefinitionDate(Date mostRecentTrustmarkDefinitionDate) {
        this.mostRecentTrustmarkDefinitionDate = mostRecentTrustmarkDefinitionDate;
    }


    @Override
    public Integer getKeywordCount() {
        return keywordCount;
    }

    public void setKeywordCount(Integer keywordCountAll) {
        this.keywordCount = keywordCountAll;
    }


    @Override
    public Boolean getSupportsTrustInteroperabilityProfiles() {
        return supportsTrustInteroperabilityProfiles;
    }

    public void setSupportsTrustInteroperabilityProfiles(Boolean supportsTrustInteroperabilityProfiles) {
        this.supportsTrustInteroperabilityProfiles = supportsTrustInteroperabilityProfiles;
    }

    @Override
    public Integer getTrustInteroperabilityProfileCountAll() {
        return trustInteroperabilityProfileCountAll;
    }

    public void setTrustInteroperabilityProfileCountAll(Integer trustInteroperabilityProfileCountAll) {
        this.trustInteroperabilityProfileCountAll = trustInteroperabilityProfileCountAll;
    }

    @Override
    public Date getMostRecentTrustInteroperabilityProfileDate() {
        return mostRecentTrustInteroperabilityProfileDate;
    }

    public void setMostRecentTrustInteroperabilityProfileDate(Date mostRecentTrustInteroperabilityProfileDate) {
        this.mostRecentTrustInteroperabilityProfileDate = mostRecentTrustInteroperabilityProfileDate;
    }

    @Override
    public Boolean getSupportsTrustmarks() {
        return supportsTrustmarks;
    }

    public void setSupportsTrustmarks(Boolean supportsTrustmarks) {
        this.supportsTrustmarks = supportsTrustmarks;
    }


    @Override
    public RemoteOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(RemoteOrganization organization) {
        this.organization = organization;
    }

    @Override
    public RemoteVersionSet getCurrentVersionSet() {
        return currentVersionSet;
    }

    public void setCurrentVersionSet(RemoteVersionSet currentVersionSet) {
        this.currentVersionSet = currentVersionSet;
    }

    public List<RemoteVersionSet> getVersionSetList() {
        if (versionSetList == null)
            versionSetList = new ArrayList<>();
        return versionSetList;
    }

    @Override
    public List<RemoteVersionSet> listVersionSets() {
        return getVersionSetList();
    }

    public void setVersionSetList(List<RemoteVersionSet> versionSetList) {
        this.versionSetList = versionSetList;
    }

    public void addVersionSet(RemoteVersionSet remoteVersionSet) {
        this.getVersionSetList().add(remoteVersionSet);
    }


    @Override
    public Map<String, URL> getTrustmarkDefinitionLocations() {
        if (trustmarkDefinitionLocations == null)
            trustmarkDefinitionLocations = new HashMap<>();
        return trustmarkDefinitionLocations;
    }

    public void setTrustmarkDefinitionLocations(Map<String, URL> trustmarkDefinitionLocations) {
        this.trustmarkDefinitionLocations = trustmarkDefinitionLocations;
    }

    @Override
    public Map<String, URL> getKeywordLocations() {
        if (keywordLocations == null)
            keywordLocations = new HashMap<>();
        return keywordLocations;
    }

    public void setKeywordLocations(Map<String, URL> keywordLocations) {
        this.keywordLocations = keywordLocations;
    }

    @Override
    public Map<String, URL> getTrustInteroperabilityProfileLocations() {
        if (trustInteroperabilityProfileLocations == null)
            trustInteroperabilityProfileLocations = new HashMap<>();
        return trustInteroperabilityProfileLocations;
    }

    public void setTrustInteroperabilityProfileLocations(Map<String, URL> trustInteroperabilityProfileLocations) {
        this.trustInteroperabilityProfileLocations = trustInteroperabilityProfileLocations;
    }


    @Override
    public Map<String, URL> getTrustmarkDefinitionByNameLocations() {
        if (trustmarkDefinitionByNameLocations == null)
            trustmarkDefinitionByNameLocations = new HashMap<>();
        return trustmarkDefinitionByNameLocations;
    }

    public void setTrustmarkDefinitionByNameLocations(Map<String, URL> trustmarkDefinitionByNameLocations) {
        this.trustmarkDefinitionByNameLocations = trustmarkDefinitionByNameLocations;
    }

    @Override
    public Map<String, URL> getTrustInteroperabilityProfileByNameLocations() {
        if (trustInteroperabilityProfileByNameLocations == null)
            trustInteroperabilityProfileByNameLocations = new HashMap<>();
        return trustInteroperabilityProfileByNameLocations;
    }

    public void setTrustInteroperabilityProfileByNameLocations(Map<String, URL> trustInteroperabilityProfileByNameLocations) {
        this.trustInteroperabilityProfileByNameLocations = trustInteroperabilityProfileByNameLocations;
    }
}
