package edu.gatech.gtri.trustmark.v1_0.impl.tip.parser;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionParserFactoryJParsec;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.TrustExpressionTypeUtility;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.TrustExpressionParserDataReference;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.noop;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureCycle;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureIdentifierUnexpectedTrustInteroperabilityProfile;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureIdentifierUnknown;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionRequirement;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureNonTerminalUnexpected;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureParser;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureResolveTrustInteroperabilityProfile;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureResolveTrustmarkDefinition;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureTypeUnexpected;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean.TYPE_BOOLEAN;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDecimal.TYPE_DECIMAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeString.TYPE_STRING;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeStringList.TYPE_STRING_LIST;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataLiteralBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataLiteralDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataLiteralDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataLiteralString;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionRequirement;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.sequenceValidation;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.NonEmptyList.nonEmptyListSemigroup;
import static org.gtri.fj.data.TreeMap.iterableTreeMap;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.condition;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public class TrustExpressionParserImpl implements TrustExpressionParser {

    private final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver;
    private final TrustmarkDefinitionResolver trustmarkDefinitionResolver;

    public TrustExpressionParserImpl(
            final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver,
            final TrustmarkDefinitionResolver trustmarkDefinitionResolver) {

        requireNonNull(trustInteroperabilityProfileResolver);
        requireNonNull(trustmarkDefinitionResolver);

        this.trustInteroperabilityProfileResolver = trustInteroperabilityProfileResolver;
        this.trustmarkDefinitionResolver = trustmarkDefinitionResolver;
    }

    @Override
    public TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final TrustInteroperabilityProfileReference trustInteroperabilityProfileReference) {

        requireNonNull(trustInteroperabilityProfileReference);

        return parse(nil(), trustInteroperabilityProfileReference);
    }

    @Override
    public TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final String trustInteroperabilityProfileUriString) {

        requireNonNull(trustInteroperabilityProfileUriString);

        return parse(nil(), trustInteroperabilityProfileUriString);
    }

    @Override
    public TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final URI trustInteroperabilityProfileUri) {

        requireNonNull(trustInteroperabilityProfileUri);

        return parse(nil(), trustInteroperabilityProfileUri);
    }

    @Override
    public TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final TrustInteroperabilityProfile trustInteroperabilityProfile) {

        requireNonNull(trustInteroperabilityProfile);

        return parse(nil(), trustInteroperabilityProfile);
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final TrustInteroperabilityProfileReference trustInteroperabilityProfileReference) {

        return parse(trustInteroperabilityProfileList, trustInteroperabilityProfileReference.getIdentifier());
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final String trustInteroperabilityProfileUriString) {

        return reduce(Try.<URI, RuntimeException>f(() -> URI.create(trustInteroperabilityProfileUriString))._1().toEither().bimap(
                runtimeException -> terminal(fail(nel(failureURI(trustInteroperabilityProfileList, trustInteroperabilityProfileUriString, runtimeException)))),
                uri -> parse(trustInteroperabilityProfileList, uri)));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final URI trustInteroperabilityProfileUri) {

        return reduce(Try.<TrustInteroperabilityProfile, ResolveException>f(() -> trustInteroperabilityProfileResolver.resolve(trustInteroperabilityProfileUri))._1().toEither().bimap(
                resolveException -> terminal(fail(nel(failureResolveTrustInteroperabilityProfile(trustInteroperabilityProfileList, trustInteroperabilityProfileUri, resolveException)))),
                trustInteroperabilityProfile -> parse(trustInteroperabilityProfileList, trustInteroperabilityProfile)));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final TrustInteroperabilityProfile trustInteroperabilityProfile) {

        return reduce(accumulate(
                validationCycle(trustInteroperabilityProfileList, trustInteroperabilityProfile),
                validationTrustExpression(trustInteroperabilityProfileList, trustInteroperabilityProfile),
                validationTrustInteroperabilityProfileReferenceMap(trustInteroperabilityProfileList, trustInteroperabilityProfile),
                (trustInteroperabilityProfileNonEmptyList, trustExpression, trustInteroperabilityProfileReferenceMap) -> parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpression))
                .toEither()
                .left()
                .map(this::terminalFail));
    }

    private Validation<NonEmptyList<TrustExpressionFailure>, NonEmptyList<TrustInteroperabilityProfile>> validationCycle(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final TrustInteroperabilityProfile trustInteroperabilityProfile) {

        return condition(
                !trustInteroperabilityProfileList.exists(trustInteroperabilityProfileInner -> trustInteroperabilityProfileInner.getIdentifier().equals(trustInteroperabilityProfile.getIdentifier())),
                nel(failureCycle(nel(trustInteroperabilityProfile, trustInteroperabilityProfileList))),
                nel(trustInteroperabilityProfile, trustInteroperabilityProfileList));
    }

    private Validation<NonEmptyList<TrustExpressionFailure>, TrustExpression<TrustExpressionData>> validationTrustExpression(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final TrustInteroperabilityProfile trustInteroperabilityProfile) {

        return Try.<TrustExpression<TrustExpressionData>, RuntimeException>f(() -> TrustExpressionParserFactoryJParsec.parser().parse(trustInteroperabilityProfile.getTrustExpression()))._1()
                .f().map(runtimeException -> nel(failureParser(nel(trustInteroperabilityProfile, trustInteroperabilityProfileList), trustInteroperabilityProfile.getTrustExpression(), runtimeException)));
    }

    private Validation<NonEmptyList<TrustExpressionFailure>, TreeMap<String, Either<TrustInteroperabilityProfileReference, P2<TrustmarkDefinitionRequirement, TreeMap<String, TrustmarkDefinitionParameter>>>>> validationTrustInteroperabilityProfileReferenceMap(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final TrustInteroperabilityProfile trustInteroperabilityProfile) {

        return sequenceValidation(
                nonEmptyListSemigroup(),
                iterableList(trustInteroperabilityProfile.getReferences())
                        .<Validation<NonEmptyList<TrustExpressionFailure>, P2<String, Either<TrustInteroperabilityProfileReference, P2<TrustmarkDefinitionRequirement, TreeMap<String, TrustmarkDefinitionParameter>>>>>>map(abstractTIPReference -> {
                            if (abstractTIPReference.isTrustmarkDefinitionRequirement()) {
                                return validationTrustmarkDefinitionRequirement(trustInteroperabilityProfileList, trustInteroperabilityProfile, (TrustmarkDefinitionRequirement) abstractTIPReference)
                                        .map(trustmarkDefinition -> p(
                                                abstractTIPReference.getId(),
                                                right(p(
                                                        (TrustmarkDefinitionRequirement) abstractTIPReference,
                                                        iterableTreeMap(
                                                                stringOrd,
                                                                iterableList(trustmarkDefinition.getAllParameters())
                                                                        .map(trustmarkDefinitionParameter -> p(trustmarkDefinitionParameter.getIdentifier(), trustmarkDefinitionParameter)))))));
                            } else if (abstractTIPReference.isTrustInteroperabilityProfileReference()) {
                                return success(p(
                                        abstractTIPReference.getId(),
                                        left((TrustInteroperabilityProfileReference) abstractTIPReference)));
                            } else {
                                throw new RuntimeException("The abstract TIP reference must be either a trustmark definition requirement or a trust interoperability profile reference.");
                            }
                        }))
                .map(list -> iterableTreeMap(stringOrd, list));
    }

    private Validation<NonEmptyList<TrustExpressionFailure>, TrustmarkDefinition> validationTrustmarkDefinitionRequirement(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement) {

        return Try.<TrustmarkDefinition, ResolveException>f(() -> trustmarkDefinitionResolver.resolve(trustmarkDefinitionRequirement.getIdentifier()))._1()
                .f().map(resolveException -> nel(failureResolveTrustmarkDefinition(nel(trustInteroperabilityProfile, trustInteroperabilityProfileList), trustmarkDefinitionRequirement.getIdentifier(), resolveException)));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parseHelper(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TreeMap<String, Either<TrustInteroperabilityProfileReference, P2<TrustmarkDefinitionRequirement, TreeMap<String, TrustmarkDefinitionParameter>>>> trustInteroperabilityProfileReferenceMap,
            final TrustExpression<TrustExpressionData> trustExpressionOuter) {

        return trustExpressionOuter.match(
                trustExpressionData -> parseHelperTerminal(
                        trustInteroperabilityProfileNonEmptyList,
                        trustInteroperabilityProfileReferenceMap,
                        trustExpressionData),
                (trustExpressionOperatorUnary, trustExpression, trustExpressionData) -> trustExpressionOperatorUnary.matchUnary(
                        noop -> parseHelperUnary(
                                TrustExpression::noop,
                                TrustExpressionTypeUtility::mustSatisfyNoop,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpression),
                                trustInteroperabilityProfileNonEmptyList),
                        not -> parseHelperUnary(
                                TrustExpression::not,
                                TrustExpressionTypeUtility::mustSatisfyNot,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpression),
                                trustInteroperabilityProfileNonEmptyList),
                        exists -> parseHelperUnary(
                                TrustExpression::exists,
                                TrustExpressionTypeUtility::mustSatisfyExists,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpression),
                                trustInteroperabilityProfileNonEmptyList)),
                (trustExpressionOperatorBinary, trustExpressionLeft, trustExpressionRight, trustExpressionData) -> trustExpressionOperatorBinary.matchBinary(
                        and -> parseHelperBinary(
                                TrustExpression::and,
                                TrustExpressionTypeUtility::mustSatisfyAndForParser,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList),
                        or -> parseHelperBinary(
                                TrustExpression::or,
                                TrustExpressionTypeUtility::mustSatisfyOrForParser,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList),
                        lessThan -> parseHelperBinary(
                                TrustExpression::lessThan,
                                TrustExpressionTypeUtility::mustSatisfyLessThan,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList),
                        lessThanOrEqual -> parseHelperBinary(
                                TrustExpression::lessThanOrEqual,
                                TrustExpressionTypeUtility::mustSatisfyLessThanOrEqual,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList),
                        greaterThanOrEqual -> parseHelperBinary(
                                TrustExpression::greaterThanOrEqual,
                                TrustExpressionTypeUtility::mustSatisfyGreaterThanOrEqual,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList),
                        greaterThan -> parseHelperBinary(
                                TrustExpression::greaterThan,
                                TrustExpressionTypeUtility::mustSatisfyGreaterThan,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList),
                        equal -> parseHelperBinary(
                                TrustExpression::equal,
                                TrustExpressionTypeUtility::mustSatisfyEqual,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList),
                        notEqual -> parseHelperBinary(
                                TrustExpression::notEqual,
                                TrustExpressionTypeUtility::mustSatisfyNotEqual,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList),
                        contains -> parseHelperBinary(
                                TrustExpression::contains,
                                TrustExpressionTypeUtility::mustSatisfyContains,
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parseHelper(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                trustInteroperabilityProfileNonEmptyList)));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parseHelperUnary(
            final F2<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>>> unary,
            final F2<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>>, NonEmptyList<TrustInteroperabilityProfile>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> mustSatisfy,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpression,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return unary.f(
                trustExpression,
                mustSatisfy.f(
                                trustExpression,
                                trustInteroperabilityProfileNonEmptyList)
                        .map(trustExpressionParserData -> dataNonTerminal(trustInteroperabilityProfileNonEmptyList, TYPE_BOOLEAN)));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parseHelperBinary(
            final F3<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>>, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>>> binary,
            final F3<TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>>, TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>>, NonEmptyList<TrustInteroperabilityProfile>, Validation<NonEmptyList<TrustExpressionFailure>, P2<TrustExpressionParserData, TrustExpressionParserData>>> mustSatisfy,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return binary.f(
                trustExpressionLeft,
                trustExpressionRight,
                mustSatisfy.f(
                                trustExpressionLeft,
                                trustExpressionRight,
                                trustInteroperabilityProfileNonEmptyList)
                        .map(trustExpressionParserData -> dataNonTerminal(trustInteroperabilityProfileNonEmptyList, TYPE_BOOLEAN)));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parseHelperTerminal(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TreeMap<String, Either<TrustInteroperabilityProfileReference, P2<TrustmarkDefinitionRequirement, TreeMap<String, TrustmarkDefinitionParameter>>>> trustInteroperabilityProfileReferenceFor,
            final TrustExpressionData data) {

        return data.match(
                dataLiteral -> dataLiteral.matchLiteral(
                        value -> terminalSuccess(dataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, value)),
                        value -> terminalSuccess(dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, value)),
                        value -> terminalSuccess(dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, value)),
                        value -> terminalSuccess(dataLiteralString(trustInteroperabilityProfileNonEmptyList, value))),
                dataReference -> dataReference.matchReference(
                        identifier -> trustInteroperabilityProfileReferenceFor.get(identifier)
                                .map(eitherInner -> reduce(eitherInner.bimap(
                                        trustInteroperabilityProfileReference -> noopHelper(trustInteroperabilityProfileNonEmptyList, parse(trustInteroperabilityProfileNonEmptyList.toList(), trustInteroperabilityProfileReference)),
                                        trustmarkDefinitionRequirement -> terminalSuccess(dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement._1())))))
                                .orSome(terminalFailNel(failureIdentifierUnknown(trustInteroperabilityProfileNonEmptyList, identifier))),
                        (trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> trustInteroperabilityProfileReferenceFor.get(trustmarkDefinitionRequirementIdentifier)
                                .map(eitherInner -> reduce(eitherInner.bimap(
                                        trustInteroperabilityProfileReference -> terminalFailNel(failureIdentifierUnexpectedTrustInteroperabilityProfile(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier)),
                                        trustmarkDefinitionRequirement -> trustmarkDefinitionRequirement._2().get(trustmarkDefinitionParameterIdentifier)
                                                .map(trustmarkDefinitionParameter -> trustmarkDefinitionParameter.getParameterKind().match(
                                                        parameterKindBoolean -> terminalSuccess(dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, TYPE_BOOLEAN, trustmarkDefinitionRequirement._1(), trustmarkDefinitionParameter)),
                                                        parameterKindDatetime -> terminalSuccess(dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, TYPE_DATE_TIME_STAMP, trustmarkDefinitionRequirement._1(), trustmarkDefinitionParameter)),
                                                        parameterKindEnum -> terminalSuccess(dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, TYPE_STRING, trustmarkDefinitionRequirement._1(), trustmarkDefinitionParameter)),
                                                        parameterKindEnumMulti -> terminalSuccess(dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, TYPE_STRING_LIST, trustmarkDefinitionRequirement._1(), trustmarkDefinitionParameter)),
                                                        parameterKindNumber -> terminalSuccess(dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, TYPE_DECIMAL, trustmarkDefinitionRequirement._1(), trustmarkDefinitionParameter)),
                                                        parameterKindString -> terminalSuccess(dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, TYPE_STRING, trustmarkDefinitionRequirement._1(), trustmarkDefinitionParameter)),
                                                        parameterKindOther -> terminalFailNel(failureTypeUnexpected(trustInteroperabilityProfileNonEmptyList, nel(ParameterKind.BOOLEAN, ParameterKind.DATETIME, ParameterKind.ENUM, ParameterKind.ENUM_MULTI, ParameterKind.NUMBER, ParameterKind.STRING), parameterKindOther))))
                                                .orSome(terminalFailNel(failureIdentifierUnknownTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement._1(), trustmarkDefinitionParameterIdentifier))))))
                                .orSome(terminalFailNel(failureIdentifierUnknownTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier)))),
                dataNonTerminal -> terminalFailNel(failureNonTerminalUnexpected(trustInteroperabilityProfileNonEmptyList)));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> terminalFailNel(
            final TrustExpressionFailure trustInteroperabilityProfileParserFailure) {

        return terminal(fail(nel(trustInteroperabilityProfileParserFailure)));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> terminalFail(
            final NonEmptyList<TrustExpressionFailure> trustInteroperabilityProfileParserFailureNonEmptyList) {

        return terminal(fail(trustInteroperabilityProfileParserFailureNonEmptyList));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> terminalSuccess(
            final TrustExpressionParserDataLiteral<?> trustInteroperabilityProfileTrustExpressionParserDataLiteral) {

        return terminal(success(trustInteroperabilityProfileTrustExpressionParserDataLiteral));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> terminalSuccess(
            final TrustExpressionParserDataReference trustInteroperabilityProfileTrustExpressionParserDataReference) {

        return terminal(success(trustInteroperabilityProfileTrustExpressionParserDataReference));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> noopHelper(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpression) {

        return noop(trustExpression, trustExpression.getData().map(trustExpressionParserData -> dataNonTerminal(trustInteroperabilityProfileNonEmptyList, trustExpressionParserData.getTrustExpressionType())));
    }
}
