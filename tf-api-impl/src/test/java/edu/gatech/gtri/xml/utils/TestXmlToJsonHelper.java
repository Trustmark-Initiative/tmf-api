package edu.gatech.gtri.xml.utils;

import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests the code which converts XML to JSON. <br/><br/>
 *
 * @user brad
 * @date 10/7/16
 */
public class TestXmlToJsonHelper {

    private static final Logger log = LoggerFactory.getLogger(TestXmlToJsonHelper.class);


    @BeforeEach
    public void printStart() {
        log.info("======================================== STARTING TEST ========================================");
    }

    @AfterEach
    public void printStop() {
        log.info("======================================== STOPPING TEST ========================================\n\n");
    }

    protected Element readXML(File xmlFile, String nsUri) throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new FileReader(xmlFile));
        assertThat(document, notNullValue());

        Element root = document.getRootElement();
        root.addNamespace("xhtml", "http://www.w3.org/1999/xhtml");
        root.addNamespace("tf", nsUri);

        return root;
    }//end readXML()

    protected Element readXML(File xmlFile) throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new FileReader(xmlFile));
        assertThat(document, notNullValue());

        Element root = document.getRootElement();
        root.addNamespace("xhtml", "http://www.w3.org/1999/xhtml");
        root.addNamespace("tf", TrustmarkFrameworkConstants.NAMESPACE_URI);

        return root;
    }//end readXML()

    //====================================================================================================================
    //  TESTS
    //====================================================================================================================
    @Test
    public void testConvertTDFullXML() throws Exception {
        log.info("Converting TD Full XML to JSON...");

        File file = new File("./src/test/resources/TDs/td-full.xml");
        Element xml = readXML(file);
        String json = XmlToJsonHelper.toJson(xml);
        log.info("Converted to JSON: \n" + json);

        log.info("Successfully converted TD Full XML to JSON");
    }

    @Test
    public void testConvertTIPFullXML() throws Exception {
        log.info("Converting TIP Full XML to JSON...");

        File file = new File("./src/test/resources/TIPs/tip-full.xml");
        Element xml = readXML(file);
        String json = XmlToJsonHelper.toJson(xml);
        log.info("Converted to JSON: \n" + json);

        log.info("Successfully converted TIP Full XML to JSON");
    }

    @Test
    public void testConvertTrustmarkFullXML() throws Exception {
        log.info("Converting Trustmark Full XML to JSON...");

        File file = new File("./src/test/resources/Trustmarks/trustmark-full.xml");
        Element xml = readXML(file);
        String json = XmlToJsonHelper.toJson(xml);
        log.info("Converted to JSON: \n" + json);

        log.info("Successfully converted Trustmark Full XML to JSON");
    }

    @Test
    public void testConvertTrustmarkStatusReportFullXML() throws Exception {
        log.info("Converting Trustmark Status Report Full XML to JSON...");

        File file = new File("./src/test/resources/TSRs/statusreport-full.xml");
        Element xml = readXML(file);
        String json = XmlToJsonHelper.toJson(xml);
        log.info("Converted to JSON: \n" + json);

        log.info("Successfully converted Trustmark Status Report Full XML to JSON");
    }

}/* end TestXmlToJsonHelper */
