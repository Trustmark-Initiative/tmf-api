package edu.gatech.gtri.trustmark.v1_0.impl.assessment;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluation;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluator;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.assessment.StepResult;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel.IssuanceCriteriaEvaluatorJUEL;

public class TestIssuanceCriteriaEvaluatorJUEL extends AbstractTest {

    private static final Logger logger = LogManager.getLogger(TestIssuanceCriteriaEvaluatorJUEL.class);


    private void doTest(String testName, String issuanceCriteria, AssessmentResults results, Boolean expectedEvaluation) throws Exception {
        logger.info("Getting an evaluator");
        IssuanceCriteriaEvaluatorJUEL evaluator = getEvaluator();
        assertThat(evaluator, notNullValue());
        logger.info("Successfully retrieved an evaluator");

        logger.info(String.format("For test %s, evaluating issuance criteria '%s' against results '%s'...", testName, issuanceCriteria, results));
        IssuanceCriteriaEvaluation evaluation = evaluator.evaluate(issuanceCriteria, results);
        logger.info(String.format("IsSatisfied for test %s: %b", testName, evaluation.isSatisfied()));
        assertThat(testName, evaluation.isSatisfied(), equalTo(expectedEvaluation));
    }//end doTest()

    @Test
    public void testYesPredicateWithYesResult() throws Exception {
        String name = "testYesPredicateWithYesResult";
        String issuanceCriteria = "yes(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testYesPredicateWithNoResult() throws Exception {
        String name = "testYesPredicateWithNoResult";
        String issuanceCriteria = "yes(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NO);
        Boolean expectedEvaluation = false;

        doTest(name, issuanceCriteria, results, expectedEvaluation);

    }


    @Test
    public void testYesPredicateWithNAResult() throws Exception {
        String name = "testYesPredicateWithNAResult";
        String issuanceCriteria = "yes(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NA);
        Boolean expectedEvaluation = false;

        doTest(name, issuanceCriteria, results, expectedEvaluation);

    }


    @Test
    public void testNoPredicateWithYesResult() throws Exception {
        String name = "testNoPredicateWithYesResult";
        String issuanceCriteria = "no(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNoPredicateWithNoResult() throws Exception {
        String name = "testNoPredicateWithNoResult";
        String issuanceCriteria = "no(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NO);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNoPredicateWithNAResult() throws Exception {
        String name = "testNoPredicateWithNAResult";
        String issuanceCriteria = "no(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NA);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNAPredicateWithYesResult() throws Exception {
        String name = "testNAPredicateWithYesResult";
        String issuanceCriteria = "na(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNAPredicateWithNoResult() throws Exception {
        String name = "testNAPredicateWithNoResult";
        String issuanceCriteria = "na(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NO);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNAPredicateWithNAResult() throws Exception {
        String name = "testNAPredicateWithNAResult";
        String issuanceCriteria = "na(step1)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NA);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testAnd() throws Exception {
        String name = "testAnd";
        String issuanceCriteria = "yes(step1) and yes(step2)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        results.put("step2", StepResult.NO);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testOr() throws Exception {
        String name = "testOr";
        String issuanceCriteria = "yes(step1) or yes(step2)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        results.put("step2", StepResult.NO);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNotWord() throws Exception {
        String name = "testNotWord";
        String issuanceCriteria = "not(yes(step1))";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNotSymbol() throws Exception {
        String name = "testNotSymbol";
        String issuanceCriteria = "!(yes(step1))";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testComboAndOrNotWithGrouping() throws Exception {
        String name = "testComboAndOrNotWithGrouping";
        String issuanceCriteria = "yes(step1) and (yes(step2) or (not(no(step3))))";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        results.put("step2", StepResult.NO);
        results.put("step3", StepResult.NA);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testComboAndOrNotWithoutGrouping() throws Exception {
        String name = "testComboAndOrNotWithoutGrouping";
        String issuanceCriteria = "yes(step1) and yes(step2) or (not(no(step3)))";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NO);
        results.put("step2", StepResult.NO);
        results.put("step3", StepResult.NA);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }


    /**
     * Tests creation of a new {@link IssuanceCriteriaEvaluatorJUEL} instance.
     */
    // TODO @Test
    public void testGetInstance() {
        logger.info("Getting an instance of 'IssuanceCriteriaEvaluatorFactory'...");
        IssuanceCriteriaEvaluatorFactory factory = FactoryLoader.getInstance(IssuanceCriteriaEvaluatorFactory.class);
        assertThat(factory, notNullValue());
        logger.info("Successfully retrieved an instance of IssuanceCriteriaEvaluatorFactory");
        logger.info("Getting an instance of 'IssuanceCriteriaEvaluator'");
        IssuanceCriteriaEvaluator evaluator = factory.createEvaluator();
        assertThat(evaluator, notNullValue());
        assertThat(evaluator, instanceOf(IssuanceCriteriaEvaluatorJUEL.class));
        logger.info("Successfully retrieved an instance of IssuanceCriteriaEvaluator, and it is indeed IssuanceCriteriaEvaluatorJUEL");
    }// end testGetInstance();


    private IssuanceCriteriaEvaluatorJUEL getEvaluator() {
        IssuanceCriteriaEvaluatorFactory factory = FactoryLoader.getInstance(IssuanceCriteriaEvaluatorFactory.class);

        IssuanceCriteriaEvaluator evaluator = factory.createEvaluator();
        assertThat(evaluator, notNullValue());
        assertThat(evaluator, instanceOf(IssuanceCriteriaEvaluatorJUEL.class));

        return (IssuanceCriteriaEvaluatorJUEL) evaluator;
    }

}
