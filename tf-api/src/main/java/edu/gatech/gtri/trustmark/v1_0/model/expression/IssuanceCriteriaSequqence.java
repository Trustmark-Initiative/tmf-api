package edu.gatech.gtri.trustmark.v1_0.model.expression;

import java.util.Collections;
import java.util.List;

public class IssuanceCriteriaSequqence {

	protected final List<IdentifierNode> sequence;
	
	public IssuanceCriteriaSequqence(List<IdentifierNode> sequence) {
		this.sequence = Collections.unmodifiableList(sequence);
	}

	public List<IdentifierNode> getIdentifierSequence() {
		return sequence;
	}

}
