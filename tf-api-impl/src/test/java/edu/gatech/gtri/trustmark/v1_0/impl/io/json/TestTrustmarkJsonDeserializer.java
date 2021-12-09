package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/9/15.
 */
public class TestTrustmarkJsonDeserializer extends AbstractTest {

    public static final String TRUSTMARK_FULL_FILE = "./src/test/resources/Trustmarks/trustmark-full.json";

    @Test
    public void testParseFullTrustmarkFile() throws Exception {
        logger.info("Testing that we can read a maximal trustmark XML file...");

        File xmlFile = new File(TRUSTMARK_FULL_FILE);
        String json = FileUtils.readFileToString(xmlFile);
        Trustmark trustmark = new TrustmarkJsonDeserializer().deserialize(json);
        assertThat(trustmark, notNullValue());

        // TODO Lots more assertions here.

    }//end testXmlValidationAgainstValidFile()


}//end testParseFullTrustmarkFile()
