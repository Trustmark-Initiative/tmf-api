package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by brad on 12/10/15.
 */
public class TrustInteroperabilityProfileJsonDeserializer extends AbstractDeserializer {

    private static final Logger log = Logger.getLogger(TrustInteroperabilityProfileJsonDeserializer.class);


    public static TrustInteroperabilityProfile deserialize(String jsonString ) throws ParseException {
        log.debug("Deserializing TrustInteroperabilityProfile JSON...");

        JSONObject jsonObject = new JSONObject(jsonString);
        isSupported(jsonObject);

        TrustInteroperabilityProfileImpl tip = new TrustInteroperabilityProfileImpl();
        tip.setOriginalSource(jsonString);
        tip.setOriginalSourceType("application/json");

        tip.setTypeName("TrustInteroperabilityProfile");
        tip.setIdentifier(getUri(jsonObject, "Identifier", true));
        tip.setName(getString(jsonObject, "Name", true));
        tip.setVersion(getString(jsonObject, "Version", true));
        tip.setDescription(getString(jsonObject, "Description", true));
        tip.setPublicationDateTime(getDate(jsonObject, "PublicationDateTime", true));
        tip.setTrustExpression(getString(jsonObject, "TrustExpression", true));
        String primaryTip =  getString(jsonObject, "Primary", false);
        if(primaryTip != null && primaryTip.equals("true")) {
            tip.setPrimary(Boolean.TRUE);
        }
        String moniker =  getString(jsonObject, "Moniker", false);
        if(moniker != null) {
            tip.setMoniker(moniker);
        }
        tip.setLegalNotice(getString(jsonObject, "LegalNotice", false));
        tip.setNotes(getString(jsonObject, "Notes", false));

        tip.setIssuer(readEntity(jsonObject, "Issuer", true));

        if( jsonObject.has("Deprecated") ){
            tip.setDeprecated(jsonObject.getBoolean("Deprecated"));
        }else{
            tip.setDeprecated(false);
        }

        JSONObject supersessionObj = jsonObject.optJSONObject("Supersessions");
        if( supersessionObj != null ){
            log.debug("Encountered TIP Supersessions...");
            JSONArray supersedesArrayJson = supersessionObj.optJSONArray("Supersedes");
            if( supersedesArrayJson != null ){
                for( int supersedesIdx = 0; supersedesIdx < supersedesArrayJson.length(); supersedesIdx++ ){
                    log.debug("Encountered TIP -> Supersessions -> Supersedes["+supersedesIdx+"]...");
                    tip.addToSupersedes(readTFIFDirectly(supersedesArrayJson.optJSONObject(supersedesIdx)));
                }
            }
            JSONArray supersededByArrayJson = supersessionObj.optJSONArray("SupersededBy");
            if( supersededByArrayJson != null ){
                for( int supersededByIdx = 0; supersededByIdx < supersededByArrayJson.length(); supersededByIdx++ ){
                    log.debug("Encountered TIP -> Supersessions -> SupersededBy["+supersededByIdx+"]...");
                    tip.addToSupersededBy(readTFIFDirectly(supersededByArrayJson.optJSONObject(supersededByIdx)));
                }
            }
        }
        log.debug("There are "+tip.getSupersededBy().size()+" superseded by references and "+tip.getSupersedes().size()+" supersedes references");


        JSONArray satisfiesArray = jsonObject.optJSONArray("Satisfies");
        if( satisfiesArray == null && jsonObject.has("Satisfies") ){
            satisfiesArray = new JSONArray();
            satisfiesArray.put(jsonObject.getJSONObject("Satisfies"));
        }
        if( satisfiesArray != null && satisfiesArray.length() > 0 ){
            for( int i = 0; i < satisfiesArray.length(); i++ ){
                JSONObject satisfiesRefJSON = satisfiesArray.getJSONObject(i);
                tip.addToSatisfies(readTFIFDirectly(satisfiesRefJSON));
            }
        }

        JSONArray knownConflictsArray = jsonObject.optJSONArray("KnownConflicts");
        if( knownConflictsArray != null && knownConflictsArray.length() > 0 ){
            for( int i = 0; i < knownConflictsArray.length(); i++ ){
                JSONObject knownConclitsRefJSON = knownConflictsArray.optJSONObject(i);
                tip.addToKnownConflict(readTFIFDirectly(knownConclitsRefJSON));
            }
        }


        JSONArray termsArrayJson = jsonObject.optJSONArray("Terms");
        if( termsArrayJson != null ){
            for( int termIndex = 0; termIndex < termsArrayJson.length(); termIndex++ ){
                JSONObject termJson = (JSONObject) termsArrayJson.get(termIndex);
                tip.addTerm(readTerm(termJson));
            }
        }

        HashMap<String, SourceImpl> sourceMap = new HashMap<String, SourceImpl>();
        JSONArray sourcesArrayJson = jsonObject.optJSONArray("Sources");
        if( sourcesArrayJson != null ){
            for( int sourcesIndex = 0; sourcesIndex < sourcesArrayJson.length(); sourcesIndex++ ){
                JSONObject sourceJson = sourcesArrayJson.getJSONObject(sourcesIndex);
                SourceImpl source = readSource(sourceJson);
                tip.addSource( source );
                String id = getString(sourceJson, "$id", false);
                sourceMap.put(id, source);
            }
        }

        JSONArray keywordsArrayJson = jsonObject.optJSONArray("Keywords");
        if( keywordsArrayJson != null ){
            for( int keywordIndex = 0; keywordIndex < keywordsArrayJson.length(); keywordIndex++ ){
                String keyword = (String) keywordsArrayJson.get(keywordIndex);
                tip.addToKeywords(keyword);
            }
        }


        HashMap<String, Entity> providerReferenceMap = buildProviderReferenceMap(jsonObject);

        // Now we build all the references...
        if( jsonObject.has("References") ){
            JSONObject refsJSON = jsonObject.getJSONObject("References");
            if( !refsJSON.has("TrustInteroperabilityProfileReferences") && !refsJSON.has("TrustmarkDefinitionRequirements") )
                throw new ParseException("TrustInteroperabilityProfiles References must contain an array named 'TrustInteroperabilityProfileReferences' or 'TrustmarkDefinitionRequirements'.");


            JSONArray tipRefArray = refsJSON.optJSONArray("TrustInteroperabilityProfileReferences");
            if( tipRefArray != null ) {
                for (int i = 0; i < tipRefArray.length(); i++ ){
                    JSONObject tipRefJSON = tipRefArray.getJSONObject(i);
                    tip.addReference(buildTrustInteroperabilityProfileReference(tipRefJSON));
                }
            }

            JSONArray tdReqRefArray = refsJSON.optJSONArray("TrustmarkDefinitionRequirements");
            if( tdReqRefArray != null ) {
                for (int i = 0; i < tdReqRefArray.length(); i++ ){
                    JSONObject tdReqRefJSON = tdReqRefArray.getJSONObject(i);
                    tip.addReference(buildTrustmarkDefinitionRequirement(tdReqRefJSON, providerReferenceMap));
                }
            }

        }else{
            throw new ParseException("TrustInteroperabilityProfiles are expected to have a 'References' section with TD and TIP references.");
        }

        return tip;
    }//end deserialize

