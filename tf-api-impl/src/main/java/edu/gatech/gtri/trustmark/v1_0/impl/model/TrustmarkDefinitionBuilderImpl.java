package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractBuilderImpl;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.BuilderException;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionBuilder;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.model.ValidationBuilderException;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionUtils;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/15/17
 */
public class TrustmarkDefinitionBuilderImpl extends AbstractBuilderImpl implements TrustmarkDefinitionBuilder {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LoggerFactory.getLogger(TrustmarkDefinitionBuilderImpl.class);

    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public TrustmarkDefinitionBuilderImpl() {
        this.trustmarkDefinition = new TrustmarkDefinitionImpl();
        this.trustmarkDefinition.setIssuanceCriteria("yes(all)");
        this.trustmarkDefinition.setVersion("1.0");
        this.trustmarkDefinition.setPublicationDateTime(Calendar.getInstance().getTime());
    }

    //==================================================================================================================
    //  INSTANCE VARIABLES
    //==================================================================================================================
    private TrustmarkDefinitionImpl trustmarkDefinition;
    private List<TrustmarkFrameworkIdentifiedObjectImpl> trustmarkFrameworkIdentifiedObjects = new ArrayList<>();

    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================
    private TrustmarkDefinitionImpl getMetadata() {
        Object obj = this.trustmarkDefinition;
        return (TrustmarkDefinitionImpl) obj;
    }

    private TrustmarkFrameworkIdentifiedObjectImpl findTMIByIdentifier(URI id) {
        TrustmarkFrameworkIdentifiedObjectImpl obj = null;
        for (TrustmarkFrameworkIdentifiedObjectImpl cur : this.trustmarkFrameworkIdentifiedObjects) {
            if (cur.getIdentifier().equals(id)) {
                obj = cur;
                break;
            }
        }
        return obj;
    }

    public void addTrustmarkFrameworkIdentifiedObject(TrustmarkFrameworkIdentifiedObjectImpl obj) {
        if (this.trustmarkFrameworkIdentifiedObjects == null)
            this.trustmarkFrameworkIdentifiedObjects = new ArrayList<>();
        if (!this.trustmarkFrameworkIdentifiedObjects.contains(obj))
            this.trustmarkFrameworkIdentifiedObjects.add(obj);

    }
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

    @Override
    public TrustmarkDefinition build() throws BuilderException {
        log.debug("Validating Trustmark Definition...");
        TrustmarkDefinitionUtils utils = FactoryLoader.getInstance(TrustmarkDefinitionUtils.class);
        Collection<ValidationResult> validationResults = utils.validate(this.trustmarkDefinition);
        if (validationResults != null && validationResults.size() > 0) {
            ValidationBuilderException vbe = new ValidationBuilderException(validationResults);
            if (vbe.getFatalResults().size() > 0) {
                throw vbe;
            } else if (vbe.getWarningResults().size() > 0) {
                log.warn("Encountered " + vbe.getWarningResults().size() + " warnings: ");
                for (ValidationResult result : vbe.getWarningResults()) {
                    log.warn("   Result[" + result.getSeverity() + "]: " + result.getMessage());
                }
            }
        }

        log.debug("Returning Trustmark Definition...");
        return this.trustmarkDefinition;
    }

    @Override
    public TrustmarkDefinition buildWithNoValidation() {
        log.debug("Building trustmark definition with no validation [MAY BE INVALID]...");
        return this.trustmarkDefinition;
    }

    @Override
    public TrustmarkDefinitionBuilder setIdentifier(String uriString) throws URISyntaxException {
        return this.setIdentifier(new URI(uriString));
    }

