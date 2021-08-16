package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;


import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionStringParser;
import org.jparsec.Parser;

import static java.util.Objects.requireNonNull;

public class TrustExpressionStringParserImpl implements TrustExpressionStringParser {

    private final Parser<TrustExpression<TrustExpressionData>> parser;

    public TrustExpressionStringParserImpl(final Parser<TrustExpression<TrustExpressionData>> parser) {

        requireNonNull(parser);

        this.parser = parser;
    }

    @Override
    public TrustExpression<TrustExpressionData> parse(final String trustExpression) {

        requireNonNull(trustExpression);

        return parser.parse(trustExpression);
    }
}
