package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

public interface TrustExpressionStringParser {

    TrustExpression<TrustExpressionData> parse(final String trustExpression);
}
