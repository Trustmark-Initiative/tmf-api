package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.TrustExpressionParserFailureIdentifier;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.TrustExpressionParserFailureParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.TrustExpressionParserFailureResolve;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.TrustExpressionParserFailureURI;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.junit.Test;

import java.net.URI;

import static org.gtri.fj.data.List.list;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.single;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpressionParserFailure {

    @Test
    public void testParserFailureIdentifier() {
        final TrustInteroperabilityProfile trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        final String identifier = "";

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureIdentifier(null, identifier));
        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).match(
                null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).match(
                (a, b, c) -> null,
                null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                null));

        assertEquals(identifier, TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).getIdentifier());
        assertEquals(identifier, TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> b));

        assertEquals(nel(trustInteroperabilityProfile), TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).getTrustInteroperabilityProfileNonEmptyList());
        assertEquals(nel(trustInteroperabilityProfile), TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> a));

        assertTrue(TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), identifier).toString().contains(TrustExpressionParserFailureIdentifier.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionParserFailureIdentifier.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "identifier")
                .verify();
    }

    @Test
    public void testParserFailureURI() {
        final String uriString = "";
        final RuntimeException runtimeException = new RuntimeException();

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureURI(null, uriString, runtimeException));
        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureURI(list(), null, runtimeException));
        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureURI(list(), uriString, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).match(
                null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).match(
                (a, b, c) -> null,
                null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                null));

        assertEquals(list(), TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).getTrustInteroperabilityProfileList());
        assertEquals(list(), TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).match(
                (a, b, c) -> a,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertEquals(uriString, TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).getUriString());
        assertEquals(uriString, TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).match(
                (a, b, c) -> b,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertEquals(runtimeException, TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).getException());
        assertEquals(runtimeException, TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).match(
                (a, b, c) -> c,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertTrue(TrustExpressionParserFailure.parserFailureURI(list(), uriString, runtimeException).toString().contains(TrustExpressionParserFailureURI.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionParserFailureURI.class)
                .withPrefabValues(List.class, nil(), single(new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileList", "uriString", "exception")
                .verify();
    }

    @Test
    public void testParserFailureResolve() {
        final URI uri = URI.create("");
        final ResolveException resolveException = new ResolveException();

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureResolve(null, uri, resolveException));
        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureResolve(list(), null, resolveException));
        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureResolve(list(), uri, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).match(
                null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).match(
                (a, b, c) -> null,
                null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                null));

        assertEquals(list(), TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).getTrustInteroperabilityProfileList());
        assertEquals(list(), TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).match(
                (a, b, c) -> null,
                (a, b, c) -> a,
                (a, b, c) -> null,
                (a, b) -> null));

        assertEquals(uri, TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).getUri());
        assertEquals(uri, TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).match(
                (a, b, c) -> null,
                (a, b, c) -> b,
                (a, b, c) -> null,
                (a, b) -> null));

        assertEquals(resolveException, TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).getException());
        assertEquals(resolveException, TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).match(
                (a, b, c) -> null,
                (a, b, c) -> c,
                (a, b, c) -> null,
                (a, b) -> null));

        assertTrue(TrustExpressionParserFailure.parserFailureResolve(list(), uri, resolveException).toString().contains(TrustExpressionParserFailureResolve.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionParserFailureResolve.class)
                .withPrefabValues(List.class, nil(), single(new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileList", "uri", "exception")
                .verify();
    }

    @Test
    public void testParserFailureParser() {
        final TrustInteroperabilityProfile trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        final String trustExpression = "";
        final RuntimeException runtimeException = new RuntimeException();

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureParser(null, trustExpression, runtimeException));
        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), null, runtimeException));
        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).match(
                null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).match(
                (a, b, c) -> null,
                null,
                (a, b, c) -> null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                null,
                (a, b) -> null));

        assertThrows(NullPointerException.class, () -> TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> null,
                null));

        assertEquals(nel(trustInteroperabilityProfile), TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).getTrustInteroperabilityProfileNonEmptyList());
        assertEquals(nel(trustInteroperabilityProfile), TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> a,
                (a, b) -> null));

        assertEquals(trustExpression, TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).getTrustExpression());
        assertEquals(trustExpression, TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> b,
                (a, b) -> null));

        assertEquals(runtimeException, TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).getException());
        assertEquals(runtimeException, TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).match(
                (a, b, c) -> null,
                (a, b, c) -> null,
                (a, b, c) -> c,
                (a, b) -> null));

        assertTrue(TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), trustExpression, runtimeException).toString().contains(TrustExpressionParserFailureParser.class.getSimpleName()));


        EqualsVerifier
                .forClass(TrustExpressionParserFailureParser.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustExpression", "exception")
                .verify();
    }
}
