package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import static java.util.Objects.requireNonNull;

@MetaInfServices
public class TrustExpressionEvaluatorFailureJsonProducer implements JsonProducer<TrustExpressionEvaluatorFailure, JSONObject> {

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);
    private static final JsonProducer<TrustInteroperabilityProfile, JSONObject> jsonProducerForTrustInteroperabilityProfile = jsonManager.findProducerStrict(TrustInteroperabilityProfile.class, JSONObject.class).some();

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
                    put("$Type", TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureURI.class.getSimpleName());
                    put("UriString", uriString);
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}),
                (uri, exception) -> new JSONObject(new java.util.HashMap<String, String>() {{
                    put("$Type", TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureResolve.class.getSimpleName());
                    put("Uri", uri.toString());
                    put("Exception", exception.getClass().getSimpleName());
                    put("Message", exception.getMessage());
                }}));
    }
}
