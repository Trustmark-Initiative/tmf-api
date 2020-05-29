package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteOrganization;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/27/17
 */
public class RemoteOrganizationImpl extends RemoteObjectImpl implements RemoteOrganization {

    private URI identifier;
    private String name;
    private String abbreviation;
    private String logoImagePath;
    private List<Contact> contacts = new ArrayList<>();

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
        return contacts;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String getAbbreviation() {
        return abbreviation;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String getLogoImagePath() {
        return logoImagePath;
    }
    public void setLogoImagePath(String logoImagePath) {
        this.logoImagePath = logoImagePath;
    }

    @Override
    public Contact getDefaultContact() {
        Contact c = null;
        for( Contact cur : this.getContacts() ){
            if( cur.getKind() == ContactKindCode.PRIMARY ){
                c = cur;
                break;
            }
        }
        if( c == null && this.getContacts().size() > 0 )
            c = getContacts().get(0);
        return c;
    }
}/* end RemoteOrganizationImpl */