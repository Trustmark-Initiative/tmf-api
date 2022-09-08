package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.impl.io.bulk.BulkReadArtifact;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class TrustmarkDefinitionImpl extends TrustmarkFrameworkReferenceImpl implements TrustmarkDefinition, Comparable<TrustmarkDefinition>, TrustmarkDefinition.Metadata, BulkReadArtifact {

    private String id;
    private Entity trustmarkDefiningOrganization;
    private String targetStakeholderDescription;
    private String targetRecipientDescription;
    private String targetRelyingPartyDescription;
    private String targetProviderDescription;
    private String providerEligibilityCriteria;
    private String assessorQualificationsDescription;
    private String trustmarkRevocationCriteria;
    private String extensionDescription;
    private String conformanceCriteriaPreface;
    private String assessmentStepPreface;
    private String issuanceCriteria;
    private List<ConformanceCriterion> conformanceCriteria;
    private List<AssessmentStep> assessmentSteps;
    private transient Map<String, Object> transientDataMap;

    public TrustmarkDefinitionImpl() {
        super();
        this.id = null;
        this.trustmarkDefiningOrganization = null;
        this.targetStakeholderDescription = null;
        this.targetRecipientDescription = null;
        this.targetRelyingPartyDescription = null;
        this.targetProviderDescription = null;
        this.providerEligibilityCriteria = null;
        this.assessorQualificationsDescription = null;
        this.trustmarkRevocationCriteria = null;
        this.extensionDescription = null;
        this.conformanceCriteriaPreface = null;
        this.assessmentStepPreface = null;
        this.issuanceCriteria = null;
        this.conformanceCriteria = new ArrayList<>();
        this.assessmentSteps = new ArrayList<>();
        this.transientDataMap = new HashMap<>();
    }

    public TrustmarkDefinitionImpl(
            final String typeName,
            final URI identifier,
            final String name,
            final Integer number,
            final String version,
            final String description,
            final Date publicationDateTime,
            final String legalNotice,
            final String notes,
            final boolean deprecated,
            final List<String> keywords,
            final Collection<Source> sources,
            final List<TrustmarkFrameworkIdentifiedObject> supersedes,
            final List<TrustmarkFrameworkIdentifiedObject> supersededBy,
            final List<TrustmarkFrameworkIdentifiedObject> satisfies,
            final List<TrustmarkFrameworkIdentifiedObject> knownConflicts,
            final Collection<Term> terms,
            final String id,
            final Entity trustmarkDefiningOrganization,
            final String targetStakeholderDescription,
            final String targetRecipientDescription,
            final String targetRelyingPartyDescription,
            final String targetProviderDescription,
            final String providerEligibilityCriteria,
            final String assessorQualificationsDescription,
            final String trustmarkRevocationCriteria,
            final String extensionDescription,
            final String conformanceCriteriaPreface,
            final String assessmentStepPreface,
            final String issuanceCriteria,
            final List<ConformanceCriterion> conformanceCriteria,
            final List<AssessmentStep> assessmentSteps,
            final Map<String, Object> transientDataMap) {

        super(typeName, identifier, name, number, version, description, publicationDateTime, legalNotice, notes, deprecated, keywords, sources, supersedes, supersededBy, satisfies, knownConflicts, terms);
        this.id = id;
        this.trustmarkDefiningOrganization = trustmarkDefiningOrganization;
        this.targetStakeholderDescription = targetStakeholderDescription;
        this.targetRecipientDescription = targetRecipientDescription;
        this.targetRelyingPartyDescription = targetRelyingPartyDescription;
        this.targetProviderDescription = targetProviderDescription;
        this.providerEligibilityCriteria = providerEligibilityCriteria;
        this.assessorQualificationsDescription = assessorQualificationsDescription;
        this.trustmarkRevocationCriteria = trustmarkRevocationCriteria;
        this.extensionDescription = extensionDescription;
        this.conformanceCriteriaPreface = conformanceCriteriaPreface;
        this.assessmentStepPreface = assessmentStepPreface;
        this.issuanceCriteria = issuanceCriteria;
        this.conformanceCriteria = conformanceCriteria;
        this.assessmentSteps = assessmentSteps;
        this.transientDataMap = transientDataMap;
    }

    @Override
    public Metadata getMetadata() {
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public Entity getTrustmarkDefiningOrganization() {
        return trustmarkDefiningOrganization;
    }

    public void setTrustmarkDefiningOrganization(final Entity trustmarkDefiningOrganization) {
        this.trustmarkDefiningOrganization = trustmarkDefiningOrganization;
    }

    @Override
    public String getTargetStakeholderDescription() {
        return targetStakeholderDescription;
    }

    public void setTargetStakeholderDescription(final String targetStakeholderDescription) {
        this.targetStakeholderDescription = targetStakeholderDescription;
    }

    @Override
    public String getTargetRecipientDescription() {
        return targetRecipientDescription;
    }

    public void setTargetRecipientDescription(final String targetRecipientDescription) {
        this.targetRecipientDescription = targetRecipientDescription;
    }

    @Override
    public String getTargetRelyingPartyDescription() {
        return targetRelyingPartyDescription;
    }

    public void setTargetRelyingPartyDescription(final String targetRelyingPartyDescription) {
        this.targetRelyingPartyDescription = targetRelyingPartyDescription;
    }

    @Override
    public String getTargetProviderDescription() {
        return targetProviderDescription;
    }

    public void setTargetProviderDescription(final String targetProviderDescription) {
        this.targetProviderDescription = targetProviderDescription;
    }

    @Override
    public String getProviderEligibilityCriteria() {
        return providerEligibilityCriteria;
    }

    public void setProviderEligibilityCriteria(final String providerEligibilityCriteria) {
        this.providerEligibilityCriteria = providerEligibilityCriteria;
    }

    @Override
    public String getAssessorQualificationsDescription() {
        return assessorQualificationsDescription;
    }

    public void setAssessorQualificationsDescription(final String assessorQualificationsDescription) {
        this.assessorQualificationsDescription = assessorQualificationsDescription;
    }

    @Override
    public String getTrustmarkRevocationCriteria() {
        return trustmarkRevocationCriteria;
    }

    public void setTrustmarkRevocationCriteria(final String trustmarkRevocationCriteria) {
        this.trustmarkRevocationCriteria = trustmarkRevocationCriteria;
    }

    @Override
    public String getExtensionDescription() {
        return extensionDescription;
    }

    public void setExtensionDescription(final String extensionDescription) {
        this.extensionDescription = extensionDescription;
    }

    @Override
    public String getConformanceCriteriaPreface() {
        return conformanceCriteriaPreface;
    }

    public void setConformanceCriteriaPreface(final String conformanceCriteriaPreface) {
        this.conformanceCriteriaPreface = conformanceCriteriaPreface;
    }

    @Override
    public String getAssessmentStepPreface() {
        return assessmentStepPreface;
    }

    public void setAssessmentStepPreface(final String assessmentStepPreface) {
        this.assessmentStepPreface = assessmentStepPreface;
    }

    @Override
    public String getIssuanceCriteria() {
        return issuanceCriteria;
    }

    public void setIssuanceCriteria(final String issuanceCriteria) {
        this.issuanceCriteria = issuanceCriteria;
    }

    @Override
    public List<ConformanceCriterion> getConformanceCriteria() {
        return conformanceCriteria;
    }

    public void setConformanceCriteria(final List<ConformanceCriterion> conformanceCriteria) {
        this.conformanceCriteria = conformanceCriteria;
    }

    public void addConformanceCriterion(final ConformanceCriterion conformanceCriterion) {
        conformanceCriteria.add(conformanceCriterion);
    }

    @Override
    public List<AssessmentStep> getAssessmentSteps() {
        return assessmentSteps;
    }

    public void setAssessmentSteps(final List<AssessmentStep> assessmentSteps) {
        this.assessmentSteps = assessmentSteps;
    }

    public void addAssessmentStep(final AssessmentStep assessmentStep) {
        assessmentSteps.add(assessmentStep);
    }

    public Map<String, Object> getTransientDataMap() {
        return this.transientDataMap;
    }

    @Override
    public int compareTo(final TrustmarkDefinition trustmarkDefinition) {

        requireNonNull(trustmarkDefinition);

        return this.getName().compareTo(trustmarkDefinition.getName()) != 0 ?
                this.getName().compareTo(trustmarkDefinition.getName()) :
                this.getIdentifier().compareTo(trustmarkDefinition.getIdentifier());
    }
}
