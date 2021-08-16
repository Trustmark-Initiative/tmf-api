package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

public interface TrustExpressionEvaluator {

    TrustExpressionEvaluation evaluate(
            final String trustInteroperabilityProfileUriString,
            final List<String> trustmarkUriStringList);

    TrustExpressionEvaluation evaluate(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionOuter,
            final List<Trustmark> trustmarkList);
}
