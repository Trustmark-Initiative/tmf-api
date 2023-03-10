package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorBinary;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorUnary;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorSource;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
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

import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.lang.StringUtility.stringOrd;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

@MetaInfServices
public class TrustExpressionEvaluationJsonProducer implements JsonProducer<TrustExpressionEvaluation, JSONObject> {

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);

    // @formatter:off
    private static final JsonProducer<TrustExpressionFailure,          JSONObject> jsonProducerForTrustExpressionFailure          = new TrustExpressionFailureJsonProducer();
    private static final JsonProducer<TrustExpressionEvaluatorFailure, JSONObject> jsonProducerForTrustExpressionEvaluatorFailure = new TrustExpressionEvaluatorFailureJsonProducer();
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
            put(ATTRIBUTE_KEY_JSON_TYPE, TrustExpressionEvaluation.class.getSimpleName());
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
            put(ATTRIBUTE_KEY_JSON_TYPE, trustExpressionOperatorBinary.getClass().getSimpleName());
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
            put(ATTRIBUTE_KEY_JSON_TYPE, trustExpressionOperatorUnary.getClass().getSimpleName());
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
                    put(ATTRIBUTE_KEY_JSON_TYPE, TrustExpressionFailure.class.getSimpleName());
                    put("TrustExpressionFailureList", new JSONArray(failure.map(trustExpressionFailure -> jsonProducerForTrustExpressionFailure.serialize(trustExpressionFailure)).toCollection()));
                }}),
                success -> new JSONObject(new HashMap<String, Object>() {{
                    put(ATTRIBUTE_KEY_JSON_TYPE, success.getClass().getSimpleName());
                    putAll(serializeDataToJSONObjectHelper(success).toMap());
                }})));
    }

    public static JSONObject serializeDataToJSONObjectHelper(
            final TrustExpressionEvaluatorData data) {

        return data.match(
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type.getClass().getSimpleName());
                    put("TrustExpressionEvaluatorDataValue", value);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type.getClass().getSimpleName());
                    put("TrustExpressionEvaluatorDataValue", value);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type.getClass().getSimpleName());
                    put("TrustExpressionEvaluatorDataValue", value);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type.getClass().getSimpleName());
                    put("TrustExpressionEvaluatorDataValue", value);
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type, value) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type.getClass().getSimpleName());
                    put("TrustExpressionEvaluatorDataValue", new JSONArray(value.sort(stringOrd).toCollection()));
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}),
                (source, type) -> new JSONObject(new HashMap<String, Object>() {{
                    put("TrustExpressionEvaluatorDataType", type.getClass().getSimpleName());
                    put("TrustExpressionEvaluatorDataSource", serializeSourceToJSONObject(source));
                }}));
    }

    private static JSONObject serializeSourceToJSONObject(
            final TrustExpressionEvaluatorSource source) {

        return source.match(
                trustInteroperabilityProfileNonEmptyList -> new JSONObject(new HashMap<String, Object>() {{
                    put(ATTRIBUTE_KEY_JSON_TYPE, source.getClass().getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileNonEmptyList.map(TrustExpressionEvaluationJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                }}),
                (trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList, trustmarkVerifierFailureList) -> new JSONObject(new HashMap<String, Object>() {{
                    put(ATTRIBUTE_KEY_JSON_TYPE, source.getClass().getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileNonEmptyList.map(TrustExpressionEvaluationJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustmarkDefinitionRequirement", serializeTrustmarkDefinitionRequirement(trustmarkDefinitionRequirement));
                    put("TrustmarkList", new JSONArray(trustmarkList.sort(ord((o1, o2) -> stringOrd.compare(o1.getIdentifier().toString(), o2.getIdentifier().toString()))).map(TrustExpressionEvaluationJsonProducer::serializeTrustmark).toCollection()));
                    put("TrustmarkVerifierFailureList", new JSONArray(trustmarkVerifierFailureList.map(TrustmarkVerifierFailure::messageFor).toCollection()));
                }}),
                (trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList) -> new JSONObject(new HashMap<String, Object>() {{
                    put(ATTRIBUTE_KEY_JSON_TYPE, source.getClass().getSimpleName());
                    put("TrustInteroperabilityProfileList", new JSONArray(trustInteroperabilityProfileNonEmptyList.map(TrustExpressionEvaluationJsonProducer::serializeTrustInteroperabilityProfile).toCollection()));
                    put("TrustmarkDefinitionRequirement", serializeTrustmarkDefinitionRequirement(trustmarkDefinitionRequirement));
                    put("TrustmarkDefinitionParameter", trustmarkDefinitionParameter.getIdentifier());
                    put("TrustmarkList", new JSONArray(trustmarkNonEmptyList.sort(ord((o1, o2) -> stringOrd.compare(o1.getIdentifier().toString(), o2.getIdentifier().toString()))).map(TrustExpressionEvaluationJsonProducer::serializeTrustmark).toCollection()));
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

    private static JSONObject serializeTrustmark(final Trustmark trustmark) {
        return new JSONObject(new HashMap<String, Object>() {{
            put("Identifier", trustmark.getIdentifier());
        }});
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
