package edu.gatech.gtri.trustmark.v1_0.model.expression;


public class IssuanceCriteriaFunctionNode extends ExpressionNode {

	/**
	 * May be {@link IdentifierNode}, {@link IssuanceCriteriaRange}, or {@link IssuanceCriteriaSequqence}.
	 */
	protected final Object parameter;
	
	protected final Class<?> parameterType;
	
	public IssuanceCriteriaFunctionNode(IdentifierNode parameter) {
		this.parameter = parameter;
		this.parameterType = IdentifierNode.class;
	}
	
	public IssuanceCriteriaFunctionNode(IssuanceCriteriaRange parameter) {
		this.parameter = parameter;
		this.parameterType = IssuanceCriteriaRange.class;
	}
	
	public IssuanceCriteriaFunctionNode(IssuanceCriteriaSequqence parameter) {
		this.parameter = parameter;
		this.parameterType = IssuanceCriteriaSequqence.class;
	}
	
	@Override
	public int getCardinality() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ExpressionNode getChild(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object getParameter() {
		return parameter;
	}
	
	public Class<?> getParameterType() {
		return parameterType;
	}

	public static enum IssuanceCriteriaFunction {
		YES,
		NO,
		NA
	}
}
