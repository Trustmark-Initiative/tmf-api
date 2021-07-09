package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P3;

import static java.util.Objects.requireNonNull;

public class TrustExpressionEvaluation {

    private final P2<List<TrustExpressionEvaluatorFailure>, TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>>> evaluation;

    public TrustExpressionEvaluation(
            final P2<List<TrustExpressionEvaluatorFailure>, TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>>> evaluation) {

        requireNonNull(evaluation);

        this.evaluation = evaluation;
    }

    public P2<List<TrustExpressionEvaluatorFailure>, TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>>> getEvaluation() {
        return evaluation;
    }

    public List<TrustExpressionEvaluatorFailure> getTrustExpressionEvaluatorFailureList() {
        return evaluation._1();
    }

    public TrustExpression<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, P2<P2<Option<TrustInteroperabilityProfile>, TrustExpressionState>, Either<TrustExpressionParserFailure, P3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>>>>> getTrustExpression() {
        return evaluation._2();
    }
}
