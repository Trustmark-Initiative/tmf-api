package edu.gatech.gtri.trustmark.v1_0.service;

import java.util.Date;
import java.util.List;

/**
 * Created by brad on 5/14/17.
 */
public interface RemoteTaxonomyTerm {

    /**
     * Returns the actual term text given from the server, such as "Security"
     */
    public String getTerm();

    /**
     * Returns the last updated date from the server for this term, for doing cache checks.
     */
    public Date getLastUpdatedDate();

    /**
     * Returns the list of children for this term.
     */
    public List<RemoteTaxonomyTerm> getChildren();

}
