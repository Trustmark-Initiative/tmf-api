package edu.gatech.gtri.trustmark.v1_0.tip;

import org.gtri.fj.function.F1;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F4;

import java.util.Objects;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorAnd.OPERATOR_AND;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorBinary;
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
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionOperator.TrustExpressionOperatorUnary;
import static java.util.Objects.requireNonNull;

public abstract class TrustExpression<T0> {

    public abstract T0 getData();

    public abstract <T1> T1 match(
            final F1<T0, T1> fTrustExpressionTerminal,
            final F3<TrustExpressionOperatorUnary, TrustExpression<T0>, T0, T1> fTrustExpressionUnary,
            final F4<TrustExpressionOperatorBinary, TrustExpression<T0>, TrustExpression<T0>, T0, T1> fTrustExpressionBinary);

    public static final class TrustExpressionBinary<T0> extends TrustExpression<T0> {

        private final TrustExpressionOperatorBinary operator;
        private final TrustExpression<T0> left;
        private final TrustExpression<T0> right;
        private final T0 data;

        private TrustExpressionBinary(
                final TrustExpressionOperatorBinary operator,
                final TrustExpression<T0> left,
                final TrustExpression<T0> right,
                final T0 data) {

            requireNonNull(operator);
            requireNonNull(left);
            requireNonNull(right);
            requireNonNull(data);

            this.data = data;
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        public TrustExpressionOperatorBinary getOperator() {
            return operator;
        }

        public TrustExpression<T0> getLeft() {
            return left;
        }

        public TrustExpression<T0> getRight() {
            return right;
        }

        public T0 getData() {
            return data;
        }

        public final <T1> T1 match(
                final F1<T0, T1> fTrustExpressionTerminal,
                final F3<TrustExpressionOperatorUnary, TrustExpression<T0>, T0, T1> fTrustExpressionUnary,
                final F4<TrustExpressionOperatorBinary, TrustExpression<T0>, TrustExpression<T0>, T0, T1> fTrustExpressionBinary) {

            requireNonNull(fTrustExpressionTerminal);
            requireNonNull(fTrustExpressionUnary);
            requireNonNull(fTrustExpressionBinary);

            return fTrustExpressionBinary.f(operator, left, right, getData());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionBinary<?> that = (TrustExpressionBinary<?>) o;
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

    public static final class TrustExpressionUnary<T0> extends TrustExpression<T0> {

        private final TrustExpressionOperatorUnary operator;
        private final TrustExpression<T0> expression;
        private final T0 data;

        private TrustExpressionUnary(
                final TrustExpressionOperatorUnary operator,
                final TrustExpression<T0> expression,
                final T0 data) {

            requireNonNull(operator);
            requireNonNull(expression);
            requireNonNull(data);

            this.data = data;
            this.operator = operator;
            this.expression = expression;
        }

        public TrustExpressionOperatorUnary getOperator() {
            return operator;
        }

        public TrustExpression<T0> getExpression() {
            return expression;
        }

        public T0 getData() {
            return data;
        }

        public final <T1> T1 match(
                final F1<T0, T1> fTrustExpressionTerminal,
                final F3<TrustExpressionOperatorUnary, TrustExpression<T0>, T0, T1> fTrustExpressionUnary,
                final F4<TrustExpressionOperatorBinary, TrustExpression<T0>, TrustExpression<T0>, T0, T1> fTrustExpressionBinary) {

            requireNonNull(fTrustExpressionTerminal);
            requireNonNull(fTrustExpressionUnary);
            requireNonNull(fTrustExpressionBinary);

            return fTrustExpressionUnary.f(operator, expression, getData());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionUnary<?> that = (TrustExpressionUnary<?>) o;
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

    public static final class TrustExpressionTerminal<T0> extends TrustExpression<T0> {

        private final T0 data;

        private TrustExpressionTerminal(
                final T0 data) {

            requireNonNull(data);

            this.data = data;
        }

        public T0 getData() {
            return data;
        }

        public <T1> T1 match(
                final F1<T0, T1> fTrustExpressionTerminal,
                final F3<TrustExpressionOperatorUnary, TrustExpression<T0>, T0, T1> fTrustExpressionUnary,
                final F4<TrustExpressionOperatorBinary, TrustExpression<T0>, TrustExpression<T0>, T0, T1> fTrustExpressionBinary) {

            requireNonNull(fTrustExpressionTerminal);
            requireNonNull(fTrustExpressionUnary);
            requireNonNull(fTrustExpressionBinary);

            return fTrustExpressionTerminal.f(getData());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionTerminal<?> that = (TrustExpressionTerminal<?>) o;
            return data.equals(that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }

        @Override
        public String toString() {
            return "TrustExpressionTerminal{" +
                    "data=" + getData() +
                    '}';
        }
    }

    // @formatter:off
    public static final <T0> TrustExpression<T0> terminal                (                                                                 final T0 data) { return new TrustExpressionTerminal<>(data); }
    public static final <T0> TrustExpressionUnary<T0> noop               (final TrustExpression<T0> expression,                            final T0 data) { return new TrustExpressionUnary<>   (OPERATOR_NOOP,                  expression, data); }
    public static final <T0> TrustExpressionUnary<T0> not                (final TrustExpression<T0> expression,                            final T0 data) { return new TrustExpressionUnary<>   (OPERATOR_NOT,                   expression, data); }
    public static final <T0> TrustExpressionUnary<T0> exists             (final TrustExpression<T0> expression,                            final T0 data) { return new TrustExpressionUnary<>   (OPERATOR_EXISTS,                expression, data); }
    public static final <T0> TrustExpressionBinary<T0> and               (final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_AND,                   left, right, data); }
    public static final <T0> TrustExpressionBinary<T0> or                (final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_OR,                    left, right, data); }
    public static final <T0> TrustExpressionBinary<T0> lessThan          (final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_LESS_THAN,             left, right, data); }
    public static final <T0> TrustExpressionBinary<T0> lessThanOrEqual   (final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_LESS_THAN_OR_EQUAL,    left, right, data); }
    public static final <T0> TrustExpressionBinary<T0> greaterThanOrEqual(final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_GREATER_THAN_OR_EQUAL, left, right, data); }
    public static final <T0> TrustExpressionBinary<T0> greaterThan       (final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_GREATER_THAN,          left, right, data); }
    public static final <T0> TrustExpressionBinary<T0> equal             (final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_EQUAL,                 left, right, data); }
    public static final <T0> TrustExpressionBinary<T0> notEqual          (final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_NOT_EQUAL,             left, right, data); }
    public static final <T0> TrustExpressionBinary<T0> contains          (final TrustExpression<T0> left, final TrustExpression<T0> right, final T0 data) { return new TrustExpressionBinary<>  (OPERATOR_CONTAINS,              left, right, data); }
    // @formatter:on
}
