package edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

import de.odysseus.el.util.SimpleResolver;
import edu.gatech.gtri.trustmark.v1_0.assessment.AssessmentResults;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.el.*;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssuanceCriteriaELContext extends ELContext {
	public static final String ALL_PREDICATE_PARAM = "all";
	public static final String NONE_PREDICATE_PARAM = "none";

	public static final String ALL_PREDICATE_PARAM_PARENS = "(all)";
	public static final String NONE_PREDICATE_PARAM_PARENS = "(none)";

	private static final String WHITESPACE = "\\s";
	private static final String EMPTY_STRING = "";

	private final IssuanceCriteriaELFunctionMapperBase functionMapper;
	private final ELResolver resolver = new SimpleResolver();
	private final AssessmentResultsELVariableMapper variableMapper;

	public IssuanceCriteriaELContext(String expression, AssessmentResults results, ExpressionFactory exprFactory) {
		variableMapper = new AssessmentResultsELVariableMapper(results, exprFactory);

		if (expression.replaceAll(WHITESPACE, EMPTY_STRING).toLowerCase(Locale.ROOT).contains(ALL_PREDICATE_PARAM_PARENS) ||
				expression.replaceAll(WHITESPACE, EMPTY_STRING).toLowerCase().contains(NONE_PREDICATE_PARAM_PARENS)) {
			functionMapper = IssuanceCriteriaELAllOrNoneFunctionMapper.getInstance();
		} else if (expression.contains(",")) {
			functionMapper = IssuanceCriteriaELStepListFunctionMapper.getInstance();
		} else if (expression.contains("...")) {
			functionMapper = IssuanceCriteriaELStepListFunctionMapper.getInstance();
		} else {
			functionMapper = IssuanceCriteriaELFunctionMapper.getInstance();
		}
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