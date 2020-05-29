package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTaxonomyTerm;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brad on 5/14/17.
 */
public class RemoteTaxonomyTermImpl extends RemoteObjectImpl implements RemoteTaxonomyTerm {

    public RemoteTaxonomyTermImpl(){}
    public RemoteTaxonomyTermImpl(JSONObject json) throws RemoteException {
        try {
            this.setLastUpdatedDate(xmlStringToDate(json.getString("lastUpdated")));
            this.setTerm(json.getString("term"));

            JSONArray childrenArray = json.optJSONArray("children");
            if( childrenArray != null && childrenArray.length() > 0 ){
                for( int i = 0; i < childrenArray.length(); i++ ){
                    JSONObject childJson = childrenArray.getJSONObject(i);
                    RemoteTaxonomyTermImpl child = new RemoteTaxonomyTermImpl(childJson);
                    this.addChild(child);
                }
            }
        }catch(JSONException jsone){
            throw new RemoteException("Invalid JSON response from server!", jsone);
        }
    }

    private String term;
    private Date lastUpdatedDate;
    private List<RemoteTaxonomyTerm> children;


    @Override
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public List<RemoteTaxonomyTerm> getChildren() {
        if( children == null )
            children = new ArrayList<>();
        return children;
    }

    public void setChildren(List<RemoteTaxonomyTerm> children) {
        this.children = children;
    }


    public void addChild(RemoteTaxonomyTerm term){
        this.getChildren().add(term);
    }
}
