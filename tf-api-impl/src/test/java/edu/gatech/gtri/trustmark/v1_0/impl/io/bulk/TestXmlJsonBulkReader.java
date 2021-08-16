package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadResult;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReader;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReaderFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 * @author brad
 * @date 5/4/17
 */
public class TestXmlJsonBulkReader extends AbstractTest {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LogManager.getLogger(TestXmlJsonBulkReader.class);
    //==================================================================================================================
    //  TESTS
    //==================================================================================================================
    @Test
    public void testFactoryLoader() {
        log.info("Testing that the XmlJsonBulkReader can be found at all...");

        BulkReaderFactory factory = FactoryLoader.getInstance(BulkReaderFactory.class);
        assertThat(factory, notNullValue());

        List<File> jsonFiles = collectTestJsonFiles();

        BulkReader reader = factory.createBulkReader(convert(jsonFiles));
        assertThat(reader, notNullValue());
        assertThat(reader, instanceOf(XmlJsonBulKReader.class));

        log.info("Successfully resolved XmlJsonBulkReader!");
    }//end testFactoryLoader()


    @Test
    public void testAllJsonBulkImport() throws Exception {
        log.info("Testing that the XmlJsonBulkReader can import TDs...");
        List<File> jsonFiles = collectTestJsonFiles();
        BulkReader reader = FactoryLoader.getInstance(BulkReaderFactory.class).createBulkReader(convert(jsonFiles));

        log.debug("Performing import...");
        BulkReadResult result = reader.readBulkFrom(null, jsonFiles);
        assertThat(result, notNullValue());
        assertThat(result.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(result.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(result.getResultingTrustInteroperabilityProfiles(), notNullValue());
        assertThat(result.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));

        log.info("Successfully resolved XmlJsonBulkReader!");
    }//end testFactoryLoader()


    //==================================================================================================================
    //  HELPER METHODS
    //==================================================================================================================
    private File[] convert(List<File> files){
        File[] fileArray = new File[files.size()];
        for( int i = 0; i < files.size(); i++ )
            fileArray[i] = files.get(i);
        return fileArray;
    }

    private List<File> collectTestJsonFiles(){
        List<File> files = new ArrayList<>();
        String baseUrl = "./src/test/resources/spoof_remote_service";
        String tdsBase = baseUrl + File.separator + "tds_1";

        files.add(new File(tdsBase+File.separator+"acceptance-criteria_1.0.json"));
        files.add(new File(tdsBase+File.separator+"acceptance-of-federal-piv-credentials-for-non-organizational-users_1.0.json"));

        return files;
    }

}/* end TestXmlJsonBulkReader */