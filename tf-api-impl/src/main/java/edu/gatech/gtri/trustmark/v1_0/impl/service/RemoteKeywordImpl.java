package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteKeyword;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * Default implementation of a RemoteKeyword object.
 * <br/><br/>
 * @author brad
 * @date 3/27/17
 */
public class RemoteKeywordImpl extends RemoteObjectImpl implements RemoteKeyword, Comparable<RemoteKeyword> {
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public RemoteKeywordImpl(){}
    public RemoteKeywordImpl(JSONObject json) throws RemoteFormatException {
        this.setText(json.optString("name"));
        this.setCount(json.optInt("count"));
        this.setTrustmarkDefinitionCount(json.optInt("tdCount"));
        this.setTrustInteroperabilityProfileCount(json.optInt("tipCount"));
        try {
            this.setFormats(buildFormats(json.optJSONObject("_links")));
        }catch(MalformedURLException me){
            throw new RemoteFormatException("Invalid keyword link URL!", me);
        }
        this.setIgnore(json.optBoolean("ignore"));
    }
    //==================================================================================================================
    //  INSTANCE VARIABLES
    //==================================================================================================================
    private String text;
    private Integer trustmarkDefinitionCount;
    private Integer trustInteroperabilityProfileCount;
    private Integer count;
    private Boolean ignore;
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    @Override
    public String getText() {
        return text;
    }
    @Override
    public Integer getTrustmarkDefinitionCount() {
        return trustmarkDefinitionCount;
    }
    @Override
    public Integer getTrustInteroperabilityProfileCount() {
        return trustInteroperabilityProfileCount;
    }
    @Override
    public Integer getCount() {
        return count;
    }
    @Override
    public Boolean getIgnore() {
        return ignore;
    }
    //==================================================================================================================
    //  SETTERS
    //==================================================================================================================
    public void setText(String text) {
        this.text = text;
    }
    public void setTrustmarkDefinitionCount(Integer trustmarkDefinitionCount) {
        this.trustmarkDefinitionCount = trustmarkDefinitionCount;
    }
    public void setTrustInteroperabilityProfileCount(Integer trustInteroperabilityProfileCount) {
        this.trustInteroperabilityProfileCount = trustInteroperabilityProfileCount;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }
    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================

    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================
    @Override
    public int compareTo(RemoteKeyword o) {
        return this.getText().compareToIgnoreCase(o.getText());
    }


}/* end RemoteKeywordImpl */