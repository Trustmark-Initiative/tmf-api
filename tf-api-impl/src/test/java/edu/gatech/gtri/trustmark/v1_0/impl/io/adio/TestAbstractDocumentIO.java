package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Nick
 * @date 2017-01-25
 */
public class TestAbstractDocumentIO extends AbstractTest {

    //==================================================================================================================
    //  Tests
    //==================================================================================================================
    @Test
    public void testRoundTrip_1() throws Exception {
        logger.info("Testing ADIO single round trip #1...");
        
        JSONObject originalJson = new JSONObject(getFileString("adio_test_1.json"));
        String originalXmlString = getFileString("adio_test_1.xml");
        
        FactoryLoader.register(AdioTest1Resolver.class, new AdioTest1Resolver());
        AdioTest1Resolver resolver = FactoryLoader.getInstance(AdioTest1Resolver.class);
        
        AdioTest1 entityFromJson = resolver.resolve(originalJson.toString());
        AdioTest1 entityFromXml = resolver.resolve(originalXmlString);
        
        //SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        //Serializer jsonSerializer = factory.getJsonSerializer();
        //Serializer xmlSerializer = factory.getXmlSerializer();
        SerializerJson jsonSerializer = new SerializerJson();
        SerializerXml xmlSerializer = new SerializerXml();
        
        JSONObject jsonFromEntityFromJson = serializeToJson(jsonSerializer, entityFromJson);
        JSONObject jsonFromEntityFromXml = serializeToJson(jsonSerializer, entityFromXml);
        
        assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, JSONObject.class, originalJson, jsonFromEntityFromJson);
        assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, JSONObject.class, originalJson, jsonFromEntityFromXml);
        
        String xmlFromEntityFromJson = serializeToXml(xmlSerializer, entityFromJson);
        String xmlFromEntityFromXml = serializeToXml(xmlSerializer, entityFromXml);
    
        assertNoXmlDiffIssuesOfType(DiffSeverity.MAJOR, originalXmlString, xmlFromEntityFromJson);
        assertNoXmlDiffIssuesOfType(DiffSeverity.MAJOR, originalXmlString, xmlFromEntityFromXml);
        
        logger.info("Successfully tested ADIO single round trip #1.");
    }
    
    @Test
    public void testDoubleRoundTrip_1() throws Exception {
        logger.info("Testing ADIO double round trip #1...");
        
        FactoryLoader.register(AdioTest1Resolver.class, new AdioTest1Resolver());
        AdioTest1Resolver resolver = FactoryLoader.getInstance(AdioTest1Resolver.class);
        
        //SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        //Serializer jsonSerializer = factory.getJsonSerializer();
        //Serializer xmlSerializer = factory.getXmlSerializer();
        SerializerJson jsonSerializer = new SerializerJson();
        SerializerXml xmlSerializer = new SerializerXml();
        
        JSONObject originalJson = new JSONObject(getFileString("adio_test_1.json"));
        AdioTest1 entityFromJson = resolver.resolve(originalJson.toString());
        String xmlFromEntityFromJson = serializeToXml(xmlSerializer, entityFromJson);
        AdioTest1 entityFromXmlFromEntityFromJson = resolver.resolve(xmlFromEntityFromJson);
        JSONObject jsonFromEntityXmlFromEntityFromJson = serializeToJson(jsonSerializer, entityFromXmlFromEntityFromJson);
        logger.debug("Outgoing AdioTest1 String: " + jsonFromEntityXmlFromEntityFromJson);
        assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, JSONObject.class, originalJson, jsonFromEntityXmlFromEntityFromJson);
        
        String originalXmlString = getFileString("adio_test_1.xml");
        AdioTest1 entityFromXml = resolver.resolve(originalXmlString);
        JSONObject jsonFromEntityFromXml = serializeToJson(jsonSerializer, entityFromXml);
        AdioTest1 entityFromJsonFromEntityFromXml = resolver.resolve(jsonFromEntityFromXml.toString());
        String xmlFromEntityFromJsonFromEntityFromXml = serializeToXml(xmlSerializer, entityFromJsonFromEntityFromXml);
        logger.debug("Outgoing AdioTest1 String: " + xmlFromEntityFromJsonFromEntityFromXml);
        assertNoXmlDiffIssuesOfType(DiffSeverity.MAJOR, originalXmlString, xmlFromEntityFromJsonFromEntityFromXml);
        
        logger.info("Successfully tested ADIO double round trip #1.");
        
    }
    
    
    //==================================================================================================================
    //  Test Helper Methods
    //==================================================================================================================

    private String getFileString(String name) throws IOException {
        File file = new File("./src/test/resources/adio/"+name);
        assertThat(file.exists(), equalTo(Boolean.TRUE));
        return readStringFromFile(file);
    }
    
    private JSONObject serializeToJson(SerializerJson serializer, Object instance) {
        return (JSONObject) serializer.serialize(instance);
    }
    
    private String serializeToXml(SerializerXml serializer, Object instance) throws Exception {
        StringWriter writer = new StringWriter();
        Codec<?> codec = Codec.loadCodecFor(instance.getClass());
        serializer.serialize(writer, instance, codec.getRootElementName());
        return writer.toString();
    }

}
