package edu.gatech.gtri.trustmark.v1_0.assessment;

import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResult;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import java.util.Collection;

/**
 * Checking of trustmark definition issuance criteria against a collection of assessment step results.
 *
 * @author GTRI Trustmark Team
 *
 */
public interface TrustmarkDefinitionIssuanceCriteria {

    /**
     * Checks whether or not the issuance criteria is satisfied for the given TrustmarkDefinition against the
     * given results.
     */
    public Boolean checkTrustmarkDefinitionIssuanceCriteria(TrustmarkDefinition td, Collection<AssessmentStepResult> results) throws IssuanceCriteriaEvaluationException;

}/* end TrustmarkDefinitionIssuanceCriteria */