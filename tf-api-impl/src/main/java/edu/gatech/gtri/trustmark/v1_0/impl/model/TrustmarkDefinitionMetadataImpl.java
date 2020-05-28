package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brad on 12/7/15.
 */
public class TrustmarkDefinitionMetadataImpl extends TrustmarkFrameworkIdentifiedObjectImpl implements TrustmarkDefinition.Metadata {


    private URI trustmarkReferenceAttributeName;
    private Date publicationDateTime;
    private Entity trustmarkDefiningOrganization;
    private String targetStakeholderDescription;
    private String targetRecipientDescription;
    private String targetRelyingPartyDescription;
    private String targetProviderDescription;
    private String providerEligibilityCriteria;
    private String assessorQualificationsDescription;
    private String trustmarkRevocationCriteria;
    private String extensionDescription;
    private String legalNotice;
    private String notes;
    private Boolean deprecated = Boolean.FALSE;
    private List<TrustmarkFrameworkIdentifiedObject> supersedes;
    private List<TrustmarkFrameworkIdentifiedObject> supersededBy;
    private List<String> keywords;
    private List<TrustmarkFrameworkIdentifiedObject> satisfies;
    private List<TrustmarkFrameworkIdentifiedObject> knownConflicts;


    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getKnownConflicts() {
        if( knownConflicts == null )
            knownConflicts = new ArrayList<>();
        return knownConflicts;
    }

    public void setKnownConflicts(List<TrustmarkFrameworkIdentifiedObject> knownConflicts) {
        this.knownConflicts = knownConflicts;
    }

    public void addToKnownConflicts(TrustmarkFrameworkIdentifiedObject tmfi){
        this.getKnownConflicts().add(tmfi);
    }

    @Override
    public URI getTrustmarkReferenceAttributeName() {
        return trustmarkReferenceAttributeName;
    }

    public void setTrustmarkReferenceAttributeName(URI trustmarkReferenceAttributeName) {
        this.trustmarkReferenceAttributeName = trustmarkReferenceAttributeName;
    }

    @Override
    public Date getPublicationDateTime() {
        return publicationDateTime;
    }

    public void setPublicationDateTime(Date publicationDateTime) {
        this.publicationDateTime = publicationDateTime;
    }

    @Override
    public Entity getTrustmarkDefiningOrganization() {
        return trustmarkDefiningOrganization;
    }

    public void setTrustmarkDefiningOrganization(Entity trustmarkDefiningOrganization) {
        this.trustmarkDefiningOrganization = trustmarkDefiningOrganization;
    }

    @Override
    public String getTargetStakeholderDescription() {
        return targetStakeholderDescription;
    }

    public void setTargetStakeholderDescription(String targetStakeholderDescription) {
        this.targetStakeholderDescription = targetStakeholderDescription;
    }

    @Override
    public String getTargetRecipientDescription() {
        return targetRecipientDescription;
    }

    public void setTargetRecipientDescription(String targetRecipientDescription) {
        this.targetRecipientDescription = targetRecipientDescription;
    }

    @Override
    public String getTargetRelyingPartyDescription() {
        return targetRelyingPartyDescription;
    }

    public void setTargetRelyingPartyDescription(String targetRelyingPartyDescription) {
        this.targetRelyingPartyDescription = targetRelyingPartyDescription;
    }

    @Override
    public String getTargetProviderDescription() {
        return targetProviderDescription;
    }

    public void setTargetProviderDescription(String targetProviderDescription) {
        this.targetProviderDescription = targetProviderDescription;
    }

    @Override
    public String getProviderEligibilityCriteria() {
        return providerEligibilityCriteria;
    }

    public void setProviderEligibilityCriteria(String providerEligibilityCriteria) {
        this.providerEligibilityCriteria = providerEligibilityCriteria;
    }

    @Override
    public String getAssessorQualificationsDescription() {
        return assessorQualificationsDescription;
    }

    public void setAssessorQualificationsDescription(String assessorQualificationsDescription) {
        this.assessorQualificationsDescription = assessorQualificationsDescription;
    }

    @Override
    public String getTrustmarkRevocationCriteria() {
        return trustmarkRevocationCriteria;
    }

    public void setTrustmarkRevocationCriteria(String trustmarkRevocationCriteria) {
        this.trustmarkRevocationCriteria = trustmarkRevocationCriteria;
    }

    @Override
    public String getExtensionDescription() {
        return extensionDescription;
    }

    public void setExtensionDescription(String extensionDescription) {
        this.extensionDescription = extensionDescription;
    }

    @Override
    public String getLegalNotice() {
        return legalNotice;
    }

    public void setLegalNotice(String legalNotice) {
        this.legalNotice = legalNotice;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean isDeprecated(){
        return this.deprecated;
    }
    public Boolean getDeprecated(){
        return this.isDeprecated();
    }

    public void setDeprecated(Boolean deprecated){
        this.deprecated = deprecated;
    }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSupersedes() {
        if( supersedes == null )
            supersedes = new ArrayList<>();
        return supersedes;
    }

    public void setSupersedes(List<TrustmarkFrameworkIdentifiedObject> supersedes) {
        this.supersedes = supersedes;
    }
    public void addToSupersedes(TrustmarkFrameworkIdentifiedObject supersedes){
        if( !this.getSupersedes().contains(supersedes) )
            this.getSupersedes().add(supersedes);
    }
    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSupersededBy() {
        if( supersededBy == null )
            supersededBy = new ArrayList<>();
        return supersededBy;
    }

    public void setSupersededBy(List<TrustmarkFrameworkIdentifiedObject> supersededBy) {
        this.supersededBy = supersededBy;
    }
    public void addToSupersededBy(TrustmarkFrameworkIdentifiedObject supersededBy){
        if( !this.getSupersededBy().contains(supersededBy) )
            this.getSupersededBy().add(supersededBy);
    }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSatisfies() {
        if( satisfies == null )
            satisfies = new ArrayList<>();
        return satisfies;
    }

    public void setSatisfies(List<TrustmarkFrameworkIdentifiedObject> satisfies) {
        this.satisfies = satisfies;
    }
    public void addToSatisfies(TrustmarkFrameworkIdentifiedObject satisfies){
        if( !this.getSatisfies().contains(satisfies) )
            this.getSatisfies().add(satisfies);
    }

    @Override
    public List<String> getKeywords() {
        if( keywords == null )
            keywords = new ArrayList<>();
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    public void addToKeywords( String keyword ){
        if( !this.getKeywords().contains(keyword) )
            this.getKeywords().add(keyword);
    }
}
