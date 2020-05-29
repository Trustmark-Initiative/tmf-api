package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import java.util.*;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;

/**
 * TODO complete implementation
 * 
 * @author GTRI Trustmark Team
 *
 */
public class TIPEvaluationImpl implements TIPEvaluation {
	//==================================================================================================================
	// Constructors
	//==================================================================================================================
	public TIPEvaluationImpl(){}
	public TIPEvaluationImpl(TrustInteroperabilityProfile tip){
		this.tip = tip;
		this.evaluationDate = Calendar.getInstance().getTime();
	}
	//==================================================================================================================
	// Private Instance Variables
	//==================================================================================================================
	private Date evaluationDate;
	private Boolean isSatisfied;
	private Set<Set<TrustmarkDefinitionRequirement>> satisfactionGap;
	private TrustInteroperabilityProfile tip;
	private Map<TrustInteroperabilityProfile, TIPEvaluation> subTipEvaluations;
	//==================================================================================================================
	// Getters
	//==================================================================================================================
	@Override
	public Date getEvaluationDate() {
		return evaluationDate;
	}
	public Boolean getSatisfied() {
		return isSatisfied;
	}
	@Override
	public Boolean isSatisfied(){
		return getSatisfied();
	}
	@Override
	public Set<Set<TrustmarkDefinitionRequirement>> getSatisfactionGap() {
		if( satisfactionGap == null )
			satisfactionGap = new HashSet<>();
		return satisfactionGap;
	}
	@Override
	public TrustInteroperabilityProfile getTIP() {
		return tip;
	}
	@Override
	public Map<TrustInteroperabilityProfile, TIPEvaluation> getSubTipEvaluations() {
		if( subTipEvaluations == null )
			subTipEvaluations = new HashMap<>();
		return subTipEvaluations;
	}
	//==================================================================================================================
	// Setters
	//==================================================================================================================
	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}
	public void setSatisfied(Boolean satisfied) {
		isSatisfied = satisfied;
	}
	public void setSatisfactionGap(Set<Set<TrustmarkDefinitionRequirement>> satisfactionGap) {
		this.satisfactionGap = satisfactionGap;
	}
	public void setTip(TrustInteroperabilityProfile tip) {
		this.tip = tip;
	}
	public void setSubTipEvaluations(Map<TrustInteroperabilityProfile, TIPEvaluation> subTipEvaluations) {
		this.subTipEvaluations = subTipEvaluations;
	}
	//==================================================================================================================
	// Adder Methods
	//==================================================================================================================
	public void addSatisfactionGap(Set<TrustmarkDefinitionRequirement> singleGap) {
		this.getSatisfactionGap().add(singleGap);
	}
	public void addSubTipEvaluation(TrustInteroperabilityProfile subtip, TIPEvaluation tipEvaluation){
		this.getSubTipEvaluations().put(subtip, tipEvaluation);
	}
	//==================================================================================================================
	// Private Helper Methods
	//==================================================================================================================

	//==================================================================================================================
	// Public Helper Methods
	//==================================================================================================================
	public TIPEvaluation getSubTipEvaluation(TrustInteroperabilityProfile tip){
		return this.getSubTipEvaluations().get(tip);
	}


}/* end TIPEvaluationImpl */