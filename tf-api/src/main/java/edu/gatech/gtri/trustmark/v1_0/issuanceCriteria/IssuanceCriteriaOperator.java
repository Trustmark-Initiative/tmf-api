package edu.gatech.gtri.trustmark.v1_0.issuanceCriteria;

import org.gtri.fj.function.F1;

import static java.util.Objects.requireNonNull;

public abstract class IssuanceCriteriaOperator {

    private IssuanceCriteriaOperator() {
    }

    public abstract <T1> T1 match(
            final F1<IssuanceCriteriaOperatorUnary, T1> fIssuanceCriteriaOperatorUnary,
            final F1<IssuanceCriteriaOperatorBinary, T1> fIssuanceCriteriaOperatorBinary);

    public static abstract class IssuanceCriteriaOperatorBinary extends IssuanceCriteriaOperator {

        public <T1> T1 match(
                final F1<IssuanceCriteriaOperatorUnary, T1> fIssuanceCriteriaOperatorUnary,
                final F1<IssuanceCriteriaOperatorBinary, T1> fIssuanceCriteriaOperatorBinary) {

            requireNonNull(fIssuanceCriteriaOperatorUnary);
            requireNonNull(fIssuanceCriteriaOperatorBinary);

            return fIssuanceCriteriaOperatorBinary.f(this);
        }

        public abstract <T1> T1 matchBinary(
                final F1<IssuanceCriteriaOperatorAnd, T1> fIssuanceCriteriaOperatorAnd,
                final F1<IssuanceCriteriaOperatorOr, T1> fIssuanceCriteriaOperatorOr);
    }

    public static final class IssuanceCriteriaOperatorAnd extends IssuanceCriteriaOperatorBinary {

        public static final IssuanceCriteriaOperatorAnd OPERATOR_AND = new IssuanceCriteriaOperatorAnd();

        private IssuanceCriteriaOperatorAnd() {
        }

        public <T1> T1 matchBinary(
                final F1<IssuanceCriteriaOperatorAnd, T1> fIssuanceCriteriaOperatorAnd,
                final F1<IssuanceCriteriaOperatorOr, T1> fIssuanceCriteriaOperatorOr) {

            requireNonNull(fIssuanceCriteriaOperatorAnd);
            requireNonNull(fIssuanceCriteriaOperatorOr);

            return fIssuanceCriteriaOperatorAnd.f(this);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaOperatorAnd{}";
        }
    }

    public static final class IssuanceCriteriaOperatorOr extends IssuanceCriteriaOperatorBinary {

        public static final IssuanceCriteriaOperatorOr OPERATOR_OR = new IssuanceCriteriaOperatorOr();

        private IssuanceCriteriaOperatorOr() {
        }

        public <T1> T1 matchBinary(
                final F1<IssuanceCriteriaOperatorAnd, T1> fIssuanceCriteriaOperatorAnd,
                final F1<IssuanceCriteriaOperatorOr, T1> fIssuanceCriteriaOperatorOr) {

            requireNonNull(fIssuanceCriteriaOperatorAnd);
            requireNonNull(fIssuanceCriteriaOperatorOr);

            return fIssuanceCriteriaOperatorOr.f(this);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaOperatorOr{}";
        }
    }

    public static abstract class IssuanceCriteriaOperatorUnary extends IssuanceCriteriaOperator {

        public <T1> T1 match(
                final F1<IssuanceCriteriaOperatorUnary, T1> fIssuanceCriteriaOperatorUnary,
                final F1<IssuanceCriteriaOperatorBinary, T1> fIssuanceCriteriaOperatorBinary) {

            requireNonNull(fIssuanceCriteriaOperatorUnary);
            requireNonNull(fIssuanceCriteriaOperatorBinary);

            return fIssuanceCriteriaOperatorUnary.f(this);
        }

        public abstract <T1> T1 matchUnary(
                final F1<IssuanceCriteriaOperatorNoop, T1> fIssuanceCriteriaOperatorNoop,
                final F1<IssuanceCriteriaOperatorNot, T1> fIssuanceCriteriaOperatorNot,
                final F1<IssuanceCriteriaOperatorYes, T1> fIssuanceCriteriaOperatorYes,
                final F1<IssuanceCriteriaOperatorNo, T1> fIssuanceCriteriaOperatorNo,
                final F1<IssuanceCriteriaOperatorNa, T1> fIssuanceCriteriaOperatorNa);
    }

    public static final class IssuanceCriteriaOperatorNoop extends IssuanceCriteriaOperatorUnary {

        public static final IssuanceCriteriaOperatorNoop OPERATOR_NOOP = new IssuanceCriteriaOperatorNoop();

        private IssuanceCriteriaOperatorNoop() {
        }

        public <T1> T1 matchUnary(
                final F1<IssuanceCriteriaOperatorNoop, T1> fIssuanceCriteriaOperatorNoop,
                final F1<IssuanceCriteriaOperatorNot, T1> fIssuanceCriteriaOperatorNot,
                final F1<IssuanceCriteriaOperatorYes, T1> fIssuanceCriteriaOperatorYes,
                final F1<IssuanceCriteriaOperatorNo, T1> fIssuanceCriteriaOperatorNo,
                final F1<IssuanceCriteriaOperatorNa, T1> fIssuanceCriteriaOperatorNa) {

            requireNonNull(fIssuanceCriteriaOperatorNoop);
            requireNonNull(fIssuanceCriteriaOperatorNot);
            requireNonNull(fIssuanceCriteriaOperatorYes);
            requireNonNull(fIssuanceCriteriaOperatorNo);
            requireNonNull(fIssuanceCriteriaOperatorNa);

            return fIssuanceCriteriaOperatorNoop.f(this);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaOperatorNoop{}";
        }
    }

