package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;

/**
 * Created by brad on 12/7/15.
 */
public class TrustInteroperabilityProfileReferenceImpl extends TrustmarkFrameworkIdentifiedObjectImpl implements TrustInteroperabilityProfileReference {

    public TrustInteroperabilityProfileReferenceImpl(){
        this.setTypeName("TrustInteroperabilityProfileReference");
    }

    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isTrustmarkDefinitionRequirement(){return false;}

    public Boolean isTrustInteroperabilityProfileReference(){return true;}

}
