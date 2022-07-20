package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.BuilderException;
import edu.gatech.gtri.trustmark.v1_0.model.BuilderFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by brad on 3/20/17.
 */
public class TestTrustInteroperabilityProfileBuilder extends AbstractTest {

    @Test
    public void testInstantiation() {
        logger.info("Testing that we can create a TrustInteroperabilityProfileBuilder...");

        BuilderFactory builderFactory = FactoryLoader.getInstance(BuilderFactory.class);
        assertThat(builderFactory, notNullValue());

        TrustInteroperabilityProfileBuilder builder = builderFactory.createTrustInteroperabilityProfileBuilder();
        assertThat(builder, notNullValue());

        logger.info("Successfully created a TrustInteroperabilityProfileBuilder!");
    }

    @Test
    public void testBuildBareMinimum() throws Exception {
        logger.info("Testing that we can build the bare minimum TD...");

        TrustInteroperabilityProfileBuilder builder = FactoryLoader.getInstance(BuilderFactory.class).createTrustInteroperabilityProfileBuilder();
        builder.setName("Test")
                .setIdentifier("https://trustmarks.gtri.org/test1")
                .setIssuerOrganization("https://gtri.org/", "GTRI Test", "Test", "s.brad.lee@gmail.com")
                .setDescription("a description")
                .setTrustExpression("tip1")
                .addTrustInteroperabilityProfileReference("tip1", "https://trustmark.gtri.org/tip1");

        TrustInteroperabilityProfile tip = builder.build();
        assertThat(tip, notNullValue());
        assertThat(tip.getName(), equalTo("Test"));
        assertThat(tip.getDescription(), equalTo("a description"));
        assertThat(tip.getIdentifier().toString(), equalTo("https://trustmarks.gtri.org/test1"));
        assertThat(tip.getIssuer().getIdentifier().toString(), equalTo("https://gtri.org/"));
        assertThat(tip.getIssuer().getName(), equalTo("GTRI Test"));
        assertThat(tip.getIssuer().getDefaultContact().getDefaultEmail(), equalTo("s.brad.lee@gmail.com"));
        assertThat(tip.getIssuer().getDefaultContact().getResponder(), equalTo("Test"));

        assertThat(tip.getTrustExpression(), equalTo("tip1"));
        assertThat(tip.getReferences(), notNullValue());
        assertThat(tip.getReferences().size(), equalTo(1));

        logger.info("Successfully tested buildBareMinimum!");
    }

    @Test
    public void testCatchTrustExpressionError() throws Exception {
        logger.info("Testing that we can catch errors in trust expressions...");

        TrustInteroperabilityProfileBuilder builder = FactoryLoader.getInstance(BuilderFactory.class).createTrustInteroperabilityProfileBuilder();
        builder.setName("Test")
                .setIdentifier("https://trustmarks.gtri.org/test1")
                .setIssuerOrganization("https://gtri.org/", "GTRI Test", "Test", "s.brad.lee@gmail.com")
                .setDescription("a description")
                .setTrustExpression("tip1 blurb!")
                .addTrustInteroperabilityProfileReference("tip1", "https://trustmark.gtri.org/tip1");

        try {
            TrustInteroperabilityProfile tip = builder.build();
            fail("Expected to catch an error with the TIP's TIP expression, but no such error was given!");
        } catch (BuilderException be) {
            logger.info("Caught error: " + be);
        }

        logger.info("Successfully ran test testCatchTrustExpressionError()");
    }
}
