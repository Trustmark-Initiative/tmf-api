package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import org.gtri.fj.function.F1;

import static java.util.Objects.requireNonNull;

public enum TrustExpressionState {
    SUCCESS,
    FAILURE,
    UNKNOWN;

    public <T1> T1 match(
            F1<TrustExpressionState, T1> fSuccess,
            F1<TrustExpressionState, T1> fFailure,
            F1<TrustExpressionState, T1> fUnknown,
            F1<TrustExpressionState, T1> f) {
        requireNonNull(fSuccess);
        requireNonNull(fFailure);
        requireNonNull(fUnknown);
        requireNonNull(f);

        return
                this == SUCCESS ? fSuccess.f(this) :
                        this == FAILURE ? fFailure.f(this) :
                                this == UNKNOWN ? fUnknown.f(this) :
                                        f.f(this);
    }

    public TrustExpressionState not() {
        return this.match(
                success -> FAILURE,
                failure -> SUCCESS,
                unknown -> UNKNOWN,
                value -> {
                    throw new RuntimeException("Unexpected trust expression state.");
                });
    }

    public TrustExpressionState and(TrustExpressionState expression) {
        requireNonNull(expression);

        return this.match(
                success -> expression.match(
                        successInner -> SUCCESS,
                        failureInner -> FAILURE,
                        unknownInner -> UNKNOWN,
                        valueInner -> {
                            throw new RuntimeException("Unexpected trust expression state.");
                        }),
                failure -> FAILURE,
                unknown -> expression.match(
                        successInner -> UNKNOWN,
                        failureInner -> FAILURE,
                        unknownInner -> UNKNOWN,
                        valueInner -> {
                            throw new RuntimeException("Unexpected trust expression state.");
                        }),
                value -> {
                    throw new RuntimeException("Unexpected trust expression state.");
                });
    }

    public TrustExpressionState or(final TrustExpressionState expression) {
        requireNonNull(expression);

        return this.match(
                success -> SUCCESS,
                failure -> expression.match(
                        successInner -> SUCCESS,
                        failureInner -> FAILURE,
                        unknownInner -> UNKNOWN,
                        valueInner -> {
                            throw new RuntimeException("Unexpected trust expression state.");
                        }),
                unknown -> expression.match(
                        successInner -> SUCCESS,
                        failureInner -> UNKNOWN,
                        unknownInner -> UNKNOWN,
                        valueInner -> {
                            throw new RuntimeException("Unexpected trust expression state.");
                        }),
                value -> {
                    throw new RuntimeException("Unexpected trust expression state.");
                });
    }
}
