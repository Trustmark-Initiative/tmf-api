package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class TrustmarkFrameworkIdentifiedObjectXmlProducer extends AbstractXmlProducer implements XmlProducer {


    @Override
    public Class getSupportedType() {
        return TrustmarkFrameworkIdentifiedObject.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof TrustmarkFrameworkIdentifiedObject) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        TrustmarkFrameworkIdentifiedObject tfiObj = (TrustmarkFrameworkIdentifiedObject) instance;

        xmlWriter.writeStartElement(NAMESPACE_URI, "Identifier");
        xmlWriter.writeCharacters(tfiObj.getIdentifier().toString());
        xmlWriter.writeEndElement(); //end "Identifier"

        if(tfiObj.getNumber() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Number");
            xmlWriter.writeCharacters(tfiObj.getNumber().toString());
            xmlWriter.writeEndElement(); //end "Name"
        }

        if(tfiObj.getName() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
            xmlWriter.writeCharacters(tfiObj.getName());
            xmlWriter.writeEndElement(); //end "Name"
        }

        if(tfiObj.getVersion() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Version");
            xmlWriter.writeCharacters(tfiObj.getVersion());
            xmlWriter.writeEndElement(); //end "Version"
        }

        if(tfiObj.getDescription() != null ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "Description");
            xmlWriter.writeCharacters(tfiObj.getDescription());
            xmlWriter.writeEndElement(); //end "Name"
        }

    }//end serialize()



}
