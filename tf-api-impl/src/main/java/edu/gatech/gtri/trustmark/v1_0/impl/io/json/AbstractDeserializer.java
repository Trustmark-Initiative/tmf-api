package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.impl.model.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Provides methods to all types of system deserializers.
 * <br/><br/>
 * Created by brad on 12/10/15.
 */
public abstract class AbstractDeserializer {

    private static final Logger log = Logger.getLogger(AbstractDeserializer.class);

    public static TermImpl readTerm(JSONObject termJson ) throws ParseException {
        TermImpl term = new TermImpl();
        term.setName(getString(termJson, "Name", true));
        term.setDefinition(getString(termJson, "Definition", true));
        if( termJson.has("Abbreviations") ){
            JSONArray abbrJson = termJson.getJSONArray("Abbreviations");
            for( int abbrIndex = 0; abbrIndex < abbrJson.length(); abbrIndex++ ){
                term.addAbbreviation((String) abbrJson.get(abbrIndex));
            }
        }
        return term;
    }

    protected static SourceImpl readSource( JSONObject sourceJson ) throws ParseException {
        SourceImpl source = new SourceImpl();
        source.setIdentifier(getString(sourceJson, "Identifier", true));
        source.setReference(getString(sourceJson, "Reference", true));
        return source;
    }//end readSource()


    public static ExtensionImpl readExtension(JSONObject jsonObject, String fieldName, Boolean required)
            throws ParseException {
        if( !jsonObject.has(fieldName) ){
            if( required )
                throw new ParseException("Missing required field: "+fieldName);
            return null;
        }

        List extensionObjects = new ArrayList<>();
        JSONArray extJSON = null;
        try{
            extJSON = jsonObject.optJSONArray(fieldName);
            if( extJSON == null ){
                extensionObjects.add( jsonObject.opt(fieldName));
            }else{
                for( int i = 0; i < extJSON.length(); i++ )
                    extensionObjects.add(extJSON.get(i));
            }
        }catch(JSONException jsone){
            throw new ParseException("Field '"+fieldName+"' is expected to be a JSONArray, but is not!", jsone);
        }

        if( extensionObjects != null && extensionObjects.size() > 0 ) {
            ExtensionImpl extension = new ExtensionImpl();
            for (Object obj : extensionObjects ) {
                extension.addData(obj);
            }
            return extension;
        }else{
            return null;
        }
    }//end readExtension()


    public static EntityImpl readEntity(JSONObject jsonObject, String fieldName, Boolean required)
            throws ParseException {
        if( !jsonObject.has(fieldName) ){
            if( required )
                throw new ParseException("Missing required field: "+fieldName);
            return null;
        }

        JSONObject entityJSON = null;
        try{
            entityJSON = jsonObject.getJSONObject(fieldName);
        }catch(JSONException jsone){
            throw new ParseException("Field '"+fieldName+"' is expected to be a JSONObject, but is not!", jsone);
        }

        return readEntityDirectly(entityJSON);
    }//end readTrustmarkFrameworkIdentifiedObject

    public static EntityImpl readEntityDirectly(JSONObject entityJSON) throws ParseException {
        EntityImpl entity = new EntityImpl();

        entity.setIdentifier(getUri(entityJSON, "Identifier", true));
        entity.setName(getString(entityJSON, "Name", true));

        if( entityJSON.has("PrimaryContact") ){
            entity.addContact(readContactDirectly(entityJSON.getJSONObject("PrimaryContact")));
        }else if( entityJSON.has("Contact") ){
            entity.addContact(readContactDirectly(entityJSON.getJSONObject("Contact")));
        }
        if( entityJSON.optJSONArray("OtherContacts") != null ){
            JSONArray contactJSONArray = entityJSON.getJSONArray("OtherContacts");
            for( int i = 0; i < contactJSONArray.length(); i++ ){
                JSONObject contactJSON = contactJSONArray.optJSONObject(i);
                if( contactJSON != null ){
                    entity.addContact(readContactDirectly(contactJSON));
                }
            }
        }else if( entityJSON.optJSONArray("Contacts") != null ){
            JSONArray contactJSONArray = entityJSON.getJSONArray("Contacts");
            for( int i = 0; i < contactJSONArray.length(); i++ ){
                JSONObject contactJSON = contactJSONArray.optJSONObject(i);
                if( contactJSON != null ){
                    entity.addContact(readContactDirectly(contactJSON));
                }
            }
        }

        if( entity.getDefaultContact() == null )
            throw new ParseException("Entity '"+entity.getName()+"' does not have any contacts!  Expecting either PrimaryContact or OtherContacts to have a value.");

        return entity;
    }//end readTrustmarkFrameworkIdentifiedObject

