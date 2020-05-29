package edu.gatech.gtri.trustmark.v1_0.model.expression;

public class LogicalUnaryNode extends ExpressionNode {

	protected final ExpressionNode child;
	protected final Operator operator;

	public LogicalUnaryNode(ExpressionNode child, Operator operator) {
		this.child = child;
		this.operator = operator;
	}
	
	public ExpressionNode getChildExpression() {
		return child;
	}

	@Override
	public int getCardinality() {
		return 1;
	}

	@Override
	public ExpressionNode getChild(int i) {
		return i == 0 ? child : null;
	}
	
	public Operator getOperator() {
		return operator;
	}

	public enum Operator {
		NOT;
	}

}
