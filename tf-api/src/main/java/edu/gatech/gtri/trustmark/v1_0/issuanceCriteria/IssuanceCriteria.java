package edu.gatech.gtri.trustmark.v1_0.issuanceCriteria;

import org.gtri.fj.function.F1;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F4;

import java.util.Objects;

import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorAnd.OPERATOR_AND;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorBinary;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorNa.OPERATOR_NA;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorNo.OPERATOR_NO;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorNoop.OPERATOR_NOOP;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorNot.OPERATOR_NOT;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorOr.OPERATOR_OR;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorUnary;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaOperator.IssuanceCriteriaOperatorYes.OPERATOR_YES;
import static java.util.Objects.requireNonNull;

public abstract class IssuanceCriteria<T0> {

    public abstract T0 getData();

    public abstract <T1> T1 match(
            final F1<T0, T1> fIssuanceCriteriaTerminal,
            final F3<IssuanceCriteriaOperatorUnary, IssuanceCriteria<T0>, T0, T1> fIssuanceCriteriaUnary,
            final F4<IssuanceCriteriaOperatorBinary, IssuanceCriteria<T0>, IssuanceCriteria<T0>, T0, T1> fIssuanceCriteriaBinary);

    public static final class IssuanceCriteriaBinary<T0> extends IssuanceCriteria<T0> {

        private final IssuanceCriteriaOperatorBinary operator;
        private final IssuanceCriteria<T0> left;
        private final IssuanceCriteria<T0> right;
        private final T0 data;

