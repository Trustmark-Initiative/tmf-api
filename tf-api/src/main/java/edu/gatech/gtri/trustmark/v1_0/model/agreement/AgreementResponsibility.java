package edu.gatech.gtri.trustmark.v1_0.model.agreement;

/**
 * A well defined function to be fulled by a party within the context of the agreement,
 * and explicit sets of technical requirements for the defined function.
 *
 * @author Nicholas Saney
 */
public interface AgreementResponsibility extends AbstractSupplementedTIPSnapshotParent, Comparable<AgreementResponsibility> {
    
    /**
     * The identifier for this responsibility.
     */
    public String getIdentifier();
    
    /**
     * The name for this responsibility.
     */
    public String getName();
    
    /**
     * The category for this responsibility.
     */
    public String getCategory();
    
    /**
     * The definition for this responsibility.
     */
    public String getDefinition();
    
}
