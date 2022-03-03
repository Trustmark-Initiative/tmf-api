package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/9/15.
 */
public class TestTrustInteroperabilityProfileXmlDeserializer extends AbstractTest {

    public static final String TIP_FULL_FILE = "./src/test/resources/TIPs/tip-full.xml";

    @Test
    public void testParseFullTrustInteroperabilityProfileFile() throws Exception {
        logger.info("Testing that we can read a maximal TrustInteroperabilityProfile XML file...");

        File xmlFile = new File(TIP_FULL_FILE);
        String xml = FileUtils.readFileToString(xmlFile);
        TrustInteroperabilityProfile tip = new TrustInteroperabilityProfileXmlDeserializer().deserialize(xml);
        assertThat(tip, notNullValue());

        assertTipFull(tip);

    }//end testXmlValidationAgainstValidFile()


}
