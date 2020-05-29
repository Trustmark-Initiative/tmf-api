package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/9/15.
 */
public class TestTrustmarkDefinitionJsonDeserializer extends AbstractTest {

    public static final String TD_FULL_FILE = "./src/test/resources/TDs/td-full.json";

    @Test
    public void testParseFullTrustmarkDefinitionFile() throws Exception {
        logger.info("Testing that we can read a maximal trustmark definition XML file...");

        File xmlFile = new File(TD_FULL_FILE);
        String json = FileUtils.readFileToString(xmlFile);
        TrustmarkDefinition td = TrustmarkDefinitionJsonDeserializer.deserialize(json);
        assertThat(td, notNullValue());

        assertTdFull(td);

    }//end testParseFullTrustmarkDefinitionFile()


}//end testParseFullTrustmarkFile()