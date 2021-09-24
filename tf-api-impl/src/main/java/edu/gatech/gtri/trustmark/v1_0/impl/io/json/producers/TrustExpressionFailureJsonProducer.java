package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureExpressionLeft;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureExpressionRight;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknown;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureNonTerminalUnexpected;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureResolveTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureResolveTrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureTrustmarkAbsent;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureTypeMismatch;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureTypeUnexpected;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureTypeUnexpectedLeft;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureTypeUnexpectedRight;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureTypeUnorderableLeft;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureTypeUnorderableRight;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.TrustExpressionFailureURI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

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
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("UriString", uriString);
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, uri, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureResolveTrustInteroperabilityProfile.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("Uri", uri.toString());
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, uri, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureResolveTrustmarkDefinition.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("Uri", uri.toString());
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, expression, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureParser.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("Expression", expression);
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, identifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureIdentifierUnknown.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("Identifier", identifier);
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustmarkDefinitionRequirementIdentifier", trustmarkDefinitionRequirementIdentifier);
                    put("TrustmarkDefinitionParameterIdentifier", trustmarkDefinitionParameterIdentifier);
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustmarkDefinitionRequirement", jsonProducerForTrustmarkDefinitionRequirement.serialize(trustmarkDefinitionRequirement));
                    put("TrustmarkDefinitionParameterIdentifier", trustmarkDefinitionParameterIdentifier);
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustmarkDefinitionRequirementIdentifier", trustmarkDefinitionRequirementIdentifier);
                    put("TrustmarkDefinitionParameterIdentifier", trustmarkDefinitionParameterIdentifier);
                }}),
                (trustInteroperabilityProfileList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureNonTerminalUnexpected.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                }}),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTrustmarkAbsent.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustmarkDefinitionRequirement", jsonProducerForTrustmarkDefinitionRequirement.serialize(trustmarkDefinitionRequirement));
                    put("TrustmarkDefinitionParameterIdentifier", trustmarkDefinitionParameter.getIdentifier());
                }}),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnexpected.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionTypeExpected", typeExpected);
                    put("TrustExpressionTypeActual", typeActual);
                }}),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnexpectedLeft.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionTypeExpected", typeExpected);
                    put("TrustExpressionTypeActual", typeActual);
                }}),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnexpectedRight.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionTypeExpected", typeExpected);
                    put("TrustExpressionTypeActual", typeActual);
                }}),
                (trustInteroperabilityProfileList, typeLeft, typeRight) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeMismatch.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionTypeLeft", typeLeft);
                    put("TrustExpressionTypeRight", typeRight);
                }}),
                (trustInteroperabilityProfileList, type) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnorderableLeft.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionType", type);
                }}),
                (trustInteroperabilityProfileList, type) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureTypeUnorderableRight.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionType", type);
                }}),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureExpression.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionFailureList", new JSONArray(trustExpressionFailureNonEmptyList.map(TrustExpressionFailureJsonProducer.this::serialize).toCollection()));
                }}),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureExpressionLeft.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionFailureList", new JSONArray(trustExpressionFailureNonEmptyList.map(TrustExpressionFailureJsonProducer.this::serialize).toCollection()));
                }}),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailureExpressionRight.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustExpressionFailureList", new JSONArray(trustExpressionFailureNonEmptyList.map(TrustExpressionFailureJsonProducer.this::serialize).toCollection()));
                }}));
    }
}