    private static TrustmarkDefinitionRequirementImpl buildTrustmarkDefinitionRequirement(JSONObject tdReqObj, HashMap<String, Entity> providerReferenceMap)
    throws ParseException {
        TrustmarkDefinitionRequirementImpl tdReq = new TrustmarkDefinitionRequirementImpl();
        tdReq.setTypeName("TrustmarkDefinitionRequirement");
        tdReq.setId(getString(tdReqObj, "$id", true));

        if( !tdReqObj.has("TrustmarkDefinitionReference") ){
            throw new ParseException("TrustmarkDefinitionRequirement is missing the TrustmarkDefinitionReference!");
        }
        JSONObject tdRefJSON = tdReqObj.getJSONObject("TrustmarkDefinitionReference");
        tdReq.setIdentifier(getUri(tdRefJSON, "Identifier", true));
        tdReq.setName(getString(tdRefJSON, "Name", false));
        if(exists(tdRefJSON, "Number")) {
            tdReq.setNumber(getNumber(tdRefJSON, "Number", false).intValue());
        }
        tdReq.setVersion(getString(tdRefJSON, "Version", false));
        tdReq.setDescription(getString(tdRefJSON, "Description", false));

        if( tdReqObj.has("ProviderReferences") || (tdReqObj.has("ProviderReference") && tdReqObj.optJSONArray("ProviderReference") != null)){
            JSONArray refArray = null;
            if( tdReqObj.has("ProviderReferences") ) {
                refArray = tdReqObj.getJSONArray("ProviderReferences");
            }else{
                refArray = tdReqObj.optJSONArray("ProviderReference");
            }
            for( int i = 0; i < refArray.length(); i++ ){
                JSONObject refJson = refArray.getJSONObject(i);
                String refVal = null;
                if( refJson.has("$id") ){
                    refVal = refJson.getString("$id");
                }else if( refJson.has("$ref") ){
                    refVal = refJson.getString("$ref");
                    if( refVal.startsWith("#") ){
                        refVal = refVal.substring(1);
                    }
                }
                Entity provider = providerReferenceMap.get(refVal);
                if( provider == null )
                    throw new ParseException("Could not find reference '"+refVal+"' from TD Requirement '"+tdReq.getId()+"' in which there were "+providerReferenceMap.keySet().size()+" total references to find from!");
                tdReq.addProviderReference(provider);
            }
        }else if( tdReqObj.has("ProviderReference") ){
            JSONObject providerJSON = tdReqObj.optJSONObject("ProviderReference");
            if( providerJSON != null ){
                String refVal = null;
                if( providerJSON.has("$id") ){
                    refVal = providerJSON.getString("$id");
                }else if( providerJSON.has("$ref") ){
                    refVal = providerJSON.getString("$ref");
                    if( refVal.startsWith("#") ){
                        refVal = refVal.substring(1);
                    }
                }
                Entity provider = providerReferenceMap.get(refVal);
                if( provider == null )
                    throw new ParseException("Could not find reference '"+refVal+"' from TD Requirement '"+tdReq.getId()+"'!");
                tdReq.addProviderReference(provider);
            }
        }

        return tdReq;
    }

