package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;

import java.net.URI;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionParserFailure {
    private TrustExpressionParserFailure() {
    }

    public abstract <T1> T1 match(
            final F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureURI,
            final F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionParserFailureResolve,
            final F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureParser,
            final F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionParserFailureIdentifier);

    public static final class TrustExpressionParserFailureURI extends TrustExpressionParserFailure {

        private final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList;
        private final String uriString;
        private final RuntimeException exception;

        private TrustExpressionParserFailureURI(
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

        public List<TrustInteroperabilityProfile> getTrustInteroperabilityProfileList() {
            return trustInteroperabilityProfileList;
        }

        public String getUriString() {
            return uriString;
        }

        public RuntimeException getException() {
            return exception;
        }

        public <T1> T1 match(
                final F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureURI,
                final F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionParserFailureResolve,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureParser,
                final F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionParserFailureIdentifier) {

            requireNonNull(fTrustExpressionParserFailureURI);
            requireNonNull(fTrustExpressionParserFailureResolve);
            requireNonNull(fTrustExpressionParserFailureParser);
            requireNonNull(fTrustExpressionParserFailureIdentifier);

            return fTrustExpressionParserFailureURI.f(trustInteroperabilityProfileList, uriString, exception);
        }

        @Override
        public String toString() {
            return "TrustExpressionParserFailureURI{" +
                    "trustInteroperabilityProfileList=" + trustInteroperabilityProfileList +
                    ", uriString='" + uriString + '\'' +
                    ", exception=" + exception +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionParserFailureURI that = (TrustExpressionParserFailureURI) o;
            return trustInteroperabilityProfileList.equals(that.trustInteroperabilityProfileList) && uriString.equals(that.uriString) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileList, uriString, exception);
        }
    }

    public static final class TrustExpressionParserFailureResolve extends TrustExpressionParserFailure {

        private final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList;
        private final URI uri;
        private final ResolveException exception;

        private TrustExpressionParserFailureResolve(
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

        public List<TrustInteroperabilityProfile> getTrustInteroperabilityProfileList() {
            return trustInteroperabilityProfileList;
        }

        public URI getUri() {
            return uri;
        }

        public ResolveException getException() {
            return exception;
        }

        public <T1> T1 match(
                final F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureURI,
                final F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionParserFailureResolve,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureParser,
                final F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionParserFailureIdentifier) {

            requireNonNull(fTrustExpressionParserFailureURI);
            requireNonNull(fTrustExpressionParserFailureResolve);
            requireNonNull(fTrustExpressionParserFailureParser);
            requireNonNull(fTrustExpressionParserFailureIdentifier);

            return fTrustExpressionParserFailureResolve.f(trustInteroperabilityProfileList, uri, exception);
        }

        @Override
        public String toString() {
            return "TrustExpressionParserFailureResolve{" +
                    "trustInteroperabilityProfileList=" + trustInteroperabilityProfileList +
                    ", uri=" + uri +
                    ", exception=" + exception +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionParserFailureResolve that = (TrustExpressionParserFailureResolve) o;
            return trustInteroperabilityProfileList.equals(that.trustInteroperabilityProfileList) && uri.equals(that.uri) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileList, uri, exception);
        }
    }

    public static final class TrustExpressionParserFailureParser extends TrustExpressionParserFailure {

        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final String trustExpression;
        private final RuntimeException exception;

        private TrustExpressionParserFailureParser(
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

        public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
            return trustInteroperabilityProfileNonEmptyList;
        }

        public String getTrustExpression() {
            return trustExpression;
        }

        public RuntimeException getException() {
            return exception;
        }

        public <T1> T1 match(
                final F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureURI,
                final F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionParserFailureResolve,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureParser,
                final F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionParserFailureIdentifier) {

            requireNonNull(fTrustExpressionParserFailureURI);
            requireNonNull(fTrustExpressionParserFailureResolve);
            requireNonNull(fTrustExpressionParserFailureParser);
            requireNonNull(fTrustExpressionParserFailureIdentifier);

            return fTrustExpressionParserFailureParser.f(trustInteroperabilityProfileNonEmptyList, trustExpression, exception);
        }

        @Override
        public String toString() {
            return "TrustExpressionParserFailureParser{" +
                    "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                    ", trustExpression='" + trustExpression + '\'' +
                    ", exception=" + exception +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionParserFailureParser that = (TrustExpressionParserFailureParser) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && trustExpression.equals(that.trustExpression) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, trustExpression, exception);
        }
    }

    public static final class TrustExpressionParserFailureIdentifier extends TrustExpressionParserFailure {

        private final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList;
        private final String identifier;

        private TrustExpressionParserFailureIdentifier(
                final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
                final String identifier) {

            requireNonNull(trustInteroperabilityProfileNonEmptyList);
            requireNonNull(identifier);

            this.trustInteroperabilityProfileNonEmptyList = trustInteroperabilityProfileNonEmptyList;
            this.identifier = identifier;
        }

        public NonEmptyList<TrustInteroperabilityProfile> getTrustInteroperabilityProfileNonEmptyList() {
            return trustInteroperabilityProfileNonEmptyList;
        }

        public String getIdentifier() {
            return identifier;
        }

        public <T1> T1 match(
                final F3<List<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureURI,
                final F3<List<TrustInteroperabilityProfile>, URI, ResolveException, T1> fTrustExpressionParserFailureResolve,
                final F3<NonEmptyList<TrustInteroperabilityProfile>, String, RuntimeException, T1> fTrustExpressionParserFailureParser,
                final F2<NonEmptyList<TrustInteroperabilityProfile>, String, T1> fTrustExpressionParserFailureIdentifier) {

            requireNonNull(fTrustExpressionParserFailureURI);
            requireNonNull(fTrustExpressionParserFailureResolve);
            requireNonNull(fTrustExpressionParserFailureParser);
            requireNonNull(fTrustExpressionParserFailureIdentifier);

            return fTrustExpressionParserFailureIdentifier.f(trustInteroperabilityProfileNonEmptyList, identifier);
        }

        @Override
        public String toString() {
            return "TrustExpressionParserFailureIdentifier{" +
                    "trustInteroperabilityProfileNonEmptyList=" + trustInteroperabilityProfileNonEmptyList +
                    ", identifier='" + identifier + '\'' +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionParserFailureIdentifier that = (TrustExpressionParserFailureIdentifier) o;
            return trustInteroperabilityProfileNonEmptyList.equals(that.trustInteroperabilityProfileNonEmptyList) && identifier.equals(that.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustInteroperabilityProfileNonEmptyList, identifier);
        }
    }

    public static final TrustExpressionParserFailureURI parserFailureURI(final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList, final String uriString, final RuntimeException exception) {
        return new TrustExpressionParserFailureURI(trustInteroperabilityProfileList, uriString, exception);
    }

    public static final TrustExpressionParserFailureResolve parserFailureResolve(final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList, final URI uri, final ResolveException exception) {
        return new TrustExpressionParserFailureResolve(trustInteroperabilityProfileList, uri, exception);
    }

    public static final TrustExpressionParserFailureParser parserFailureParser(final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList, final String trustExpression, final RuntimeException exception) {
        return new TrustExpressionParserFailureParser(trustInteroperabilityProfileNonEmptyList, trustExpression, exception);
    }

    public static final TrustExpressionParserFailureIdentifier parserFailureIdentifier(final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList, final String identifier) {
        return new TrustExpressionParserFailureIdentifier(trustInteroperabilityProfileNonEmptyList, identifier);
    }
}