    @Override
    public TrustmarkDefinitionBuilder setIdentifier(URI uri) {
        this.getMetadata().setIdentifier(uri);
//        if( this.getMetadata().getTrustmarkReferenceAttributeName() == null )
//            this.setTrustmarkReferenceAttributeName(uri);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setName(String name) {
        this.getMetadata().setName(name);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setVersion(String version) {
        this.getMetadata().setVersion(version);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setDescription(String description) {
        this.getMetadata().setDescription(description);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setDeprecated(Boolean deprecated) {
        this.getMetadata().setDeprecated(deprecated);
        return this;
    }


    @Override
    public TrustmarkDefinitionBuilder addSupersedes(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        this.getMetadata().addToSupersedes(trustmarkFrameworkIdentifiedObject);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addSupersedes(URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl tmfi = new TrustmarkFrameworkIdentifiedObjectImpl();
        tmfi.setIdentifier(identifier);
        return this.addSupersedes(tmfi);
    }

    @Override
    public TrustmarkDefinitionBuilder addSupersedes(String idString) throws URISyntaxException {
        return this.addSupersedes(new URI(idString));
    }

    @Override
    public TrustmarkDefinitionBuilder addSupersedes(String idString, String name, String version) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        return this.addSupersedes(impl);
    }

    @Override
    public TrustmarkDefinitionBuilder addSupersedes(String idString, String name, String version, String description)
            throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        impl.setDescription(description);
        return this;
    }


    @Override
    public TrustmarkDefinitionBuilder addSupersededBy(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        this.getMetadata().addToSupersededBy(trustmarkFrameworkIdentifiedObject);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addSupersededBy(URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(identifier);
        return this.addSupersededBy(impl);
    }

    @Override
    public TrustmarkDefinitionBuilder addSupersededBy(String idString) throws URISyntaxException {
        return this.addSupersededBy(new URI(idString));
    }

    @Override
    public TrustmarkDefinitionBuilder addSupersededBy(String idString, String name, String version) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        return this.addSupersededBy(impl);
    }

    @Override
    public TrustmarkDefinitionBuilder addSupersededBy(String idString, String name, String version, String description)
            throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        impl.setDescription(description);
        return this.addSupersededBy(impl);
    }


    @Override
    public TrustmarkDefinitionBuilder addSatisfies(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        this.getMetadata().addToSatisfies(trustmarkFrameworkIdentifiedObject);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addSatisfies(URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(identifier);
        return this.addSatisfies(impl);
    }

    @Override
    public TrustmarkDefinitionBuilder addSatisfies(String idString) throws URISyntaxException {
        return this.addSatisfies(new URI(idString));
    }

    @Override
    public TrustmarkDefinitionBuilder addSatisfies(String idString, String name, String version) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        return this.addSatisfies(impl);
    }

    @Override
    public TrustmarkDefinitionBuilder addSatisfies(String idString, String name, String version, String description)
            throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        impl.setDescription(description);
        return this.addSatisfies(impl);
    }


    @Override
    public TrustmarkDefinitionBuilder addKnownConflict(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        this.getMetadata().addToKnownConflict(trustmarkFrameworkIdentifiedObject);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addKnownConflict(URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(identifier);
        return this.addKnownConflict(impl);
    }

    @Override
    public TrustmarkDefinitionBuilder addKnownConflict(String idString) throws URISyntaxException {
        return this.addKnownConflict(new URI(idString));
    }

    @Override
    public TrustmarkDefinitionBuilder addKnownConflict(String idString, String name, String version) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        return this.addKnownConflict(impl);
    }

