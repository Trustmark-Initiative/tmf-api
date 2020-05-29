package edu.gatech.gtri.trustmark.v1_0.model.expression;

public class NestedNode extends ExpressionNode {

	protected final ExpressionNode child;

	public NestedNode(ExpressionNode child) {
		this.child = child;
	}
	
	public ExpressionNode getNestedExpressionNode() {
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

}
