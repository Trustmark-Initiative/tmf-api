package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementSignatory;

/**
 * Created by Nicholas Saney on 4/5/17.
 */
public class AgreementSignatoryImpl implements AgreementSignatory {
    
    ////// Instance Fields //////
    protected String individualName;
    protected String individualTitle;
    protected String divisionName;
    protected String auxiliaryText;
    
    ////// Instance Methods //////
    @Override
    public String getIndividualName() { return this.individualName; }
    public void setIndividualName(String individualName) { this.individualName = individualName; }
    
    @Override
    public String getIndividualTitle() { return this.individualTitle; }
    public void setIndividualTitle(String individualTitle) { this.individualTitle = individualTitle; }
    
    @Override
    public String getDivisionName() { return this.divisionName; }
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }
    
    @Override
    public String getAuxiliaryText() { return this.auxiliaryText; }
    public void setAuxiliaryText(String auxiliaryText) { this.auxiliaryText = auxiliaryText; }
    
}
