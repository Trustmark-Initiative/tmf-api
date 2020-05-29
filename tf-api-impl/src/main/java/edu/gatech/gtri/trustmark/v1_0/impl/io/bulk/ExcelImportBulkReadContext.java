package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.impl.model.ContactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by Nicholas on 09/20/2016.
 */
public class ExcelImportBulkReadContext extends BulkReadContextImpl {
    
    ////// Constants //////
    
    public static final String PROPERTY_TDO_IDENTIFIER = "tdo.identifier";
    public static final String PROPERTY_TDO_NAME = "tdo.name";
    public static final String PROPERTY_TDO_PRIMARY_CONTACT_EMAIL = "tdo.primaryContact.email";
    public static final String PROPERTY_TDO_PRIMARY_CONTACT_TELEPHONE = "tdo.primaryContact.telephone";
    public static final String PROPERTY_TDO_PRIMARY_CONTACT_MAILING_ADDRESS = "tdo.primaryContact.mailingAddress";
    
    public static final String PROPERTY_TIP_ISSUER_IDENTIFIER = "tipIssuer.identifier";
    public static final String PROPERTY_TIP_ISSUER_NAME = "tipIssuer.name";
    public static final String PROPERTY_TIP_ISSUER_PRIMARY_CONTACT_EMAIL = "tipIssuer.primaryContact.email";
    public static final String PROPERTY_TIP_ISSUER_PRIMARY_CONTACT_TELEPHONE = "tipIssuer.primaryContact.telephone";
    public static final String PROPERTY_TIP_ISSUER_PRIMARY_CONTACT_MAILING_ADDRESS = "tipIssuer.primaryContact.mailingAddress";
    
    public static final String PROPERTY_TD_PROVIDER_REFERENCE_IDS = "tdProviderReferenceIds";
    public static final Pattern SPLITTER_TD_PROVIDER_REFERENCE_IDS = Pattern.compile("\\|");
    
    public static final String PROPERTY_TD_IDENTIFIER_URI_BASE = "tdIdentifierUriBase";
    public static final String PROPERTY_TIP_IDENTIFIER_URI_BASE = "tipIdentifierUriBase";
    
    public static final String PROPERTY_TFAM_URIS_FOR_EXTERNAL_RESOLUTION = "tpatUrisForExternalResolution";
    public static final Pattern SPLITTER_TFAM_URIS_FOR_EXTERNAL_RESOLUTION = Pattern.compile("\\|");
    
    public static final String PROPERTY_DEFAULT_VERSION = "defaultVersion";
    public static final String PROPERTY_DEFAULT_LEGAL_NOTICE = "defaultLegalNotice";
    public static final String PROPERTY_DEFAULT_NOTES = "defaultNotes";
    public static final String PROPERTY_DEFAULT_ISSUANCE_CRITERIA = "defaultIssuanceCriteria";
    public static final String PROPERTY_DEFAULT_REVOCATION_CRITERIA = "defaultRevocationCriteria";
    
    
    ////// Instance Fields //////

    public File file;
    public Properties properties;
    
    
    ////// Constructor //////

    public ExcelImportBulkReadContext(File propertiesFile) throws Exception {
        this(propertiesFile, propertiesFromFile(propertiesFile));
    }
    
    public ExcelImportBulkReadContext(Properties properties) throws Exception {
        this(null, properties);
    }
    
