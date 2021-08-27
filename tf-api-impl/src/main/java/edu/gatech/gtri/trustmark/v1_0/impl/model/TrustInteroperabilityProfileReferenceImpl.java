package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.function.F1;

import static java.util.Objects.requireNonNull;

/**
 * Created by brad on 12/7/15.
 */
public class TrustInteroperabilityProfileReferenceImpl extends TrustmarkFrameworkIdentifiedObjectImpl implements TrustInteroperabilityProfileReference {

    public TrustInteroperabilityProfileReferenceImpl() {
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

    public Boolean isTrustmarkDefinitionRequirement() {
        return false;
    }

    public Boolean isTrustInteroperabilityProfileReference() {
        return true;
    }

    @Override
    public <T1> T1 match(
            final F1<TrustmarkDefinitionRequirement, T1> fTrustmarkDefinitionRequirement,
            final F1<TrustInteroperabilityProfileReference, T1> fTrustInteroperabilityProfileReference) {

        requireNonNull(fTrustmarkDefinitionRequirement);
        requireNonNull(fTrustInteroperabilityProfileReference);

        return fTrustInteroperabilityProfileReference.f(this);
    }
}
