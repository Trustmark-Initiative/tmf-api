package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.impl.util.ConformanceCriterionUtils;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
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
public class TestTrustmarkDefinitionXmlProducer extends AbstractTest {
    private static final Logger logger = LoggerFactory.getLogger(TestTrustmarkDefinitionXmlProducer.class);

    public static final String TD_FULL_FILE = "./src/test/resources/TDs/td-full.json";
    public static final String TD_FULL_FILE_XML = "./src/test/resources/TDs/td-full.xml";


    public static final String TD_FULL_FILE_XML_CRITERION = "./src/test/resources/TDs/td-full-criterion.xml";

    @Test
    public void testXmlOutput() throws Exception {
        logger.info("Testing Simple TrustmarkDefinition XML Output...");

        logger.debug("Loading TrustmarkDefinition from file...");
        File jsonFile = new File(TD_FULL_FILE);
        String json = FileUtils.readFileToString(jsonFile);
        TrustmarkDefinition td = new TrustmarkDefinitionJsonDeserializer(true).deserialize(json);
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
        logger.debug("Successfully produced XML: \n" + xml2);
        XmlHelper.validateXml(xml2);

        TrustmarkDefinition td2 = new TrustmarkDefinitionXmlDeserializer(true).deserialize(xml2);
        assertThat(td2, notNullValue());

        assertTdFull(td2);

        logger.info("Successfully output XML using the TrustmarkDefinitionXmlProducer!");
    }//end testXmlOutput()

    @Test
    public void testMultiCriteriaIdGeneration() throws Exception {
        String TD_FULL_FILE = "./src/test/resources/TDs/tdAssessmentSteps.json";
        logger.debug("Loading TrustmarkDefinition from json file...{}", TD_FULL_FILE);
        File jsonFile = new File(TD_FULL_FILE);
        String json = FileUtils.readFileToString(jsonFile);
        TrustmarkDefinition td = new TrustmarkDefinitionJsonDeserializer(true).deserialize(json);
        assertThat(td, notNullValue());

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
        //logger.debug("Successfully produced XML: \n" + xml2);
        XmlHelper.validateXml(xml2);
        //XmlHelper.readWithDom4j(xml2).selectSingleNode("//*[local-name()='TrustmarkDefinition']/*[local-name()='AssessmentSteps']/*[local-name()='AssessmentStep']/@tf:id")
        assertThat(XmlHelper.readWithDom4j(xml2).selectSingleNode("//tf:TrustmarkDefinition/tf:AssessmentSteps/tf:AssessmentStep[@tf:id='SingleAssessmentStep']"),
                notNullValue());

        TrustmarkDefinition td2 = new TrustmarkDefinitionXmlDeserializer(true).deserialize(xml2);
        assertThat(td2, notNullValue());
        logger.info("Successfully output XML using the TrustmarkDefinitionXmlProducer!");
    }//end testXmlOutput()

    @Test
    public void testConformanceCriterionId() throws Exception {
        logger.info("Testing Serialization of a TrustmarkDefinition in XML emphasizing the conformance criteria id...");

        logger.debug("Loading trustmark from file...");
        File file = new File(TD_FULL_FILE_XML_CRITERION);
        String text = FileUtils.readFileToString(file);
        TrustmarkDefinition td = new TrustmarkDefinitionXmlDeserializer(true).deserialize(text);
        assertThat(td, notNullValue());

        logger.debug("Successfully parsed JSON");

        logger.debug("Loading jsonSerializer...");
        Serializer xmlSerializer = FactoryLoader.getInstance(SerializerFactory.class).getXmlSerializer();
        assertThat(xmlSerializer, notNullValue());

        logger.debug("Serializing json...");
        StringWriter jsonWriter = new StringWriter();
        xmlSerializer.serialize(td, jsonWriter);

        String xml = jsonWriter.toString();
        assertThat(xml, notNullValue());
        logger.debug("Successfully produced xml: \n" + xml);

        TrustmarkDefinition td2 = new TrustmarkDefinitionXmlDeserializer(true).deserialize(xml);
        assertThat(td2, notNullValue());

        td2.getConformanceCriteria().forEach(criterion -> {
            assert(criterion.getId().contains(ConformanceCriterionUtils.CRITERION_ID_PREFIX));
        });
    }
}
