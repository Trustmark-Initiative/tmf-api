package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.service.ServiceReferenceNameResolver;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadContext;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkService;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nicholas on 9/12/2016.
 */
public class BulkReadContextImpl implements BulkReadContext {
    
    // Constants
    private static final Logger logger = LoggerFactory.getLogger(BulkReadContextImpl.class);
    
    
    // Instance Properties
    private Entity trustmarkDefiningOrganization = null;
    public void setTrustmarkDefiningOrganization(Entity _trustmarkDefiningOrganization) {
        this.trustmarkDefiningOrganization = _trustmarkDefiningOrganization;
    }
    
    private Entity trustInteroperabilityProfile = null;
    public void setTrustInteroperabilityProfileIssuer(Entity _trustInteroperabilityProfile) {
        this.trustInteroperabilityProfile = _trustInteroperabilityProfile;
    }
    
    private final List<Entity> trustmarkProviderReferences = new ArrayList<>();
    public void addTrustmarkProviderReference(Entity entity) {
        this.trustmarkProviderReferences.add(entity);
    }

    public void setTrustmarkProviderReferences(Collection<? extends Entity> _trustmarkProviderReferences) {
        this.trustmarkProviderReferences.clear();
        this.trustmarkProviderReferences.addAll(_trustmarkProviderReferences);
    }
    
    private String tdIdentifierUriBase = null;
    public void setTdIdentifierUriBase(String _tdIdentifierUriBase) {
        this.tdIdentifierUriBase = _tdIdentifierUriBase;
    }
    
    private String tipIdentifierUriBase = null;
    public void setTipIdentifierUriBase(String _tipIdentifierUriBase) {
        this.tipIdentifierUriBase = _tipIdentifierUriBase;
    }
    
    private final List<URI> tfamUrisForExternalResolution = new ArrayList<>();
    public void addTfamUriForExternalResolution(URI uri) { this.tfamUrisForExternalResolution.add(uri); }


    private String defaultVersion = null;
    public void setDefaultVersion(String _defaultVersion) {
        this.defaultVersion = _defaultVersion;
    }

    private String defaultTdLegalNotice = null;
    public void setDefaultTdLegalNotice(String _defaultTdLegalNotice) {
        this.defaultTdLegalNotice = _defaultTdLegalNotice;
    }

    private String defaultTdNotes = null;
    public void setDefaultTdNotes(String _defaultTdNotes) {
        this.defaultTdNotes = _defaultTdNotes;
    }

    private String defaultTipLegalNotice = null;
    public void setDefaulTiptLegalNotice(String _defaultTipLegalNotice) {
        this.defaultTipLegalNotice = _defaultTipLegalNotice;
    }
    
    private String defaultTipNotes = null;
    public void setDefaultTipNotes(String _defaultTipNotes) {
        this.defaultTipNotes = _defaultTipNotes;
    }
    
    private String defaultIssuanceCriteria;
    public void setDefaultIssuanceCriteria(String _defaultIssuanceCriteria) {
        this.defaultIssuanceCriteria = _defaultIssuanceCriteria;
    }
    
    private String defaultRevocationCriteria = null;
    public void setDefaultRevocationCriteria(String _defaultRevocationCriteria) {
        this.defaultRevocationCriteria = _defaultRevocationCriteria;
    }

    private String defaultStakeholderDescription = null;
    public void setDefaultStakeholderDescription(String _defaultStakeholderDescription) {
        this.defaultStakeholderDescription = _defaultStakeholderDescription;
    }

    private String defaultRecipientDescription = null;
    public void setDefaultRecipientDescription(String _defaultRecipientDescription) {
        this.defaultRecipientDescription = _defaultRecipientDescription;
    }

    private String defaultRelyingPartyDescription = null;
    public void setDefaultRelyingPartyDescription(String _defaultRelyingPartyDescription){
        this.defaultRelyingPartyDescription = _defaultRelyingPartyDescription;
    }

    private String defaultProviderDescription = null;
    public void setDefaultProviderDescription(String _defaultProviderDescription){
        this.defaultProviderDescription = _defaultProviderDescription;
    }

    private String defaultProviderEligibilityCriteria = null;
    public void setDefaultProviderEligibilityCriteria(String _defaultProviderEligibilityCriteria){
        this.defaultProviderEligibilityCriteria = _defaultProviderEligibilityCriteria;
    }

    private String defaultAssessorQualificationsDescription = null;
    public void setDefaultAssessorQualificationsDescription(String _defaultAssessorQualificationsDescription){
        this.defaultAssessorQualificationsDescription = _defaultAssessorQualificationsDescription;
    }

    private String defaultExtensionDescription = null;
    public void setDefaultExtensionDescription(String _defaultExtensionDescription){
        this.defaultExtensionDescription = _defaultExtensionDescription;
    }

    // Instance Methods
    @Override
    public Entity getTrustmarkDefiningOrganization() {
        return this.trustmarkDefiningOrganization;
    }
    
