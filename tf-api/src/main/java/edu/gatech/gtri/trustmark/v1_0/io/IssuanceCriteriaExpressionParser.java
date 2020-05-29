package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.expression.ExpressionTree;

public interface IssuanceCriteriaExpressionParser {

	public ExpressionTree parseIssuanceCriteriaExpressionString(String issuanceCriteriaExpressionString)
	throws ParseException;


}
