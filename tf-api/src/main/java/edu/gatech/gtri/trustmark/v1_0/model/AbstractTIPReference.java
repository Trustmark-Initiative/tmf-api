package edu.gatech.gtri.trustmark.v1_0.model;

import org.gtri.fj.function.F1;

/**
 * Models the abstract "References" section in the TIP.  This is either a TIP reference or a TD requirement.
 * <br/><br/>
 * Created by brad on 12/7/15.
 */
public interface AbstractTIPReference extends TrustmarkFrameworkIdentifiedObject {

    /**
     * An Identifier for this modeled Trust Interoperability Profile Reference.
     */
    String getId();

    Boolean isTrustmarkDefinitionRequirement();

    Boolean isTrustInteroperabilityProfileReference();

    <T1> T1 match(
            F1<TrustmarkDefinitionRequirement, T1> fTrustmarkDefinitionRequirement,
            F1<TrustInteroperabilityProfileReference, T1> fTrustInteroperabilityProfileReference);

}//end AbstractTIPReference
