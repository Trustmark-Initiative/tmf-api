package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


/**
 * Created by brad on 1/7/16.
 */
public class TestTrustmarkDefinitionXmlProducer extends AbstractTest {
    private static final Logger logger = Logger.getLogger(TestTrustmarkDefinitionXmlProducer.class);

    public static final String TD_FULL_FILE = "./src/test/resources/TDs/td-full.json";

    @Test
    public void testXmlOutput() throws Exception {
        logger.info("Testing Simple TrustmarkDefinition XML Output...");

        logger.debug("Loading TrustmarkDefinition from file...");
        File jsonFile = new File(TD_FULL_FILE);
        String json = FileUtils.readFileToString(jsonFile);
        TrustmarkDefinition td = TrustmarkDefinitionJsonDeserializer.deserialize(json);
        assertThat(td, notNullValue());
        assertTdFull(td);
        logger.debug("Successfully loaded td-full.json!");

        logger.debug("Getting XmlManager...");
        XmlManager manager = FactoryLoader.getInstance(XmlManager.class);
        assertThat(manager, notNullValue());

        logger.debug("Getting the XmlSerializer....");
        SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        Serializer serializer = factory.getXmlSerializer();

        logger.debug("Serializing TD to XML...");
        StringWriter output = new StringWriter();
        serializer.serialize(td, output);

        String xml2 = output.toString();
        logger.debug("Successfully produced XML: \n"+xml2);
        XmlHelper.validateXml(xml2);

        TrustmarkDefinition td2 = TrustmarkDefinitionXmlDeserializer.deserialize(xml2);
        assertThat(td2, notNullValue());

        assertTdFull(td2);

        logger.info("Successfully output XML using the TrustmarkDefinitionXmlProducer!");
    }//end testXmlOutput()



}
