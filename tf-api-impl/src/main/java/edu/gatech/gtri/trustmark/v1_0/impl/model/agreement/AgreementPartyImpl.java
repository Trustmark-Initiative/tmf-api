package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementParty;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibility;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementSignatory;

import java.net.URI;
import java.util.*;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AgreementPartyImpl implements AgreementParty {
    
    ////// Instance Fields //////
    protected String abbreviatedName;
    protected Entity organization;
    protected SortedSet<AgreementResponsibility> responsibilities;
    protected List<AgreementSignatory> signatories;
    
    
    ////// Instance Methods //////
    @Override
    public int compareTo(AgreementParty that) {
        if (that == null) { return -1; }
        Entity thisOrganization = this.getOrganization();
        URI thisIdentifier = thisOrganization.getIdentifier();
        Entity thatOrganization = that.getOrganization();
        URI thatIdentifier = thatOrganization.getIdentifier();
        return thisIdentifier.compareTo(thatIdentifier);
    }
    
    @Override
    public String getAbbreviatedName() { return this.abbreviatedName; }
    public void setAbbreviatedName(String abbreviatedName) { this.abbreviatedName = abbreviatedName; }
    
    @Override
    public Entity getOrganization() { return this.organization; }
    public void setOrganization(Entity organization) { this.organization = organization; }
    
    @Override
    public SortedSet<AgreementResponsibility> getResponsibilities() {
        if (this.responsibilities == null) { this.responsibilities = new TreeSet<>(); }
        return this.responsibilities;
    }
    public void setResponsibilities(Collection<? extends AgreementResponsibility> responsibilities) {
        this.getResponsibilities().clear();
        this.getResponsibilities().addAll(responsibilities);
    }
    public void addToResponsibilities(AgreementResponsibility responsibility) {
        this.getResponsibilities().add(responsibility);
    }
    
    @Override
    public List<AgreementSignatory> getSignatories() {
        if (this.signatories == null) { this.signatories = new ArrayList<>(); }
        return this.signatories;
    }
    public void setSignatories(Collection<? extends AgreementSignatory> signatories) {
        this.getSignatories().clear();
        this.getSignatories().addAll(signatories);
    }
    public void addToSignatories(AgreementSignatory signatory) {
        this.getSignatories().add(signatory);
    }
    
}
