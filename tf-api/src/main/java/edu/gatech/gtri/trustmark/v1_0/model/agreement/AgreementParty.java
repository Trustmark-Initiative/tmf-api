package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * An organization that intends to participate in the development and execution of an Agreement.
 *
 * @author Nicholas Saney
 */
public interface AgreementParty extends Comparable<AgreementParty> {
    
    /**
     * The abbreviated name used to reference this party throughout the Agreement.
     */
    public String getAbbreviatedName();
    
    /**
     * The organizational contact information for this Party.
     */
    public Entity getOrganization();
    
    /**
     * The set of responsibilities assigned to this party.
     */
    public SortedSet<AgreementResponsibility> getResponsibilities();
    
    /**
     * The list of signatories from this party in the Agreement.
     */
    public List<AgreementSignatory> getSignatories();
    
}
