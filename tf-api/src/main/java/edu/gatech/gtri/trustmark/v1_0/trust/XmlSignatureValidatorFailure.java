package edu.gatech.gtri.trustmark.v1_0.trust;

import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.w3c.dom.Document;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignatureException;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public abstract class XmlSignatureValidatorFailure {

    public abstract <T1> T1 match(
            final F1<Document, T1> fFailureSignatureNodeList,
            final F2<Document, MarshalException, T1> fFailureMarshallException,
            final F2<Document, XMLSignatureException, T1> fFailureXMLSignatureException,
            final F1<Document, T1> fFailureSignatureValue,
            final F2<Document, Reference, T1> fFailureSignatureSignedInfoReference);

    public static final class XmlSignatureValidationFailureSignatureNodeList extends XmlSignatureValidatorFailure {

        private final Document document;

        private XmlSignatureValidationFailureSignatureNodeList(final Document document) {

            requireNonNull(document);

            this.document = document;
        }

        public <T1> T1 match(
                final F1<Document, T1> fFailureSignatureNodeList,
                final F2<Document, MarshalException, T1> fFailureMarshallException,
                final F2<Document, XMLSignatureException, T1> fFailureXMLSignatureException,
                final F1<Document, T1> fFailureSignatureValue,
                final F2<Document, Reference, T1> fFailureSignatureSignedInfoReference) {

            requireNonNull(fFailureSignatureNodeList);
            requireNonNull(fFailureMarshallException);
            requireNonNull(fFailureXMLSignatureException);
            requireNonNull(fFailureSignatureValue);
            requireNonNull(fFailureSignatureSignedInfoReference);

            return fFailureSignatureNodeList.f(document);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureValidationFailureSignatureNodeList that = (XmlSignatureValidationFailureSignatureNodeList) o;
            return document.equals(that.document);
        }

        @Override
        public int hashCode() {
            return Objects.hash(document);
        }

        @Override
        public String toString() {
            return "XmlSignatureValidationFailureSignatureNodeList{" +
                    "document=" + document +
                    '}';
        }
    }

    public static final class XmlSignatureValidatorFailureMarshalException extends XmlSignatureValidatorFailure {

        private final Document document;
        private final MarshalException marshalException;

        private XmlSignatureValidatorFailureMarshalException(final Document document, final MarshalException marshalException) {

            requireNonNull(document);
            requireNonNull(marshalException);

            this.document = document;
            this.marshalException = marshalException;
        }

        public <T1> T1 match(
                final F1<Document, T1> fFailureSignatureNodeList,
                final F2<Document, MarshalException, T1> fFailureMarshallException,
                final F2<Document, XMLSignatureException, T1> fFailureXMLSignatureException,
                final F1<Document, T1> fFailureSignatureValue,
                final F2<Document, Reference, T1> fFailureSignatureSignedInfoReference) {

            requireNonNull(fFailureSignatureNodeList);
            requireNonNull(fFailureMarshallException);
            requireNonNull(fFailureXMLSignatureException);
            requireNonNull(fFailureSignatureValue);
            requireNonNull(fFailureSignatureSignedInfoReference);

            return fFailureMarshallException.f(document, marshalException);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureValidatorFailureMarshalException that = (XmlSignatureValidatorFailureMarshalException) o;
            return document.equals(that.document) && marshalException.equals(that.marshalException);
        }

        @Override
        public int hashCode() {
            return Objects.hash(document, marshalException);
        }

        @Override
        public String toString() {
            return "XmlSignatureValidationFailureMarshalException{" +
                    "document=" + document +
                    ", marshalException=" + marshalException +
                    '}';
        }
    }

    public static final class XmlSignatureValidationFailureXMLSignatureException extends XmlSignatureValidatorFailure {

        private final Document document;
        private final XMLSignatureException xmlSignatureException;

        private XmlSignatureValidationFailureXMLSignatureException(final Document document, final XMLSignatureException xmlSignatureException) {

            requireNonNull(document);
            requireNonNull(xmlSignatureException);

            this.document = document;
            this.xmlSignatureException = xmlSignatureException;
        }

        public <T1> T1 match(
                final F1<Document, T1> fFailureSignatureNodeList,
                final F2<Document, MarshalException, T1> fFailureMarshallException,
                final F2<Document, XMLSignatureException, T1> fFailureXMLSignatureException,
                final F1<Document, T1> fFailureSignatureValue,
                final F2<Document, Reference, T1> fFailureSignatureSignedInfoReference) {

            requireNonNull(fFailureSignatureNodeList);
            requireNonNull(fFailureMarshallException);
            requireNonNull(fFailureXMLSignatureException);
            requireNonNull(fFailureSignatureValue);
            requireNonNull(fFailureSignatureSignedInfoReference);

            return fFailureXMLSignatureException.f(document, xmlSignatureException);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureValidationFailureXMLSignatureException that = (XmlSignatureValidationFailureXMLSignatureException) o;
            return document.equals(that.document) && xmlSignatureException.equals(that.xmlSignatureException);
        }

        @Override
        public int hashCode() {
            return Objects.hash(document, xmlSignatureException);
        }

        @Override
        public String toString() {
            return "XmlSignatureValidationFailureXMLSignatureException{" +
                    "document=" + document +
                    ", xmlSignatureException=" + xmlSignatureException +
                    '}';
        }
    }

    public static final class XmlSignatureValidationFailureSignatureValue extends XmlSignatureValidatorFailure {

        private final Document document;

        private XmlSignatureValidationFailureSignatureValue(final Document document) {

            requireNonNull(document);

            this.document = document;
        }

        public <T1> T1 match(
                final F1<Document, T1> fFailureSignatureNodeList,
                final F2<Document, MarshalException, T1> fFailureMarshallException,
                final F2<Document, XMLSignatureException, T1> fFailureXMLSignatureException,
                final F1<Document, T1> fFailureSignatureValue,
                final F2<Document, Reference, T1> fFailureSignatureSignedInfoReference) {

            requireNonNull(fFailureSignatureNodeList);
            requireNonNull(fFailureMarshallException);
            requireNonNull(fFailureXMLSignatureException);
            requireNonNull(fFailureSignatureValue);
            requireNonNull(fFailureSignatureSignedInfoReference);

            return fFailureSignatureValue.f(document);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureValidationFailureSignatureValue that = (XmlSignatureValidationFailureSignatureValue) o;
            return document.equals(that.document);
        }

        @Override
        public int hashCode() {
            return Objects.hash(document);
        }

        @Override
        public String toString() {
            return "XmlSignatureValidationFailureSignatureValue{" +
                    "document=" + document +
                    '}';
        }
    }

    public static final class XmlSignatureValidationFailureSignatureSignedInfoReference extends XmlSignatureValidatorFailure {

        private final Document document;
        private final Reference reference;

        private XmlSignatureValidationFailureSignatureSignedInfoReference(
                final Document document,
                final Reference reference) {

            requireNonNull(document);
            requireNonNull(reference);

            this.document = document;
            this.reference = reference;
        }

        public <T1> T1 match(
                final F1<Document, T1> fFailureSignatureNodeList,
                final F2<Document, MarshalException, T1> fFailureMarshallException,
                final F2<Document, XMLSignatureException, T1> fFailureXMLSignatureException,
                final F1<Document, T1> fFailureSignatureValue,
                final F2<Document, Reference, T1> fFailureSignatureSignedInfoReference) {

            requireNonNull(fFailureSignatureNodeList);
            requireNonNull(fFailureMarshallException);
            requireNonNull(fFailureXMLSignatureException);
            requireNonNull(fFailureSignatureValue);
            requireNonNull(fFailureSignatureSignedInfoReference);

            return fFailureSignatureSignedInfoReference.f(document, reference);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final XmlSignatureValidationFailureSignatureSignedInfoReference that = (XmlSignatureValidationFailureSignatureSignedInfoReference) o;
            return document.equals(that.document) && reference.equals(that.reference);
        }

        @Override
        public int hashCode() {
            return Objects.hash(document, reference);
        }

        @Override
        public String toString() {
            return "XmlSignatureValidationFailureSignatureSignedInfoReference{" +
                    "document=" + document +
                    ", reference=" + reference +
                    '}';
        }
    }

    public static XmlSignatureValidatorFailure failureSignatureNodeList(final Document document) {
        return new XmlSignatureValidationFailureSignatureNodeList(document);
    }

    public static XmlSignatureValidatorFailure failureUnmarshalXmlSignature(final Document document, final MarshalException marshalException) {
        return new XmlSignatureValidatorFailureMarshalException(document, marshalException);
    }

    public static XmlSignatureValidatorFailure failureXMLSignatureException(final Document document, final XMLSignatureException xmlSignatureException) {
        return new XmlSignatureValidationFailureXMLSignatureException(document, xmlSignatureException);
    }

    public static XmlSignatureValidatorFailure failureSignatureValue(final Document document) {
        return new XmlSignatureValidationFailureSignatureValue(document);
    }

    public static XmlSignatureValidatorFailure failureSignatureSignedInfoReference(final Document document, final Reference reference) {
        return new XmlSignatureValidationFailureSignatureSignedInfoReference(document, reference);
    }

    public static String messageFor(final XmlSignatureValidatorFailure xmlSignatureValidatorFailure) {

        return xmlSignatureValidatorFailure.match(
                (document) -> format("The system could not find an XML Signature."),
                (document, marshalException) -> format("The system could not unmarshal the XML Signature; message: '%s'.", marshalException.getMessage()),
                (document, xmlSignatureException) -> format("The system could not validate the XML Signature; message: '%s'.", xmlSignatureException.getMessage()),
                (document) -> format("The system could not cryptographically verify the signature."),
                (document, reference) -> format("The system could not verify the digest in the reference ('%s').", reference.getURI().toString()));
    }
}
