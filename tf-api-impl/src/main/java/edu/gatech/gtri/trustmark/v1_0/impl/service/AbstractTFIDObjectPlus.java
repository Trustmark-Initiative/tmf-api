package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brad on 4/15/17.
 */
public abstract class AbstractTFIDObjectPlus extends AbstractTFIDObject {

    protected AbstractTFIDObjectPlus(){}
    protected AbstractTFIDObjectPlus(String type, JSONObject json) throws RemoteException {
        super(type, json);
        try {
            this.setDeprecated(json.optBoolean(DEPRECATED_KEY, Boolean.FALSE));
            if( json.optString(PUBLICATION_DATETIME_KEY) != null && json.optString(PUBLICATION_DATETIME_KEY).trim().length() > 0 )
                this.setPublicationDateTime(this.xmlStringToDate(json.optString(PUBLICATION_DATETIME_KEY)));
            if (json.optJSONArray(KEYWORDS_KEY) != null) {
                JSONArray keywords = json.optJSONArray(KEYWORDS_KEY);
                for (int i = 0; i < keywords.length(); i++) {
                    this.addKeyword(keywords.optString(i));
                }
            }


            if( json.optString("PublisherName") != null )
                this.setPublisherName(json.optString("PublisherName"));

            if( json.optString("PublisherIdentifier") != null )
                this.setPublisherIdentifier(json.optString("PublisherIdentifier"));


        }catch(JSONException jsone){
            throw new RemoteException("Bad "+type+" JSON encountered", jsone);
        }
    }


    private Boolean deprecated = Boolean.FALSE;
    private Date publicationDateTime;
    private List<String> keywords = new ArrayList<>();
    private String publisherName;
    private String publisherIdentifier;

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherIdentifier() {
        return publisherIdentifier;
    }

    public void setPublisherIdentifier(String publisherIdentifier) {
        this.publisherIdentifier = publisherIdentifier;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public Date getPublicationDateTime() {
        return publicationDateTime;
    }

    public void setPublicationDateTime(Date publicationDateTime) {
        this.publicationDateTime = publicationDateTime;
    }

    public List<String> getKeywords() {
        if( keywords == null )
            keywords = new ArrayList<>();
        return keywords;
    }
    public void addKeyword(String kw){
        this.getKeywords().add(kw);
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }




}
