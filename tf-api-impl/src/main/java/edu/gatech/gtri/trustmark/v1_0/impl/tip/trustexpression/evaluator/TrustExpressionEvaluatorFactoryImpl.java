package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserFactory;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class TrustExpressionEvaluatorFactoryImpl implements TrustExpressionEvaluatorFactory {

    @Override
    public TrustExpressionEvaluator createDefaultEvaluator() {

        return new TrustExpressionEvaluatorImpl(
                FactoryLoader.getInstance(TrustmarkResolver.class),
                FactoryLoader.getInstance(TrustExpressionParserFactory.class).createDefaultParser());
    }
}
