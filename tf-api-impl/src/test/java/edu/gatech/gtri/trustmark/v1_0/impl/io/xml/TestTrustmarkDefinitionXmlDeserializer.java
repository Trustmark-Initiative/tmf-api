package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/9/15.
 */
public class TestTrustmarkDefinitionXmlDeserializer extends AbstractTest {

    public static final String TD_FULL_FILE = "./src/test/resources/TDs/td-full.xml";

    @Test
    public void testParseFullTrustmarkDefinitionFile() throws Exception {
        logger.info("Testing that we can read a maximal TD XML file...");

        File xmlFile = new File(TD_FULL_FILE);
        String xml = FileUtils.readFileToString(xmlFile);
        TrustmarkDefinition td = new TrustmarkDefinitionXmlDeserializer().deserialize(xml);
        assertThat(td, notNullValue());

        assertTdFull(td);

        logger.info("Successfully validated that we can read TD XML!");
    }//end testXmlValidationAgainstValidFile()


}