        private IssuanceCriteriaBinary(
                final IssuanceCriteriaOperatorBinary operator,
                final IssuanceCriteria<T0> left,
                final IssuanceCriteria<T0> right,
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

        public IssuanceCriteriaOperatorBinary getOperator() {
            return operator;
        }

        public IssuanceCriteria<T0> getLeft() {
            return left;
        }

        public IssuanceCriteria<T0> getRight() {
            return right;
        }

        public T0 getData() {
            return data;
        }

        public final <T1> T1 match(
                final F1<T0, T1> fIssuanceCriteriaTerminal,
                final F3<IssuanceCriteriaOperatorUnary, IssuanceCriteria<T0>, T0, T1> fIssuanceCriteriaUnary,
                final F4<IssuanceCriteriaOperatorBinary, IssuanceCriteria<T0>, IssuanceCriteria<T0>, T0, T1> fIssuanceCriteriaBinary) {

            requireNonNull(fIssuanceCriteriaTerminal);
            requireNonNull(fIssuanceCriteriaUnary);
            requireNonNull(fIssuanceCriteriaBinary);

            return fIssuanceCriteriaBinary.f(operator, left, right, getData());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final IssuanceCriteriaBinary<?> that = (IssuanceCriteriaBinary<?>) o;
            return operator.equals(that.operator) && left.equals(that.left) && right.equals(that.right) && data.equals(that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(operator, left, right, data);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaBinary{" +
                    "operator=" + operator +
                    ", left=" + left +
                    ", right=" + right +
                    ", data=" + data +
                    '}';
        }
    }

    public static final class IssuanceCriteriaUnary<T0> extends IssuanceCriteria<T0> {

        private final IssuanceCriteriaOperatorUnary operator;
        private final IssuanceCriteria<T0> expression;
        private final T0 data;

        private IssuanceCriteriaUnary(
                final IssuanceCriteriaOperatorUnary operator,
                final IssuanceCriteria<T0> expression,
                final T0 data) {

            requireNonNull(operator);
            requireNonNull(expression);
            requireNonNull(data);

            this.data = data;
            this.operator = operator;
            this.expression = expression;
        }

        public IssuanceCriteriaOperatorUnary getOperator() {
            return operator;
        }

        public IssuanceCriteria<T0> getExpression() {
            return expression;
        }

        public T0 getData() {
            return data;
        }

        public final <T1> T1 match(
                final F1<T0, T1> fIssuanceCriteriaTerminal,
                final F3<IssuanceCriteriaOperatorUnary, IssuanceCriteria<T0>, T0, T1> fIssuanceCriteriaUnary,
                final F4<IssuanceCriteriaOperatorBinary, IssuanceCriteria<T0>, IssuanceCriteria<T0>, T0, T1> fIssuanceCriteriaBinary) {

            requireNonNull(fIssuanceCriteriaTerminal);
            requireNonNull(fIssuanceCriteriaUnary);
            requireNonNull(fIssuanceCriteriaBinary);

            return fIssuanceCriteriaUnary.f(operator, expression, getData());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final IssuanceCriteriaUnary<?> that = (IssuanceCriteriaUnary<?>) o;
            return operator.equals(that.operator) && expression.equals(that.expression) && data.equals(that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(operator, expression, data);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaUnary{" +
                    "operator=" + operator +
                    ", expression=" + expression +
                    ", data=" + data +
                    '}';
        }
    }

    public static final class IssuanceCriteriaTerminal<T0> extends IssuanceCriteria<T0> {

        private final T0 data;

        private IssuanceCriteriaTerminal(
                final T0 data) {

            requireNonNull(data);

            this.data = data;
        }

        public T0 getData() {
            return data;
        }

        public <T1> T1 match(
                final F1<T0, T1> fIssuanceCriteriaTerminal,
                final F3<IssuanceCriteriaOperatorUnary, IssuanceCriteria<T0>, T0, T1> fIssuanceCriteriaUnary,
                final F4<IssuanceCriteriaOperatorBinary, IssuanceCriteria<T0>, IssuanceCriteria<T0>, T0, T1> fIssuanceCriteriaBinary) {

            requireNonNull(fIssuanceCriteriaTerminal);
            requireNonNull(fIssuanceCriteriaUnary);
            requireNonNull(fIssuanceCriteriaBinary);

            return fIssuanceCriteriaTerminal.f(getData());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final IssuanceCriteriaTerminal<?> that = (IssuanceCriteriaTerminal<?>) o;
            return data.equals(that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaTerminal{" +
                    "data=" + getData() +
                    '}';
        }
    }

    // @formatter:off
    public static final <T0> IssuanceCriteria<T0> terminal (                                                                   final T0 data) { return new IssuanceCriteriaTerminal<>(data); }
    public static final <T0> IssuanceCriteriaUnary<T0> noop(final IssuanceCriteria<T0> expression,                             final T0 data) { return new IssuanceCriteriaUnary<>   (OPERATOR_NOOP,                  expression, data); }
    public static final <T0> IssuanceCriteriaUnary<T0> not (final IssuanceCriteria<T0> expression,                             final T0 data) { return new IssuanceCriteriaUnary<>   (OPERATOR_NOT,                   expression, data); }
    public static final <T0> IssuanceCriteriaUnary<T0> yes (final IssuanceCriteria<T0> expression,                             final T0 data) { return new IssuanceCriteriaUnary<>   (OPERATOR_YES,                   expression, data); }
    public static final <T0> IssuanceCriteriaUnary<T0> no  (final IssuanceCriteria<T0> expression,                             final T0 data) { return new IssuanceCriteriaUnary<>   (OPERATOR_NO,                    expression, data); }
    public static final <T0> IssuanceCriteriaUnary<T0> na  (final IssuanceCriteria<T0> expression,                             final T0 data) { return new IssuanceCriteriaUnary<>   (OPERATOR_NA,                    expression, data); }
    public static final <T0> IssuanceCriteriaBinary<T0> and(final IssuanceCriteria<T0> left, final IssuanceCriteria<T0> right, final T0 data) { return new IssuanceCriteriaBinary<>  (OPERATOR_AND,                   left, right, data); }
    public static final <T0> IssuanceCriteriaBinary<T0> or (final IssuanceCriteria<T0> left, final IssuanceCriteria<T0> right, final T0 data) { return new IssuanceCriteriaBinary<>  (OPERATOR_OR,                    left, right, data); }
    // @formatter:on
}
