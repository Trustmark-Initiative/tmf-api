package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.function.F1;

import static java.util.Objects.requireNonNull;

public class TrustInteroperabilityProfileReferenceImpl extends TrustmarkFrameworkIdentifiedObjectImpl implements TrustInteroperabilityProfileReference {

    private String id;

    public TrustInteroperabilityProfileReferenceImpl() {
        this.setTypeName(TYPE_NAME_TRUST_INTEROPERABILITY_PROFILE_REFERENCE);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public boolean isTrustmarkDefinitionRequirement() {
        return false;
    }

    public boolean isTrustInteroperabilityProfileReference() {
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
