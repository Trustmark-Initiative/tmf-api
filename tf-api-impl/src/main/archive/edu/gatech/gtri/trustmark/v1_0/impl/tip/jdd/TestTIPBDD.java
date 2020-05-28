package edu.gatech.gtri.trustmark.v1_0.impl.tip.jdd;

import java.net.URI;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.gatech.gtri.trustmark.v1_0.impl.tip.TIPEvaluationImpl;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;
import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import edu.gatech.gtri.trustmark.v1_0.impl.io.InMemoryMapTIPResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirementType;

/**
 * Tests TIPBDD functionality which includes evaluating TIP trust expressions.
 * 
 * TODO Add tests for trust expressions with more complex logical structures.
 * 
 * TODO Add tests for trust expressions that include a wider variety of types of
 * reference ID names, e.g., include hyphens, dots, underscores, etc. I.e., test
 * for all types of characters allowed in XML IDs.
 * 
 * TODO Add tests for situations that should result in failure.
 * 
 * TODO Flesh out documentation.
 * 
 * @author GTRI Trustmark Team
 *
 */
public class TestTIPBDD extends AbstractJddTestCase {

	private static final Logger logger = Logger.getLogger(TestTIPBDD.class);

	private void executeTestCase(TDReqTestCase tc) throws Exception {
		logger.info("Executing Test Case["+tc.getName()+"]: "+tc.getDescription());

		TrustInteroperabilityProfile tip = tc.getTip();
		Set<TrustmarkDefinitionRequirementType> evaluationInputs = tc.getEvaluationInputs();
		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = tc.getExpectedGap();

		logger.debug("Creating new TIPBDD with in-memory TIP finder...");
		TIPBDD tipBDD = new TIPBDD(tip, new InMemoryMapTIPResolver(this.getTIPMap()));
		// tipBDD.printBDD();

		logger.debug("Evaluating...");
		Set<Set<TrustmarkDefinitionRequirementType>> tdReqCubes = tipBDD.evaluate(evaluationInputs);
		logger.debug("Asserting correctness...");
		MatcherAssert.assertThat(tdReqCubes, Matchers.equalTo(expectedGap));
		tdReqCubes = tipBDD.evaluate(evaluationInputs);
		MatcherAssert.assertThat(tdReqCubes, Matchers.equalTo(expectedGap));


		// This next section was taken from the TestTIPEvaluatorJDD class, so technically isn't really supposed to be here.
		logger.debug("Testing with JDD evaluator...");
		TIPEvaluation expectedEvaluation = tc.getExpectedEvaluation();

		TIPEvaluatorJDD evaluatorJDD = getTIPEvaluatorJDD();
		TIPEvaluation actualEvaluation = evaluatorJDD.evaluateTIPAgainstTDRequirements(tip, evaluationInputs);

		runMatchersOnEvaluations(expectedEvaluation, actualEvaluation);

		// evaluate again to test that the evaluator can perform multiple
		// evaluations on the same TIP in series
		actualEvaluation = evaluatorJDD.evaluateTIPAgainstTDRequirements(tip, evaluationInputs);

		runMatchersOnEvaluations(expectedEvaluation, actualEvaluation);


		logger.info("Successfully executed test case: "+tc.getName());
	}



	@Test
	public void testTipWithOneReferenceMatchingEvaluationInput() throws Exception {
		// Test Case 1
		String name = "Test Case 1";
		String description = "TIP with one reference; matching evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI.create("http://trustmark.org/TIPA"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		TrustmarkDefinitionRequirementType tdReq = this.getTestReferenceTypeChoice("TD1Any").getTrustmarkDefinitionRequirement();
		evaluationInputs.add(tdReq);

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(new Date(), expectedGap, tip, evaluationInputs, null);
		TDReqTestCase tc = new TDReqTestCase(name, description, tip, evaluationInputs, expectedGap, expectedEvaluation);
		this.executeTestCase(tc);
	}



