package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers.AbstractXmlProducer;
import org.dom4j.Namespace;
import org.dom4j.QName;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Nicholas on 01/31/2017.
 */
public class AbstractDocumentXmlSerializer<T> extends AbstractDocumentOutputSerializer<T, XMLStreamWriter, XMLStreamException> {
    
    ////// Constants //////
    
    public static final String ATTRIBUTE_KEY_NIL = "nil";
    public static final String ATTRIBUTE_VALUE_TRUE = "true";
    public static final String NAMESPACE_TF_PREFIX = TrustmarkFrameworkConstants.NAMESPACE_PREFIX;
    public static final String NAMESPACE_TF_URI = TrustmarkFrameworkConstants.NAMESPACE_URI;
    public static final Namespace NAMESPACE_TF = Namespace.get(NAMESPACE_TF_PREFIX, NAMESPACE_TF_URI);
    public static final String NAMESPACE_XSI_PREFIX = "xsi";
    public static final String NAMESPACE_XSI_URI = edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers.AbstractXmlProducer.XSI_NS_URI;
    public static final Namespace NAMESPACE_XSI = Namespace.get(NAMESPACE_XSI_PREFIX, NAMESPACE_XSI_URI);
    public static final QName ATTRIBUTE_KEY_XML_TD_ID = QName.get(ATTRIBUTE_KEY_ID, NAMESPACE_TF);     // tf:id
    public static final QName ATTRIBUTE_KEY_XML_TD_REF = QName.get(ATTRIBUTE_KEY_REF, NAMESPACE_TF);   // tf:ref
    public static final QName ATTRIBUTE_KEY_XML_XSI_NIL = QName.get(ATTRIBUTE_KEY_NIL, NAMESPACE_XSI); // xsi:nil
    public static final Set<QName> ATTRIBUTE_KEYS_XML_META = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        ATTRIBUTE_KEY_XML_TD_ID,
        ATTRIBUTE_KEY_XML_TD_REF,
        ATTRIBUTE_KEY_XML_XSI_NIL
    )));
    
    public static final String CDATA_START = "<![CDATA[";
    public static final String CDATA_END_BRACKETS = "]]";
    public static final String CDATA_END_TAG = ">";
    public static final String CDATA_END = CDATA_END_BRACKETS + CDATA_END_TAG;
    public static final String CDATA_END_ESCAPE = CDATA_END_BRACKETS + CDATA_END + CDATA_START + CDATA_END_TAG;
    
    
    ////// Constructor //////
    
    public AbstractDocumentXmlSerializer(Codec<T> _codec) { super(_codec); }
    
    
    ////// Instance Methods //////
    
    @Override
    public <C> AbstractDocumentOutputSerializer<C, XMLStreamWriter, XMLStreamException> getAnalogousSerializer(Codec<C> otherCodec) {
        return otherCodec.xmlSerializer;
    }
    
    @Override
    protected void serializeTopLevelObject(AbstractDocumentOutputContext<XMLStreamWriter> context, T obj) throws XMLStreamException {
        // the calling SerializerXML does the top level startNode() and end() calls,
        // so this has to be just the stuff in-between
        this.serializeNonRefObjectAttributesAndChildren(context, obj);
    }
    
    @Override
    protected void serializeObjectId(AbstractDocumentOutputContext<XMLStreamWriter> context, String id) throws XMLStreamException {
        context.writer.writeAttribute(NAMESPACE_TF_PREFIX, NAMESPACE_TF_URI, ATTRIBUTE_KEY_ID, id);
    }
    
    @Override
    protected void serializeObjectRef(AbstractDocumentOutputContext<XMLStreamWriter> context, String ref) throws XMLStreamException {
        context.writer.writeAttribute(NAMESPACE_TF_PREFIX, NAMESPACE_TF_URI, ATTRIBUTE_KEY_REF, ref);
        context.writer.writeAttribute(NAMESPACE_XSI_PREFIX, NAMESPACE_XSI_URI, ATTRIBUTE_KEY_NIL, ATTRIBUTE_VALUE_TRUE);
    }
    
    @Override
    protected void serializeEmptyObjectStart(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        QName qualifiedName = this.codec.getQualifiedName(name);
        context.writer.writeEmptyElement(
            qualifiedName.getNamespacePrefix(),
            qualifiedName.getName(),
            qualifiedName.getNamespaceURI()
        );
    }
    
    @Override
    protected void serializeEmptyObjectEnd(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        // do nothing on empty object end
    }
    
    @Override
    protected void serializeObjectStart(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        QName qualifiedName = this.codec.getQualifiedName(name);
        context.writer.writeStartElement(
            qualifiedName.getNamespacePrefix(),
            qualifiedName.getName(),
            qualifiedName.getNamespaceURI()
        );
    }
    
    @Override
    protected void serializeObjectAttributes(AbstractDocumentOutputContext<XMLStreamWriter> context, Map<String, String> attributes) throws XMLStreamException {
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            context.writer.writeAttribute(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    protected void serializeBeforeObjectChild(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        // nothing special on element child start
    }
    
    @Override
    protected void serializeAfterObjectChild(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        // nothing special on element child end
    }
    
    @Override
    protected void serializeObjectEnd(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        context.writer.writeEndElement();
    }
    
    @Override
    protected void serializeSequenceStart(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        this.serializeObjectStart(context, name);
    }
    
    @Override
    protected void serializeBeforeSequenceChild(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        // nothing special on sequence child start
    }
    
    @Override
    protected void serializeAfterSequenceChild(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        // nothing special on sequence child end
    }
    
    @Override
    protected void serializeSequenceEnd(AbstractDocumentOutputContext<XMLStreamWriter> context, String name) throws XMLStreamException {
        this.serializeObjectEnd(context, name);
    }
    
    @Override
    protected void serializeValue(AbstractDocumentOutputContext<XMLStreamWriter> context, String name, boolean isCdata, String value) throws XMLStreamException {
        if (value == null) {
            this.serializeEmptyObjectStart(context, name);
            this.serializeEmptyObjectEnd(context, name);
        }
        else {
            this.serializeObjectStart(context, name);
            if (isCdata) {
                value = value.replaceAll(Pattern.quote(CDATA_END), CDATA_END_ESCAPE);
                context.writer.writeCData(value);
            }
            else {
                context.writer.writeCharacters(value);
            }
            this.serializeObjectEnd(context, name);
        }
    }
    
    @Override
    protected void serializeInnerLevelNonCodecObject(AbstractDocumentOutputContext<XMLStreamWriter> context, String name, Object object) throws XMLStreamException
    {
        this.serializeObjectStart(context, name);
        AbstractXmlProducer.writeXml(object, context.writer);
        this.serializeObjectEnd(context, name);
    }
    
}
