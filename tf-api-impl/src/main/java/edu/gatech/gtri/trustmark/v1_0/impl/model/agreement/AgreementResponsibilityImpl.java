package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibility;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AgreementResponsibilityImpl extends AbstractSupplementedTIPSnapshotParentAdapter implements AgreementResponsibility {
    
    ////// Instance Fields //////
    protected String identifier;
    protected String name;
    protected String category;
    protected String definition;
    
    
    ////// Instance Methods //////
    @Override
    public int compareTo(AgreementResponsibility that) {
        if (that == null) { return -1; }
        String thisName = this.getName();
        String thatName = that.getName();
        return thisName.compareToIgnoreCase(thatName);
    }
    
    @Override
    public String getIdentifier() { return this.identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
    
    @Override
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    
    @Override
    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }
    
    @Override
    public String getDefinition() { return this.definition; }
    public void setDefinition(String definition) { this.definition = definition; }
}
