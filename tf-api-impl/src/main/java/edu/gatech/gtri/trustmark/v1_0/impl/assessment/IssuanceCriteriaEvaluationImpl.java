package edu.gatech.gtri.trustmark.v1_0.impl.assessment;

import java.util.Date;
import java.util.Set;

import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluation;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluator;

public class IssuanceCriteriaEvaluationImpl implements IssuanceCriteriaEvaluation {

	private final Date evaluationDate;
	private final Boolean isSatisfied;
	private final Set<AssessmentResults> evaluationGap;
	private final IssuanceCriteriaEvaluator evaluator;
	private final String issuanceCriteria;
	
	public IssuanceCriteriaEvaluationImpl(Date evaluationDate,
			Boolean isSatisfied, Set<AssessmentResults> evaluationGap,
			IssuanceCriteriaEvaluator evaluator, String issuanceCriteria) {

		this.evaluationDate = evaluationDate;
		this.isSatisfied = isSatisfied;
		this.evaluationGap = evaluationGap;
		this.evaluator = evaluator;
		this.issuanceCriteria = issuanceCriteria;
	}

	public Date getEvaluationDate() {
		return evaluationDate;
	}

	public Boolean isSatisfied() {
		return isSatisfied;
	}

	public Set<AssessmentResults> getSatisfactionGap() {
		return evaluationGap;
	}

	public IssuanceCriteriaEvaluator getIssuanceCriteriaEvaluator() {
		return evaluator;
	}

	public String getIssuanceCriteria() {
		return issuanceCriteria;
	}

}
