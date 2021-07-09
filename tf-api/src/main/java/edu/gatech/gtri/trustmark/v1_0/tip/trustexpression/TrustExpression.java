package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import org.gtri.fj.data.Either;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F4;

import java.util.Objects;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorAnd;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorAnd.AND;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorBinary;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorNot;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorNot.NOT;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorOr;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorOr.OR;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorUnary;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.Either.right;

public abstract class TrustExpression<NONTERMINAL, TERMINAL> {

    private TrustExpression() {
    }

    public abstract <T1> T1 match(
            final F1<TERMINAL, T1> fTrustExpressionTerminal,
            final F3<TrustExpressionOperatorUnary, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionUnary,
            final F4<TrustExpressionOperatorBinary, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionBinary);

    public Either<NONTERMINAL, TERMINAL> getData() {
        return match(
                (data) -> right(data),
                (operator, expression, data) -> left(data),
                (operator, left, right, data) -> left(data));
    }

    public <T1> T1 getData(
            F1<NONTERMINAL, T1> fNonTerminal,
            F1<TERMINAL, T1> fTerminal) {
        return reduce(getData().bimap(fNonTerminal, fTerminal));
    }

    public static abstract class TrustExpressionBinary<NONTERMINAL, TERMINAL> extends TrustExpression<NONTERMINAL, TERMINAL> {

        private final TrustExpressionOperatorBinary operator;
        private final TrustExpression<NONTERMINAL, TERMINAL> left;
        private final TrustExpression<NONTERMINAL, TERMINAL> right;
        private final NONTERMINAL data;

        private TrustExpressionBinary(
                final TrustExpressionOperatorBinary operator,
                final TrustExpression<NONTERMINAL, TERMINAL> left,
                final TrustExpression<NONTERMINAL, TERMINAL> right,
                final NONTERMINAL data) {

            requireNonNull(operator);
            requireNonNull(left);
            requireNonNull(right);
            requireNonNull(data);

            this.operator = operator;
            this.left = left;
            this.right = right;
            this.data = data;
        }

        public TrustExpressionOperatorBinary getOperator() {
            return operator;
        }

        public TrustExpression<NONTERMINAL, TERMINAL> getLeft() {
            return left;
        }

        public TrustExpression<NONTERMINAL, TERMINAL> getRight() {
            return right;
        }

        public NONTERMINAL getDataNonTerminal() {
            return data;
        }

        public final <T1> T1 match(
                final F1<TERMINAL, T1> fTrustExpressionTerminal,
                final F3<TrustExpressionOperatorUnary, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionUnary,
                final F4<TrustExpressionOperatorBinary, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionBinary) {

            requireNonNull(fTrustExpressionTerminal);
            requireNonNull(fTrustExpressionUnary);
            requireNonNull(fTrustExpressionBinary);

            return fTrustExpressionBinary.f(operator, left, right, data);
        }

        public abstract <T1> T1 matchBinary(
                final F4<TrustExpressionOperatorAnd, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionAnd,
                final F4<TrustExpressionOperatorOr, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionOr);

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionBinary<?, ?> that = (TrustExpressionBinary<?, ?>) o;
            return operator.equals(that.operator) && left.equals(that.left) && right.equals(that.right) && data.equals(that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(operator, left, right, data);
        }

        @Override
        public String toString() {
            return "TrustExpressionBinary{" +
                    "operator=" + operator +
                    ", left=" + left +
                    ", right=" + right +
                    ", data=" + data +
                    '}';
        }
    }

    public static final class TrustExpressionOr<NONTERMINAL, TERMINAL> extends TrustExpressionBinary<NONTERMINAL, TERMINAL> {

        private TrustExpressionOr(
                final TrustExpression<NONTERMINAL, TERMINAL> left,
                final TrustExpression<NONTERMINAL, TERMINAL> right,
                final NONTERMINAL data) {

            super(OR, left, right, data);
        }

        public <T1> T1 matchBinary(
                final F4<TrustExpressionOperatorAnd, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionAnd,
                final F4<TrustExpressionOperatorOr, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionOr) {

            requireNonNull(fTrustExpressionAnd);
            requireNonNull(fTrustExpressionOr);

            return fTrustExpressionOr.f((TrustExpressionOperatorOr) getOperator(), getLeft(), getRight(), getDataNonTerminal());
        }
    }

    public static final class TrustExpressionAnd<NONTERMINAL, TERMINAL> extends TrustExpressionBinary<NONTERMINAL, TERMINAL> {

        private TrustExpressionAnd(
                final TrustExpression<NONTERMINAL, TERMINAL> left,
                final TrustExpression<NONTERMINAL, TERMINAL> right,
                final NONTERMINAL data) {

            super(AND, left, right, data);
        }

        public <T1> T1 matchBinary(
                final F4<TrustExpressionOperatorAnd, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionAnd,
                final F4<TrustExpressionOperatorOr, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionOr) {

            requireNonNull(fTrustExpressionAnd);
            requireNonNull(fTrustExpressionOr);

            return fTrustExpressionAnd.f((TrustExpressionOperatorAnd) getOperator(), getLeft(), getRight(), getDataNonTerminal());
        }
    }

