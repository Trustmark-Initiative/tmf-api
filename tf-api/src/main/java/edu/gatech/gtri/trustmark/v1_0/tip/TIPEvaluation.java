package edu.gatech.gtri.trustmark.v1_0.tip;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;

/**
 * The result of a TIP evaluation event.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TIPEvaluation {

	/**
	 * Returns the date on which the evaluation occurred.
	 */
	public Date getEvaluationDate();

	/**
	 * Returns whether the evaluation input satisfied the TIP.
	 */
	public Boolean isSatisfied();

	/**
	 * Returns the gap between the evaluation input and TIP satisfaction. If
	 * null is returned, then no gap was calculated. If the returned set is
	 * empty, then there is no gap and the evaluation input satisfies the TIP.
	 * Otherwise, each member of the returned set is a non-empty set of
	 * {@link TrustmarkDefinitionRequirement} instances that when combined with
	 * the original evaluation input, will satisfy this TIP (and hence sub-TIPs as well).
	 * <br/><br/>
	 * @return The evaluation gap between the evaluation input and TIP
	 *         satisfaction as a Set of Sets.  Note that this set will recursively descend into all sub-tips.
	 */
	public Set<Set<TrustmarkDefinitionRequirement>> getSatisfactionGap();

	/**
	 * Returns the {@link TrustInteroperabilityProfile} that was evaluated.
	 */
	public TrustInteroperabilityProfile getTIP();

	/**
	 * In the event that this TIP is comprised of Sub TIPs, this method will return all of the immediate sub-tips along
	 * with their evaluation, which lead to this evaluation.  If the TIP contains no Sub-TIPs, then this method will
	 * return an empty map.
     */
	public Map<TrustInteroperabilityProfile, TIPEvaluation> getSubTipEvaluations();




}
