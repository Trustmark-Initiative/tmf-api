package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.parser;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionParserFactoryJParsec;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataReference;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;

import java.net.URI;

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
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureIdentifierUnexpectedTrustInteroperabilityProfile;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureIdentifierUnknown;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionRequirement;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureNonTerminalUnexpected;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureParser;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureResolveTrustInteroperabilityProfile;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureResolveTrustmarkDefinition;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralString;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionRequirement;
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
                validationTrustExpression(trustInteroperabilityProfileList, trustInteroperabilityProfile),
                validationTrustInteroperabilityProfileReferenceMap(trustInteroperabilityProfileList, trustInteroperabilityProfile),
                (trustExpression, trustInteroperabilityProfileReferenceMap) -> parse(nel(trustInteroperabilityProfile, trustInteroperabilityProfileList), trustInteroperabilityProfileReferenceMap, trustExpression))
                .toEither()
                .left()
                .map(this::terminalFail));
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

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TreeMap<String, Either<TrustInteroperabilityProfileReference, P2<TrustmarkDefinitionRequirement, TreeMap<String, TrustmarkDefinitionParameter>>>> trustInteroperabilityProfileReferenceMap,
            final TrustExpression<TrustExpressionData> trustExpressionOuter) {

        return trustExpressionOuter.match(
                data -> parseTerminal(
                        trustInteroperabilityProfileNonEmptyList,
                        trustInteroperabilityProfileReferenceMap,
                        data),
                (trustExpressionOperatorUnary, trustExpression, data) -> trustExpressionOperatorUnary.matchUnary(
                        not -> not(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpression),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        exists -> exists(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpression),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList)))),
                (trustExpressionOperatorBinary, trustExpressionLeft, trustExpressionRight, data) -> trustExpressionOperatorBinary.matchBinary(
                        and -> and(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        or -> or(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        lessThan -> lessThan(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        lessThanOrEqual -> lessThanOrEqual(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        greaterThanOrEqual -> greaterThanOrEqual(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        greaterThan -> greaterThan(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        equal -> equal(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        notEqual -> notEqual(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList))),
                        contains -> contains(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                success(dataNonTerminal(trustInteroperabilityProfileNonEmptyList)))));
    }

    private TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parseTerminal(
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
                                        trustInteroperabilityProfileReference -> parse(trustInteroperabilityProfileNonEmptyList.toList(), trustInteroperabilityProfileReference),
                                        trustmarkDefinitionRequirement -> terminalSuccess(dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement._1())))))
                                .orSome(terminalFailNel(failureIdentifierUnknown(trustInteroperabilityProfileNonEmptyList, identifier))),
                        (trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> trustInteroperabilityProfileReferenceFor.get(trustmarkDefinitionRequirementIdentifier)
                                .map(eitherInner -> reduce(eitherInner.bimap(
                                        trustInteroperabilityProfileReference -> terminalFailNel(failureIdentifierUnexpectedTrustInteroperabilityProfile(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier)),
                                        trustmarkDefinitionRequirement -> trustmarkDefinitionRequirement._2().get(trustmarkDefinitionParameterIdentifier)
                                                .map(trustmarkDefinitionParameter -> terminalSuccess(dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement._1(), trustmarkDefinitionParameter)))
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
}
