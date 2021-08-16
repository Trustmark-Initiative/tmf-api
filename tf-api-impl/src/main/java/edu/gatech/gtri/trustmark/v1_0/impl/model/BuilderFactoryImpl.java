package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.BuilderFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileBuilder;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Default implementation of the {@link BuilderFactory}.
 * <br/><br/>
 * @author brad
 * @date 3/15/17
 */
public class BuilderFactoryImpl implements BuilderFactory {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LogManager.getLogger(BuilderFactoryImpl.class);
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

    @Override
    public TrustmarkDefinitionBuilder createTrustmarkDefinitionBuilder() {
        log.debug("Creating a new TrustmarkDefinitionBuilder...");
        return new TrustmarkDefinitionBuilderImpl();
    }

    @Override
    public TrustInteroperabilityProfileBuilder createTrustInteroperabilityProfileBuilder() {
        log.debug("Creating a new TrustInteroperabilityProfileBuilder...");
        return new TrustInteroperabilityProfileBuilderImpl();
    }




}/* end BuilderFactoryImpl */