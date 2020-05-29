package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluatorFactory;

/**
 * Created by brad on 5/20/16.
 */
public class TIPEvaluatorFactoryImpl implements TIPEvaluatorFactory {

    @Override
    public TIPEvaluator createDefaultEvaluator() {
        return new ExpressionBindingTIPEvaluator();
    }
}