    public static EntityImpl readEntityReference(JSONObject entityJSON) throws ParseException {
        EntityImpl entity = new EntityImpl();

        entity.setIdentifier(getUri(entityJSON, "Identifier", true));
        entity.setName(getString(entityJSON, "Name", false));

        if( entityJSON.has("PrimaryContact") ){
            entity.addContact(readContactDirectly(entityJSON.getJSONObject("PrimaryContact")));
        }else if( entityJSON.has("Contact") ){
            entity.addContact(readContactDirectly(entityJSON.getJSONObject("Contact")));
        }
        if( entityJSON.optJSONArray("OtherContacts") != null ){
            JSONArray contactJSONArray = entityJSON.getJSONArray("OtherContacts");
            for( int i = 0; i < contactJSONArray.length(); i++ ){
                JSONObject contactJSON = contactJSONArray.optJSONObject(i);
                if( contactJSON != null ){
                    entity.addContact(readContactDirectly(contactJSON));
                }
            }
        }else if( entityJSON.optJSONArray("Contacts") != null ){
            JSONArray contactJSONArray = entityJSON.getJSONArray("Contacts");
            for( int i = 0; i < contactJSONArray.length(); i++ ){
                JSONObject contactJSON = contactJSONArray.optJSONObject(i);
                if( contactJSON != null ){
                    entity.addContact(readContactDirectly(contactJSON));
                }
            }
        }

        return entity;
    }//end readTrustmarkFrameworkIdentifiedObject

    public static ContactImpl readContact(JSONObject jsonObject, String fieldName, Boolean required)
            throws ParseException {
        if( !jsonObject.has(fieldName) ){
            if( required )
                throw new ParseException("Missing required field: "+fieldName);
            return null;
        }

        JSONObject contactJSON = null;
        try{
            contactJSON = jsonObject.getJSONObject(fieldName);
        }catch(JSONException jsone){
            throw new ParseException("Field '"+fieldName+"' is expected to be a JSONObject, but is not!", jsone);
        }

        return readContactDirectly(contactJSON);
    }
    public static ContactImpl readContactDirectly(JSONObject contactJSON)
            throws ParseException {

        ContactImpl contact = new ContactImpl();
        int fieldsAdded = 0;

        if( contactJSON.has("Kind") ){
            contact.setKind(ContactKindCode.fromString(contactJSON.getString("Kind")));
        }else{
            contact.setKind(ContactKindCode.OTHER);
        }

        if( contactJSON.has("Responder") ){
            fieldsAdded++;
            contact.setResponder(getString(contactJSON, "Responder", true));
        }

        if( contactJSON.has("Email") ){
            fieldsAdded++;
            contact.addEmail(getString(contactJSON, "Email", true));
        }
        if( contactJSON.has("Emails") ){
            fieldsAdded++;
            contact.setEmails(readListOfStrings(contactJSON, "Emails"));
        }

        if( contactJSON.has("Telephone") ){
            fieldsAdded++;
            contact.addTelephone(getString(contactJSON, "Telephone", true));
        }
        if( contactJSON.has("Telephones") ){
            fieldsAdded++;
            contact.setTelephones(readListOfStrings(contactJSON, "Telephones"));
        }

        if( contactJSON.has("PhysicalAddress") ){
            fieldsAdded++;
            contact.addPhysicalAddress(getString(contactJSON, "PhysicalAddress", true));
        }
        if( contactJSON.has("PhysicalAddresses") ){
            fieldsAdded++;
            contact.setPhysicalAddresses(readListOfStrings(contactJSON, "PhysicalAddresses"));
        }

        if( contactJSON.has("MailingAddress") ){
            fieldsAdded++;
            contact.addMailingAddress(getString(contactJSON, "MailingAddress", true));
        }
        if( contactJSON.has("MailingAddresses") ){
            fieldsAdded++;
            contact.setMailingAddresses(readListOfStrings(contactJSON, "MailingAddresses"));
        }

        if( contactJSON.has("WebsiteURL") ){
            fieldsAdded++;
            contact.addWebsiteURL(getUrl(contactJSON, "WebsiteURL", true));
        }
        if( contactJSON.has("WebsiteURLs") ){
            fieldsAdded++;
            try {
                contact.setWebsiteURLs(listOfStringsToListOfURLs(readListOfStrings(contactJSON, "WebsiteURLs")));
            }catch(MalformedURLException me){
                throw new ParseException("Invalid Contact Website URLs!", me);
            }
        }

        if( contactJSON.has("Notes") ){
            fieldsAdded++;
            contact.setNotes(getString(contactJSON, "Notes", true));
        }

        if( fieldsAdded == 0 )
            throw new ParseException("(a ContactType) is required to have at least some data (like 'Responder', 'Email', etc).");

        return contact;
    }//end readTrustmarkFrameworkIdentifiedObject

