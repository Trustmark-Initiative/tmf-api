package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.util.*;

/**
 * A structured document developed by two or more collaborating Parties for
 * the purpose of meeting shared business objectives through legal execution.
 *
 * @author Nicholas Saney
 */
public interface Agreement extends HasSource, TrustmarkFrameworkIdentifiedObject, AbstractAgreementLegalSectionParent {
    
    /**
     * The title of this Agreement.
     * This should be a synonym for TrustmarkFrameworkIdentifiedObject#getName().
     */
    public String getTitle();
    
    /**
     * The timestamp for when this Agreement was created.
     */
    public Date getCreationDateTime();
    
    /**
     * The timestamp for when this Agreement becomes effective.
     * If null, then the effective date should be specified in the legal sections of this Agreement.
     */
    public Date getEffectiveDateTime();
    
    /**
     * The timestamp for when this Agreement becomes terminated.
     * If null, then the termination date should be specified in the legal sections of this Agreement.
     */
    public Date getTerminationDateTime();
    
    /**
     * An ordered listing of the non-binding sections of this Agreement.
     */
    public List<AgreementNonBindingSection> getNonBindingSections();
    
    /**
     * The set of defined terms of this Agreement.
     */
    public SortedSet<Term> getTerms();
    
    /**
     * The set of collaborating Parties of this Agreement.
     */
    public SortedSet<AgreementParty> getParties();
    
    /**
     * The set of Responsibilities to be assigned to the various Parties of this Agreement.
     */
    public SortedSet<AgreementResponsibility> getResponsibilities();
    
    /**
     * The mapping from Responsibility to Parties.
     */
    public Map<AgreementResponsibility, Collection<AgreementParty>> getMappingFromResponsibilityToParties();
    
    /**
     * The text which will be shown at the start of the signature page of this Agreement when it is exported as PDF.
     */
    public String getSignaturePageText();
    
    /**
     * An ordered listing of the attachments in this Agreement.
     */
    public List<AgreementAttachment> getAttachments();
    
}
