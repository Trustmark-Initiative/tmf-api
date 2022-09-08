package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkStatusReportJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkStatusReportXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by brad on 1/7/16.
 */
public class TestTrustmarkStatusReportXmlProducer extends AbstractTest {
    private static final Logger logger = LoggerFactory.getLogger(TestTrustmarkStatusReportXmlProducer.class);

    public static final String TSR_FULL_FILE = "./src/test/resources/TSRs/statusreport-full.json";

    @Test
    public void testXmlOutput() throws Exception {
        logger.info("Testing Simple TrustmarkStatusReport XML Output...");

        logger.debug("Loading TSR from file...");
        File xmlFile = new File(TSR_FULL_FILE);
        String xml = FileUtils.readFileToString(xmlFile);
        TrustmarkStatusReport tsr = new TrustmarkStatusReportJsonDeserializer().deserialize(xml);
        assertThat(tsr, notNullValue());
        logger.debug("Successfully loaded statusreport-full.json!");

        logger.debug("Getting XmlManager...");
        XmlManager manager = FactoryLoader.getInstance(XmlManager.class);
        assertThat(manager, notNullValue());

        logger.debug("Getting XML Serializer...");
        SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        Serializer serializer = factory.getXmlSerializer();

        logger.debug("Serializing TIP to XML...");
        StringWriter output = new StringWriter();
        serializer.serialize(tsr, output);

        String xml2 = output.toString();
        logger.debug("Successfully produced XML: \n" + xml2);
        XmlHelper.validateXml(xml2);

        TrustmarkStatusReport tsr2 = new TrustmarkStatusReportXmlDeserializer().deserialize(xml2);
        assertThat(tsr2, notNullValue());

        // TODO we could do a TIP DIFF routine here.

        logger.info("Successfully output XML using the TrustmarkStatusReportXmlProducer!");
    }


}
