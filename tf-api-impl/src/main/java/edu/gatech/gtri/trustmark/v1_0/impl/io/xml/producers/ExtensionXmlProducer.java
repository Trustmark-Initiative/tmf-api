package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Extension;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


/**
 * Created by brad on 1/7/16.
 */
public class ExtensionXmlProducer implements XmlProducer<Extension> {

    @Override
    public Class getSupportedType() {
        return Extension.class;
    }

    @Override
    public void serialize(Extension extension, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if (extension.getData() != null && !extension.getData().isEmpty()) {
            for (Object thing : extension.getData()) {
                XmlProducerUtility.writeXml(thing, xmlWriter);
            }
        }

    }//end serialize()


}//end TrustmarkStatusReportXmlProducer
