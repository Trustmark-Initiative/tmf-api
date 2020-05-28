package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluationException;

/**
 * Useful for telling the TIP Evaluation engine which expression language to use for the javax.scripting API.
 * Created by brad on 5/31/16.
 */
public interface ExpressionBindingLanguageProvider {

    /**
     * The highest priority provider is used.  All others ignored.
     */
    Integer getPriority();

    /**
     * The name of your expression language as javax.scripting knows it.
     */
    String getExpressionBindingLanguage();

    /**
     * Given the starting experssion, this method will transform it into the executable expression.
     */
    String transformExpression(String expression) throws TIPEvaluationException;

}
