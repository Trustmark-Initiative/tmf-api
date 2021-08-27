package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserFactory;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class TrustmarkDefinitionRequirementEvaluatorFactoryImpl implements TrustmarkDefinitionRequirementEvaluatorFactory {

    @Override
    public TrustmarkDefinitionRequirementEvaluator createDefaultEvaluator() {

        return new TrustmarkDefinitionRequirementEvaluatorImpl(
                FactoryLoader.getInstance(TrustmarkResolver.class),
                FactoryLoader.getInstance(TrustExpressionParserFactory.class).createDefaultParser());
    }
}
