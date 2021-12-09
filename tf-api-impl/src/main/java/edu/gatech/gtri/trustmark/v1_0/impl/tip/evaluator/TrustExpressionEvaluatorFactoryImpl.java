package edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserFactory;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifier;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class TrustExpressionEvaluatorFactoryImpl implements TrustExpressionEvaluatorFactory {

    @Override
    public TrustExpressionEvaluator createDefaultEvaluator() {

        return new TrustExpressionEvaluatorImpl(
                FactoryLoader.getInstance(TrustmarkResolver.class),
                FactoryLoader.getInstance(TrustExpressionParserFactory.class).createDefaultParser());
    }

    @Override
    public TrustExpressionEvaluator createEvaluator(
            final TrustmarkVerifier trustmarkVerifier) {

        return new TrustExpressionEvaluatorImpl(
                FactoryLoader.getInstance(TrustmarkResolver.class),
                trustmarkVerifier,
                FactoryLoader.getInstance(TrustExpressionParserFactory.class).createDefaultParser());
    }
}
