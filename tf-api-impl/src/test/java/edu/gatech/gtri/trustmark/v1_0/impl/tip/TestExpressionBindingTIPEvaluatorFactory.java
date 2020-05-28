package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluatorFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by brad on 5/20/16.
 */
public class TestExpressionBindingTIPEvaluatorFactory extends AbstractTest {

    @Test
    public void testFactoryResolution() {
        logger.info("Asserting we can resovle the TIPEvaluatorFactoryImpl...");
        TIPEvaluatorFactory evaluatorFactory = FactoryLoader.getInstance(TIPEvaluatorFactory.class);
        assertThat(evaluatorFactory, notNullValue());
        assertThat(evaluatorFactory, instanceOf(TIPEvaluatorFactoryImpl.class));
    }

    @Test
    public void testTIPEvaluatorResolution(){
        logger.info("Asserting we can resovle the ExpressionBindingTIPEvaluator...");
        TIPEvaluatorFactory evaluatorFactory = FactoryLoader.getInstance(TIPEvaluatorFactory.class);
        assertThat(evaluatorFactory, notNullValue());
        TIPEvaluator tipEvaluator = evaluatorFactory.createDefaultEvaluator();
        assertThat(tipEvaluator, notNullValue());
        assertThat(tipEvaluator, instanceOf(ExpressionBindingTIPEvaluator.class));
        assertThat(tipEvaluator.calculatesSatisfactionGap(), equalTo(Boolean.FALSE));
    }


}
