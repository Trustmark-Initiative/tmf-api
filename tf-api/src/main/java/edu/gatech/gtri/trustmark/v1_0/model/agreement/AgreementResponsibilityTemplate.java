package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import java.net.URI;
import java.util.List;

/**
 * A standalone artifact that allows for easier reuse of Responsibilities when
 * creating Agreements.
 */
public interface AgreementResponsibilityTemplate extends Comparable<AgreementResponsibilityTemplate> {

    /**
     * The name of this templatized responsibility.
     */
    String getName();

    /**
     * The category of this templatized responsibility.
     */
    String getCategory();

    /**
     * The definition of this templatized responsibility.
     */
    String getDefinition();

    /**
     * The identifiers of the TIPs in this templatized responsibility.
     */
    List<URI> getTipIdentifiers();
}
