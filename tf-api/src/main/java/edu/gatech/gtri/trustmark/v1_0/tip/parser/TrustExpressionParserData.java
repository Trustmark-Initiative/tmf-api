package edu.gatech.gtri.trustmark.v1_0.tip.parser;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionTypeOwner;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral.TrustExpressionParserDataLiteralBoolean;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral.TrustExpressionParserDataLiteralDateTimeStamp;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral.TrustExpressionParserDataLiteralDecimal;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral.TrustExpressionParserDataLiteralString;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.TrustExpressionParserDataReference.TrustExpressionParserDataReferenceTrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.TrustExpressionParserDataReference.TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F4;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean.TYPE_BOOLEAN;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDecimal.TYPE_DECIMAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeString.TYPE_STRING;
import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionParserData implements TrustExpressionTypeOwner {

    public abstract NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList();

    public abstract TrustExpressionType getTrustExpressionType();

    public abstract <T1> T1 match(
            final F1<TrustExpressionParserDataLiteral<?>, T1> fTrustExpressionParserDataLiteral,
            final F1<TrustExpressionParserDataReference, T1> fTrustExpressionParserDataReference,
            final F1<TrustExpressionParserDataNonTerminal, T1> fTrustExpressionParserDataNonTerminal);

    public abstract static class TrustExpressionParserDataLiteral<T0> extends TrustExpressionParserData {

        @Override
        public <T1> T1 match(
                final F1<TrustExpressionParserDataLiteral<?>, T1> fTrustExpressionParserDataLiteral,
                final F1<TrustExpressionParserDataReference, T1> fTrustExpressionParserDataReference,
                final F1<TrustExpressionParserDataNonTerminal, T1> fTrustExpressionParserDataNonTerminal) {

            requireNonNull(fTrustExpressionParserDataLiteral);
            requireNonNull(fTrustExpressionParserDataReference);
            requireNonNull(fTrustExpressionParserDataNonTerminal);

            return fTrustExpressionParserDataLiteral.f(this);
        }

        public abstract T0 getValue();

        public abstract <T1> T1 matchLiteral(
                final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Boolean, T1> fTrustExpressionParserDataLiteralBoolean,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Instant, T1> fTrustExpressionParserDataLiteralDateTimeStamp,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, BigDecimal, T1> fTrustExpressionParserDataLiteralDecimal,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, String, T1> fTrustExpressionParserDataLiteralString);

        public static final class TrustExpressionParserDataLiteralBoolean extends TrustExpressionParserDataLiteral<Boolean> {

            private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
            private final TrustExpressionType trustExpressionType = TYPE_BOOLEAN;
            private final boolean value;

            private TrustExpressionParserDataLiteralBoolean(
                    final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                    final boolean value) {

                requireNonNull(trustInteroperabilityProfileNonEmptyList);
                requireNonNull(value);

                this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
                this.value = value;
            }

            @Override
            public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
                return trustInteroperabilityProfileNonEmptyList;
            }

            @Override
            public Boolean getValue() {
                return value;
            }

            public TrustExpressionType getTrustExpressionType() {
                return trustExpressionType;
            }

            @Override
            public <T1> T1 matchLiteral(
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Boolean, T1> fTrustExpressionParserDataLiteralBoolean,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Instant, T1> fTrustExpressionParserDataLiteralDateTimeStamp,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, BigDecimal, T1> fTrustExpressionParserDataLiteralDecimal,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, String, T1> fTrustExpressionParserDataLiteralString) {

                requireNonNull(fTrustExpressionParserDataLiteralBoolean);
                requireNonNull(fTrustExpressionParserDataLiteralDateTimeStamp);
                requireNonNull(fTrustExpressionParserDataLiteralDecimal);
                requireNonNull(fTrustExpressionParserDataLiteralString);

                return fTrustExpressionParserDataLiteralBoolean.f(trustInteroperabilityProfileNonEmptyList, trustExpressionType, value);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionParserDataLiteralBoolean that = (TrustExpressionParserDataLiteralBoolean) o;
                return value == that.value && trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpressionType == that.trustExpressionType;
            }

            @Override
            public int hashCode() {
                return Objects.hash(trustInteroperabilityProfileNonEmptyList, value, trustExpressionType);
            }

            @Override
            public String toString() {
                return "TrustExpressionParserDataLiteralBoolean{" +
                        "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                        ", value=" + value +
                        ", trustExpressionType=" + trustExpressionType +
                        '}';
            }
        }

        public static final class TrustExpressionParserDataLiteralDateTimeStamp extends TrustExpressionParserDataLiteral<Instant> {

            private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
            private final TrustExpressionType trustExpressionType = TYPE_DATE_TIME_STAMP;
            private final Instant value;

            private TrustExpressionParserDataLiteralDateTimeStamp(
                    final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                    final Instant value) {

                requireNonNull(trustInteroperabilityProfileNonEmptyList);
                requireNonNull(value);

                this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
                this.value = value;
            }

            @Override
            public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
                return trustInteroperabilityProfileNonEmptyList;
            }

            @Override
            public Instant getValue() {
                return value;
            }

            @Override

            public TrustExpressionType getTrustExpressionType() {
                return trustExpressionType;
            }

            @Override
            public <T1> T1 matchLiteral(
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Boolean, T1> fTrustExpressionParserDataLiteralBoolean,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Instant, T1> fTrustExpressionParserDataLiteralDateTimeStamp,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, BigDecimal, T1> fTrustExpressionParserDataLiteralDecimal,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, String, T1> fTrustExpressionParserDataLiteralString) {

                requireNonNull(fTrustExpressionParserDataLiteralBoolean);
                requireNonNull(fTrustExpressionParserDataLiteralDateTimeStamp);
                requireNonNull(fTrustExpressionParserDataLiteralDecimal);
                requireNonNull(fTrustExpressionParserDataLiteralString);

                return fTrustExpressionParserDataLiteralDateTimeStamp.f(trustInteroperabilityProfileNonEmptyList, trustExpressionType, value);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionParserDataLiteralDateTimeStamp that = (TrustExpressionParserDataLiteralDateTimeStamp) o;
                return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && value.equals(that.value) && trustExpressionType == that.trustExpressionType;
            }

            @Override
            public int hashCode() {
                return Objects.hash(trustInteroperabilityProfileNonEmptyList, value, trustExpressionType);
            }

            @Override
            public String toString() {
                return "TrustExpressionParserDataLiteralDateTimeStamp{" +
                        "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                        ", value=" + value +
                        ", trustExpressionType=" + trustExpressionType +
                        '}';
            }
        }

        public static final class TrustExpressionParserDataLiteralDecimal extends TrustExpressionParserDataLiteral<BigDecimal> {

            private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
            private final TrustExpressionType trustExpressionType = TYPE_DECIMAL;
            private final BigDecimal value;

            private TrustExpressionParserDataLiteralDecimal(
                    final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                    final BigDecimal value) {

                requireNonNull(trustInteroperabilityProfileNonEmptyList);
                requireNonNull(value);

                this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
                this.value = value;
            }

            @Override
            public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
                return trustInteroperabilityProfileNonEmptyList;
            }

            @Override
            public BigDecimal getValue() {
                return value;
            }

            @Override

            public TrustExpressionType getTrustExpressionType() {
                return trustExpressionType;
            }

            @Override
            public <T1> T1 matchLiteral(
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Boolean, T1> fTrustExpressionParserDataLiteralBoolean,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Instant, T1> fTrustExpressionParserDataLiteralDateTimeStamp,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, BigDecimal, T1> fTrustExpressionParserDataLiteralDecimal,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, String, T1> fTrustExpressionParserDataLiteralString) {

                requireNonNull(fTrustExpressionParserDataLiteralBoolean);
                requireNonNull(fTrustExpressionParserDataLiteralDateTimeStamp);
                requireNonNull(fTrustExpressionParserDataLiteralDecimal);
                requireNonNull(fTrustExpressionParserDataLiteralString);

                return fTrustExpressionParserDataLiteralDecimal.f(trustInteroperabilityProfileNonEmptyList, trustExpressionType, value);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionParserDataLiteralDecimal that = (TrustExpressionParserDataLiteralDecimal) o;
                return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && value.equals(that.value) && trustExpressionType == that.trustExpressionType;
            }

            @Override
            public int hashCode() {
                return Objects.hash(trustInteroperabilityProfileNonEmptyList, value, trustExpressionType);
            }

            @Override
            public String toString() {
                return "TrustExpressionParserDataLiteralDecimal{" +
                        "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                        ", value=" + value +
                        ", trustExpressionType=" + trustExpressionType +
                        '}';
            }
        }

        public static final class TrustExpressionParserDataLiteralString extends TrustExpressionParserDataLiteral<String> {

            private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
            private final TrustExpressionType trustExpressionType = TYPE_STRING;
            private final String value;

            private TrustExpressionParserDataLiteralString(
                    final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                    final String value) {

                requireNonNull(trustInteroperabilityProfileNonEmptyList);
                requireNonNull(value);

                this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
                this.value = value;
            }

            @Override
            public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
                return trustInteroperabilityProfileNonEmptyList;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public TrustExpressionType getTrustExpressionType() {
                return trustExpressionType;
            }

            @Override
            public <T1> T1 matchLiteral(
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Boolean, T1> fTrustExpressionParserDataLiteralBoolean,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, Instant, T1> fTrustExpressionParserDataLiteralDateTimeStamp,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, BigDecimal, T1> fTrustExpressionParserDataLiteralDecimal,
                    final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, String, T1> fTrustExpressionParserDataLiteralString) {

                requireNonNull(fTrustExpressionParserDataLiteralBoolean);
                requireNonNull(fTrustExpressionParserDataLiteralDateTimeStamp);
                requireNonNull(fTrustExpressionParserDataLiteralDecimal);
                requireNonNull(fTrustExpressionParserDataLiteralString);

                return fTrustExpressionParserDataLiteralString.f(trustInteroperabilityProfileNonEmptyList, trustExpressionType, value);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionParserDataLiteralString that = (TrustExpressionParserDataLiteralString) o;
                return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && value.equals(that.value) && trustExpressionType == that.trustExpressionType;
            }

            @Override
            public int hashCode() {
                return Objects.hash(trustInteroperabilityProfileNonEmptyList, value, trustExpressionType);
            }

            @Override
            public String toString() {
                return "TrustExpressionParserDataLiteralString{" +
                        "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                        ", value='" + value + '\'' +
                        ", trustExpressionType=" + trustExpressionType +
                        '}';
            }
        }
    }

    public abstract static class TrustExpressionParserDataReference extends TrustExpressionParserData {

        @Override
        public <T1> T1 match(
                final F1<TrustExpressionParserDataLiteral<?>, T1> fTrustExpressionParserDataLiteral,
                final F1<TrustExpressionParserDataReference, T1> fTrustExpressionParserDataReference,
                final F1<TrustExpressionParserDataNonTerminal, T1> fTrustExpressionParserDataNonTerminal) {

            requireNonNull(fTrustExpressionParserDataLiteral);
            requireNonNull(fTrustExpressionParserDataReference);
            requireNonNull(fTrustExpressionParserDataNonTerminal);

            return fTrustExpressionParserDataReference.f(this);
        }

        public abstract <T1> T1 matchReference(
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustmarkDefinitionRequirement, T1> fTrustExpressionParserDataReferenceTrustmarkDefinitionRequirement,
                F4<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionParserDataReferenceTrustmarkDefinitionParameter);

        public static final class TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement extends TrustExpressionParserDataReference {

            private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
            private final TrustExpressionType trustExpressionType = TYPE_BOOLEAN;
            private final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement;

            private TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement(
                    final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                    final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement) {

                requireNonNull(trustInteroperabilityProfileNonEmptyList);
                requireNonNull(trustmarkDefinitionRequirement);

                this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
                this.trustmarkDefinitionRequirement = trustmarkDefinitionRequirement;
            }

            @Override
            public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
                return trustInteroperabilityProfileNonEmptyList;
            }

            public TrustmarkDefinitionRequirement getTrustmarkDefinitionRequirement() {
                return trustmarkDefinitionRequirement;
            }

            @Override
            public TrustExpressionType getTrustExpressionType() {
                return trustExpressionType;
            }

            @Override
            public <T1> T1 matchReference(
                    F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustmarkDefinitionRequirement, T1> fTrustExpressionParserDataReferenceTrustmarkDefinitionRequirement,
                    F4<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionParserDataReferenceTrustmarkDefinitionParameter) {

                requireNonNull(fTrustExpressionParserDataReferenceTrustmarkDefinitionRequirement);
                requireNonNull(fTrustExpressionParserDataReferenceTrustmarkDefinitionParameter);

                return fTrustExpressionParserDataReferenceTrustmarkDefinitionRequirement.f(trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement that = (TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement) o;
                return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpressionType.equals(that.trustExpressionType) && trustmarkDefinitionRequirement.equals(that.trustmarkDefinitionRequirement);
            }

            @Override
            public int hashCode() {
                return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement);
            }

            @Override
            public String toString() {
                return "TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement{" +
                        "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                        ", trustExpressionType=" + trustExpressionType +
                        ", trustmarkDefinitionRequirement=" + trustmarkDefinitionRequirement +
                        '}';
            }
        }

        public static final class TrustExpressionParserDataReferenceTrustmarkDefinitionParameter extends TrustExpressionParserDataReference {

            private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
            private final TrustExpressionType trustExpressionType;
            private final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement;
            private final TrustmarkDefinitionParameter trustmarkDefinitionParameter;

            private TrustExpressionParserDataReferenceTrustmarkDefinitionParameter(
                    final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                    final TrustExpressionType trustExpressionType,
                    final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
                    final TrustmarkDefinitionParameter trustmarkDefinitionParameter) {

                requireNonNull(trustInteroperabilityProfileNonEmptyList);
                requireNonNull(trustExpressionType);
                requireNonNull(trustmarkDefinitionRequirement);
                requireNonNull(trustmarkDefinitionParameter);

                this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
                this.trustExpressionType = trustExpressionType;
                this.trustmarkDefinitionRequirement = trustmarkDefinitionRequirement;
                this.trustmarkDefinitionParameter = trustmarkDefinitionParameter;
            }

            @Override
            public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
                return trustInteroperabilityProfileNonEmptyList;
            }

            public TrustmarkDefinitionRequirement getTrustmarkDefinitionRequirement() {
                return trustmarkDefinitionRequirement;
            }

            public TrustmarkDefinitionParameter getTrustmarkDefinitionParameter() {
                return trustmarkDefinitionParameter;
            }

            @Override
            public TrustExpressionType getTrustExpressionType() {
                return trustExpressionType;
            }

            @Override
            public <T1> T1 matchReference(
                    F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustmarkDefinitionRequirement, T1> fTrustExpressionParserDataReferenceTrustmarkDefinitionRequirement,
                    F4<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionParserDataReferenceTrustmarkDefinitionParameter) {

                requireNonNull(fTrustExpressionParserDataReferenceTrustmarkDefinitionRequirement);
                requireNonNull(fTrustExpressionParserDataReferenceTrustmarkDefinitionParameter);

                return fTrustExpressionParserDataReferenceTrustmarkDefinitionParameter.f(trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement, trustmarkDefinitionParameter);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionParserDataReferenceTrustmarkDefinitionParameter that = (TrustExpressionParserDataReferenceTrustmarkDefinitionParameter) o;
                return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpressionType.equals(that.trustExpressionType) && trustmarkDefinitionRequirement.equals(that.trustmarkDefinitionRequirement) && trustmarkDefinitionParameter.equals(that.trustmarkDefinitionParameter);
            }

            @Override
            public int hashCode() {
                return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement, trustmarkDefinitionParameter);
            }

            @Override
            public String toString() {
                return "TrustExpressionParserDataReferenceTrustmarkDefinitionParameter{" +
                        "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                        ", trustExpressionType=" + trustExpressionType +
                        ", trustmarkDefinitionRequirement=" + trustmarkDefinitionRequirement +
                        ", trustmarkDefinitionParameter=" + trustmarkDefinitionParameter +
                        '}';
            }
        }
    }

    public static final class TrustExpressionParserDataNonTerminal extends TrustExpressionParserData {

        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustExpressionType trustExpressionType;

        private TrustExpressionParserDataNonTerminal(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustExpressionType trustExpressionType) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustExpressionType);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustExpressionType = trustExpressionType;
        }

        @Override
        public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
            return trustInteroperabilityProfileNonEmptyList;
        }

        @Override
        public TrustExpressionType getTrustExpressionType() {
            return trustExpressionType;
        }

        @Override
        public <T1> T1 match(
                final F1<TrustExpressionParserDataLiteral<?>, T1> fTrustExpressionParserDataLiteral,
                final F1<TrustExpressionParserDataReference, T1> fTrustExpressionParserDataReference,
                final F1<TrustExpressionParserDataNonTerminal, T1> fTrustExpressionParserDataNonTerminal) {

            requireNonNull(fTrustExpressionParserDataLiteral);
            requireNonNull(fTrustExpressionParserDataReference);
            requireNonNull(fTrustExpressionParserDataNonTerminal);

            return fTrustExpressionParserDataNonTerminal.f(this);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionParserDataNonTerminal that = (TrustExpressionParserDataNonTerminal) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpressionType == that.trustExpressionType;
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustExpressionType);
        }

        @Override
        public String toString() {
            return "TrustExpressionParserDataNonTerminal{" +
                    "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                    ", trustExpressionType=" + trustExpressionType +
                    '}';
        }
    }

    public static final TrustExpressionParserDataLiteralBoolean dataLiteralBoolean(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final Boolean value) {

        return new TrustExpressionParserDataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, value);
    }

    public static final TrustExpressionParserDataLiteralDateTimeStamp dataLiteralDateTimeStamp(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final Instant value) {

        return new TrustExpressionParserDataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, value);
    }

    public static final TrustExpressionParserDataLiteralDecimal dataLiteralDecimal(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final BigDecimal value) {

        return new TrustExpressionParserDataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, value);
    }

    public static final TrustExpressionParserDataLiteralString dataLiteralString(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final String value) {

        return new TrustExpressionParserDataLiteralString(trustInteroperabilityProfileNonEmptyList, value);
    }

    public static final TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement dataReferenceTrustmarkDefinitionRequirement(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement) {

        return new TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement);
    }

    public static final TrustExpressionParserDataReferenceTrustmarkDefinitionParameter dataReferenceTrustmarkDefinitionParameter(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustExpressionType trustExpressionType,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter) {

        return new TrustExpressionParserDataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustExpressionType, trustmarkDefinitionRequirement, trustmarkDefinitionParameter);
    }

    public static final TrustExpressionParserDataNonTerminal dataNonTerminal(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustExpressionType trustExpressionType) {

        return new TrustExpressionParserDataNonTerminal(trustInteroperabilityProfileNonEmptyList, trustExpressionType);
    }
}
