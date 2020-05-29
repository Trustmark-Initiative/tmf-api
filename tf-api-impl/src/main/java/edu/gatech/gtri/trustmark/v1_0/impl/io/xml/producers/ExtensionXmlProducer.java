package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Extension;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


/**
 * Created by brad on 1/7/16.
 */
public class ExtensionXmlProducer extends AbstractXmlProducer implements XmlProducer {

    @Override
    public Class getSupportedType() {
        return Extension.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof Extension) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Extension extension = (Extension) instance;

        if( extension.getData() != null && !extension.getData().isEmpty() ){
            for( Object thing : extension.getData() ){
                writeXml(thing, xmlWriter);
            }
        }

    }//end serialize()


}//end TrustmarkStatusReportXmlProducer