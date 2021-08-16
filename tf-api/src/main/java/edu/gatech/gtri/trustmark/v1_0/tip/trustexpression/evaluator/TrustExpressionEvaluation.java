package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class TrustExpressionEvaluation {

    private final List<TrustExpressionEvaluatorFailure> trustExpressionEvaluatorFailureList;
    private final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression;

    private TrustExpressionEvaluation(
            final List<TrustExpressionEvaluatorFailure> trustExpressionEvaluatorFailureList,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression) {

        requireNonNull(trustExpressionEvaluatorFailureList);
        requireNonNull(trustExpression);

        this.trustExpressionEvaluatorFailureList = trustExpressionEvaluatorFailureList;
        this.trustExpression = trustExpression;
    }

    public List<TrustExpressionEvaluatorFailure> getTrustExpressionEvaluatorFailureList() {
        return trustExpressionEvaluatorFailureList;
    }

    public TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> getTrustExpression() {
        return trustExpression;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TrustExpressionEvaluation that = (TrustExpressionEvaluation) o;
        return trustExpressionEvaluatorFailureList.equals(that.trustExpressionEvaluatorFailureList) && trustExpression.equals(that.trustExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trustExpressionEvaluatorFailureList, trustExpression);
    }

    @Override
    public String toString() {
        return "TrustExpressionEvaluation{" +
                "trustExpressionEvaluatorFailureList=" + trustExpressionEvaluatorFailureList +
                ", trustExpression=" + trustExpression +
                '}';
    }

    public static final TrustExpressionEvaluation trustExpressionEvaluation(
            final List<TrustExpressionEvaluatorFailure> trustExpressionEvaluatorFailureList,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression) {

        return new TrustExpressionEvaluation(trustExpressionEvaluatorFailureList, trustExpression);
    }
}
