package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractSerializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static java.lang.String.format;

public class SerializerXml extends AbstractSerializer {

    public SerializerXml() {
        super(
                "XML Serializer",
                "Serializes data into XML, using the official TF v1.1 XML format.",
                MediaType.TEXT_XML.getMediaType());
    }

    @Override
    public void serialize(
            final Trustmark trustmark,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(trustmark, writer, "Trustmark");
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport trustmarkStatusReport,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(trustmarkStatusReport, writer, "TrustmarkStatusReport");
    }

    @Override
    public void serialize(
            final TrustmarkDefinition trustmarkDefinition,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(trustmarkDefinition, writer, "TrustmarkDefinition");
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(trustInteroperabilityProfile, writer, "TrustInteroperabilityProfile");
    }

    @Override
    public void serialize(
            final Agreement agreement,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(agreement, writer, Codec.loadCodecFor(Agreement.class).getRootElementName());
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate agreementResponsibilityTemplate,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(agreementResponsibilityTemplate, writer, Codec.loadCodecFor(AgreementResponsibilityTemplate.class).getRootElementName());
    }

    private void serializeHelper(
            final HasSource hasSource,
            final Writer writer,
            final String elementName)
            throws IOException {

        if (hasSource.getOriginalSourceType() != null && hasSource.getOriginalSourceType().equalsIgnoreCase(getOutputMimeFormat())) {

            writer.write(hasSource.getOriginalSource());
            writer.flush();

        } else {

            try {

                serialize(writer, hasSource, elementName);

            } catch (XMLStreamException xmlStreamException) {

                throw new IOException("Unexpected error while streaming XML!", xmlStreamException);
            }
        }
    }

    public void serialize(
            final Writer writer,
            final Object object,
            final String elementName) throws XMLStreamException {

        serialize(writer, object, elementName, TrustmarkFrameworkConstants.NAMESPACE_URI);
    }

    public void serialize(
            final Writer writer,
            final Object object,
            final String elementName,
            final String elementNamespaceUri) throws XMLStreamException {

        final XmlManager xmlManager = FactoryLoader.getInstance(XmlManager.class);

        if (xmlManager == null) {

            throw new UnsupportedOperationException(format("The system could not find an instance of '%s'; the system could not serialize '%s'.", XmlManager.class.getCanonicalName(), object.getClass().getCanonicalName()));

        } else {

            final XmlProducer xmlProducer = xmlManager.findProducer(object.getClass());

            if (xmlProducer == null) {

                throw new UnsupportedOperationException(format("The system could not find an instance of '%s' for '%s'.", XmlProducer.class.getCanonicalName(), object.getClass().getCanonicalName()));

            } else {

                final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                final XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(writer);
                final XmlStreamWriterDelegating xmlStreamWriterDelegating = new XmlStreamWriterDelegating(xmlStreamWriter);
                xmlStreamWriterDelegating.setNamespaceContext(new DefaultNamespaceContext());
                xmlStreamWriterDelegating.writeStartDocument("UTF-8", "1.0");
                xmlStreamWriterDelegating.writeComment("Serialized by the GTRI Trustmark Framework API, version: " + FactoryLoader.getInstance(TrustmarkFramework.class).getApiImplVersion());
                xmlStreamWriterDelegating.writeStartElement(elementNamespaceUri, elementName);
                xmlStreamWriterDelegating.writeNamespace(TrustmarkFrameworkConstants.NAMESPACE_PREFIX, elementNamespaceUri);
                xmlStreamWriterDelegating.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
                xmlStreamWriterDelegating.writeNamespace("ds", "http://www.w3.org/2000/09/xmldsig#");

                xmlProducer.serialize(object, xmlStreamWriterDelegating);

                xmlStreamWriterDelegating.writeEndElement();
                xmlStreamWriterDelegating.writeEndDocument();
                xmlStreamWriterDelegating.flush();
            }
        }
    }
}