    private static List<URL> listOfStringsToListOfURLs(List<String> stringList) throws MalformedURLException {
        List<URL> urls = new ArrayList<>();
        if( stringList != null && !stringList.isEmpty() ){
            for( String s : stringList ){
                urls.add(new URL(s));
            }
        }
        return urls;
    }

    private static List<String> readListOfStrings(JSONObject jsonObject, String field){
        List<String> stringList = new ArrayList<>();
        if( jsonObject.has(field) ){
            if( jsonObject.optJSONArray(field) != null ) {
                JSONArray stringArray = jsonObject.optJSONArray(field);
                for( int i = 0; i < stringArray.length(); i++ ){
                    stringList.add(stringArray.get(i).toString());
                }
            }else if( jsonObject.optString(field) != null ) {
                stringList.add(jsonObject.getString(field));
            }else{
                log.debug("Asked to add a field["+field+"] to a list of strings, but it isn't a string");
                stringList.add(jsonObject.get(field).toString());
            }
        }
        return stringList;
    }


    protected static String getString(JSONObject jsonObject, String fieldName, Boolean required) throws ParseException {
        if( !jsonObject.has(fieldName) ){
            if( required )
                throw new ParseException("Missing required field: "+fieldName);
            return null;
        }

        String fieldValue = null;
        try {
            fieldValue = jsonObject.getString(fieldName);
        } catch (JSONException jsone) {
            log.warn("Encountered JSON exception reading field '" + fieldName + "'", jsone);
            throw new ParseException("Error reading string '" + fieldName + "'", jsone);
        }
        log.debug("Read field["+fieldName+"] = "+fieldValue);

        return fieldValue;
    }

    protected static boolean exists(JSONObject jsonObject, String fieldName)  {
        if( jsonObject.has(fieldName) )  {
            return true;
        }
        return false;
    }
    protected static Number getNumber(JSONObject jsonObject, String fieldName, Boolean required) throws ParseException {
        if( !jsonObject.has(fieldName) ){
            if( required )
                throw new ParseException("Missing required field: "+fieldName);
            return null;
        }

        BigDecimal fieldValue = null;
        try {
            fieldValue = jsonObject.getBigDecimal(fieldName);
        } catch (JSONException jsone) {
            log.warn("Encountered JSON exception reading field '" + fieldName + "'", jsone);
            throw new ParseException("Error reading BigDecimal '" + fieldName + "'", jsone);
        }
        log.debug("Read field["+fieldName+"] = "+fieldValue);
        return (Double) fieldValue.doubleValue();
    }


    public static Date getDate(JSONObject jsonObject, String fieldName, Boolean required) throws ParseException {
        if( !jsonObject.has(fieldName) ){
            if( required )
                throw new ParseException("Missing required field: "+fieldName);
            return null;
        }

        // At this point, we know it must exist.
        Object value = jsonObject.get(fieldName);
        if( value instanceof String ){
            log.debug("Read date field ["+fieldName+"] as string value: "+value);
            Calendar cal = DatatypeConverter.parseDateTime((String) value);
            return cal.getTime();
        }else if( value instanceof Number ){
            Long timestamp = ((Number) value).longValue();
            log.debug("Read date field ["+fieldName+"] as long value: "+timestamp);
            return new Date(timestamp); // I assume the default is GMT timezone?
        }else{
            throw new ParseException("Expecting field '"+fieldName+"' to be a long (millis since epoch) or string (ISO8660) format.  Value["+value.getClass().getName()+"]: "+value);
        }

    }


    protected static URI getUri(JSONObject jsonObject, String fieldName, Boolean required) throws ParseException {
        String uriString = getString(jsonObject, fieldName, required);
        URI uri = null;
        try {
            uri = new URI(uriString);
        }catch(Throwable t){
            throw new ParseException("Unable to coerce value '"+uriString+"' to a URI.", t);
        }
        return uri;
    }

    protected static URL getUrl(JSONObject jsonObject, String fieldName, Boolean required) throws ParseException {
        String uriString = getString(jsonObject, fieldName, required);
        URL url = null;
        try {
            url = new URL(uriString);
        }catch(Throwable t){
            throw new ParseException("Unable to coerce value '"+uriString+"' to a URL.", t);
        }
        return url;
    }


