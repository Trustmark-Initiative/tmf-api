package edu.gatech.gtri.trustmark.v1_0.tip;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;

import java.net.URI;
import java.util.Objects;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.right;

public abstract class TrustExpressionFailure {

    private TrustExpressionFailure() {
    }

    public abstract <T1> T1 match(
            F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
            F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
            F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
            F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
            F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
            F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
            F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
            F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
            F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
            F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
            F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
            F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
            F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
            F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
            F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
            F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
            F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
            F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
            F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
            F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
            F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight);

    public List<TrustInteroperabilityProfile> getTrustInteroperabilityProfileList() {

        return match(
                (trustInteroperabilityProfileList, uriString, exception) -> trustInteroperabilityProfileList,
                (trustInteroperabilityProfileList, uri, exception) -> trustInteroperabilityProfileList,
                (trustInteroperabilityProfileList) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, uri, exception) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, expression, exception) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, identifier) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, pNonEmptyList) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, typeLeft, typeRight) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, type) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, type) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> trustInteroperabilityProfileList.toList(),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> trustInteroperabilityProfileList.toList());
    }

    public static final class TrustExpressionFailureURI extends TrustExpressionFailure {
        private final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList;
        private final String uriString;
        private final RuntimeException exception;

        public TrustExpressionFailureURI(
                final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
                final String uriString,
                final RuntimeException exception) {

            requireNonNull(trustInteroperabilityProfileList);
            requireNonNull(uriString);
            requireNonNull(exception);

            this.trustInteroperabilityProfileList = trustInteroperabilityProfileList;
            this.uriString = uriString;
            this.exception = exception;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureURI.f(trustInteroperabilityProfileList, uriString, exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureURI that = (TrustExpressionFailureURI) o;
            return trustInteroperabilityProfileList.equals(that.trustInteroperabilityProfileList) && uriString.equals(that.uriString) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileList, uriString, exception);
        }
    }

    public static final class TrustExpressionFailureResolveTrustInteroperabilityProfile extends TrustExpressionFailure {
        private final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList;
        private final URI uri;
        private final ResolveException exception;

        public TrustExpressionFailureResolveTrustInteroperabilityProfile(
                final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
                final URI uri,
                final ResolveException exception) {

            requireNonNull(trustInteroperabilityProfileList);
            requireNonNull(uri);
            requireNonNull(exception);

            this.trustInteroperabilityProfileList = trustInteroperabilityProfileList;
            this.uri = uri;
            this.exception = exception;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureResolveTrustInteroperabilityProfile.f(trustInteroperabilityProfileList, uri, exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureResolveTrustInteroperabilityProfile that = (TrustExpressionFailureResolveTrustInteroperabilityProfile) o;
            return trustInteroperabilityProfileList.equals(that.trustInteroperabilityProfileList) && uri.equals(that.uri) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileList, uri, exception);
        }
    }

    public static final class TrustExpressionFailureCycle extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;

        public TrustExpressionFailureCycle(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureCycle.f(trustInteroperabilityProfileNonEmptyList);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureCycle that = (TrustExpressionFailureCycle) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList);
        }
    }

    public static final class TrustExpressionFailureResolveTrustmarkDefinition extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final URI uri;
        private final ResolveException exception;

        public TrustExpressionFailureResolveTrustmarkDefinition(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final URI uri,
                final ResolveException exception) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(uri);
            requireNonNull(exception);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.uri = uri;
            this.exception = exception;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureResolveTrustmarkDefinition.f(trustInteroperabilityProfileNonEmptyList, uri, exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureResolveTrustmarkDefinition that = (TrustExpressionFailureResolveTrustmarkDefinition) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && uri.equals(that.uri) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, uri, exception);
        }
    }

    public static final class TrustExpressionFailureParser extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final String trustExpression;
        private final RuntimeException exception;

        public TrustExpressionFailureParser(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final String trustExpression,
                final RuntimeException exception) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustExpression);
            requireNonNull(exception);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustExpression = trustExpression;
            this.exception = exception;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureParser.f(trustInteroperabilityProfileNonEmptyList, trustExpression, exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureParser that = (TrustExpressionFailureParser) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpression.equals(that.trustExpression) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustExpression, exception);
        }
    }

    public static final class TrustExpressionFailureIdentifierUnknown extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final String identifier;

        public TrustExpressionFailureIdentifierUnknown(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final String identifier) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(identifier);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.identifier = identifier;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureIdentifierUnknown.f(trustInteroperabilityProfileNonEmptyList, identifier);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureIdentifierUnknown that = (TrustExpressionFailureIdentifierUnknown) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && identifier.equals(that.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, identifier);
        }
    }

    public static final class TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final String trustmarkDefinitionRequirementIdentifier;
        private final String trustmarkDefinitionParameterIdentifier;

        public TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final String trustmarkDefinitionRequirementIdentifier,
                final String trustmarkDefinitionParameterIdentifier) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustmarkDefinitionRequirementIdentifier);
            requireNonNull(trustmarkDefinitionParameterIdentifier);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustmarkDefinitionRequirementIdentifier = trustmarkDefinitionRequirementIdentifier;
            this.trustmarkDefinitionParameterIdentifier = trustmarkDefinitionParameterIdentifier;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile.f(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionParameterIdentifier, trustmarkDefinitionParameterIdentifier);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile that = (TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustmarkDefinitionRequirementIdentifier.equals(that.trustmarkDefinitionRequirementIdentifier) && trustmarkDefinitionParameterIdentifier.equals(that.trustmarkDefinitionParameterIdentifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier);
        }
    }

    public static final class TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement;
        private final String trustmarkDefinitionParameterIdentifier;

        public TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
                final String trustmarkDefinitionParameterIdentifier) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustmarkDefinitionRequirement);
            requireNonNull(trustmarkDefinitionParameterIdentifier);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustmarkDefinitionRequirement = trustmarkDefinitionRequirement;
            this.trustmarkDefinitionParameterIdentifier = trustmarkDefinitionParameterIdentifier;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter.f(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter that = (TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustmarkDefinitionRequirement.equals(that.trustmarkDefinitionRequirement) && trustmarkDefinitionParameterIdentifier.equals(that.trustmarkDefinitionParameterIdentifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier);
        }
    }

    public static final class TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final String trustmarkDefinitionRequirementIdentifier;
        private final String trustmarkDefinitionParameterIdentifier;

        public TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final String trustmarkDefinitionRequirementIdentifier,
                final String trustmarkDefinitionParameterIdentifier) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustmarkDefinitionRequirementIdentifier);
            requireNonNull(trustmarkDefinitionParameterIdentifier);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustmarkDefinitionRequirementIdentifier = trustmarkDefinitionRequirementIdentifier;
            this.trustmarkDefinitionParameterIdentifier = trustmarkDefinitionParameterIdentifier;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement.f(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement that = (TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustmarkDefinitionRequirementIdentifier.equals(that.trustmarkDefinitionRequirementIdentifier) && trustmarkDefinitionParameterIdentifier.equals(that.trustmarkDefinitionParameterIdentifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier);
        }
    }

    public static final class TrustExpressionFailureNonTerminalUnexpected extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;

        public TrustExpressionFailureNonTerminalUnexpected(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureNonTerminalUnexpected.f(trustInteroperabilityProfileNonEmptyList);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureNonTerminalUnexpected that = (TrustExpressionFailureNonTerminalUnexpected) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList);
        }
    }

    public static final class TrustExpressionFailureTrustmarkAbsent extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement;
        private final TrustmarkDefinitionParameter trustmarkDefinitionParameter;

        public TrustExpressionFailureTrustmarkAbsent(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
                final TrustmarkDefinitionParameter trustmarkDefinitionParameter) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustmarkDefinitionRequirement);
            requireNonNull(trustmarkDefinitionParameter);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustmarkDefinitionRequirement = trustmarkDefinitionRequirement;
            this.trustmarkDefinitionParameter = trustmarkDefinitionParameter;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureTrustmarkAbsent.f(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureTrustmarkAbsent that = (TrustExpressionFailureTrustmarkAbsent) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustmarkDefinitionRequirement.equals(that.trustmarkDefinitionRequirement) && trustmarkDefinitionParameter.equals(that.trustmarkDefinitionParameter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter);
        }
    }

    public static final class TrustExpressionFailureTrustmarkVerifierFailure extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement;
        private final NonEmptyList<TrustmarkVerifierFailure> trustmarkVerifierFailureNonEmptyList;

        public TrustExpressionFailureTrustmarkVerifierFailure(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
                final NonEmptyList<TrustmarkVerifierFailure> trustmarkVerifierFailureNonEmptyList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustmarkDefinitionRequirement);
            requireNonNull(trustmarkVerifierFailureNonEmptyList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustmarkDefinitionRequirement = trustmarkDefinitionRequirement;
            this.trustmarkVerifierFailureNonEmptyList = trustmarkVerifierFailureNonEmptyList;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureTrustmarkVerifierFailure.f(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkVerifierFailureNonEmptyList);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureTrustmarkVerifierFailure that = (TrustExpressionFailureTrustmarkVerifierFailure) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustmarkDefinitionRequirement.equals(that.trustmarkDefinitionRequirement) && trustmarkVerifierFailureNonEmptyList.equals(that.trustmarkVerifierFailureNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkVerifierFailureNonEmptyList);
        }
    }

    public static final class TrustExpressionFailureTypeUnexpected extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final NonEmptyList<Either<ParameterKind, TrustExpressionType>> typeExpectedList;
        private final Either<ParameterKind, TrustExpressionType> typeActual;

        public TrustExpressionFailureTypeUnexpected(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final NonEmptyList<Either<ParameterKind, TrustExpressionType>> typeExpectedList,
                final Either<ParameterKind, TrustExpressionType> typeActual) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(typeExpectedList);
            requireNonNull(typeActual);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.typeExpectedList = typeExpectedList;
            this.typeActual = typeActual;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureTypeUnexpected.f(trustInteroperabilityProfileNonEmptyList, typeExpectedList, typeActual);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureTypeUnexpected that = (TrustExpressionFailureTypeUnexpected) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && typeExpectedList.equals(that.typeExpectedList) && typeActual.equals(that.typeActual);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, typeExpectedList, typeActual);
        }
    }

    public static final class TrustExpressionFailureTypeUnexpectedLeft extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final NonEmptyList<TrustExpressionType> typeExpectedList;
        private final TrustExpressionType typeActual;

        public TrustExpressionFailureTypeUnexpectedLeft(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final NonEmptyList<TrustExpressionType> typeExpectedList,
                final TrustExpressionType typeActual) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(typeExpectedList);
            requireNonNull(typeActual);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.typeExpectedList = typeExpectedList;
            this.typeActual = typeActual;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureTypeUnexpectedLeft.f(trustInteroperabilityProfileNonEmptyList, typeExpectedList, typeActual);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureTypeUnexpectedLeft that = (TrustExpressionFailureTypeUnexpectedLeft) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && typeExpectedList.equals(that.typeExpectedList) && typeActual.equals(that.typeActual);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, typeExpectedList, typeActual);
        }
    }

    public static final class TrustExpressionFailureTypeUnexpectedRight extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final NonEmptyList<TrustExpressionType> typeExpectedList;
        private final TrustExpressionType typeActual;

        public TrustExpressionFailureTypeUnexpectedRight(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final NonEmptyList<TrustExpressionType> typeExpectedList,
                final TrustExpressionType typeActual) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(typeExpectedList);
            requireNonNull(typeActual);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.typeExpectedList = typeExpectedList;
            this.typeActual = typeActual;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureTypeUnexpectedRight.f(trustInteroperabilityProfileNonEmptyList, typeExpectedList, typeActual);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureTypeUnexpectedRight that = (TrustExpressionFailureTypeUnexpectedRight) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && typeExpectedList.equals(that.typeExpectedList) && typeActual.equals(that.typeActual);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, typeExpectedList, typeActual);
        }
    }

    public static final class TrustExpressionFailureTypeMismatch extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustExpressionType typeLeft;
        private final TrustExpressionType typeRight;

        public TrustExpressionFailureTypeMismatch(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustExpressionType typeLeft,
                final TrustExpressionType typeRight) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(typeLeft);
            requireNonNull(typeRight);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.typeLeft = typeLeft;
            this.typeRight = typeRight;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureTypeMismatch.f(trustInteroperabilityProfileNonEmptyList, typeLeft, typeRight);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureTypeMismatch that = (TrustExpressionFailureTypeMismatch) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && typeLeft == that.typeLeft && typeRight == that.typeRight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, typeLeft, typeRight);
        }
    }

    public static final class TrustExpressionFailureTypeUnorderableLeft extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustExpressionType type;

        public TrustExpressionFailureTypeUnorderableLeft(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustExpressionType type) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(type);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.type = type;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureTypeUnorderableLeft.f(trustInteroperabilityProfileNonEmptyList, type);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureTypeUnorderableLeft that = (TrustExpressionFailureTypeUnorderableLeft) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, type);
        }
    }

    public static final class TrustExpressionFailureTypeUnorderableRight extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final TrustExpressionType type;

        public TrustExpressionFailureTypeUnorderableRight(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final TrustExpressionType type) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(type);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.type = type;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureTypeUnorderableRight.f(trustInteroperabilityProfileNonEmptyList, type);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureTypeUnorderableRight that = (TrustExpressionFailureTypeUnorderableRight) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, type);
        }
    }

    public static final class TrustExpressionFailureExpression extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList;

        public TrustExpressionFailureExpression(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustExpressionFailureNonEmptyList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustExpressionFailureNonEmptyList = trustExpressionFailureNonEmptyList;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureExpression.f(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureExpression that = (TrustExpressionFailureExpression) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpressionFailureNonEmptyList.equals(that.trustExpressionFailureNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
        }
    }

    public static final class TrustExpressionFailureExpressionLeft extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList;

        public TrustExpressionFailureExpressionLeft(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustExpressionFailureNonEmptyList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustExpressionFailureNonEmptyList = trustExpressionFailureNonEmptyList;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureExpressionLeft.f(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureExpressionLeft that = (TrustExpressionFailureExpressionLeft) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpressionFailureNonEmptyList.equals(that.trustExpressionFailureNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
        }
    }

    public static final class TrustExpressionFailureExpressionRight extends TrustExpressionFailure {
        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList;

        public TrustExpressionFailureExpressionRight(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(trustExpressionFailureNonEmptyList);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.trustExpressionFailureNonEmptyList = trustExpressionFailureNonEmptyList;
        }

        public <T1> T1 match(
                F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureURI,
                F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustInteroperabilityProfile,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureCycle,
                F3<NonEmptyList<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionFailureResolveTrustmarkDefinition,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionFailureParser,
                F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionFailureIdentifierUnknown,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
                F3<NonEmptyList<TrustInteroperabilityProfile>, String, String, T1> fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
                F1<NonEmptyList<TrustInteroperabilityProfile>, T1> fTrustExpressionFailureNonTerminalUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, TrustmarkDefinitionParameter, T1> fTrustExpressionFailureTrustmarkAbsent,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustmarkDefinitionRequirement, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionFailureTrustmarkVerifierFailure,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<Either<ParameterKind, TrustExpressionType>>, Either<ParameterKind, TrustExpressionType>, T1> fTrustExpressionFailureTypeUnexpected,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedLeft,
                F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnexpectedRight,
                F3<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, TrustExpressionType, T1> fTrustExpressionFailureTypeMismatch,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, TrustExpressionType, T1> fTrustExpressionFailureTypeUnorderableRight,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpression,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionLeft,
                F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, T1> fTrustExpressionFailureExpressionRight) {

            requireNonNull(fTrustExpressionFailureURI);
            requireNonNull(fTrustExpressionFailureResolveTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureCycle);
            requireNonNull(fTrustExpressionFailureResolveTrustmarkDefinition);
            requireNonNull(fTrustExpressionFailureParser);
            requireNonNull(fTrustExpressionFailureIdentifierUnknown);
            requireNonNull(fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter);
            requireNonNull(fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement);
            requireNonNull(fTrustExpressionFailureNonTerminalUnexpected);
            requireNonNull(fTrustExpressionFailureTrustmarkAbsent);
            requireNonNull(fTrustExpressionFailureTrustmarkVerifierFailure);
            requireNonNull(fTrustExpressionFailureTypeUnexpected);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedLeft);
            requireNonNull(fTrustExpressionFailureTypeUnexpectedRight);
            requireNonNull(fTrustExpressionFailureTypeMismatch);
            requireNonNull(fTrustExpressionFailureTypeUnorderableLeft);
            requireNonNull(fTrustExpressionFailureTypeUnorderableRight);
            requireNonNull(fTrustExpressionFailureExpression);
            requireNonNull(fTrustExpressionFailureExpressionLeft);
            requireNonNull(fTrustExpressionFailureExpressionRight);

            return fTrustExpressionFailureExpressionRight.f(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionFailureExpressionRight that = (TrustExpressionFailureExpressionRight) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpressionFailureNonEmptyList.equals(that.trustExpressionFailureNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
        }
    }

    public static final TrustExpressionFailure failureURI(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final String uriString,
            final RuntimeException exception) {

        return new TrustExpressionFailureURI(trustInteroperabilityProfileList, uriString, exception);
    }

    public static final TrustExpressionFailure failureResolveTrustInteroperabilityProfile(
            final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList,
            final URI uri,
            final ResolveException exception) {

        return new TrustExpressionFailureResolveTrustInteroperabilityProfile(trustInteroperabilityProfileList, uri, exception);
    }

    public static final TrustExpressionFailure failureCycle(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return new TrustExpressionFailureCycle(trustInteroperabilityProfileNonEmptyList);
    }

    public static final TrustExpressionFailure failureResolveTrustmarkDefinition(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final URI uri,
            final ResolveException exception) {

        return new TrustExpressionFailureResolveTrustmarkDefinition(trustInteroperabilityProfileNonEmptyList, uri, exception);
    }

    public static final TrustExpressionFailure failureParser(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final String trustExpression,
            final RuntimeException exception) {

        return new TrustExpressionFailureParser(trustInteroperabilityProfileNonEmptyList, trustExpression, exception);
    }

    public static final TrustExpressionFailure failureIdentifierUnknown(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final String identifier) {

        return new TrustExpressionFailureIdentifierUnknown(trustInteroperabilityProfileNonEmptyList, identifier);
    }

    public static final TrustExpressionFailure failureIdentifierUnexpectedTrustInteroperabilityProfile(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final String trustmarkDefinitionRequirementIdentifier,
            final String trustmarkDefinitionParameterIdentifier) {

        return new TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier);
    }

    public static final TrustExpressionFailure failureIdentifierUnknownTrustmarkDefinitionParameter(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final String trustmarkDefinitionParameterIdentifier) {

        return new TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier);
    }

    public static final TrustExpressionFailure failureIdentifierUnknownTrustmarkDefinitionRequirement(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final String trustmarkDefinitionRequirementIdentifier,
            final String trustmarkDefinitionParameterIdentifier) {

        return new TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier);
    }

    public static final TrustExpressionFailure failureNonTerminalUnexpected(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return new TrustExpressionFailureNonTerminalUnexpected(trustInteroperabilityProfileNonEmptyList);
    }

    public static final TrustExpressionFailure failureTrustmarkAbsent(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinitionParameter trustmarkDefinitionParameter) {

        return new TrustExpressionFailureTrustmarkAbsent(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter);
    }

    public static final TrustExpressionFailure failureTrustmarkVerifierFailure(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final NonEmptyList<TrustmarkVerifierFailure> trustmarkVerifierFailureNonEmptyList) {

        return new TrustExpressionFailureTrustmarkVerifierFailure(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkVerifierFailureNonEmptyList);
    }

    public static final TrustExpressionFailure failureTypeUnexpected(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final NonEmptyList<ParameterKind> typeExpectedList,
            final ParameterKind typeActual) {

        requireNonNull(typeActual);

        return new TrustExpressionFailureTypeUnexpected(trustInteroperabilityProfileNonEmptyList, typeExpectedList.map(parameterKind -> left(parameterKind)), left(typeActual));
    }

    public static final TrustExpressionFailure failureTypeUnexpected(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final NonEmptyList<TrustExpressionType> typeExpectedList,
            final TrustExpressionType typeActual) {

        requireNonNull(typeActual);

        return new TrustExpressionFailureTypeUnexpected(trustInteroperabilityProfileNonEmptyList, typeExpectedList.map(parameterKind -> right(parameterKind)), right(typeActual));
    }

    public static final TrustExpressionFailure failureTypeUnexpectedLeft(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final NonEmptyList<TrustExpressionType> typeExpectedList,
            final TrustExpressionType typeActual) {

        return new TrustExpressionFailureTypeUnexpectedLeft(trustInteroperabilityProfileNonEmptyList, typeExpectedList, typeActual);
    }

    public static final TrustExpressionFailure failureTypeUnexpectedRight(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final NonEmptyList<TrustExpressionType> typeExpectedList,
            final TrustExpressionType typeActual) {

        return new TrustExpressionFailureTypeUnexpectedRight(trustInteroperabilityProfileNonEmptyList, typeExpectedList, typeActual);
    }

    public static final TrustExpressionFailure failureTypeMismatch(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustExpressionType typeLeft,
            final TrustExpressionType typeRight) {

        return new TrustExpressionFailureTypeMismatch(trustInteroperabilityProfileNonEmptyList, typeLeft, typeRight);
    }

    public static final TrustExpressionFailure failureTypeUnorderableLeft(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustExpressionType type) {

        return new TrustExpressionFailureTypeUnorderableLeft(trustInteroperabilityProfileNonEmptyList, type);
    }

    public static final TrustExpressionFailure failureTypeUnorderableRight(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final TrustExpressionType type) {

        return new TrustExpressionFailureTypeUnorderableRight(trustInteroperabilityProfileNonEmptyList, type);
    }

    public static final TrustExpressionFailure failureExpression(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList) {

        return new TrustExpressionFailureExpression(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
    }

    public static final TrustExpressionFailure failureExpressionLeft(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList) {

        return new TrustExpressionFailureExpressionLeft(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
    }

    public static final TrustExpressionFailure failureExpressionRight(
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList) {

        return new TrustExpressionFailureExpressionRight(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList);
    }

    public static String messageFor(final TrustExpressionFailure trustExpressionFailure) {

        return trustExpressionFailure.match(
                (trustInteroperabilityProfileList, uriString, exception) -> format("'%s': could not resolve '%s'; message: %s.", trustInteroperabilityProfileList.head().getIdentifier().toString(), uriString, exception.getMessage()),
                (trustInteroperabilityProfileList, uri, exception) -> format("'%s': could not resolve trust interoperability profile at '%s'; message: %s.", trustInteroperabilityProfileList.head().getIdentifier().toString(), uri.toString(), exception.getMessage()),
                (trustInteroperabilityProfileList) -> format("'%s': contained a cycle (%s).", trustInteroperabilityProfileList.head().getIdentifier().toString(), String.join(",", trustInteroperabilityProfileList.map(TrustInteroperabilityProfile::getIdentifier).map(URI::toString).toCollection())),
                (trustInteroperabilityProfileList, uri, exception) -> format("'%s': could not resolve trustmark definition at '%s'; message: %s.", trustInteroperabilityProfileList.head().getIdentifier().toString(), uri.toString(), exception.getMessage()),
                (trustInteroperabilityProfileList, expression, exception) -> format("'%s': could not parse '%s'; message: %s", trustInteroperabilityProfileList.head().getIdentifier().toString(), expression, exception.getMessage()),
                (trustInteroperabilityProfileList, identifier) -> format("'%s': could not resolve identifier '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), identifier),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> format("'%s': resolved '%s' as trust interoperability profile; could not resolve parameter '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier) -> format("'%s': resolved '%s'; could not resolve parameter '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), trustmarkDefinitionRequirement.getIdentifier().toString(), trustmarkDefinitionParameterIdentifier),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier) -> format("'%s': could not resolve identifier '%s.%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier),
                (trustInteroperabilityProfileList) -> format("'%s': unexpected non-terminal.", trustInteroperabilityProfileList.head().getIdentifier().toString()),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter) -> format("'%s': no trustmark satisfies '%s' '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), trustmarkDefinitionRequirement.getIdentifier().toString(), trustmarkDefinitionParameter.getIdentifier()),
                (trustInteroperabilityProfileList, trustmarkDefinitionRequirement, trustmarkNonEmptyList) -> String.join(lineSeparator(), trustmarkNonEmptyList.map(p -> format("'%s': trustmark '%s' satisfies '%s', but it is not valid; message: %s.", trustInteroperabilityProfileList.head().getIdentifier().toString(), p.getTrustmark().getIdentifier().toString(), trustmarkDefinitionRequirement.getIdentifier().toString(), TrustmarkVerifierFailure.messageFor(p))).toCollection()),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> format("'%s': expected type '%s'; actual type '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), String.join(",", typeExpected.map(either -> Either.reduce(either.bimap(ParameterKind::name, trustExpressionType -> trustExpressionType.getClass().getSimpleName()))).toCollection()), Either.reduce(typeActual.bimap(ParameterKind::name, trustExpressionType -> trustExpressionType.getClass().getSimpleName()))),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> format("'%s': expected type '%s'; actual type '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), String.join(",", typeExpected.map(trustExpressionType -> trustExpressionType.getClass().getSimpleName()).toCollection()), typeActual.getClass().getSimpleName()),
                (trustInteroperabilityProfileList, typeExpected, typeActual) -> format("'%s': expected type '%s'; actual type '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), String.join(",", typeExpected.map(trustExpressionType -> trustExpressionType.getClass().getSimpleName()).toCollection()), typeActual.getClass().getSimpleName()),
                (trustInteroperabilityProfileList, typeLeft, typeRight) -> format("'%s': expected types to match: '%s', '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), typeLeft.getClass().getSimpleName(), typeRight.getClass().getSimpleName()),
                (trustInteroperabilityProfileList, type) -> format("'%s': expected type to be orderable: '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), type.getClass().getSimpleName()),
                (trustInteroperabilityProfileList, type) -> format("'%s': expected type to be orderable: '%s'.", trustInteroperabilityProfileList.head().getIdentifier().toString(), type.getClass().getSimpleName()),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> String.join(lineSeparator(), trustExpressionFailureNonEmptyList.map(TrustExpressionFailure::messageFor).toCollection()),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> String.join(lineSeparator(), trustExpressionFailureNonEmptyList.map(TrustExpressionFailure::messageFor).toCollection()),
                (trustInteroperabilityProfileList, trustExpressionFailureNonEmptyList) -> String.join(lineSeparator(), trustExpressionFailureNonEmptyList.map(TrustExpressionFailure::messageFor).toCollection()));
    }
}
