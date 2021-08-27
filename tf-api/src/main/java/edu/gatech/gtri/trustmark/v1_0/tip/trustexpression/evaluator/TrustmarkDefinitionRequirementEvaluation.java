package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class TrustmarkDefinitionRequirementEvaluation {


    private final List<TrustExpressionEvaluatorFailure> trustExpressionEvaluatorFailureList;
    private final Validation<NonEmptyList<TrustExpressionFailure>, List<P2<TrustmarkDefinitionRequirement, List<Trustmark>>>> trustmarkDefinitionRequirementSatisfaction;

    public TrustmarkDefinitionRequirementEvaluation(
            final List<TrustExpressionEvaluatorFailure> trustExpressionEvaluatorFailureList,
            final Validation<NonEmptyList<TrustExpressionFailure>, List<P2<TrustmarkDefinitionRequirement, List<Trustmark>>>> trustmarkDefinitionRequirementSatisfaction) {

        requireNonNull(trustExpressionEvaluatorFailureList);
        requireNonNull(trustmarkDefinitionRequirementSatisfaction);

        this.trustExpressionEvaluatorFailureList = trustExpressionEvaluatorFailureList;
        this.trustmarkDefinitionRequirementSatisfaction = trustmarkDefinitionRequirementSatisfaction;
    }

    public List<TrustExpressionEvaluatorFailure> getTrustExpressionEvaluatorFailureList() {
        return trustExpressionEvaluatorFailureList;
    }

    public Validation<NonEmptyList<TrustExpressionFailure>, List<P2<TrustmarkDefinitionRequirement, List<Trustmark>>>> getTrustmarkDefinitionRequirementSatisfaction() {
        return trustmarkDefinitionRequirementSatisfaction;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TrustmarkDefinitionRequirementEvaluation that = (TrustmarkDefinitionRequirementEvaluation) o;
        return trustExpressionEvaluatorFailureList.equals(that.trustExpressionEvaluatorFailureList) && trustmarkDefinitionRequirementSatisfaction.equals(that.trustmarkDefinitionRequirementSatisfaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trustExpressionEvaluatorFailureList, trustmarkDefinitionRequirementSatisfaction);
    }

    public static TrustmarkDefinitionRequirementEvaluation trustmarkDefinitionRequirementEvaluation(
            final List<TrustExpressionEvaluatorFailure> trustExpressionEvaluatorFailureList,
            final Validation<NonEmptyList<TrustExpressionFailure>, List<P2<TrustmarkDefinitionRequirement, List<Trustmark>>>> trustmarkDefinitionRequirementSatisfaction) {

        return new TrustmarkDefinitionRequirementEvaluation(
                trustExpressionEvaluatorFailureList,
                trustmarkDefinitionRequirementSatisfaction);
    }
}