    public static abstract class TrustExpressionUnary<NONTERMINAL, TERMINAL> extends TrustExpression<NONTERMINAL, TERMINAL> {

        private final TrustExpressionOperatorUnary operator;
        private final TrustExpression<NONTERMINAL, TERMINAL> expression;
        private final NONTERMINAL data;

        private TrustExpressionUnary(
                final TrustExpressionOperatorUnary operator,
                final TrustExpression<NONTERMINAL, TERMINAL> expression,
                final NONTERMINAL data) {

            requireNonNull(operator);
            requireNonNull(expression);
            requireNonNull(data);

            this.operator = operator;
            this.expression = expression;
            this.data = data;
        }

        public TrustExpressionOperatorUnary getOperator() {
            return operator;
        }

        public TrustExpression<NONTERMINAL, TERMINAL> getExpression() {
            return expression;
        }

        public NONTERMINAL getDataNonterminal() {
            return data;
        }

        public final <T1> T1 match(
                final F1<TERMINAL, T1> fTrustExpressionTerminal,
                final F3<TrustExpressionOperatorUnary, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionUnary,
                final F4<TrustExpressionOperatorBinary, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionBinary) {

            requireNonNull(fTrustExpressionTerminal);
            requireNonNull(fTrustExpressionUnary);
            requireNonNull(fTrustExpressionBinary);

            return fTrustExpressionUnary.f(operator, expression, data);
        }

        public abstract <T1> T1 matchUnary(
                final F3<TrustExpressionOperatorNot, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionNot);

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionUnary<?, ?> that = (TrustExpressionUnary<?, ?>) o;
            return operator.equals(that.operator) && expression.equals(that.expression) && data.equals(that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(operator, expression, data);
        }

        @Override
        public String toString() {
            return "TrustExpressionUnary{" +
                    "operator=" + operator +
                    ", expression=" + expression +
                    ", data=" + data +
                    '}';
        }
    }

    public static final class TrustExpressionNot<NONTERMINAL, TERMINAL> extends TrustExpressionUnary<NONTERMINAL, TERMINAL> {

        private TrustExpressionNot(
                final TrustExpression<NONTERMINAL, TERMINAL> expression,
                final NONTERMINAL data) {

            super(NOT, expression, data);
        }

        public <T1> T1 matchUnary(
                final F3<TrustExpressionOperatorNot, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionNot) {

            requireNonNull(fTrustExpressionNot);

            return fTrustExpressionNot.f((TrustExpressionOperatorNot) getOperator(), getExpression(), getDataNonterminal());
        }
    }

    public static final class TrustExpressionTerminal<NONTERMINAL, TERMINAL> extends TrustExpression<NONTERMINAL, TERMINAL> {
        private final TERMINAL data;

        private TrustExpressionTerminal(
                final TERMINAL data) {

            requireNonNull(data);

            this.data = data;
        }

        public TERMINAL getDataTerminal() {
            return data;
        }

        public <T1> T1 match(
                final F1<TERMINAL, T1> fTrustExpressionTerminal,
                final F3<TrustExpressionOperatorUnary, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionUnary,
                final F4<TrustExpressionOperatorBinary, TrustExpression<NONTERMINAL, TERMINAL>, TrustExpression<NONTERMINAL, TERMINAL>, NONTERMINAL, T1> fTrustExpressionBinary) {

            requireNonNull(fTrustExpressionTerminal);
            requireNonNull(fTrustExpressionUnary);
            requireNonNull(fTrustExpressionBinary);

            return fTrustExpressionTerminal.f(data);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionTerminal<?, ?> that = (TrustExpressionTerminal<?, ?>) o;
            return data.equals(that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }

        @Override
        public String toString() {
            return "TrustExpressionTerminal{" +
                    "data=" + data +
                    '}';
        }
    }

    // @formatter:off
    public static final <NONTERMINAL, TERMINAL> TrustExpressionTerminal<NONTERMINAL, TERMINAL>   terminal(                                                                                                       final TERMINAL    data) { return new TrustExpressionTerminal<>(data); }
    public static final <NONTERMINAL, TERMINAL> TrustExpressionNot<NONTERMINAL, TERMINAL>        not     (final TrustExpression<NONTERMINAL, TERMINAL> expression,                                               final NONTERMINAL data) { return new TrustExpressionNot<>     (expression, data); }
    public static final <NONTERMINAL, TERMINAL> TrustExpressionAnd<NONTERMINAL, TERMINAL>        and     (final TrustExpression<NONTERMINAL, TERMINAL> left, final TrustExpression<NONTERMINAL, TERMINAL> right, final NONTERMINAL data) { return new TrustExpressionAnd<>     (left, right, data); }
    public static final <NONTERMINAL, TERMINAL> TrustExpressionOr<NONTERMINAL, TERMINAL>         or      (final TrustExpression<NONTERMINAL, TERMINAL> left, final TrustExpression<NONTERMINAL, TERMINAL> right, final NONTERMINAL data) { return new TrustExpressionOr<>      (left, right, data); }
    // @formatter:on
}
