package edu.gatech.gtri.trustmark.v1_0.impl.tip.jdd;

import java.net.URI;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.gatech.gtri.trustmark.v1_0.impl.tip.TIPEvaluationImpl;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirementType;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluationException;

public class TestTIPEvaluatorJDD extends AbstractJddTestCase {

	private static final Logger logger = LogManager.getLogger(TestTIPEvaluatorJDD.class);
//
//	@Test
//	public void executeTestCases() throws TIPEvaluationException {
//		TIPEvaluatorJDD evaluator = getEvaluator();
//		for(TDReqTestCase testCase : this.getTestCases()) {
//			logger.info("Executing TD Requirement Test Case: " + testCase.getName());
//			executeTestCase(evaluator, testCase);
//			logger.info("Done executing TD Requirement Test Case: " + testCase.getName());
//		}
//
//		for(TrustmarkTestCase trustmarkTestCase : TIPTestCaseData.getTrustmarkTestCases()) {
//			logger.info("Executing Trustmark Test Case: " + trustmarkTestCase.getName());
//			executeTrustmarkTestCase(evaluator, trustmarkTestCase);
//			logger.info("Done executing Trustmark Test Case: " + trustmarkTestCase.getName());
//		}
//	}
	
	/**
	 * Executes the provided test case.
	 * 
	 * @param testCase
	 * @throws TIPEvaluationException 
	 * @throws Exception
	 */
	public void executeTestCase(TIPEvaluatorJDD evaluator, TDReqTestCase testCase) throws TIPEvaluationException {
		TrustInteroperabilityProfile tip = testCase.getTip();
		Set<TrustmarkDefinitionRequirementType> evaluationInputs = testCase.getEvaluationInputs();
		TIPEvaluation expectedEvaluation = testCase.getExpectedEvaluation();

		TIPEvaluation actualEvaluation = evaluator.evaluateTIPAgainstTDRequirements(tip, evaluationInputs);

		runMatchersOnEvaluations(expectedEvaluation, actualEvaluation);

		// evaluate again to test that the evaluator can perform multiple
		// evaluations on the same TIP in series
		actualEvaluation = evaluator.evaluateTIPAgainstTDRequirements(tip, evaluationInputs);

		runMatchersOnEvaluations(expectedEvaluation, actualEvaluation);

	}

	public void executeTrustmarkTestCase(TIPEvaluatorJDD evaluator, TrustmarkTestCase testCase) throws TIPEvaluationException {
		logger.info("Executing Trustmark Test Case["+testCase.getName()+"]: "+testCase.getDescription());
		TrustInteroperabilityProfile tip = testCase.getTip();
		Set<Trustmark> evaluationInputs = testCase.getEvaluationInputs();
		TIPEvaluation expectedEvaluation = testCase.getExpectedEvaluation();

		logger.debug("Performing evaluation...");
		TIPEvaluation actualEvaluation = evaluator.evaluateTIPAgainstTrustmarks(tip, evaluationInputs);

		runMatchersOnEvaluations(expectedEvaluation, actualEvaluation);

		// evaluate again to test that the evaluator can perform multiple
		// evaluations on the same TIP in series
		actualEvaluation = evaluator.evaluateTIPAgainstTrustmarks(tip, evaluationInputs);

		runMatchersOnEvaluations(expectedEvaluation, actualEvaluation);

		logger.info("Trustmark Test Case '"+testCase.getName()+"' was successful.");
	}


	@Test
	public void testTipRefTipMatchingEvalInputTMSubset() throws Exception {
		// Test Case 1
		String name = "Test Case 1";
		String description = "TIP that references other TIP; matching evaluation input trustmark subset.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI.create("http://trustmark.org/TIPD"));

		Set<Trustmark> evaluationInputs = new LinkedHashSet<>();
		{
			Trustmark trustmark = trustmarkMap.get("TD2NIEF");
			evaluationInputs.add(trustmark);
		}
		{
			Trustmark trustmark = trustmarkMap.get("TD1NIEF");
			evaluationInputs.add(trustmark);
		}

		Set<Set<TrustmarkDefinitionRequirementType>> expectedSatisfactionGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(new Date(), expectedSatisfactionGap, tip, null, evaluationInputs);

		TrustmarkTestCase tc = new TrustmarkTestCase(name, description, tip,
				evaluationInputs, expectedSatisfactionGap, expectedEvaluation);

		this.executeTrustmarkTestCase(getTIPEvaluatorJDD(), tc);
	}

	@Test
	public void testTipRefTipNonMatchingEvalInputTM() throws Exception {
		// Test Case 2
		String name = "Test Case 2";
		String description = "TIP that references other TIP; non-matching evaluation input trustmarks.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPD"));

		Set<Trustmark> evaluationInputs = new LinkedHashSet<Trustmark>();
		{
			Trustmark trustmark = trustmarkMap.get("TD3NIEF");
			evaluationInputs.add(trustmark);
		}

		Set<Set<TrustmarkDefinitionRequirementType>> expectedSatisfactionGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		{
			Set<TrustmarkDefinitionRequirementType> expectedCube = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD2Any").getTrustmarkDefinitionRequirement());
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD1Any").getTrustmarkDefinitionRequirement());
			expectedSatisfactionGap.add(expectedCube);
		}
		{
			Set<TrustmarkDefinitionRequirementType> expectedCube = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD2Any").getTrustmarkDefinitionRequirement());
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD3GTRI").getTrustmarkDefinitionRequirement());
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD4NIEFGTRI").getTrustmarkDefinitionRequirement());
			expectedSatisfactionGap.add(expectedCube);
		}

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedSatisfactionGap, tip, null, evaluationInputs);

		TrustmarkTestCase tc = new TrustmarkTestCase(name, description, tip, evaluationInputs, expectedSatisfactionGap, expectedEvaluation);

		this.executeTrustmarkTestCase(getTIPEvaluatorJDD(), tc);
	}


	@Test
	public void testTipRefTipNonMatchingEvalInputTM2() throws Exception {
		// Test Case 15
		String name = "Test Case 15";
		String description = "TIP that references other TIP; non-matching evaluation input trustmarks.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPD"));

		Set<Trustmark> trustmarkInputs = new LinkedHashSet<Trustmark>();
		{
			Trustmark trustmark = trustmarkMap.get("TD3GTRI");
			trustmarkInputs.add(trustmark);
		}

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		{
			Set<TrustmarkDefinitionRequirementType> expectedCube = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD2Any").getTrustmarkDefinitionRequirement());
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD1Any").getTrustmarkDefinitionRequirement());
			expectedGap.add(expectedCube);
		}
		{
			Set<TrustmarkDefinitionRequirementType> expectedCube = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD2Any").getTrustmarkDefinitionRequirement());
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD4NIEFGTRI").getTrustmarkDefinitionRequirement());
			expectedGap.add(expectedCube);
		}

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, null, trustmarkInputs);

		TrustmarkTestCase tc = new TrustmarkTestCase(name, description, tip,
				trustmarkInputs, expectedGap, expectedEvaluation);

		this.executeTrustmarkTestCase(getTIPEvaluatorJDD(), tc);
	}

}
