package edu.gatech.gtri.trustmark.v1_0.tip;

import org.gtri.fj.function.F1;

import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionOperator {

    private TrustExpressionOperator() {
    }

    public abstract <T1> T1 match(
            final F1<TrustExpressionOperatorUnary, T1> fTrustExpressionOperatorUnary,
            final F1<TrustExpressionOperatorBinary, T1> fTrustExpressionOperatorBinary);

    public static abstract class TrustExpressionOperatorBinary extends TrustExpressionOperator {

        public <T1> T1 match(
                final F1<TrustExpressionOperatorUnary, T1> fTrustExpressionOperatorUnary,
                final F1<TrustExpressionOperatorBinary, T1> fTrustExpressionOperatorBinary) {

            requireNonNull(fTrustExpressionOperatorUnary);
            requireNonNull(fTrustExpressionOperatorBinary);

            return fTrustExpressionOperatorBinary.f(this);
        }

        public abstract <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains);
    }

    public static final class TrustExpressionOperatorAnd extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorAnd OPERATOR_AND = new TrustExpressionOperatorAnd();

        private TrustExpressionOperatorAnd() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorAnd.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorAnd{}";
        }
    }

    public static final class TrustExpressionOperatorOr extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorOr OPERATOR_OR = new TrustExpressionOperatorOr();

        private TrustExpressionOperatorOr() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorOr.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorOr{}";
        }
    }

    public static final class TrustExpressionOperatorLessThan extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorLessThan OPERATOR_LESS_THAN = new TrustExpressionOperatorLessThan();

        private TrustExpressionOperatorLessThan() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorLessThan.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorLessThan{}";
        }
    }

    public static final class TrustExpressionOperatorLessThanOrEqual extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorLessThanOrEqual OPERATOR_LESS_THAN_OR_EQUAL = new TrustExpressionOperatorLessThanOrEqual();

        private TrustExpressionOperatorLessThanOrEqual() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorLessThanOrEqual.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorLessThanOrEqual{}";
        }
    }

    public static final class TrustExpressionOperatorGreaterThanOrEqual extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorGreaterThanOrEqual OPERATOR_GREATER_THAN_OR_EQUAL = new TrustExpressionOperatorGreaterThanOrEqual();

        private TrustExpressionOperatorGreaterThanOrEqual() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorGreaterThanOrEqual.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorGreaterThanOrEqual{}";
        }
    }

    public static final class TrustExpressionOperatorGreaterThan extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorGreaterThan OPERATOR_GREATER_THAN = new TrustExpressionOperatorGreaterThan();

        private TrustExpressionOperatorGreaterThan() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorGreaterThan.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorGreaterThan{}";
        }
    }

    public static final class TrustExpressionOperatorEqual extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorEqual OPERATOR_EQUAL = new TrustExpressionOperatorEqual();

        private TrustExpressionOperatorEqual() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorEqual.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorEqual{}";
        }
    }

    public static final class TrustExpressionOperatorNotEqual extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorNotEqual OPERATOR_NOT_EQUAL = new TrustExpressionOperatorNotEqual();

        private TrustExpressionOperatorNotEqual() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorNotEqual.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorNotEqual{}";
        }
    }

    public static final class TrustExpressionOperatorContains extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorContains OPERATOR_CONTAINS = new TrustExpressionOperatorContains();

        private TrustExpressionOperatorContains() {
        }

        public <T1> T1 matchBinary(
                final F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                final F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr,
                final F1<TrustExpressionOperatorLessThan, T1> fTrustExpressionOperatorLessThan,
                final F1<TrustExpressionOperatorLessThanOrEqual, T1> fTrustExpressionOperatorLessThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThanOrEqual, T1> fTrustExpressionOperatorGreaterThanOrEqual,
                final F1<TrustExpressionOperatorGreaterThan, T1> fTrustExpressionOperatorGreaterThan,
                final F1<TrustExpressionOperatorEqual, T1> fTrustExpressionOperatorEqual,
                final F1<TrustExpressionOperatorNotEqual, T1> fTrustExpressionOperatorNotEqual,
                final F1<TrustExpressionOperatorContains, T1> fTrustExpressionOperatorContains) {

            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);
            requireNonNull(fTrustExpressionOperatorLessThan);
            requireNonNull(fTrustExpressionOperatorLessThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThanOrEqual);
            requireNonNull(fTrustExpressionOperatorGreaterThan);
            requireNonNull(fTrustExpressionOperatorEqual);
            requireNonNull(fTrustExpressionOperatorNotEqual);
            requireNonNull(fTrustExpressionOperatorContains);

            return fTrustExpressionOperatorContains.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorContains{}";
        }
    }

    public static abstract class TrustExpressionOperatorUnary extends TrustExpressionOperator {

        public <T1> T1 match(
                final F1<TrustExpressionOperatorUnary, T1> fTrustExpressionOperatorUnary,
                final F1<TrustExpressionOperatorBinary, T1> fTrustExpressionOperatorBinary) {

            requireNonNull(fTrustExpressionOperatorUnary);
            requireNonNull(fTrustExpressionOperatorBinary);

            return fTrustExpressionOperatorUnary.f(this);
        }

        public abstract <T1> T1 matchUnary(
                final F1<TrustExpressionOperatorNoop, T1> fTrustExpressionOperatorNoop,
                final F1<TrustExpressionOperatorNot, T1> fTrustExpressionOperatorNot,
                final F1<TrustExpressionOperatorExists, T1> fTrustExpressionOperatorExists);
    }

    public static final class TrustExpressionOperatorNoop extends TrustExpressionOperatorUnary {

        public static final TrustExpressionOperatorNoop OPERATOR_NOOP = new TrustExpressionOperatorNoop();

        private TrustExpressionOperatorNoop() {
        }

        public <T1> T1 matchUnary(
                final F1<TrustExpressionOperatorNoop, T1> fTrustExpressionOperatorNoop,
                final F1<TrustExpressionOperatorNot, T1> fTrustExpressionOperatorNot,
                final F1<TrustExpressionOperatorExists, T1> fTrustExpressionOperatorExists) {

            requireNonNull(fTrustExpressionOperatorNoop);
            requireNonNull(fTrustExpressionOperatorNot);
            requireNonNull(fTrustExpressionOperatorExists);

            return fTrustExpressionOperatorNoop.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorNoop{}";
        }
    }

    public static final class TrustExpressionOperatorNot extends TrustExpressionOperatorUnary {

        public static final TrustExpressionOperatorNot OPERATOR_NOT = new TrustExpressionOperatorNot();

        private TrustExpressionOperatorNot() {
        }

        public <T1> T1 matchUnary(
                final F1<TrustExpressionOperatorNoop, T1> fTrustExpressionOperatorNoop,
                final F1<TrustExpressionOperatorNot, T1> fTrustExpressionOperatorNot,
                final F1<TrustExpressionOperatorExists, T1> fTrustExpressionOperatorExists) {

            requireNonNull(fTrustExpressionOperatorNoop);
            requireNonNull(fTrustExpressionOperatorNot);
            requireNonNull(fTrustExpressionOperatorExists);

            return fTrustExpressionOperatorNot.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorNot{}";
        }
    }

    public static final class TrustExpressionOperatorExists extends TrustExpressionOperatorUnary {

        public static final TrustExpressionOperatorExists OPERATOR_EXISTS = new TrustExpressionOperatorExists();

        private TrustExpressionOperatorExists() {
        }

        public <T1> T1 matchUnary(
                final F1<TrustExpressionOperatorNoop, T1> fTrustExpressionOperatorNoop,
                final F1<TrustExpressionOperatorNot, T1> fTrustExpressionOperatorNot,
                final F1<TrustExpressionOperatorExists, T1> fTrustExpressionOperatorExists) {

            requireNonNull(fTrustExpressionOperatorNoop);
            requireNonNull(fTrustExpressionOperatorNot);
            requireNonNull(fTrustExpressionOperatorExists);

            return fTrustExpressionOperatorExists.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorExists{}";
        }
    }
}
