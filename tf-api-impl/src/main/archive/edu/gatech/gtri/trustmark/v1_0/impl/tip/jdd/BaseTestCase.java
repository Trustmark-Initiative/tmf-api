package edu.gatech.gtri.trustmark.v1_0.impl.tip.jdd;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirementType;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;

import java.util.Set;

public abstract class BaseTestCase {
    protected final String name;
    protected final String description;
    protected final TrustInteroperabilityProfile tip;
    protected final Set<Set<TrustmarkDefinitionRequirementType>> expectedGap;
    protected final TIPEvaluation expectedEvaluation;

    protected BaseTestCase(String name, String description,
                           TrustInteroperabilityProfile tip,
                           Set<Set<TrustmarkDefinitionRequirementType>> expectedGap,
                           TIPEvaluation expectedEvaluation) {

        this.name = name;
        this.description = description;
        this.tip = tip;
        this.expectedGap = expectedGap;
        this.expectedEvaluation = expectedEvaluation;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TrustInteroperabilityProfile getTip() {
        return tip;
    }

    public Set<Set<TrustmarkDefinitionRequirementType>> getExpectedGap() {
        return expectedGap;
    }

    public TIPEvaluation getExpectedEvaluation() {
        return expectedEvaluation;
    }
}