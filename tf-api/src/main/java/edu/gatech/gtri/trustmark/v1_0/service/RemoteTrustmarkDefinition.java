package edu.gatech.gtri.trustmark.v1_0.service;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.util.Date;
import java.util.List;

/**
 * Returns information about a TrustmarkDefinition that is remote.  The goal here is to represent a plausible subset
 * of information about a TD that makes sense in a big list.
 * <br/><br/>
 * Created by brad on 2/4/16.
 */
public interface RemoteTrustmarkDefinition extends RemoteObject, TrustmarkFrameworkIdentifiedObject {

    public Boolean getDeprecated();
    public Date getPublicationDateTime();
    public List<String> getKeywords();

    public String getPublisherName();
    public String getPublisherIdentifier();

}
