package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.product.P2;

public interface TrustInteroperabilityProfileTrustExpressionEvaluator {

    TrustExpressionEvaluation evaluate(
            final String trustInteroperabilityProfileUriString,
            final List<String> trustmarkUriStringList);

    TrustExpressionEvaluation evaluate(
            final TrustExpression<Option<TrustInteroperabilityProfile>, P2<Option<TrustInteroperabilityProfile>, Either<TrustExpressionParserFailure, P2<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement>>>> trustExpressionOuter,
            final List<Trustmark> trustmarkList);
}
