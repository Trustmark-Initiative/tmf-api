package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class EntityXmlProducer implements XmlProducer<Entity> {

    private static final Logger log = LogManager.getLogger(EntityXmlProducer.class);

    @Override
    public Class<Entity> getSupportedType() {
        return Entity.class;
    }

    @Override
    public void serialize(Entity entity, XMLStreamWriter xmlWriter) throws XMLStreamException {
        xmlWriter.writeStartElement(NAMESPACE_URI, "Identifier");
        xmlWriter.writeCharacters(entity.getIdentifier().toString());
        xmlWriter.writeEndElement(); //end "Identifier"

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(entity.getName());
        xmlWriter.writeEndElement(); //end "Name"

        if (entity.getContacts() != null && !entity.getContacts().isEmpty()) {
            log.debug("entity[" + entity.getIdentifier().toString() + "] has " + entity.getContacts().size() + " contacts.");
            for (Contact contact : entity.getContacts()) {
                if (contact == null)
                    throw new UnsupportedOperationException("A Contact under an entity[" + entity.getIdentifier().toString() + "] was found to be null!");
                xmlWriter.writeStartElement(NAMESPACE_URI, "Contact");
                XmlProducerUtility.writeXml(contact, xmlWriter);
                xmlWriter.writeEndElement();
            }
        }

    }//end serialize()

}//end class EntityXmlProducer