    private static TrustInteroperabilityProfileReferenceImpl buildTrustInteroperabilityProfileReference(JSONObject tipRefObj)
    throws ParseException {
        TrustInteroperabilityProfileReferenceImpl tipRef = new TrustInteroperabilityProfileReferenceImpl();
        tipRef.setTypeName("TrustInteroperabilityProfileReference");
        tipRef.setId(getString(tipRefObj, "$id", true));
        tipRef.setIdentifier(getUri(tipRefObj, "Identifier", true));
        tipRef.setName(getString(tipRefObj, "Name", false));
        if (exists(tipRefObj, "Number"))  {
            tipRef.setNumber(getNumber(tipRefObj, "Number", false).intValue());
        }
        tipRef.setVersion(getString(tipRefObj, "Version", false));
        tipRef.setDescription(getString(tipRefObj, "Description", false));
        return tipRef;
    }


    private static HashMap<String, Entity> buildProviderReferenceMap(JSONObject tipJson) throws ParseException {
        HashMap<String, Entity> providerReferenceMap = new HashMap<>();

        if( tipJson.has("References") ){
            JSONObject refsJSON = tipJson.getJSONObject("References");
            if( !refsJSON.has("TrustInteroperabilityProfileReferences") && !refsJSON.has("TrustmarkDefinitionRequirements") )
                throw new ParseException("TrustInteroperabilityProfiles References must contain an array named 'TrustInteroperabilityProfileReferences' or 'TrustmarkDefinitionRequirements'.");

            JSONArray tdReqRefArray = refsJSON.optJSONArray("TrustmarkDefinitionRequirements");
            if( tdReqRefArray != null ) {
                for (int i = 0; i < tdReqRefArray.length(); i++ ){
                    JSONObject tdReqRefJSON = tdReqRefArray.getJSONObject(i);
                    if( tdReqRefJSON.has("ProviderReferences") || (tdReqRefJSON.has("ProviderReference") && tdReqRefJSON.optJSONArray("ProviderReference") != null) ){
                        JSONArray providerRefArray = null;
                        if( tdReqRefJSON.has("ProviderReferences") ) {
                            providerRefArray = tdReqRefJSON.getJSONArray("ProviderReferences");
                        }else{
                            providerRefArray = tdReqRefJSON.optJSONArray("ProviderReference");
                        }
                        log.debug("Found "+providerRefArray.length()+" provider references...");
                        for( int j = 0; j < providerRefArray.length(); j++ ){
                            JSONObject providerJson = providerRefArray.getJSONObject(j);
                            if( providerJson.has("$id") ){
                                EntityImpl provider = readEntityReference(providerJson);
                                String id = getString(providerJson, "$id", true);
                                log.info("Storing provider reference #"+id);
                                providerReferenceMap.put(id, provider);
                            }
                        }
                    }else if( tdReqRefJSON.has("ProviderReference") ) {
                        JSONObject providerJson = tdReqRefJSON.optJSONObject("ProviderReference");
                        if( providerJson.has("$id") ){
                            EntityImpl provider = readEntityReference(providerJson);
                            String id = getString(providerJson, "$id", true);
                            log.info("Storing provider reference #"+id);
                            providerReferenceMap.put(id, provider);
                        }
                    }
                }
            }

        }
        return providerReferenceMap;
    }//end buildProviderReferenceMap()


}//end TrustmarkJsonDeserializer()