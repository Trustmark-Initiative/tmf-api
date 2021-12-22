package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteVersionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by brad on 3/27/17.
 */
public class RemoteVersionSetImpl extends RemoteObjectImpl implements RemoteVersionSet {

    private static final Logger log = LoggerFactory.getLogger(RemoteVersionSetImpl.class);

    public RemoteVersionSetImpl(){}
    public RemoteVersionSetImpl(JSONObject json){
        this.setName(json.optString("name"));
        this.setSuccessorName(json.optString("successor", null));
        this.setPredecessorName(json.optString("predecessor", null));
        this.setProduction(json.optBoolean("production"));
        this.setEditable(json.optBoolean("editable"));
        String value = json.optString("releasedDate");
        this.setReleasedDate(getDateFromString(value));
        if( json.optJSONObject("downloadAll") != null ){
            JSONArray formats = json.optJSONObject("downloadAll").optJSONObject("_links").optJSONArray("_formats");
            if( formats.length() > 0 ){
                for( int i = 0; i < formats.length(); i++ ){
                    JSONObject format = formats.optJSONObject(i);
                    if( "json".equalsIgnoreCase(format.optString("format")) ){
                        try {
                            this.setDownloadAllUrl(new URL(format.optString("href")));
                        }catch(Throwable t){
                            log.error("Cannot parse download all URL: "+format.optString("href"), t);
                        }
                    }
                }
            }
        }
    }


    private String name;
    private Boolean production;
    private Boolean editable;
    private Date releasedDate;
    private String predecessorName;
    private String successorName;
    private URL downloadAllUrl;


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean isProduction() {
        return production;
    }

    public void setProduction(Boolean production) {
        this.production = production;
    }

    @Override
    public Boolean isEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    @Override
    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    @Override
    public String getPredecessorName() {
        return predecessorName;
    }

    public void setPredecessorName(String predecessorName) {
        this.predecessorName = predecessorName;
    }

    @Override
    public String getSuccessorName() {
        return successorName;
    }

    public void setSuccessorName(String successorName) {
        this.successorName = successorName;
    }

    @Override
    public URL getDownloadAllUrl() {
        return downloadAllUrl;
    }

    public void setDownloadAllUrl(URL downloadAllUrl) {
        this.downloadAllUrl = downloadAllUrl;
    }


}
