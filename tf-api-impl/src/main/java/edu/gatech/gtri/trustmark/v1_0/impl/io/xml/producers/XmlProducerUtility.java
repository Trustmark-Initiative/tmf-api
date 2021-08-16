package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlUtils;
import edu.gatech.gtri.trustmark.v1_0.model.Categorized;
import edu.gatech.gtri.trustmark.v1_0.model.LegallyPublished;
import edu.gatech.gtri.trustmark.v1_0.model.Supersedable;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringReader;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public final class XmlProducerUtility {
    private XmlProducerUtility() {
    }

    public static final String XSI_NS_URI = "http://www.w3.org/2001/XMLSchema-instance";
    private static final Logger log = LogManager.getLogger(XmlProducerUtility.class);

    private static XmlManager getXmlManager() {
        return FactoryLoader.getInstance(XmlManager.class);
    }

    public static void writeXml(Object thing, XMLStreamWriter xmlStreamWriter) throws XMLStreamException {
        if (thing == null)
            throw new IllegalArgumentException("Not expecting a null argument to write as XML!");

        XmlManager manager = getXmlManager();
        if (manager == null)
            throw new UnsupportedOperationException("Could not find required class: " + XmlManager.class.getName());
        XmlProducer producer = manager.findProducer(thing.getClass());
        if (producer == null)
            throw new UnsupportedOperationException("No XmlProducer found for object[" + thing.getClass().getName() + "]: " + thing);

        producer.serialize(thing, xmlStreamWriter);
    }

    public static void writeString(XMLStreamWriter xmlWriter, String elementName, String value) throws XMLStreamException {
        xmlWriter.writeStartElement(NAMESPACE_URI, elementName);
        xmlWriter.writeCharacters(value);
        xmlWriter.writeEndElement();
    }

    public static void writeCDataString(XMLStreamWriter xmlWriter, String elementName, String value) throws XMLStreamException {
        xmlWriter.writeStartElement(NAMESPACE_URI, elementName);
        xmlWriter.writeCData(value);
        xmlWriter.writeEndElement();
    }

    public static void writeXmlToStream(String xml, XMLStreamWriter writer) throws XMLStreamException {
        log.debug("Reading string, and copying to XMLStreamWriter...");
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xml));
        while (reader.hasNext()) {
            reader.next();
            switch (reader.getEventType()) {
                case XMLStreamConstants.ATTRIBUTE:
                    log.debug("  ** ATTRIBUTE");
                    break;
                case XMLStreamConstants.CDATA:
                    writer.writeCData(reader.getText());
                    break;
                case XMLStreamConstants.CHARACTERS:
                    writer.writeCharacters(reader.getText());
                    break;
                case XMLStreamConstants.COMMENT:
                    writer.writeComment(reader.getText());
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    writer.writeEndElement();
                    break;
                case XMLStreamConstants.NAMESPACE:
                    log.debug("  ** NAMESPACE");
                    break;
                case XMLStreamConstants.SPACE:
                    writer.writeCharacters(reader.getText());
                    break;
                case XMLStreamConstants.START_ELEMENT:
                    if (reader.getNamespaceURI() != null) {
                        writer.writeStartElement(reader.getNamespaceURI(), reader.getLocalName());
                    } else {
                        writer.writeStartElement(reader.getLocalName());
                    }

                    if (reader.getNamespaceCount() > 0) {
                        for (int i = 0; i < reader.getNamespaceCount(); i++) {
                            writer.writeNamespace(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
                        }
                    }

                    if (reader.getAttributeCount() > 0) {
                        for (int i = 0; i < reader.getAttributeCount(); i++) {
                            if (reader.getAttributeNamespace(i) != null) {
                                writer.writeAttribute(reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributeValue(i));
                            } else {
                                writer.writeAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
                            }
                        }
                    }

                    break;
                case XMLStreamConstants.DTD:
                    log.debug("  IGNORING ** DTD");
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    log.debug("  IGNORING ** END_DOCUMENT");
                    break;
                case XMLStreamConstants.ENTITY_DECLARATION:
                    log.debug("  IGNORING ** ENTITY_DECLARATION");
                    break;
                case XMLStreamConstants.ENTITY_REFERENCE:
                    log.debug("  IGNORING ** ENTITY_REFERENCE");
                    break;
                case XMLStreamConstants.NOTATION_DECLARATION:
                    log.debug("  IGNORING ** NOTATION_DECLARATION");
                    break;
                case XMLStreamConstants.PROCESSING_INSTRUCTION:
                    log.debug("  IGNORING ** PROCESSING_INSTRUCTION");
                    break;
                case XMLStreamConstants.START_DOCUMENT:
                    log.debug("  IGNORING ** START_DOCUMENT");
                    break;
                default:
                    log.warn("  ** <UNKNOWN TYPE: " + reader.getEventType() + ">!!");
                    break;
            }
        }

        log.debug("Successfully wrote String to XMLStreamWriter!");
    }//end writeXmlToStream()

    public static void writeMainIdentifyingInformation(XMLStreamWriter xmlWriter, TrustmarkFrameworkIdentifiedObject tfio) throws XMLStreamException {

        xmlWriter.writeStartElement(NAMESPACE_URI, "Identifier");
        xmlWriter.writeCharacters(tfio.getIdentifier().toString());
        xmlWriter.writeEndElement(); //end "Identifier"

//        if( tfio instanceof TrustmarkDefinition.Metadata ) {
//            xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkReferenceAttributeName");
//            xmlWriter.writeCharacters(((TrustmarkDefinition.Metadata) tfio).getTrustmarkReferenceAttributeName().toString());
//            xmlWriter.writeEndElement(); //end "TrustmarkReferenceAttributeName"
//        }

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(tfio.getName());
        xmlWriter.writeEndElement(); //end "Name"

        xmlWriter.writeStartElement(NAMESPACE_URI, "Version");
        xmlWriter.writeCharacters(tfio.getVersion());
        xmlWriter.writeEndElement(); //end "Version"

        xmlWriter.writeStartElement(NAMESPACE_URI, "Description");
        xmlWriter.writeCharacters(tfio.getDescription());
        xmlWriter.writeEndElement(); //end "Description"

        if (tfio instanceof LegallyPublished) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "PublicationDateTime");
            xmlWriter.writeCharacters(XmlUtils.toDateTimeString(((LegallyPublished) tfio).getPublicationDateTime()));
            xmlWriter.writeEndElement(); //end "PublicationDateTime"
        }

    }

    public static void writeLegalEase(XMLStreamWriter xmlWriter, LegallyPublished lp) throws XMLStreamException {
        if (lp.getLegalNotice() != null) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "LegalNotice");
            xmlWriter.writeCharacters(lp.getLegalNotice());
            xmlWriter.writeEndElement(); //end "LegalNotice"
        }
        if (lp.getNotes() != null) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Notes");
            xmlWriter.writeCharacters(lp.getNotes());
            xmlWriter.writeEndElement(); //end "Notes"
        }
    }

    public static void writeKeywords(XMLStreamWriter xmlWriter, Categorized categorized) throws XMLStreamException {
        if (categorized.getKeywords() != null && categorized.getKeywords().size() > 0) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Keywords");
            for (String keyword : categorized.getKeywords()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "Keyword");
                xmlWriter.writeCharacters(keyword);
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement(); // end "Keywords"
        }
    }

    public static void writeSatisfies(XMLStreamWriter xmlWriter, Supersedable supersedable) throws XMLStreamException {
        if (supersedable.getSatisfies() != null && supersedable.getSatisfies().size() > 0) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Satisfactions");
            for (TrustmarkFrameworkIdentifiedObject satisfiesRef : supersedable.getSatisfies()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "Satisfies");
                writeString(xmlWriter, "Identifier", satisfiesRef.getIdentifier().toString());
                if (satisfiesRef.getName() != null)
                    writeString(xmlWriter, "Name", satisfiesRef.getName());
                if (satisfiesRef.getVersion() != null)
                    writeString(xmlWriter, "Version", satisfiesRef.getVersion());
                if (satisfiesRef.getDescription() != null)
                    writeCDataString(xmlWriter, "Description", satisfiesRef.getDescription());
                xmlWriter.writeEndElement(); //end tf:Satisfies
            }
            xmlWriter.writeEndElement(); //end tf:Satisfactions
        }
    }

    public static void writeKnownConflicts(XMLStreamWriter xmlWriter, Supersedable supersedable) throws XMLStreamException {
        if (supersedable.getKnownConflicts() != null && supersedable.getKnownConflicts().size() > 0) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "KnownConflicts");
            for (TrustmarkFrameworkIdentifiedObject knownConflictsRef : supersedable.getKnownConflicts()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "KnownConflict");
                writeString(xmlWriter, "Identifier", knownConflictsRef.getIdentifier().toString());
                if (knownConflictsRef.getName() != null)
                    writeString(xmlWriter, "Name", knownConflictsRef.getName());
                if (knownConflictsRef.getVersion() != null)
                    writeString(xmlWriter, "Version", knownConflictsRef.getVersion());
                if (knownConflictsRef.getDescription() != null)
                    writeCDataString(xmlWriter, "Description", knownConflictsRef.getDescription());
                xmlWriter.writeEndElement(); //end tf:KnownConflict
            }
            xmlWriter.writeEndElement(); //end tf:KnownConflicts
        }
    }

    public static void writeDeprecated(XMLStreamWriter xmlWriter, Supersedable supersedable) throws XMLStreamException {
        log.debug("supersedable.isDeprecated(): " + supersedable.isDeprecated());
        if (supersedable.isDeprecated()) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Deprecated");
            xmlWriter.writeCharacters("true");
            xmlWriter.writeEndElement();
        }
    }

    public static void writeSupersessionInfo(XMLStreamWriter xmlWriter, Supersedable supersedable) throws XMLStreamException {
        if (supersedable.getSupersedes() != null && supersedable.getSupersedes().size() > 0 || supersedable.getSupersededBy() != null && supersedable.getSupersededBy().size() > 0) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Supersessions");
            if (supersedable.getSupersedes() != null && supersedable.getSupersedes().size() > 0) {
                for (TrustmarkFrameworkIdentifiedObject supersedes : supersedable.getSupersedes()) {
                    xmlWriter.writeStartElement(NAMESPACE_URI, "Supersedes");
                    writeString(xmlWriter, "Identifier", supersedes.getIdentifier().toString());
                    if (supersedes.getName() != null)
                        writeString(xmlWriter, "Name", supersedes.getName());
                    if (supersedes.getVersion() != null)
                        writeString(xmlWriter, "Version", supersedes.getVersion());
                    if (supersedes.getDescription() != null)
                        writeCDataString(xmlWriter, "Description", supersedes.getDescription());
                    xmlWriter.writeEndElement(); //end tf:Supersedes
                }
            }
            if (supersedable.getSupersededBy() != null && supersedable.getSupersededBy().size() > 0) {
                for (TrustmarkFrameworkIdentifiedObject supersededBy : supersedable.getSupersededBy()) {
                    xmlWriter.writeStartElement(NAMESPACE_URI, "SupersededBy");
                    writeString(xmlWriter, "Identifier", supersededBy.getIdentifier().toString());
                    if (supersededBy.getName() != null)
                        writeString(xmlWriter, "Name", supersededBy.getName());
                    if (supersededBy.getVersion() != null)
                        writeString(xmlWriter, "Version", supersededBy.getVersion());
                    if (supersededBy.getDescription() != null)
                        writeCDataString(xmlWriter, "Description", supersededBy.getDescription());
                    xmlWriter.writeEndElement(); //end tf:Supersedes
                }
            }
            xmlWriter.writeEndElement(); // end tf:Supersessions
        }
    }


}//end AbstractXmlProducer
