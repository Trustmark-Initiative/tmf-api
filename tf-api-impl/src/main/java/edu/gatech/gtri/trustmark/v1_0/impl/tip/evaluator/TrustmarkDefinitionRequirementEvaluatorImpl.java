package edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustmarkDefinitionRequirementEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustmarkDefinitionRequirementEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;

import java.net.URI;
import java.net.URISyntaxException;

import static edu.gatech.gtri.trustmark.v1_0.impl.trust.TrustmarkUtility.satisfyingTrustmarkList;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureNonTerminalUnexpected;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.evaluatorFailureResolve;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.evaluatorFailureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustmarkDefinitionRequirementEvaluation.trustmarkDefinitionRequirementEvaluation;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.P.p;

public class TrustmarkDefinitionRequirementEvaluatorImpl implements TrustmarkDefinitionRequirementEvaluator {

    private final TrustmarkResolver trustmarkResolver;
    private final TrustExpressionParser trustExpressionParser;

    public TrustmarkDefinitionRequirementEvaluatorImpl(
            final TrustmarkResolver trustmarkResolver,
            final TrustExpressionParser trustExpressionParser) {

        requireNonNull(trustmarkResolver);
        requireNonNull(trustExpressionParser);

        this.trustmarkResolver = trustmarkResolver;
        this.trustExpressionParser = trustExpressionParser;
    }

    @Override
    public TrustmarkDefinitionRequirementEvaluation evaluate(
            final String trustInteroperabilityProfileUriString,
            final List<String> trustmarkUriStringList) {

        requireNonNull(trustInteroperabilityProfileUriString);
        requireNonNull(trustmarkUriStringList);

        final P2<List<TrustExpressionEvaluatorFailure>, List<Trustmark>> resolve = resolve(trustmarkUriStringList);

        return trustmarkDefinitionRequirementEvaluation(resolve._1(), evaluateTrustExpression(trustExpressionParser.parse(trustInteroperabilityProfileUriString), resolve._2()));
    }

    @Override
    public TrustmarkDefinitionRequirementEvaluation evaluate(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionOuter,
            final List<Trustmark> trustmarkList) {

        requireNonNull(trustExpressionOuter);
        requireNonNull(trustmarkList);

        return trustmarkDefinitionRequirementEvaluation(nil(), evaluateTrustExpression(trustExpressionOuter, trustmarkList));
    }

    @Override
    public TrustmarkDefinitionRequirementEvaluation evaluate(
            final List<TrustmarkDefinitionRequirement> trustmarkDefinitionRequirementList,
            final List<Trustmark> trustmarkList) {

        requireNonNull(trustmarkDefinitionRequirementList);
        requireNonNull(trustmarkList);

        return trustmarkDefinitionRequirementEvaluation(nil(), evaluateSatisfyingTrustmarkList(success(trustmarkDefinitionRequirementList), trustmarkList));
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

        return Try.<URI, URISyntaxException>f(() -> new URI(trustmarkUriString))._1().f().<TrustExpressionEvaluatorFailure>map(uriSyntaxException -> evaluatorFailureURI(trustmarkUriString, uriSyntaxException))
                .bind(trustmarkUri -> Try.<Trustmark, ResolveException>f(() -> trustmarkResolver.resolve(trustmarkUri))._1().f().map(resolveException -> evaluatorFailureResolve(trustmarkUri, resolveException)))
                .toEither();
    }

    private static Validation<NonEmptyList<TrustExpressionFailure>, List<P2<TrustmarkDefinitionRequirement, List<Trustmark>>>> evaluateTrustExpression(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpression,
            final List<Trustmark> trustmarkList) {

        return evaluateSatisfyingTrustmarkList(evaluateTrustExpression(trustExpression), trustmarkList);
    }

    private static Validation<NonEmptyList<TrustExpressionFailure>, List<TrustmarkDefinitionRequirement>> evaluateTrustExpression(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionOuter) {

        return trustExpressionOuter.match(
                data -> data.bind(TrustmarkDefinitionRequirementEvaluatorImpl::evaluateTrustExpression),
                (trustExpressionOperatorUnary, trustExpression, data) -> evaluateTrustExpression(trustExpression),
                (trustExpressionOperatorBinary, trustExpressionLeft, trustExpressionRight, data) -> accumulate(
                        evaluateTrustExpression(trustExpressionLeft),
                        evaluateTrustExpression(trustExpressionRight),
                        List::append));
    }

    private static Validation<NonEmptyList<TrustExpressionFailure>, List<TrustmarkDefinitionRequirement>> evaluateTrustExpression(
            final TrustExpressionParserData data) {

        return data.match(
                dataLiteral -> success(nil()),
                dataReference -> dataReference.matchReference(
                        (trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement) -> success(arrayList(trustmarkDefinitionRequirement)),
                        (trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement, trustmarkDefinitionParameter) -> success(arrayList(trustmarkDefinitionRequirement))),
                dataNonTerminal -> fail(nel(failureNonTerminalUnexpected(data.getTrustInteroperabilityProfileNonEmptyList()))));
    }

    private static Validation<NonEmptyList<TrustExpressionFailure>, List<P2<TrustmarkDefinitionRequirement, List<Trustmark>>>> evaluateSatisfyingTrustmarkList(
            final Validation<NonEmptyList<TrustExpressionFailure>, List<TrustmarkDefinitionRequirement>> trustmarkDefinitionRequirementList,
            final List<Trustmark> trustmarkList) {

        return trustmarkDefinitionRequirementList
                .map(list -> list
                        .map(trustmarkDefinitionRequirement -> p(
                                trustmarkDefinitionRequirement,
                                satisfyingTrustmarkList(trustmarkDefinitionRequirement, trustmarkList))));
    }
}
