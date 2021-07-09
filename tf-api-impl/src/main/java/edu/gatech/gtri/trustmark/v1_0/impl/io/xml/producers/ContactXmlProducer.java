package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.net.URL;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class ContactXmlProducer implements XmlProducer<Contact> {


    @Override
    public Class<Contact> getSupportedType() {
        return Contact.class;
    }

    @Override
    public void serialize(Contact contact, XMLStreamWriter xmlWriter) throws XMLStreamException {
        xmlWriter.writeStartElement(NAMESPACE_URI, "Kind");
        if (contact.getKind() == null) {
            xmlWriter.writeCharacters(ContactKindCode.PRIMARY.toString());
        } else {
            xmlWriter.writeCharacters(contact.getKind().toString());
        }
        xmlWriter.writeEndElement();


        if (contact.getResponder() != null) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Responder");
            xmlWriter.writeCharacters(contact.getResponder());
            xmlWriter.writeEndElement();
        }

        for (String email : contact.getEmails()) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Email");
            xmlWriter.writeCharacters(email);
            xmlWriter.writeEndElement();
        }

        if (contact.getTelephones() != null && contact.getTelephones().size() > 0) {
            for (String tele : contact.getTelephones()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "Telephone");
                xmlWriter.writeCharacters(tele);
                xmlWriter.writeEndElement();
            }
        }

        if (contact.getPhysicalAddresses() != null && contact.getPhysicalAddresses().size() > 0) {
            for (String addr : contact.getPhysicalAddresses()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "PhysicalAddress");
                xmlWriter.writeCharacters(addr);
                xmlWriter.writeEndElement();
            }
        }

        if (contact.getMailingAddresses() != null && contact.getMailingAddresses().size() > 0) {
            for (String addr : contact.getMailingAddresses()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "MailingAddress");
                xmlWriter.writeCharacters(addr);
                xmlWriter.writeEndElement();
            }
        }

        if (contact.getWebsiteURLs() != null && contact.getWebsiteURLs().size() > 0) {
            for (URL url : contact.getWebsiteURLs()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "WebsiteURL");
                xmlWriter.writeCharacters(url.toString());
                xmlWriter.writeEndElement();
            }
        }

        if (contact.getNotes() != null) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Notes");
            xmlWriter.writeCharacters(contact.getNotes());
            xmlWriter.writeEndElement();
        }

    }//end serialize()

}//end class EntityXmlProducer
