package edu.gatech.gtri.trustmark.v1_0.model;

import org.gtri.fj.function.F1;

/**
 * Implementations represent a reference in a Trust Interoperability Profile:
 * either a Trustmark Definition requirement or a Trust Interoperability Profile
 * Reference.
 *
 * @author GTRI Trustmark Team
 */
public interface AbstractTIPReference extends TrustmarkFrameworkIdentifiedObject {

    /**
     * Returns the identifier for the reference.
     *
     * @return the identifier for the reference.
     */
    String getId();

    /**
     * Returns true if this reference is a Trustmark Definition requirement.
     *
     * @return true if this reference is a Trustmark Definition requirement.
     */
    boolean isTrustmarkDefinitionRequirement();

    /**
     * Returns true if this reference is a Trust Interoperability Profile
     * reference.
     *
     * @return true if this reference is a Trust Interoperability Profile
     * reference.
     */
    boolean isTrustInteroperabilityProfileReference();

    /**
     * If the reference is a Trustmark Definintion requirement, return the
     * result of applying the first function; if the reference is a Trust
     * Interoperability Profile reference, return the result of applying the
     * second function.
     *
     * @param fTrustmarkDefinitionRequirement        the function to apply to
     *                                               the Trustmark Definition
     *                                               requirement
     * @param fTrustInteroperabilityProfileReference the function to apply to
     *                                               the Trust Interoperability
     *                                               Profile reference
     * @param <T1>                                   the type of the output of
     *                                               the function
     * @return if the reference is a Trustmark Definintion requirement, the
     * result of applying the first function; if the reference is a Trust
     * Interoperability Profile reference, the result of applying the second
     * function.
     */
    <T1> T1 match(
            final F1<TrustmarkDefinitionRequirement, T1> fTrustmarkDefinitionRequirement,
            final F1<TrustInteroperabilityProfileReference, T1> fTrustInteroperabilityProfileReference);
}
