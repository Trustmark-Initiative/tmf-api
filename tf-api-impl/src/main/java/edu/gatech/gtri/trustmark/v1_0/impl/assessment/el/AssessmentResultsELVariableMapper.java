package edu.gatech.gtri.trustmark.v1_0.impl.assessment.el;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.assessment.StepResult;

public class AssessmentResultsELVariableMapper extends VariableMapper {

	protected final AssessmentResults results;
	protected final ExpressionFactory exprFactory;
	
	public AssessmentResultsELVariableMapper(AssessmentResults results, ExpressionFactory exprFactory) {
		this.results = results;
		this.exprFactory = exprFactory;
	}
	
	@Override
	public ValueExpression resolveVariable(String name) {
		StepResult result = results.get(name);
		ValueExpression ve = exprFactory.createValueExpression(result, StepResult.class);
		return ve;
	}

	@Override
	public ValueExpression setVariable(String name, ValueExpression expr) {
		throw new UnsupportedOperationException("PURPOSEFULLY NOT IMPLEMENTED");
	}

}
