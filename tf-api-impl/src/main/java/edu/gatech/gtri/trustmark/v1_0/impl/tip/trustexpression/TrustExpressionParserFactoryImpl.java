package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionParserFactoryJParsec;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFactory;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class TrustExpressionParserFactoryImpl implements TrustExpressionParserFactory {
    @Override
    public TrustExpressionParser createDefaultParser() {
        return new TrustExpressionParserImpl(TrustExpressionParserFactoryJParsec.parser());
    }
}
