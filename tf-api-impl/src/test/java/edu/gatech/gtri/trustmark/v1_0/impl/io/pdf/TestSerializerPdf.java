package edu.gatech.gtri.trustmark.v1_0.impl.io.pdf;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResolver;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

/**
 * Created by Nicholas Saney on 3/27/17.
 */
public class TestSerializerPdf extends AbstractTest {
    
    public static final String FOLDER_NAME = "agreements/pdf_test";
    
    //==================================================================================================================
    //  Tests
    //==================================================================================================================
    @Test
    public void testPdf() throws Exception {
        this.testPdfFile("agreement-test-01");
        this.testPdfFile("agreement-test-minimal");
    }
    
    private void testPdfFile(String fileName) throws Exception {
        logger.info("Testing Agreement PDF creation...");
        
        String originalXmlString = this.getFileString(FOLDER_NAME, fileName + ".xml");
        
        AgreementResolver resolver = FactoryLoader.getInstance(AgreementResolver.class);
        Agreement entityFromXml = resolver.resolve(originalXmlString);
        
        SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        Serializer pdfSerializer = factory.getPdfSerializer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfSerializer.serialize(entityFromXml, baos);
        byte[] pdfBytes = baos.toByteArray();
        
        this.writeBytesToFile(FOLDER_NAME, fileName + ".pdf", pdfBytes);
        
        logger.info("Successfully tested Agreement PDF creation.");
    }
}
