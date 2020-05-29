package edu.gatech.gtri.trustmark.v1_0.model.expression;

public class LogicalBinaryNode extends ExpressionNode  {

	protected final ExpressionNode left;
	protected final ExpressionNode right;
	protected final Operator operator;
	
	public LogicalBinaryNode(ExpressionNode left, ExpressionNode right,
			Operator operator) {

		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	public ExpressionNode getLeftChild() {
		return left;
	}
	
	public ExpressionNode getRightChild() {
		return right;
	}

	@Override
	public int getCardinality() {
		return 2;
	}

	@Override
	public ExpressionNode getChild(int i) {
		return i == 0 ? left : i == 1 ? right : null;
	}
	
	public Operator getOperator() {
		return operator;
	}

	public enum Operator {
		AND,
		OR;
	}

}
