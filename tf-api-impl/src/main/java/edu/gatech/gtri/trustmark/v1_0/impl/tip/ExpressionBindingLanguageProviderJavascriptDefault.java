package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluationException;

/**
 * Created by brad on 5/31/16.
 */
public class ExpressionBindingLanguageProviderJavascriptDefault implements ExpressionBindingLanguageProvider {

    @Override
    public Integer getPriority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public String getExpressionBindingLanguage() {
        return "JavaScript";
    }

    @Override
    public String transformExpression(String expression) throws TIPEvaluationException {
        String javascriptedExpr = expression;

        javascriptedExpr = javascriptedExpr.replaceAll(" [a|A][n|N][d|D] ", " and ");
        javascriptedExpr = javascriptedExpr.replace(" and ", " && ");

        javascriptedExpr = javascriptedExpr.replaceAll(" [o|O][r|R] ", " or ");
        javascriptedExpr = javascriptedExpr.replace(" or ", " || ");

        return javascriptedExpr;
    }
}
