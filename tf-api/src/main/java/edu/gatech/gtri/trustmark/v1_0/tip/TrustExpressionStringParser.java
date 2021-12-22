package edu.gatech.gtri.trustmark.v1_0.tip;

public interface TrustExpressionStringParser {

    TrustExpression<TrustExpressionData> parse(final String trustExpression);
}
