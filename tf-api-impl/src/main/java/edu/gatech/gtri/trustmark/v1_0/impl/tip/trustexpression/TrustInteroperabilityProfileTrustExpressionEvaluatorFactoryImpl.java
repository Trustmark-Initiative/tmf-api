package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParserFactory;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class TrustInteroperabilityProfileTrustExpressionEvaluatorFactoryImpl implements TrustInteroperabilityProfileTrustExpressionEvaluatorFactory {

    @Override
    public TrustInteroperabilityProfileTrustExpressionEvaluator createDefaultEvaluator() {

        return new TrustInteroperabilityProfileTrustExpressionEvaluatorImpl(
                FactoryLoader.getInstance(TrustmarkResolver.class),
                FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionParserFactory.class).createDefaultParser());
    }
}
