package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.dao.SearchResult;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.util.List;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 9/15/16
 */
public class SinglePageSearchResultTD implements SearchResult<TrustmarkDefinition> {

    private List<TrustmarkDefinition> tds;

    public SinglePageSearchResultTD(List<TrustmarkDefinition> tds){
        this.tds = tds;
    }

    @Override
    public List<TrustmarkDefinition> getResults() {
        return tds;
    }

    @Override
    public Integer getTotalCount() {
        return tds.size();
    }

    @Override
    public Integer getOffset() {
        return 0;
    }

    @Override
    public Integer getCount() {
        return tds.size();
    }

    @Override
    public Integer getPage() {
        return 1;
    }

    @Override
    public SearchResult<TrustmarkDefinition> nextPage() {
        throw new UnsupportedOperationException("not yet implemented.");
    }

}/* end SinglePageSearchResultTD */