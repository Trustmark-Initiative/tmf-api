package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 12/7/15.
 */
public class EntityImpl implements Entity {

    private URI identifier;
    private String name;
    private List<Contact> contacts;

    @Override
    public URI getIdentifier() {
        return identifier;
    }

    public void setIdentifier(URI identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Contact> getContacts() {
        if( contacts == null )
            contacts = new ArrayList<Contact>();
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public Contact getDefaultContact() {
        if( !this.getContacts().isEmpty() )
            return this.getContacts().get(0);
        return null;
    }

    public void addContact(Contact contact){
        this.getContacts().add(contact);
    }

    public String toString() {
        return String.format("Entity[%s]", this.getIdentifier() == null ? "" : this.getIdentifier().toString());
    }


}
