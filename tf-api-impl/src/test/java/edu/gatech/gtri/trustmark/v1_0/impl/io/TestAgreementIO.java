package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.json.JSONObject;
import org.junit.Test;

import java.io.StringWriter;
/**
 * Created by Nicholas on 02/03/2017.
 */
public class TestAgreementIO extends AbstractTest {
    
    public static final String FOLDER_NAME = "agreements/io_test";
    
    //==================================================================================================================
    //  Tests
    //==================================================================================================================
    @Test
    public void testRoundTrip() throws Exception {
        logger.info("Testing Agreement IO single round trip...");
        
        JSONObject originalJson = new JSONObject(getFileString(FOLDER_NAME, "agreement_test_1.json"));
        String originalXmlString = this.getFileString(FOLDER_NAME, "agreement_test_1.xml");
        
        AgreementResolver resolver = FactoryLoader.getInstance(AgreementResolver.class);
        //SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        //Serializer jsonSerializer = factory.getJsonSerializer();
        //Serializer xmlSerializer = factory.getXmlSerializer();
        SerializerJson jsonSerializer = new SerializerJson();
        SerializerXml xmlSerializer = new SerializerXml();
        
        Agreement entityFromJson = resolver.resolve(originalJson.toString());
        String xmlFromEntityFromJson = this.serializeToXml(xmlSerializer, entityFromJson);
        this.writeStringToFile(FOLDER_NAME, "agreement_test_1_fromEntityFromJson.xml", xmlFromEntityFromJson);
        Agreement entityFromXmlFromEntityFromJson = resolver.resolve(xmlFromEntityFromJson);
        
        Agreement entityFromXml = resolver.resolve(originalXmlString);
        JSONObject jsonFromEntityFromXml = this.serializeToJson(jsonSerializer, entityFromXml);
        this.writeStringToFile(FOLDER_NAME, "agreement_test_1_fromEntityFromXml.json", jsonFromEntityFromXml.toString(2));
        Agreement entityFromJsonFromEntityFromXml = resolver.resolve(jsonFromEntityFromXml.toString());
        
        this.assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, Agreement.class, entityFromJson, entityFromXmlFromEntityFromJson);
        this.assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, Agreement.class, entityFromXml, entityFromJsonFromEntityFromXml);
        this.assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, Agreement.class, entityFromJson, entityFromXml);
        
        logger.info("Successfully tested Agreement IO single round trip.");
    }
    
    
    //==================================================================================================================
    //  Test Helper Methods
    //==================================================================================================================
    
    protected JSONObject serializeToJson(SerializerJson serializer, Agreement instance) {
        return (JSONObject) serializer.serialize(instance);
    }
    
    protected String serializeToXml(SerializerXml serializer, Agreement instance) throws Exception {
        StringWriter writer = new StringWriter();
        Codec<?> codec = Codec.loadCodecFor(Agreement.class);
        serializer.serialize(writer, instance, codec.getRootElementName());
        return writer.toString();
    }
    
}