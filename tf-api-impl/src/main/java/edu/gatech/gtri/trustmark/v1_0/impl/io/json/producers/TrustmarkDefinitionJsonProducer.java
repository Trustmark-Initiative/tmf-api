package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.impl.io.IdUtility;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonUtils;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.JsonProducerUtility.toJson;

/**
 * Created by brad on 1/7/16.
 */
public final class TrustmarkDefinitionJsonProducer implements JsonProducer<TrustmarkDefinition, JSONObject> {

    @Override
    public Class<TrustmarkDefinition> getSupportedType() {
        return TrustmarkDefinition.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(TrustmarkDefinition td) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("$TMF_VERSION", FactoryLoader.getInstance(TrustmarkFramework.class).getTrustmarkFrameworkVersion());
        jsonObject.put("$Type", TrustmarkDefinition.class.getSimpleName());
//        jsonObject.put("$id", td.getId() == null ? IdUtility.trustmarkDefinitionId() : td.getId()); // TODO: Add $id for consistency.

        JSONObject metadataJson = new JSONObject();
        metadataJson.put("Identifier", td.getMetadata().getIdentifier().toString());
        //metadataJson.put("TrustmarkReferenceAttributeName", td.getMetadata().getTrustmarkReferenceAttributeName().toString());
        metadataJson.put("Name", td.getMetadata().getName());
        metadataJson.put("Version", td.getMetadata().getVersion());
        metadataJson.put("Description", td.getMetadata().getDescription());
        metadataJson.put("PublicationDateTime", JsonUtils.toDateTimeString(td.getMetadata().getPublicationDateTime()));
        metadataJson.put("TrustmarkDefiningOrganization", toJson(td.getMetadata().getTrustmarkDefiningOrganization()));
        if (td.getMetadata().getTargetStakeholderDescription() != null)
            metadataJson.put("TargetStakeholderDescription", td.getMetadata().getTargetStakeholderDescription());
        if (td.getMetadata().getTargetRecipientDescription() != null)
            metadataJson.put("TargetRecipientDescription", td.getMetadata().getTargetRecipientDescription());
        if (td.getMetadata().getTargetRelyingPartyDescription() != null)
            metadataJson.put("TargetRelyingPartyDescription", td.getMetadata().getTargetRelyingPartyDescription());
        if (td.getMetadata().getTargetProviderDescription() != null)
            metadataJson.put("TargetProviderDescription", td.getMetadata().getTargetProviderDescription());
        if (td.getMetadata().getProviderEligibilityCriteria() != null)
            metadataJson.put("ProviderEligibilityCriteria", td.getMetadata().getProviderEligibilityCriteria());
        if (td.getMetadata().getAssessorQualificationsDescription() != null)
            metadataJson.put("AssessorQualificationsDescription", td.getMetadata().getAssessorQualificationsDescription());
        if (td.getMetadata().getTrustmarkRevocationCriteria() != null)
            metadataJson.put("TrustmarkRevocationCriteria", td.getMetadata().getTrustmarkRevocationCriteria());
        if (td.getMetadata().getExtensionDescription() != null)
            metadataJson.put("ExtensionDescription", td.getMetadata().getExtensionDescription());
        if (td.getMetadata().getLegalNotice() != null)
            metadataJson.put("LegalNotice", td.getMetadata().getLegalNotice());
        if (td.getMetadata().getNotes() != null)
            metadataJson.put("Notes", td.getMetadata().getNotes());

        if (td.getMetadata().isDeprecated())
            metadataJson.put("Deprecated", Boolean.TRUE);

        if (JsonProducerUtility.collectionNotEmpty(td.getMetadata().getSupersedes()) || JsonProducerUtility.collectionNotEmpty(td.getMetadata().getSupersededBy())) {
            JSONObject supersessionsJson = new JSONObject();
            if (JsonProducerUtility.collectionNotEmpty(td.getMetadata().getSupersedes())) {
                JSONArray supersedesObjs = new JSONArray();
                for (TrustmarkFrameworkIdentifiedObject supersedes : td.getMetadata().getSupersedes()) {
                    supersedesObjs.put(JsonProducerUtility.createJsonReference(supersedes));
                }
                supersessionsJson.put("Supersedes", supersedesObjs);
            }
            if (JsonProducerUtility.collectionNotEmpty(td.getMetadata().getSupersededBy())) {
                JSONArray supersedesObjs = new JSONArray();
                for (TrustmarkFrameworkIdentifiedObject supersededBy : td.getMetadata().getSupersededBy()) {
                    supersedesObjs.put(JsonProducerUtility.createJsonReference(supersededBy));
                }
                supersessionsJson.put("SupersededBy", supersedesObjs);
            }
            metadataJson.put("Supersessions", supersessionsJson);
        }

        if (td.getMetadata().getSatisfies() != null && !td.getMetadata().getSatisfies().isEmpty()) {
            JSONArray satisfiesObjs = new JSONArray();
            for (TrustmarkFrameworkIdentifiedObject supersedes : td.getMetadata().getSatisfies()) {
                satisfiesObjs.put(JsonProducerUtility.createJsonReference(supersedes));
            }
            metadataJson.put("Satisfies", satisfiesObjs);
        }
        if (JsonProducerUtility.collectionNotEmpty(td.getMetadata().getKnownConflicts())) {
            JSONArray knownConflictsArray = new JSONArray();
            for (TrustmarkFrameworkIdentifiedObject knownConflict : td.getMetadata().getKnownConflicts()) {
                knownConflictsArray.put(JsonProducerUtility.createJsonReference(knownConflict));
            }
            metadataJson.put("KnownConflicts", knownConflictsArray);
        }


        if (td.getMetadata().getKeywords() != null && td.getMetadata().getKeywords().size() > 0) {
            JSONArray keywords = new JSONArray();
            for (String keyword : td.getMetadata().getKeywords()) {
                keywords.put(keyword);
            }
            metadataJson.put("Keywords", keywords);
        }

        jsonObject.put("Metadata", metadataJson);


        List<Term> terms = td.getTermsSorted();
        if (!terms.isEmpty()) {
            JSONArray termsArray = new JSONArray();
            for (Term term : terms) {
                termsArray.put(toJson(term));
            }
            jsonObject.put("Terms", termsArray);
        } // TODO If terms is empty, is this an error?


        Collection<Source> sources = td.getSources();
        JSONArray sourcesArray = new JSONArray();
        for (Source source : sources) {
            sourcesArray.put(toJson(source));
        }
        jsonObject.put("Sources", sourcesArray);


        jsonObject.put("ConformanceCriteriaPreface", td.getConformanceCriteriaPreface());
        JSONArray critArray = new JSONArray();
        for (ConformanceCriterion crit : td.getConformanceCriteria()) {
            critArray.put(toJson(crit));
        }
        jsonObject.put("ConformanceCriteria", critArray);


        jsonObject.put("AssessmentStepsPreface", td.getAssessmentStepPreface());
        JSONArray stepArray = new JSONArray();
        for (AssessmentStep step : td.getAssessmentSteps()) {
            stepArray.put(toJson(step));
        }
        jsonObject.put("AssessmentSteps", stepArray);

        jsonObject.put("IssuanceCriteria", td.getIssuanceCriteria());

        return jsonObject;
    }


}
