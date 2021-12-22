package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

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
public class TestTrustInteroperabilityProfileJsonDeserializer extends AbstractTest {

    public static final String TIP_FULL_FILE = "./src/test/resources/TIPs/tip-full.json";

    @Test
    public void testParseFullTipFile() throws Exception {
        logger.info("Testing that we can read a maximal TIP XML file...");

        File xmlFile = new File(TIP_FULL_FILE);
        String json = FileUtils.readFileToString(xmlFile);
        TrustInteroperabilityProfile tip = new TrustInteroperabilityProfileJsonDeserializer().deserialize(json);
        assertThat(tip, notNullValue());

        assertTipFull(tip);

    }//end testParseFullTipFile()


}//end testParseFullTrustmarkFile()
