package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 12/7/15.
 */
public class ContactImpl implements Contact {

    private ContactKindCode kind;
    private String responder;
    private List<String> emails;
    private List<String> telephones;
    private List<String> physicalAddresses;
    private List<String> mailingAddresses;
    private List<URL> websiteURLs;
    private String notes;

    @Override
    public ContactKindCode getKind() {
        return kind;
    }

    public void setKind(ContactKindCode kind) {
        this.kind = kind;
    }

    @Override
    public String getResponder() {
        return responder;
    }

    public void setResponder(String responder) {
        this.responder = responder;
    }

    @Override
    public List<String> getEmails() {
        if( emails == null )
            emails = new ArrayList<String>();
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void addEmail(String email){
        this.getEmails().add(email);
    }

    @Override
    public List<String> getTelephones() {
        if( telephones == null )
            telephones = new ArrayList<String>();
        return telephones;
    }

    public void setTelephones(List<String> telephones) {
        this.telephones = telephones;
    }

    public void addTelephone(String tele){
        this.getTelephones().add(tele);
    }

    @Override
    public List<String> getPhysicalAddresses() {
        if( physicalAddresses == null )
            physicalAddresses = new ArrayList<String>();
        return physicalAddresses;
    }

    public void setPhysicalAddresses(List<String> physicalAddresses) {
        this.physicalAddresses = physicalAddresses;
    }

    public void addPhysicalAddress(String addr){
        this.getPhysicalAddresses().add(addr);
    }

    @Override
    public List<String> getMailingAddresses() {
        if( mailingAddresses == null )
            mailingAddresses = new ArrayList<String>();
        return mailingAddresses;
    }

    public void setMailingAddresses(List<String> mailingAddresses) {
        this.mailingAddresses = mailingAddresses;
    }

    public void addMailingAddress(String addr){
        this.getMailingAddresses().add(addr);
    }

    @Override
    public List<URL> getWebsiteURLs() {
        if( websiteURLs == null )
            websiteURLs = new ArrayList<URL>();
        return websiteURLs;
    }

    public void setWebsiteURLs(List<URL> websiteURLs) {
        this.websiteURLs = websiteURLs;
    }

    public void addWebsiteURL(URL url){
        this.getWebsiteURLs().add(url);
    }

    @Override
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    @Override
    public String getDefaultEmail() {
        if( !this.getEmails().isEmpty() )
            return this.getEmails().get(0);
        return null;
    }

    @Override
    public String getDefaultTelephone() {
        if( !this.getTelephones().isEmpty() )
            return this.getTelephones().get(0);
        return null;
    }

    @Override
    public String getDefaultPhysicalAddress() {
        if( !this.getPhysicalAddresses().isEmpty() )
            return this.getPhysicalAddresses().get(0);
        return null;
    }

    @Override
    public String getDefaultMailingAddress() {
        if( !this.getMailingAddresses().isEmpty() )
            return this.getMailingAddresses().get(0);
        return null;
    }

    @Override
    public URL getDefaultWebsiteURL() {
        if( !this.getWebsiteURLs().isEmpty() )
            return this.getWebsiteURLs().get(0);
        return null;
    }


    public String toString(){
        return String.format("Contact[%s - %s]", this.getResponder(), this.getDefaultEmail());
    }


}
