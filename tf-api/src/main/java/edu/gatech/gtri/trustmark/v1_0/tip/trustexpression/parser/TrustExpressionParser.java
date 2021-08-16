package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import java.net.URI;

public interface TrustExpressionParser {

    TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final TrustInteroperabilityProfileReference trustInteroperabilityProfileReference);

    TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final String trustInteroperabilityProfileUriString);

    TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final URI trustInteroperabilityProfileUri);

    TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> parse(
            final TrustInteroperabilityProfile trustInteroperabilityProfile);
}
