package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Created by Nicholas on 02/01/2017.
 */
public abstract class AbstractDocumentXmlProducer<INPUT> implements XmlProducer<INPUT> {

    @Override
    public abstract Class<INPUT> getSupportedType();

    @Override
    public void serialize(INPUT instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        Codec<INPUT> codec = Codec.loadCodecFor(this.getSupportedType());
        codec.xmlSerializer.serializeRootObject(xmlWriter, instance);
    }
}
