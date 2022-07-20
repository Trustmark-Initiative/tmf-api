package edu.gatech.gtri.trustmark.v1_0.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F2;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionEvaluatorFailure {

    private TrustExpressionEvaluatorFailure() {
    }

    public abstract <T1> T1 match(
            final F2<String, URISyntaxException, T1> fTrustExpressionEvaluatorFailureURI,
            final F2<URI, ResolveException, T1> fTrustExpressionEvaluatorFailureResolve,
            final F2<Trustmark, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionEvaluatorFailureVerify);

    public static final class TrustExpressionEvaluatorFailureURI extends TrustExpressionEvaluatorFailure {

        private final String uriString;
        private final URISyntaxException exception;

        private TrustExpressionEvaluatorFailureURI(
                final String uriString,
                final URISyntaxException exception) {

            requireNonNull(uriString);
            requireNonNull(exception);

            this.uriString = uriString;
            this.exception = exception;
        }

        public String getUriString() {
            return uriString;
        }

        public URISyntaxException getException() {
            return exception;
        }

        @Override
        public <T1> T1 match(
                final F2<String, URISyntaxException, T1> fTrustExpressionEvaluatorFailureURI,
                final F2<URI, ResolveException, T1> fTrustExpressionEvaluatorFailureResolve,
                final F2<Trustmark, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionEvaluatorFailureVerify) {

            requireNonNull(fTrustExpressionEvaluatorFailureURI);
            requireNonNull(fTrustExpressionEvaluatorFailureResolve);
            requireNonNull(fTrustExpressionEvaluatorFailureVerify);

            return fTrustExpressionEvaluatorFailureURI.f(uriString, exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorFailureURI that = (TrustExpressionEvaluatorFailureURI) o;
            return uriString.equals(that.uriString) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uriString, exception);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorFailureURI{" +
                    "uriString='" + uriString + '\'' +
                    ", exception=" + exception +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorFailureResolve extends TrustExpressionEvaluatorFailure {

        private final URI uri;
        private final ResolveException exception;

        private TrustExpressionEvaluatorFailureResolve(
                final URI uri,
                final ResolveException exception) {

            requireNonNull(uri);
            requireNonNull(exception);

            this.uri = uri;
            this.exception = exception;
        }

        public URI getUri() {
            return uri;
        }

        public ResolveException getException() {
            return exception;
        }

        @Override
        public <T1> T1 match(
                final F2<String, URISyntaxException, T1> fTrustExpressionEvaluatorFailureURI,
                final F2<URI, ResolveException, T1> fTrustExpressionEvaluatorFailureResolve,
                final F2<Trustmark, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionEvaluatorFailureVerify) {

            requireNonNull(fTrustExpressionEvaluatorFailureURI);
            requireNonNull(fTrustExpressionEvaluatorFailureResolve);
            requireNonNull(fTrustExpressionEvaluatorFailureVerify);

            return fTrustExpressionEvaluatorFailureResolve.f(uri, exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorFailureResolve that = (TrustExpressionEvaluatorFailureResolve) o;
            return uri.equals(that.uri) && exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uri, exception);
        }

        @Override
        public String toString() {
            return "TrustExpressionEvaluatorFailureResolve{" +
                    "uri=" + uri +
                    ", exception=" + exception +
                    '}';
        }
    }

    public static final class TrustExpressionEvaluatorFailureVerify extends TrustExpressionEvaluatorFailure {

        private final Trustmark trustmark;
        private final NonEmptyList<TrustmarkVerifierFailure> trustmarkVerificationFailureNonEmptyList;

        public TrustExpressionEvaluatorFailureVerify(
                final Trustmark trustmark,
                final NonEmptyList<TrustmarkVerifierFailure> trustmarkVerificationFailureNonEmptyList) {

            requireNonNull(trustmark);
            requireNonNull(trustmarkVerificationFailureNonEmptyList);

            this.trustmark = trustmark;
            this.trustmarkVerificationFailureNonEmptyList = trustmarkVerificationFailureNonEmptyList;
        }

        public Trustmark getTrustmark() {
            return trustmark;
        }

        public NonEmptyList<TrustmarkVerifierFailure> getTrustmarkVerificationFailureNonEmptyList() {
            return trustmarkVerificationFailureNonEmptyList;
        }

        @Override
        public <T1> T1 match(
                final F2<String, URISyntaxException, T1> fTrustExpressionEvaluatorFailureURI,
                final F2<URI, ResolveException, T1> fTrustExpressionEvaluatorFailureResolve,
                final F2<Trustmark, NonEmptyList<TrustmarkVerifierFailure>, T1> fTrustExpressionEvaluatorFailureVerify) {

            requireNonNull(fTrustExpressionEvaluatorFailureURI);
            requireNonNull(fTrustExpressionEvaluatorFailureResolve);
            requireNonNull(fTrustExpressionEvaluatorFailureVerify);

            return fTrustExpressionEvaluatorFailureVerify.f(trustmark, trustmarkVerificationFailureNonEmptyList);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final TrustExpressionEvaluatorFailureVerify that = (TrustExpressionEvaluatorFailureVerify) o;
            return trustmark.equals(that.trustmark) && trustmarkVerificationFailureNonEmptyList.equals(that.trustmarkVerificationFailureNonEmptyList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trustmark, trustmarkVerificationFailureNonEmptyList);
        }
    }

    public static final TrustExpressionEvaluatorFailureURI evaluatorFailureURI(final String uriString, final URISyntaxException exception) {
        return new TrustExpressionEvaluatorFailureURI(uriString, exception);
    }

    public static final TrustExpressionEvaluatorFailureResolve evaluatorFailureResolve(final URI uri, final ResolveException exception) {
        return new TrustExpressionEvaluatorFailureResolve(uri, exception);
    }

    public static final TrustExpressionEvaluatorFailureVerify evaluatorFailureVerify(final Trustmark trustmark, final NonEmptyList<TrustmarkVerifierFailure> trustmarkVerificationFailureNonEmptyList) {
        return new TrustExpressionEvaluatorFailureVerify(trustmark, trustmarkVerificationFailureNonEmptyList);
    }

    public static final String messageFor(final TrustExpressionEvaluatorFailure trustExpressionEvaluatorFailure) {

        return trustExpressionEvaluatorFailure.match(
                (uriString, runtimeException) -> format("The system could not parse the URI ('%s'); message: %s", uriString, runtimeException.getMessage()),
                (uri, resolveException) -> format("The system could not resolve the URI ('%s'); message: %s", uri.toString(), resolveException.getMessage()),
                (trustmark, trustmarkVerificationFailureNonEmptyList) -> format("The system could not verify the trustmark ('%s'); message: %s", trustmark.getIdentifier().toString(), join(System.lineSeparator(), trustmarkVerificationFailureNonEmptyList.map(TrustmarkVerifierFailure::messageFor).toList().toJavaList())));
    }
}
