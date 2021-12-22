package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.TrustExpressionBinary;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.TrustExpressionTerminal;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.TrustExpressionUnary;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorAnd.OPERATOR_AND;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorNot.OPERATOR_NOT;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorOr.OPERATOR_OR;
import static org.gtri.fj.product.Unit.unit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpression {

    @Test
    public void testEquals() {

        EqualsVerifier
                .forClass(TrustExpressionBinary.class)
                .withRedefinedSuperclass()
                .withNonnullFields("operator", "left", "right", "data")
                .verify();

        EqualsVerifier
                .forClass(TrustExpressionUnary.class)
                .withRedefinedSuperclass()
                .withNonnullFields("operator", "expression", "data")
                .verify();
    }

    @Test
    public void testGetData() {

        final Object terminal = new Object();
        final Object nonterminal = new Object();

        assertEquals(terminal, terminal(terminal).getData());
        assertEquals(nonterminal, not(terminal(terminal), nonterminal).getData());
        assertEquals(nonterminal, and(terminal(terminal), terminal(terminal), nonterminal).getData());
        assertEquals(nonterminal, or(terminal(terminal), terminal(terminal), nonterminal).getData());
    }

    @Test
    public void testAnd() {

        final Object terminalLeft = new Object();
        final Object terminalRight = new Object();
        final TrustExpression<Object> left = terminal(terminalLeft);
        final TrustExpression<Object> right = terminal(terminalRight);

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

        assertEquals(OPERATOR_AND, and(left, right, unit()).getOperator());
        assertEquals(OPERATOR_AND, and(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> a));

        assertEquals(left, and(left, right, unit()).getLeft());
        assertEquals(left, and(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> b));

        assertEquals(right, and(left, right, unit()).getRight());
        assertEquals(right, and(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> c));

        assertTrue(and(left, right, unit()).toString().contains(TrustExpressionBinary.class.getSimpleName()));
    }

    @Test
    public void testOr() {

        final Object terminalLeft = new Object();
        final Object terminalRight = new Object();
        final TrustExpression<Object> left = terminal(terminalLeft);
        final TrustExpression<Object> right = terminal(terminalRight);

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

        assertEquals(OPERATOR_OR, or(left, right, unit()).getOperator());
        assertEquals(OPERATOR_OR, or(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> a));

        assertEquals(left, or(left, right, unit()).getLeft());
        assertEquals(left, or(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> b));

        assertEquals(right, or(left, right, unit()).getRight());
        assertEquals(right, or(left, right, unit()).match(
                a -> null,
                (a, b, c) -> null,
                (a, b, c, d) -> c));

        assertTrue(or(left, right, unit()).toString().contains(TrustExpressionBinary.class.getSimpleName()));
    }

    @Test
    public void testNot() {

        final Object terminal = new Object();
        final TrustExpression<Object> expression = terminal(terminal);

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

        assertEquals(OPERATOR_NOT, not(expression, unit()).getOperator());
        assertEquals(OPERATOR_NOT, not(expression, unit()).match(
                a -> null,
                (a, b, c) -> a,
                (a, b, c, d) -> null));

        assertEquals(expression, not(expression, unit()).getExpression());
        assertEquals(expression, not(expression, unit()).match(
                a -> null,
                (a, b, c) -> b,
                (a, b, c, d) -> null));

        assertTrue(not(expression, unit()).toString().contains(TrustExpressionUnary.class.getSimpleName()));

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

        assertEquals(terminal, terminal(terminal).getData());
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