    public static TrustmarkFrameworkIdentifiedObject readTrustmarkFrameworkIdentifiedObject(JSONObject jsonObject, String fieldName, Boolean required)
            throws ParseException {
        if( !jsonObject.has(fieldName) ){
            if( required )
                throw new ParseException("Missing required field: "+fieldName);
            return null;
        }

        JSONObject tfidObj = null;
        try{
            tfidObj = jsonObject.getJSONObject(fieldName);
        }catch(JSONException jsone){
            throw new ParseException("Field '"+fieldName+"' is expected to be a JSONObject, but is not!", jsone);
        }

        TrustmarkFrameworkIdentifiedObjectImpl obj = new TrustmarkFrameworkIdentifiedObjectImpl();
        obj.setTypeName(fieldName);
        obj.setIdentifier(getUri(tfidObj, "Identifier", true));
        obj.setName(getString(tfidObj, "Name", false));
        if(exists(tfidObj, "Number")) {
            obj.setNumber(getNumber(tfidObj, "Number", false).intValue());
        }
        obj.setVersion(getString(tfidObj, "Version", false));
        obj.setDescription(getString(tfidObj, "Description", false));

        return obj;
    }//end readTrustmarkFrameworkIdentifiedObject


    protected static TrustmarkFrameworkIdentifiedObject readTFIFDirectly(JSONObject supersedes) throws ParseException {
        if( supersedes == null )
            return null;

        TrustmarkFrameworkIdentifiedObjectImpl tfi = new TrustmarkFrameworkIdentifiedObjectImpl();
        try {
            tfi.setIdentifier(new URI(supersedes.getString("Identifier")));
        }catch(URISyntaxException urise){
            throw new ParseException("Could not parse id '"+supersedes.getString("Identifier")+"' as Supersedes TD Identifier", urise);
        }
        tfi.setName(supersedes.optString("Name"));
        tfi.setVersion(supersedes.optString("Version"));
        tfi.setNumber(supersedes.optInt("Number"));
        tfi.setDescription(supersedes.optString("Description"));
        tfi.setTypeName("TrustmarkDefinitionReference");
        return tfi;
    }


    /**
     * This method will inspect the given JSON object and make sure it is compatible with the current library version.
     */
    public static void isSupported(JSONObject json) throws ParseException {
        isSupportedVersion(json);
        isSupportedType(json);
    }//end isSupported()
    
    public static void isSupportedVersion(JSONObject json) throws ParseException {
        String tmfVersion = json.optString("$TMF_VERSION");
        if( tmfVersion == null || tmfVersion.trim().length() == 0 ){
            throw new ParseException("This JSON is not supported.  The JSON given cannot be parsed, because it is missing a '$TMF_VERSION' field to indicate which version of the library it works with.");
        }
        tmfVersion = tmfVersion.trim();
        TrustmarkFramework framework = FactoryLoader.getInstance(TrustmarkFramework.class);
        Double trustmarkFrameworkVersion = Double.parseDouble(framework.getTrustmarkFrameworkVersion());
        Double frameworkVersion = Double.parseDouble((tmfVersion));
        if(trustmarkFrameworkVersion < frameworkVersion) {
//        if( !framework.getTrustmarkFrameworkVersion().equalsIgnoreCase(tmfVersion) ){
            throw new ParseException("The TMF Version in the JSON Object given ["+tmfVersion+"] does not support your API version["+framework.getTrustmarkFrameworkVersion()+"], and cannot be parsed.  Try converting it first.");
        }
    }//end isSupportedVersion()
    
    public static void isSupportedType(JSONObject json) throws ParseException {
        String type = json.optString("$Type");
        if( type == null || type.trim().length() == 0 ){
            throw new ParseException("This JSON is not supported.  The JSON given cannot be parsed, because it is missing a '$Type' field to indicate which object this is.");
        }
        type = type.trim();

        if( !getTypesSupported().contains(type.toLowerCase()) ){
            throw new ParseException("Cannot parse object of type '"+type+"'.  This API version does not support that object.");
        }
    }//end isSupportedType()


    private static List<String> getTypesSupported() {
        List<String> typesSupported = new ArrayList<>();
        typesSupported.add("trustmarkdefinition");
        typesSupported.add("trustinteroperabilityprofile");
        typesSupported.add("trustmark");
        typesSupported.add("trustmarkstatusreport");
        typesSupported.add("agreement");
        typesSupported.add("agreementresponsibilitytemplate");
        return typesSupported;
    }

}//end AbstractDeserializer