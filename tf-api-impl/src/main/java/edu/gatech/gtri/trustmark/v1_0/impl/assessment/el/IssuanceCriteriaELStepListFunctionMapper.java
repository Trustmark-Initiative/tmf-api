package edu.gatech.gtri.trustmark.v1_0.impl.assessment.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.FunctionMapper;

import edu.gatech.gtri.trustmark.v1_0.assessment.StepResult;

/**
 * EL implementations of issuance criteria functions for a sequence of step ids
 * 
 * @author GTRI Trustmark Team
 *
 */
public class IssuanceCriteriaELStepListFunctionMapper extends IssuanceCriteriaELFunctionMapperBase {

	private static final Map<String, Method> functionMap = new HashMap<String, Method>();;
	private static final Boolean syncVar = false;
	private static IssuanceCriteriaELStepListFunctionMapper instance = null;

	private IssuanceCriteriaELStepListFunctionMapper() {
		synchronized (syncVar) {
			if (functionMap.isEmpty()) {
				try {
					functionMap.put(":yes",
							IssuanceCriteriaELStepListFunctionMapper.class.getMethod(
									"yes", StepResult[].class));
					functionMap.put(":no",
							IssuanceCriteriaELStepListFunctionMapper.class.getMethod(
									"no", StepResult[].class));
					functionMap.put(":na",
							IssuanceCriteriaELStepListFunctionMapper.class.getMethod(
									"na", StepResult[].class));

				} catch (NoSuchMethodException nsme) {
					// this should never happen since all methods that implement
					// functions are defined in this class
					throw new RuntimeException(nsme);
				}
			}
		}
	}

	protected Map<String, Method> functionMap() {
		return this.functionMap;
	}

	public static IssuanceCriteriaELStepListFunctionMapper getInstance() {
		if (instance == null)
			instance = new IssuanceCriteriaELStepListFunctionMapper();
		return instance;
	}

	/**
	 * Returns true if each of the supplied assessment step result is YES.
	 * 
	 * @param results
	 *            The assessment step result to check.
	 * @return true if the supplied assessment step result is YES.
	 */
	public static boolean yes(StepResult... results) {
		for (StepResult result : results) {
			if (result != StepResult.YES) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if each of the supplied assessment step result is NO.
	 * 
	 * @param results
	 *            The assessment step result to check.
	 * @return true if the supplied assessment step result is NO.
	 */
	public static boolean no(StepResult... results) {
		for (StepResult result : results) {
			if (result != StepResult.NO) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if each of the supplied assessment step result is NA.
	 * 
	 * @param results
	 *            The assessment step result to check.
	 * @return true if the supplied assessment step result is NA.
	 */
	public static boolean na(StepResult... results) {
		for (StepResult result : results) {
			if (result != StepResult.NA) {
				return false;
			}
		}

		return true;
	}
}
