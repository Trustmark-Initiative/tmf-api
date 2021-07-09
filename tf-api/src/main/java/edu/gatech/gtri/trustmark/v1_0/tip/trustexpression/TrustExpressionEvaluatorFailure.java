package edu.gatech.gtri.trustmark.v1_0.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.gtri.fj.function.F2;

import java.net.URI;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionEvaluatorFailure {

    private TrustExpressionEvaluatorFailure() {
    }

    public abstract <T1> T1 match(
            F2<String, RuntimeException, T1> fTrustExpressionEvaluatorFailureURI,
            F2<URI, ResolveException, T1> fTrustExpressionEvaluatorFailureResolve);

    public static final class TrustExpressionEvaluatorFailureURI extends TrustExpressionEvaluatorFailure {

        private final String uriString;
        private final RuntimeException exception;

        private TrustExpressionEvaluatorFailureURI(
                final String uriString,
                final RuntimeException exception) {

            requireNonNull(uriString);
            requireNonNull(exception);

            this.uriString = uriString;
            this.exception = exception;
        }

        public String getUriString() {
            return uriString;
        }

        public RuntimeException getException() {
            return exception;
        }

        @Override
        public <T1> T1 match(
                final F2<String, RuntimeException, T1> fTrustExpressionEvaluatorFailureURI,
                final F2<URI, ResolveException, T1> fTrustExpressionEvaluatorFailureResolve) {

            requireNonNull(fTrustExpressionEvaluatorFailureURI);
            requireNonNull(fTrustExpressionEvaluatorFailureResolve);

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
                final F2<String, RuntimeException, T1> fTrustExpressionEvaluatorFailureURI,
                final F2<URI, ResolveException, T1> fTrustExpressionEvaluatorFailureResolve) {

            requireNonNull(fTrustExpressionEvaluatorFailureURI);
            requireNonNull(fTrustExpressionEvaluatorFailureResolve);

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

    // @formatter:off
    public static final TrustExpressionEvaluatorFailureURI     evaluatorFailureURI     (final String uriString, final RuntimeException exception) { return new TrustExpressionEvaluatorFailureURI     (uriString, exception); }
    public static final TrustExpressionEvaluatorFailureResolve evaluatorFailureResolve (final URI uri,          final ResolveException exception) { return new TrustExpressionEvaluatorFailureResolve (uri,       exception); }
    // @formatter:on
}
