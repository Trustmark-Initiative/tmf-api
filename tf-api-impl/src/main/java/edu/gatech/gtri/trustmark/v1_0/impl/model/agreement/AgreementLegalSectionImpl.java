package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementLegalSection;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.ListStyleType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AgreementLegalSectionImpl
    extends AbstractAgreementTextSectionAdapter<AgreementLegalSection>
    implements AgreementLegalSection
{
    
    ////// Instance Fields //////
    protected ListStyleType listStyleType;
    protected List<AgreementLegalSection> legalSections;
    
    
    ////// Instance Methods //////
    @Override
    public ListStyleType getListStyleType() { return this.listStyleType; }
    public void setListStyleType(ListStyleType listStyleType) { this.listStyleType = listStyleType; }
    
    @Override
    public List<AgreementLegalSection> getLegalSections() {
        if (this.legalSections == null) { this.legalSections = new ArrayList<>(); }
        return this.legalSections;
    }
    public void setLegalSections(Collection<? extends AgreementLegalSection> legalSections) {
        this.getLegalSections().clear();
        this.getLegalSections().addAll(legalSections);
    }
    public void addToLegalSections(AgreementLegalSection legalSection) {
        this.getLegalSections().add(legalSection);
    }
    
}
