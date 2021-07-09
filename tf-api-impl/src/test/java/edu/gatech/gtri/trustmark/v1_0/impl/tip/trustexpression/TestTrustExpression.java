package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.TrustExpressionAnd;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.TrustExpressionBinary;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.TrustExpressionNot;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.TrustExpressionOr;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.TrustExpressionTerminal;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.TrustExpressionUnary;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.product.Unit;
import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorAnd.AND;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorNot.NOT;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorOr.OR;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.function.Function.identity;
import static org.gtri.fj.product.Unit.unit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpression {

    @Test
    public void testGetData() {

        final Object terminal = new Object();
        final Object nonterminal = new Object();

        assertEquals(right(terminal), terminal(terminal).getData());
        assertEquals(left(nonterminal), not(terminal(terminal), nonterminal).getData());
        assertEquals(left(nonterminal), and(terminal(terminal), terminal(terminal), nonterminal).getData());
        assertEquals(left(nonterminal), or(terminal(terminal), terminal(terminal), nonterminal).getData());
    }

    @Test
    public void testAnd() {

        final Object terminalLeft = new Object();
        final Object terminalRight = new Object();
        final TrustExpression<Unit, Object> left = terminal(terminalLeft);
        final TrustExpression<Unit, Object> right = terminal(terminalRight);

        assertThrows(NullPointerException.class, () -> and(null, right, unit()));
        assertThrows(NullPointerException.class, () -> and(left, null, unit()));
        assertThrows(NullPointerException.class, () -> and(left, right, null));
        assertThrows(NullPointerException.class, () -> and(left, right, unit()).match(
                null,
                (a, b, c) -> null,
                (a, b, c, d) -> null));

        assertThrows(NullPointerException.class, () -> and(left, right, unit()).match(
                a -> null,
                null,
                (a, b, c, d) -> null));

        assertThrows(NullPointerException.class, () -> and(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                null));
        assertThrows(NullPointerException.class, () -> and(left, right, unit()).matchBinary(
                null,
                (a, b, c, d) -> null));
        assertThrows(NullPointerException.class, () -> and(left, right, unit()).matchBinary(
                (a, b, c, d) -> null,
                null));

        assertEquals(AND, and(left, right, unit()).getOperator());
        assertEquals(AND, and(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> a));
        assertEquals(AND, and(left, right, unit()).matchBinary(
                (a, b, c, d) -> a,
                (a, b, c, d) -> null));

        assertEquals(left, and(left, right, unit()).getLeft());
        assertEquals(left, and(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> b));
        assertEquals(left, and(left, right, unit()).matchBinary(
                (a, b, c, d) -> b,
                (a, b, c, d) -> null));

        assertEquals(right, and(left, right, unit()).getRight());
        assertEquals(right, and(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> c));
        assertEquals(right, and(left, right, unit()).matchBinary(
                (a, b, c, d) -> c,
                (a, b, c, d) -> null));

        assertTrue(and(left, right, unit()).toString().contains(TrustExpressionBinary.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionAnd.class)
                .withRedefinedSuperclass()
                .withNonnullFields("operator", "left", "right", "data")
                .verify();
    }

    @Test
    public void testOr() {

        final Object terminalLeft = new Object();
        final Object terminalRight = new Object();
        final TrustExpression<Unit, Object> left = terminal(terminalLeft);
        final TrustExpression<Unit, Object> right = terminal(terminalRight);

        assertThrows(NullPointerException.class, () -> or(null, right, unit()));
        assertThrows(NullPointerException.class, () -> or(left, null, unit()));
        assertThrows(NullPointerException.class, () -> or(left, right, null));
        assertThrows(NullPointerException.class, () -> or(left, right, unit()).match(
                null,
                (a, b, c) -> null,
                (a, b, c, d) -> null));

        assertThrows(NullPointerException.class, () -> or(left, right, unit()).match(
                a -> null,
                null,
                (a, b, c, d) -> null));

        assertThrows(NullPointerException.class, () -> or(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                null));
        assertThrows(NullPointerException.class, () -> or(left, right, unit()).matchBinary(
                null,
                (a, b, c, d) -> null));
        assertThrows(NullPointerException.class, () -> or(left, right, unit()).matchBinary(
                (a, b, c, d) -> null,
                null));

        assertEquals(OR, or(left, right, unit()).getOperator());
        assertEquals(OR, or(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> a));
        assertEquals(OR, or(left, right, unit()).matchBinary(
                (a, b, c, d) -> null,
                (a, b, c, d) -> a));

        assertEquals(left, or(left, right, unit()).getLeft());
        assertEquals(left, or(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> b));
        assertEquals(left, or(left, right, unit()).matchBinary(
                (a, b, c, d) -> null,
                (a, b, c, d) -> b));

        assertEquals(right, or(left, right, unit()).getRight());
        assertEquals(right, or(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> c));
        assertEquals(right, or(left, right, unit()).matchBinary(
                (a, b, c, d) -> null,
                (a, b, c, d) -> c));

        assertTrue(or(left, right, unit()).toString().contains(TrustExpressionBinary.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionOr.class)
                .withRedefinedSuperclass()
                .withNonnullFields("operator", "left", "right", "data")
                .verify();
    }

    @Test
    public void testNot() {

        final Object terminal = new Object();
        final TrustExpression<Unit, Object> expression = terminal(terminal);

        assertThrows(NullPointerException.class, () -> not(null, unit()));
        assertThrows(NullPointerException.class, () -> not(expression, null));
        assertThrows(NullPointerException.class, () -> not(expression, unit()).match(
                null,
                (a, b, c) -> null,
                (a, b, c, d) -> null));

        assertThrows(NullPointerException.class, () -> not(expression, unit()).match(
                a -> null,
                null,
                (a, b, c, d) -> null));
        assertThrows(NullPointerException.class, () -> not(expression, unit()).match(
                a -> null,
                (a, b, c) -> null,
                null));

        assertThrows(NullPointerException.class, () -> not(expression, unit()).matchUnary(
                null));

        assertEquals(NOT, not(expression, unit()).getOperator());
        assertEquals(NOT, not(expression, unit()).match(
                a -> null,
                (a, b, c) -> a,
                (a, b, c, d) -> null));
        assertEquals(NOT, not(expression, unit()).matchUnary(
                (a, b, c) -> a));

        assertEquals(expression, not(expression, unit()).getExpression());
        assertEquals(expression, not(expression, unit()).match(
                a -> null,
                (a, b, c) -> b,
                (a, b, c, d) -> null));
        assertEquals(expression, not(expression, unit()).matchUnary(
                (a, b, c) -> b));

        assertTrue(not(expression, unit()).toString().contains(TrustExpressionUnary.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionNot.class)
                .withRedefinedSuperclass()
                .withNonnullFields("operator", "expression", "data")
                .verify();
    }

    @Test
    public void testTerminal() {

        final Object terminal = new Object();

        assertThrows(NullPointerException.class, () -> terminal(null));
        assertThrows(NullPointerException.class, () -> terminal(terminal).match(
                null,
                (a, b, c) -> null,
                (a, b, c, d) -> null));

        assertThrows(NullPointerException.class, () -> terminal(terminal).match(
                a -> null,
                null,
                (a, b, c, d) -> null));

        assertThrows(NullPointerException.class, () -> terminal(terminal).match(
                a -> null,
                (a, b, c) -> null,
                null));

        assertEquals(terminal, terminal(terminal).getDataTerminal());
        assertEquals(terminal, terminal(terminal).match(
                a -> a,
                (a, b, c) -> null,
                (a, b, c, d) -> null));

        assertTrue(terminal(terminal).toString().contains(TrustExpressionTerminal.class.getSimpleName()));

        EqualsVerifier
                .forClass(TrustExpressionTerminal.class)
                .withRedefinedSuperclass()
                .withNonnullFields("data")
                .verify();
    }
}
