package edu.gatech.gtri.trustmark.v1_0.model.expression;

public class IdentifierNode extends ExpressionNode {

	protected final String identifier;
	
	public IdentifierNode(String identifier) {
		this.identifier = identifier;
	}
	
	@Override
	public int getCardinality() {
		return 0;
	}

	@Override
	public ExpressionNode getChild(int i) {
		return null;
	}
	
	public String getIdentifier() {
		return identifier;
	}

}
