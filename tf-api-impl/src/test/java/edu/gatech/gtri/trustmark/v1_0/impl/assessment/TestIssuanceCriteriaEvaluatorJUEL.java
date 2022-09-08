package edu.gatech.gtri.trustmark.v1_0.impl.assessment;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluation;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluator;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.assessment.StepResult;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel.IssuanceCriteriaEvaluatorJUEL;

public class TestIssuanceCriteriaEvaluatorJUEL extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(TestIssuanceCriteriaEvaluatorJUEL.class);


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
    public void testIssuanceCriteriaWithHiphensInStepIDs() throws Exception {
        String name = "testIssuanceCriteriaWithHiphensInStepIDs";
        String issuanceCriteria = "yes(AccessAuthorizations-Valid) " +
                "and yes(AccessAuthorizations-IntendedUse) " +
                "and not na(AccessAuthorizations-NeedToKnow) " +
                "and not na(AccessAuthorizations-PersonnelSecurity) " +
                "and not na(AccessAuthorizations-OtherCriteria)";

        AssessmentResults results = new AssessmentResultsImpl();
        results.put("AccessAuthorizations-Valid", StepResult.YES);
        results.put("AccessAuthorizations-IntendedUse", StepResult.YES);
        results.put("AccessAuthorizations-NeedToKnow", StepResult.YES);
        results.put("AccessAuthorizations-PersonnelSecurity", StepResult.YES);
        results.put("AccessAuthorizations-OtherCriteria", StepResult.YES);

        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testComplexExpressionWithExplicitNewlines() throws Exception {
        String name = "testComplexExpressionWithExplicitNewlines";
        String issuanceCriteria = "yes(SystemUsesandSupportsSAML20) \n" +
                "and yes(RedirectBindingSupport) \n" +
                "and (yes(NameIDPolicySupport) or yes(ForceAuthnSupport) or yes(isPassiveSupport))\n" +
                "and (yes(SignatureVerificationonAuthnRequest) or yes(ProperUseofAssertioninSAMLResponse) or yes(ProperDigitalSignatureofSAMLAssertion))\n" +
                "and (yes(UseofProperAssertionConsumerServiceURL) or yes(POSTBindingSupport) \n" +
                "     or yes(ProperUseofIssuerinSAMLResponse) or yes(ProperUseofSAMLAuthnStatement)\n" +
                "  or yes(ValidContextClass) or yes(ValidSubjectID) or yes(ValidConditions))";

        AssessmentResults results = new AssessmentResultsImpl();
        results.put("SystemUsesandSupportsSAML20", StepResult.YES);
        results.put("RedirectBindingSupport", StepResult.YES);
        results.put("NameIDPolicySupport", StepResult.YES);
        results.put("ForceAuthnSupport", StepResult.YES);
        results.put("isPassiveSupport", StepResult.YES);
        results.put("SignatureVerificationonAuthnRequest", StepResult.YES);
        results.put("ProperUseofAssertioninSAMLResponse", StepResult.YES);
        results.put("ProperDigitalSignatureofSAMLAssertion", StepResult.YES);
        results.put("UseofProperAssertionConsumerServiceURL", StepResult.YES);
        results.put("POSTBindingSupport", StepResult.YES);
        results.put("ProperUseofIssuerinSAMLResponse", StepResult.YES);
        results.put("ProperUseofSAMLAuthnStatement", StepResult.YES);
        results.put("ValidContextClass", StepResult.NO);
        results.put("ValidSubjectID", StepResult.NO);
        results.put("ValidConditions", StepResult.NO);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testYesAllLowerCasePredicateWith2YesResults() throws Exception {
        String name = "testYesAllLowerCasePredicateWith2YesResults";
        String issuanceCriteria = "yes(all)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.YES);
        results.put("SecondStep", StepResult.YES);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testYesAllMixedCasePredicateWith2YesResults() throws Exception {
        String name = "testYesAllMixedCasePredicateWith2YesResults";
        String issuanceCriteria = "yes(AlL)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.YES);
        results.put("SecondStep", StepResult.YES);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testSimpleYesAssessmentStep() throws Exception {
        String name = "testSimpleAssessmentStep";
        String issuanceCriteria = "FirstStep";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.YES);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testSimplNoAssessmentStep() throws Exception {
        String name = "testSimpleAssessmentStep";
        String issuanceCriteria = "FirstStep";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.NO);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testSimpleYesoAllPredicateWithEllipsis() throws Exception {
        String name = "testSimpleYesoAllPredicateWithEllipsis";
        String issuanceCriteria = "yes(FirstStep...SecondStep)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.YES);
        results.put("SecondStep", StepResult.YES);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testSimpleNoAllPredicateWithEllipsis() throws Exception {
        String name = "testNoAllPredicateWithEllipsis";
        String issuanceCriteria = "no(FirstStep...SecondStep)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.NO);
        results.put("SecondStep", StepResult.NO);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    // PASS
    @Test
    public void testSimpleNoAllPredicateWithEllipsis2() throws Exception {
        String name = "testSimpleNoAllPredicateWithEllipsis2";
        String issuanceCriteria = "no(SecondStep...ThirdStep)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.YES);
        results.put("SecondStep", StepResult.NO);
        results.put("ThirdStep", StepResult.NO);
        results.put("FourthStep", StepResult.NA);
        results.put("FifthStep", StepResult.YES);
        results.put("SixthStep", StepResult.NO);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNoAllPredicateWithEllipsis() throws Exception {
        String name = "testNoAllPredicateWithEllipsis";
        String issuanceCriteria = "not(yes(FirstStep, SecondStep) or yes(ThirdStep ... FifthStep))";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.NO);
        results.put("SecondStep", StepResult.NO);
        results.put("ThirdStep", StepResult.NO);
        results.put("FourthStep", StepResult.NO);
        results.put("FifthStep", StepResult.NO);
        results.put("SixthStep", StepResult.NO);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNoAllPredicateWith2NoResults() throws Exception {
        String name = "testNoAllPredicateWith2NoResults";
        String issuanceCriteria = "no(ALL)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.NO);
        results.put("SecondStep", StepResult.NO);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNaAllPredicateWith2NaResults() throws Exception {
        String name = "testNaAllPredicateWith2NaResults";
        String issuanceCriteria = "na(ALL)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.NA);
        results.put("SecondStep", StepResult.NA);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testYesNonePredicateWith2YesResults() throws Exception {
        String name = "testYesNonePredicateWith2YesResults";
        String issuanceCriteria = "yes(NONE)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("FirstStep", StepResult.NO);
        results.put("SecondStep", StepResult.NA);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }


    @Test
    public void testYesPredicateMultiStepWithYesResult() throws Exception {
//        boolean varArgs = javax.el.varArgs;
        String name = "testYesPredicateWithYesResult";
        String issuanceCriteria = "yes(step1,step3)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        results.put("step2", StepResult.NO);
        results.put("step3", StepResult.YES);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNoPredicateMultiStepWithNoResult() throws Exception {
//        boolean varArgs = javax.el.varArgs;
        String name = "testNoPredicateMultiStepWithNoResult";
        String issuanceCriteria = "no(step1,step2)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NO);
        results.put("step2", StepResult.NO);
        results.put("step3", StepResult.YES);
        Boolean expectedEvaluation = true;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testNaPredicateMultiStepWithNaResult() throws Exception {
//        boolean varArgs = javax.el.varArgs;
        String name = "testNoPredicateMultiStepWithNoResult";
        String issuanceCriteria = "na(step1,step2,step3,step5)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.NA);
        results.put("step2", StepResult.NA);
        results.put("step3", StepResult.YES);
        results.put("step4", StepResult.YES);
        results.put("step5", StepResult.NA);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

    @Test
    public void testOrMulti() throws Exception {
        String name = "testOrMulti";
        String issuanceCriteria = "yes(step1) and yes(step2)";
        AssessmentResults results = new AssessmentResultsImpl();
        results.put("step1", StepResult.YES);
        results.put("step2", StepResult.NO);
        results.put("step3", StepResult.YES);
        Boolean expectedEvaluation = false;
        doTest(name, issuanceCriteria, results, expectedEvaluation);
    }

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
