package edu.gatech.gtri.trustmark.v1_0.io.xml;


import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Created by brad on 1/7/16.
 */
public interface XmlProducer {

    /**
     * This is how the system determines to use your custom producer, based on the type of object being serialized.
     */
    public Class getSupportedType();

    /**
     * Serialization of XML depends on the default javax.xml.stream APIs built into the JDK.  Please see
     * {@link XMLStreamWriter} for more information on how to produce XML within the "stream".  Do not start/end
     * document, or close the stream - this will be handled outside the producer.
     */
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException;


}
