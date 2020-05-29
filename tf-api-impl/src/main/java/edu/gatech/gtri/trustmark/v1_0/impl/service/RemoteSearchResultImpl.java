package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteSearchResult;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustmarkDefinition;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 4/15/17.
 */
public class RemoteSearchResultImpl extends RemoteObjectImpl implements RemoteSearchResult {
    //==================================================================================================================
    //  Constructors
    //==================================================================================================================
    public RemoteSearchResultImpl(){}
    public RemoteSearchResultImpl(JSONObject json) throws RemoteException {
        this.setJson(json);
    }
    //==================================================================================================================
    //  Instance Variables
    //==================================================================================================================
    private String queryString;
    private List<String> searchTerms = new ArrayList<>();
    private String warningText;

    private Integer trustmarkDefinitionTotalCount;
    private Integer trustmarkDefinitionMatchCount;
    private List<RemoteTrustmarkDefinition> trustmarkDefinitions = new ArrayList<>();

    private Integer trustInteroperabilityProfileTotalCount;
    private Integer trustInteroperabilityProfileMatchCount;
    private List<RemoteTrustInteroperabilityProfile> trustInteroperabilityProfiles = new ArrayList<>();

    //==================================================================================================================
    //  Getters
    //==================================================================================================================
    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public List<String> getSearchTerms() {
        if( searchTerms == null )
            searchTerms = new ArrayList<>();
        return searchTerms;
    }

    @Override
    public String getWarningText() {
        return warningText;
    }

    @Override
    public Integer getTrustmarkDefinitionTotalCount() {
        return trustmarkDefinitionTotalCount;
    }

    @Override
    public Integer getTrustmarkDefinitionMatchCount() {
        return trustmarkDefinitionMatchCount;
    }

    @Override
    public List<RemoteTrustmarkDefinition> getTrustmarkDefinitions() {
        if( trustmarkDefinitions == null )
            trustmarkDefinitions = new ArrayList<>();
        return trustmarkDefinitions;
    }

    @Override
    public Integer getTrustInteroperabilityProfileTotalCount() {
        return trustInteroperabilityProfileTotalCount;
    }

    @Override
    public Integer getTrustInteroperabilityProfileMatchCount() {
        return trustInteroperabilityProfileMatchCount;
    }

    @Override
    public List<RemoteTrustInteroperabilityProfile> getTrustInteroperabilityProfiles() {
        if( trustInteroperabilityProfiles == null )
            trustInteroperabilityProfiles = new ArrayList<>();
        return trustInteroperabilityProfiles;
    }

    //==================================================================================================================
    //  Setters
    //==================================================================================================================
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setSearchTerms(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }

    public void setWarningText(String warningText) {
        this.warningText = warningText;
    }

    public void setTrustmarkDefinitionTotalCount(Integer trustmarkDefinitionTotalCount) {
        this.trustmarkDefinitionTotalCount = trustmarkDefinitionTotalCount;
    }

    public void setTrustmarkDefinitionMatchCount(Integer trustmarkDefinitionMatchCount) {
        this.trustmarkDefinitionMatchCount = trustmarkDefinitionMatchCount;
    }

    public void setTrustmarkDefinitions(List<RemoteTrustmarkDefinition> trustmarkDefinitions) {
        this.trustmarkDefinitions = trustmarkDefinitions;
    }

    public void setTrustInteroperabilityProfileTotalCount(Integer trustInteroperabilityProfileTotalCount) {
        this.trustInteroperabilityProfileTotalCount = trustInteroperabilityProfileTotalCount;
    }

    public void setTrustInteroperabilityProfileMatchCount(Integer trustInteroperabilityProfileMatchCount) {
        this.trustInteroperabilityProfileMatchCount = trustInteroperabilityProfileMatchCount;
    }

    public void setTrustInteroperabilityProfiles(List<RemoteTrustInteroperabilityProfile> trustInteroperabilityProfiles) {
        this.trustInteroperabilityProfiles = trustInteroperabilityProfiles;
    }
    //==================================================================================================================
    //  Adders
    //==================================================================================================================
    public void addSearchTerm(String term){
        this.getSearchTerms().add(term);
    }
    public void addTrustmarkDefinition(RemoteTrustmarkDefinition td){
        this.getTrustmarkDefinitions().add(td);
    }
    public void addTrustInteroperabilityProfile(RemoteTrustInteroperabilityProfile tip){
        this.getTrustInteroperabilityProfiles().add(tip);
    }
    //==================================================================================================================
    //  Public Methods
    //==================================================================================================================
    @Override
    public Boolean areTrustmarkDefinitionsSuppressed() {
        return this.getTrustmarkDefinitionMatchCount() > this.getTrustmarkDefinitions().size();
    }
    @Override
    public Boolean areTrustInteroperabilityProfilesSuppressed() {
        return this.getTrustInteroperabilityProfileMatchCount() > this.getTrustInteroperabilityProfiles().size();
    }

    public void setJson(JSONObject json) throws RemoteException {
        if( json == null )
            throw new RemoteException("Cannot parse null JSON");
        if( json.optJSONObject("results") == null )
            throw new RemoteException("Invalid JSON - missing 'results' key from search.");

        if( json.optJSONObject("_links") != null ){
            try {
                this.setFormats(this.buildFormats(json.optJSONObject("_links")));
            }catch(MalformedURLException male){
                throw new RemoteException("Cannot parse remote JSON _links!  Bad URL", male);
            }
        }

        this.setQueryString(json.optString("queryString"));
        this.setTrustmarkDefinitionTotalCount(json.optInt("tdCountTotal"));
        this.setTrustInteroperabilityProfileTotalCount(json.optInt("tipCountTotal"));

        JSONObject results = json.optJSONObject("results");
        this.setTrustmarkDefinitionMatchCount(results.optInt("tdCount"));
        this.setTrustInteroperabilityProfileMatchCount(results.optInt("tipCount"));

        JSONArray termsArray = json.optJSONArray("terms");
        if( termsArray != null && termsArray.length() > 0 ) {
            for( int i = 0; i < termsArray.length(); i++ ){
                String term = termsArray.optString(i);
                this.addSearchTerm(term);
            }
        }

        JSONArray tdArray = results.optJSONArray("tds");
        if( tdArray != null && tdArray.length() > 0 ) {
            for( int i = 0; i < tdArray.length(); i++ ){
                JSONObject tdJson = tdArray.optJSONObject(i);
                this.addTrustmarkDefinition(new RemoteTrustmarkDefinitionImpl(tdJson));
            }
        }

        JSONArray tipsArray = results.optJSONArray("tips");
        if( tipsArray != null && tipsArray.length() > 0 ) {
            for( int i = 0; i < tipsArray.length(); i++ ){
                JSONObject tipJson = tipsArray.optJSONObject(i);
                this.addTrustInteroperabilityProfile(new RemoteTrustInteroperabilityProfileImpl(tipJson));
            }
        }

    }
    //==================================================================================================================
    //  Private Helper Methods
    //==================================================================================================================



}