    private ExcelImportBulkReadContext(File _file, Properties _properties) throws Exception {
        this.file = _file;
        this.properties = _properties;
        
        // Trustmark Defining Organization
        EntityImpl tdoEntity = new EntityImpl();
        tdoEntity.setIdentifier(new URI(this.getTrimmedProperty(PROPERTY_TDO_IDENTIFIER)));
        tdoEntity.setName(this.getTrimmedProperty(PROPERTY_TDO_NAME));
        ContactImpl tdoContact = new ContactImpl();
        tdoContact.setKind(ContactKindCode.PRIMARY);
        tdoContact.addEmail(this.getTrimmedProperty(PROPERTY_TDO_PRIMARY_CONTACT_EMAIL));
        tdoContact.addTelephone(this.getTrimmedProperty(PROPERTY_TDO_PRIMARY_CONTACT_TELEPHONE));
        tdoContact.addMailingAddress(this.getTrimmedProperty(PROPERTY_TDO_PRIMARY_CONTACT_MAILING_ADDRESS));
        tdoEntity.addContact(tdoContact);
        this.setTrustmarkDefiningOrganization(tdoEntity);
        
        // TIP Issuer
        EntityImpl tipIssuerEntity = new EntityImpl();
        tipIssuerEntity.setIdentifier(new URI(this.getTrimmedProperty(PROPERTY_TIP_ISSUER_IDENTIFIER)));
        tipIssuerEntity.setName(this.getTrimmedProperty(PROPERTY_TIP_ISSUER_NAME));
        ContactImpl tipIssuerContact = new ContactImpl();
        tipIssuerContact.setKind(ContactKindCode.PRIMARY);
        tipIssuerContact.addEmail(this.getTrimmedProperty(PROPERTY_TIP_ISSUER_PRIMARY_CONTACT_EMAIL));
        tipIssuerContact.addTelephone(this.getTrimmedProperty(PROPERTY_TIP_ISSUER_PRIMARY_CONTACT_TELEPHONE));
        tipIssuerContact.addMailingAddress(this.getTrimmedProperty(PROPERTY_TIP_ISSUER_PRIMARY_CONTACT_MAILING_ADDRESS));
        tipIssuerEntity.addContact(tipIssuerContact);
        this.setTrustInteroperabilityProfileIssuer(tipIssuerEntity);
        
        // TD Provider References
        String[] tdProviderReferenceIds = this.getSplitProperty(PROPERTY_TD_PROVIDER_REFERENCE_IDS, SPLITTER_TD_PROVIDER_REFERENCE_IDS);
        for (String tdProviderReferenceId : tdProviderReferenceIds) {
            EntityImpl tdProviderReference = new EntityImpl();
            tdProviderReference.setIdentifier(new URI(tdProviderReferenceId));
            this.addTrustmarkProviderReference(tdProviderReference);
        }
        
        // TD Identifier URI Base
        this.setTdIdentifierUriBase(this.getTrimmedProperty(PROPERTY_TD_IDENTIFIER_URI_BASE));
        
        // TIP Identifier URI Base
        this.setTipIdentifierUriBase(this.getTrimmedProperty(PROPERTY_TIP_IDENTIFIER_URI_BASE));
    
        // TFAM URIs for External Resolution
        String[] tfamUrisForExternalResolution = this.getSplitProperty(PROPERTY_TFAM_URIS_FOR_EXTERNAL_RESOLUTION, SPLITTER_TFAM_URIS_FOR_EXTERNAL_RESOLUTION);
        for (String tfamUriString : tfamUrisForExternalResolution) {
            URI tfamUri = new URI(tfamUriString);
            this.addTfamUriForExternalResolution(tfamUri);
        }
        
        // Default Wording
        this.setDefaultVersion(this.getTrimmedProperty(PROPERTY_DEFAULT_VERSION));
        this.setDefaultLegalNotice(this.getTrimmedProperty(PROPERTY_DEFAULT_LEGAL_NOTICE));
        this.setDefaultNotes(this.getTrimmedProperty(PROPERTY_DEFAULT_NOTES));
        this.setDefaultIssuanceCriteria(this.getTrimmedProperty(PROPERTY_DEFAULT_ISSUANCE_CRITERIA));
        this.setDefaultRevocationCriteria(this.getTrimmedProperty(PROPERTY_DEFAULT_REVOCATION_CRITERIA));
        
    }
    
    
    ////// Static Methods //////
    
    private static Properties propertiesFromFile(File file) throws IOException {
        Properties result = new Properties();
        try (FileReader reader = new FileReader(file)) {
            result.load(reader);
        }
        return result;
    }
    
    
    ////// Instance Methods //////
    
    public String getTrimmedProperty(String key) {
        return BulkImportUtils.defaultTrim(this.properties.getProperty(key));
    }
    
    public String[] getSplitProperty(String key, Pattern splitter) {
        String[] result = splitter.split(BulkImportUtils.defaultTrim(this.properties.getProperty(key)));
        for (int i = 0; i < result.length; ++i) {
            result[i] = BulkImportUtils.defaultTrim(result[i]);
        }
        return result;
    }
}
