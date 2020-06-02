package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.service.ServiceReferenceNameResolver;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadContext;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkService;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkServiceFactory;
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(BulkReadContextImpl.class);
    
    
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
    
    private String defaultLegalNotice = null;
    public void setDefaultLegalNotice(String _defaultLegalNotice) {
        this.defaultLegalNotice = _defaultLegalNotice;
    }
    
    private String defaultNotes = null;
    public void setDefaultNotes(String _defaultNotes) {
        this.defaultNotes = _defaultNotes;
    }
    
    private String defaultIssuanceCriteria;
    public void setDefaultIssuanceCriteria(String _defaultIssuanceCriteria) {
        this.defaultIssuanceCriteria = _defaultIssuanceCriteria;
    }
    
    private String defaultRevocationCriteria = null;
    public void setDefaultRevocationCriteria(String _defaultRevocationCriteria) {
        this.defaultRevocationCriteria = _defaultRevocationCriteria;
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
                System.out.printf("Unable to create TrustmarkFrameworkService (API Client) for URL[%s]. %s\n", tfamBaseUrl, ex.getMessage());
            }
            try {
                TrustmarkFrameworkIdentifiedObject tfido = serviceReferenceNameResolver.resolve(tfs, reference);
                if(tfido != null) {
                    return tfido;
                }
            }
            catch (Exception ex) {
                System.out.printf("Error resolving external artifact [%s]. %s\n", reference, ex.getMessage());
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
    public String getDefaultLegalNotice() {
        return this.defaultLegalNotice;
    }
    
    @Override
    public String getDefaultNotes() {
        return this.defaultNotes;
    }
    
    @Override
    public String getDefaultIssuanceCriteria() {
        return this.defaultIssuanceCriteria;
    }
    
    @Override
    public String getDefaultRevocationCriteria() {
        return this.defaultRevocationCriteria;
    }
}
