package edu.gatech.gtri.trustmark.v1_0.service;

import java.util.List;

/**
 * Returned by the {@link TrustmarkFrameworkService} when a search is performed.
 * <br/><br/>
 * Created by brad on 3/23/17.
 */
public interface RemoteSearchResult {

    /**
     * The original input query string.
     */
    public String getQueryString();

    /**
     * How the server broke it into search terms.
     */
    public List<String> getSearchTerms();

    /**
     * If the server needed to modify your query, or some other minor issue occurred, the reason will be in this field.
     */
    public String getWarningText();

    /**
     * The total number of Trustmark Definitions in the remote system.
     */
    public Integer getTrustmarkDefinitionTotalCount();
    /**
     * How many TDs matched the search query.  May differ from the count returned by {@link RemoteSearchResult#getTrustmarkDefinitions()}
     * because some could be suppressed.
     */
    public Integer getTrustmarkDefinitionMatchCount();
    /**
     * Those TDs returned by the server for this search.
     */
    public List<RemoteTrustmarkDefinition> getTrustmarkDefinitions();
    /**
     * True if the server has removed some matches (ie, if 25 matched you might only get 15 and this field will be true).
     */
    public Boolean areTrustmarkDefinitionsSuppressed();


    /**
     * The total number of TIPs in the remote system.
     */
    public Integer getTrustInteroperabilityProfileTotalCount();
    /**
     * The number of matching TIPs.  May differ from the count returned by {@link RemoteSearchResult#getTrustInteroperabilityProfiles()}
     * because some could be suppressed.
     */
    public Integer getTrustInteroperabilityProfileMatchCount();
    /**
     * Those TIPs returned by the server for this search.
     */
    public List<RemoteTrustInteroperabilityProfile> getTrustInteroperabilityProfiles();
    /**
     * Returns true if the remote server has suppressed search results (ie, if 25 matched you might only get 15 and this
     * field will be true in that case).
     */
    public Boolean areTrustInteroperabilityProfilesSuppressed();


}
