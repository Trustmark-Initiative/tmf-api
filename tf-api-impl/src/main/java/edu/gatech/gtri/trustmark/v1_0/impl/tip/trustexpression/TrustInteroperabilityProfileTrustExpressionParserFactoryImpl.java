package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParserFactory;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class TrustInteroperabilityProfileTrustExpressionParserFactoryImpl implements TrustInteroperabilityProfileTrustExpressionParserFactory {

    @Override
    public TrustInteroperabilityProfileTrustExpressionParser createDefaultParser() {
        return new TrustInteroperabilityProfileTrustExpressionParserImpl(FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class));
    }
}
