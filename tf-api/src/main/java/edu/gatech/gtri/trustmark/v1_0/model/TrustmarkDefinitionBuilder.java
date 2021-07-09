package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * An implementation of a Builder interface paradigm for creating Trustmark Definitions in memory.
 * <br/><br/>
 * @author brad
 * @date 3/14/17
 */
public interface TrustmarkDefinitionBuilder extends Builder {

    /**
     * Responsible for building this TrustmarkDefinition when all appropriate builder methods have been called. (ie,
     * validation will occur and stop this prior to build if TD is invalid).
     */
    public TrustmarkDefinition build() throws BuilderException;

    /**
     * Similar to {@link TrustmarkDefinitionBuilder#build()}, but does not perform validation (and thus, you may get an
     * invalid TrustmarkDefinition object).
     */
    public TrustmarkDefinition buildWithNoValidation();


    //==================================================================================================================
    //  Field setting methods
    //==================================================================================================================
    public TrustmarkDefinitionBuilder setIdentifier(String uriString) throws URISyntaxException;
    public TrustmarkDefinitionBuilder setIdentifier(URI uri);
    public TrustmarkDefinitionBuilder setName(String name);
    public TrustmarkDefinitionBuilder setVersion(String version);
    public TrustmarkDefinitionBuilder setDescription(String description);

    public TrustmarkDefinitionBuilder setDeprecated(Boolean deprecated);

    public TrustmarkDefinitionBuilder addSupersedes(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject);
    public TrustmarkDefinitionBuilder addSupersedes(URI identifier);
    public TrustmarkDefinitionBuilder addSupersedes(String idString) throws URISyntaxException;
    public TrustmarkDefinitionBuilder addSupersedes(String idString, String name, String version) throws URISyntaxException;
    public TrustmarkDefinitionBuilder addSupersedes(String idString, String name, String version, String description) throws URISyntaxException;

    public TrustmarkDefinitionBuilder addSupersededBy(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject);
    public TrustmarkDefinitionBuilder addSupersededBy(URI identifier);
    public TrustmarkDefinitionBuilder addSupersededBy(String idString) throws URISyntaxException;
    public TrustmarkDefinitionBuilder addSupersededBy(String idString, String name, String version) throws URISyntaxException;
    public TrustmarkDefinitionBuilder addSupersededBy(String idString, String name, String version, String description) throws URISyntaxException;

    public TrustmarkDefinitionBuilder addSatisfies(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject);
    public TrustmarkDefinitionBuilder addSatisfies(URI identifier);
    public TrustmarkDefinitionBuilder addSatisfies(String idString) throws URISyntaxException;
    public TrustmarkDefinitionBuilder addSatisfies(String idString, String name, String version) throws URISyntaxException;
    public TrustmarkDefinitionBuilder addSatisfies(String idString, String name, String version, String description) throws URISyntaxException;

    public TrustmarkDefinitionBuilder addKnownConflict(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject);
    public TrustmarkDefinitionBuilder addKnownConflict(URI identifier);
    public TrustmarkDefinitionBuilder addKnownConflict(String idString) throws URISyntaxException;
    public TrustmarkDefinitionBuilder addKnownConflict(String idString, String name, String version) throws URISyntaxException;
    public TrustmarkDefinitionBuilder addKnownConflict(String idString, String name, String version, String description) throws URISyntaxException;

    public TrustmarkDefinitionBuilder addKeyword(String keyword);

    public TrustmarkDefinitionBuilder setPublicationDateTime(Date dateTime);
    public TrustmarkDefinitionBuilder setPublicationDateTime(Long millis);

    public TrustmarkDefinitionBuilder setLegalNotice(String notice);
    public TrustmarkDefinitionBuilder setNotes(String notes);

//    public TrustmarkDefinitionBuilder setTrustmarkReferenceAttributeName(String uriString) throws URISyntaxException;
//    public TrustmarkDefinitionBuilder setTrustmarkReferenceAttributeName(URI uri);

    public TrustmarkDefinitionBuilder setTrustmarkDefiningOrganization(Entity entity);
    public TrustmarkDefinitionBuilder setTrustmarkDefiningOrganization(URI uri, String name, String contactName, String contactEmail);
    public TrustmarkDefinitionBuilder setTrustmarkDefiningOrganization(String uri, String name, String contactName, String contactEmail) throws URISyntaxException;

    public TrustmarkDefinitionBuilder setTargetStakeholderDescription(String desc);
    public TrustmarkDefinitionBuilder setTargetRecipientDescription(String desc);
    public TrustmarkDefinitionBuilder setTargetRelyingPartyDescription(String desc);
    public TrustmarkDefinitionBuilder setTargetProviderDescription(String desc);
    public TrustmarkDefinitionBuilder setProviderEligibilityCriteria(String desc);
    public TrustmarkDefinitionBuilder setAssessorQualificationsDescription(String desc);
    public TrustmarkDefinitionBuilder setTrustmarkRevocationCriteria(String desc);
    public TrustmarkDefinitionBuilder setExtensionDescription(String desc);

    public TrustmarkDefinitionBuilder setIssuanceCriteria(String criteria);
    public TrustmarkDefinitionBuilder setConformanceCriteriaPreface(String criteria);
    public TrustmarkDefinitionBuilder setAssessmentStepPreface(String criteria);

    public TrustmarkDefinitionBuilder addTerm(String name, String desc);
    public TrustmarkDefinitionBuilder addTerm(String name, String desc, String ... abbreviations);
    public TrustmarkDefinitionBuilder addTerm(String name, String desc, Collection<String> abbreviations);

    public TrustmarkDefinitionBuilder addSource(String identifier, String reference);
    public Source getSourceByIdentifier(String id); // For cross-linking

    public TrustmarkDefinitionBuilder addCriterion(String name, String description);
    public ConformanceCriterion getCriterionByName(String name);
    public TrustmarkDefinitionBuilder addCitation(ConformanceCriterion criterion, Source source, String location);

    public TrustmarkDefinitionBuilder addAssessmentStep(String name, String description);
    public AssessmentStep getAssessmentStepByName(String name); // For cross-linking
    public TrustmarkDefinitionBuilder addArtifact(AssessmentStep step, String name, String description);
    public TrustmarkDefinitionBuilder addCriterionLink(AssessmentStep step, ConformanceCriterion criterion);
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind);
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind, String displayName);
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind, String displayName, String description);
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind, String displayName, String description, Boolean required);
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind, String displayName, String description, Boolean required, List<String> enumValues);

}/* end TrustmarkDefinitionBuilder */
