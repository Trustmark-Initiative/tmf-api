package edu.gatech.gtri.trustmark.v1_0.service;

import java.util.Date;
import java.util.List;

/**
 * Represents the high level response from a server about all of it's taxonomy terms.
 * <br/><br/>
 * Created by brad on 5/14/17.
 */
public interface RemoteTaxonomy {

    /**
     * Total number of terms on the server.
     */
    public Integer getTotalTermCount();

    /**
     * The most recent date of ALL terms - useful for overall cache checks.
     */
    public Date getLastUpdated();

    /**
     * The count of top level terms (ie, the size of getTerms()).
     */
    public Integer getTopLevelTermCount();

    /**
     * A list of all top level terms.
     */
    public List<RemoteTaxonomyTerm> getTerms();

}
