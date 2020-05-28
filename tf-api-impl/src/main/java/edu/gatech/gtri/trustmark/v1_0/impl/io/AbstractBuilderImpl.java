package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.model.ContactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.model.Builder;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Implements the default builder methods, making them available to all sub-classes.
 * <br/><br/>
 * @author brad
 * @date 3/14/17
 */
public abstract class AbstractBuilderImpl implements Builder {
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    protected List<Entity> entities = new ArrayList<>();
    protected List<Contact> contacts = new ArrayList<>();
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    public List<Entity> getEntities(){
        if( this.entities == null )
            this.entities = new ArrayList<>();
        return this.entities;
    }
    public List<Contact> getContacts(){
        if( this.contacts == null )
            this.contacts = new ArrayList<>();
        return this.contacts;
    }
    //==================================================================================================================
    //  INTERFACE METHODS
    //==================================================================================================================
    protected String generateId(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll(Pattern.quote("-"), "");
        uuid = uuid.toUpperCase();
        return uuid;
    }


    @Override
    public Entity buildEntity(URI uri, String name, String contactEmail) {
        EntityImpl entity = (EntityImpl) this.getEntityByName(name);
        if( entity == null ){
            entity = new EntityImpl();
            entity.setName( name );
            this.entities.add(entity);
        }
        entity.setIdentifier(uri);
        Contact c = buildContact(contactEmail);
        entity.addContact(c);
        return entity;
    }

    @Override
    public Entity buildEntity(String uri, String name, String contactEmail) throws URISyntaxException {
        return this.buildEntity(new URI(uri), name, contactEmail);
    }

    @Override
    public Entity buildEntity(URI uri, String name, Contact contact) {
        EntityImpl entity = (EntityImpl) this.getEntityByName(name);
        if( entity == null ){
            entity = new EntityImpl();
            entity.setName( name );
            this.entities.add(entity);
        }
        entity.setIdentifier(uri);
        Contact already = this.getContact(contact.getDefaultEmail());
        if( already != null ){
            entity.addContact(already);
        }else{
            entity.addContact(contact);
            this.contacts.add(contact);
        }
        return entity;
    }

    @Override
    public Entity buildEntity(String uri, String name, Contact contact) throws URISyntaxException {
        return this.buildEntity(new URI(uri), name, contact);
    }

    @Override
    public Entity getEntityByUri(String uri) {
        for( Entity e : this.entities ){
            if( e.getIdentifier().toString().equalsIgnoreCase(uri.trim()) ){
                return e;
            }
        }
        return null;
    }

    @Override
    public Entity getEntityByName(String name) {
        for( Entity e : this.entities ){
            if( e.getName().trim().equalsIgnoreCase(name.trim()) ){
                return e;
            }
        }
        return null;
    }

    @Override
    public Builder addContact(Entity entity, Contact c) {
        Contact already = this.getContact(c.getDefaultEmail());
        if( already != null )
            ((EntityImpl) entity).addContact(already);
        else{
            ((EntityImpl) entity).addContact(c);
            this.contacts.add(c);
        }
        return this;
    }

    @Override
    public Contact buildContact(String email) {
        Contact existing = getContact(email);
        if( existing != null )
            return existing;

        ContactImpl contact = new ContactImpl();
        contact.setKind(ContactKindCode.PRIMARY);
        contact.addEmail(email);
        this.contacts.add(contact);
        return contact;
    }

    @Override
    public Contact buildContact(String responder, String email) {
        ContactImpl existing = (ContactImpl) getContact(email);
        if( existing != null ) {
            existing.setResponder(responder);
            return existing;
        }

        ContactImpl contact = new ContactImpl();
        contact.setKind(ContactKindCode.PRIMARY);
        contact.addEmail(email);
        contact.setResponder(responder);
        this.contacts.add(contact);
        return contact;
    }

    @Override
    public Contact getContact(String email) {
        for( Contact c : this.contacts ){
            for( String curEmail : c.getEmails() ){
                if( curEmail.trim().equalsIgnoreCase(email) ){
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public Builder setContactKind(Contact contact, ContactKindCode kind) {
        ((ContactImpl) contact).setKind(kind);
        return this;
    }

    @Override
    public Builder setContactResponder(Contact contact, String responder) {
        ((ContactImpl) contact).setResponder(responder);
        return this;
    }

    @Override
    public Builder setContactNotes(Contact contact, String notes) {
        ((ContactImpl) contact).setNotes(notes);
        return this;
    }

    @Override
    public Builder addContactEmail(Contact contact, String email) {
        ContactImpl impl = (ContactImpl) contact;
        if( !impl.getEmails().contains(email.trim()) ){
            impl.getEmails().add(email.trim());
        }
        return this;
    }

    @Override
    public Builder addContactTelephone(Contact contact, String telephone) {
        ContactImpl impl = (ContactImpl) contact;
        if( !impl.getTelephones().contains(telephone.trim()) ){
            impl.getTelephones().add(telephone.trim());
        }
        return this;
    }

    @Override
    public Builder addContactMailingAddress(Contact contact, String addr) {
        ContactImpl impl = (ContactImpl) contact;
        if( !impl.getMailingAddresses().contains(addr.trim()) ){
            impl.getMailingAddresses().add(addr.trim());
        }
        return this;
    }

    @Override
    public Builder addContactPhysicalAddress(Contact contact, String addr) {
        ContactImpl impl = (ContactImpl) contact;
        if( !impl.getPhysicalAddresses().contains(addr.trim()) ){
            impl.getPhysicalAddresses().add(addr.trim());
        }
        return this;
    }

    @Override
    public Builder addContactWebsiteURL(Contact contact, URL url) {
        ContactImpl impl = (ContactImpl) contact;
        if( !impl.getWebsiteURLs().contains(url) ){
            impl.getWebsiteURLs().add(url);
        }
        return this;
    }

    @Override
    public Builder addContactWebsiteURL(Contact contact, String url) throws MalformedURLException {
        return this.addContactWebsiteURL(contact, new URL(url));
    }


}/* end AbstractBuilderImpl */