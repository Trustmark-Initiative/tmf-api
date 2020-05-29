package edu.gatech.gtri.trustmark.v1_0.model.expression;


public abstract class ExpressionNode {

	/**
	 * Get the node's number of children.
	 */
	public abstract int getCardinality();
	
	/**
	 * Get i'th child
	 */
	public abstract ExpressionNode getChild(int i);

}
