package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import org.gtri.fj.function.F1;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class XmlSignatureFailure {
    private XmlSignatureFailure() {
    }

    public abstract <T1> T1 match(
            final F1<Exception, T1> fFailureCanonicalizationMethod,
            final F1<Exception, T1> fFailureSignatureMethod,
            final F1<Exception, T1> fFailureDigestMethod,
            final F1<Exception, T1> fFailureTransform,
            final F1<Exception, T1> fFailureKeyValue,
            final F1<Exception, T1> fFailureSign);

    private static final class XmlSignatureFailureCanonicalizationMethod extends XmlSignatureFailure {
        private final Exception exception;

        private XmlSignatureFailureCanonicalizationMethod(final Exception exception) {
            requireNonNull(exception);
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }

        public <T1> T1 match(
                final F1<Exception, T1> fFailureCanonicalizationMethod,
                final F1<Exception, T1> fFailureSignatureMethod,
                final F1<Exception, T1> fFailureDigestMethod,
                final F1<Exception, T1> fFailureTransform,
                final F1<Exception, T1> fFailureKeyValue,
                final F1<Exception, T1> fFailureSign) {

            requireNonNull(fFailureCanonicalizationMethod);
            requireNonNull(fFailureSignatureMethod);
            requireNonNull(fFailureDigestMethod);
            requireNonNull(fFailureTransform);
            requireNonNull(fFailureKeyValue);
            requireNonNull(fFailureSign);

            return fFailureCanonicalizationMethod.f(exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureFailureCanonicalizationMethod that = (XmlSignatureFailureCanonicalizationMethod) o;
            return exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exception);
        }

        @Override
        public String toString() {
            return "XmlSignatureFailureCanonicalizationMethod{" +
                    "exception=" + exception +
                    '}';
        }
    }

    private static final class XmlSignatureFailureSignatureMethod extends XmlSignatureFailure {
        private final Exception exception;

        private XmlSignatureFailureSignatureMethod(final Exception exception) {
            requireNonNull(exception);
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }

        public <T1> T1 match(
                final F1<Exception, T1> fFailureCanonicalizationMethod,
                final F1<Exception, T1> fFailureSignatureMethod,
                final F1<Exception, T1> fFailureDigestMethod,
                final F1<Exception, T1> fFailureTransform,
                final F1<Exception, T1> fFailureKeyValue,
                final F1<Exception, T1> fFailureSign) {

            requireNonNull(fFailureCanonicalizationMethod);
            requireNonNull(fFailureSignatureMethod);
            requireNonNull(fFailureDigestMethod);
            requireNonNull(fFailureTransform);
            requireNonNull(fFailureKeyValue);
            requireNonNull(fFailureSign);

            return fFailureSignatureMethod.f(exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureFailureSignatureMethod that = (XmlSignatureFailureSignatureMethod) o;
            return exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exception);
        }

        @Override
        public String toString() {
            return "XmlSignatureFailureSignatureMethod{" +
                    "exception=" + exception +
                    '}';
        }
    }

    private static final class XmlSignatureFailureDigestMethod extends XmlSignatureFailure {
        private final Exception exception;

        private XmlSignatureFailureDigestMethod(final Exception exception) {
            requireNonNull(exception);
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }

        public <T1> T1 match(
                final F1<Exception, T1> fFailureCanonicalizationMethod,
                final F1<Exception, T1> fFailureSignatureMethod,
                final F1<Exception, T1> fFailureDigestMethod,
                final F1<Exception, T1> fFailureTransform,
                final F1<Exception, T1> fFailureKeyValue,
                final F1<Exception, T1> fFailureSign) {

            requireNonNull(fFailureCanonicalizationMethod);
            requireNonNull(fFailureSignatureMethod);
            requireNonNull(fFailureDigestMethod);
            requireNonNull(fFailureTransform);
            requireNonNull(fFailureKeyValue);
            requireNonNull(fFailureSign);

            return fFailureDigestMethod.f(exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureFailureDigestMethod that = (XmlSignatureFailureDigestMethod) o;
            return exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exception);
        }

        @Override
        public String toString() {
            return "XmlSignatureFailureDigestMethod{" +
                    "exception=" + exception +
                    '}';
        }
    }

    private static final class XmlSignatureFailureTransform extends XmlSignatureFailure {
        private final Exception exception;

        private XmlSignatureFailureTransform(final Exception exception) {
            requireNonNull(exception);
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }

        public <T1> T1 match(
                final F1<Exception, T1> fFailureCanonicalizationMethod,
                final F1<Exception, T1> fFailureSignatureMethod,
                final F1<Exception, T1> fFailureDigestMethod,
                final F1<Exception, T1> fFailureTransform,
                final F1<Exception, T1> fFailureKeyValue,
                final F1<Exception, T1> fFailureSign) {

            requireNonNull(fFailureCanonicalizationMethod);
            requireNonNull(fFailureSignatureMethod);
            requireNonNull(fFailureDigestMethod);
            requireNonNull(fFailureTransform);
            requireNonNull(fFailureKeyValue);
            requireNonNull(fFailureSign);

            return fFailureTransform.f(exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureFailureTransform that = (XmlSignatureFailureTransform) o;
            return exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exception);
        }

        @Override
        public String toString() {
            return "XmlSignatureFailureTransform{" +
                    "exception=" + exception +
                    '}';
        }
    }

    private static final class XmlSignatureFailureKeyValue extends XmlSignatureFailure {
        private final Exception exception;

        private XmlSignatureFailureKeyValue(final Exception exception) {
            requireNonNull(exception);
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }

        public <T1> T1 match(
                final F1<Exception, T1> fFailureCanonicalizationMethod,
                final F1<Exception, T1> fFailureSignatureMethod,
                final F1<Exception, T1> fFailureDigestMethod,
                final F1<Exception, T1> fFailureTransform,
                final F1<Exception, T1> fFailureKeyValue,
                final F1<Exception, T1> fFailureSign) {

            requireNonNull(fFailureCanonicalizationMethod);
            requireNonNull(fFailureSignatureMethod);
            requireNonNull(fFailureDigestMethod);
            requireNonNull(fFailureTransform);
            requireNonNull(fFailureKeyValue);
            requireNonNull(fFailureSign);

            return fFailureKeyValue.f(exception);
        }

        @Override
        public String toString() {
            return "XmlSignatureFailureKeyValue{" +
                    "exception=" + exception +
                    '}';
        }
    }

    private static final class XmlSignatureFailureSign extends XmlSignatureFailure {
        private final Exception exception;

        private XmlSignatureFailureSign(final Exception exception) {
            requireNonNull(exception);
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }

        public <T1> T1 match(
                final F1<Exception, T1> fFailureCanonicalizationMethod,
                final F1<Exception, T1> fFailureSignatureMethod,
                final F1<Exception, T1> fFailureDigestMethod,
                final F1<Exception, T1> fFailureTransform,
                final F1<Exception, T1> fFailureKeyValue,
                final F1<Exception, T1> fFailureSign) {

            requireNonNull(fFailureCanonicalizationMethod);
            requireNonNull(fFailureSignatureMethod);
            requireNonNull(fFailureDigestMethod);
            requireNonNull(fFailureTransform);
            requireNonNull(fFailureKeyValue);
            requireNonNull(fFailureSign);

            return fFailureSign.f(exception);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureFailureSign that = (XmlSignatureFailureSign) o;
            return exception.equals(that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exception);
        }

        @Override
        public String toString() {
            return "XmlSignatureFailureSign{" +
                    "exception=" + exception +
                    '}';
        }
    }

    public static XmlSignatureFailure failureCanonicalizationMethod(final Exception exception) {
        return new XmlSignatureFailureCanonicalizationMethod(exception);
    }

    public static XmlSignatureFailure failureSignatureMethod(final Exception exception) {
        return new XmlSignatureFailureSignatureMethod(exception);
    }

    public static XmlSignatureFailure failureDigestMethod(final Exception exception) {
        return new XmlSignatureFailureDigestMethod(exception);
    }

    public static XmlSignatureFailure failureTransform(final Exception exception) {
        return new XmlSignatureFailureTransform(exception);
    }

    public static XmlSignatureFailure failureKeyValue(final Exception exception) {
        return new XmlSignatureFailureKeyValue(exception);
    }

    public static XmlSignatureFailure failureSign(final Exception exception) {
        return new XmlSignatureFailureSign(exception);
    }

    public static String messageFor(final XmlSignatureFailure xmlSignatureFailure) {

        return xmlSignatureFailure.match(
                Exception::getMessage,
                Exception::getMessage,
                Exception::getMessage,
                Exception::getMessage,
                Exception::getMessage,
                Exception::getMessage);
    }
}
