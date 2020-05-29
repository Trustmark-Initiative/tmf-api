package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Use with {@link edu.gatech.gtri.trustmark.v1_0.FactoryLoader#getInstance(Class)} to create builder objects.
 * <br/><br/>
 * @author brad
 * @date 3/14/17
 */
public interface BuilderFactory {

    /**
     * Creates a new {@link TrustmarkDefinitionBuilder} for building {@link TrustmarkDefinition} objects.
     */
    public TrustmarkDefinitionBuilder createTrustmarkDefinitionBuilder();


    /**
     * Creates a new {@link TrustInteroperabilityProfileBuilder} for building {@link TrustInteroperabilityProfile} objects.
     */
    public TrustInteroperabilityProfileBuilder createTrustInteroperabilityProfileBuilder();


}/* end BuilderFactory */