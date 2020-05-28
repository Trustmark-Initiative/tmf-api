package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import java.util.List;

/**
 * An object that is the parent of a styled and ordered list of legal sections.
 *
 * @author Nicholas Saney
 */
public interface AbstractAgreementLegalSectionParent {
    
    /**
     * The type of list item style to apply to the contained legal sections.
     */
    public ListStyleType getListStyleType();
    
    /**
     * An ordered listing of the contained legal sections.
     */
    public List<AgreementLegalSection> getLegalSections();
    
}
