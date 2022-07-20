package edu.gatech.gtri.trustmark.v1_0.impl;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 1/6/16.
 */
public class TestTrustmarkFrameworkImpl extends AbstractTest {

    @Test
    public void testTFImpl(){
        logger.info("Testing that we can get the library versions and build dates...");
        TrustmarkFramework tf = FactoryLoader.getInstance(TrustmarkFramework.class);
        assertThat(tf, notNullValue());
        assertThat(tf, instanceOf(TrustmarkFrameworkImpl.class));

        assertThat(tf.getTrustmarkFrameworkVersion(), notNullValue());
        assertThat(tf.getApiVersion(), notNullValue());
        assertThat(tf.getApiBuildDate(), notNullValue());
        assertThat(tf.getApiImplVersion(), notNullValue());
        assertThat(tf.getApiImplBuildDate(), notNullValue());

        logger.info("Successfully retrieved build information: ");
        logger.info("       TF Version: "+tf.getTrustmarkFrameworkVersion());
        logger.info("    API Timestamp: "+tf.getApiBuildDate());
        logger.info("      API Version: "+tf.getApiVersion());
        logger.info("   Impl Timestamp: "+tf.getApiImplBuildDate());
        logger.info("     Impl Version: "+tf.getApiImplVersion());
    }


}
