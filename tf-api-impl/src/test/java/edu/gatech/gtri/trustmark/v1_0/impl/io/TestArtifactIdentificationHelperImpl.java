package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.ArtifactIdentification;
import edu.gatech.gtri.trustmark.v1_0.io.ArtifactIdentificationHelper;
import org.junit.Test;

import java.io.File;

import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.TEXT_XML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by brad on 7/29/16.
 */
public class TestArtifactIdentificationHelperImpl extends AbstractTest {


    @Test
    public void testFactoryLoaderResolution(){
        ArtifactIdentificationHelper aiHelper = FactoryLoader.getInstance(ArtifactIdentificationHelper.class);
        assertThat(aiHelper, notNullValue());
        assertThat(aiHelper, instanceOf(ArtifactIdentificationHelperImpl.class));
    }

    @Test
    public void testTrustmarkDefinitionResolution() throws Exception {
        File file = new File("./src/test/resources/TDs/td-full.xml");
        assertThat(file.exists(), is(Boolean.TRUE));

        ArtifactIdentificationHelper aiHelper = FactoryLoader.getInstance(ArtifactIdentificationHelper.class);
        assertThat(aiHelper, notNullValue());

        ArtifactIdentification id = aiHelper.getArtifactIdentification(file);
        assertThat(id, notNullValue());
        assertThat(id.getFile(), equalTo(file));
        assertThat(id.getArtifactType(), equalTo(ArtifactIdentification.ArtifactType.TRUSTMARK_DEFINITION));
        assertThat(id.getMimeType(), equalTo(TEXT_XML.getMediaType()));

    }

    @Test
    public void testTrustmarkResolution() throws Exception {
        File file = new File("./src/test/resources/Trustmarks/trustmark-full.xml");
        assertThat(file.exists(), is(Boolean.TRUE));

        ArtifactIdentificationHelper aiHelper = FactoryLoader.getInstance(ArtifactIdentificationHelper.class);
        assertThat(aiHelper, notNullValue());

        ArtifactIdentification id = aiHelper.getArtifactIdentification(file);
        assertThat(id, notNullValue());
        assertThat(id.getFile(), equalTo(file));
        assertThat(id.getArtifactType(), equalTo(ArtifactIdentification.ArtifactType.TRUSTMARK));
        assertThat(id.getMimeType(), equalTo(TEXT_XML.getMediaType()));

    }


    @Test
    public void testTrustmarkStatusReportResolution() throws Exception {
        File file = new File("./src/test/resources/TSRs/statusreport-full.xml");
        assertThat(file.exists(), is(Boolean.TRUE));

        ArtifactIdentificationHelper aiHelper = FactoryLoader.getInstance(ArtifactIdentificationHelper.class);
        assertThat(aiHelper, notNullValue());

        ArtifactIdentification id = aiHelper.getArtifactIdentification(file);
        assertThat(id, notNullValue());
        assertThat(id.getFile(), equalTo(file));
        assertThat(id.getArtifactType(), equalTo(ArtifactIdentification.ArtifactType.TRUSTMARK_STATUS_REPORT));
        assertThat(id.getMimeType(), equalTo(TEXT_XML.getMediaType()));

    }


    @Test
    public void testTrustInteroperabilityProfileResolution() throws Exception {
        File file = new File("./src/test/resources/TIPs/tip-full.xml");
        assertThat(file.exists(), is(Boolean.TRUE));

        ArtifactIdentificationHelper aiHelper = FactoryLoader.getInstance(ArtifactIdentificationHelper.class);
        assertThat(aiHelper, notNullValue());

        ArtifactIdentification id = aiHelper.getArtifactIdentification(file);
        assertThat(id, notNullValue());
        assertThat(id.getFile(), equalTo(file));
        assertThat(id.getArtifactType(), equalTo(ArtifactIdentification.ArtifactType.TRUST_INTEROPERABILITY_PROFILE));
        assertThat(id.getMimeType(), equalTo(TEXT_XML.getMediaType()));

    }

}
