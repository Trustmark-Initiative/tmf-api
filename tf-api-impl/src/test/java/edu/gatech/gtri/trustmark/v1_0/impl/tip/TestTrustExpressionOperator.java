package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorAnd.OPERATOR_AND;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorContains.OPERATOR_CONTAINS;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorEqual.OPERATOR_EQUAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorExists.OPERATOR_EXISTS;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorGreaterThan.OPERATOR_GREATER_THAN;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorGreaterThanOrEqual.OPERATOR_GREATER_THAN_OR_EQUAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorLessThan.OPERATOR_LESS_THAN;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorLessThanOrEqual.OPERATOR_LESS_THAN_OR_EQUAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorNoop.OPERATOR_NOOP;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorNot.OPERATOR_NOT;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorNotEqual.OPERATOR_NOT_EQUAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorOr.OPERATOR_OR;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpressionOperator {

    @Test
    public void testMatchWithUnary() {

        assertThrows(NullPointerException.class, () -> OPERATOR_NOT.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_EXISTS.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_AND.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_OR.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_LESS_THAN.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_LESS_THAN_OR_EQUAL.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_GREATER_THAN_OR_EQUAL.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_GREATER_THAN.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_EQUAL.match(
                null,
                ignore -> null));

        assertThrows(NullPointerException.class, () -> OPERATOR_NOT_EQUAL.match(
                null,
                ignore -> null));

        assertEquals(true, OPERATOR_NOT.match(
                ignore -> true,
                ignore -> false));

        assertEquals(true, OPERATOR_EXISTS.match(
                ignore -> true,
                ignore -> false));

        assertEquals(true, OPERATOR_AND.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_OR.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_LESS_THAN.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_LESS_THAN_OR_EQUAL.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_GREATER_THAN_OR_EQUAL.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_GREATER_THAN.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_EQUAL.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_NOT_EQUAL.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_CONTAINS.match(
                ignore -> false,
                ignore -> true));
    }

    @Test
    public void testMatchWithBinary() {

        assertThrows(NullPointerException.class, () -> OPERATOR_NOT.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_EXISTS.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_AND.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_OR.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_LESS_THAN.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_LESS_THAN_OR_EQUAL.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_GREATER_THAN_OR_EQUAL.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_GREATER_THAN.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_EQUAL.match(
                ignore -> null,
                null));

        assertThrows(NullPointerException.class, () -> OPERATOR_NOT_EQUAL.match(
                ignore -> null,
                null));

        assertEquals(true, OPERATOR_NOT.match(
                ignore -> true,
                ignore -> false));

        assertEquals(true, OPERATOR_EXISTS.match(
                ignore -> true,
                ignore -> false));

        assertEquals(true, OPERATOR_AND.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_OR.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_LESS_THAN.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_LESS_THAN_OR_EQUAL.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_GREATER_THAN_OR_EQUAL.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_GREATER_THAN.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_EQUAL.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_NOT_EQUAL.match(
                ignore -> false,
                ignore -> true));

        assertEquals(true, OPERATOR_CONTAINS.match(
                ignore -> false,
                ignore -> true));
    }

    @Test
    public void testMatchUnary() {

        assertEquals(true, OPERATOR_NOOP.matchUnary(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertEquals(true, OPERATOR_NOT.matchUnary(
                ignore -> false,
                ignore -> true,
                ignore -> false));

        assertEquals(true, OPERATOR_EXISTS.matchUnary(
                ignore -> false,
                ignore -> false,
                ignore -> true));
    }

    @Test
    public void testMatchBinary() {

        assertEquals(true, OPERATOR_AND.matchBinary(
                ignore -> true,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false));

        assertEquals(true, OPERATOR_OR.matchBinary(
                ignore -> false,
                ignore -> true,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false));

        assertEquals(true, OPERATOR_LESS_THAN.matchBinary(
                ignore -> false,
                ignore -> false,
                ignore -> true,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false));

        assertEquals(true, OPERATOR_LESS_THAN_OR_EQUAL.matchBinary(
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> true,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false));

        assertEquals(true, OPERATOR_GREATER_THAN_OR_EQUAL.matchBinary(
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> true,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false));

        assertEquals(true, OPERATOR_GREATER_THAN.matchBinary(
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> true,
                ignore -> false,
                ignore -> false,
                ignore -> false));

        assertEquals(true, OPERATOR_EQUAL.matchBinary(
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertEquals(true, OPERATOR_NOT_EQUAL.matchBinary(
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> true,
                ignore -> false));

        assertEquals(true, OPERATOR_CONTAINS.matchBinary(
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> true));

    }

    @Test
    public void testToString() {

        assertTrue(OPERATOR_NOT.toString().contains(OPERATOR_NOT.getClass().getSimpleName()));
        assertTrue(OPERATOR_EXISTS.toString().contains(OPERATOR_EXISTS.getClass().getSimpleName()));
        assertTrue(OPERATOR_AND.toString().contains(OPERATOR_AND.getClass().getSimpleName()));
        assertTrue(OPERATOR_OR.toString().contains(OPERATOR_OR.getClass().getSimpleName()));
        assertTrue(OPERATOR_LESS_THAN.toString().contains(OPERATOR_LESS_THAN.getClass().getSimpleName()));
        assertTrue(OPERATOR_LESS_THAN_OR_EQUAL.toString().contains(OPERATOR_LESS_THAN_OR_EQUAL.getClass().getSimpleName()));
        assertTrue(OPERATOR_GREATER_THAN_OR_EQUAL.toString().contains(OPERATOR_GREATER_THAN_OR_EQUAL.getClass().getSimpleName()));
        assertTrue(OPERATOR_GREATER_THAN.toString().contains(OPERATOR_GREATER_THAN.getClass().getSimpleName()));
        assertTrue(OPERATOR_EQUAL.toString().contains(OPERATOR_EQUAL.getClass().getSimpleName()));
        assertTrue(OPERATOR_NOT_EQUAL.toString().contains(OPERATOR_NOT_EQUAL.getClass().getSimpleName()));
        assertTrue(OPERATOR_CONTAINS.toString().contains(OPERATOR_CONTAINS.getClass().getSimpleName()));
    }
}
