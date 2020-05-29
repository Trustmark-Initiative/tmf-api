package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractSerializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import org.apache.commons.io.input.XmlStreamReaderException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by brad on 12/15/15.
 */
public class SerializerXml extends AbstractSerializer {

    public static String NAME = "XML Serializer";
    public static String DESCRIPTION = "Serializes data into XML, using the official TF v1.1 XML format.";
    public static String OUTPUT_MIME_FORMAT = "text/xml";

    public SerializerXml(){
        super(NAME, DESCRIPTION, OUTPUT_MIME_FORMAT);
    }

    @Override
    public void serialize(Trustmark trustmark, Writer writer, Map model) throws IOException {
        if( trustmark.getOriginalSourceType() != null && trustmark.getOriginalSourceType().equalsIgnoreCase("text/xml") ){
            writer.write(trustmark.getOriginalSource());
            writer.flush();
            return;
        }

        try {
            serialize(writer, trustmark, "Trustmark");
        }catch(XMLStreamException xmlse){
            throw new IOException("Unexpected error while streaming XML!", xmlse);
        }
    }
    @Override
    public void serialize(TrustmarkStatusReport tsr, Writer writer, Map model) throws IOException {
        if( tsr.getOriginalSourceType() != null && tsr.getOriginalSourceType().equalsIgnoreCase("text/xml") ){
            writer.write(tsr.getOriginalSource());
            writer.flush();
            return;
        }

        try {
            serialize(writer, tsr, "TrustmarkStatusReport");
        }catch(XMLStreamException xmlse){
            throw new IOException("Unexpected error while streaming XML!", xmlse);
        }
    }
    @Override
    public void serialize(TrustmarkDefinition td, Writer writer, Map model) throws IOException {
        if( td.getOriginalSourceType() != null && td.getOriginalSourceType().equalsIgnoreCase("text/xml") ){
            writer.write(td.getOriginalSource());
            writer.flush();
            return;
        }

        try {
            serialize(writer, td, "TrustmarkDefinition");
        }catch(XMLStreamException xmlse){
            throw new IOException("Unexpected error while streaming XML!", xmlse);
        }
    }
    @Override
    public void serialize(TrustInteroperabilityProfile tip, Writer writer, Map model) throws IOException {
        if( tip.getOriginalSourceType() != null && tip.getOriginalSourceType().equalsIgnoreCase("text/xml") ){
            writer.write(tip.getOriginalSource());
            writer.flush();
            return;
        }

        try {
            serialize(writer, tip, "TrustInteroperabilityProfile");
        }catch(XMLStreamException xmlse){
            throw new IOException("Unexpected error while streaming XML!", xmlse);
        }
    }
    @Override
    public void serialize(Agreement agreement, Writer writer, Map model) throws IOException {
        if( agreement.getOriginalSourceType() != null && agreement.getOriginalSourceType().equalsIgnoreCase(OUTPUT_MIME_FORMAT) ){
            writer.write(agreement.getOriginalSource());
            writer.flush();
            return;
        }

        try {
            Codec<Agreement> codec = Codec.loadCodecFor(Agreement.class);
            serialize(writer, agreement, codec.getRootElementName());
        }catch(XMLStreamException xmlse){
            throw new IOException("Unexpected error while streaming XML!", xmlse);
        }
    }
    @Override
    public void serialize(AgreementResponsibilityTemplate art, Writer writer, Map model) throws IOException {
        if( art.getOriginalSourceType() != null && art.getOriginalSourceType().equalsIgnoreCase(OUTPUT_MIME_FORMAT) ){
            writer.write(art.getOriginalSource());
            writer.flush();
            return;
        }

        try {
            Codec<AgreementResponsibilityTemplate> codec = Codec.loadCodecFor(AgreementResponsibilityTemplate.class);
            serialize(writer, art, codec.getRootElementName());
        }catch(XMLStreamException xmlse){
            throw new IOException("Unexpected error while streaming XML!", xmlse);
        }
    }


    private XmlManager getManager(){
        return FactoryLoader.getInstance(XmlManager.class);
    }

    public void serialize(Writer writer, Object thing, String rootElementName, String nsUri) throws XMLStreamException {
        XmlManager manager = getManager();
        if( manager == null )
            throw new UnsupportedOperationException("No such class loaded: JsonManager.  Cannot serialize object: "+thing.getClass().getName()+": "+thing);
        XmlProducer producer = manager.findProducer(thing.getClass());
        if( producer == null )
            throw new UnsupportedOperationException("Cannot find JsonProducer for class: "+thing.getClass().getName());

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(writer);
        XmlStreamWriterDelegating delegatingStreamWriter = new XmlStreamWriterDelegating(xmlStreamWriter);
        delegatingStreamWriter.setNamespaceContext(new DefaultNamespaceContext());
        delegatingStreamWriter.writeStartDocument("UTF-8", "1.0");
        delegatingStreamWriter.writeComment("Serialized by the GTRI Trustmark Framework API, version: "+ FactoryLoader.getInstance(TrustmarkFramework.class).getApiImplVersion());
        delegatingStreamWriter.writeStartElement(nsUri, rootElementName);
        delegatingStreamWriter.writeNamespace(TrustmarkFrameworkConstants.NAMESPACE_PREFIX, nsUri);
        delegatingStreamWriter.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        delegatingStreamWriter.writeNamespace("ds", "http://www.w3.org/2000/09/xmldsig#");

        producer.serialize(thing, delegatingStreamWriter);

        delegatingStreamWriter.writeEndElement(); // end rootElementName
        delegatingStreamWriter.writeEndDocument();
    }

    public void serialize(Writer writer, Object thing, String rootElementName) throws XMLStreamException {
        XmlManager manager = getManager();
        if( manager == null )
            throw new UnsupportedOperationException("No such class loaded: JsonManager.  Cannot serialize object: "+thing.getClass().getName()+": "+thing);
        XmlProducer producer = manager.findProducer(thing.getClass());
        if( producer == null )
            throw new UnsupportedOperationException("Cannot find JsonProducer for class: "+thing.getClass().getName());

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(writer);
        XmlStreamWriterDelegating delegatingStreamWriter = new XmlStreamWriterDelegating(xmlStreamWriter);
        delegatingStreamWriter.setNamespaceContext(new DefaultNamespaceContext());
        delegatingStreamWriter.writeStartDocument("UTF-8", "1.0");
        delegatingStreamWriter.writeComment("Serialized by the GTRI Trustmark Framework API, version: "+ FactoryLoader.getInstance(TrustmarkFramework.class).getApiImplVersion());
        delegatingStreamWriter.writeStartElement(TrustmarkFrameworkConstants.NAMESPACE_URI, rootElementName);
        delegatingStreamWriter.writeNamespace(TrustmarkFrameworkConstants.NAMESPACE_PREFIX, TrustmarkFrameworkConstants.NAMESPACE_URI);
        delegatingStreamWriter.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        delegatingStreamWriter.writeNamespace("ds", "http://www.w3.org/2000/09/xmldsig#");

        producer.serialize(thing, delegatingStreamWriter);

        delegatingStreamWriter.writeEndElement(); // end rootElementName
        delegatingStreamWriter.writeEndDocument();
    }

}//end SerializerJson