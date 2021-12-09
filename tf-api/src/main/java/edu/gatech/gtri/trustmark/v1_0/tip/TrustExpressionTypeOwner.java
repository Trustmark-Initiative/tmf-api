package edu.gatech.gtri.trustmark.v1_0.tip;

import org.gtri.fj.function.F0;

import static java.util.Objects.requireNonNull;

public interface TrustExpressionTypeOwner {

    TrustExpressionType getTrustExpressionType();

    default <T1> T1 matchType(
            final TrustExpressionTypeOwner right,
            final F0<T1> fTrustExpressionTypeMatch,
            final F0<T1> fTrustExpressionTypeMismatch) {

        requireNonNull(right);
        requireNonNull(fTrustExpressionTypeMatch);
        requireNonNull(fTrustExpressionTypeMismatch);

        return getTrustExpressionType().match(
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMatch.f()));
    }

    default <T1> T1 matchType(
            final TrustExpressionTypeOwner right,
            final F0<T1> fTrustExpressionTypeBoolean,
            final F0<T1> fTrustExpressionTypeDateTimeStamp,
            final F0<T1> fTrustExpressionTypeDecimal,
            final F0<T1> fTrustExpressionTypeString,
            final F0<T1> fTrustExpressionTypeStringList,
            final F0<T1> fTrustExpressionTypeNone,
            final F0<T1> fTrustExpressionTypeMismatch) {

        requireNonNull(right);
        requireNonNull(fTrustExpressionTypeBoolean);
        requireNonNull(fTrustExpressionTypeDateTimeStamp);
        requireNonNull(fTrustExpressionTypeDecimal);
        requireNonNull(fTrustExpressionTypeString);
        requireNonNull(fTrustExpressionTypeStringList);
        requireNonNull(fTrustExpressionTypeNone);
        requireNonNull(fTrustExpressionTypeMismatch);

        return getTrustExpressionType().match(
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeBoolean.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeDateTimeStamp.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeDecimal.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeString.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeStringList.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f()),
                valueLeft -> right.getTrustExpressionType().match(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeNone.f()));
    }
}
