package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorBinary;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorUnary;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorSource;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import java.util.HashMap;

import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.iterableList;

@MetaInfServices
public class TrustExpressionEvaluationJsonProducer implements JsonProducer<TrustExpressionEvaluation, JSONObject> {

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);

    // @formatter:off
    private static final JsonProducer<TrustInteroperabilityProfile,    JSONObject> jsonProducerForTrustInteroperabilityProfile    = jsonManager.findProducerStrict(TrustInteroperabilityProfile.class,    JSONObject.class).some();
    private static final JsonProducer<Trustmark,                       JSONObject> jsonProducerForTrustmark                       = jsonManager.findProducerStrict(Trustmark.class,                       JSONObject.class).some();
    private static final JsonProducer<TrustExpressionFailure,          JSONObject> jsonProducerForTrustExpressionFailure          = jsonManager.findProducerStrict(TrustExpressionFailure.class,          JSONObject.class).some();
    private static final JsonProducer<TrustExpressionEvaluatorFailure, JSONObject> jsonProducerForTrustExpressionEvaluatorFailure = jsonManager.findProducerStrict(TrustExpressionEvaluatorFailure.class, JSONObject.class).some();
    private static final JsonProducer<TrustmarkDefinitionRequirement,  JSONObject> jsonProducerForTrustmarkDefinitionRequirement  = jsonManager.findProducerStrict(TrustmarkDefinitionRequirement.class,  JSONObject.class).some();
    // @formatter:on

    @Override
    public Class<TrustExpressionEvaluation> getSupportedType() {
        return TrustExpressionEvaluation.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(final TrustExpressionEvaluation trustExpressionEvaluation) {
        return new JSONObject(new HashMap<String, Object>() {{
            put("$Type", TrustExpressionEvaluation.class.getSimpleName());
            put("TrustExpressionEvaluatorFailureList", new JSONArray(trustExpressionEvaluation.getTrustExpressionEvaluatorFailureList().map(jsonProducerForTrustExpressionEvaluatorFailure::serialize).toCollection()));
            put("TrustExpression", serializeTrustExpression(trustExpressionEvaluation.getTrustExpression()));
        }});
    }

    private static JSONObject serializeTrustExpression(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression) {

        return trustExpression.match(
                TrustExpressionEvaluationJsonProducer::serializeDataToJSONObject,
                TrustExpressionEvaluationJsonProducer::serializeTrustExpressionUnaryToJSONObject,
                (operator, left, right, data) -> operator.matchBinary(
                        and -> serializeTrustExpressionBinaryToJSONObject(and, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd),
                        or -> serializeTrustExpressionBinaryToJSONObject(or, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr),
                        lessThan -> serializeTrustExpressionBinaryToJSONObject(lessThan, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther),
                        lessThanOrEqual -> serializeTrustExpressionBinaryToJSONObject(lessThanOrEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther),
                        greaterThanOrEqual -> serializeTrustExpressionBinaryToJSONObject(greaterThanOrEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther),
                        greaterThan -> serializeTrustExpressionBinaryToJSONObject(greaterThan, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther),
                        equal -> serializeTrustExpressionBinaryToJSONObject(equal, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther),
                        notEqual -> serializeTrustExpressionBinaryToJSONObject(notEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther),
                        contains -> serializeTrustExpressionBinaryToJSONObject(contains, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)
                ));
    }

    private static Either<JSONObject, JSONArray> serializeTrustExpressionForAnd(
            final Option<TrustInteroperabilityProfile> trustInteroperabilityProfileParentOption,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression) {

        return trustExpression.match(
                TrustExpressionEvaluationJsonProducer::seralizeData,
                TrustExpressionEvaluationJsonProducer::serializeTrustExpressionUnary,
                (operator, left, right, data) -> operator.matchBinary(
                        and -> trustInteroperabilityProfileParentOption
                                .bind(trustInteroperabilityProfileParent -> trustInteroperabilityProfileOption(data).map(trustInteroperabilityProfile -> trustInteroperabilityProfileParent.getIdentifier().equals(trustInteroperabilityProfile.getIdentifier())))
                                .orSome(false) ?
                                right(serializeTrustExpressionBinaryToJSONArray(left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd)) :
                                left(serializeTrustExpressionBinaryToJSONObject(and, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd)),
                        or -> left(serializeTrustExpressionBinaryToJSONObject(or, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr)),
                        lessThan -> left(serializeTrustExpressionBinaryToJSONObject(lessThan, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        lessThanOrEqual -> left(serializeTrustExpressionBinaryToJSONObject(lessThanOrEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        greaterThanOrEqual -> left(serializeTrustExpressionBinaryToJSONObject(greaterThanOrEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        greaterThan -> left(serializeTrustExpressionBinaryToJSONObject(greaterThan, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        equal -> left(serializeTrustExpressionBinaryToJSONObject(equal, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        notEqual -> left(serializeTrustExpressionBinaryToJSONObject(notEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        contains -> left(serializeTrustExpressionBinaryToJSONObject(contains, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther))
                ));
    }

    private static Either<JSONObject, JSONArray> serializeTrustExpressionForOr(
            final Option<TrustInteroperabilityProfile> trustInteroperabilityProfileParentOption,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression) {

        return trustExpression.match(
                TrustExpressionEvaluationJsonProducer::seralizeData,
                TrustExpressionEvaluationJsonProducer::serializeTrustExpressionUnary,
                (operator, left, right, data) -> operator.matchBinary(
                        and -> left(serializeTrustExpressionBinaryToJSONObject(and, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd)),
                        or -> trustInteroperabilityProfileParentOption
                                .bind(trustInteroperabilityProfileParent -> trustInteroperabilityProfileOption(data).map(trustInteroperabilityProfile -> trustInteroperabilityProfileParent.getIdentifier().equals(trustInteroperabilityProfile.getIdentifier())))
                                .orSome(false) ?
                                right(serializeTrustExpressionBinaryToJSONArray(left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr)) :
                                left(serializeTrustExpressionBinaryToJSONObject(or, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr)),
                        lessThan -> left(serializeTrustExpressionBinaryToJSONObject(lessThan, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        lessThanOrEqual -> left(serializeTrustExpressionBinaryToJSONObject(lessThanOrEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        greaterThanOrEqual -> left(serializeTrustExpressionBinaryToJSONObject(greaterThanOrEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        greaterThan -> left(serializeTrustExpressionBinaryToJSONObject(greaterThan, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        equal -> left(serializeTrustExpressionBinaryToJSONObject(equal, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        notEqual -> left(serializeTrustExpressionBinaryToJSONObject(notEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        contains -> left(serializeTrustExpressionBinaryToJSONObject(contains, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther))
                ));
    }

    private static Either<JSONObject, JSONArray> serializeTrustExpressionForOther(
            final Option<TrustInteroperabilityProfile> trustInteroperabilityProfileParentOption,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression) {

        return trustExpression.match(
                TrustExpressionEvaluationJsonProducer::seralizeData,
                TrustExpressionEvaluationJsonProducer::serializeTrustExpressionUnary,
                (operator, left, right, data) -> operator.matchBinary(
                        and -> left(serializeTrustExpressionBinaryToJSONObject(and, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd)),
                        or -> left(serializeTrustExpressionBinaryToJSONObject(or, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr)),
                        lessThan -> left(serializeTrustExpressionBinaryToJSONObject(lessThan, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        lessThanOrEqual -> left(serializeTrustExpressionBinaryToJSONObject(lessThanOrEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        greaterThanOrEqual -> left(serializeTrustExpressionBinaryToJSONObject(greaterThanOrEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        greaterThan -> left(serializeTrustExpressionBinaryToJSONObject(greaterThan, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        equal -> left(serializeTrustExpressionBinaryToJSONObject(equal, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        notEqual -> left(serializeTrustExpressionBinaryToJSONObject(notEqual, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther)),
                        contains -> left(serializeTrustExpressionBinaryToJSONObject(contains, left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOther))
                ));
    }

    private static JSONObject serializeTrustExpressionBinaryToJSONObject(
            final TrustExpressionOperatorBinary trustExpressionOperatorBinary,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> data,
            final F2<Option<TrustInteroperabilityProfile>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, Either<JSONObject, JSONArray>> serialize) {

        return new JSONObject(new HashMap<String, Object>() {{
            put("$Type", trustExpressionOperatorBinary.getClass().getSimpleName());
            put("TrustExpression", serializeTrustExpressionBinaryToJSONArray(left, right, data, serialize));
            put("TrustExpressionData", serializeDataToJSONObject(data));
        }});
    }

    private static JSONArray serializeTrustExpressionBinaryToJSONArray(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> data,
            final F2<Option<TrustInteroperabilityProfile>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, Either<JSONObject, JSONArray>> serialize) {

        return reduce(serialize.f(trustInteroperabilityProfileOption(data), left).bimap(
                leftJSONObject -> reduce(serialize.f(trustInteroperabilityProfileOption(data), right).bimap(
                        rightJSONObject -> new JSONArray(arrayList(leftJSONObject).append(arrayList(rightJSONObject)).toCollection()),
                        rightJSONArray -> new JSONArray(arrayList((Object) leftJSONObject).append(iterableList(rightJSONArray.toList())).toCollection()))),
                leftJSONArray -> reduce(serialize.f(trustInteroperabilityProfileOption(data), right).bimap(
                        rightJSONObject -> new JSONArray(iterableList(leftJSONArray.toList()).append(arrayList(rightJSONObject)).toCollection()),
                        rightJSONArray -> new JSONArray(iterableList(leftJSONArray.toList()).append(iterableList(rightJSONArray.toList())).toCollection())))));
    }

    private static Either<JSONObject, JSONArray> serializeTrustExpressionUnary(
            final TrustExpressionOperatorUnary operator,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> expression,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> data) {

        return left(serializeTrustExpressionUnaryToJSONObject(operator, expression, data));
    }

    private static JSONObject serializeTrustExpressionUnaryToJSONObject(
            final TrustExpressionOperatorUnary trustExpressionOperatorUnary,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> expression,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> data) {

        return new JSONObject(new HashMap<String, Object>() {{
            put("$Type", trustExpressionOperatorUnary.getClass().getSimpleName());
            put("TrustExpression", serializeTrustExpression(expression));
            put("TrustExpressionData", serializeDataToJSONObject(data));
        }});
    }

    private static Either<JSONObject, JSONArray> seralizeData(
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> data) {

        return left(serializeDataToJSONObject(data));
    }

    private static JSONObject serializeDataToJSONObject(
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> data) {

        return reduce(data.toEither().bimap(
                failure -> new JSONObject(new HashMap<String, Object>() {{
                    put("$Type", TrustExpressionFailure.class.getSimpleName());
                    put("TrustExpressionFailureList", new JSONArray(failure.map(trustExpressionFailure -> jsonProducerForTrustExpressionFailure.serialize(trustExpressionFailure)).toCollection()));
                }}),
                success -> new JSONObject(new HashMap<String, Object>() {{
                    put("$Type", success.getClass().getSimpleName());
                    putAll(serializeDataToJSONObjectHelper(success).toMap());
                }})));
    }

    public static JSONObject serializeDataToJSONObjectHelper(
            final TrustExpressionEvaluatorData data) {

        return data.match(
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type);
                    put("TrustExpressionEvaluatorDataValue", value);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type);
                    put("TrustExpressionEvaluatorDataValue", value);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type);
                    put("TrustExpressionEvaluatorDataValue", value);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type);
                    put("TrustExpressionEvaluatorDataValue", value);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type);
                    put("TrustExpressionEvaluatorDataValue", new JSONArray(value.toCollection()));
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}));
    }

    private static JSONObject serializeSourceToJSONObject(
            final TrustExpressionEvaluatorSource source) {

        return source.match(
                trustInteroperabilityProfileNonEmptyList -> new JSONObject(new HashMap<String, Object>() {{
                    put("$Type", source.getClass().getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileNonEmptyList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                }}),
                (trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList) -> new JSONObject(new HashMap<String, Object>() {{
                    put("$Type", source.getClass().getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileNonEmptyList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustmarkDefinitionRequirement", jsonProducerForTrustmarkDefinitionRequirement.serialize(trustmarkDefinitionRequirement));
                    put("TrustmarkList", new JSONArray(trustmarkList.map(trustmark -> jsonProducerForTrustmark.serialize(trustmark)).toCollection()));
                }}),
                (trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList) -> new JSONObject(new HashMap<String, Object>() {{
                    put("$Type", source.getClass().getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileNonEmptyList.map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
                    put("TrustmarkDefinitionRequirement", jsonProducerForTrustmarkDefinitionRequirement.serialize(trustmarkDefinitionRequirement));
                    put("TrustmarkDefinitionParameter", trustmarkDefinitionParameter.getIdentifier());
                    put("TrustmarkList", new JSONArray(trustmarkNonEmptyList.map(trustmark -> jsonProducerForTrustmark.serialize(trustmark)).toCollection()));
                }}));
    }

    private static Option<TrustInteroperabilityProfile> trustInteroperabilityProfileOption(
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> data) {

        return trustInteroperabilityProfileList(data).headOption();
    }

    private static List<TrustInteroperabilityProfile> trustInteroperabilityProfileList(
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> data) {

        return reduce(data.toEither().bimap(
                failure -> failure.head().getTrustInteroperabilityProfileList(),
                success -> success.getSource().getTrustInteroperabilityProfileNonEmptyList().toList()));
    }

}
