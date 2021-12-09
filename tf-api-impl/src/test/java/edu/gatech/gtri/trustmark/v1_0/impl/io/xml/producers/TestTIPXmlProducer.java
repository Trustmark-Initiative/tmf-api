package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by brad on 1/7/16.
 */
public class TestTIPXmlProducer extends AbstractTest {
    private static final Logger logger = LogManager.getLogger(TestTIPXmlProducer.class);

    public static final String TIP_FULL_FILE = "./src/test/resources/TIPs/tip-full.json";

    @Test
    public void testXmlOutput() throws Exception {
        logger.info("Testing Simple Trustmark XML Output...");

        logger.debug("Loading Trustmark from file...");
        File xmlFile = new File(TIP_FULL_FILE);
        String xml = FileUtils.readFileToString(xmlFile);
        TrustInteroperabilityProfile tip = new TrustInteroperabilityProfileJsonDeserializer().deserialize(xml);
        assertThat(tip, notNullValue());
        assertTipFull(tip);
        logger.debug("Successfully loaded tip-full.json!");

        logger.debug("Getting XmlManager...");
        XmlManager manager = FactoryLoader.getInstance(XmlManager.class);
        assertThat(manager, notNullValue());

        logger.debug("Getting the XmlSerializer....");
        SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        Serializer serializer = factory.getXmlSerializer();

        logger.debug("Serializing TIP to XML...");
        StringWriter output = new StringWriter();
        serializer.serialize(tip, output);

        String xml2 = output.toString();
        XmlHelper.validateXml(xml2);
        logger.debug("Successfully produced XML: \n" + xml2);

        TrustInteroperabilityProfile tip2 = TrustInteroperabilityProfileXmlDeserializer.deserialize(xml2);
        assertThat(tip2, notNullValue());
        assertTipFull(tip2);

        logger.info("Successfully output XML using the TIPXmlProducer!");
    }


}
