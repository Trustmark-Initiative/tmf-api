package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import org.apache.commons.io.FileUtils;
import org.dom4j.Element;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by brad on 12/9/15.
 */
public class TestXmlHelper extends AbstractTest {

    public static final String VALID_FILE = "./src/test/resources/TDs/td-full.xml";

    @Test
    public void testXmlValidationAgainstValidFile() throws Exception {
        logger.info("Testing validation against a valid file...");

        File xmlFile = new File(VALID_FILE);
        String xml = FileUtils.readFileToString(xmlFile);
        XmlHelper.validateXml(xml);

    }//end testXmlValidationAgainstValidFile()


    @Test
    public void testXmlValidationAgainstInvalidFile() throws Exception {
        logger.info("Testing validation against an invalid file...");

        File xmlFile = new File("./src/test/resources/TDs/invalid.xml");
        String xml = FileUtils.readFileToString(xmlFile);
        assertThrows(ParseException.class, () -> XmlHelper.validateXml(xml));

    }//end testXmlValidationAgainstValidFile()


    @Test
    public void testXmlValidationAgainstLegacyFile() throws Exception {
        logger.info("Testing validation against an invalid file (which is legacy)...");

        File xmlFile = new File("./src/test/resources/TDs/invalid-legacy.xml");
        String xml = FileUtils.readFileToString(xmlFile);
        assertThrows(ParseException.class, () -> XmlHelper.validateXml(xml));

    }//end testXmlValidationAgainstValidFile()


    @Test
    public void testReadDom4j() throws Exception {
        logger.info("Making sure we can read DOM4j into memory...");
        Element root = XmlHelper.readWithDom4j(FileUtils.readFileToString(new File(VALID_FILE)));
        assertThat(root, notNullValue());
        assertThat(root.getNamespaceForPrefix(TrustmarkFrameworkConstants.NAMESPACE_PREFIX), notNullValue());
        assertThat(root.getNamespaceForPrefix(TrustmarkFrameworkConstants.NAMESPACE_PREFIX).getURI(), is(TrustmarkFrameworkConstants.NAMESPACE_URI));

    }


}
