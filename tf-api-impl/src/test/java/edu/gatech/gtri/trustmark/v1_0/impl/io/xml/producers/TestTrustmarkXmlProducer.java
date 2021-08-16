package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkStatusReportXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by brad on 1/7/16.
 */
public class TestTrustmarkXmlProducer extends AbstractTest {
    private static final Logger logger = LogManager.getLogger(TestTrustmarkXmlProducer.class);

    public static final String TM_FULL_FILE = "./src/test/resources/Trustmarks/trustmark-full.json";

    @Test
    public void testXmlOutput() throws Exception {
        logger.info("Testing Simple Trustmark XML Output...");

        logger.debug("Loading Trustmark from file...");
        File xmlFile = new File(TM_FULL_FILE);
        String xml = FileUtils.readFileToString(xmlFile);
        Trustmark tm = TrustmarkJsonDeserializer.deserialize(xml);
        assertThat(tm, notNullValue());
        logger.debug("Successfully loaded trustmark-full.json!");

        logger.debug("Getting XmlManager...");
        XmlManager manager = FactoryLoader.getInstance(XmlManager.class);
        assertThat(manager, notNullValue());

        logger.debug("Getting the XmlSerializer....");
        SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        Serializer serializer = factory.getXmlSerializer();

        logger.debug("Serializing trustmark to XML...");
        StringWriter output = new StringWriter();
        serializer.serialize(tm, output);

        String xml2 = output.toString();
        logger.debug("Successfully produced XML: \n"+xml2);
        XmlHelper.validateXml(xml2);

        Trustmark trustmark2 = TrustmarkXmlDeserializer.deserialize(xml2);
        assertThat(trustmark2, notNullValue());

        // TODO we could do a Trustmark DIFF routine here.

        logger.info("Successfully output XML using the TrustmarkXmlProducer!");
    }




}
