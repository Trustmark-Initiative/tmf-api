package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResponsibilityTemplateResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.json.JSONObject;
import org.junit.Test;

import java.io.StringWriter;

/**
 * Created by Nicholas on 2017-06-06.
 */
public class TestAgreementResponsibilityTemplateIO extends AbstractTest {
    
    public static final String FOLDER_NAME = "agreementResponsibilityTemplates/io_test";
    
    //==================================================================================================================
    //  Tests
    //==================================================================================================================
    @Test
    public void testRoundTrip() throws Exception {
        logger.info("Testing Agreement Responsibility Templates IO single round trip...");
        
        JSONObject originalJson = new JSONObject(getFileString(FOLDER_NAME, "agreement_responsibility_template_test_1.json"));
        String originalXmlString = this.getFileString(FOLDER_NAME, "agreement_responsibility_template_test_1.xml");
        
        AgreementResponsibilityTemplateResolver resolver = FactoryLoader.getInstance(AgreementResponsibilityTemplateResolver.class);
        //SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        //Serializer jsonSerializer = factory.getJsonSerializer();
        //Serializer xmlSerializer = factory.getXmlSerializer();
        SerializerJson jsonSerializer = new SerializerJson();
        SerializerXml xmlSerializer = new SerializerXml();
        
        AgreementResponsibilityTemplate entityFromJson = resolver.resolve(originalJson.toString());
        String xmlFromEntityFromJson = this.serializeToXml(xmlSerializer, entityFromJson);
        this.writeStringToFile(FOLDER_NAME, "agreement_responsibility_template_test_1_fromEntityFromJson.xml", xmlFromEntityFromJson);
        AgreementResponsibilityTemplate entityFromXmlFromEntityFromJson = resolver.resolve(xmlFromEntityFromJson);
        
        AgreementResponsibilityTemplate entityFromXml = resolver.resolve(originalXmlString);
        JSONObject jsonFromEntityFromXml = this.serializeToJson(jsonSerializer, entityFromXml);
        this.writeStringToFile(FOLDER_NAME, "agreement_responsibility_template_test_1_fromEntityFromXml.json", jsonFromEntityFromXml.toString(2));
        AgreementResponsibilityTemplate entityFromJsonFromEntityFromXml = resolver.resolve(jsonFromEntityFromXml.toString());
        
        this.assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, AgreementResponsibilityTemplate.class, entityFromJson, entityFromXmlFromEntityFromJson);
        this.assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, AgreementResponsibilityTemplate.class, entityFromXml, entityFromJsonFromEntityFromXml);
        this.assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, AgreementResponsibilityTemplate.class, entityFromJson, entityFromXml);
        
        logger.info("Successfully tested Agreement Responsibility Templates IO single round trip.");
    }
    
    
    //==================================================================================================================
    //  Test Helper Methods
    //==================================================================================================================
    
    protected JSONObject serializeToJson(SerializerJson serializer, AgreementResponsibilityTemplate instance) {
        return (JSONObject) serializer.serialize(instance);
    }
    
    protected String serializeToXml(SerializerXml serializer, AgreementResponsibilityTemplate instance) throws Exception {
        StringWriter writer = new StringWriter();
        Codec<?> codec = Codec.loadCodecFor(AgreementResponsibilityTemplate.class);
        serializer.serialize(writer, instance, codec.getRootElementName());
        return writer.toString();
    }
    
}