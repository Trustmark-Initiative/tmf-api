package edu.gatech.gtri.trustmark.v1_0.io.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface XmlProducer<INPUT> {

    /**
     * This is how the system determines to use your custom producer, based on the type of object being serialized.
     */
    Class<INPUT> getSupportedType();

    /**
     * Serialization of XML depends on the default javax.xml.stream APIs built into the JDK.  Please see
     * {@link XMLStreamWriter} for more information on how to produce XML within the "stream".  Do not start/end
     * document, or close the stream - this will be handled outside the producer.
     */
    void serialize(INPUT instance, XMLStreamWriter xmlWriter) throws XMLStreamException;
}
