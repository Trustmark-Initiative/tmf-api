package edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel;

import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluator;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluatorFactory;

/**
 * Implements a translation from a given issuance criteria string to java EL for evaluation.
 */
public class IssuanceCriteriaEvaluatorJUELFactory implements IssuanceCriteriaEvaluatorFactory {

    @Override
    public IssuanceCriteriaEvaluator createEvaluator() {
        return new IssuanceCriteriaEvaluatorJUEL();
    }

}
