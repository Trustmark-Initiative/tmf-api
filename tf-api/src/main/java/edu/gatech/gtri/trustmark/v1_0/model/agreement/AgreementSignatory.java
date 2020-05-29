package edu.gatech.gtri.trustmark.v1_0.model.agreement;

/**
 * Information about a single person who will sign the Agreement on behalf of a Party, to
 * indicate the Party's legal acceptance of and consent to the Agreement.
 *
 * @author Nicholas Saney
 */
public interface AgreementSignatory {
    
    /**
     * The name of the signatory.
     */
    public String getIndividualName();
    
    /**
     * The title of the signatory as relevant to the Party they represent.
     */
    public String getIndividualTitle();
    
    /**
     * The division of the Party that the signatory represents (may be null or empty).
     */
    public String getDivisionName();
    
    /**
     * Any auxiliary text to be included under the signatory's name, title, and division.
     */
    public String getAuxiliaryText();
    
}
