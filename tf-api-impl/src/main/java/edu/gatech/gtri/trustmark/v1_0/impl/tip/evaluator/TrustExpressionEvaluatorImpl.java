package edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.impl.tip.TrustExpressionTypeUtility;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifier;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F0;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.impl.trust.TrustmarkUtility.satisfyingTrustmarkList;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureNonTerminalUnexpected;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureTrustmarkAbsent;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureTrustmarkVerifierFailure;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluation.trustExpressionEvaluation;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueNone;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueString;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueStringList;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.evaluatorFailureResolve;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.evaluatorFailureURI;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.lefts;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Either.rights;
import static org.gtri.fj.data.List.listEqual;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.fromList;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.iif;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.lang.StringUtility.stringEqual;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public class TrustExpressionEvaluatorImpl implements TrustExpressionEvaluator {

    private final Option<TrustmarkVerifier> trustmarkVerifierOption;
    private final TrustmarkResolver trustmarkResolver;
    private final TrustExpressionParser trustExpressionParser;

    public TrustExpressionEvaluatorImpl(
            final TrustmarkResolver trustmarkResolver,
            final TrustExpressionParser trustExpressionParser) {

        requireNonNull(trustmarkResolver);
        requireNonNull(trustExpressionParser);

        this.trustmarkResolver = trustmarkResolver;
        this.trustmarkVerifierOption = none();
        this.trustExpressionParser = trustExpressionParser;
    }

    public TrustExpressionEvaluatorImpl(
            final TrustmarkResolver trustmarkResolver,
            final TrustmarkVerifier trustmarkVerifier,
            final TrustExpressionParser trustExpressionParser) {

        requireNonNull(trustmarkResolver);
        requireNonNull(trustmarkVerifier);
        requireNonNull(trustExpressionParser);

        this.trustmarkResolver = trustmarkResolver;
        this.trustmarkVerifierOption = some(trustmarkVerifier);
        this.trustExpressionParser = trustExpressionParser;
    }

    @Override
    public TrustExpressionEvaluation evaluate(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionOuter,
            final List<Trustmark> trustmarkList) {

        requireNonNull(trustExpressionOuter);
        requireNonNull(trustmarkList);

        return trustExpressionEvaluation(nil(), evaluateHelper(trustExpressionOuter, trustmarkList));
    }

    @Override
    public TrustExpressionEvaluation evaluate(
            final String trustInteroperabilityProfileUriString,
            final List<String> trustmarkUriStringList) {

        requireNonNull(trustInteroperabilityProfileUriString);
        requireNonNull(trustmarkUriStringList);

        final P2<List<TrustExpressionEvaluatorFailure>, List<Trustmark>> resolve = resolve(trustmarkUriStringList);

        return trustExpressionEvaluation(resolve._1(), evaluateHelper(trustExpressionParser.parse(trustInteroperabilityProfileUriString), resolve._2()));
    }

    private P2<List<TrustExpressionEvaluatorFailure>, List<Trustmark>> resolve(
            final List<String> trustmarkUriStringList) {

        return trustmarkUriStringList
                .foldLeft(
                        p -> trustmarkUriString ->
                                reduce(resolve(trustmarkUriString).bimap(
                                        trustExpressionEvaluatorFailure -> p.map1(list -> list.snoc(trustExpressionEvaluatorFailure)),
                                        trustmark -> p.map2(list -> list.snoc(trustmark))
                                )),
                        p(nil(), nil()));
    }

    private Either<TrustExpressionEvaluatorFailure, Trustmark> resolve(
            final String trustmarkUriString) {

        return Try.<URI, RuntimeException>f(() -> URI.create(trustmarkUriString))._1().f().<TrustExpressionEvaluatorFailure>map(runtimeException -> evaluatorFailureURI(trustmarkUriString, runtimeException))
                .bind(trustmarkUri -> Try.<Trustmark, ResolveException>f(() -> trustmarkResolver.resolve(trustmarkUri))._1().f().map(resolveException -> evaluatorFailureResolve(trustmarkUri, resolveException)))
                .toEither();
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> evaluateHelper(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionOuter,
            final List<Trustmark> trustmarkList) {

        return trustExpressionOuter.match(
                trustExpressionParserData -> evaluateHelperTerminal(
                        trustmarkList,
                        trustExpressionParserData),
                (trustExpressionOperatorUnary, trustExpression, trustExpressionParserData) -> trustExpressionOperatorUnary.matchUnary(
                        trustExpressionOperatorNoop -> evaluateHelperUnary(
                                TrustExpression::noop,
                                TrustExpressionTypeUtility::mustSatisfyNoop,
                                evaluateHelper(trustExpression, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperUnaryNoop()),
                        trustExpressionOperatorNot -> evaluateHelperUnary(
                                TrustExpression::not,
                                TrustExpressionTypeUtility::mustSatisfyNot,
                                evaluateHelper(trustExpression, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperUnaryBoolean(value -> !value)),
                        trustExpressionOperatorExists -> evaluateHelperUnary(
                                TrustExpression::exists,
                                TrustExpressionTypeUtility::mustSatisfyExists,
                                evaluateHelper(trustExpression, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperUnaryNone(() -> false, () -> true))),
                (trustExpressionOperatorBinary, trustExpressionLeft, trustExpressionRight, trustExpressionParserData) -> trustExpressionOperatorBinary.matchBinary(
                        trustExpressionOperatorAnd -> evaluateHelperBinaryShortCircuit(
                                TrustExpression::and,
                                TrustExpressionTypeUtility::mustSatisfyAndForEvaluator,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryBooleanShortCircuit(
                                        value -> iif(value == false, false),
                                        (valueLeft, valueRight) -> valueLeft && valueRight)),
                        trustExpressionOperatorOr -> evaluateHelperBinaryShortCircuit(
                                TrustExpression::or,
                                TrustExpressionTypeUtility::mustSatisfyOrForEvaluator,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryBooleanShortCircuit(
                                        value -> iif(value == true, true),
                                        (valueLeft, valueRight) -> valueLeft || valueRight)),
                        trustExpressionOperatorLessThan -> evaluateHelperBinary(
                                TrustExpression::lessThan,
                                TrustExpressionTypeUtility::mustSatisfyLessThan,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryOrderable(
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) < 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) < 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) < 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) < 0)),
                        trustExpressionOperatorLessThanOrEqual -> evaluateHelperBinary(
                                TrustExpression::lessThanOrEqual,
                                TrustExpressionTypeUtility::mustSatisfyLessThanOrEqual,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryOrderable(
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) <= 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) <= 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) <= 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) <= 0)),
                        trustExpressionOperatorGreaterThanOrEqual -> evaluateHelperBinary(
                                TrustExpression::greaterThanOrEqual,
                                TrustExpressionTypeUtility::mustSatisfyGreaterThanOrEqual,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryOrderable(
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) >= 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) >= 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) >= 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) >= 0)),
                        trustExpressionOperatorGreaterThan -> evaluateHelperBinary(
                                TrustExpression::greaterThan,
                                TrustExpressionTypeUtility::mustSatisfyGreaterThan,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryOrderable(
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) > 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) > 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) > 0,
                                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) > 0)),
                        trustExpressionOperatorEqual -> evaluateHelperBinary(
                                TrustExpression::equal,
                                TrustExpressionTypeUtility::mustSatisfyEqual,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryEqual(
                                        (valueLeft, valueRight) -> valueLeft.equals(valueRight),
                                        (valueLeft, valueRight) -> valueLeft.equals(valueRight),
                                        (valueLeft, valueRight) -> valueLeft.equals(valueRight),
                                        (valueLeft, valueRight) -> valueLeft.equals(valueRight),
                                        (valueLeft, valueRight) -> listEqual(stringEqual).eq(valueLeft.sort(stringOrd), valueRight.sort(stringOrd)),
                                        () -> true)),
                        trustExpressionOperatorNotEqual -> evaluateHelperBinary(
                                TrustExpression::notEqual,
                                TrustExpressionTypeUtility::mustSatisfyNotEqual,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryEqual(
                                        (valueLeft, valueRight) -> !valueLeft.equals(valueRight),
                                        (valueLeft, valueRight) -> !valueLeft.equals(valueRight),
                                        (valueLeft, valueRight) -> !valueLeft.equals(valueRight),
                                        (valueLeft, valueRight) -> !valueLeft.equals(valueRight),
                                        (valueLeft, valueRight) -> listEqual(stringEqual).notEq(valueLeft.sort(stringOrd), valueRight.sort(stringOrd)),
                                        () -> false)),
                        trustExpressionOperatorContains -> evaluateHelperBinary(
                                TrustExpression::contains,
                                TrustExpressionTypeUtility::mustSatisfyContains,
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                trustExpressionParserData,
                                evaluateHelperBinaryStringListString(
                                        (valueLeft, valueRight) -> valueLeft.exists(valueLeftElement -> stringEqual.eq(valueLeftElement, valueRight))))));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> evaluateHelperUnary(
            final F2<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>> unary,
            final F2<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, NonEmptyList<TrustInteroperabilityProfile>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> mustSatisfy,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> trustExpressionParserDataValidation,
            final F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData> f) {

        return unary.f(
                trustExpression,
                trustExpressionParserDataValidation
                        .bind(trustExpressionParserData -> mustSatisfy.f(
                                        trustExpression,
                                        trustExpressionParserData.getTrustInteroperabilityProfileNonEmptyList())
                                .map(trustExpressionEvaluatorData -> f.f(trustExpressionParserData.getTrustInteroperabilityProfileNonEmptyList(), trustExpressionEvaluatorData))));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> evaluateHelperBinaryShortCircuit(
            final F3<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>> binary,
            final F3<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, NonEmptyList<TrustInteroperabilityProfile>, Validation<NonEmptyList<TrustExpressionFailure>, P2<TrustExpressionEvaluatorData, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>>> mustSatisfy,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpressionRight,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> trustExpressionParserDataValidation,
            final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> f) {

        return binary.f(
                trustExpressionLeft,
                trustExpressionRight,
                trustExpressionParserDataValidation
                        .bind(trustExpressionParserData -> mustSatisfy.f(
                                        trustExpressionLeft,
                                        trustExpressionRight,
                                        trustExpressionParserData.getTrustInteroperabilityProfileNonEmptyList())
                                .bind(p -> f.f(trustExpressionParserData.getTrustInteroperabilityProfileNonEmptyList(), p._1(), p._2()))));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> evaluateHelperBinary(
            final F3<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>> binary,
            final F3<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>, NonEmptyList<TrustInteroperabilityProfile>, Validation<NonEmptyList<TrustExpressionFailure>, P2<TrustExpressionEvaluatorData, TrustExpressionEvaluatorData>>> mustSatisfy,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpressionRight,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> trustExpressionParserDataValidation,
            final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData> f) {

        return binary.f(
                trustExpressionLeft,
                trustExpressionRight,
                trustExpressionParserDataValidation
                        .bind(trustExpressionParserData -> mustSatisfy.f(
                                        trustExpressionLeft,
                                        trustExpressionRight,
                                        trustExpressionParserData.getTrustInteroperabilityProfileNonEmptyList())
                                .map(p -> f.f(trustExpressionParserData.getTrustInteroperabilityProfileNonEmptyList(), p._1(), p._2()))));
    }

    private F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData> evaluateHelperUnaryNoop() {

        return (trustInteroperabilityNonEmptyList, trustExpressionEvaluatorData) -> trustExpressionEvaluatorData.matchValue(
                value -> dataValueBoolean(trustInteroperabilityNonEmptyList, value),
                value -> dataValueDateTimeStamp(trustInteroperabilityNonEmptyList, value),
                value -> dataValueDecimal(trustInteroperabilityNonEmptyList, value),
                value -> dataValueString(trustInteroperabilityNonEmptyList, value),
                value -> dataValueStringList(trustInteroperabilityNonEmptyList, value),
                () -> dataValueNone(trustInteroperabilityNonEmptyList));
    }

    private F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData> evaluateHelperUnaryNone(
            final F0<Boolean> f1,
            final F0<Boolean> f2) {

        return (trustInteroperabilityNonEmptyList, trustExpressionEvaluatorData) -> trustExpressionEvaluatorData.matchValueNone(
                () -> dataValueBoolean(trustInteroperabilityNonEmptyList, f1.f()),
                () -> dataValueBoolean(trustInteroperabilityNonEmptyList, f2.f()));
    }

    private F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData> evaluateHelperUnaryBoolean(
            final F1<Boolean, Boolean> f) {

        return (trustInteroperabilityNonEmptyList, trustExpressionEvaluatorData) -> trustExpressionEvaluatorData.matchValueBoolean(
                value -> dataValueBoolean(trustInteroperabilityNonEmptyList, f.f(value)),
                f0ThrowsRuntimeExceptionForUnexpectedBranch());
    }

    private F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> evaluateHelperBinaryBooleanShortCircuit(
            final F1<Boolean, Option<Boolean>> fShortCircuit,
            final F2<Boolean, Boolean, Boolean> f) {

        return (trustInteroperabilityNonEmptyList, trustExpressionEvaluatorDataLeft, trustExpressionEvaluatorDataRightValidation) ->
                trustExpressionEvaluatorDataLeft.matchValueBoolean(
                        valueLeft -> fShortCircuit.f(valueLeft)
                                .map(value -> Validation.<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>success(dataValueBoolean(trustInteroperabilityNonEmptyList, value)))
                                .orSome(trustExpressionEvaluatorDataRightValidation.map(trustExpressionEvaluatorDataRight -> trustExpressionEvaluatorDataRight.matchValueBoolean(
                                        valueRight -> dataValueBoolean(trustInteroperabilityNonEmptyList, f.f(valueLeft, valueRight)),
                                        f0ThrowsRuntimeExceptionForUnexpectedBranch()))),
                        f0ThrowsRuntimeExceptionForUnexpectedBranch());
    }

    private F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData> evaluateHelperBinaryStringListString(
            final F2<List<String>, String, Boolean> f) {

        return (trustInteroperabilityNonEmptyList, trustExpressionEvaluatorDataLeft, trustExpressionEvaluatorDataRight) -> trustExpressionEvaluatorDataLeft.matchValueStringList(
                valueLeft -> trustExpressionEvaluatorDataRight.matchValueString(
                        valueRight -> dataValueBoolean(trustInteroperabilityNonEmptyList, f.f(valueLeft, valueRight)),
                        f0ThrowsRuntimeExceptionForUnexpectedBranch()),
                f0ThrowsRuntimeExceptionForUnexpectedBranch());
    }

    private F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData> evaluateHelperBinaryOrderable(
            final F2<Boolean, Boolean, Boolean> f1,
            final F2<Instant, Instant, Boolean> f2,
            final F2<BigDecimal, BigDecimal, Boolean> f3,
            final F2<String, String, Boolean> f4) {

        return (trustInteroperabilityNonEmptyList, trustExpressionEvaluatorDataLeft, trustExpressionEvaluatorDataRight) -> trustExpressionEvaluatorDataLeft.matchValue(
                trustExpressionEvaluatorDataRight,
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f1.f(valueLeft, valueRight)),
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f2.f(valueLeft, valueRight)),
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f3.f(valueLeft, valueRight)),
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f4.f(valueLeft, valueRight)),
                f2ThrowsRuntimeExceptionForUnexpectedBranch(),
                f0ThrowsRuntimeExceptionForUnexpectedBranch(),
                f0ThrowsRuntimeExceptionForUnexpectedBranch());
    }

    private F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData, TrustExpressionEvaluatorData> evaluateHelperBinaryEqual(
            final F2<Boolean, Boolean, Boolean> f1,
            final F2<Instant, Instant, Boolean> f2,
            final F2<BigDecimal, BigDecimal, Boolean> f3,
            final F2<String, String, Boolean> f4,
            final F2<List<String>, List<String>, Boolean> f5,
            final F0<Boolean> f6) {

        return (trustInteroperabilityNonEmptyList, trustExpressionEvaluatorDataLeft, trustExpressionEvaluatorDataRight) -> trustExpressionEvaluatorDataLeft.matchValue(
                trustExpressionEvaluatorDataRight,
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f1.f(valueLeft, valueRight)),
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f2.f(valueLeft, valueRight)),
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f3.f(valueLeft, valueRight)),
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f4.f(valueLeft, valueRight)),
                (valueLeft, valueRight) -> dataValueBoolean(trustInteroperabilityNonEmptyList, f5.f(valueLeft, valueRight)),
                () -> dataValueBoolean(trustInteroperabilityNonEmptyList, f6.f()),
                f0ThrowsRuntimeExceptionForUnexpectedBranch());
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> evaluateHelperTerminal(
            final List<Trustmark> trustmarkList,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> data) {

        return terminal(data.bind(dataInner -> dataInner.match(
                dataLiteral -> dataLiteral.matchLiteral(
                        (trustInteroperabilityProfileNonEmptyList, trustExpressionType, dataLiteralBoolean) -> success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, dataLiteralBoolean)),
                        (trustInteroperabilityProfileNonEmptyList, trustExpressionType, dataLiteralDateTimeStamp) -> success(dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, dataLiteralDateTimeStamp)),
                        (trustInteroperabilityProfileNonEmptyList, trustExpressionType, dataLiteralDecimal) -> success(dataValueDecimal(trustInteroperabilityProfileNonEmptyList, dataLiteralDecimal)),
                        (trustInteroperabilityProfileNonEmptyList, trustExpressionType, dataLiteralString) -> success(dataValueString(trustInteroperabilityProfileNonEmptyList, dataLiteralString))),
                dataReference -> dataReference.matchReference(
                        (trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement) -> evaluateHelperTerminalHelper(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, satisfyingTrustmarkList(trustmarkDefinitionRequirement, trustmarkList)),
                        (trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement, trustmarkDefinitionParameter) -> evaluateHelperTerminalHelper(trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, satisfyingTrustmarkList(trustmarkDefinitionRequirement, trustmarkList))),
                dataNonTerminal -> fail(nel(failureNonTerminalUnexpected(dataInner.getTrustInteroperabilityProfileNonEmptyList()))))));
    }

    private Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> evaluateHelperTerminalHelper(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final List<Trustmark> trustmarkList) {

        return fromList(trustmarkList)
                .map(trustmarkNonEmptyList -> reduce(trustmarkVerifiedEither(trustmarkNonEmptyList).bimap(
                        nel -> Validation.<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, nel)),
                        trustmarkVerifiedNonEmptyList -> Validation.<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkVerifiedNonEmptyList.toList())))))
                .orSome(() -> success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, nil())));
    }

    private Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> evaluateHelperTerminalHelper(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustExpressionType trustExpressionType,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final List<Trustmark> trustmarkList) {

        return fromList(trustmarkList)
                .map(trustmarkNonEmptyList -> reduce(trustmarkVerifiedEither(trustmarkNonEmptyList).bimap(
                        nel -> Validation.<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>fail(nel(failureTrustmarkVerifierFailure(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, nel))),
                        trustmarkVerifiedNonEmptyList -> Validation.<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>success(trustExpressionType.match(
                                typeBoolean ->
                                        fromNull(trustmarkVerifiedNonEmptyList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                                .map(TrustmarkParameterBinding::getBooleanValue)
                                                .map(value -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList, value))
                                                .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList)),
                                typeDateTimeStamp ->
                                        fromNull(trustmarkVerifiedNonEmptyList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                                .map(TrustmarkParameterBinding::getDateTimeValue)
                                                .map(value -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList, value))
                                                .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList)),
                                typeDecimal ->
                                        fromNull(trustmarkVerifiedNonEmptyList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                                .map(TrustmarkParameterBinding::getNumericValue)
                                                .map(value -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList, value))
                                                .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList)),
                                typeString ->
                                        fromNull(trustmarkVerifiedNonEmptyList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                                .map(TrustmarkParameterBinding::getStringValue)
                                                .map(value -> dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList, value))
                                                .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList)),
                                typeStringList ->
                                        fromNull(trustmarkVerifiedNonEmptyList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                                .map(TrustmarkParameterBinding::getStringListValue)
                                                .map(List::iterableList)
                                                .map(value -> dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList, value))
                                                .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList)),
                                typeNone ->
                                        dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkVerifiedNonEmptyList))))))
                .orSome(() -> fail(nel(failureTrustmarkAbsent(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter))));
    }

    private Either<NonEmptyList<TrustmarkVerifierFailure>, NonEmptyList<Trustmark>> trustmarkVerifiedEither(
            final NonEmptyList<Trustmark> trustmarkNonEmptyList) {

        // verify the trustmarks: left failure, right success
        final NonEmptyList<Either<NonEmptyList<TrustmarkVerifierFailure>, Trustmark>> trustmarkEitherList = trustmarkVerifierOption.map(trustmarkVerifier -> trustmarkNonEmptyList.map(trustmark -> trustmarkVerifier.verify(trustmark).toEither())).orSome(trustmarkNonEmptyList.map(Either::right));

        // if the system verifies at least one trustmark, success; otherwise, failure.
        return fromList(rights(trustmarkEitherList.toList()))
                .map(Either::<NonEmptyList<TrustmarkVerifierFailure>, NonEmptyList<Trustmark>>right)
                .orSome(() -> fromList(lefts(trustmarkEitherList.toList()))
                        .map(NonEmptyList::join)
                        .map(nel -> Either.<NonEmptyList<TrustmarkVerifierFailure>, NonEmptyList<Trustmark>>left(nel))
                        .some());
    }

    private static <T1> F0<T1> f0ThrowsRuntimeExceptionForUnexpectedBranch() {
        return () -> {
            throw new RuntimeException("Unexpected branch.");
        };
    }

    private static <T1, T2, T3> F2<T1, T2, T3> f2ThrowsRuntimeExceptionForUnexpectedBranch() {
        return (t1, t2) -> {
            throw new RuntimeException("Unexpected branch.");
        };
    }
}
