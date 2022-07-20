package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.List;

/**
 * Implementations represent a Trustmark Definition requirement: a reference to
 * a Trustmark Definition and a list of Trustmark Providers that may satisfy the
 * requirement.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkDefinitionRequirement extends AbstractTIPReference {

    /**
     * Returns, if non-empty, the list of Trustmark Providers that may satisfy
     * the requirement; if empty, any Trustmark Provider may satisfy the
     * requirement.
     *
     * @return if non-empty, the list of Trustmark Providers that may satisfy
     * the requirement; if empty, any Trustmark Provider may satisfy the
     * requirement.
     */
    List<Entity> getProviderReferences();
}
