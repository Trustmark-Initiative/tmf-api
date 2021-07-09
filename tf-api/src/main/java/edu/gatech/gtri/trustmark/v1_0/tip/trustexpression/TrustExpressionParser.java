package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import org.gtri.fj.product.Unit;

public interface TrustExpressionParser {

    TrustExpression<Unit, String> parse(final String trustExpression);
}
