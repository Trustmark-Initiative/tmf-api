package edu.gatech.gtri.trustmark.v1_0.impl.assessment.el;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.assessment.StepResult;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel.IssuanceCriteriaELContext;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssessmentResultsELVariableMapper extends VariableMapper {

	protected final AssessmentResults results;
	protected final ExpressionFactory exprFactory;
	
	public AssessmentResultsELVariableMapper(AssessmentResults results, ExpressionFactory exprFactory) {
		this.results = results;
		this.exprFactory = exprFactory;
	}

	@Override
	public ValueExpression resolveVariable(String name) {
		ValueExpression ve = null;
		if (name.toLowerCase(Locale.ROOT).equals(IssuanceCriteriaELContext.ALL_PREDICATE_PARAM) ||
				name.toLowerCase(Locale.ROOT).equals(IssuanceCriteriaELContext.NONE_PREDICATE_PARAM)) {
			List<StepResult> stepResults = new ArrayList<StepResult>(results.values());
			Map<String, List<StepResult>> predicate = new HashMap<>();
			predicate.put(name, stepResults);
			ve = exprFactory.createValueExpression(predicate, Map.class);
		} else if (name.contains("...")) {
			List<String> steps =
					Stream.of(name.split("\\\\s*(?:…|\\\\.{3})\\\\s*"))
							.collect(Collectors.toList());
			List<StepResult> stepResults = new ArrayList<>();

			steps.forEach(step -> {
				StepResult result = results.get(step);
				stepResults.add(result);
			});

			ve = exprFactory.createValueExpression(stepResults.toArray(), StepResult[].class);
		} else {
			// This parameter must match the param in the predicate
			StepResult result = results.get(name);
			ve = exprFactory.createValueExpression(result, StepResult.class);
		}
		return ve;
	}

	@Override
	public ValueExpression setVariable(String name, ValueExpression expr) {
		throw new UnsupportedOperationException("PURPOSEFULLY NOT IMPLEMENTED");
	}

}
