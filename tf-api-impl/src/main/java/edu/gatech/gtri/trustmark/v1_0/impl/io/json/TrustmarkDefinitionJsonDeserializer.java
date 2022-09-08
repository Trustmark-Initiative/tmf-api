package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.ArtifactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.AssessmentStepImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.CitationImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ConformanceCriterionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.SourceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.function.Try1;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

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
 * Implementations deserialize JSON and, optionally, a URI into a Trust
 * Interoperability Profile.
 *
 * @author GTRI Trustmark Team
 */
public final class TrustmarkDefinitionJsonDeserializer implements JsonDeserializer<TrustmarkDefinition> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkDefinitionJsonDeserializer.class);

    private final boolean withTerms;

    public TrustmarkDefinitionJsonDeserializer(final boolean withTerms) {
        this.withTerms = withTerms;
    }

    public TrustmarkDefinition deserialize(final String jsonString, final URI uri) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark Definition JSON . . .");

        final JSONObject jsonObject = readJSONObject(jsonString);
        assertSupported(jsonObject);

        final TrustmarkDefinitionImpl trustmarkDefinition = new TrustmarkDefinitionImpl();

        final TreeMap<String, SourceImpl> sourceTreeMap = readJSONObjectList(jsonObject, "Sources")
                .mapException(jsonObjectInner -> p(readString(jsonObjectInner, "$id"), readSource(jsonObjectInner)))
                .foldLeft(tree -> p -> tree.set(p._1(), p._2()), TreeMap.empty(stringOrd));

        final TreeMap<String, ConformanceCriterionImpl> conformanceCriterionTreeMap = readJSONObjectList(jsonObject, "ConformanceCriteria")
                .mapException(readConformanceCriterion(sourceTreeMap))
                .foldLeft(tree -> conformanceCriterion -> tree.set(conformanceCriterion.getId(), conformanceCriterion), TreeMap.empty(stringOrd));

        trustmarkDefinition.setIssuanceCriteria(readString(jsonObject, "IssuanceCriteria"));
        readMetadata(trustmarkDefinition, readJSONObject(jsonObject, "Metadata"));

        readJSONObjectList(jsonObject, "AssessmentSteps").mapException(readAssessmentStep(conformanceCriterionTreeMap)).forEach(trustmarkDefinition::addAssessmentStep);
        if (withTerms) {
            readJSONObjectList(jsonObject, "Terms").mapException(JsonDeserializerUtility::readTerm).forEach(trustmarkDefinition::addTerm);
        }

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

    private static TrustmarkDefinitionImpl readMetadata(final TrustmarkDefinitionImpl trustmarkDefinition, final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        trustmarkDefinition.setDeprecated(readBooleanOption(jsonObject, "Deprecated").orSome(false));
        trustmarkDefinition.setDescription(readString(jsonObject, "Description"));
        trustmarkDefinition.setIdentifier(readURI(jsonObject, "Identifier"));
        trustmarkDefinition.setName(readString(jsonObject, "Name"));
        trustmarkDefinition.setPublicationDateTime(readDate(jsonObject, "PublicationDateTime"));
        trustmarkDefinition.setTrustmarkDefiningOrganization(readEntity(readJSONObject(jsonObject, "TrustmarkDefiningOrganization")));
        trustmarkDefinition.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUSTMARK_DEFINITION);
        trustmarkDefinition.setVersion(readString(jsonObject, "Version"));

        readJSONObjectList(jsonObject, "KnownConflicts").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustmarkDefinition::addToKnownConflict);
        readJSONObjectList(jsonObject, "Satisfies").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustmarkDefinition::addToSatisfies);

        readJSONObjectOption(jsonObject, "Supersessions").foreachDoEffectException(jsonObjectInner -> {
            readJSONObjectList(jsonObjectInner, "Supersedes").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustmarkDefinition::addToSupersedes);
            readJSONObjectList(jsonObjectInner, "SupersededBy").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustmarkDefinition::addToSupersededBy);
        });

        readStringList(jsonObject, "Keywords").forEach(trustmarkDefinition::addToKeywords);

        readStringOption(jsonObject, "AssessorQualificationsDescription").forEach(trustmarkDefinition::setAssessorQualificationsDescription);
        readStringOption(jsonObject, "ExtensionDescription").forEach(trustmarkDefinition::setExtensionDescription);
        readStringOption(jsonObject, "LegalNotice").forEach(trustmarkDefinition::setLegalNotice);
        readStringOption(jsonObject, "Notes").forEach(trustmarkDefinition::setNotes);
        readStringOption(jsonObject, "ProviderEligibilityCriteria").forEach(trustmarkDefinition::setProviderEligibilityCriteria);
        readStringOption(jsonObject, "TargetProviderDescription").forEach(trustmarkDefinition::setTargetProviderDescription);
        readStringOption(jsonObject, "TargetRecipientDescription").forEach(trustmarkDefinition::setTargetRecipientDescription);
        readStringOption(jsonObject, "TargetRelyingPartyDescription").forEach(trustmarkDefinition::setTargetRelyingPartyDescription);
        readStringOption(jsonObject, "TargetStakeholderDescription").forEach(trustmarkDefinition::setTargetStakeholderDescription);
        readStringOption(jsonObject, "TrustmarkRevocationCriteria").forEach(trustmarkDefinition::setTrustmarkRevocationCriteria);

        return trustmarkDefinition;
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
