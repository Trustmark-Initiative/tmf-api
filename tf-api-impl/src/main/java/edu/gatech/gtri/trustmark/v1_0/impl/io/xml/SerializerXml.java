package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.impl.io.SerializerAbstract;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.TEXT_XML;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public final class SerializerXml extends SerializerAbstract {

    public SerializerXml() {
        super(
                SerializerXml.class.getCanonicalName(),
                SerializerXml.class.getCanonicalName(),
                TEXT_XML.getMediaType());
    }

    @Override
    public void serialize(
            final Agreement artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final Agreement artifact,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(artifact, writer, Codec.loadCodecFor(Agreement.class).getRootElementName());
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate artifact,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(artifact, writer, Codec.loadCodecFor(AgreementResponsibilityTemplate.class).getRootElementName());
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(artifact, writer, "TrustInteroperabilityProfile");
    }

    @Override
    public void serialize(
            final Trustmark artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final Trustmark artifact,
            final Writer writer,
            final Map model)
            throws IOException {

        if (artifact.getOriginalSourceType().equals(TEXT_XML.getMediaType())) {
            // TODO: Serialize trustmark based on content of trustmark; it's not possible at present, as not all of the information necessary for serialization is present.

            writer.write(artifact.getOriginalSource());
            writer.flush();
        } else {

            serializeHelper(artifact, writer, "Trustmark");
        }
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(artifact, writer, "TrustmarkDefinition");
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport artifact,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(artifact, writer, "TrustmarkStatusReport");
    }

    private void serializeHelper(
            final Object artifact,
            final Writer writer,
            final String elementName)
            throws IOException {

        serialize(writer, artifact, elementName, TrustmarkFrameworkConstants.NAMESPACE_URI);
        writer.flush();
    }

    public void serialize(
            final Writer writer,
            final Object object,
            final String elementName,
            final String elementNamespaceUri) throws IOException {

        try {
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
        } catch (final XMLStreamException xmlStreamException) {
            throw new IOException(xmlStreamException);
        }
    }
}
