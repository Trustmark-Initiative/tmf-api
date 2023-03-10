package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import static java.util.Objects.requireNonNull;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

@MetaInfServices
public class TrustExpressionEvaluatorFailureJsonProducer implements JsonProducer<TrustExpressionEvaluatorFailure, JSONObject> {

    @Override
    public Class<TrustExpressionEvaluatorFailure> getSupportedType() {
        return TrustExpressionEvaluatorFailure.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(final TrustExpressionEvaluatorFailure trustExpressionEvaluatorFailure) {
        requireNonNull(trustExpressionEvaluatorFailure);

        return trustExpressionEvaluatorFailure.match(
                (uriString, exception) -> new JSONObject(new java.util.HashMap<String, String>() {{
                    put(ATTRIBUTE_KEY_JSON_TYPE, TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureURI.class.getSimpleName());
                    put("UriString", uriString);
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (uri, exception) -> new JSONObject(new java.util.HashMap<String, String>() {{
                    put(ATTRIBUTE_KEY_JSON_TYPE, TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureResolve.class.getSimpleName());
                    put("Uri", uri.toString());
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (trustmark, trustmarkVerificationFailureNonEmptyList) -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put(ATTRIBUTE_KEY_JSON_TYPE, TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureVerify.class.getSimpleName());
                    put("Uri", trustmark.getIdentifier().toString());
                    put("Message", new JSONArray(trustmarkVerificationFailureNonEmptyList.map(TrustmarkVerifierFailure::messageFor).toCollection()));
                }}));
    }
}
