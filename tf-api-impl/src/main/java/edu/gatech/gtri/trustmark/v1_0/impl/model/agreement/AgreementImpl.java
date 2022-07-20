package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementAttachment;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementLegalSection;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementNonBindingSection;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementParty;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibility;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.ListStyleType;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Nicholas on 01/27/2017.
 */
public class AgreementImpl implements Agreement {

    ////// Instance Fields //////

    //// TrustmarkFrameworkIdentifiedObject ////
    protected URI identifier;
    protected String name;
    protected String version;
    protected String description;
    protected Integer number;

    //// AbstractAgreementLegalSectionParent ////
    protected ListStyleType listStyleType;
    protected List<AgreementLegalSection> legalSections;

    //// Agreement ////
    protected Date creationDateTime;
    protected Date effectiveDateTime;
    protected Date terminationDateTime;
    protected List<AgreementNonBindingSection> nonBindingSections;
    protected SortedSet<Term> terms;
    protected SortedSet<AgreementParty> parties;
    protected SortedSet<AgreementResponsibility> responsibilities;
    protected String signaturePageText;
    protected List<AgreementAttachment> attachments;


    ////// Instance Methods //////

    //// TrustmarkFrameworkIdentifiedObject ////
    @Override
    public String getTypeName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public URI getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(URI identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //// AbstractAgreementLegalSectionParent ////
    @Override
    public ListStyleType getListStyleType() {
        return this.listStyleType;
    }

    public void setListStyleType(ListStyleType listStyleType) {
        this.listStyleType = listStyleType;
    }

    @Override
    public List<AgreementLegalSection> getLegalSections() {
        if (this.legalSections == null) {
            this.legalSections = new ArrayList<>();
        }
        return this.legalSections;
    }

    public void setLegalSections(Collection<? extends AgreementLegalSection> legalSections) {
        this.getLegalSections().clear();
        this.getLegalSections().addAll(legalSections);
    }

    public void addToLegalSections(AgreementLegalSection legalSection) {
        this.getLegalSections().add(legalSection);
    }

    //// Agreement ////
    @Override
    public String getTitle() {
        return this.getName();
    }

    public void setTitle(String title) {
        this.setName(title);
    }

    @Override
    public Date getCreationDateTime() {
        return this.creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public Date getEffectiveDateTime() {
        return this.effectiveDateTime;
    }

    public void setEffectiveDateTime(Date effectiveDateTime) {
        this.effectiveDateTime = effectiveDateTime;
    }

    @Override
    public Date getTerminationDateTime() {
        return this.terminationDateTime;
    }

    public void setTerminationDateTime(Date terminationDateTime) {
        this.terminationDateTime = terminationDateTime;
    }

    @Override
    public List<AgreementNonBindingSection> getNonBindingSections() {
        if (this.nonBindingSections == null) {
            this.nonBindingSections = new ArrayList<>();
        }
        return this.nonBindingSections;
    }

    public void setNonBindingSections(Collection<? extends AgreementNonBindingSection> nonBindingSections) {
        this.getNonBindingSections().clear();
        this.getNonBindingSections().addAll(nonBindingSections);
    }

    public void addToNonBindingSections(AgreementNonBindingSection nonBindingSection) {
        this.getNonBindingSections().add(nonBindingSection);
    }

    @Override
    public SortedSet<Term> getTerms() {
        if (this.terms == null) {
            this.terms = new TreeSet<>();
        }
        return this.terms;
    }

    public void setTerms(Collection<? extends Term> terms) {
        this.getTerms().clear();
        this.getTerms().addAll(terms);
    }

    public void addToTerms(Term term) {
        this.getTerms().add(term);
    }

    @Override
    public SortedSet<AgreementParty> getParties() {
        if (this.parties == null) {
            this.parties = new TreeSet<>();
        }
        return this.parties;
    }

    public void setParties(Collection<? extends AgreementParty> parties) {
        this.getParties().clear();
        this.getParties().addAll(parties);
    }

    public void addToParties(AgreementParty party) {
        this.getParties().add(party);
    }

    @Override
    public SortedSet<AgreementResponsibility> getResponsibilities() {
        if (this.responsibilities == null) {
            this.responsibilities = new TreeSet<>();
        }
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
    public Map<AgreementResponsibility, Collection<AgreementParty>> getMappingFromResponsibilityToParties() {
        SetValuedMap<AgreementResponsibility, AgreementParty> result = new HashSetValuedHashMap<>();
        for (AgreementParty party : this.getParties()) {
            if (party == null) {
                continue;
            }
            for (AgreementResponsibility responsibility : party.getResponsibilities()) {
                if (responsibility == null) {
                    continue;
                }
                result.put(responsibility, party);
            }
        }
        return result.asMap();
    }

    @Override
    public String getSignaturePageText() {
        return this.signaturePageText;
    }

    public void setSignaturePageText(String signaturePageText) {
        this.signaturePageText = signaturePageText;
    }

    @Override
    public List<AgreementAttachment> getAttachments() {
        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }
        return this.attachments;
    }

    public void setAttachments(Collection<? extends AgreementAttachment> attachments) {
        this.getAttachments().clear();
        this.getAttachments().addAll(attachments);
    }

    public void addToAttachments(AgreementAttachment attachment) {
        this.getAttachments().add(attachment);
    }

}
