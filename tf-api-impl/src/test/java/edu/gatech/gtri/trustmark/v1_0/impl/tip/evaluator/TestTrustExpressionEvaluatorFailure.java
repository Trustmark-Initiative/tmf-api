package edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureResolve;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureURI;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.TrustExpressionEvaluatorFailureVerify;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.data.NonEmptyList;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureIdentifier;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpressionEvaluatorFailure {

    @Test
    public void testEvaluatorFailureURI() {
        final String uriString = "";
        final URISyntaxException uriSyntaxException = new URISyntaxException("", "");

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(null, uriSyntaxException));
        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, uriSyntaxException).match(
                null,
                (a, b) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, uriSyntaxException).match(
                (a, b) -> null,
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, uriSyntaxException).match(
                (a, b) -> null,
                (a, b) -> null,
                null));

        assertEquals(uriString, TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, uriSyntaxException).getUriString());
        assertEquals(uriString, TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, uriSyntaxException).match(
                (a, b) -> a,
                (a, b) -> null,
                (a, b) -> null));

        assertEquals(uriSyntaxException, TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, uriSyntaxException).getException());
        assertEquals(uriSyntaxException, TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, uriSyntaxException).match(
                (a, b) -> b,
                (a, b) -> null,
                (a, b) -> null));

        assertTrue(TrustExpressionEvaluatorFailure.evaluatorFailureURI(uriString, uriSyntaxException).toString().contains(TrustExpressionEvaluatorFailureURI.class.getSimpleName()));
    }

    @Test
    public void testEvaluatorFailureResolve() {
        final URI uri = URI.create("");
        final ResolveException resolveException = new ResolveException();

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(null, resolveException));
        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                null,
                (a, b) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                (a, b) -> null,
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                (a, b) -> null,
                (a, b) -> null,
                null));

        assertEquals(uri, TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).getUri());
        assertEquals(uri, TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                (a, b) -> null,
                (a, b) -> a,
                (a, b) -> null));

        assertEquals(resolveException, TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).getException());
        assertEquals(resolveException, TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).match(
                (a, b) -> null,
                (a, b) -> b,
                (a, b) -> null));

        assertTrue(TrustExpressionEvaluatorFailure.evaluatorFailureResolve(uri, resolveException).toString().contains(TrustExpressionEvaluatorFailureResolve.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorFailureResolve.class)
                .withNonnullFields("uri", "exception")
                .verify();
    }

    @Test
    public void testEvaluatorFailureVerify() {
        final Trustmark trustmark = new TrustmarkImpl();
        final NonEmptyList<TrustmarkVerifierFailure> trustmarkVerificationFailureNonEmptyList = nel(failureIdentifier(trustmark));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureVerify(null, trustmarkVerificationFailureNonEmptyList));
        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList).match(
                null,
                (a, b) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList).match(
                (a, b) -> null,
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList).match(
                (a, b) -> null,
                (a, b) -> null,
                null));

        assertEquals(trustmark, TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList).getTrustmark());
        assertEquals(trustmark, TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList).match(
                (a, b) -> null,
                (a, b) -> null,
                (a, b) -> a));

        assertEquals(trustmarkVerificationFailureNonEmptyList, TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList).getTrustmarkVerificationFailureNonEmptyList());
        assertEquals(trustmarkVerificationFailureNonEmptyList, TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList).match(
                (a, b) -> null,
                (a, b) -> null,
                (a, b) -> b));

        assertTrue(TrustExpressionEvaluatorFailure.evaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList).toString().contains(TrustExpressionEvaluatorFailureVerify.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorFailureVerify.class)
                .withPrefabValues(NonEmptyList.class, nel(failureIdentifier(trustmark)), nel(failureIdentifier(trustmark), failureIdentifier(trustmark)))
                .withNonnullFields("trustmark", "trustmarkVerificationFailureNonEmptyList")
                .verify();
    }
}
