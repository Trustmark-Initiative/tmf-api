package edu.gatech.gtri.trustmark.v1_0.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import java.net.URI;

public interface TrustExpressionEvaluator {

    TrustExpressionEvaluation evaluate(
            final String trustInteroperabilityProfileUriString,
            final List<String> trustmarkUriStringList);

    TrustExpressionEvaluation evaluate(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionOuter,
            final List<Trustmark> trustmarkList);
}
