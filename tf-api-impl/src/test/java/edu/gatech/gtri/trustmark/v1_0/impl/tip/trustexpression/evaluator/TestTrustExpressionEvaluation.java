package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluation.trustExpressionEvaluation;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueBoolean;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.success;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTrustExpressionEvaluation {

    @Test
    public void test() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final List<TrustExpressionEvaluatorFailure> trustExpressionEvaluatorFailureList = arrayList(TrustExpressionEvaluatorFailure.evaluatorFailureURI("", new RuntimeException()));
        TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpression = TrustExpression.terminal(success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true)));

        assertTrue(trustExpressionEvaluation(trustExpressionEvaluatorFailureList, trustExpression).toString().contains(TrustExpressionEvaluation.class.getSimpleName()));
        assertEquals(trustExpressionEvaluatorFailureList, trustExpressionEvaluation(trustExpressionEvaluatorFailureList, trustExpression).getTrustExpressionEvaluatorFailureList());
        assertEquals(trustExpression, trustExpressionEvaluation(trustExpressionEvaluatorFailureList, trustExpression).getTrustExpression());

        EqualsVerifier
                .forClass(TrustExpressionEvaluation.class)
                .usingGetClass()
                .withPrefabValues(
                        TrustExpression.class,
                        TrustExpression.terminal(success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true))),
                        and(TrustExpression.terminal(success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true))), TrustExpression.terminal(success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true))), TrustExpression.terminal(success(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true)))))
                .withPrefabValues(List.class, nil(), arrayList(TrustExpressionEvaluatorFailure.evaluatorFailureURI("", new RuntimeException())))
                .withNonnullFields("trustExpressionEvaluatorFailureList", "trustExpression")
                .verify();
    }
}
