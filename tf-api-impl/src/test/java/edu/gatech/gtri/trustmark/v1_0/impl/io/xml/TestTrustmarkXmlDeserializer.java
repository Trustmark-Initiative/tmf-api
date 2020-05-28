package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/9/15.
 */
public class TestTrustmarkXmlDeserializer extends AbstractTest {

    public static final String TRUSTMARK_FULL_FILE = "./src/test/resources/Trustmarks/trustmark-full.xml";

    @Test
    public void testParseFullTrustmarkFile() throws Exception {
        logger.info("Testing that we can read a maximal trustmark XML file...");

        File xmlFile = new File(TRUSTMARK_FULL_FILE);
        String xml = FileUtils.readFileToString(xmlFile);
        Trustmark trustmark = TrustmarkXmlDeserializer.deserialize(xml);
        assertThat(trustmark, notNullValue());
        assertThat(trustmark.getIdentifier(), notNullValue());
        assertThat(trustmark.getIdentifier().toString(), is("http://provider.example/trustmark/1"));
        assertThat(trustmark.getTrustmarkDefinitionReference().getIdentifier().toString(), equalTo("https://trustmark.gtri.gatech.edu/operational-pilot/trustmark-definitions/ficam-privacy-minimal-attribute-release-requirements-for-csps-and-bae-responders/1.0-SNAPSHOT/"));
        assertThat(trustmark.getPolicyURL().toString(), equalTo("https://trustmark.gtri.gatech.edu/operational-pilot/example/policy"));
        assertThat(trustmark.getProvider(), notNullValue());
        assertThat(trustmark.getProvider().getIdentifier().toString(), equalTo("https://trustmark.gtri.gatech.edu/"));
        assertThat(trustmark.getRecipient(), notNullValue());
        assertThat(trustmark.getRecipient().getIdentifier().toString(), equalTo("https://trustmark.gtri.gatech.edu/"));
        assertThat(trustmark.getExceptionInfo(), notNullValue());
        assertThat(trustmark.getExceptionInfo().size(), equalTo(3));
        assertThat(trustmark.getParameterBindings(), notNullValue());
        assertThat(trustmark.getParameterBindings().size(), equalTo(3));


    }//end testXmlValidationAgainstValidFile()


}
