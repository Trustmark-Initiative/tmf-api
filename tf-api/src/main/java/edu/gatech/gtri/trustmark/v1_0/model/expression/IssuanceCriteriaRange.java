package edu.gatech.gtri.trustmark.v1_0.model.expression;

public class IssuanceCriteriaRange {

	protected final IdentifierNode beginNode;
	protected final IdentifierNode endNode;
	
	public IssuanceCriteriaRange(IdentifierNode beginNode, IdentifierNode endNode) {
		this.beginNode = beginNode;
		this.endNode = endNode;
	}

	public IdentifierNode getBeginIdentifierNode() {
		return beginNode;
	}
	
	public IdentifierNode getEndIdentifierNode() {
		return endNode;
	}
}
