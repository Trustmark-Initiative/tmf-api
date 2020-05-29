package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;

/**
 * Provides methods which performs the Builder pattern for creating TrustInteroperabilityProfile instances.
 * <br/><br/>
 * Created by brad on 3/20/17.
 */
public interface TrustInteroperabilityProfileBuilder  extends Builder {

    /**
     * Responsible for building this TrustInteroperabilityProfile when all appropriate builder methods have been called. (ie,
     * validation will occur and stop this prior to build if TIP is invalid).
     */
    public TrustInteroperabilityProfile build() throws BuilderException;

    /**
     * Similar to {@link TrustInteroperabilityProfileBuilder#build()}, but does not perform validation (and thus, you may get an
     * invalid TrustInteroperabilityProfile object).
     */
    public TrustInteroperabilityProfile buildWithNoValidation();



    public TrustInteroperabilityProfileBuilder setIdentifier(URI uri);
    public TrustInteroperabilityProfileBuilder setIdentifier(String uriString) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder setName(String name);
    public TrustInteroperabilityProfileBuilder setVersion(String version);
    public TrustInteroperabilityProfileBuilder setDescription(String description);

    public TrustInteroperabilityProfileBuilder setDeprecated(Boolean deprecated);

    public TrustInteroperabilityProfileBuilder addSupersedes(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject);
    public TrustInteroperabilityProfileBuilder addSupersedes(URI identifier);
    public TrustInteroperabilityProfileBuilder addSupersedes(String idString) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addSupersedes(String idString, String name, String version) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addSupersedes(String idString, String name, String version, String description) throws URISyntaxException;

    public TrustInteroperabilityProfileBuilder addSupersededBy(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject);
    public TrustInteroperabilityProfileBuilder addSupersededBy(URI identifier);
    public TrustInteroperabilityProfileBuilder addSupersededBy(String idString) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addSupersededBy(String idString, String name, String version) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addSupersededBy(String idString, String name, String version, String description) throws URISyntaxException;

    public TrustInteroperabilityProfileBuilder addSatisfies(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject);
    public TrustInteroperabilityProfileBuilder addSatisfies(URI identifier);
    public TrustInteroperabilityProfileBuilder addSatisfies(String idString) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addSatisfies(String idString, String name, String version) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addSatisfies(String idString, String name, String version, String description) throws URISyntaxException;

    public TrustInteroperabilityProfileBuilder addKnownConflict(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject);
    public TrustInteroperabilityProfileBuilder addKnownConflict(URI identifier);
    public TrustInteroperabilityProfileBuilder addKnownConflict(String idString) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addKnownConflict(String idString, String name, String version) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addKnownConflict(String idString, String name, String version, String description) throws URISyntaxException;

    public TrustInteroperabilityProfileBuilder addKeyword(String keyword);

    public TrustInteroperabilityProfileBuilder setPublicationDateTime(Date dateTime);
    public TrustInteroperabilityProfileBuilder setPublicationDateTime(Long millis);

    public TrustInteroperabilityProfileBuilder setLegalNotice(String notice);
    public TrustInteroperabilityProfileBuilder setNotes(String notes);

    public TrustInteroperabilityProfileBuilder setIssuerOrganization(Entity entity);
    public TrustInteroperabilityProfileBuilder setIssuerOrganization(URI uri, String name, String contactName, String contactEmail);
    public TrustInteroperabilityProfileBuilder setIssuerOrganization(String uri, String name, String contactName, String contactEmail) throws URISyntaxException;

    public TrustInteroperabilityProfileBuilder addTrustInteroperabilityProfileReference(String id, TrustmarkFrameworkIdentifiedObject tmfi);
    public TrustInteroperabilityProfileBuilder addTrustInteroperabilityProfileReference(String id, URI identifier);
    public TrustInteroperabilityProfileBuilder addTrustInteroperabilityProfileReference(String id, String identifierString) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addTrustInteroperabilityProfileReference(String id, String identifierString, String name, String version, String description) throws URISyntaxException;

    public TrustInteroperabilityProfileBuilder addTrustmarkDefinitionRequirement(String id, TrustmarkFrameworkIdentifiedObject tmfi, Entity ... providerReferences);
    public TrustInteroperabilityProfileBuilder addTrustmarkDefinitionRequirement(String id, URI identifier, Entity ... providerReferences);
    public TrustInteroperabilityProfileBuilder addTrustmarkDefinitionRequirement(String id, String identifierString, Entity ... providerReferences) throws URISyntaxException;
    public TrustInteroperabilityProfileBuilder addTrustmarkDefinitionRequirement(String id, String identifierString, String name, String version, String description, Entity ... providerReferences) throws URISyntaxException;

    public TrustInteroperabilityProfileBuilder setTrustExpression(String trustExpression);

    public TrustInteroperabilityProfileBuilder addSource(String identifier, String reference);
    public Source getSourceByIdentifier(String id); // For cross-linking

    public TrustInteroperabilityProfileBuilder addTerm(String name, String desc);
    public TrustInteroperabilityProfileBuilder addTerm(String name, String desc, String ... abbreviations);
    public TrustInteroperabilityProfileBuilder addTerm(String name, String desc, Collection<String> abbreviations);

}
