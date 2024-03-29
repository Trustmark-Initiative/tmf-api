package edu.gatech.gtri.trustmark.v1_0.impl.tip.parser;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserFactory;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class TrustExpressionParserFactoryImpl implements TrustExpressionParserFactory {

    @Override
    public TrustExpressionParser createDefaultParser() {

        return new TrustExpressionParserImpl(
                FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class),
                FactoryLoader.getInstance(TrustmarkDefinitionResolver.class));
    }
}