	@Test
	public void testTipWithOneRefNonMatchingEvalInput() throws Exception {
		// Test Case 2
		String name = "Test Case 2";
		String description = "TIP with one reference; non-matching evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI.create("http://trustmark.org/TIPA"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		TrustmarkDefinitionRequirementType tdReq = this
				.getTestReferenceTypeChoice("TD2Any")
				.getTrustmarkDefinitionRequirement();
		evaluationInputs.add(tdReq);

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		Set<TrustmarkDefinitionRequirementType> expectedCube1 = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		expectedCube1.add(this.getTestReferenceTypeChoice("TD1Any").getTrustmarkDefinitionRequirement());
		expectedGap.add(expectedCube1);

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);
		this.executeTestCase(tc);
	}


	@Test
	public void testTipWithOneRefNoEvalInput() throws Exception {
		// Test Case 3
		String name = "Test Case 3";
		String description = "TIP with one reference; no evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPA"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		Set<TrustmarkDefinitionRequirementType> expectedCube1 = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		expectedCube1.add(this.getTestReferenceTypeChoice(
				"TD1Any").getTrustmarkDefinitionRequirement());
		expectedGap.add(expectedCube1);

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}


	@Test
	public void testTipWithTwoRefMatchingEvalInput() throws Exception {
		// Test Case 4
		String name = "Test Case 4";
		String description = "TIP with two references; matching evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPB"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD1Any")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD2Any")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}


	@Test
	public void testTipWithTwoRefNonMatchingEvalInput() throws Exception {
		// Test Case 5
		String name = "Test Case 5";
		String description = "TIP with two references; non-matching evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPB"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		TrustmarkDefinitionRequirementType tdReq = this
				.getTestReferenceTypeChoice("TD2Any")
				.getTrustmarkDefinitionRequirement();
		evaluationInputs.add(tdReq);

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		Set<TrustmarkDefinitionRequirementType> expectedCube1 = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		expectedCube1.add(this.getTestReferenceTypeChoice(
				"TD1Any").getTrustmarkDefinitionRequirement());
		expectedGap.add(expectedCube1);

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}


	@Test
	public void testTipWithTwoRefNoEvalInput() throws Exception {
		// Test Case 6
		String name = "Test Case 6";
		String description = "TIP with two references; no evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPB"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		Set<TrustmarkDefinitionRequirementType> expectedCube1 = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		expectedCube1.add(this.getTestReferenceTypeChoice(
				"TD1Any").getTrustmarkDefinitionRequirement());
		expectedCube1.add(this.getTestReferenceTypeChoice(
				"TD2Any").getTrustmarkDefinitionRequirement());
		expectedGap.add(expectedCube1);

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}


	@Test
	public void testComplexTipMatchingEvalInput() throws Exception {
		// Test Case 7
		String name = "Test Case 7";
		String description = "Complex TIP; matching evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPC"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD1Any")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD3NIEF")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD4NIEF")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}


	@Test
	public void testComplexTipNonMatchingEvalInput() throws Exception {
		// Test Case 8
		String name = "Test Case 8";
		String description = "Complex TIP; non-matching evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPC"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD4GTRI")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		{
			Set<TrustmarkDefinitionRequirementType> expectedCube = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD1Any").getTrustmarkDefinitionRequirement());
			expectedGap.add(expectedCube);
		}
		{
			Set<TrustmarkDefinitionRequirementType> expectedCube = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD3GTRI").getTrustmarkDefinitionRequirement());
			expectedGap.add(expectedCube);
		}

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}


	@Test
	public void testComplexTipNoEvalInput() throws Exception {
		// Test Case 9
		String name = "Test Case 9";
		String description = "Complex TIP; no evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPC"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();
		{
			Set<TrustmarkDefinitionRequirementType> expectedCube = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD1Any").getTrustmarkDefinitionRequirement());
			expectedGap.add(expectedCube);
		}
		{
			Set<TrustmarkDefinitionRequirementType> expectedCube = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD3GTRI").getTrustmarkDefinitionRequirement());
			expectedCube.add(this.getTestReferenceTypeChoice(
					"TD4NIEFGTRI").getTrustmarkDefinitionRequirement());
			expectedGap.add(expectedCube);
		}

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}


	@Test
	public void testComplexTipMatchingEvalInputSubset() throws Exception {
		// Test Case 10
		String name = "Test Case 10";
		String description = "Complex TIP; matching evaluation input subset.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPC"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD1Any")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}


	@Test
	public void testTipRefTipMatchingEvalInputSubset() throws Exception {
		// Test Case 11
		String name = "Test Case 11";
		String description = "TIP that references other TIP; matching evaluation input subset.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPD"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD2Any")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD1Any")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
		}

		Set<Set<TrustmarkDefinitionRequirementType>> expectedGap = new LinkedHashSet<Set<TrustmarkDefinitionRequirementType>>();

		TIPEvaluation expectedEvaluation = new TIPEvaluationImpl(
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}

	@Test
	public void testTipRefTipNonMatchingEvalInput() throws Exception {
		// Test Case 12
		String name = "Test Case 12";
		String description = "TIP that references other TIP; non-matching evaluation input.";

		TrustInteroperabilityProfile tip = this.getTestTIP(URI
				.create("http://trustmark.org/TIPD"));

		Set<TrustmarkDefinitionRequirementType> evaluationInputs = new LinkedHashSet<TrustmarkDefinitionRequirementType>();
		{
			TrustmarkDefinitionRequirementType tdReq = this
					.getTestReferenceTypeChoice("TD3GTRI")
					.getTrustmarkDefinitionRequirement();
			evaluationInputs.add(tdReq);
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
				new Date(), expectedGap, tip, evaluationInputs, null);

		TDReqTestCase tc = new TDReqTestCase(name, description, tip,
				evaluationInputs, expectedGap, expectedEvaluation);

		this.executeTestCase(tc);
	}



}//end class TestTIPBDD()