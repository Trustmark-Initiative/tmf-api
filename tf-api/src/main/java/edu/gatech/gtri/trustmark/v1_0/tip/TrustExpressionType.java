package edu.gatech.gtri.trustmark.v1_0.tip;

import org.gtri.fj.function.F1;

import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionType {

    private TrustExpressionType() {
    }

    public abstract <T1> T1 match(
            F1<TrustExpressionType, T1> fTrustExpressionTypeBoolean,
            F1<TrustExpressionType, T1> fTrustExpressionTypeDateTimeStamp,
            F1<TrustExpressionType, T1> fTrustExpressionTypeDecimal,
            F1<TrustExpressionType, T1> fTrustExpressionTypeString,
            F1<TrustExpressionType, T1> fTrustExpressionTypeStringList,
            F1<TrustExpressionType, T1> fTrustExpressionTypeNone);

    public static final class TrustExpressionTypeBoolean extends TrustExpressionType {

        public static final TrustExpressionTypeBoolean TYPE_BOOLEAN = new TrustExpressionTypeBoolean();

        public <T1> T1 match(
                F1<TrustExpressionType, T1> fTrustExpressionTypeBoolean,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDateTimeStamp,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDecimal,
                F1<TrustExpressionType, T1> fTrustExpressionTypeString,
                F1<TrustExpressionType, T1> fTrustExpressionTypeStringList,
                F1<TrustExpressionType, T1> fTrustExpressionTypeNone) {

            requireNonNull(fTrustExpressionTypeBoolean);
            requireNonNull(fTrustExpressionTypeDateTimeStamp);
            requireNonNull(fTrustExpressionTypeDecimal);
            requireNonNull(fTrustExpressionTypeString);
            requireNonNull(fTrustExpressionTypeStringList);
            requireNonNull(fTrustExpressionTypeNone);

            return fTrustExpressionTypeBoolean.f(this);
        }
    }

    public static final class TrustExpressionTypeDateTimeStamp extends TrustExpressionType {

        public static final TrustExpressionTypeDateTimeStamp TYPE_DATE_TIME_STAMP = new TrustExpressionTypeDateTimeStamp();

        public <T1> T1 match(
                F1<TrustExpressionType, T1> fTrustExpressionTypeBoolean,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDateTimeStamp,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDecimal,
                F1<TrustExpressionType, T1> fTrustExpressionTypeString,
                F1<TrustExpressionType, T1> fTrustExpressionTypeStringList,
                F1<TrustExpressionType, T1> fTrustExpressionTypeNone) {

            requireNonNull(fTrustExpressionTypeBoolean);
            requireNonNull(fTrustExpressionTypeDateTimeStamp);
            requireNonNull(fTrustExpressionTypeDecimal);
            requireNonNull(fTrustExpressionTypeString);
            requireNonNull(fTrustExpressionTypeStringList);
            requireNonNull(fTrustExpressionTypeNone);

            return fTrustExpressionTypeDateTimeStamp.f(this);
        }
    }

    public static final class TrustExpressionTypeDecimal extends TrustExpressionType {

        public static final TrustExpressionTypeDecimal TYPE_DECIMAL = new TrustExpressionTypeDecimal();

        public <T1> T1 match(
                F1<TrustExpressionType, T1> fTrustExpressionTypeBoolean,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDateTimeStamp,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDecimal,
                F1<TrustExpressionType, T1> fTrustExpressionTypeString,
                F1<TrustExpressionType, T1> fTrustExpressionTypeStringList,
                F1<TrustExpressionType, T1> fTrustExpressionTypeNone) {

            requireNonNull(fTrustExpressionTypeBoolean);
            requireNonNull(fTrustExpressionTypeDateTimeStamp);
            requireNonNull(fTrustExpressionTypeDecimal);
            requireNonNull(fTrustExpressionTypeString);
            requireNonNull(fTrustExpressionTypeStringList);
            requireNonNull(fTrustExpressionTypeNone);

            return fTrustExpressionTypeDecimal.f(this);
        }
    }

    public static final class TrustExpressionTypeString extends TrustExpressionType {

        public static final TrustExpressionTypeString TYPE_STRING = new TrustExpressionTypeString();

        public <T1> T1 match(
                F1<TrustExpressionType, T1> fTrustExpressionTypeBoolean,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDateTimeStamp,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDecimal,
                F1<TrustExpressionType, T1> fTrustExpressionTypeString,
                F1<TrustExpressionType, T1> fTrustExpressionTypeStringList,
                F1<TrustExpressionType, T1> fTrustExpressionTypeNone) {

            requireNonNull(fTrustExpressionTypeBoolean);
            requireNonNull(fTrustExpressionTypeDateTimeStamp);
            requireNonNull(fTrustExpressionTypeDecimal);
            requireNonNull(fTrustExpressionTypeString);
            requireNonNull(fTrustExpressionTypeStringList);
            requireNonNull(fTrustExpressionTypeNone);

            return fTrustExpressionTypeString.f(this);
        }
    }

    public static final class TrustExpressionTypeStringList extends TrustExpressionType {

        public static final TrustExpressionTypeStringList TYPE_STRING_LIST = new TrustExpressionTypeStringList();

        public <T1> T1 match(
                F1<TrustExpressionType, T1> fTrustExpressionTypeBoolean,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDateTimeStamp,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDecimal,
                F1<TrustExpressionType, T1> fTrustExpressionTypeString,
                F1<TrustExpressionType, T1> fTrustExpressionTypeStringList,
                F1<TrustExpressionType, T1> fTrustExpressionTypeNone) {

            requireNonNull(fTrustExpressionTypeBoolean);
            requireNonNull(fTrustExpressionTypeDateTimeStamp);
            requireNonNull(fTrustExpressionTypeDecimal);
            requireNonNull(fTrustExpressionTypeString);
            requireNonNull(fTrustExpressionTypeStringList);
            requireNonNull(fTrustExpressionTypeNone);

            return fTrustExpressionTypeStringList.f(this);
        }
    }

    public static final class TrustExpressionTypeNone extends TrustExpressionType {

        public static final TrustExpressionTypeNone TYPE_NONE = new TrustExpressionTypeNone();

        public <T1> T1 match(
                F1<TrustExpressionType, T1> fTrustExpressionTypeBoolean,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDateTimeStamp,
                F1<TrustExpressionType, T1> fTrustExpressionTypeDecimal,
                F1<TrustExpressionType, T1> fTrustExpressionTypeString,
                F1<TrustExpressionType, T1> fTrustExpressionTypeStringList,
                F1<TrustExpressionType, T1> fTrustExpressionTypeNone) {

            requireNonNull(fTrustExpressionTypeBoolean);
            requireNonNull(fTrustExpressionTypeDateTimeStamp);
            requireNonNull(fTrustExpressionTypeDecimal);
            requireNonNull(fTrustExpressionTypeString);
            requireNonNull(fTrustExpressionTypeStringList);
            requireNonNull(fTrustExpressionTypeNone);

            return fTrustExpressionTypeNone.f(this);
        }
    }
}
