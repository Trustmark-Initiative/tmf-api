package edu.gatech.gtri.trustmark.v1_0.service;

/**
 * Represents a keyword at a remote TFAM, including some basic metadata such as the number of TDs and TIPs using
 * this keyword.
 * <br/><br/>
 * @author brad
 * @date 3/27/17
 */
public interface RemoteKeyword extends RemoteObject {

    /**
     * The actual text of the keyword.  Note that keywords are case insensitive, so this is probably all lowercase all
     * the time.
     */
    public String getText();

    /**
     * The count of TDs which have this keyword.
     */
    public Integer getTrustmarkDefinitionCount();

    /**
     * The count of TIPs which have this keyword.
     */
    public Integer getTrustInteroperabilityProfileCount();

    /**
     * All trustmark artifacts count associated to this keyword (ie, TDs + TIPs).
     * @return
     */
    public Integer getCount();

    /**
     * If true, then it is an indication from the server that this keyword is not very useful, and can most likely be
     * safely ignored from user interfaces or local business use cases.
     */
    public Boolean getIgnore();

}/* end RemoteKeyword */