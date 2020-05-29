package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by brad on 12/9/15.
 */
public class TestTrustmarkStatusReportXmlDeserializer extends AbstractTest {

    public static final String TSR_FULL_FILE = "./src/test/resources/TSRs/statusreport-full.xml";

    @Test
    public void testParseFullTrustmarkStatusReportFile() throws Exception {
        logger.info("Testing that we can read a maximal TrustmarkStatusReport XML file...");

        File xmlFile = new File(TSR_FULL_FILE);
        String xml = FileUtils.readFileToString(xmlFile);
        TrustmarkStatusReport tsr = TrustmarkStatusReportXmlDeserializer.deserialize(xml);
        assertThat(tsr, notNullValue());

        assertThat(tsr.getTrustmarkReference().toString(), equalTo("https://trustmark.gtri.gatech.edu/operational-pilot/example/trustmark/1"));
        assertThat(tsr.getStatus(), equalTo(TrustmarkStatusCode.EXPIRED));
        assertThat(tsr.getNotes(), equalTo("Notes."));
        assertThat(tsr.getSupersederTrustmarkReferences(), notNullValue());
        assertThat(toStringList(tsr.getSupersederTrustmarkReferences()), contains("https://trustmark.gtri.gatech.edu/operational-pilot/example/trustmark/2", "https://trustmark.gtri.gatech.edu/operational-pilot/example/trustmark/3"));
//        assertThat(tsr.getStatusDateTime().getTime(), equalTo(1388552400000l));

    }//end testXmlValidationAgainstValidFile()


    private List<String> toStringList(Collection<URI> uris){
        List<String> strings = new ArrayList<>();
        for( URI uri : uris ){
            strings.add(uri.toString());
        }
        Collections.sort(strings);
        return strings;
    }
}
