package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.HasSource;

import java.net.URI;
import java.util.List;

/**
 * A standalone artifact that allows for easier reuse of Responsibilities
 * when creating Agreements.
 */
public interface AgreementResponsibilityTemplate extends HasSource, Comparable<AgreementResponsibilityTemplate> {
    
    /**
     * The name of this templatized responsibility.
     */
    public String getName();
    
    /**
     * The category of this templatized responsibility.
     */
    public String getCategory();
    
    /**
     * The definition of this templatized responsibility.
     */
    public String getDefinition();
    
    /**
     * The identifiers of the TIPs in this templatized responsibility.
     */
    public List<URI> getTipIdentifiers();
    
}
