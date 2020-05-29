package edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

import de.odysseus.el.util.SimpleResolver;
import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.el.AssessmentResultsELVariableMapper;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.el.IssuanceCriteriaELFunctionMapper;

public class IssuanceCriteriaELContext extends ELContext {

	private final IssuanceCriteriaELFunctionMapper functionMapper;
	private final ELResolver resolver = new SimpleResolver();
	private final AssessmentResultsELVariableMapper variableMapper;

	public IssuanceCriteriaELContext(AssessmentResults results, ExpressionFactory exprFactory) {
		variableMapper = new AssessmentResultsELVariableMapper(results, exprFactory);
		functionMapper = IssuanceCriteriaELFunctionMapper.getInstance();
	}

	@Override
	public ELResolver getELResolver() {
		return resolver;
	}

	@Override
	public FunctionMapper getFunctionMapper() {
		return functionMapper;
	}

	@Override
	public VariableMapper getVariableMapper() {
		return variableMapper;
	}

}