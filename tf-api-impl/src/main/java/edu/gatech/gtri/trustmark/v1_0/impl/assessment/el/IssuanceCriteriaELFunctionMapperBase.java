package edu.gatech.gtri.trustmark.v1_0.impl.assessment.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.FunctionMapper;

import edu.gatech.gtri.trustmark.v1_0.assessment.StepResult;

/**
 * EL implementations of issuance criteria functions
 * 
 * @author GTRI Trustmark Team
 *
 */
public abstract class IssuanceCriteriaELFunctionMapperBase extends FunctionMapper {

	public IssuanceCriteriaELFunctionMapperBase() {
	}

	protected abstract Map<String, Method> functionMap();

	@Override
	public Method resolveFunction(String prefix, String localName) {
		return functionMap().get(prefix + ":" + localName);
	}
}
