package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.DefaultNamespaceContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlStreamWriterDelegating;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

        JSONObject jsonFromEntityFromJson = (JSONObject) FactoryLoader.getInstance(JsonManager.class).findProducer(((Object) entityFromJson).getClass()).serialize(entityFromJson);

        JSONObject jsonFromEntityFromXml = (JSONObject) FactoryLoader.getInstance(JsonManager.class).findProducer(((Object) entityFromXml).getClass()).serialize(entityFromXml);

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

        JSONObject jsonFromEntityXmlFromEntityFromJson = (JSONObject) FactoryLoader.getInstance(JsonManager.class).findProducer(((Object) entityFromXmlFromEntityFromJson).getClass()).serialize(entityFromXmlFromEntityFromJson);
        logger.debug("Outgoing AdioTest1 String: " + jsonFromEntityXmlFromEntityFromJson);
        assertNoJsonDiffIssuesOfType(DiffSeverity.MAJOR, JSONObject.class, originalJson, jsonFromEntityXmlFromEntityFromJson);

        String originalXmlString = getFileString("adio_test_1.xml");
        AdioTest1 entityFromXml = resolver.resolve(originalXmlString);

        JSONObject jsonFromEntityFromXml = (JSONObject) FactoryLoader.getInstance(JsonManager.class).findProducer(((Object) entityFromXml).getClass()).serialize(entityFromXml);
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
        File file = new File("./src/test/resources/adio/" + name);
        assertThat(file.exists(), equalTo(Boolean.TRUE));
        return readStringFromFile(file);
    }

    private String serializeToXml(SerializerXml serializer, Object instance) throws Exception {
        StringWriter writer = new StringWriter();
        Codec<?> codec = Codec.loadCodecFor(instance.getClass());

        try {
            final XmlManager xmlManager = FactoryLoader.getInstance(XmlManager.class);

            if (xmlManager == null) {

                throw new UnsupportedOperationException(format("The system could not find an instance of '%s'; the system could not serialize '%s'.", XmlManager.class.getCanonicalName(), instance.getClass().getCanonicalName()));

            } else {

                final XmlProducer xmlProducer = xmlManager.findProducer(instance.getClass());

                if (xmlProducer == null) {

                    throw new UnsupportedOperationException(format("The system could not find an instance of '%s' for '%s'.", XmlProducer.class.getCanonicalName(), instance.getClass().getCanonicalName()));

                } else {

                    final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                    final XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(writer);
                    final XmlStreamWriterDelegating xmlStreamWriterDelegating = new XmlStreamWriterDelegating(xmlStreamWriter);
                    xmlStreamWriterDelegating.setNamespaceContext(new DefaultNamespaceContext());
                    xmlStreamWriterDelegating.writeStartDocument("UTF-8", "1.0");
                    xmlStreamWriterDelegating.writeComment("Serialized by the GTRI Trustmark Framework API, version: " + FactoryLoader.getInstance(TrustmarkFramework.class).getApiImplVersion());
                    xmlStreamWriterDelegating.writeStartElement(TrustmarkFrameworkConstants.NAMESPACE_URI, codec.getRootElementName());
                    xmlStreamWriterDelegating.writeNamespace(TrustmarkFrameworkConstants.NAMESPACE_PREFIX, TrustmarkFrameworkConstants.NAMESPACE_URI);
                    xmlStreamWriterDelegating.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
                    xmlStreamWriterDelegating.writeNamespace("ds", "http://www.w3.org/2000/09/xmldsig#");

                    xmlProducer.serialize(instance, xmlStreamWriterDelegating);

                    xmlStreamWriterDelegating.writeEndElement();
                    xmlStreamWriterDelegating.writeEndDocument();
                    xmlStreamWriterDelegating.flush();
                }
            }
        } catch (final XMLStreamException xmlStreamException) {
            throw new IOException(xmlStreamException);
        }

        return writer.toString();
    }

}
