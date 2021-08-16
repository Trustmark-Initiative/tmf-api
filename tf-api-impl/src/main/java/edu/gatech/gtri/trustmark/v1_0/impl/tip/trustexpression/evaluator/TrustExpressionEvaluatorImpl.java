package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F0;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.impl.tip.TrustmarkUtility.satisfyingTrustmarkList;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.contains;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.equal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.exists;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.greaterThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.greaterThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.lessThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.lessThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.notEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureExpression;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureExpressionLeft;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureExpressionRight;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureNonTerminalUnexpected;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureTrustmarkAbsent;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType.TYPE_BOOLEAN;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType.TYPE_STRING;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType.TYPE_STRING_LIST;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluation.trustExpressionEvaluation;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueNone;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueString;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueStringList;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure.evaluatorFailureResolve;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure.evaluatorFailureURI;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.listEqual;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.lang.StringUtility.stringEqual;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public class TrustExpressionEvaluatorImpl implements TrustExpressionEvaluator {

    private final TrustmarkResolver trustmarkResolver;
    private final TrustExpressionParser trustExpressionParser;

    public TrustExpressionEvaluatorImpl(
            final TrustmarkResolver trustmarkResolver,
            final TrustExpressionParser trustExpressionParser) {

        requireNonNull(trustmarkResolver);
        requireNonNull(trustExpressionParser);

        this.trustmarkResolver = trustmarkResolver;
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

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> evaluateHelper(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionOuter,
            final List<Trustmark> trustmarkList) {

        return trustExpressionOuter.match(
                data -> stateTerminal(data, trustmarkList),
                (trustExpressionOperatorUnary, trustExpression, data) -> trustExpressionOperatorUnary.matchUnary(
                        trustExpressionOperatorNot -> stateNot(
                                evaluateHelper(trustExpression, trustmarkList),
                                data),
                        trustExpressionOperatorExists -> stateExists(
                                evaluateHelper(trustExpression, trustmarkList),
                                data)),
                (trustExpressionOperatorBinary, trustExpressionLeft, trustExpressionRight, data) -> trustExpressionOperatorBinary.matchBinary(
                        trustExpressionOperatorAnd -> stateAnd(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data),
                        trustExpressionOperatorOr -> stateOr(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data),
                        trustExpressionOperatorLessThan -> stateLessThan(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data),
                        trustExpressionOperatorLessThanOrEqual -> stateLessThanOrEqual(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data),
                        trustExpressionOperatorGreaterThanOrEqual -> stateGreaterThanOrEqual(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data),
                        trustExpressionOperatorGreaterThan -> stateGreaterThan(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data),
                        trustExpressionOperatorEqual -> stateEqual(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data),
                        trustExpressionOperatorNotEqual -> stateNotEqual(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data),
                        trustExpressionOperatorContains -> stateContains(
                                evaluateHelper(trustExpressionLeft, trustmarkList),
                                evaluateHelper(trustExpressionRight, trustmarkList),
                                data)));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateTerminal(
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> data,
            final List<Trustmark> trustmarkList) {

        return terminal(data.bind(dataInner -> dataInner.match(
                dataLiteral -> dataLiteral.matchLiteral(
                        (trustInteroperabilityProfileNonEmptyList, dataLiteralBoolean) -> success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, dataLiteralBoolean)),
                        (trustInteroperabilityProfileNonEmptyList, dataLiteralDateTimeStamp) -> success(dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, dataLiteralDateTimeStamp)),
                        (trustInteroperabilityProfileNonEmptyList, dataLiteralDecimal) -> success(dataValueDecimal(trustInteroperabilityProfileNonEmptyList, dataLiteralDecimal)),
                        (trustInteroperabilityProfileNonEmptyList, dataLiteralString) -> success(dataValueString(trustInteroperabilityProfileNonEmptyList, dataLiteralString))),
                dataReference -> dataReference.matchReference(
                        (trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement) -> success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, satisfyingTrustmarkList(trustmarkDefinitionRequirement, trustmarkList))),
                        (trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter) -> stateTerminalHelper(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, satisfyingTrustmarkList(trustmarkDefinitionRequirement, trustmarkList))),
                dataNonTerminal -> fail(nel(failureNonTerminalUnexpected(dataInner.getTrustInteroperabilityProfileNonEmptyList()))))));
    }

    private static Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> stateTerminalHelper(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final List<Trustmark> trustmarkList) {

        return trustmarkList.isEmpty() ?
                fail(nel(failureTrustmarkAbsent(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter))) :
                success(trustmarkDefinitionParameter.getParameterKind().match(
                        parameterKindBoolean ->
                                fromNull(trustmarkList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                        .map(TrustmarkParameterBinding::getBooleanValue)
                                        .map(value -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()), value))
                                        .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()))),
                        parameterKindDatetime ->
                                fromNull(trustmarkList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                        .map(TrustmarkParameterBinding::getDateTimeValue)
                                        .map(value -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()), value))
                                        .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()))),
                        parameterKindEnum ->
                                fromNull(trustmarkList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                        .map(TrustmarkParameterBinding::getStringValue)
                                        .map(value -> dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()), value))
                                        .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()))),
                        parameterKindEnumMulti ->
                                fromNull(trustmarkList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                        .map(TrustmarkParameterBinding::getStringListValue)
                                        .map(List::iterableList)
                                        .map(value -> dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()), value))
                                        .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()))),
                        parameterKindNumber ->
                                fromNull(trustmarkList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                        .map(TrustmarkParameterBinding::getNumericValue)
                                        .map(value -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()), value))
                                        .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()))),
                        parameterKindString ->
                                fromNull(trustmarkList.head().getParameterBinding(trustmarkDefinitionParameter.getIdentifier()))
                                        .map(TrustmarkParameterBinding::getStringValue)
                                        .map(value -> dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()), value))
                                        .orSome(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, nel(trustmarkList.head(), trustmarkList.tail()))),
                        parameterKindOther -> {
                            throw new RuntimeException("ParameterKind must be one of BOOLEAN, DATETIME, ENUM, ENUM_MULTI, NUMBER, and STRING.");
                        }));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateNot(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> expression,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return not(
                expression,
                parserDataValidation.bind(parserData -> expression.getData()
                        .f().map(nel -> nel(failureExpression(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel)))
                        .bind(evaluatorData -> evaluatorData.matchValueBoolean(
                                success1(parserData, value -> !value),
                                failureTypeUnexpected0(parserData, TYPE_BOOLEAN, evaluatorData.getType())))));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateExists(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> expression,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return exists(
                expression,
                parserDataValidation.bind(parserData -> expression.getData()
                        .f().map(nel -> nel(failureExpression(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel)))
                        .bind(evaluatorData -> evaluatorData.matchValueNone(
                                success0(parserData, () -> false),
                                success0(parserData, () -> true)))));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateContains(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return contains(
                left,
                right,
                parserDataValidation.bind(parserData -> accumulate(
                        left.getData()
                                .f().map(nel -> nel(failureExpressionLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel)))
                                .bind(data -> data.matchValueStringList(
                                        value -> success(value),
                                        () -> fail(nel(TrustExpressionFailure.failureTypeUnexpectedLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), TYPE_STRING_LIST, data.getType()))))),
                        right.getData()
                                .f().map(nel -> nel(failureExpressionRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel)))
                                .bind(data -> data.matchValueString(
                                        value -> success(value),
                                        () -> fail(nel(TrustExpressionFailure.failureTypeUnexpectedRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), TYPE_STRING, data.getType()))))),
                        (valueLeft, valueRight) -> dataValueBoolean(parserData.getTrustInteroperabilityProfileNonEmptyList(), valueLeft.exists(valueLeftElement -> stringEqual.eq(valueLeftElement, valueRight))))));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateAnd(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return and(
                left,
                right,
                parserDataValidation.bind(parserData -> accumulate(
                        left.getData()
                                .f().map(nel -> nel(failureExpressionLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel)))
                                .bind(data -> data.matchValueBoolean(
                                        value -> success(value),
                                        () -> fail(nel(TrustExpressionFailure.failureTypeUnexpectedLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), TYPE_BOOLEAN, data.getType()))))),
                        right.getData()
                                .f().map(nel -> nel(failureExpressionRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel)))
                                .bind(data -> data.matchValueBoolean(
                                        value -> success(value),
                                        () -> fail(nel(TrustExpressionFailure.failureTypeUnexpectedRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), TYPE_BOOLEAN, data.getType()))))),
                        (valueLeft, valueRight) -> dataValueBoolean(parserData.getTrustInteroperabilityProfileNonEmptyList(), valueLeft && valueRight))));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateOr(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return or(
                left,
                right,
                parserDataValidation.bind(parserData -> {

                    final Validation<NonEmptyList<TrustExpressionFailure>, Boolean> valueLeftOuter = left.getData()
                            .f().map(nel -> nel(failureExpressionLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel)))
                            .bind(data -> data.matchValueBoolean(
                                    value -> success(value),
                                    () -> fail(nel(TrustExpressionFailure.failureTypeUnexpectedLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), TYPE_BOOLEAN, data.getType())))));

                    final Validation<NonEmptyList<TrustExpressionFailure>, Boolean> valueRightOuter = right.getData()
                            .f().map(nel -> nel(failureExpressionRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel)))
                            .bind(data -> data.matchValueBoolean(
                                    value -> success(value),
                                    () -> fail(nel(TrustExpressionFailure.failureTypeUnexpectedRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), TYPE_BOOLEAN, data.getType())))));

                    return reduce(valueLeftOuter.toEither().bimap(
                            failureLeft -> reduce(valueRightOuter.toEither().bimap(
                                    failureRight -> fail(failureLeft.append(failureRight.toList())),
                                    successRight -> success(dataValueBoolean(parserData.getTrustInteroperabilityProfileNonEmptyList(), successRight))
                            )),
                            successLeft -> reduce(valueRightOuter.toEither().bimap(
                                    failureRight -> success(dataValueBoolean(parserData.getTrustInteroperabilityProfileNonEmptyList(), successLeft)),
                                    successRight -> success(dataValueBoolean(parserData.getTrustInteroperabilityProfileNonEmptyList(), successLeft || successRight))
                            ))));
                }));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateLessThan(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return lessThan(
                left,
                right,
                parserDataValidation.bind(parserData -> ordHelper(
                        left,
                        right,
                        parserData,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) < 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) < 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) < 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) < 0)));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateLessThanOrEqual(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return lessThanOrEqual(
                left,
                right,
                parserDataValidation.bind(parserData -> ordHelper(
                        left,
                        right,
                        parserData,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) <= 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) <= 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) <= 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) <= 0)));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateGreaterThanOrEqual(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return greaterThanOrEqual(
                left,
                right,
                parserDataValidation.bind(parserData -> ordHelper(
                        left,
                        right,
                        parserData,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) >= 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) >= 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) >= 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) >= 0)));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateGreaterThan(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return greaterThan(
                left,
                right,
                parserDataValidation.bind(parserData -> ordHelper(
                        left,
                        right,
                        parserData,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) > 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) > 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) > 0,
                        (valueLeft, valueRight) -> valueLeft.compareTo(valueRight) > 0)));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateEqual(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return equal(
                left,
                right,
                parserDataValidation.bind(parserData -> eqHelper(
                        left,
                        right,
                        parserData,
                        Boolean::equals,
                        Instant::equals,
                        BigDecimal::equals,
                        String::equals,
                        (valueLeft, valueRight) -> listEqual(stringEqual).eq(valueLeft.sort(stringOrd), valueRight.sort(stringOrd)),
                        () -> true)));
    }

    private static TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> stateNotEqual(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData> parserDataValidation) {

        return notEqual(
                left,
                right,
                parserDataValidation.bind(parserData -> eqHelper(
                        left,
                        right,
                        parserData,
                        (valueLeft, valueRight) -> !valueLeft.equals(valueRight),
                        (valueLeft, valueRight) -> !valueLeft.equals(valueRight),
                        (valueLeft, valueRight) -> !valueLeft.equals(valueRight),
                        (valueLeft, valueRight) -> !valueLeft.equals(valueRight),
                        (valueLeft, valueRight) -> listEqual(stringEqual).notEq(valueLeft.sort(stringOrd), valueRight.sort(stringOrd)),
                        () -> false)));
    }

    private static Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> eqHelper(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final TrustExpressionParserData parserData,
            final F2<Boolean, Boolean, Boolean> fBoolean,
            final F2<Instant, Instant, Boolean> fDateTimeStamp,
            final F2<BigDecimal, BigDecimal, Boolean> fDecimal,
            final F2<String, String, Boolean> fString,
            final F2<List<String>, List<String>, Boolean> fStringList,
            final F0<Boolean> fNone) {

        return accumulate(
                left.getData().f().map(nel -> nel(failureExpressionLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel))),
                right.getData().f().map(nel -> nel(failureExpressionRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel))),
                (dataLeft, dataRight) -> dataLeft.matchValue(
                        valueLeft -> dataRight.matchValueBoolean(
                                success1(parserData, valueLeft, fBoolean),
                                failureTypeMismatch0(parserData, dataLeft.getType(), dataRight.getType())),
                        valueLeft -> dataRight.matchValueDateTimeStamp(
                                success1(parserData, valueLeft, fDateTimeStamp),
                                failureTypeMismatch0(parserData, dataLeft.getType(), dataRight.getType())),
                        valueLeft -> dataRight.matchValueDecimal(
                                success1(parserData, valueLeft, fDecimal),
                                failureTypeMismatch0(parserData, dataLeft.getType(), dataRight.getType())),
                        valueLeft -> dataRight.matchValueString(
                                success1(parserData, valueLeft, fString),
                                failureTypeMismatch0(parserData, dataLeft.getType(), dataRight.getType())),
                        valueLeft -> dataRight.matchValueStringList(
                                success1(parserData, valueLeft, fStringList),
                                failureTypeMismatch0(parserData, dataLeft.getType(), dataRight.getType())),
                        () -> dataRight.matchValueNone(
                                failureTypeMismatch0(parserData, dataLeft.getType(), dataRight.getType()),
                                success0(parserData, fNone))))
                .bind(i -> i);
    }

    private static Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> ordHelper(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> left,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> right,
            final TrustExpressionParserData parserData,
            final F2<Boolean, Boolean, Boolean> fBoolean,
            final F2<Instant, Instant, Boolean> fDateTimeStamp,
            final F2<BigDecimal, BigDecimal, Boolean> fDecimal,
            final F2<String, String, Boolean> fString) {

        return accumulate(
                left.getData().f().map(nel -> nel(failureExpressionLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel))),
                right.getData().f().map(nel -> nel(failureExpressionRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), nel))),
                (dataLeft, dataRight) -> dataLeft.matchValue(
                        valueLeft -> dataRight.matchValue(
                                success1(parserData, valueLeft, fBoolean),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableRight1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableRight0(parserData, dataLeft.getType(), dataRight.getType())),
                        valueLeft -> dataRight.matchValue(
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                success1(parserData, valueLeft, fDateTimeStamp),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableRight1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableRight0(parserData, dataLeft.getType(), dataRight.getType())),
                        valueLeft -> dataRight.matchValue(
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                success1(parserData, valueLeft, fDecimal),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableRight1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableRight0(parserData, dataLeft.getType(), dataRight.getType())),
                        valueLeft -> dataRight.matchValue(
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatch1(parserData, dataLeft.getType(), dataRight.getType()),
                                success1(parserData, valueLeft, fString),
                                failureTypeMismatchAndTypeUnorderableRight1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableRight0(parserData, dataLeft.getType(), dataRight.getType())),
                        valueLeft -> dataRight.matchValue(
                                failureTypeMismatchAndTypeUnorderableLeft(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableLeft(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableLeft(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableLeft(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeUnorderable1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderable0(parserData, dataLeft.getType(), dataRight.getType())),
                        () -> dataRight.matchValue(
                                failureTypeMismatchAndTypeUnorderableLeft(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableLeft(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableLeft(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderableLeft(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeMismatchAndTypeUnorderable1(parserData, dataLeft.getType(), dataRight.getType()),
                                failureTypeUnorderable0(parserData, dataLeft.getType(), dataRight.getType()))))
                .bind(i -> i);
    }

    private static F0<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeUnexpectedLeft0(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeExpected,
            final TrustExpressionType typeActual) {

        return () -> fail(nel(
                TrustExpressionFailure.failureTypeUnexpectedLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeExpected, typeActual)));
    }

    private static F0<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeUnexpected0(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeExpected,
            final TrustExpressionType typeActual) {

        return () -> fail(nel(
                TrustExpressionFailure.failureTypeUnexpected(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeExpected, typeActual)));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeUnexpectedLeft1(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeExpected,
            final TrustExpressionType typeActual) {

        return ignore -> fail(nel(
                TrustExpressionFailure.failureTypeUnexpectedLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeExpected, typeActual)));
    }

    private static F0<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeUnexpectedRight0(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeExpected,
            final TrustExpressionType typeActual) {

        return () -> fail(nel(
                TrustExpressionFailure.failureTypeUnexpectedRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeExpected, typeActual)));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeUnexpectedRight1(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeExpected,
            final TrustExpressionType typeActual) {

        return ignore -> fail(nel(
                TrustExpressionFailure.failureTypeUnexpectedRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeExpected, typeActual)));
    }

    private static F0<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeMismatch0(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return () -> fail(nel(
                TrustExpressionFailure.failureTypeMismatch(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft, typeRight)));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeMismatch1(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return ignore -> fail(nel(
                TrustExpressionFailure.failureTypeMismatch(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft, typeRight)));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeMismatchAndTypeUnorderableLeft(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return ignore -> fail(nel(
                TrustExpressionFailure.failureTypeMismatch(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft, typeRight),
                TrustExpressionFailure.failureTypeUnorderableLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft)));
    }

    private static F0<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeMismatchAndTypeUnorderableRight0(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return () -> fail(nel(
                TrustExpressionFailure.failureTypeMismatch(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft, typeRight),
                TrustExpressionFailure.failureTypeUnorderableRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeRight)));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeMismatchAndTypeUnorderableRight1(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return ignore -> fail(nel(
                TrustExpressionFailure.failureTypeMismatch(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft, typeRight),
                TrustExpressionFailure.failureTypeUnorderableRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeRight)));
    }

    private static F0<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeUnorderable0(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return () -> fail(nel(
                TrustExpressionFailure.failureTypeUnorderableLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft),
                TrustExpressionFailure.failureTypeUnorderableRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeRight)));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeUnorderable1(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return ignore -> fail(nel(
                TrustExpressionFailure.failureTypeUnorderableLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft),
                TrustExpressionFailure.failureTypeUnorderableRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeRight)));
    }

    private static F0<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeMismatchAndTypeUnorderable0(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return () -> fail(nel(
                TrustExpressionFailure.failureTypeMismatch(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft, typeRight),
                TrustExpressionFailure.failureTypeUnorderableLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft),
                TrustExpressionFailure.failureTypeUnorderableRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeRight)));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> failureTypeMismatchAndTypeUnorderable1(
            final TrustExpressionParserData parserData,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return ignore -> fail(nel(
                TrustExpressionFailure.failureTypeMismatch(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft, typeRight),
                TrustExpressionFailure.failureTypeUnorderableLeft(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeLeft),
                TrustExpressionFailure.failureTypeUnorderableRight(parserData.getTrustInteroperabilityProfileNonEmptyList(), typeRight)));
    }

    private static F0<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> success0(
            final TrustExpressionParserData parserData,
            final F0<Boolean> f) {

        return () -> success(dataValueBoolean(parserData.getTrustInteroperabilityProfileNonEmptyList(), f.f()));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> success1(
            final TrustExpressionParserData parserData,
            final T0 valueLeft,
            final F2<T0, T0, Boolean> f) {

        return valueRight -> success(dataValueBoolean(parserData.getTrustInteroperabilityProfileNonEmptyList(), f.f(valueLeft, valueRight)));
    }

    private static <T0> F1<T0, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> success1(
            final TrustExpressionParserData parserData,
            final F1<T0, Boolean> f) {

        return value -> success(dataValueBoolean(parserData.getTrustInteroperabilityProfileNonEmptyList(), f.f(value)));
    }
}
