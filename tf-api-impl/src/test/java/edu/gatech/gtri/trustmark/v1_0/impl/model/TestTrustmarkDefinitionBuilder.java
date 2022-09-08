package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.BuilderFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 3/20/17.
 */
public class TestTrustmarkDefinitionBuilder extends AbstractTest {

    @Test
    public void testInstantiation() {
        logger.info("Testing that we can create a TrustmarkDefinitionBuilder...");

        BuilderFactory builderFactory = FactoryLoader.getInstance(BuilderFactory.class);
        assertThat(builderFactory, notNullValue());

        TrustmarkDefinitionBuilder builder = builderFactory.createTrustmarkDefinitionBuilder();
        assertThat(builder, notNullValue());

        logger.info("Successfully created a TrustmarkDefinitionBuilder!");
    }


    @Test
    public void testBuildBareMinimum() throws Exception {
        logger.info("Testing that we can build the bare minimum TD...");

        TrustmarkDefinitionBuilder builder = FactoryLoader.getInstance(BuilderFactory.class).createTrustmarkDefinitionBuilder();
        builder.setName("Test")
                .setIdentifier("https://trustmarks.gtri.org/test1")
                .setTrustmarkDefiningOrganization("https://gtri.org/", "GTRI Test", "Test", "s.brad.lee@gmail.com")
                .setDescription("a description")
                .addAssessmentStep("Test", "Test Step Description");

        TrustmarkDefinition td = builder.build();
        assertThat(td, notNullValue());
        assertThat(td.getMetadata().getName(), equalTo("Test"));
        assertThat(td.getMetadata().getDescription(), equalTo("a description"));
        assertThat(td.getMetadata().getIdentifier().toString(), equalTo("https://trustmarks.gtri.org/test1"));
        assertThat(td.getMetadata().getTrustmarkDefiningOrganization().getIdentifier().toString(), equalTo("https://gtri.org/"));
        assertThat(td.getMetadata().getTrustmarkDefiningOrganization().getName(), equalTo("GTRI Test"));
        assertThat(td.getMetadata().getTrustmarkDefiningOrganization().getDefaultContact().getDefaultEmail(), equalTo("s.brad.lee@gmail.com"));
        assertThat(td.getMetadata().getTrustmarkDefiningOrganization().getDefaultContact().getResponder(), equalTo("Test"));

        assertThat(td.getAssessmentSteps().size(), equalTo(1));
        assertThat(td.getAssessmentSteps().get(0).getNumber(), equalTo(1));
        assertThat(td.getAssessmentSteps().get(0).getName(), equalTo("Test"));
        assertThat(td.getAssessmentSteps().get(0).getDescription(), equalTo("Test Step Description"));

        logger.info("Successfully tested buildBareMinimum!");
    }



}
