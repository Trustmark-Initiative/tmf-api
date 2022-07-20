package edu.gatech.gtri.trustmark.v1_0.impl.assessment.el;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.el.FunctionMapper;

import edu.gatech.gtri.trustmark.v1_0.assessment.StepResult;
import edu.gatech.gtri.trustmark.v1_0.impl.assessment.juel.IssuanceCriteriaELContext;

/**
 * EL implementations of issuance criteria functions for ALL or NONE step IDs
 * 
 * @author GTRI Trustmark Team
 *
 */

public class IssuanceCriteriaELAllOrNoneFunctionMapper extends IssuanceCriteriaELFunctionMapperBase {

	private static final Map<String, Method> functionMap = new HashMap<String, Method>();
	private static final Boolean syncVar = false;
	private static IssuanceCriteriaELAllOrNoneFunctionMapper instance = null;

	private IssuanceCriteriaELAllOrNoneFunctionMapper() {

		synchronized (syncVar) {
			if (functionMap.isEmpty()) {
				try {
					functionMap.put(":yes",
							IssuanceCriteriaELAllOrNoneFunctionMapper.class.getMethod(
									"yes", Map.class));
					functionMap.put(":no",
							IssuanceCriteriaELAllOrNoneFunctionMapper.class.getMethod(
									"no", Map.class));
					functionMap.put(":na",
							IssuanceCriteriaELAllOrNoneFunctionMapper.class.getMethod(
									"na", Map.class));

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

	public static IssuanceCriteriaELAllOrNoneFunctionMapper getInstance() {
		if (instance == null)
			instance = new IssuanceCriteriaELAllOrNoneFunctionMapper();
		return instance;
	}

	public static boolean yes(Map<String, List<StepResult>> predicate) {
		for (Map.Entry<String, List<StepResult>> entry : predicate.entrySet()) {
			for (StepResult result : entry.getValue()) {
				if (entry.getKey().toLowerCase(Locale.ROOT).equals(IssuanceCriteriaELContext.ALL_PREDICATE_PARAM)) {
					if (result != StepResult.YES) {
						return false;
					}
				} else { // Add NONE
					if (result == StepResult.YES) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static boolean no(Map<String, List<StepResult>> predicate) {
		for (Map.Entry<String, List<StepResult>> entry : predicate.entrySet()) {
			for (StepResult result : entry.getValue()) {
				if (entry.getKey().toLowerCase(Locale.ROOT).equals(IssuanceCriteriaELContext.ALL_PREDICATE_PARAM)) {
					if (result != StepResult.NO) {
						return false;
					}
				} else { // Add NONE
					if (result == StepResult.NO) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static boolean na(Map<String, List<StepResult>> predicate) {
		for (Map.Entry<String, List<StepResult>> entry : predicate.entrySet()) {
			for (StepResult result : entry.getValue()) {
				if (entry.getKey().toLowerCase(Locale.ROOT).equals(IssuanceCriteriaELContext.ALL_PREDICATE_PARAM)) {
					if (result != StepResult.NA) {
						return false;
					}
				} else { // Add NONE
					if (result == StepResult.NA) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
