package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureResolve;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureURI;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpressionEvaluatorFailure {

    @Test
    public void testEvaluatorFailureURI() {
        final String uriString = "";
        final RuntimeException runtimeException = new RuntimeException();

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(null, runtimeException));
        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, runtimeException).match(
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, runtimeException).match(
                (a, b) -> null,
                null));

        assertEquals(uriString, TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, runtimeException).getUriString());
        assertEquals(uriString, TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, runtimeException).match(
                (a, b) -> a,
                (a, b) -> null));

        assertEquals(runtimeException, TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, runtimeException).getException());
        assertEquals(runtimeException, TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, runtimeException).match(
                (a, b) -> b,
                (a, b) -> null));

        assertTrue(TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, runtimeException).toString().contains(TrustExpressionEvaluatorFailureURI.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorFailureURI.class)
                .withNonnullFields("uriString", "exception")
                .verify();
    }

    @Test
    public void testEvaluatorFailureResolve() {
        final URI uri = URI.create("");
        final ResolveException resolveException = new ResolveException();

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(null, resolveException));
        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                (a, b) -> null,
                null));

        assertEquals(uri, TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).getUri());
        assertEquals(uri, TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                (a, b) -> null,
                (a, b) -> a));

        assertEquals(resolveException, TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).getException());
        assertEquals(resolveException, TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                (a, b) -> null,
                (a, b) -> b));

        assertTrue(TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).toString().contains(TrustExpressionEvaluatorFailureResolve.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorFailureResolve.class)
                .withNonnullFields("uri", "exception")
                .verify();
    }
}