    @Override
    public TrustmarkDefinitionBuilder addKnownConflict(String idString, String name, String version, String description)
            throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        impl.setDescription(description);
        return this.addKnownConflict(impl);
    }

    @Override
    public TrustmarkDefinitionBuilder addKeyword(String keyword) {
        this.getMetadata().addToKeywords(keyword.trim());
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setPublicationDateTime(Date dateTime) {
        this.getMetadata().setPublicationDateTime(dateTime);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setPublicationDateTime(Long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return this.setPublicationDateTime(c.getTime());
    }

    @Override
    public TrustmarkDefinitionBuilder setLegalNotice(String notice) {
        this.getMetadata().setLegalNotice(notice);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setNotes(String notes) {
        this.getMetadata().setNotes(notes);
        return this;
    }

//    @Override
//    public TrustmarkDefinitionBuilder setTrustmarkReferenceAttributeName(String uriString) throws URISyntaxException {
//        return this.setTrustmarkReferenceAttributeName(new URI(uriString));
//    }
//
//    @Override
//    public TrustmarkDefinitionBuilder setTrustmarkReferenceAttributeName(URI uri) {
//        this.getMetadata().setTrustmarkReferenceAttributeName(uri);
//        return this;
//    }

    @Override
    public TrustmarkDefinitionBuilder setTrustmarkDefiningOrganization(Entity entity) {
        Entity already = this.getEntityByUri(entity.getIdentifier().toString());
        if (already != null) {
            ((EntityImpl) already).setName(entity.getName());
            // TODO Should we bother saving these contacts?
            ((EntityImpl) already).setContacts(entity.getContacts());
            this.getMetadata().setTrustmarkDefiningOrganization(already);
        } else {
            EntityImpl impl = new EntityImpl();
            impl.setIdentifier(entity.getIdentifier());
            impl.setName(entity.getName());
            impl.setContacts(entity.getContacts());
            this.getEntities().add(impl);
            this.getMetadata().setTrustmarkDefiningOrganization(impl);
        }
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setTrustmarkDefiningOrganization(URI uri, String name, String contactName, String contactEmail) {
        EntityImpl entity = new EntityImpl();
        entity.setIdentifier(uri);
        entity.setName(name);

        ContactImpl contact = new ContactImpl();
        contact.setKind(ContactKindCode.PRIMARY);
        contact.setResponder(contactName);
        contact.addEmail(contactEmail);

        entity.addContact(contact);

        return this.setTrustmarkDefiningOrganization(entity);
    }

    @Override
    public TrustmarkDefinitionBuilder setTrustmarkDefiningOrganization(String uri, String name, String contactName, String contactEmail)
            throws URISyntaxException {
        return this.setTrustmarkDefiningOrganization(new URI(uri), name, contactName, contactEmail);
    }

    @Override
    public TrustmarkDefinitionBuilder setTargetStakeholderDescription(String desc) {
        this.getMetadata().setTargetStakeholderDescription(desc);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setTargetRecipientDescription(String desc) {
        this.getMetadata().setTargetRecipientDescription(desc);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setTargetRelyingPartyDescription(String desc) {
        this.getMetadata().setTargetRelyingPartyDescription(desc);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setTargetProviderDescription(String desc) {
        this.getMetadata().setTargetProviderDescription(desc);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setProviderEligibilityCriteria(String desc) {
        this.getMetadata().setProviderEligibilityCriteria(desc);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setAssessorQualificationsDescription(String desc) {
        this.getMetadata().setAssessorQualificationsDescription(desc);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setTrustmarkRevocationCriteria(String desc) {
        this.getMetadata().setTrustmarkRevocationCriteria(desc);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setExtensionDescription(String desc) {
        this.getMetadata().setExtensionDescription(desc);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setIssuanceCriteria(String criteria) {
        this.trustmarkDefinition.setIssuanceCriteria(criteria);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setConformanceCriteriaPreface(String criteria) {
        this.trustmarkDefinition.setConformanceCriteriaPreface(criteria);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder setAssessmentStepPreface(String preface) {
        this.trustmarkDefinition.setAssessmentStepPreface(preface);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addTerm(String name, String desc) {
        TermImpl termImpl = new TermImpl();
        termImpl.setName(name);
        termImpl.setDefinition(desc);
        this.trustmarkDefinition.addTerm(termImpl);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addTerm(String name, String desc, String... abbreviations) {
        TermImpl termImpl = new TermImpl();
        termImpl.setName(name);
        termImpl.setDefinition(desc);
        for (String s : abbreviations) {
            termImpl.addAbbreviation(s.trim());
        }
        this.trustmarkDefinition.addTerm(termImpl);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addTerm(String name, String desc, Collection<String> abbreviations) {
        TermImpl termImpl = new TermImpl();
        termImpl.setName(name);
        termImpl.setDefinition(desc);
        for (String s : abbreviations) {
            termImpl.addAbbreviation(s.trim());
        }
        this.trustmarkDefinition.addTerm(termImpl);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addSource(String identifier, String reference) {
        SourceImpl already = (SourceImpl) this.getSourceByIdentifier(identifier);
        if (already != null) {
            already.setReference(reference);
        } else {
            SourceImpl source = new SourceImpl();
            source.setIdentifier(identifier);
            source.setReference(reference);
            this.trustmarkDefinition.addSource(source);
        }
        return this;
    }

    @Override
    public Source getSourceByIdentifier(String id) {
        if (this.trustmarkDefinition.getSources() != null) {
            for (Source s : this.trustmarkDefinition.getSources()) {
                if (s.getIdentifier().equalsIgnoreCase(id)) {
                    return s;
                }
            }
        }
        return null;
    }

    @Override
    public TrustmarkDefinitionBuilder addCriterion(String name, String description) {
        ConformanceCriterion already = this.getCriterionByName(name);
        if (already == null) {
            already = new ConformanceCriterionImpl();
            ((ConformanceCriterionImpl) already).setId(generateId());
            ((ConformanceCriterionImpl) already).setNumber(this.trustmarkDefinition.getConformanceCriteria().size() + 1);
            this.trustmarkDefinition.getConformanceCriteria().add(already);
        }

        ((ConformanceCriterionImpl) already).setName(name);
        ((ConformanceCriterionImpl) already).setDescription(description);

        return this;
    }

    @Override
    public ConformanceCriterion getCriterionByName(String name) {
        if (this.trustmarkDefinition.getConformanceCriteria() != null) {
            for (ConformanceCriterion crit : this.trustmarkDefinition.getConformanceCriteria()) {
                if (crit.getName().equalsIgnoreCase(name.trim())) {
                    return crit;
                }
            }
        }
        return null;
    }

    @Override
    public TrustmarkDefinitionBuilder addCitation(ConformanceCriterion criterion, Source source, String location) {
        CitationImpl citation = new CitationImpl();
        this.addSource(source.getIdentifier(), source.getReference());
        SourceImpl s1 = (SourceImpl) this.getSourceByIdentifier(source.getIdentifier());
        citation.setSource(s1);
        citation.setDescription(location);
        ((ConformanceCriterionImpl) criterion).addCitation(citation);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addAssessmentStep(String name, String description) {
        AssessmentStepImpl impl = (AssessmentStepImpl) this.getAssessmentStepByName(name);
        if (impl == null) {
            impl = new AssessmentStepImpl();
            impl.setId(generateId());
            impl.setName(name);
            impl.setDescription(description);
            impl.setNumber(this.trustmarkDefinition.getAssessmentSteps().size() + 1);
            this.trustmarkDefinition.addAssessmentStep(impl);
        } else {
            // In this case, we just override the description (they've already got a step with this name)
            impl.setDescription(description);
        }
        return this;
    }

    @Override
    public AssessmentStep getAssessmentStepByName(String name) {
        if (this.trustmarkDefinition.getAssessmentSteps() != null) {
            for (AssessmentStep step : this.trustmarkDefinition.getAssessmentSteps()) {
                if (step.getName().equalsIgnoreCase(name.trim())) {
                    return step;
                }
            }
        }
        return null;
    }

    @Override
    public TrustmarkDefinitionBuilder addArtifact(AssessmentStep step, String name, String description) {
        ArtifactImpl artifact = new ArtifactImpl();
        artifact.setName(name);
        artifact.setDescription(description);
        ((AssessmentStepImpl) step).addArtifact(artifact);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addCriterionLink(AssessmentStep step, ConformanceCriterion criterion) {
        ((AssessmentStepImpl) step).addConformanceCriterion(criterion);
        return this;
    }

    @Override
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind) {
        return this.addParameter(step, id, kind, id);
    }

    @Override
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind, String displayName) {
        return this.addParameter(step, id, kind, displayName, "No description given.");
    }

    @Override
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind, String displayName, String description) {
        return this.addParameter(step, id, kind, displayName, "No description given.", false);
    }

    @Override
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind, String displayName, String description, Boolean required) {
        return this.addParameter(step, id, kind, displayName, "No description given.", false, null);
    }

    @Override
    public TrustmarkDefinitionBuilder addParameter(AssessmentStep step, String id, ParameterKind kind, String displayName, String description, Boolean required, List<String> enumValues) {
        TrustmarkDefinitionParameterImpl param = new TrustmarkDefinitionParameterImpl();
        param.setIdentifier(id);
        param.setParameterKind(kind);
        param.setName(displayName);
        param.setDescription(description);
        param.setRequired(required);
        param.setEnumValues(enumValues);
        ((AssessmentStepImpl) step).addParameter(param);
        return this;
    }


}/* end TrustmarkDefinitionBuilderImpl */
