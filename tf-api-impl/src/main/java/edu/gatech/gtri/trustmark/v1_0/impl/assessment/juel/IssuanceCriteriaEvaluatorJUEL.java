package edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import de.odysseus.el.ExpressionFactoryImpl;
import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluation;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluationException;
import edu.gatech.gtri.trustmark.v1_0.assessment.IssuanceCriteriaEvaluator;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.AssessmentResultsImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.IssuanceCriteriaEvaluationImpl;

/**
 * Implements a translation from a given issuance criteria string to java EL for
 * evaluation using the Java Unified Expression Language (JUEL) library.
 */
public class IssuanceCriteriaEvaluatorJUEL implements IssuanceCriteriaEvaluator {

	private static final String SINGLE_STEP_ID_REGEX_STRING = "(?<=\\{).+?(?=\\})";
	private static final String STEP_ID_INSIDE_PARENS_REGEX_STRING = "(?<=\\().+?(?=\\))";
	private static final String ELLIPSIS_REGEX_STRING = "\\s*(?:…|\\.{3})\\s*";


	public static String toELString(String issuanceCriteria) {
		String elStr = removeUnnecessaryParens(issuanceCriteria);
		elStr = "${" + elStr + "}";
		return elStr;
	}

	/**
	 * Removes unnecessary parentheses from the supplied EL expression string.
	 * This is needed because the JUEL library chokes on unnecessary parens. An
	 * example of unnecessary parens is "${((rainy or cloudy))}".
	 *
	 * @param str
	 *            Input EL expression string
	 * @return the EL expression string with unnecessary parentheses removed.
	 */
	private static String removeUnnecessaryParens(String str) {
		int i = 0;
		while (true) {
			// if there are less than 4 characters left to scan, then we're done
			if (i >= str.length() - 4)
				break;

			// match double open parens
			if (str.charAt(i) == '(' && str.charAt(i + 1) == '(') {
				// count open and close parens
				// if count ends with double close parens then remove a set of
				// those parens
				int count = 2;

				boolean foundMatch = false;
				// keep i at its position
				int j = i + 2;
				for (; j < str.length(); j++) {
					// increment count on open paren
					// decrement count on close paren
					// if second of double open parens is closed by a paren not
					// in a double close paren, then break
					// break when count == 0
					// look for double close paren

					if (str.charAt(j) == ')') {
						count--;
						if (count == 0)
							break;
						if (count == 1) {
							if (j < str.length() - 1
									&& str.charAt(j + 1) == ')') {
								foundMatch = true;
								break;
							} else
								break;
						}
					} else if (str.charAt(j) == '(')
						count++;
				}

				if (foundMatch) {
					str = stringRemoveCharAt(str, j);
					str = stringRemoveCharAt(str, i);
					i--;
				}
			}

			i++;
		}

		return str;
	}

	private static String stringRemoveCharAt(String str, int toRemove) {
		str = str.substring(0, toRemove)
				+ str.substring(toRemove + 1, str.length());
		return str;
	}

	private String replaceEllipsisWIthStepIdSequence(String expression, AssessmentResults assessmentResults) {

		if (expression.contains("...")) {
			List<String> results = new ArrayList<>(assessmentResults.keySet());

			// find parameters inside parens
			Matcher matcher = Pattern.compile(STEP_ID_INSIDE_PARENS_REGEX_STRING).matcher(expression);
			while (matcher.find()) {
				String matched = matcher.group();

				if (matched.contains("...")) {
					// get the 1st and last step id in the range
					List<String> steps =
							Stream.of(matched.split(ELLIPSIS_REGEX_STRING))
									.collect(Collectors.toList());

					int start = results.indexOf(steps.get(0));
					int end = results.indexOf(steps.get(1));

					StringBuilder sb = new StringBuilder();
					results.forEach(result -> {
						int elementIndex = results.indexOf(result);
						if (elementIndex >= start && elementIndex <= end) {
							sb.append(result);
						}
						if (elementIndex >= start && elementIndex < end) {
							sb.append(",");
						}
					});

					expression = expression.replace(matcher.group(), sb.toString());
				}
			}
		}

		return expression;
	}

	private String addStepIdFunction(String expression, AssessmentResults assessmentResults) {

		if (isStepResultId(expression, assessmentResults)) {

			Matcher matcher = Pattern.compile(SINGLE_STEP_ID_REGEX_STRING).matcher(expression);

			if (matcher.find()) {

				String stepId = "yes(" + matcher.group() + ")";

				expression = expression.replace(matcher.group(), stepId);
			}
		}

		return expression;
	}

	private boolean isStepResultId(String expression, AssessmentResults results) {

		Matcher matcher = Pattern.compile(SINGLE_STEP_ID_REGEX_STRING).matcher(expression);
		if (matcher.find()) {
			// if the expression is a step id
			if (results.containsKey(matcher.group())) {
				return true;
			}
		}

		return false;
	}

	private String replaceMinusWithUnderscoreInExpression(String expression) {

		String replaced = expression.replace("-", "_");

		return replaced;
	}

	private AssessmentResults replaceMinusWithUnderscoreInStepResults(AssessmentResults results) {

		AssessmentResults tempResults = new AssessmentResultsImpl();
		results.forEach((key, value) ->  tempResults.put(replaceMinusWithUnderscoreInExpression(key), value));

		return tempResults;
	}

	public IssuanceCriteriaEvaluation evaluate(String issuanceCriteria, AssessmentResults results)
			throws IssuanceCriteriaEvaluationException {

		try {
			// Step ids in Issuance criteria expressions and in step results can potentially contain hiphens ("-") which
			// is by default interpreted as an operator by JUEL. Replace all hiphens with underscores in both the
			// issuance criteria expression and in the assessment results step ids.
			issuanceCriteria = replaceMinusWithUnderscoreInExpression(issuanceCriteria);
			results = replaceMinusWithUnderscoreInStepResults(results);


			String elStr = toELString(issuanceCriteria);

			// for no-function expressions, add yes function
			elStr = addStepIdFunction(elStr, results);

			// for range of step ids (ellipsis) replace the ellipsis string (not understood by JUEL)
			// with the sequence of step ids inside the range
			elStr = replaceEllipsisWIthStepIdSequence(elStr, results);

			final ExpressionFactory exprFactory = new ExpressionFactoryImpl();

			// pass the expression to select the right function and possibly avariable mapper(s)
			IssuanceCriteriaELContext elCtx = new IssuanceCriteriaELContext(elStr, results, exprFactory);

			ValueExpression valueExpression = exprFactory.createValueExpression(elCtx, elStr, Boolean.class);

			Object evaluationResultObj = valueExpression.getValue(elCtx);

			Boolean evaluationResult = (Boolean) evaluationResultObj;

			IssuanceCriteriaEvaluation evaluation = new IssuanceCriteriaEvaluationImpl(
					new Date(), evaluationResult, null, this, issuanceCriteria);

			return evaluation;

		} catch (Exception e) {
			throw new IssuanceCriteriaEvaluationException(e);
		}
	}
}
