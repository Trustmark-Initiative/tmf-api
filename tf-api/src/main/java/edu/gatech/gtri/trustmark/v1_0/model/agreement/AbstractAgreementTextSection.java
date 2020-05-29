package edu.gatech.gtri.trustmark.v1_0.model.agreement;

/**
 * A text section in an Agreement.
 *
 * @author Nicholas Saney
 */
public interface AbstractAgreementTextSection<T extends AbstractAgreementTextSection<T>> extends AbstractOrderedObject<T> {
    
    /**
     * The title of this section.
     */
    public String getTitle();
    
    /**
     * The text of this section.
     */
    public String getText();
    
}
