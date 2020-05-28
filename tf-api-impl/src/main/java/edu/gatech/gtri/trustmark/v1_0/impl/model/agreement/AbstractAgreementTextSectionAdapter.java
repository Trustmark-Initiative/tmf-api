package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AbstractAgreementTextSection;

/**
 * Created by Nicholas on 01/30/2017.
 */
public abstract class AbstractAgreementTextSectionAdapter<T extends AbstractAgreementTextSection<T>>
    extends AbstractOrderedObjectAdapter<T>
    implements AbstractAgreementTextSection<T>
{
    
    ////// Instance Fields //////
    protected String title;
    protected String text;
    
    
    ////// Instance Methods //////
    @Override
    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }
    
    @Override
    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }
    
}
