package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.function.F2;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P3;
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
    private static final JsonProducer<TrustExpressionParserFailure,    JSONObject> jsonProducerForTrustExpressionParserFailure    = jsonManager.findProducerStrict(TrustExpressionParserFailure.class,    JSONObject.class).some();
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

    private static JSONObject serializeTrustExpression(final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> trustExpression) {
        return trustExpression.match(
                TrustExpressionEvaluationJsonProducer::serializeTerminal,
                (operator, expression, data) -> operator.matchUnary(
                        not -> serializeTrustExpressionForNotHelper(expression, data)),
                (operator, left, right, data) -> operator.matchBinary(
                        and -> serializeTrustExpressionBinaryToJSONObject(left, right, data, TrustExpression.TrustExpressionAnd.class, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd),
                        or -> serializeTrustExpressionBinaryToJSONObject(left, right, data, TrustExpression.TrustExpressionOr.class, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr)));
    }

    private static Either<JSONObject, JSONArray> serializeTrustExpressionForAnd(final Option<TrustInteroperabilityProfile> trustInteroperabilityProfileParentOption, final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> trustExpression) {
        return trustExpression.match(
                terminal -> left(serializeTerminal(terminal)),
                (operator, expression, data) -> operator.matchUnary(
                        not -> left(serializeTrustExpressionForNotHelper(expression, data))),
                (operator, left, right, data) -> operator.matchBinary(
                        and -> trustInteroperabilityProfileParentOption
                                .bind(trustInteroperabilityProfileParent -> data._1().map(trustInteroperabilityProfile -> trustInteroperabilityProfileParent.getIdentifier().equals(trustInteroperabilityProfile.getIdentifier())))
                                .orSome(false) ?
                                right(serializeTrustExpressionBinaryToJSONArray(left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd)) :
                                left(serializeTrustExpressionBinaryToJSONObject(left, right, data, TrustExpression.TrustExpressionAnd.class, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd)),
                        or -> left(serializeTrustExpressionBinaryToJSONObject(left, right, data, TrustExpression.TrustExpressionOr.class, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr))));
    }

    private static Either<JSONObject, JSONArray> serializeTrustExpressionForOr(final Option<TrustInteroperabilityProfile> trustInteroperabilityProfileParentOption, final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> trustExpression) {
        return trustExpression.match(
                terminal -> left(serializeTerminal(terminal)),
                (operator, expression, data) -> operator.matchUnary(
                        not -> left(serializeTrustExpressionForNotHelper(expression, data))),
                (operator, left, right, data) -> operator.matchBinary(
                        and -> left(serializeTrustExpressionBinaryToJSONObject(left, right, data, TrustExpression.TrustExpressionAnd.class, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForAnd)),
                        or -> trustInteroperabilityProfileParentOption
                                .bind(trustInteroperabilityProfileParent -> data._1().map(trustInteroperabilityProfile -> trustInteroperabilityProfileParent.getIdentifier().equals(trustInteroperabilityProfile.getIdentifier())))
                                .orSome(false) ?
                                right(serializeTrustExpressionBinaryToJSONArray(left, right, data, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr)) :
                                left(serializeTrustExpressionBinaryToJSONObject(left, right, data, TrustExpression.TrustExpressionOr.class, TrustExpressionEvaluationJsonProducer::serializeTrustExpressionForOr))));
    }

    private static JSONObject serializeTrustExpressionForNotHelper(
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> expression,
            final P2<Option<TrustInteroperabilityProfile>, TrustExpressionState> data) {

        return new JSONObject(new HashMap<String, Object>() {{
            put("$Type", TrustExpression.TrustExpressionNot.class.getSimpleName());
            data._1().map(trustInteroperabilityProfile -> put("TrustInteroperabilityProfile", jsonProducerForTrustInteroperabilityProfile.serialize(trustInteroperabilityProfile)));
            put("TrustExpressionState", data._2().name());
            put("TrustExpression", serializeTrustExpression(expression));
        }});
    }

    private static JSONArray serializeTrustExpressionBinaryToJSONArray(
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> left,
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> right,
            final P2<Option<TrustInteroperabilityProfile>, TrustExpressionState> data,
            final F2<Option<TrustInteroperabilityProfile>, TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>>, Either<JSONObject, JSONArray>> f) {

        return reduce(f.f(data._1(), left).bimap(
                leftJSONObject -> reduce(f.f(data._1(), right).bimap(
                        rightJSONObject -> new JSONArray(arrayList(leftJSONObject).append(arrayList(rightJSONObject)).toCollection()),
                        rightJSONArray -> new JSONArray(arrayList((Object) leftJSONObject).append(iterableList(rightJSONArray.toList())).toCollection()))),
                leftJSONArray -> reduce(f.f(data._1(), right).bimap(
                        rightJSONObject -> new JSONArray(iterableList(leftJSONArray.toList()).append(arrayList(rightJSONObject)).toCollection()),
                        rightJSONArray -> new JSONArray(iterableList(leftJSONArray.toList()).append(iterableList(rightJSONArray.toList())).toCollection())))));
    }

    private static JSONObject serializeTrustExpressionBinaryToJSONObject(
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> left,
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> right,
            final P2<Option<TrustInteroperabilityProfile>, TrustExpressionState> data,
            final Class<?> clazz,
            final F2<Option<TrustInteroperabilityProfile>, TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>>, Either<JSONObject, JSONArray>> f) {

        return new JSONObject(new HashMap<String, Object>() {{
            put("$Type", clazz.getSimpleName());
            data._1().map(trustInteroperabilityProfile -> put("TrustInteroperabilityProfile", jsonProducerForTrustInteroperabilityProfile.serialize(trustInteroperabilityProfile)));
            put("TrustExpressionState", data._2().name());
            put("TrustExpression", reduce(f.f(data._1(), left).bimap(
                    leftJSONObject -> reduce(f.f(data._1(), right).bimap(
                            rightJSONObject -> new JSONArray(arrayList(leftJSONObject).append(arrayList(rightJSONObject)).toCollection()),
                            rightJSONArray -> new JSONArray(arrayList((Object) leftJSONObject).append(iterableList(rightJSONArray.toList())).toCollection()))),
                    leftJSONArray -> reduce(f.f(data._1(), right).bimap(
                            rightJSONObject -> new JSONArray(iterableList(leftJSONArray.toList()).append(arrayList(rightJSONObject)).toCollection()),
                            rightJSONArray -> new JSONArray(iterableList(leftJSONArray.toList()).append(iterableList(rightJSONArray.toList())).toCollection()))))));
        }});
    }

    private static JSONObject serializeTerminal(final P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>> terminal) {
        return new JSONObject(new HashMap<String, Object>() {{
            terminal._1()._1().map(trustInteroperabilityProfile -> put("TrustInteroperabilityProfile", jsonProducerForTrustInteroperabilityProfile.serialize(trustInteroperabilityProfile)));
            put("TrustExpressionState", terminal._1()._2().name());
            putAll(serializeTerminalInner(terminal._2()).toMap());
        }});
    }

    private static JSONObject serializeTerminalInner(final Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>> terminalInner) {
        return reduce(terminalInner.bimap(
                jsonProducerForTrustExpressionParserFailure::serialize,
                TrustExpressionEvaluationJsonProducer::serializeTerminalInnerSuccess
        ));
    }

    private static JSONObject serializeTerminalInnerSuccess(final P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>> p) {
        return new JSONObject(new HashMap<String, Object>() {{
            put("$Type", TrustExpression.TrustExpressionTerminal.class.getSimpleName());
            put("TrustInteroperabilityProfileList", new JSONArray(p._1().toList().map(jsonProducerForTrustInteroperabilityProfile::serialize).toCollection()));
            put("TrustmarkDefinitionRequirement", jsonProducerForTrustmarkDefinitionRequirement.serialize(p._2()));
            put("TrustmarkList", new JSONArray(p._3().map(jsonProducerForTrustmark::serialize).toCollection()));
        }});
    }
}
