package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonUtils;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.JsonProducerUtility.toJson;

/**
 * Created by brad on 1/7/16.
 */
public final class TIPJsonProducer implements JsonProducer<TrustInteroperabilityProfile, JSONObject> {

    private static final Logger log = LogManager.getLogger(TIPJsonProducer.class);

    @Override
    public Class<TrustInteroperabilityProfile> getSupportedType() {
        return TrustInteroperabilityProfile.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(TrustInteroperabilityProfile tip) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("$TMF_VERSION", FactoryLoader.getInstance(TrustmarkFramework.class).getTrustmarkFrameworkVersion());
        jsonObject.put("$Type", TrustInteroperabilityProfile.class.getSimpleName());
        jsonObject.put("Identifier", tip.getIdentifier().toString());
        jsonObject.put("Name", tip.getName());
        jsonObject.put("Version", tip.getVersion());
        jsonObject.put("Description", tip.getDescription());
        jsonObject.put("Primary", tip.isPrimary() ? "true" : "false");
//        jsonObject.put("Moniker", tip.getMoniker());

        jsonObject.put("PublicationDateTime", JsonUtils.toDateTimeString(tip.getPublicationDateTime()));
        if (tip.getLegalNotice() != null)
            jsonObject.put("LegalNotice", tip.getLegalNotice());
        if (tip.getNotes() != null)
            jsonObject.put("Notes", tip.getNotes());

        jsonObject.put("Issuer", toJson(tip.getIssuer()));

        if (tip.isDeprecated())
            jsonObject.put("Deprecated", Boolean.TRUE);

        if (JsonProducerUtility.collectionNotEmpty(tip.getSupersedes()) || JsonProducerUtility.collectionNotEmpty(tip.getSupersededBy())) {
            JSONObject supersessionsJson = new JSONObject();
            if (JsonProducerUtility.collectionNotEmpty(tip.getSupersedes())) {
                JSONArray supersedesObjs = new JSONArray();
                for (TrustmarkFrameworkIdentifiedObject supersedes : tip.getSupersedes()) {
                    supersedesObjs.put(JsonProducerUtility.createJsonReference(supersedes));
                }
                supersessionsJson.put("Supersedes", supersedesObjs);
            }
            if (JsonProducerUtility.collectionNotEmpty(tip.getSupersededBy())) {
                JSONArray supersedesObjs = new JSONArray();
                for (TrustmarkFrameworkIdentifiedObject supersededBy : tip.getSupersededBy()) {
                    supersedesObjs.put(JsonProducerUtility.createJsonReference(supersededBy));
                }
                supersessionsJson.put("SupersededBy", supersedesObjs);
            }
            jsonObject.put("Supersessions", supersessionsJson);
        }

        if (tip.getSatisfies() != null && !tip.getSatisfies().isEmpty()) {
            JSONArray satisfiesObjs = new JSONArray();
            for (TrustmarkFrameworkIdentifiedObject supersedes : tip.getSatisfies()) {
                satisfiesObjs.put(JsonProducerUtility.createJsonReference(supersedes));
            }
            jsonObject.put("Satisfies", satisfiesObjs);
        }

        if (JsonProducerUtility.collectionNotEmpty(tip.getKnownConflicts())) {
            JSONArray knownConflictsArray = new JSONArray();
            for (TrustmarkFrameworkIdentifiedObject knownConflict : tip.getKnownConflicts()) {
                knownConflictsArray.put(JsonProducerUtility.createJsonReference(knownConflict));
            }
            jsonObject.put("KnownConflicts", knownConflictsArray);
        }


        if (tip.getKeywords() != null && tip.getKeywords().size() > 0) {
            JSONArray keywords = new JSONArray();
            for (String keyword : tip.getKeywords()) {
                keywords.put(keyword);
            }
            jsonObject.put("Keywords", keywords);
        }


        List<Term> terms = tip.getTermsSorted();
        if (!terms.isEmpty()) {
            JSONArray termsArray = new JSONArray();
            for (Term term : terms) {
                termsArray.put(toJson(term));
            }
            jsonObject.put("Terms", termsArray);
        } // TODO If terms is empty, is this an error?


        Collection<Source> sources = tip.getSources();
        JSONArray sourcesArray = new JSONArray();
        for (Source source : sources) {
            sourcesArray.put(toJson(source));
        }
        jsonObject.put("Sources", sourcesArray);


        jsonObject.put("TrustExpression", tip.getTrustExpression());

        JSONObject referencesObject = new JSONObject();

        JSONArray tipReferencesArray = new JSONArray();
        JSONArray tdRequirementsArray = new JSONArray();
        HashMap<String, String> encounteredEntities = new HashMap<>();

        for (AbstractTIPReference abstractTIPReference : tip.getReferences()) {
            JSONObject refObj = (JSONObject) toJson(abstractTIPReference);  // Should match up with 'TrustmarkFrameworkIdentifiedObject'
            refObj.put("$id", abstractTIPReference.getId());
            if (abstractTIPReference.isTrustmarkDefinitionRequirement()) {
                TrustmarkDefinitionRequirement tdReqRef = (TrustmarkDefinitionRequirement) abstractTIPReference;
                List<Entity> providers = tdReqRef.getProviderReferences();
                log.trace("Providers = [" + (providers == null ? "" : Arrays.toString(providers.toArray())) + "]");
                refObj.put("$Type", "TrustmarkDefinitionRequirement");
                refObj.put("TrustmarkDefinitionReference", toTMIRefJson(tdReqRef));
                if (providers != null && providers.size() > 0) {
                    JSONArray providersArray = new JSONArray();
                    for (Entity provider : providers) {
                        if(provider.getIdentifier() == null || StringUtils.isEmpty(provider.getIdentifier().toString())){
                            log.trace("Skipping adding empty provider reference = [" + provider + "]");
                            continue;
                        }
                        if (encounteredEntities.containsKey(provider.getIdentifier().toString())) {
                            log.trace("Adding new provider reference = [" + provider + "]");
                            String providerRef = encounteredEntities.get(provider.getIdentifier().toString());
                            JSONObject providerRefJson = new JSONObject();
                            providerRefJson.put("$ref", "#" + providerRef);
                            providersArray.put(providerRefJson);
                        } else {
                            String providerRef = "provider" + provider.getIdentifier().toString().hashCode();
                            log.trace("Adding a reference to an existing provider = [" + provider + "] providerRef = [" + providerRef + "]");
                            encounteredEntities.put(provider.getIdentifier().toString(), providerRef);
                            JSONObject providerJson = (JSONObject) toJson(provider);
                            providerJson.put("$id", providerRef);
                            providersArray.put(providerJson);
                        }
                    }
                    if(providersArray != null && providersArray.length() > 0) {
                        refObj.put("ProviderReferences", providersArray);
                    }
                }
                tdRequirementsArray.put(refObj);
            } else if (abstractTIPReference.isTrustInteroperabilityProfileReference()) {
                refObj.put("$Type", "TrustInteroperabilityProfileReference");
                fillTMIRefJson(refObj, abstractTIPReference);
                tipReferencesArray.put(refObj);
            } else {
                throw new UnsupportedOperationException("Encountered unknown AbstractTIPReference type: " + abstractTIPReference.getClass().getName());
            }
        }
        if (tipReferencesArray.length() > 0) {
            referencesObject.put("TrustInteroperabilityProfileReferences", tipReferencesArray);
        }
        if (tdRequirementsArray.length() > 0) {
            referencesObject.put("TrustmarkDefinitionRequirements", tdRequirementsArray);
        }
        if (tipReferencesArray.length() == 0 && tdRequirementsArray.length() == 0) {
            log.warn("TIP[" + tip.getIdentifier().toString() + "] does not have either TD requirement references or TIP references, and is not valid.");
            throw new RuntimeException("A TIP Must have either TIP references or TD Requirement References to be considered valid.");
        }
        jsonObject.put("References", referencesObject);

        return jsonObject;
    }

    private JSONObject toTMIRefJson(TrustmarkDefinitionRequirement tdReq) {
        JSONObject json = new JSONObject();
        fillTMIRefJson(json, tdReq);
        return json;
    }

    private void fillTMIRefJson(JSONObject json, TrustmarkFrameworkIdentifiedObject tmio) {
        if (tmio.getIdentifier() != null)
            json.put("Identifier", tmio.getIdentifier().toString());
        if (notEmpty(tmio.getName()))
            json.put("Name", tmio.getName());
        if (tmio.getNumber() != null)
            json.put("Number", tmio.getNumber());
        if (notEmpty(tmio.getVersion()))
            json.put("Version", tmio.getVersion());
        if (notEmpty(tmio.getDescription()))
            json.put("Description", tmio.getDescription());
    }

    private boolean notEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }


}//end EntityJsonProducer
