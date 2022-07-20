package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.function.F1;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class TrustmarkDefinitionRequirementImpl extends TrustmarkFrameworkIdentifiedObjectImpl implements TrustmarkDefinitionRequirement {

    private String id;
    private List<Entity> providerReferences;

    public TrustmarkDefinitionRequirementImpl() {
        this.setTypeName(TYPE_NAME_TRUSTMARK_DEFINITION_REQUIREMENT);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public List<Entity> getProviderReferences() {
        return this.providerReferences;
    }

    public void setProviderReferences(final List<Entity> providerReferences) {
        this.providerReferences = providerReferences;
    }

    public void addProviderReference(final Entity provider) {
        if (this.providerReferences == null)
            this.providerReferences = new ArrayList<>();
        this.providerReferences.add(provider);
    }

    public boolean isTrustmarkDefinitionRequirement() {
        return true;
    }

    public boolean isTrustInteroperabilityProfileReference() {
        return false;
    }

    public <T1> T1 match(
            final F1<TrustmarkDefinitionRequirement, T1> fTrustmarkDefinitionRequirement,
            final F1<TrustInteroperabilityProfileReference, T1> fTrustInteroperabilityProfileReference) {

        requireNonNull(fTrustmarkDefinitionRequirement);
        requireNonNull(fTrustInteroperabilityProfileReference);

        return fTrustmarkDefinitionRequirement.f(this);
    }
}
