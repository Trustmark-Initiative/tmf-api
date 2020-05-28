package edu.gatech.gtri.trustmark.v1_0.impl.assessment.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.el.FunctionMapper;

import edu.gatech.gtri.trustmark.v1_0.assessment.StepResult;

/**
 * EL implementations of issuance criteria functions
 * 
 * @author GTRI Trustmark Team
 *
 */
public class IssuanceCriteriaELFunctionMapper extends FunctionMapper {

	private static final Map<String, Method> functionMap = new HashMap<String, Method>();;
	private static final Boolean syncVar = false;
	private static IssuanceCriteriaELFunctionMapper instance = null;

	private IssuanceCriteriaELFunctionMapper() {
		synchronized (syncVar) {
			if (functionMap.isEmpty()) {
				try {
					functionMap.put(":yes",
							IssuanceCriteriaELFunctionMapper.class.getMethod(
									"yes", StepResult.class));
					functionMap.put(":no",
							IssuanceCriteriaELFunctionMapper.class.getMethod(
									"no", StepResult.class));
					functionMap.put(":na",
							IssuanceCriteriaELFunctionMapper.class.getMethod(
									"na", StepResult.class));
				} catch (NoSuchMethodException nsme) {
					// this should never happen since all methods that implement
					// functions are defined in this class
					throw new RuntimeException(nsme);
				}
			}
		}
	}

	public static IssuanceCriteriaELFunctionMapper getInstance() {
		if (instance == null)
			instance = new IssuanceCriteriaELFunctionMapper();
		return instance;
	}

	/**
	 * Returns true if the supplied assessment step result is YES.
	 * 
	 * @param result
	 *            The assessment step result to check.
	 * @return true if the supplied assessment step result is YES.
	 */
	public static boolean yes(StepResult result) {
		if (result.equals(StepResult.YES))
			return true;
		return false;
	}

	/**
	 * Returns true if the supplied assessment step result is NO.
	 * 
	 * @param result
	 *            The assessment step result to check.
	 * @return true if the supplied assessment step result is NO.
	 */
	public static boolean no(StepResult result) {
		if (result.equals(StepResult.NO))
			return true;
		return false;
	}

	/**
	 * Returns true if the supplied assessment step result is NA.
	 * 
	 * @param result
	 *            The assessment step result to check.
	 * @return true if the supplied assessment step result is NA.
	 */
	public static boolean na(StepResult result) {
		if (result.equals(StepResult.NA))
			return true;
		return false;
	}

	@Override
	public Method resolveFunction(String prefix, String localName) {
		return functionMap.get(prefix + ":" + localName);
	}
}
