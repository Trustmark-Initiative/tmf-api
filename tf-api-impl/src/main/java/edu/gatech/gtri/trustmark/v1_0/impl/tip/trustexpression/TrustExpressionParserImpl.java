package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;


import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParser;
import org.gtri.fj.product.Unit;
import org.jparsec.Parser;

import static java.util.Objects.requireNonNull;

public class TrustExpressionParserImpl implements TrustExpressionParser {

    private final Parser<TrustExpression<Unit, String>> parser;

    public TrustExpressionParserImpl(final Parser<TrustExpression<Unit, String>> parser) {
        requireNonNull(parser);

        this.parser = parser;
    }

    @Override
    public TrustExpression<Unit, String> parse(final String trustExpression) {
        requireNonNull(trustExpression);

        return parser.parse(trustExpression);
    }
}
