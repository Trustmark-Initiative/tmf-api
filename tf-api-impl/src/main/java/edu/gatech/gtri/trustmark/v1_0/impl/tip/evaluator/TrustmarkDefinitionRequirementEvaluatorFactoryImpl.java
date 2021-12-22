package edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustmarkDefinitionRequirementEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustmarkDefinitionRequirementEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserFactory;
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
