package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureExpressionLeft;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureExpressionRight;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknown;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureNonTerminalUnexpected;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureParser;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureResolveTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureResolveTrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTrustmarkAbsent;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTrustmarkVerifierFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeMismatch;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeUnexpected;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeUnexpectedLeft;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeUnexpectedRight;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeUnorderableLeft;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeUnorderableRight;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureURI;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.product.P2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import java.util.HashMap;

import static java.util.Objects.requireNonNull;

@MetaInfServices
public class TrustExpressionFailureJsonProducer implements JsonProducer<TrustExpressionFailure, JSONObject> {

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);
    private static final JsonProducer<TrustInteroperabilityProfile, JSONObject> jsonProducerForTrustInteroperabilityProfile = new TrustInteroperabilityProfileJsonProducer();
    private static final JsonProducer<TrustmarkDefinitionRequirement, JSONObject> jsonProducerForTrustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementJsonProducer();

    @Override
    public Class<TrustExpressionFailure> getSupportedType() {
        return TrustExpressionFailure.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(final TrustExpressionFailure trustExpressionFailure) {
        requireNonNull(trustExpressionFailure);

        return trustExpressionFailure.match(
                (trustInteroperabilityProfileList, uriString, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureURI.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("UriString", uriString);
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, uri, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureResolveTrustInteroperabilityProfile.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("Uri", uri.toString());
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureResolveTrustmarkDefinition.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                }}),
                (trustInteroperabilityProfileList, uri, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureResolveTrustmarkDefinition.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("Uri", uri.toString());
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, expression, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureParser.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("Expression", expression);
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, identifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureIdentifierUnknown.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("Identifier", identifier);
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustmarkDefinitionRequirementIdentifier", trustmarkDefinitionRequirementIdentifier);
                    put("TrustmarkDefinitionParameterIdentifier", trustmarkDefinitionParameterIdentifier);
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustmarkDefinitionRequirement", serializeTrustmarkDefinitionRequirement(trustmarkDefinitionRequirement));
                    put("TrustmarkDefinitionParameterIdentifier", trustmarkDefinitionParameterIdentifier);
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustmarkDefinitionRequirementIdentifier", trustmarkDefinitionRequirementIdentifier);
                    put("TrustmarkDefinitionParameterIdentifier", trustmarkDefinitionParameterIdentifier);
                }}),
                (trustInteroperabilityProfileList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureNonTerminalUnexpected.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTrustmarkAbsent.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustmarkDefinitionRequirement", serializeTrustmarkDefinitionRequirement(trustmarkDefinitionRequirement));
                    put("TrustmarkDefinitionParameterIdentifier", trustmarkDefinitionParameter.getIdentifier());
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkVerifierFailureNonEmptyList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTrustmarkVerifierFailure.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustmarkDefinitionRequirement", serializeTrustmarkDefinitionRequirement(trustmarkDefinitionRequirement));
                    put("TrustmarkVerifierFailureNonEmptyList", new JSONArray(trustmarkVerifierFailureNonEmptyList.map(TrustmarkVerifierFailure::messageFor).toCollection()));
                }}),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnexpected.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionTypeExpected", new JSONArray(typeExpected.map(type -> type.bimap(parameterKind -> parameterKind.name(), trustExpressionType -> trustExpressionType.getClass().getSimpleName())).toCollection()));
                    put("TrustExpressionTypeActual", typeActual.bimap(parameterKind -> parameterKind.name(), trustExpressionType -> trustExpressionType.getClass().getSimpleName()));
                }}),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnexpectedLeft.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionTypeExpected", new JSONArray(typeExpected.map(Object::getClass).map(Class::getSimpleName).toCollection()));
                    put("TrustExpressionTypeActual", typeActual.getClass().getSimpleName());
                }}),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnexpectedRight.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionTypeExpected", new JSONArray(typeExpected.map(Object::getClass).map(Class::getSimpleName).toCollection()));
                    put("TrustExpressionTypeActual", typeActual.getClass().getSimpleName());
                }}),
                (trustInteroperabilityProfileList, typeLeft, typeRight) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeMismatch.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionTypeLeft", typeLeft.getClass().getSimpleName());
                    put("TrustExpressionTypeRight", typeRight.getClass().getSimpleName());
                }}),
                (trustInteroperabilityProfileList, type) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnorderableLeft.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionType", type.getClass().getSimpleName());
                }}),
                (trustInteroperabilityProfileList, type) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnorderableRight.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionType", type.getClass().getSimpleName());
                }}),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureExpression.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionFailureList", new JSONArray(trustExpressionFailureNonEmptyList.map(TrustExpressionFailureJsonProducer.this::serialize).toCollection()));
                }}),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureExpressionLeft.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionFailureList", new JSONArray(trustExpressionFailureNonEmptyList.map(TrustExpressionFailureJsonProducer.this::serialize).toCollection()));
                }}),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureExpressionRight.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(TrustExpressionFailureJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustExpressionFailureList", new JSONArray(trustExpressionFailureNonEmptyList.map(TrustExpressionFailureJsonProducer.this::serialize).toCollection()));
                }}));
    }

    private static JSONObject serializeTrustInteroperabilityProfile(final TrustInteroperabilityProfile trustInteroperabilityProfile) {
        return new JSONObject(new HashMap<String, Object>() {{
            put("Identifier", trustInteroperabilityProfile.getIdentifier());
            put("Name", trustInteroperabilityProfile.getName());
        }});
    }

    private static JSONObject serializeTrustmarkDefinitionRequirement(final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement) {
        return new JSONObject(new HashMap<String, Object>() {{
            put("Name", trustmarkDefinitionRequirement.getName());
        }});
    }
}
