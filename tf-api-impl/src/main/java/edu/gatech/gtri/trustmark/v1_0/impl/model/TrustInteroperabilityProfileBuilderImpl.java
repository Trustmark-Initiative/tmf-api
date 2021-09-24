package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractBuilderImpl;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.util.TrustInteroperabilityProfileUtils;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionUtils;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/15/17
 */
public class TrustInteroperabilityProfileBuilderImpl extends AbstractBuilderImpl implements TrustInteroperabilityProfileBuilder {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LogManager.getLogger(TrustInteroperabilityProfileBuilderImpl.class);
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public TrustInteroperabilityProfileBuilderImpl(){
        this.tip = new TrustInteroperabilityProfileImpl();
        this.tip.setVersion("1.0");
        this.tip.setPublicationDateTime(Calendar.getInstance().getTime());
        this.tip.setDeprecated(false);
    }
    //==================================================================================================================
    //  INSTANCE VARIABLES
    //==================================================================================================================
    private TrustInteroperabilityProfileImpl tip;
    private List<TrustmarkFrameworkIdentifiedObjectImpl> trustmarkFrameworkIdentifiedObjects = new ArrayList<>();
    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================
    private TrustmarkFrameworkIdentifiedObjectImpl findTMIByIdentifier(URI id){
        TrustmarkFrameworkIdentifiedObjectImpl obj = null;
        for( TrustmarkFrameworkIdentifiedObjectImpl cur : this.trustmarkFrameworkIdentifiedObjects ){
            if( cur.getIdentifier().equals(id) ){
                obj = cur;
                break;
            }
        }
        return obj;
    }

    public void addTrustmarkFrameworkIdentifiedObject(TrustmarkFrameworkIdentifiedObjectImpl obj){
        if( this.trustmarkFrameworkIdentifiedObjects == null )
            this.trustmarkFrameworkIdentifiedObjects = new ArrayList<>();
        if( !this.trustmarkFrameworkIdentifiedObjects.contains(obj) )
            this.trustmarkFrameworkIdentifiedObjects.add(obj);
        
    }
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

    @Override
    public TrustInteroperabilityProfile build() throws BuilderException {
        log.debug("Validating TIP...");
        TrustInteroperabilityProfileUtils utils = FactoryLoader.getInstance(TrustInteroperabilityProfileUtils.class);
        Collection<ValidationResult> validationResults = utils.validate(this.tip);
        if( validationResults != null && validationResults.size() > 0 ){
            ValidationBuilderException vbe = new ValidationBuilderException(validationResults);
            if( vbe.getFatalResults().size() > 0 ){
                throw vbe;
            }else if( vbe.getWarningResults().size() > 0 ){
                log.warn("Encountered "+vbe.getWarningResults().size()+" warnings: ");
                for( ValidationResult result : vbe.getWarningResults() ){
                    log.warn("   Result["+result.getSeverity()+"]: "+result.getMessage());
                }
            }
        }

        log.debug("Returning TIP...");
        return this.tip;
    }

