package edu.gatech.gtri.trustmark.v1_0.impl.assessment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel.IssuanceCriteriaEvaluatorJUELFactory;

/**
 * Created by brad on 3/23/15.
 */
public class TestIssuanceCriteriaEvaluatorJUELFactory extends AbstractTest {

    private static final Logger logger = LogManager.getLogger(TestIssuanceCriteriaEvaluatorJUELFactory.class);


    /**
     * Tests that when we get an instance of the IssuanceCriteriaEvaluatorFactory, that it is the IssuanceCriteriaEvaluatorJUELFactory
     */
    @Test
    public void testGetInstance(){
    	// TODO complete
        logger.info("Getting an instance of 'IssuanceCriteriaEvaluatorFactory'...");
        IssuanceCriteriaEvaluatorFactory factory = FactoryLoader.getInstance(IssuanceCriteriaEvaluatorFactory.class);
        assertThat(factory, notNullValue());
        assertThat(factory, instanceOf(IssuanceCriteriaEvaluatorJUELFactory.class));
        logger.info("Successfully retrieved an instance of IssuanceCriteriaEvaluatorFactory, and it is indeed IssuanceCriteriaEvaluatorELFactory");
    }//end testGetInstance();


}//end TestIssuanceCriteriaEvaluatorELFactory()