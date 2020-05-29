package edu.gatech.gtri.trustmark.v1_0.assessment;

import java.util.Date;
import java.util.Set;

/**
 * The result of an issuance criteria evaluation event.
 * 
 * @author GTRI Trustmark Team
 */
public interface IssuanceCriteriaEvaluation {

	/**
	 * Returns the date on which the evaluation occurred.
	 * 
	 * @return The date on which the evaluation occurred
	 */
	public Date getEvaluationDate();

	/**
	 * Returns whether the evaluation input satisfied the evaluated issuance
	 * criteria.
	 * 
	 * @return Whether the evaluation input satisfied the evaluated issuance
	 *         criteria
	 */
	public Boolean isSatisfied();

	/**
	 * Returns the gap between the evaluation input and issuance criteria
	 * satisfaction. If null is returned, then no gap was calculated. If the
	 * returned set is empty, then there is no gap and the input assessment
	 * results satisfies the issuance criteria. Otherwise, each member of the
	 * returned set is an {@link AssessmentResults} instance that, if applied
	 * along with the original evaluation input, will satisfy the issuance
	 * criteria .
	 * 
	 * @return The evaluation gap between the input assessment results and
	 *         issuance criteria satisfaction
	 */
	public Set<AssessmentResults> getSatisfactionGap();

	/**
	 * Returns the issuance criteria that was evaluated.
	 * 
	 * @return The issuance criteria that was evaluated
	 */
	public String getIssuanceCriteria();

}
