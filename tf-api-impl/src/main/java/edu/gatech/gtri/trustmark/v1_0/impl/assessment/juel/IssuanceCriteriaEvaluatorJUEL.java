package edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel;

import java.util.Date;

import javax.el.ExpressionFactory;

import de.odysseus.el.ExpressionFactoryImpl;
import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluation;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluationException;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluator;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.IssuanceCriteriaEvaluationImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.juel.JUELUtilities;

/**
 * Implements a translation from a given issuance criteria string to java EL for
 * evaluation using the Java Unified Expression Language (JUEL) library.
 */
public class IssuanceCriteriaEvaluatorJUEL implements IssuanceCriteriaEvaluator {

	public IssuanceCriteriaEvaluation evaluate(String issuanceCriteria,
			AssessmentResults results)
			throws IssuanceCriteriaEvaluationException {

		try {
			String elStr = JUELUtilities.toELString(issuanceCriteria);

			final ExpressionFactory exprFactory = new ExpressionFactoryImpl();
			IssuanceCriteriaELContext elCtx = new IssuanceCriteriaELContext(
					results, exprFactory);
			Object evaluationResultObj = exprFactory.createValueExpression(
					elCtx, elStr, Boolean.class).getValue(elCtx);
			Boolean evaluationResult = (Boolean) evaluationResultObj;

			IssuanceCriteriaEvaluation evaluation = new IssuanceCriteriaEvaluationImpl(
					new Date(), evaluationResult, null, this, issuanceCriteria);
			return evaluation;

		} catch (Exception e) {
			throw new IssuanceCriteriaEvaluationException(e);
		}
	}

}
