package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionParserFactoryJParsec;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionStringParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionStringParserFactory;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class TrustExpressionStringParserFactoryImpl implements TrustExpressionStringParserFactory {

    @Override
    public TrustExpressionStringParser createDefaultParser() {

        return new TrustExpressionStringParserImpl(TrustExpressionParserFactoryJParsec.parser());
    }
}
