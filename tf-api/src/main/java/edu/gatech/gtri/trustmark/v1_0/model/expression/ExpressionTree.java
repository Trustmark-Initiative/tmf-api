package edu.gatech.gtri.trustmark.v1_0.model.expression;

public class ExpressionTree {

	protected final ExpressionNode root;
	
	public ExpressionTree(ExpressionNode root) {
		this.root = root;
	}
	
	public ExpressionNode getRoot() {
		return root;
	}
}