    @Override
    public TrustInteroperabilityProfile buildWithNoValidation() {
        log.debug("Building TIP with no validation [MAY BE INVALID]...");
        return this.tip;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setIdentifier(String uriString) throws URISyntaxException {
        return this.setIdentifier(new URI(uriString));
    }

    @Override
    public TrustInteroperabilityProfileBuilder setIdentifier(URI uri) {
        this.tip.setIdentifier(uri);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setName(String name) {
        this.tip.setName(name);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setVersion(String version) {
        this.tip.setVersion(version);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setDescription(String description) {
        this.tip.setDescription(description);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setDeprecated(Boolean deprecated) {
        this.tip.setDeprecated(deprecated);
        return this;
    }


    @Override
    public TrustInteroperabilityProfileBuilder addSupersedes(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        this.tip.addToSupersedes(trustmarkFrameworkIdentifiedObject);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSupersedes(URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl tmfi = new TrustmarkFrameworkIdentifiedObjectImpl();
        tmfi.setIdentifier(identifier);
        return this.addSupersedes(tmfi);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSupersedes(String idString) throws URISyntaxException {
        return this.addSupersedes(new URI(idString));
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSupersedes(String idString, String name, String version) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        return this.addSupersedes(impl);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSupersedes(String idString, String name, String version, String description)
    throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        impl.setDescription(description);
        return this;
    }


    @Override
    public TrustInteroperabilityProfileBuilder addSupersededBy(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        this.tip.addToSupersededBy(trustmarkFrameworkIdentifiedObject);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSupersededBy(URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(identifier);
        return this.addSupersededBy(impl);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSupersededBy(String idString) throws URISyntaxException {
        return this.addSupersededBy(new URI(idString));
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSupersededBy(String idString, String name, String version) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        return this.addSupersededBy(impl);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSupersededBy(String idString, String name, String version, String description)
    throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        impl.setDescription(description);
        return this.addSupersededBy(impl);
    }


    @Override
    public TrustInteroperabilityProfileBuilder addSatisfies(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        this.tip.addToSatisfies(trustmarkFrameworkIdentifiedObject);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSatisfies(URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(identifier);
        return this.addSatisfies(impl);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSatisfies(String idString) throws URISyntaxException {
        return this.addSatisfies(new URI(idString));
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSatisfies(String idString, String name, String version) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        return this.addSatisfies(impl);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addSatisfies(String idString, String name, String version, String description)
    throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        impl.setDescription(description);
        return this.addSatisfies(impl);
    }



    @Override
    public TrustInteroperabilityProfileBuilder addKnownConflict(TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        this.tip.addToKnownConflict(trustmarkFrameworkIdentifiedObject);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder addKnownConflict(URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(identifier);
        return this.addKnownConflict(impl);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addKnownConflict(String idString) throws URISyntaxException {
        return this.addKnownConflict(new URI(idString));
    }

    @Override
    public TrustInteroperabilityProfileBuilder addKnownConflict(String idString, String name, String version) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        return this.addKnownConflict(impl);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addKnownConflict(String idString, String name, String version, String description)
            throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl impl = new TrustmarkFrameworkIdentifiedObjectImpl();
        impl.setIdentifier(new URI(idString));
        impl.setName(name);
        impl.setVersion(version);
        impl.setDescription(description);
        return this.addKnownConflict(impl);
    }


    @Override
    public TrustInteroperabilityProfileBuilder addKeyword(String keyword) {
        this.tip.addToKeywords(keyword.trim());
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setPublicationDateTime(Date dateTime) {
        this.tip.setPublicationDateTime(dateTime);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setPublicationDateTime(Long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return this.setPublicationDateTime(c.getTime());
    }

    @Override
    public TrustInteroperabilityProfileBuilder setLegalNotice(String notice) {
        this.tip.setLegalNotice(notice);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setNotes(String notes) {
        this.tip.setNotes(notes);
        return this;
    }


    @Override
    public TrustInteroperabilityProfileBuilder setIssuerOrganization(Entity entity) {
        Entity already = this.getEntityByUri(entity.getIdentifier().toString());
        if( already != null ){
            ((EntityImpl) already).setName(entity.getName());
            // TODO Should we bother saving these contacts?
            ((EntityImpl) already).setContacts(entity.getContacts());
            this.tip.setIssuer(already);
        }else{
            EntityImpl impl = new EntityImpl();
            impl.setIdentifier(entity.getIdentifier());
            impl.setName(entity.getName());
            impl.setContacts(entity.getContacts());
            this.getEntities().add(impl);
            this.tip.setIssuer(impl);
        }
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder setIssuerOrganization(URI uri, String name, String contactName, String contactEmail) {
        EntityImpl entity = new EntityImpl();
        entity.setIdentifier(uri);
        entity.setName(name);

        ContactImpl contact = new ContactImpl();
        contact.setKind(ContactKindCode.PRIMARY);
        contact.setResponder(contactName);
        contact.addEmail(contactEmail);

        entity.addContact(contact);

        return this.setIssuerOrganization(entity);
    }

    @Override
    public TrustInteroperabilityProfileBuilder setIssuerOrganization(String uri, String name, String contactName, String contactEmail)
    throws URISyntaxException {
        return this.setIssuerOrganization(new URI(uri), name, contactName, contactEmail);
    }
//
//
//    @Override
//    public TrustmarkDefinitionBuilder addSource(String identifier, String reference) {
//        SourceImpl already = (SourceImpl) this.getSourceByIdentifier(identifier);
//        if( already != null ){
//            already.setReference(reference);
//        }else{
//            SourceImpl source = new SourceImpl();
//            source.setIdentifier(identifier);
//            source.setReference(reference);
//            this.trustmarkDefinition.addSource(source);
//        }
//        return this;
//    }
//
//    @Override
//    public Source getSourceByIdentifier(String id) {
//        if( this.trustmarkDefinition.getSources() != null ){
//            for( Source s : this.trustmarkDefinition.getSources() ){
//                if( s.getIdentifier().equalsIgnoreCase(id) ){
//                    return s;
//                }
//            }
//        }
//        return null;
//    }

    @Override
    public TrustInteroperabilityProfileBuilder setTrustExpression(String trustExpression) {
        this.tip.setTrustExpression(trustExpression);
        return this;
    }


    protected void removeReferenceById(String id){
        List<AbstractTIPReference> references = new ArrayList<>();
        for( AbstractTIPReference ref : this.tip.getReferences() ){
            if( ref.getId().equals(id) ){
                continue;
            }else{
                references.add(ref);
            }
        }
        this.tip.setReferences(references);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTrustInteroperabilityProfileReference(String id, TrustmarkFrameworkIdentifiedObject tmfi) {
        removeReferenceById(id);
        TrustInteroperabilityProfileReferenceImpl reference = new TrustInteroperabilityProfileReferenceImpl();
        reference.setId(id);
        reference.setTypeName("TrustInteroperabilityProfile");
        reference.setIdentifier(tmfi.getIdentifier());
        reference.setName(tmfi.getName());
        reference.setVersion(tmfi.getVersion());
        reference.setDescription(tmfi.getDescription());
        this.tip.addReference(reference);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTrustInteroperabilityProfileReference(String id, URI identifier) {
        TrustmarkFrameworkIdentifiedObjectImpl tmfi = new TrustmarkFrameworkIdentifiedObjectImpl();
        tmfi.setIdentifier(identifier);
        tmfi.setTypeName("TrustInteroperabilityProfile");
        return this.addTrustInteroperabilityProfileReference(id, tmfi);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTrustInteroperabilityProfileReference(String id, String identifierString) throws URISyntaxException {
        return this.addTrustInteroperabilityProfileReference(id, new URI(identifierString));
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTrustInteroperabilityProfileReference(String id, String identifierString, String name, String version, String description) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl tmfi = new TrustmarkFrameworkIdentifiedObjectImpl();
        tmfi.setIdentifier(new URI(identifierString));
        tmfi.setTypeName("TrustInteroperabilityProfile");
        tmfi.setName(name);
        tmfi.setVersion(version);
        tmfi.setDescription(description);
        return this.addTrustInteroperabilityProfileReference(id, tmfi);
    }




    @Override
    public TrustInteroperabilityProfileBuilder addTrustmarkDefinitionRequirement(String id, TrustmarkFrameworkIdentifiedObject tmfi, Entity... providerReferences) {
        this.removeReferenceById(id);
        TrustmarkDefinitionRequirementImpl requirement = new TrustmarkDefinitionRequirementImpl();
        requirement.setId(id);
        requirement.setTypeName("TrustmarkDefinition");
        requirement.setIdentifier(tmfi.getIdentifier());
        requirement.setName(tmfi.getName());
        requirement.setVersion(tmfi.getVersion());
        requirement.setDescription(tmfi.getDescription());
        // TODO Should we instead reuse existing provider references?
        List providerRefList = new ArrayList();
        if( providerReferences != null && providerReferences.length > 0 ){
            for( Entity pr : providerReferences ){
                providerRefList.add(pr);
            }
        }
        requirement.setProviderReferences(providerRefList);
        this.tip.addReference(requirement);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTrustmarkDefinitionRequirement(String id, URI identifier, Entity... providerReferences) {
        TrustmarkFrameworkIdentifiedObjectImpl tmfi = new TrustmarkFrameworkIdentifiedObjectImpl();
        tmfi.setTypeName("TrustmarkDefinition");
        tmfi.setIdentifier(identifier);
        return this.addTrustmarkDefinitionRequirement(id, tmfi, providerReferences);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTrustmarkDefinitionRequirement(String id, String identifierString, Entity... providerReferences) throws URISyntaxException {
        return this.addTrustmarkDefinitionRequirement(id, new URI(identifierString), providerReferences);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTrustmarkDefinitionRequirement(String id, String identifierString, String name, String version, String description, Entity... providerReferences) throws URISyntaxException {
        TrustmarkFrameworkIdentifiedObjectImpl tmfi = new TrustmarkFrameworkIdentifiedObjectImpl();
        tmfi.setTypeName("TrustmarkDefinition");
        tmfi.setIdentifier(new URI(identifierString));
        tmfi.setName(name);
        tmfi.setVersion(version);
        tmfi.setDescription(description);
        return this.addTrustmarkDefinitionRequirement(id, tmfi, providerReferences);
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTerm(String name, String desc) {
        TermImpl termImpl = new TermImpl();
        termImpl.setName(name);
        termImpl.setDefinition(desc);
        this.tip.addTerm(termImpl);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTerm(String name, String desc, String... abbreviations) {
        TermImpl termImpl = new TermImpl();
        termImpl.setName(name);
        termImpl.setDefinition(desc);
        for( String s : abbreviations ){
            termImpl.addAbbreviation(s.trim());
        }
        this.tip.addTerm(termImpl);
        return this;
    }

    @Override
    public TrustInteroperabilityProfileBuilder addTerm(String name, String desc, Collection<String> abbreviations) {
        TermImpl termImpl = new TermImpl();
        termImpl.setName(name);
        termImpl.setDefinition(desc);
        for( String s : abbreviations ){
            termImpl.addAbbreviation(s.trim());
        }
        this.tip.addTerm(termImpl);
        return this;
    }


    @Override
    public TrustInteroperabilityProfileBuilder addSource(String identifier, String reference) {
        SourceImpl already = (SourceImpl) this.getSourceByIdentifier(identifier);
        if( already != null ){
            already.setReference(reference);
        }else{
            SourceImpl source = new SourceImpl();
            source.setIdentifier(identifier);
            source.setReference(reference);
            this.tip.addSource(source);
        }
        return this;
    }

    @Override
    public Source getSourceByIdentifier(String id) {
        if( this.tip.getSources() != null ){
            for( Source s : this.tip.getSources() ){
                if( s.getIdentifier().equalsIgnoreCase(id) ){
                    return s;
                }
            }
        }
        return null;
    }


}/* end TrustmarkDefinitionBuilderImpl */
