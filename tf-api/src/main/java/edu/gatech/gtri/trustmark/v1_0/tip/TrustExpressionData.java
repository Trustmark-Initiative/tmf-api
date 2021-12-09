package edu.gatech.gtri.trustmark.v1_0.tip;

import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.TrustExpressionDataLiteral.TrustExpressionDataLiteralBoolean;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.TrustExpressionDataLiteral.TrustExpressionDataLiteralDateTimeStamp;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.TrustExpressionDataLiteral.TrustExpressionDataLiteralDecimal;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.TrustExpressionDataLiteral.TrustExpressionDataLiteralString;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.TrustExpressionDataReference.TrustExpressionDataReferenceTrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.TrustExpressionDataReference.TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionData {

    public abstract <T1> T1 match(
            final F1<TrustExpressionDataLiteral<?>, T1> fTrustExpressionDataLiteral,
            final F1<TrustExpressionDataReference, T1> fTrustExpressionDataReference,
            final F1<TrustExpressionDataNonTerminal, T1> fTrustExpressionDataNonTerminal);

    public abstract static class TrustExpressionDataLiteral<T0> extends TrustExpressionData {

        public <T1> T1 match(
                final F1<TrustExpressionDataLiteral<?>, T1> fTrustExpressionDataLiteral,
                final F1<TrustExpressionDataReference, T1> fTrustExpressionDataReference,
                final F1<TrustExpressionDataNonTerminal, T1> fTrustExpressionDataNonTerminal) {

            requireNonNull(fTrustExpressionDataLiteral);
            requireNonNull(fTrustExpressionDataReference);
            requireNonNull(fTrustExpressionDataNonTerminal);

            return fTrustExpressionDataLiteral.f(this);
        }

        public abstract T0 getValue();

        public abstract <T1> T1 matchLiteral(
                final F1<Boolean, T1> fTrustExpressionDataLiteralBoolean,
                final F1<Instant, T1> fTrustExpressionDataLiteralDateTimeStamp,
                final F1<BigDecimal, T1> fTrustExpressionDataLiteralDecimal,
                final F1<String, T1> fTrustExpressionDataLiteralString);

        public static final class TrustExpressionDataLiteralBoolean extends TrustExpressionDataLiteral<Boolean> {

            private final boolean value;

            private TrustExpressionDataLiteralBoolean(
                    final boolean value) {

                requireNonNull(value);

                this.value = value;
            }

            public Boolean getValue() {
                return value;
            }

            public <T1> T1 matchLiteral(
                    final F1<Boolean, T1> fTrustExpressionDataLiteralBoolean,
                    final F1<Instant, T1> fTrustExpressionDataLiteralDateTimeStamp,
                    final F1<BigDecimal, T1> fTrustExpressionDataLiteralDecimal,
                    final F1<String, T1> fTrustExpressionDataLiteralString) {

                requireNonNull(fTrustExpressionDataLiteralBoolean);
                requireNonNull(fTrustExpressionDataLiteralDateTimeStamp);
                requireNonNull(fTrustExpressionDataLiteralDecimal);
                requireNonNull(fTrustExpressionDataLiteralString);

                return fTrustExpressionDataLiteralBoolean.f(getValue());
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionDataLiteralBoolean that = (TrustExpressionDataLiteralBoolean) o;
                return value == that.value;
            }

            @Override
            public int hashCode() {
                return Objects.hash(value);
            }

            @Override
            public String toString() {
                return "TrustExpressionDataLiteralBoolean{" +
                        "value=" + value +
                        '}';
            }
        }

        public static final class TrustExpressionDataLiteralDateTimeStamp extends TrustExpressionDataLiteral<Instant> {

            private final Instant value;

            private TrustExpressionDataLiteralDateTimeStamp(
                    final Instant value) {

                requireNonNull(value);

                this.value = value;
            }

            public Instant getValue() {
                return value;
            }

            public <T1> T1 matchLiteral(
                    final F1<Boolean, T1> fTrustExpressionDataLiteralBoolean,
                    final F1<Instant, T1> fTrustExpressionDataLiteralDateTimeStamp,
                    final F1<BigDecimal, T1> fTrustExpressionDataLiteralDecimal,
                    final F1<String, T1> fTrustExpressionDataLiteralString) {

                requireNonNull(fTrustExpressionDataLiteralBoolean);
                requireNonNull(fTrustExpressionDataLiteralDateTimeStamp);
                requireNonNull(fTrustExpressionDataLiteralDecimal);
                requireNonNull(fTrustExpressionDataLiteralString);

                return fTrustExpressionDataLiteralDateTimeStamp.f(getValue());
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionDataLiteralDateTimeStamp that = (TrustExpressionDataLiteralDateTimeStamp) o;
                return value.equals(that.value);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value);
            }

            @Override
            public String toString() {
                return "TrustExpressionDataLiteralDateTimeStamp{" +
                        "value=" + value +
                        '}';
            }
        }

        public static final class TrustExpressionDataLiteralDecimal extends TrustExpressionDataLiteral<BigDecimal> {

            private final BigDecimal value;

            private TrustExpressionDataLiteralDecimal(
                    final BigDecimal value) {

                requireNonNull(value);

                this.value = value;
            }

            public BigDecimal getValue() {
                return value;
            }

            public <T1> T1 matchLiteral(
                    final F1<Boolean, T1> fTrustExpressionDataLiteralBoolean,
                    final F1<Instant, T1> fTrustExpressionDataLiteralDateTimeStamp,
                    final F1<BigDecimal, T1> fTrustExpressionDataLiteralDecimal,
                    final F1<String, T1> fTrustExpressionDataLiteralString) {

                requireNonNull(fTrustExpressionDataLiteralBoolean);
                requireNonNull(fTrustExpressionDataLiteralDateTimeStamp);
                requireNonNull(fTrustExpressionDataLiteralDecimal);
                requireNonNull(fTrustExpressionDataLiteralString);

                return fTrustExpressionDataLiteralDecimal.f(getValue());
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionDataLiteralDecimal that = (TrustExpressionDataLiteralDecimal) o;
                return value.equals(that.value);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value);
            }

            @Override
            public String toString() {
                return "TrustExpressionDataLiteralDecimal{" +
                        "value=" + value +
                        '}';
            }
        }

        public static final class TrustExpressionDataLiteralString extends TrustExpressionDataLiteral<String> {

            private final String value;

            private TrustExpressionDataLiteralString(
                    final String value) {

                requireNonNull(value);

                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public <T1> T1 matchLiteral(
                    final F1<Boolean, T1> fTrustExpressionDataLiteralBoolean,
                    final F1<Instant, T1> fTrustExpressionDataLiteralDateTimeStamp,
                    final F1<BigDecimal, T1> fTrustExpressionDataLiteralDecimal,
                    final F1<String, T1> fTrustExpressionDataLiteralString) {

                requireNonNull(fTrustExpressionDataLiteralBoolean);
                requireNonNull(fTrustExpressionDataLiteralDateTimeStamp);
                requireNonNull(fTrustExpressionDataLiteralDecimal);
                requireNonNull(fTrustExpressionDataLiteralString);

                return fTrustExpressionDataLiteralString.f(getValue());
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionDataLiteralString that = (TrustExpressionDataLiteralString) o;
                return value.equals(that.value);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value);
            }

            @Override
            public String toString() {
                return "TrustExpressionDataLiteralString{" +
                        "value='" + value + '\'' +
                        '}';
            }
        }
    }

    public abstract static class TrustExpressionDataReference extends TrustExpressionData {

        public <T1> T1 match(
                final F1<TrustExpressionDataLiteral<?>, T1> fTrustExpressionDataLiteral,
                final F1<TrustExpressionDataReference, T1> fTrustExpressionDataReference,
                final F1<TrustExpressionDataNonTerminal, T1> fTrustExpressionDataNonTerminal) {

            requireNonNull(fTrustExpressionDataLiteral);
            requireNonNull(fTrustExpressionDataReference);
            requireNonNull(fTrustExpressionDataNonTerminal);

            return fTrustExpressionDataReference.f(this);
        }

        public abstract <T1> T1 matchReference(
                F1<String, T1> fTrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile,
                F2<String, String, T1> fTrustExpressionDataReferenceTrustmarkDefinitionParameter);

        public static final class TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile extends TrustExpressionDataReference {

            private final String identifier;

            private TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile(
                    final String identifier) {

                requireNonNull(identifier);

                this.identifier = identifier;
            }

            public String getIdentifier() {
                return identifier;
            }

            public <T1> T1 matchReference(
                    F1<String, T1> fTrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile,
                    F2<String, String, T1> fTrustExpressionDataReferenceTrustmarkDefinitionParameter) {

                requireNonNull(fTrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile);
                requireNonNull(fTrustExpressionDataReferenceTrustmarkDefinitionParameter);

                return fTrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile.f(identifier);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile that = (TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile) o;
                return identifier.equals(that.identifier);
            }

            @Override
            public int hashCode() {
                return Objects.hash(identifier);
            }

            @Override
            public String toString() {
                return "TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile{" +
                        "identifier='" + identifier + '\'' +
                        '}';
            }
        }

        public static final class TrustExpressionDataReferenceTrustmarkDefinitionParameter extends TrustExpressionDataReference {

            private final String trustmarkDefinitionRequirementIdentifier;
            private final String trustmarkDefinitionParameterIdentifier;

            private TrustExpressionDataReferenceTrustmarkDefinitionParameter(
                    final String trustmarkDefinitionRequirementIdentifier,
                    final String trustmarkDefinitionParameterIdentifier) {

                requireNonNull(trustmarkDefinitionRequirementIdentifier);
                requireNonNull(trustmarkDefinitionParameterIdentifier);

                this.trustmarkDefinitionRequirementIdentifier = trustmarkDefinitionRequirementIdentifier;
                this.trustmarkDefinitionParameterIdentifier = trustmarkDefinitionParameterIdentifier;
            }

            public String getTrustmarkDefinitionRequirementIdentifier() {
                return trustmarkDefinitionRequirementIdentifier;
            }

            public String getTrustmarkDefinitionParameterIdentifier() {
                return trustmarkDefinitionParameterIdentifier;
            }

            public <T1> T1 matchReference(
                    F1<String, T1> fTrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile,
                    F2<String, String, T1> fTrustExpressionDataReferenceTrustmarkDefinitionParameter) {

                requireNonNull(fTrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile);
                requireNonNull(fTrustExpressionDataReferenceTrustmarkDefinitionParameter);

                return fTrustExpressionDataReferenceTrustmarkDefinitionParameter.f(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final TrustExpressionDataReferenceTrustmarkDefinitionParameter that = (TrustExpressionDataReferenceTrustmarkDefinitionParameter) o;
                return trustmarkDefinitionRequirementIdentifier.equals(that.trustmarkDefinitionRequirementIdentifier) && trustmarkDefinitionParameterIdentifier.equals(that.trustmarkDefinitionParameterIdentifier);
            }

            @Override
            public int hashCode() {
                return Objects.hash(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier);
            }

            @Override
            public String toString() {
                return "TrustExpressionDataReferenceTrustmarkDefinitionParameter{" +
                        "trustmarkDefinitionRequirementIdentifier='" + trustmarkDefinitionRequirementIdentifier + '\'' +
                        ", trustmarkDefinitionParameterIdentifier='" + trustmarkDefinitionParameterIdentifier + '\'' +
                        '}';
            }
        }
    }

    public static final class TrustExpressionDataNonTerminal extends TrustExpressionData {

        public static final TrustExpressionDataNonTerminal DATA_NON_TERMINAL = new TrustExpressionDataNonTerminal();

        private TrustExpressionDataNonTerminal() {
        }

        public <T1> T1 match(
                final F1<TrustExpressionDataLiteral<?>, T1> fTrustExpressionDataLiteral,
                final F1<TrustExpressionDataReference, T1> fTrustExpressionDataReference,
                final F1<TrustExpressionDataNonTerminal, T1> fTrustExpressionDataNonTerminal) {

            requireNonNull(fTrustExpressionDataLiteral);
            requireNonNull(fTrustExpressionDataReference);
            requireNonNull(fTrustExpressionDataNonTerminal);

            return fTrustExpressionDataNonTerminal.f(this);
        }

        @Override
        public String toString() {
            return "TrustExpressionDataNonTerminal{}";
        }
    }

    public static final TrustExpressionDataLiteralBoolean dataLiteralBoolean(
            final boolean value) {

        return new TrustExpressionDataLiteralBoolean(value);
    }

    public static final TrustExpressionDataLiteralDateTimeStamp dataLiteralDateTimeStamp(
            final Instant value) {

        return new TrustExpressionDataLiteralDateTimeStamp(value);
    }

    public static final TrustExpressionDataLiteralDecimal dataLiteralDecimal(
            final BigDecimal value) {

        return new TrustExpressionDataLiteralDecimal(value);
    }

    public static final TrustExpressionDataLiteralString dataLiteralString(
            final String value) {

        return new TrustExpressionDataLiteralString(value);
    }

    public static final TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile dataReferenceTrustmarkDefinitionRequirement(
            final String identifier) {

        return new TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile(identifier);
    }

    public static final TrustExpressionDataReferenceTrustmarkDefinitionParameter dataReferenceTrustmarkDefinitionParameter(
            final String trustmarkDefinitionRequirementIdentifier,
            final String trustmarkDefinitionParameterIdentifier) {

        return new TrustExpressionDataReferenceTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier);
    }

    public static final TrustExpressionDataNonTerminal dataNonTerminal() {

        return TrustExpressionDataNonTerminal.DATA_NON_TERMINAL;
    }
}
