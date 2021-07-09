package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.product.P2;

import java.net.URI;

public interface TrustInteroperabilityProfileTrustExpressionParser {

    TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final TrustInteroperabilityProfileReference trustInteroperabilityProfileReference);

    TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final String trustInteroperabilityProfileUriString);

    TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final URI trustInteroperabilityProfileUri);

    TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> parse(final TrustInteroperabilityProfile trustInteroperabilityProfile);
}
