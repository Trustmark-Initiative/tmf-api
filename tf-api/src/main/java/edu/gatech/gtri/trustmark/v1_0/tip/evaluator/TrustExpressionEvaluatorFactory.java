package edu.gatech.gtri.trustmark.v1_0.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifier;

public interface TrustExpressionEvaluatorFactory {

    TrustExpressionEvaluator createDefaultEvaluator();

    TrustExpressionEvaluator createEvaluator(
            final TrustmarkVerifier trustmarkVerifier);
}
