package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Created by brad on 1/7/16.
 */
public class XmlStreamWriterDelegating implements XMLStreamWriter {

    public static final Logger log = LoggerFactory.getLogger(XmlStreamWriterDelegating.class);

    private XMLStreamWriter writer;
    public XmlStreamWriterDelegating(XMLStreamWriter writer) {
        this.writer = writer;
    }

    @Override
    public void writeStartElement(String localName) throws XMLStreamException {
        log.debug("START ELEMENT["+localName+"] CALLED!");
        writer.writeStartElement(localName);
    }

    @Override
    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
        log.debug("START ELEMENT["+namespaceURI+","+localName+"] CALLED!");
        writer.writeStartElement(namespaceURI, localName);
    }

    @Override
    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        log.debug("START ELEMENT["+prefix+", "+namespaceURI+","+localName+"] CALLED!");
        writer.writeStartElement(prefix, localName, namespaceURI);
    }

    @Override
    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
        writer.writeEmptyElement(namespaceURI, localName);
    }

    @Override
    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        writer.writeEmptyElement(prefix, localName, namespaceURI);
    }

    @Override
    public void writeEmptyElement(String localName) throws XMLStreamException {
        writer.writeEmptyElement(localName);
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        log.debug("END ELEMENT CALLED!");
        writer.writeEndElement();
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
        writer.writeEndDocument();
    }

    @Override
    public void close() throws XMLStreamException {
        writer.close();
    }

    @Override
    public void flush() throws XMLStreamException {
        writer.flush();
    }

    @Override
    public void writeAttribute(String localName, String value) throws XMLStreamException {
        writer.writeAttribute(localName, value);
    }

    @Override
    public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(prefix, namespaceURI, localName, value);
    }

    @Override
    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(namespaceURI, localName, value);
    }

    @Override
    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
        writer.writeNamespace(prefix, namespaceURI);
    }

    @Override
    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
        writer.writeDefaultNamespace(namespaceURI);
    }

    @Override
    public void writeComment(String data) throws XMLStreamException {
        writer.writeComment(data);
    }

    @Override
    public void writeProcessingInstruction(String target) throws XMLStreamException {
        writer.writeProcessingInstruction(target);
    }

    @Override
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        writer.writeProcessingInstruction(target, data);
    }

    @Override
    public void writeCData(String data) throws XMLStreamException {
        writer.writeCData(data);
    }

    @Override
    public void writeDTD(String dtd) throws XMLStreamException {
        writer.writeDTD(dtd);
    }

    @Override
    public void writeEntityRef(String name) throws XMLStreamException {
        writer.writeEntityRef(name);
    }

    @Override
    public void writeStartDocument() throws XMLStreamException {
        log.debug("START DOCUMENT CALLED");
        writer.writeStartDocument();
    }

    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        log.debug("START DOCUMENT CALLED");
        writer.writeStartDocument(version);
    }

    @Override
    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        log.debug("START DOCUMENT CALLED");
        writer.writeStartDocument(encoding, version);
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        writer.writeCharacters(text);
    }

    @Override
    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
        writer.writeCharacters(text, start, len);
    }

    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return writer.getPrefix(uri);
    }

    @Override
    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        writer.setPrefix(prefix, uri);
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        writer.setDefaultNamespace(uri);
    }

    @Override
    public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
        writer.setNamespaceContext(context);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return writer.getNamespaceContext();
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return writer.getProperty(name);
    }
}