    @Override
    public Entity getTrustInteroperabilityProfileIssuer() {
        return this.trustInteroperabilityProfile;
    }
    
    @Override
    public List<Entity> getTrustmarkProviderReferences() {
        return this.trustmarkProviderReferences;
    }
    
    @Override
    public URI generateIdentifierForTrustmarkDefinition(String moniker, String version) throws URISyntaxException {
        String uriString = String.format("%s/%s/%s/", this.tdIdentifierUriBase, moniker, version);
        return new URI(uriString);
    }
    
    @Override
    public URI generateIdentifierForTrustInteroperabilityProfile(String moniker, String version) throws URISyntaxException {
        String uriString = String.format("%s/%s/%s/", this.tipIdentifierUriBase, moniker, version);
        return new URI(uriString);
    }
    
    @Override
    public TrustmarkFrameworkIdentifiedObject resolveReferencedExternalTrustmarkDefinition(String tdReference) {
        if(BulkImportUtils.isValidUri(tdReference))  {
            return this.resolveReferencedExternalArtifact(tdReference, TrustmarkFrameworkService::getTrustmarkDefinitionByUrl);
        }
        return this.resolveReferencedExternalArtifact(tdReference, TrustmarkFrameworkService::getTrustmarkDefinitionByName);
    }
    
    @Override
    public TrustmarkFrameworkIdentifiedObject resolveReferencedExternalTrustInteroperabilityProfile(String tipReference) {
        if(BulkImportUtils.isValidUri(tipReference))  {
            return this.resolveReferencedExternalArtifact(tipReference, TrustmarkFrameworkService::getTrustInteroperabilityProfileByUrl);
        }
        return this.resolveReferencedExternalArtifact(tipReference, TrustmarkFrameworkService::getTrustInteroperabilityProfileByName);
    }
    
    private TrustmarkFrameworkIdentifiedObject resolveReferencedExternalArtifact(String reference, ServiceReferenceNameResolver serviceReferenceNameResolver) {
        URI result = null;
        TrustmarkFrameworkIdentifiedObjectImpl trustmarkFrameworkIdentifiedObject = new TrustmarkFrameworkIdentifiedObjectImpl();
        // try resolving as external name first
        TrustmarkFrameworkServiceFactory tfsFactory = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class);
        for (URI tfamUri : this.tfamUrisForExternalResolution) {
            String tfamBaseUrl = tfamUri.toString();
            TrustmarkFrameworkService tfs = null;
            try {
                tfs = tfsFactory.createService(tfamBaseUrl);
            }catch(Exception ex){
                logger.debug(String.format("Unable to create TrustmarkFrameworkService (API Client) for URL[%s]. %s", tfamBaseUrl, ex.getMessage()));
            }
            try {
                TrustmarkFrameworkIdentifiedObject tfido = serviceReferenceNameResolver.resolve(tfs, reference);
                if(tfido != null) {
                    return tfido;
                }
            }
            catch (Exception ex) {
                logger.debug(String.format("Error resolving external artifact [%s]. %s", reference, ex.getMessage()));
            }
            if (result != null) {
                break;
            }
        }
        // second, try resolving as a URL (but return the URI object)
        if (result == null) {
            trustmarkFrameworkIdentifiedObject.setIdentifier(BulkImportUtils.getValidUrlAsUriOrNull(reference));
        }
        return trustmarkFrameworkIdentifiedObject;
    }
    
    @Override
    public String getDefaultVersion() {
        return this.defaultVersion;
    }
    
    @Override
    public String getDefaultTipLegalNotice() {
        return this.defaultTipLegalNotice;
    }
    
    @Override
    public String getDefaultTipNotes() {
        return this.defaultTipNotes;
    }

    @Override
    public String getDefaultTdLegalNotice() {
        return this.defaultTdLegalNotice;
    }

    @Override
    public String getDefaultTdNotes() {
        return this.defaultTdNotes;
    }

    @Override
    public String getDefaultIssuanceCriteria() {
        return this.defaultIssuanceCriteria;
    }
    
    @Override
    public String getDefaultRevocationCriteria() {
        return this.defaultRevocationCriteria;
    }

    @Override
    public String getDefaultTargetStakeholderDescription() {
        return this.defaultStakeholderDescription;
    }

    @Override
    public String getDefaultTargetRecipientDescription() {
        return this.defaultRecipientDescription;
    }

    @Override
    public String getDefaultTargetRelyingPartyDescription() {
        return this.defaultRelyingPartyDescription;
    }

    @Override
    public String getDefaultTargetProviderDescription() { return this.defaultProviderDescription; }

    @Override
    public String getDefaultProviderEligibilityCriteria() { return this.defaultProviderEligibilityCriteria; }

    @Override
    public String getDefaultAssessorQualificationsDescription() { return this.defaultAssessorQualificationsDescription; }

    @Override
    public String getDefaultExtensionDescription() { return this.defaultExtensionDescription; }
}
