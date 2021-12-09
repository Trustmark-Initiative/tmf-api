package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.ArtifactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.AssessmentStepImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.CitationImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ConformanceCriterionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.SourceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionMetadataImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.function.Try1;
import org.json.JSONObject;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.assertSupported;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readBooleanOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readDate;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readEntity;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readFromMap;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readInt;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObject;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readSource;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readString;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURI;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkDefinitionJsonDeserializer implements JsonDeserializer<TrustmarkDefinition> {

    private static final Logger log = LogManager.getLogger(TrustmarkDefinitionJsonDeserializer.class);

    public TrustmarkDefinition deserialize(String jsonString) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark Definition JSON . . .");

        final JSONObject jsonObject = new JSONObject(jsonString);
        assertSupported(jsonObject);

        final TrustmarkDefinitionImpl trustmarkDefinition = new TrustmarkDefinitionImpl();

        final TreeMap<String, SourceImpl> sourceTreeMap = readJSONObjectList(jsonObject, "Sources")
                .mapException(jsonObjectInner -> p(readString(jsonObjectInner, "$id"), readSource(jsonObjectInner)))
                .foldLeft(tree -> p -> tree.set(p._1(), p._2()), TreeMap.empty(stringOrd));

        final TreeMap<String, ConformanceCriterionImpl> conformanceCriterionTreeMap = readJSONObjectList(jsonObject, "ConformanceCriteria")
                .mapException(readConformanceCriterion(sourceTreeMap))
                .foldLeft(tree -> conformanceCriterion -> tree.set(conformanceCriterion.getId(), conformanceCriterion), TreeMap.empty(stringOrd));

        trustmarkDefinition.setOriginalSource(jsonString);
        trustmarkDefinition.setOriginalSourceType(SerializerJson.APPLICATION_JSON);

        trustmarkDefinition.setIssuanceCriteria(readString(jsonObject, "IssuanceCriteria"));
        trustmarkDefinition.setMetadata(readMetadata(readJSONObject(jsonObject, "Metadata")));

        readJSONObjectList(jsonObject, "AssessmentSteps").mapException(readAssessmentStep(conformanceCriterionTreeMap)).forEach(trustmarkDefinition::addAssessmentStep);
        readJSONObjectList(jsonObject, "Terms").mapException(JsonDeserializerUtility::readTerm).forEach(trustmarkDefinition::addTerm);

        readStringOption(jsonObject, "$id").forEach(trustmarkDefinition::setId);
        readStringOption(jsonObject, "AssessmentStepsPreface").forEach(trustmarkDefinition::setAssessmentStepPreface);
        readStringOption(jsonObject, "ConformanceCriteriaPreface").forEach(trustmarkDefinition::setConformanceCriteriaPreface);

        conformanceCriterionTreeMap.values().forEach(trustmarkDefinition::addConformanceCriterion);
        sourceTreeMap.values().forEach(trustmarkDefinition::addSource);

        return trustmarkDefinition;
    }

    private static Try1<JSONObject, AssessmentStepImpl, ParseException> readAssessmentStep(final TreeMap<String, ConformanceCriterionImpl> map) {
        requireNonNull(map);

        return jsonObject -> {
            requireNonNull(jsonObject);

            final AssessmentStepImpl assessmentStep = new AssessmentStepImpl();

            assessmentStep.setDescription(readString(jsonObject, "Description"));
            assessmentStep.setId(readString(jsonObject, "$id"));
            assessmentStep.setName(readString(jsonObject, "Name"));
            assessmentStep.setNumber(readInt(jsonObject, "Number"));

            readJSONObjectList(jsonObject, "Artifacts").mapException(TrustmarkDefinitionJsonDeserializer::readArtifact).forEach(assessmentStep::addArtifact);
            readJSONObjectList(jsonObject, "ConformanceCriteria").mapException(readFromMap(map, TrustmarkDefinitionJsonDeserializer::readRef)).forEach(assessmentStep::addConformanceCriterion);
            readJSONObjectList(jsonObject, "ParameterDefinitions").mapException(TrustmarkDefinitionJsonDeserializer::readTrustmarkDefinitionParameter).forEach(assessmentStep::addParameter);

            return assessmentStep;
        };
    }

    private static Try1<JSONObject, ConformanceCriterionImpl, ParseException> readConformanceCriterion(final TreeMap<String, SourceImpl> map) {
        requireNonNull(map);

        return jsonObject -> {
            requireNonNull(jsonObject);
            final ConformanceCriterionImpl conformanceCriterion = new ConformanceCriterionImpl();

            conformanceCriterion.setDescription(readString(jsonObject, "Description"));
            conformanceCriterion.setId(readString(jsonObject, "$id"));
            conformanceCriterion.setName(readString(jsonObject, "Name"));
            conformanceCriterion.setNumber(readInt(jsonObject, "Number"));

            readJSONObjectList(jsonObject, "Citations").mapException(readCitation(map)).forEach(conformanceCriterion::addCitation);

            return conformanceCriterion;
        };
    }

    private static Try1<JSONObject, CitationImpl, ParseException> readCitation(final TreeMap<String, SourceImpl> map) {
        requireNonNull(map);

        return jsonObject -> {
            requireNonNull(jsonObject);

            final CitationImpl citation = new CitationImpl();

            citation.setDescription(readString(jsonObject, "Description"));
            citation.setSource(readFromMap(map, TrustmarkDefinitionJsonDeserializer::readRef).f(jsonObject.getJSONObject("Source")));

            return citation;
        };
    }

    private static TrustmarkDefinitionMetadataImpl readMetadata(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final TrustmarkDefinitionMetadataImpl trustmarkDefinitionMetadata = new TrustmarkDefinitionMetadataImpl();

        trustmarkDefinitionMetadata.setDeprecated(readBooleanOption(jsonObject, "Deprecated").orSome(false));
        trustmarkDefinitionMetadata.setDescription(readString(jsonObject, "Description"));
        trustmarkDefinitionMetadata.setIdentifier(readURI(jsonObject, "Identifier"));
        trustmarkDefinitionMetadata.setName(readString(jsonObject, "Name"));
        trustmarkDefinitionMetadata.setPublicationDateTime(readDate(jsonObject, "PublicationDateTime"));
        trustmarkDefinitionMetadata.setTrustmarkDefiningOrganization(readEntity(readJSONObject(jsonObject, "TrustmarkDefiningOrganization")));
        trustmarkDefinitionMetadata.setTypeName("TrustmarkDefinition");
        trustmarkDefinitionMetadata.setVersion(readString(jsonObject, "Version"));

        readJSONObjectList(jsonObject, "KnownConflicts").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustmarkDefinitionMetadata::addToKnownConflicts);
        readJSONObjectList(jsonObject, "Satisfies").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustmarkDefinitionMetadata::addToSatisfies);

        readJSONObjectOption(jsonObject, "Supersessions").foreachDoEffectException(jsonObjectInner -> {
            readJSONObjectList(jsonObjectInner, "Supersedes").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustmarkDefinitionMetadata::addToSupersedes);
            readJSONObjectList(jsonObjectInner, "SupersededBy").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustmarkDefinitionMetadata::addToSupersededBy);
        });

        readStringList(jsonObject, "Keywords").forEach(trustmarkDefinitionMetadata::addToKeywords);

        readStringOption(jsonObject, "AssessorQualificationsDescription").forEach(trustmarkDefinitionMetadata::setAssessorQualificationsDescription);
        readStringOption(jsonObject, "ExtensionDescription").forEach(trustmarkDefinitionMetadata::setExtensionDescription);
        readStringOption(jsonObject, "LegalNotice").forEach(trustmarkDefinitionMetadata::setLegalNotice);
        readStringOption(jsonObject, "Notes").forEach(trustmarkDefinitionMetadata::setNotes);
        readStringOption(jsonObject, "ProviderEligibilityCriteria").forEach(trustmarkDefinitionMetadata::setProviderEligibilityCriteria);
        readStringOption(jsonObject, "TargetProviderDescription").forEach(trustmarkDefinitionMetadata::setTargetProviderDescription);
        readStringOption(jsonObject, "TargetRecipientDescription").forEach(trustmarkDefinitionMetadata::setTargetRecipientDescription);
        readStringOption(jsonObject, "TargetRelyingPartyDescription").forEach(trustmarkDefinitionMetadata::setTargetRelyingPartyDescription);
        readStringOption(jsonObject, "TargetStakeholderDescription").forEach(trustmarkDefinitionMetadata::setTargetStakeholderDescription);
        readStringOption(jsonObject, "TrustmarkRevocationCriteria").forEach(trustmarkDefinitionMetadata::setTrustmarkRevocationCriteria);

        return trustmarkDefinitionMetadata;
    }

    private static ArtifactImpl readArtifact(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final ArtifactImpl artifact = new ArtifactImpl();

        artifact.setName(readString(jsonObject, "Name"));
        artifact.setDescription(readString(jsonObject, "Description"));

        return artifact;
    }

    private static TrustmarkDefinitionParameterImpl readTrustmarkDefinitionParameter(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final TrustmarkDefinitionParameterImpl trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();

        trustmarkDefinitionParameter.setDescription(readString(jsonObject, "Description"));
        trustmarkDefinitionParameter.setIdentifier(readString(jsonObject, "Identifier"));
        trustmarkDefinitionParameter.setName(readString(jsonObject, "Name"));
        trustmarkDefinitionParameter.setParameterKind(ParameterKind.fromString(readString(jsonObject, "ParameterKind")));
        trustmarkDefinitionParameter.setRequired(readBooleanOption(jsonObject, "Required").orSome(false));

        readStringList(jsonObject, "EnumValues").forEach(trustmarkDefinitionParameter::addEnumValue);

        return trustmarkDefinitionParameter;
    }

    private static String readRef(final JSONObject jsonObjectInner) {
        String ref = jsonObjectInner.optString("$ref");
        if (ref != null && ref.startsWith("#")) ref = ref.substring(1);
        return ref;
    }
}
