package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.function.F1;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Created by brad on 12/7/15.
 */
public class TrustmarkDefinitionRequirementImpl extends TrustmarkFrameworkIdentifiedObjectImpl implements TrustmarkDefinitionRequirement {

    public TrustmarkDefinitionRequirementImpl() {
        this.setTypeName("TrustmarkDefinitionRequirement");
    }

    private String id;
    private List<Entity> providerReferences;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<Entity> getProviderReferences() {
        return this.providerReferences;
    }

    public void setProviderReferences(List<Entity> providerReferences) {
        this.providerReferences = providerReferences;
    }

    public void addProviderReference(Entity provider) {
        if (this.providerReferences == null)
            this.providerReferences = new ArrayList<>();
        this.providerReferences.add(provider);
    }

    public Boolean isTrustmarkDefinitionRequirement() {
        return true;
    }

    public Boolean isTrustInteroperabilityProfileReference() {
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
