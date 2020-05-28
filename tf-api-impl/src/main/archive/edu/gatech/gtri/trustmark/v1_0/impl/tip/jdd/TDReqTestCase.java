package edu.gatech.gtri.trustmark.v1_0.impl.tip.jdd;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirementType;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;

import java.util.Set;

public class TDReqTestCase extends BaseTestCase {
    // TODO documentation

    protected final Set<TrustmarkDefinitionRequirementType> evaluationInputs;

    public TDReqTestCase(String name, String description,
                         TrustInteroperabilityProfile tip,
                         Set<TrustmarkDefinitionRequirementType> evaluationInputs,
                         Set<Set<TrustmarkDefinitionRequirementType>> expectedGap,
                         TIPEvaluation expectedEvaluation) {

        super(name, description, tip, expectedGap, expectedEvaluation);
        this.evaluationInputs = evaluationInputs;
    }

    public Set<TrustmarkDefinitionRequirementType> getEvaluationInputs() {
        return evaluationInputs;
    }

}