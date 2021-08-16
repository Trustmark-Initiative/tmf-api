package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F4;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionEvaluatorSource {

    public abstract NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList();

    public abstract <T1> T1 match(
            F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionEvaluatorSourceValue,
            F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>, T1> fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement,
            F4<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, NonEmptyList<Trustmark>, T1> fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter);

    public static final class TrustExpressionEvaluatorSourceValue extends TrustExpressionEvaluatorSource {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;

        private TrustExpressionEvaluatorSourceValue(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
        }

        @Override
        public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
            return trustInteroperabilityProfileNonEmptyList;
        }

        @Override
        public <T1> T1 match(
                final F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionEvaluatorSourceValue,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>, T1> fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement,
                final F4<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, NonEmptyList<Trustmark>, T1> fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter) {

            requireNonNull(fTrustExpressionEvaluatorSourceValue);
            requireNonNull(fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter);

            return fTrustExpressionEvaluatorSourceValue.f(getTrustInteroperabilityProfileNonEmptyList());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorSourceValue that = (TrustExpressionEvaluatorSourceValue) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorSourceValue{" +
                    "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement extends TrustExpressionEvaluatorSource {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement;
        private final List<Trustmark> trustmarkList;

        private TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
                final List<Trustmark> trustmarkList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustmarkDefinitionRequirement);
            requireNonNull(trustmarkList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustmarkDefinitionRequirement = trustmarkDefinitionRequirement;
            this.trustmarkList = trustmarkList;
        }

        @Override
        public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
            return trustInteroperabilityProfileNonEmptyList;
        }

        public TrustmarkDefinitionRequirement getTrustmarkDefinitionRequirement() {
            return trustmarkDefinitionRequirement;
        }

        public List<Trustmark> getTrustmarkList() {
            return trustmarkList;
        }

        @Override
        public <T1> T1 match(
                final F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionEvaluatorSourceValue,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>, T1> fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement,
                final F4<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, NonEmptyList<Trustmark>, T1> fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter) {

            requireNonNull(fTrustExpressionEvaluatorSourceValue);
            requireNonNull(fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter);

            return fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement.f(getTrustInteroperabilityProfileNonEmptyList(), getTrustmarkDefinitionRequirement(), getTrustmarkList());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement that = (TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustmarkDefinitionRequirement.equals(that.trustmarkDefinitionRequirement) && trustmarkList.equals(that.trustmarkList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement{" +
                    "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                    ", trustmarkDefinitionRequirement=" + trustmarkDefinitionRequirement +
                    ", trustmarkList=" + trustmarkList +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter extends TrustExpressionEvaluatorSource {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement;
        private final TrustmarkDefinitionParameter trustmarkDefinitionParameter;
        private final NonEmptyList<Trustmark> trustmarkNonEmptyList;

        private TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
                final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
                final NonEmptyList<Trustmark> trustmarkNonEmptyList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustmarkDefinitionRequirement);
            requireNonNull(trustmarkDefinitionParameter);
            requireNonNull(trustmarkNonEmptyList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustmarkDefinitionRequirement = trustmarkDefinitionRequirement;
            this.trustmarkDefinitionParameter = trustmarkDefinitionParameter;
            this.trustmarkNonEmptyList = trustmarkNonEmptyList;
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

        public NonEmptyList<Trustmark> getTrustmarkNonEmptyList() {
            return trustmarkNonEmptyList;
        }

        @Override
        public <T1> T1 match(
                final F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionEvaluatorSourceValue,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, List<Trustmark>, T1> fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement,
                final F4<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, NonEmptyList<Trustmark>, T1> fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter) {

            requireNonNull(fTrustExpressionEvaluatorSourceValue);
            requireNonNull(fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter);

            return fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter.f(getTrustInteroperabilityProfileNonEmptyList(), getTrustmarkDefinitionRequirement(), getTrustmarkDefinitionParameter(), getTrustmarkNonEmptyList());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter that = (TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustmarkDefinitionRequirement.equals(that.trustmarkDefinitionRequirement) && trustmarkDefinitionParameter.equals(that.trustmarkDefinitionParameter) && trustmarkNonEmptyList.equals(that.trustmarkNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter{" +
                    "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                    ", trustmarkDefinitionRequirement=" + trustmarkDefinitionRequirement +
                    ", trustmarkDefinitionParameter=" + trustmarkDefinitionParameter +
                    ", trustmarkNonEmptyList=" + trustmarkNonEmptyList +
                    '}';
        }
    }

    public static final TrustExpressionEvaluatorSource source(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return new TrustExpressionEvaluatorSourceValue(trustInteroperabilityProfileNonEmptyList);
    }

    public static final TrustExpressionEvaluatorSource source(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final List<Trustmark> trustmarkList) {

        return new TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList);
    }

    public static final TrustExpressionEvaluatorSource source(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter,
            final NonEmptyList<Trustmark> trustmarkList) {

        return new TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkList);
    }
}
