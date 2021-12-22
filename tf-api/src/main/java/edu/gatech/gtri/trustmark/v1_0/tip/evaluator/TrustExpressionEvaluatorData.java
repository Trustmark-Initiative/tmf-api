package edu.gatech.gtri.trustmark.v1_0.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionTypeOwner;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F0;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean.TYPE_BOOLEAN;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDecimal.TYPE_DECIMAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeNone.TYPE_NONE;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeString.TYPE_STRING;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeStringList.TYPE_STRING_LIST;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorSource.source;
import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionEvaluatorData implements TrustExpressionTypeOwner {

    public <T1> T1 matchValueBoolean(
            final F1<Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
            final F0<T1> fTrustExpressionEvaluatorDataValueOther) {

        requireNonNull(fTrustExpressionEvaluatorDataValueBoolean);
        requireNonNull(fTrustExpressionEvaluatorDataValueOther);

        return matchValue(
                fTrustExpressionEvaluatorDataValueBoolean,
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueOther);
    }

    public <T1> T1 matchValueDateTimeStamp(
            final F1<Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
            final F0<T1> fTrustExpressionEvaluatorDataValueOther) {

        requireNonNull(fTrustExpressionEvaluatorDataValueDateTimeStamp);
        requireNonNull(fTrustExpressionEvaluatorDataValueOther);

        return matchValue(
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueDateTimeStamp,
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueOther);
    }

    public <T1> T1 matchValueDecimal(
            final F1<BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
            final F0<T1> fTrustExpressionEvaluatorDataValueOther) {

        requireNonNull(fTrustExpressionEvaluatorDataValueDecimal);
        requireNonNull(fTrustExpressionEvaluatorDataValueOther);

        return matchValue(
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueDecimal,
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueOther);
    }

    public <T1> T1 matchValueString(
            final F1<String, T1> fTrustExpressionEvaluatorDataValueString,
            final F0<T1> fTrustExpressionEvaluatorDataValueOther) {

        requireNonNull(fTrustExpressionEvaluatorDataValueString);
        requireNonNull(fTrustExpressionEvaluatorDataValueOther);

        return matchValue(
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueString,
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueOther);
    }

    public <T1> T1 matchValueStringList(
            final F1<List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
            final F0<T1> fTrustExpressionEvaluatorDataValueOther) {

        requireNonNull(fTrustExpressionEvaluatorDataValueStringList);
        requireNonNull(fTrustExpressionEvaluatorDataValueOther);

        return matchValue(
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueStringList,
                fTrustExpressionEvaluatorDataValueOther);
    }

    public <T1> T1 matchValueNone(
            final F0<T1> fTrustExpressionEvaluatorDataValueNone,
            final F0<T1> fTrustExpressionEvaluatorDataValueOther) {

        requireNonNull(fTrustExpressionEvaluatorDataValueNone);
        requireNonNull(fTrustExpressionEvaluatorDataValueOther);

        return matchValue(
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                value -> fTrustExpressionEvaluatorDataValueOther.f(),
                fTrustExpressionEvaluatorDataValueNone);
    }

    public <T1> T1 matchValue(
            final F1<Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
            final F1<Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
            final F1<BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
            final F1<String, T1> fTrustExpressionEvaluatorDataValueString,
            final F1<List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
            final F0<T1> fTrustExpressionEvaluatorDataValueNone) {

        requireNonNull(fTrustExpressionEvaluatorDataValueBoolean);
        requireNonNull(fTrustExpressionEvaluatorDataValueDateTimeStamp);
        requireNonNull(fTrustExpressionEvaluatorDataValueDecimal);
        requireNonNull(fTrustExpressionEvaluatorDataValueString);
        requireNonNull(fTrustExpressionEvaluatorDataValueStringList);
        requireNonNull(fTrustExpressionEvaluatorDataValueNone);

        return match(
                (source, type, value) -> fTrustExpressionEvaluatorDataValueBoolean.f(value),
                (source, type, value) -> fTrustExpressionEvaluatorDataValueDateTimeStamp.f(value),
                (source, type, value) -> fTrustExpressionEvaluatorDataValueDecimal.f(value),
                (source, type, value) -> fTrustExpressionEvaluatorDataValueString.f(value),
                (source, type, value) -> fTrustExpressionEvaluatorDataValueStringList.f(value),
                (source, type) -> fTrustExpressionEvaluatorDataValueNone.f());
    }

    public <T1> T1 matchValue(
            final TrustExpressionEvaluatorData right,
            final F2<Boolean, Boolean, T1> fTrustExpressionTypeBoolean,
            final F2<Instant, Instant, T1> fTrustExpressionTypeDateTimeStamp,
            final F2<BigDecimal, BigDecimal, T1> fTrustExpressionTypeDecimal,
            final F2<String, String, T1> fTrustExpressionTypeString,
            final F2<List<String>, List<String>, T1> fTrustExpressionTypeStringList,
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

        return matchValue(
                valueLeft -> right.matchValue(
                        valueRight -> fTrustExpressionTypeBoolean.f(valueLeft, valueRight),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        fTrustExpressionTypeMismatch),
                valueLeft -> right.matchValue(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeDateTimeStamp.f(valueLeft, valueRight),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        fTrustExpressionTypeMismatch),
                valueLeft -> right.matchValue(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeDecimal.f(valueLeft, valueRight),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        fTrustExpressionTypeMismatch),
                valueLeft -> right.matchValue(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeString.f(valueLeft, valueRight),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        fTrustExpressionTypeMismatch),
                valueLeft -> right.matchValue(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeStringList.f(valueLeft, valueRight),
                        fTrustExpressionTypeMismatch),
                () -> right.matchValue(
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        valueRight -> fTrustExpressionTypeMismatch.f(),
                        fTrustExpressionTypeNone));
    }

    public abstract <T1> T1 match(
            final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
            final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
            final F3<TrustExpressionEvaluatorSource, TrustExpressionType, BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
            final F3<TrustExpressionEvaluatorSource, TrustExpressionType, String, T1> fTrustExpressionEvaluatorDataValueString,
            final F3<TrustExpressionEvaluatorSource, TrustExpressionType, List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
            final F2<TrustExpressionEvaluatorSource, TrustExpressionType, T1> fTrustExpressionEvaluatorDataValueNone);

    public abstract TrustExpressionType getTrustExpressionType();

    public abstract TrustExpressionEvaluatorSource getSource();

    public static final class TrustExpressionEvaluatorDataValueBoolean extends TrustExpressionEvaluatorData {

        private final TrustExpressionEvaluatorSource source;
        private final boolean value;

        private TrustExpressionEvaluatorDataValueBoolean(
                final TrustExpressionEvaluatorSource source,
                final boolean value) {

            requireNonNull(source);
            requireNonNull(value);

            this.source = source;
            this.value = value;
        }

        public TrustExpressionEvaluatorSource getSource() {
            return source;
        }

        public TrustExpressionType getTrustExpressionType() {
            return TYPE_BOOLEAN;
        }

        public boolean getValue() {
            return value;
        }

        public <T1> T1 match(
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, String, T1> fTrustExpressionEvaluatorDataValueString,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
                final F2<TrustExpressionEvaluatorSource, TrustExpressionType, T1> fTrustExpressionEvaluatorDataValueNone) {

            requireNonNull(fTrustExpressionEvaluatorDataValueBoolean);
            requireNonNull(fTrustExpressionEvaluatorDataValueDateTimeStamp);
            requireNonNull(fTrustExpressionEvaluatorDataValueDecimal);
            requireNonNull(fTrustExpressionEvaluatorDataValueString);
            requireNonNull(fTrustExpressionEvaluatorDataValueStringList);
            requireNonNull(fTrustExpressionEvaluatorDataValueNone);

            return fTrustExpressionEvaluatorDataValueBoolean.f(getSource(), getTrustExpressionType(), getValue());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorDataValueBoolean that = (TrustExpressionEvaluatorDataValueBoolean) o;
            return value == that.value && source.equals(that.source);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, value);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorDataValueBoolean{" +
                    "source=" + source +
                    ", value=" + value +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorDataValueDateTimeStamp extends TrustExpressionEvaluatorData {

        private final TrustExpressionEvaluatorSource source;
        private final Instant value;

        private TrustExpressionEvaluatorDataValueDateTimeStamp(
                final TrustExpressionEvaluatorSource source,
                final Instant value) {

            requireNonNull(source);
            requireNonNull(value);

            this.source = source;
            this.value = value;
        }

        public TrustExpressionEvaluatorSource getSource() {
            return source;
        }

        public TrustExpressionType getTrustExpressionType() {
            return TYPE_DATE_TIME_STAMP;
        }

        public Instant getValue() {
            return value;
        }

        public <T1> T1 match(
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, String, T1> fTrustExpressionEvaluatorDataValueString,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
                final F2<TrustExpressionEvaluatorSource, TrustExpressionType, T1> fTrustExpressionEvaluatorDataValueNone) {

            requireNonNull(fTrustExpressionEvaluatorDataValueBoolean);
            requireNonNull(fTrustExpressionEvaluatorDataValueDateTimeStamp);
            requireNonNull(fTrustExpressionEvaluatorDataValueDecimal);
            requireNonNull(fTrustExpressionEvaluatorDataValueString);
            requireNonNull(fTrustExpressionEvaluatorDataValueStringList);
            requireNonNull(fTrustExpressionEvaluatorDataValueNone);

            return fTrustExpressionEvaluatorDataValueDateTimeStamp.f(getSource(), getTrustExpressionType(), getValue());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorDataValueDateTimeStamp that = (TrustExpressionEvaluatorDataValueDateTimeStamp) o;
            return source.equals(that.source) && value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, value);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorDataValueDateTimeStamp{" +
                    "source=" + source +
                    ", value=" + value +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorDataValueDecimal extends TrustExpressionEvaluatorData {

        private final TrustExpressionEvaluatorSource source;
        private final BigDecimal value;

        private TrustExpressionEvaluatorDataValueDecimal(
                final TrustExpressionEvaluatorSource source,
                final BigDecimal value) {

            requireNonNull(source);
            requireNonNull(value);

            this.source = source;
            this.value = value;
        }

        public TrustExpressionEvaluatorSource getSource() {
            return source;
        }

        public TrustExpressionType getTrustExpressionType() {
            return TYPE_DECIMAL;
        }

        public BigDecimal getValue() {
            return value;
        }

        public <T1> T1 match(
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, String, T1> fTrustExpressionEvaluatorDataValueString,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
                final F2<TrustExpressionEvaluatorSource, TrustExpressionType, T1> fTrustExpressionEvaluatorDataValueNone) {

            requireNonNull(fTrustExpressionEvaluatorDataValueBoolean);
            requireNonNull(fTrustExpressionEvaluatorDataValueDateTimeStamp);
            requireNonNull(fTrustExpressionEvaluatorDataValueDecimal);
            requireNonNull(fTrustExpressionEvaluatorDataValueString);
            requireNonNull(fTrustExpressionEvaluatorDataValueStringList);
            requireNonNull(fTrustExpressionEvaluatorDataValueNone);

            return fTrustExpressionEvaluatorDataValueDecimal.f(getSource(), getTrustExpressionType(), getValue());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorDataValueDecimal that = (TrustExpressionEvaluatorDataValueDecimal) o;
            return source.equals(that.source) && value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, value);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorDataValueDecimal{" +
                    "source=" + source +
                    ", value=" + value +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorDataValueString extends TrustExpressionEvaluatorData {

        private final TrustExpressionEvaluatorSource source;
        private final String value;

        private TrustExpressionEvaluatorDataValueString(
                final TrustExpressionEvaluatorSource source,
                final String value) {

            requireNonNull(source);
            requireNonNull(value);

            this.source = source;
            this.value = value;
        }

        public TrustExpressionEvaluatorSource getSource() {
            return source;
        }

        public TrustExpressionType getTrustExpressionType() {
            return TYPE_STRING;
        }

        public String getValue() {
            return value;
        }

        public <T1> T1 match(
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, String, T1> fTrustExpressionEvaluatorDataValueString,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
                final F2<TrustExpressionEvaluatorSource, TrustExpressionType, T1> fTrustExpressionEvaluatorDataValueNone) {

            requireNonNull(fTrustExpressionEvaluatorDataValueBoolean);
            requireNonNull(fTrustExpressionEvaluatorDataValueDateTimeStamp);
            requireNonNull(fTrustExpressionEvaluatorDataValueDecimal);
            requireNonNull(fTrustExpressionEvaluatorDataValueString);
            requireNonNull(fTrustExpressionEvaluatorDataValueStringList);
            requireNonNull(fTrustExpressionEvaluatorDataValueNone);

            return fTrustExpressionEvaluatorDataValueString.f(getSource(), getTrustExpressionType(), getValue());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorDataValueString that = (TrustExpressionEvaluatorDataValueString) o;
            return source.equals(that.source) && value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, value);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorDataValueString{" +
                    "source=" + source +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorDataValueStringList extends TrustExpressionEvaluatorData {

        private final TrustExpressionEvaluatorSource source;
        private final List<String> value;

        private TrustExpressionEvaluatorDataValueStringList(
                final TrustExpressionEvaluatorSource source,
                final List<String> value) {

            requireNonNull(source);
            requireNonNull(value);

            this.source = source;
            this.value = value;
        }

        public TrustExpressionEvaluatorSource getSource() {
            return source;
        }

        public TrustExpressionType getTrustExpressionType() {
            return TYPE_STRING_LIST;
        }

        public List<String> getValue() {
            return value;
        }

        public <T1> T1 match(
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, String, T1> fTrustExpressionEvaluatorDataValueString,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
                final F2<TrustExpressionEvaluatorSource, TrustExpressionType, T1> fTrustExpressionEvaluatorDataValueNone) {

            requireNonNull(fTrustExpressionEvaluatorDataValueBoolean);
            requireNonNull(fTrustExpressionEvaluatorDataValueDateTimeStamp);
            requireNonNull(fTrustExpressionEvaluatorDataValueDecimal);
            requireNonNull(fTrustExpressionEvaluatorDataValueString);
            requireNonNull(fTrustExpressionEvaluatorDataValueStringList);
            requireNonNull(fTrustExpressionEvaluatorDataValueNone);

            return fTrustExpressionEvaluatorDataValueStringList.f(getSource(), getTrustExpressionType(), getValue());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorDataValueStringList that = (TrustExpressionEvaluatorDataValueStringList) o;
            return source.equals(that.source) && value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, value);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorDataValueStringList{" +
                    "source=" + source +
                    ", value=" + value +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorDataValueNone extends TrustExpressionEvaluatorData {

        private final TrustExpressionEvaluatorSource source;

        private TrustExpressionEvaluatorDataValueNone(
                final TrustExpressionEvaluatorSource source) {

            requireNonNull(source);

            this.source = source;
        }

        public TrustExpressionEvaluatorSource getSource() {
            return source;
        }

        public TrustExpressionType getTrustExpressionType() {
            return TYPE_NONE;
        }

        public <T1> T1 match(
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Boolean, T1> fTrustExpressionEvaluatorDataValueBoolean,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, Instant, T1> fTrustExpressionEvaluatorDataValueDateTimeStamp,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, BigDecimal, T1> fTrustExpressionEvaluatorDataValueDecimal,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, String, T1> fTrustExpressionEvaluatorDataValueString,
                final F3<TrustExpressionEvaluatorSource, TrustExpressionType, List<String>, T1> fTrustExpressionEvaluatorDataValueStringList,
                final F2<TrustExpressionEvaluatorSource, TrustExpressionType, T1> fTrustExpressionEvaluatorDataValueNone) {

            requireNonNull(fTrustExpressionEvaluatorDataValueBoolean);
            requireNonNull(fTrustExpressionEvaluatorDataValueDateTimeStamp);
            requireNonNull(fTrustExpressionEvaluatorDataValueDecimal);
            requireNonNull(fTrustExpressionEvaluatorDataValueString);
            requireNonNull(fTrustExpressionEvaluatorDataValueStringList);
            requireNonNull(fTrustExpressionEvaluatorDataValueNone);

            return fTrustExpressionEvaluatorDataValueNone.f(getSource(), getTrustExpressionType());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorDataValueNone that = (TrustExpressionEvaluatorDataValueNone) o;
            return source.equals(that.source);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorDataValueNone{" +
                    "source=" + source +
                    '}';
        }
    }

    public static final TrustExpressionEvaluatorData dataValueBoolean(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final List<Trustmark> trustmarkList) {

        return new TrustExpressionEvaluatorDataValueBoolean(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList), trustmarkList.isNotEmpty());
    }

    public static final TrustExpressionEvaluatorData dataValueBoolean(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final NonEmptyList<TrustmarkVerifierFailure> trustmarkVerifierFailureList) {

        return new TrustExpressionEvaluatorDataValueBoolean(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkVerifierFailureList), false);
    }

    public static final TrustExpressionEvaluatorData dataValueBoolean(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final NonEmptyList<Trustmark> trustmarkList,
            final boolean value) {

        return new TrustExpressionEvaluatorDataValueBoolean(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueBoolean(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final boolean value) {

        return new TrustExpressionEvaluatorDataValueBoolean(source(trustInteroperabilityProfileNonEmptyList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueDateTimeStamp(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final NonEmptyList<Trustmark> trustmarkList,
            final Instant value) {

        return new TrustExpressionEvaluatorDataValueDateTimeStamp(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueDateTimeStamp(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final Instant value) {

        return new TrustExpressionEvaluatorDataValueDateTimeStamp(source(trustInteroperabilityProfileNonEmptyList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueDecimal(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final NonEmptyList<Trustmark> trustmarkList,
            final BigDecimal value) {

        return new TrustExpressionEvaluatorDataValueDecimal(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueDecimal(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final BigDecimal value) {

        return new TrustExpressionEvaluatorDataValueDecimal(source(trustInteroperabilityProfileNonEmptyList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueString(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final NonEmptyList<Trustmark> trustmarkList,
            final String value) {

        return new TrustExpressionEvaluatorDataValueString(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueString(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final String value) {

        return new TrustExpressionEvaluatorDataValueString(source(trustInteroperabilityProfileNonEmptyList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueStringList(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final NonEmptyList<Trustmark> trustmarkList,
            final List<String> value) {

        return new TrustExpressionEvaluatorDataValueStringList(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueStringList(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final List<String> value) {

        return new TrustExpressionEvaluatorDataValueStringList(source(trustInteroperabilityProfileNonEmptyList), value);
    }

    public static final TrustExpressionEvaluatorData dataValueNone(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final NonEmptyList<Trustmark> trustmarkList) {

        return new TrustExpressionEvaluatorDataValueNone(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkList));
    }

    public static final TrustExpressionEvaluatorData dataValueNone(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return new TrustExpressionEvaluatorDataValueNone(source(trustInteroperabilityProfileNonEmptyList));
    }

}