    public static final class IssuanceCriteriaOperatorNot extends IssuanceCriteriaOperatorUnary {

        public static final IssuanceCriteriaOperatorNot OPERATOR_NOT = new IssuanceCriteriaOperatorNot();

        private IssuanceCriteriaOperatorNot() {
        }

        public <T1> T1 matchUnary(
                final F1<IssuanceCriteriaOperatorNoop, T1> fIssuanceCriteriaOperatorNoop,
                final F1<IssuanceCriteriaOperatorNot, T1> fIssuanceCriteriaOperatorNot,
                final F1<IssuanceCriteriaOperatorYes, T1> fIssuanceCriteriaOperatorYes,
                final F1<IssuanceCriteriaOperatorNo, T1> fIssuanceCriteriaOperatorNo,
                final F1<IssuanceCriteriaOperatorNa, T1> fIssuanceCriteriaOperatorNa) {

            requireNonNull(fIssuanceCriteriaOperatorNoop);
            requireNonNull(fIssuanceCriteriaOperatorNot);
            requireNonNull(fIssuanceCriteriaOperatorYes);
            requireNonNull(fIssuanceCriteriaOperatorNo);
            requireNonNull(fIssuanceCriteriaOperatorNa);

            return fIssuanceCriteriaOperatorNot.f(this);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaOperatorNot{}";
        }
    }

    public static final class IssuanceCriteriaOperatorYes extends IssuanceCriteriaOperatorUnary {

        public static final IssuanceCriteriaOperatorYes OPERATOR_YES = new IssuanceCriteriaOperatorYes();

        private IssuanceCriteriaOperatorYes() {
        }

        public <T1> T1 matchUnary(
                final F1<IssuanceCriteriaOperatorNoop, T1> fIssuanceCriteriaOperatorNoop,
                final F1<IssuanceCriteriaOperatorNot, T1> fIssuanceCriteriaOperatorNot,
                final F1<IssuanceCriteriaOperatorYes, T1> fIssuanceCriteriaOperatorYes,
                final F1<IssuanceCriteriaOperatorNo, T1> fIssuanceCriteriaOperatorNo,
                final F1<IssuanceCriteriaOperatorNa, T1> fIssuanceCriteriaOperatorNa) {

            requireNonNull(fIssuanceCriteriaOperatorNoop);
            requireNonNull(fIssuanceCriteriaOperatorNot);
            requireNonNull(fIssuanceCriteriaOperatorYes);
            requireNonNull(fIssuanceCriteriaOperatorNo);
            requireNonNull(fIssuanceCriteriaOperatorNa);

            return fIssuanceCriteriaOperatorYes.f(this);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaOperatorYes{}";
        }
    }

    public static final class IssuanceCriteriaOperatorNo extends IssuanceCriteriaOperatorUnary {

        public static final IssuanceCriteriaOperatorNo OPERATOR_NO = new IssuanceCriteriaOperatorNo();

        private IssuanceCriteriaOperatorNo() {
        }

        public <T1> T1 matchUnary(
                final F1<IssuanceCriteriaOperatorNoop, T1> fIssuanceCriteriaOperatorNoop,
                final F1<IssuanceCriteriaOperatorNot, T1> fIssuanceCriteriaOperatorNot,
                final F1<IssuanceCriteriaOperatorYes, T1> fIssuanceCriteriaOperatorYes,
                final F1<IssuanceCriteriaOperatorNo, T1> fIssuanceCriteriaOperatorNo,
                final F1<IssuanceCriteriaOperatorNa, T1> fIssuanceCriteriaOperatorNa) {

            requireNonNull(fIssuanceCriteriaOperatorNoop);
            requireNonNull(fIssuanceCriteriaOperatorNot);
            requireNonNull(fIssuanceCriteriaOperatorYes);
            requireNonNull(fIssuanceCriteriaOperatorNo);
            requireNonNull(fIssuanceCriteriaOperatorNa);

            return fIssuanceCriteriaOperatorNo.f(this);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaOperatorNo{}";
        }
    }

    public static final class IssuanceCriteriaOperatorNa extends IssuanceCriteriaOperatorUnary {

        public static final IssuanceCriteriaOperatorNa OPERATOR_NA = new IssuanceCriteriaOperatorNa();

        private IssuanceCriteriaOperatorNa() {
        }

        public <T1> T1 matchUnary(
                final F1<IssuanceCriteriaOperatorNoop, T1> fIssuanceCriteriaOperatorNoop,
                final F1<IssuanceCriteriaOperatorNot, T1> fIssuanceCriteriaOperatorNot,
                final F1<IssuanceCriteriaOperatorYes, T1> fIssuanceCriteriaOperatorYes,
                final F1<IssuanceCriteriaOperatorNo, T1> fIssuanceCriteriaOperatorNo,
                final F1<IssuanceCriteriaOperatorNa, T1> fIssuanceCriteriaOperatorNa) {

            requireNonNull(fIssuanceCriteriaOperatorNoop);
            requireNonNull(fIssuanceCriteriaOperatorNot);
            requireNonNull(fIssuanceCriteriaOperatorYes);
            requireNonNull(fIssuanceCriteriaOperatorNo);
            requireNonNull(fIssuanceCriteriaOperatorNa);

            return fIssuanceCriteriaOperatorNa.f(this);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaOperatorNa{}";
        }
    }
}
