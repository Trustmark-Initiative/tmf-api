package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionParserFactoryJParsec;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParser;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.Unit;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureIdentifier;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureParser;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureResolve;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureURI;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.data.TreeMap.iterableTreeMap;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public class TrustInteroperabilityProfileTrustExpressionParserImpl implements TrustInteroperabilityProfileTrustExpressionParser {

    private final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver;

    public TrustInteroperabilityProfileTrustExpressionParserImpl(final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver) {
        requireNonNull(trustInteroperabilityProfileResolver);

        this.trustInteroperabilityProfileResolver = trustInteroperabilityProfileResolver;
    }

    @Override
    public TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final TrustInteroperabilityProfileReference trustInteroperabilityProfileReference) {
        requireNonNull(trustInteroperabilityProfileReference);

        return parse(nil(), trustInteroperabilityProfileReference);
    }

    @Override
    public TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final String trustInteroperabilityProfileUriString) {
        requireNonNull(trustInteroperabilityProfileUriString);

        return parse(nil(), trustInteroperabilityProfileUriString);
    }

    @Override
    public TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final URI trustInteroperabilityProfileUri) {
        requireNonNull(trustInteroperabilityProfileUri);

        return parse(nil(), trustInteroperabilityProfileUri);
    }

    @Override
    public TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final TrustInteroperabilityProfile trustInteroperabilityProfile) {
        requireNonNull(trustInteroperabilityProfile);

        return parse(nil(), trustInteroperabilityProfile);
    }

    private TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList, final TrustInteroperabilityProfileReference trustInteroperabilityProfileReference) {

        return parse(trustInteroperabilityProfileList, trustInteroperabilityProfileReference.getIdentifier());
    }

    private TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList, final String trustInteroperabilityProfileUriString) {

        return reduce(Try.<URI, RuntimeException>f(() -> URI.create(trustInteroperabilityProfileUriString))._1().toEither().bimap(
                runtimeException -> terminal(p(
                        trustInteroperabilityProfileList.headOption(),
                        left(parserFailureURI(trustInteroperabilityProfileList, trustInteroperabilityProfileUriString, runtimeException)))),
                uri -> parse(trustInteroperabilityProfileList, uri)));
    }

    private TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList, final URI trustInteroperabilityProfileUri) {

        return reduce(Try.<TrustInteroperabilityProfile, ResolveException>f(() -> trustInteroperabilityProfileResolver.resolve(trustInteroperabilityProfileUri))._1().toEither().bimap(
                resolveException -> terminal(p(
                        trustInteroperabilityProfileList.headOption(),
                        left(parserFailureResolve(trustInteroperabilityProfileList, trustInteroperabilityProfileUri, resolveException)))),
                trustInteroperabilityProfile -> parse(trustInteroperabilityProfileList, trustInteroperabilityProfile)));
    }

    private TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList, final TrustInteroperabilityProfile trustInteroperabilityProfile) {

        return reduce(Try.<TrustExpression<Unit, String>, RuntimeException>f(() -> TrustExpressionParserFactoryJParsec.parser().parse(trustInteroperabilityProfile.getTrustExpression()))._1().toEither().bimap(
                runtimeException -> terminal(p(
                        some(trustInteroperabilityProfile),
                        left(parserFailureParser(nel(trustInteroperabilityProfile, trustInteroperabilityProfileList), trustInteroperabilityProfile.getTrustExpression(), runtimeException)))),
                trustExpression -> parse(nel(trustInteroperabilityProfile, trustInteroperabilityProfileList), trustInteroperabilityProfileReferenceMap(trustInteroperabilityProfile), trustExpression)));
    }

    private TreeMap<String, Either<TrustInteroperabilityProfileReference, TrustmarkDefinitionRequirement>> trustInteroperabilityProfileReferenceMap(final TrustInteroperabilityProfile trustInteroperabilityProfile) {
        return iterableTreeMap(
                stringOrd,
                iterableList(trustInteroperabilityProfile.getReferences())
                        .map(abstractTIPReference -> {
                            if (abstractTIPReference.isTrustInteroperabilityProfileReference()) {
                                return Either.<TrustInteroperabilityProfileReference, TrustmarkDefinitionRequirement>left((TrustInteroperabilityProfileReference) abstractTIPReference);
                            } else if (abstractTIPReference.isTrustmarkDefinitionRequirement()) {
                                return Either.<TrustInteroperabilityProfileReference, TrustmarkDefinitionRequirement>right((TrustmarkDefinitionRequirement) abstractTIPReference);
                            } else {
                                throw new RuntimeException("Unexpected abstract TIP reference type.");
                            }
                        })
                        .map(either -> either.bimap(
                                trustInteroperabilityProfileReference -> p(trustInteroperabilityProfileReference.getId(), trustInteroperabilityProfileReference),
                                trustmarkDefinitionRequirement -> p(trustmarkDefinitionRequirement.getId(), trustmarkDefinitionRequirement)))
                        .map(either -> p(reduce(either.bimap(P2::_1, P2::_1)), either.bimap(P2::_2, P2::_2))));
    }

    private TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TreeMap<String, Either<TrustInteroperabilityProfileReference, TrustmarkDefinitionRequirement>> trustInteroperabilityProfileReferenceMap,
            final TrustExpression<Unit, String> trustExpressionOuter) {

        return trustExpressionOuter.match(
                identifier -> trustInteroperabilityProfileReferenceMap.get(identifier)
                        .map(either -> reduce(either.bimap(
                                trustInteroperabilityProfileReference -> parse(trustInteroperabilityProfileNonEmptyList.toList(), trustInteroperabilityProfileReference),
                                trustmarkDefinitionRequirement -> TrustExpression.<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>>terminal(p(
                                        some(trustInteroperabilityProfileNonEmptyList.head()),
                                        right(p(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement)))))))
                        .orSome(terminal(p(
                                some(trustInteroperabilityProfileNonEmptyList.head()),
                                left(parserFailureIdentifier(trustInteroperabilityProfileNonEmptyList, identifier))))),
                (trustExpressionOperatorUnary, trustExpression, data) -> trustExpressionOperatorUnary.matchUnary(
                        trustExpressionOperatorNot -> not(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpression),
                                some(trustInteroperabilityProfileNonEmptyList.head()))),
                (trustExpressionOperatorBinary, trustExpressionLeft, trustExpressionRight, data) -> trustExpressionOperatorBinary.matchBinary(
                        trustExpressionOperatorAnd -> and(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                some(trustInteroperabilityProfileNonEmptyList.head())),
                        trustExpressionOperatorOr -> or(
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionLeft),
                                parse(trustInteroperabilityProfileNonEmptyList, trustInteroperabilityProfileReferenceMap, trustExpressionRight),
                                some(trustInteroperabilityProfileNonEmptyList.head()))));
    }
}
