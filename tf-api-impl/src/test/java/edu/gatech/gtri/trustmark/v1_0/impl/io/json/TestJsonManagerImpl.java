package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.*;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.*;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 1/7/16.
 */
public class TestJsonManagerImpl extends AbstractTest {

    @Test
    public void testJsonManagerResolves() {
        logger.info("Testing that JsonManager can be loaded via FactoryLoader interface correctly...");
        JsonManager manager = FactoryLoader.getInstance(JsonManager.class);
        assertThat(manager, notNullValue());
        assertThat(manager, instanceOf(JsonManagerImpl.class));
    }

    @Test
    public void testTrustmarkHasProducer() {
        logger.info("Asserting that Trustmark has a producer...");
        JsonManager manager = FactoryLoader.getInstance(JsonManager.class);
        assertThat(manager, notNullValue());
        JsonProducer producer = manager.findProducer(Trustmark.class);
        assertThat(producer, notNullValue());
        assertThat(producer, instanceOf(TrustmarkJsonProducer.class));
    }


    @Test
    public void testTrustmarkDefinitionHasProducer() {
        logger.info("Asserting that TrustmarkDefinition has a producer...");
        JsonManager manager = FactoryLoader.getInstance(JsonManager.class);
        assertThat(manager, notNullValue());
        JsonProducer producer = manager.findProducer(TrustmarkDefinition.class);
        assertThat(producer, notNullValue());
        assertThat(producer, instanceOf(TrustmarkDefinitionJsonProducer.class));
    }


    @Test
    public void testTrustInteroperabilityProfileHasProducer() {
        logger.info("Asserting that TrustInteroperabilityProfile has a producer...");
        JsonManager manager = FactoryLoader.getInstance(JsonManager.class);
        assertThat(manager, notNullValue());
        JsonProducer producer = manager.findProducer(TrustInteroperabilityProfile.class);
        assertThat(producer, notNullValue());
        assertThat(producer, instanceOf(TrustInteroperabilityProfileJsonProducer.class));
    }


    @Test
    public void testTrustmarkStatusReportHasProducer() {
        logger.info("Asserting that TrustmarkStatusReport has a producer...");
        JsonManager manager = FactoryLoader.getInstance(JsonManager.class);
        assertThat(manager, notNullValue());
        JsonProducer producer = manager.findProducer(TrustmarkStatusReport.class);
        assertThat(producer, notNullValue());
        assertThat(producer, instanceOf(TrustmarkStatusReportJsonProducer.class));
    }

    @Test
    public void testInterfaceResolution() {
        logger.info("Asserting that JsonManager can resolve based on interfaces of given class...");
        JsonManager manager = FactoryLoader.getInstance(JsonManager.class);
        assertThat(manager, notNullValue());
        JsonProducer producer = manager.findProducer(TrustmarkFrameworkIdentifiedObjectImpl.class);
        assertThat(producer, notNullValue());
        assertThat(producer, instanceOf(TrustmarkFrameworkIdentifiedObjectJsonProducer.class));
    }

}
