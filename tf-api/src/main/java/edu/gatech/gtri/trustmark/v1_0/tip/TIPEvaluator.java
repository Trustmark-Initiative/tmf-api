package edu.gatech.gtri.trustmark.v1_0.tip;

import java.util.Set;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;

/**
 * Can evaluate a TIP against a set of {@link Trustmark} instances or against a
 * set of {@link TrustmarkDefinitionRequirement} instances. Evaluation can
 * be a check to see if the input satisfies the TIP. Or evaluation can include
 * the calculation of the gap from TIP satisfaction.
 * 
 * @author GTRI Trustmark Team, TrustmarkFeedback@gtri.gatech.edu
 *
 */
public interface TIPEvaluator {

	/**
	 * Evaluates the supplied TIP against the supplied set of trustmarks.
	 * 
	 * @param tip
	 *            The TIP to evaluate. Must not be null.
	 * @param trustmarks
	 *            The set of trustmarks to evaluate against. If null, then this
	 *            method assumes there is no input to evaluate against.
	 * @return The evaluation result
	 * @see TIPEvaluator#evaluateTIPAgainstTDRequirements(TrustInteroperabilityProfile, Set)
	 */
	TIPEvaluation evaluateTIPAgainstTrustmarks(
			TrustInteroperabilityProfile tip, Set<Trustmark> trustmarks)
			throws TIPEvaluationException;

	/**
	 * Evaluates the supplied TIP against the supplied set of TD requirements.
	 * This method is intended to support hypothetical scenarios.
	 * 
	 * @param tip
	 *            The TIP to evaluate. Must not be null.
	 * @param tdRequirements
	 *            The set of TD requirements to evaluate against. If null, then
	 *            this method assumes there is no input to evaluate against.
	 * @return The evaluation result
	 * @see TIPEvaluator#evaluateTIPAgainstTrustmarks(TrustInteroperabilityProfile, Set)
	 */
	TIPEvaluation evaluateTIPAgainstTDRequirements(
			TrustInteroperabilityProfile tip,
			Set<TrustmarkDefinitionRequirement> tdRequirements)
			throws TIPEvaluationException;

	/**
	 * Returns whether this evaluator calculates satisfaction gaps when
	 * performing evaluations.
	 */
	Boolean calculatesSatisfactionGap();
}
