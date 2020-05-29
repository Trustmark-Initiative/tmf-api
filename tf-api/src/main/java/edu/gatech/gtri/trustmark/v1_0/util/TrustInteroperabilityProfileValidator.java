package edu.gatech.gtri.trustmark.v1_0.util;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.util.Collection;

/**
 * Allows for extension of the Validation mechanism by 3rd parties.  To add your custom validation of {@link TrustInteroperabilityProfile}
 * objects, simply use the {@link java.util.ServiceLoader} mechanism to provide implementations of this interface.
 * <br/><br/>
 * Created by brad on 3/9/17.
 */
public interface TrustInteroperabilityProfileValidator {

    /**
     * Performs a validation on the TIP, returning the results.
     */
    public Collection<ValidationResult> validate(TrustInteroperabilityProfile tip);

}
