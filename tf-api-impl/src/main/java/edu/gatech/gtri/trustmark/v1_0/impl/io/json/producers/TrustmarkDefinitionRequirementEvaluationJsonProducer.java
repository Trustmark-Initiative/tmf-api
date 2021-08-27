package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustmarkDefinitionRequirementEvaluation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import java.util.HashMap;

import static org.gtri.fj.data.Either.reduce;

@MetaInfServices
public class TrustmarkDefinitionRequirementEvaluationJsonProducer implements JsonProducer<TrustmarkDefinitionRequirementEvaluation, JSONObject> {

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);

    // @formatter:off
    private static final JsonProducer<TrustInteroperabilityProfile,    JSONObject> jsonProducerForTrustInteroperabilityProfile    = jsonManager.findProducerStrict(TrustInteroperabilityProfile.class,    JSONObject.class).some();
    private static final JsonProducer<Trustmark,                       JSONObject> jsonProducerForTrustmark                       = jsonManager.findProducerStrict(Trustmark.class,                       JSONObject.class).some();
    private static final JsonProducer<TrustExpressionFailure,          JSONObject> jsonProducerForTrustExpressionFailure          = jsonManager.findProducerStrict(TrustExpressionFailure.class,          JSONObject.class).some();
    private static final JsonProducer<TrustExpressionEvaluatorFailure, JSONObject> jsonProducerForTrustExpressionEvaluatorFailure = jsonManager.findProducerStrict(TrustExpressionEvaluatorFailure.class, JSONObject.class).some();
    private static final JsonProducer<TrustmarkDefinitionRequirement,  JSONObject> jsonProducerForTrustmarkDefinitionRequirement  = jsonManager.findProducerStrict(TrustmarkDefinitionRequirement.class,  JSONObject.class).some();
    // @formatter:on

    private static final Logger log = LogManager.getLogger(TrustmarkDefinitionRequirementEvaluation.class);

    @Override
    public Class<TrustmarkDefinitionRequirementEvaluation> getSupportedType() {
        return TrustmarkDefinitionRequirementEvaluation.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(final TrustmarkDefinitionRequirementEvaluation trustmarkDefinitionRequirementEvaluation) {
        return new JSONObject(new HashMap<String, Object>() {{
            put("$Type", TrustmarkDefinitionRequirementEvaluation.class.getSimpleName());
            put("TrustExpressionEvaluatorFailureList", new JSONArray(trustmarkDefinitionRequirementEvaluation.getTrustExpressionEvaluatorFailureList().map(jsonProducerForTrustExpressionEvaluatorFailure::serialize).toCollection()));
            put("TrustmarkDefinitionRequirementSatisfactionListValidation", serialize(trustmarkDefinitionRequirementEvaluation.getTrustmarkDefinitionRequirementSatisfaction()));
        }});
    }

    public JSONObject serialize(final Validation<NonEmptyList<TrustExpressionFailure>, List<P2<TrustmarkDefinitionRequirement, List<Trustmark>>>> trustmarkDefinitionRequirementSatisfaction) {

        return reduce(trustmarkDefinitionRequirementSatisfaction.toEither().bimap(
                failure -> new JSONObject(new HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailure.class.getSimpleName());
                    put("TrustExpressionFailureList", new JSONArray(failure.map(trustExpressionFailure -> jsonProducerForTrustExpressionFailure.serialize(trustExpressionFailure)).toCollection()));
                }}),
                success -> new JSONObject(new HashMap<String, Object>() {{
                    put("$Type", success.getClass().getSimpleName());
                    put("TrustmarkDefinitionRequirementSatisfactionList", serialize(success));
                }})));
    }

    public JSONArray serialize(final List<P2<TrustmarkDefinitionRequirement, List<Trustmark>>> trustmarkDefinitionRequirementSatisfactionList) {

        return new JSONArray(trustmarkDefinitionRequirementSatisfactionList.map(p -> new JSONObject(new HashMap<String, Object>() {{
            put("$Type", p.getClass().getSimpleName());
            put("TrustmarkDefinitionRequirement", jsonProducerForTrustmarkDefinitionRequirement.serialize(p._1()));
            put("TrustmarkList", p._2().map(trustmark -> jsonProducerForTrustmark.serialize(trustmark)));
        }})).toCollection());
    }
}
