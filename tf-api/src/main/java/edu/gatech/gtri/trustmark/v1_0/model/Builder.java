package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Provides generic builder methods for system wide objects.
 * <br/><br/>
 * @author brad
 * @date 3/14/17
 */
public interface Builder {

    public Entity buildEntity(URI uri, String name, String contactEmail);
    public Entity buildEntity(String uri, String name, String contactEmail) throws URISyntaxException;
    public Entity buildEntity(URI uri, String name, Contact contact);
    public Entity buildEntity(String uri, String name, Contact contact) throws URISyntaxException;
    public Entity getEntityByUri(String uri);
    public Entity getEntityByName(String name);
    public Builder addContact(Entity entity, Contact c);

    public Contact buildContact(String email);
    public Contact buildContact(String responder, String email);
    public Contact getContact(String email);
    public Builder setContactKind(Contact contact, ContactKindCode kind);
    public Builder setContactResponder(Contact contact, String responder);
    public Builder setContactNotes(Contact contact, String notes);
    public Builder addContactEmail(Contact contact, String email);
    public Builder addContactTelephone(Contact contact, String telephone);
    public Builder addContactMailingAddress(Contact contact, String addr);
    public Builder addContactPhysicalAddress(Contact contact, String addr);
    public Builder addContactWebsiteURL(Contact contact, URL url);
    public Builder addContactWebsiteURL(Contact contact, String url) throws MalformedURLException;


}/* end Builder */
