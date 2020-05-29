package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.dao.SearchResult;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.util.List;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 9/15/16
 */
public class SinglePageSearchResultTIP implements SearchResult<TrustInteroperabilityProfile> {

    private List<TrustInteroperabilityProfile> tips;

    public SinglePageSearchResultTIP(List<TrustInteroperabilityProfile> _tips){
        this.tips = _tips;
    }

    @Override
    public List<TrustInteroperabilityProfile> getResults() {
        return tips;
    }

    @Override
    public Integer getTotalCount() {
        return tips.size();
    }

    @Override
    public Integer getOffset() {
        return 0;
    }

    @Override
    public Integer getCount() {
        return tips.size();
    }

    @Override
    public Integer getPage() {
        return 1;
    }

    @Override
    public SearchResult<TrustInteroperabilityProfile> nextPage() {
        throw new UnsupportedOperationException("not yet implemented.");
    }

}/* end SinglePageSearchResultTD */