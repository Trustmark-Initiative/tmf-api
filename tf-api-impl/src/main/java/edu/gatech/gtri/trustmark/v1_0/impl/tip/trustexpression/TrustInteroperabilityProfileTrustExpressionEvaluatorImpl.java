package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParser;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P3;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.tip.TrustmarkUtility.satisfyingTrustmarkList;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluatorFailure.evaluatorFailureResolve;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluatorFailure.evaluatorFailureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState.FAILURE;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState.SUCCESS;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState.UNKNOWN;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.product.P.p;

public class TrustInteroperabilityProfileTrustExpressionEvaluatorImpl implements TrustInteroperabilityProfileTrustExpressionEvaluator {

    private final TrustmarkResolver trustmarkResolver;
    private final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser;

    public TrustInteroperabilityProfileTrustExpressionEvaluatorImpl(
            final TrustmarkResolver trustmarkResolver,
            final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser) {

        requireNonNull(trustmarkResolver);
        requireNonNull(trustInteroperabilityProfileTrustExpressionParser);

        this.trustmarkResolver = trustmarkResolver;
        this.trustInteroperabilityProfileTrustExpressionParser = trustInteroperabilityProfileTrustExpressionParser;
    }

    @Override
    public TrustExpressionEvaluation evaluate(
            final TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> trustExpressionOuter,
            final List<Trustmark> trustmarkList) {

        requireNonNull(trustExpressionOuter);
        requireNonNull(trustmarkList);

        return new TrustExpressionEvaluation(p(nil(), evaluateHelper(trustExpressionOuter, trustmarkList)));
    }

    @Override
    public TrustExpressionEvaluation evaluate(
            final String trustInteroperabilityProfileUriString,
            final List<String> trustmarkUriStringList) {

        requireNonNull(trustInteroperabilityProfileUriString);
        requireNonNull(trustmarkUriStringList);

        return new TrustExpressionEvaluation(resolve(trustmarkUriStringList)
                .map2(trustmarkList -> evaluateHelper(trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfileUriString), trustmarkList)));
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

    private TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> evaluateHelper(
            final TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> trustExpressionOuter,
            final List<Trustmark> trustmarkList) {

        return trustExpressionOuter.match(
                data -> stateTerminal(data, trustmarkList),
                (trustExpressionOperatorUnary, trustExpression, data) -> trustExpressionOperatorUnary.matchUnary(
                        trustExpressionOperatorNot -> stateNot(
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
                                data)));
    }

    private TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> stateTerminal(
            final P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>> data,
            final List<Trustmark> trustmarkList) {

        final P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>
                dataInterim = data.map2(either -> either.right().map(p -> p.append(satisfyingTrustmarkList(p._2(), trustmarkList))));

        return terminal(dataInterim.map1(trustInteroperabilityProfileOption -> reduce(dataInterim._2().bimap(
                trustExpressionParserFailure -> p(trustInteroperabilityProfileOption, UNKNOWN),
                p3 -> p(trustInteroperabilityProfileOption, p3._3().isEmpty() ? FAILURE : SUCCESS)))));
    }

    private static TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> stateNot(
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> expression,
            final Option<TrustInteroperabilityProfile> data) {

        return not(expression, p(data, expression.getData(l -> l._2(), r -> r._1()._2()).not()));
    }

    private static TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> stateAnd(
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> left,
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> right,
            final Option<TrustInteroperabilityProfile> data) {

        return and(left, right, p(data, left.getData(l -> l._2(), r -> r._1()._2()).and(right.getData(l -> l._2(), r -> r._1()._2()))));
    }

    private static TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> stateOr(
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> left,
            final TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> right,
            final Option<TrustInteroperabilityProfile> data) {

        return or(left, right, p(data, left.getData(l -> l._2(), r -> r._1()._2()).or(right.getData(l -> l._2(), r -> r._1()._2()))));
    }
}
