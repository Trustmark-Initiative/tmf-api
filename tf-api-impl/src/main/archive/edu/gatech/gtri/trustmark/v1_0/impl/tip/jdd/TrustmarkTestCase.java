package edu.gatech.gtri.trustmark.v1_0.impl.tip.jdd;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirementType;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;

import java.util.Set;

public class TrustmarkTestCase extends BaseTestCase {
    // TODO documentation

    protected final Set<Trustmark> trustmarkInputs;

    public TrustmarkTestCase(String name, String description,
                             TrustInteroperabilityProfile tip,
                             Set<Trustmark> trustmarkInputs,
                             Set<Set<TrustmarkDefinitionRequirementType>> expectedGap,
                             TIPEvaluation expectedEvaluation) {

        super(name, description, tip, expectedGap, expectedEvaluation);
        this.trustmarkInputs = trustmarkInputs;
    }

    public Set<Trustmark> getEvaluationInputs() {
        return trustmarkInputs;
    }

}