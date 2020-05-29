package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class EntityXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(EntityXmlProducer.class);

    @Override
    public Class getSupportedType() {
        return Entity.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof Entity) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Entity entity = (Entity) instance;

        xmlWriter.writeStartElement(NAMESPACE_URI, "Identifier");
        xmlWriter.writeCharacters(entity.getIdentifier().toString());
        xmlWriter.writeEndElement(); //end "Identifier"

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(entity.getName());
        xmlWriter.writeEndElement(); //end "Name"

        if( entity.getContacts() != null && !entity.getContacts().isEmpty() ){
            log.debug("entity["+entity.getIdentifier().toString()+"] has "+entity.getContacts().size()+" contacts.");
            for( Contact contact : entity.getContacts() ){
                if( contact == null )
                    throw new UnsupportedOperationException("A Contact under an entity["+entity.getIdentifier().toString()+"] was found to be null!");
                xmlWriter.writeStartElement(NAMESPACE_URI, "Contact");
                writeXml(contact, xmlWriter);
                xmlWriter.writeEndElement();
            }
        }

    }//end serialize()

}//end class EntityXmlProducer