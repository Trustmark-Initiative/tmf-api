package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorAnd.AND;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorNot.NOT;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorOr.OR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpressionOperator {

    @Test
    public void testAnd() {

        assertThrows(NullPointerException.class, () -> AND.match(
                null,
                b -> null));

        assertThrows(NullPointerException.class, () -> AND.match(
                a -> null,
                null));

        assertThrows(NullPointerException.class, () -> AND.matchBinary(
                null,
                b -> null));

        assertThrows(NullPointerException.class, () -> AND.matchBinary(
                a -> null,
                null));

        assertTrue(AND.<Boolean>match(
                a -> false,
                b -> true));

        assertTrue(AND.<Boolean>matchBinary(
                a -> true,
                b -> false));

        assertTrue(AND.toString().contains(AND.getClass().getSimpleName()));
    }

    @Test
    public void testOr() {

        assertThrows(NullPointerException.class, () -> OR.match(
                null,
                b -> null));

        assertThrows(NullPointerException.class, () -> OR.match(
                a -> null,
                null));

        assertThrows(NullPointerException.class, () -> OR.matchBinary(
                null,
                b -> null));

        assertThrows(NullPointerException.class, () -> OR.matchBinary(
                a -> null,
                null));

        assertTrue(OR.<Boolean>match(
                a -> false,
                b -> true));

        assertTrue(OR.<Boolean>matchBinary(
                a -> false,
                b -> true));

        assertTrue(OR.toString().contains(OR.getClass().getSimpleName()));
    }

    @Test
    public void testNot() {

        assertThrows(NullPointerException.class, () -> NOT.match(
                null,
                b -> null));

        assertThrows(NullPointerException.class, () -> NOT.match(
                a -> null,
                null));

        assertThrows(NullPointerException.class, () -> NOT.matchUnary(
                null));

        assertTrue(NOT.<Boolean>match(
                a -> true,
                b -> false));

        assertTrue(NOT.<Boolean>matchUnary(
                a -> true));

        assertTrue(NOT.toString().contains(NOT.getClass().getSimpleName()));
    }
}
