package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import org.gtri.fj.function.F1;

import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionOperator {
    private TrustExpressionOperator() {
    }

    public abstract <T1> T1 match(
            F1<TrustExpressionOperatorUnary, T1> fTrustExpressionOperatorUnary,
            F1<TrustExpressionOperatorBinary, T1> fTrustExpressionOperatorBinary);

    public static abstract class TrustExpressionOperatorBinary extends TrustExpressionOperator {

        public <T1> T1 match(
                F1<TrustExpressionOperatorUnary, T1> fTrustExpressionOperatorUnary,
                F1<TrustExpressionOperatorBinary, T1> fTrustExpressionOperatorBinary) {

            requireNonNull(fTrustExpressionOperatorUnary);
            requireNonNull(fTrustExpressionOperatorBinary);

            return fTrustExpressionOperatorBinary.f(this);
        }

        public abstract <T1> T1 matchBinary(
                F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr);
    }

    public static final class TrustExpressionOperatorAnd extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorAnd AND = new TrustExpressionOperatorAnd();

        private TrustExpressionOperatorAnd() {
        }

        public <T1> T1 matchBinary(
                F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr) {
            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);

            return fTrustExpressionOperatorAnd.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorAnd{}";
        }
    }

    public static final class TrustExpressionOperatorOr extends TrustExpressionOperatorBinary {

        public static final TrustExpressionOperatorOr OR = new TrustExpressionOperatorOr();

        private TrustExpressionOperatorOr() {
        }

        public <T1> T1 matchBinary(
                F1<TrustExpressionOperatorAnd, T1> fTrustExpressionOperatorAnd,
                F1<TrustExpressionOperatorOr, T1> fTrustExpressionOperatorOr) {
            requireNonNull(fTrustExpressionOperatorAnd);
            requireNonNull(fTrustExpressionOperatorOr);

            return fTrustExpressionOperatorOr.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorOr{}";
        }
    }

    public static abstract class TrustExpressionOperatorUnary extends TrustExpressionOperator {

        public <T1> T1 match(
                F1<TrustExpressionOperatorUnary, T1> fTrustExpressionOperatorUnary,
                F1<TrustExpressionOperatorBinary, T1> fTrustExpressionOperatorBinary) {

            requireNonNull(fTrustExpressionOperatorUnary);
            requireNonNull(fTrustExpressionOperatorBinary);

            return fTrustExpressionOperatorUnary.f(this);
        }

        public abstract <T1> T1 matchUnary(
                F1<TrustExpressionOperatorNot, T1> fTrustExpressionOperatorNot);
    }

    public static final class TrustExpressionOperatorNot extends TrustExpressionOperatorUnary {

        public static final TrustExpressionOperatorNot NOT = new TrustExpressionOperatorNot();

        private TrustExpressionOperatorNot() {
        }

        public <T1> T1 matchUnary(
                F1<TrustExpressionOperatorNot, T1> fTrustExpressionOperatorNot) {

            requireNonNull(fTrustExpressionOperatorNot);

            return fTrustExpressionOperatorNot.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionOperatorNot{}";
        }
    }
}
