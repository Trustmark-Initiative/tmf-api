package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTaxonomy;
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
public class RemoteTaxonomyImpl extends RemoteObjectImpl implements RemoteTaxonomy {

    public RemoteTaxonomyImpl(){}
    public RemoteTaxonomyImpl(JSONObject json) throws RemoteException {
        try {
            this.setLastUpdated(xmlStringToDate(json.getString("lastUpdated")));
            this.setTotalTermCount(json.getInt("totalTermCount"));
            this.setTopLevelTermCount(json.getInt("topLevelTermCount"));

            JSONArray termTreeArray = json.getJSONArray("termTrees");
            for( int i = 0; i < termTreeArray.length(); i++ ){
                JSONObject termTreeObj = termTreeArray.getJSONObject(i);
                RemoteTaxonomyTermImpl term = new RemoteTaxonomyTermImpl(termTreeObj);
                this.addTerm(term);
            }

        }catch(JSONException jsone){
            throw new RemoteException("Invalid JSON response from server!", jsone);
        }
    }

    private Integer totalTermCount;
    private Date lastUpdated;
    private Integer topLevelTermCount;
    private List<RemoteTaxonomyTerm> terms;


    @Override
    public Integer getTotalTermCount() {
        return totalTermCount;
    }

    public void setTotalTermCount(Integer totalTermCount) {
        this.totalTermCount = totalTermCount;
    }

    @Override
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getTopLevelTermCount() {
        return topLevelTermCount;
    }

    public void setTopLevelTermCount(Integer topLevelTermCount) {
        this.topLevelTermCount = topLevelTermCount;
    }

    @Override
    public List<RemoteTaxonomyTerm> getTerms() {
        if( terms == null )
            terms = new ArrayList<>();
        return terms;
    }

    public void setTerms(List<RemoteTaxonomyTerm> terms) {
        this.terms = terms;
    }


    public void addTerm(RemoteTaxonomyTerm term){
        this.getTerms().add(term);
    }

}
