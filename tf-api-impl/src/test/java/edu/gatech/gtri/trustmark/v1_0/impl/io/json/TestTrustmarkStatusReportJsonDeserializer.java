package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/9/15.
 */
public class TestTrustmarkStatusReportJsonDeserializer extends AbstractTest {

    public static final String TSR_FULL_FILE = "./src/test/resources/TSRs/statusreport-full.json";

    @Test
    public void testParseFullTrustmarkStatusReportFile() throws Exception {
        logger.info("Testing that we can read a maximal trustmark status report XML file...");

        File xmlFile = new File(TSR_FULL_FILE);
        String json = FileUtils.readFileToString(xmlFile);
        TrustmarkStatusReport tsr = TrustmarkStatusReportJsonDeserializer.deserialize(json);
        assertThat(tsr, notNullValue());

        // TODO Lots more assertions here.

    }//end testParseFullTrustmarkStatusReportFile()


}//end testParseFullTrustmarkFile()