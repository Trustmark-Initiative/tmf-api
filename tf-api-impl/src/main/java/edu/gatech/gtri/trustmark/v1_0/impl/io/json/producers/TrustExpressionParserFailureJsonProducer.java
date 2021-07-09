package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import static java.util.Objects.requireNonNull;

@MetaInfServices
public class TrustExpressionParserFailureJsonProducer implements JsonProducer<TrustExpressionParserFailure, JSONObject> {

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);
    private static final JsonProducer<TrustInteroperabilityProfile, JSONObject> jsonProducerForTrustInteroperabilityProfile = jsonManager.findProducerStrict(TrustInteroperabilityProfile.class, JSONObject.class).some();

    @Override
    public Class<TrustExpressionParserFailure> getSupportedType() {
        return TrustExpressionParserFailure.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(final TrustExpressionParserFailure trustExpressionParserFailure) {
        requireNonNull(trustExpressionParserFailure);

        return trustExpressionParserFailure.match(
                (trustInteroperabilityProfileList, uriString, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionParserFailure.TrustExpressionParserFailureURI.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("UriString", uriString);
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, uri, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionParserFailure.TrustExpressionParserFailureResolve.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("Uri", uri.toString());
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, expression, exception) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionParserFailure.TrustExpressionParserFailureParser.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("Expression", expression);
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustInteroperabilityProfileList, identifier) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put("$Type", TrustExpressionParserFailure.TrustExpressionParserFailureIdentifier.class.getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("Identifier", identifier);
                }}));
    }
}
