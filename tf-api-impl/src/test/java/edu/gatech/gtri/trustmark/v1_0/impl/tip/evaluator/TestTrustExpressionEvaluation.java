package edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluation.trustExpressionEvaluation;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueBoolean;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.success;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpressionEvaluation {

    @Test
    public void test() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final List<TrustExpressionEvaluatorFailure> trustExpressionEvaluatorFailureList = arrayList(TrustExpressionEvaluatorFailure.evaluatorFailureURI("", new URISyntaxException("", "")));
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
                .withPrefabValues(List.class, nil(), arrayList(TrustExpressionEvaluatorFailure.evaluatorFailureURI("", new URISyntaxException("", ""))))
                .withNonnullFields("trustExpressionEvaluatorFailureList", "trustExpression")
                .verify();
    }
}
