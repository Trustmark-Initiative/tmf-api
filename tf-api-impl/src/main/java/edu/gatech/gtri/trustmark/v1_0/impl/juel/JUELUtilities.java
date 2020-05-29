package edu.gatech.gtri.trustmark.v1_0.impl.juel;

public class JUELUtilities {

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

}
