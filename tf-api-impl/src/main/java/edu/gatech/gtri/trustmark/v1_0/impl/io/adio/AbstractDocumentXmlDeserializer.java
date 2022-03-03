package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.QName;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentXmlSerializer.ATTRIBUTE_KEY_XML_TD_ID;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentXmlSerializer.ATTRIBUTE_KEY_XML_TD_REF;

/**
 * Created by Nicholas on 02/01/2017.
 */
public class AbstractDocumentXmlDeserializer<T> extends AbstractDocumentInputDeserializer<T, Element, Element, Element> {

    ////// Constructor //////

    public AbstractDocumentXmlDeserializer(Codec<T> _codec) {
        super(_codec);
    }


    ////// Instance Methods //////

    @Override
    protected <C> AbstractDocumentInputDeserializer<C, Element, Element, Element> getAnalogousDeserializer(Codec<C> otherCodec) {
        return otherCodec.xmlDeserializer;
    }

    @Override
    protected String getSourceType() {
        return MediaType.TEXT_XML.getMediaType();
    }

    @Override
    protected Map.Entry<IdRefType, String> getReferenceInfoForObjectNode(Element rootNode) {
        IdRefType idRefType = IdRefType.NONE;
        String idRef = null;
        for (Iterator i = rootNode.attributeIterator(); i.hasNext(); ) {
            Attribute attribute = (Attribute) i.next();
            QName qName = attribute.getQName();
            String value = attribute.getValue();
            if (qName.equals(ATTRIBUTE_KEY_XML_TD_ID)) {
                idRefType = IdRefType.ID;
                idRef = value;
                break;
            } else if (qName.equals(ATTRIBUTE_KEY_XML_TD_REF)) {
                idRefType = IdRefType.REF;
                idRef = value;
                break;
            }
        }
        return new AbstractMap.SimpleEntry<>(idRefType, idRef);
    }

    @Override
    protected List<Object> getChildNodesInSequenceNode(Element sequenceNode)
            throws AbstractDocumentDeserializationException {
        List<Object> children = new ArrayList<>();
        for (Iterator i = sequenceNode.elementIterator(); i.hasNext(); ) {
            children.add(i.next());
        }
        return children;
    }

    @Override
    protected String getStringFromValueNode(Element valueNode) throws AbstractDocumentDeserializationException {
        return valueNode.getTextTrim();
    }

    @Override
    protected boolean isObjectNode(Object node) {
        return (node instanceof Element) && this.isObjectElement((Element) node);
    }

    protected boolean isObjectElement(Element element) {
        if (this.isSequenceElement(element)) {
            return false;
        }
        if (element.elementIterator().hasNext()) {
            return true;
        }
        Map.Entry<IdRefType, String> referenceInfo = this.getReferenceInfoForObjectNode(element);
        IdRefType idRefType = referenceInfo.getKey();
        return idRefType == IdRefType.REF;
    }

    @Override
    protected boolean isSequenceNode(Object node) {
        return (node instanceof Element) && this.isSequenceElement((Element) node);
    }

    protected boolean isSequenceElement(Element element) {
        String elementName = element.getQName().getName();
        return this.codec.isObjectSequenceName(elementName) || this.codec.isValueSequenceName(elementName);
    }

    @Override
    protected boolean isValueNode(Object node) {
        return (node instanceof Element) && this.isValueElement((Element) node);
    }

    protected boolean isValueElement(Element element) {
        return !this.isSequenceElement(element) && !this.isObjectElement(element);
    }

    @Override
    protected List<Object> getMatchingNodesInRootObject(Element rootNode, int limit, String[] pathSegments)
            throws AbstractDocumentDeserializationException {
        StringBuilder xpathBuilder = new StringBuilder(".");
        for (String pathSegment : pathSegments) {
            xpathBuilder.append("/");
            QName segmentQName = this.codec.getQualifiedName(pathSegment);
            xpathBuilder.append(segmentQName.getNamespacePrefix());
            xpathBuilder.append(":");
            xpathBuilder.append(segmentQName.getName());
        }
        String xpath = xpathBuilder.toString();
        List<Object> matchingNodes = new ArrayList<>();
        List<?> selectedNodes = rootNode.selectNodes(xpath);
        for (int i = 0; i < selectedNodes.size() && (limit < 0 || i < limit); ++i) {
            matchingNodes.add(selectedNodes.get(i));
        }
        return matchingNodes;
    }

    @Override
    protected Entity getEntityFromObjectNode(Element entityNode) throws ParseException {
        return XmlDeserializerUtility.readEntity(entityNode);
    }

    @Override
    protected Term getTermFromObjectNode(Element termNode) throws ParseException {
        return XmlDeserializerUtility.readTerm(termNode);
    }

    @Override
    protected TrustInteroperabilityProfile getTipFromObjectNode(Element tipNode) throws ParseException {
        String tipXml = tipNode.asXML();
        return new TrustInteroperabilityProfileXmlDeserializer().deserialize(tipXml);
    }

}
