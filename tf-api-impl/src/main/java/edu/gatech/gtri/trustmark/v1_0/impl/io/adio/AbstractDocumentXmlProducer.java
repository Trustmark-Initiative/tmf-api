package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Created by Nicholas on 02/01/2017.
 */
public abstract class AbstractDocumentXmlProducer<T> implements XmlProducer {
    
    ////// Instance Methods - Abstract //////

    @Override
    public abstract Class<T> getSupportedType();


    ////// Instance Methods - Concrete //////

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        Codec<T> codec = Codec.loadCodecFor(this.getSupportedType());
        codec.xmlSerializer.serializeRootObject(xmlWriter, instance);
    }
    
}
