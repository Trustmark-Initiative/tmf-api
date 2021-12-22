package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidatorFailure;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.Try;
import org.gtri.fj.function.TryEffect;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.KeySelector;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.stream.IntStream;

import static edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureFailure.failureCanonicalizationMethod;
import static edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureFailure.failureDigestMethod;
import static edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureFailure.failureKeyValue;
import static edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureFailure.failureSign;
import static edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureFailure.failureSignatureMethod;
import static edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureFailure.failureTransform;
import static edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidatorFailure.failureSignatureNodeList;
import static edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidatorFailure.failureSignatureSignedInfoReference;
import static edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidatorFailure.failureSignatureValue;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.iteratorList;
import static org.gtri.fj.data.List.sequenceValidation;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.NonEmptyList.nonEmptyListSemigroup;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.condition;

public class XmlSignatureUtility {
    private XmlSignatureUtility() {
    }

    public static Validation<NonEmptyList<XmlSignatureFailure>, Document> sign(
            final Document document,
            final String uri,
            final PrivateKey privateKey,
            final X509Certificate x509Certificate) {

        requireNonNull(document);
        requireNonNull(uri);
        requireNonNull(privateKey);
        requireNonNull(x509Certificate);

        // Create a DOMSignContext and specify the RSA PrivateKey and
        // location of the resulting XMLSignature's parent element.
        final Element parent = document.getDocumentElement();
        final NodeList childNodes = parent.getChildNodes();
        final DOMSignContext domSignContext = IntStream
                .range(0, childNodes.getLength())
                .mapToObj(childNodes::item)
                .filter(node -> node != null && node instanceof Element)
                .findFirst()
                .map(nextSibling -> new DOMSignContext(privateKey, parent, nextSibling))
                .orElse(new DOMSignContext(privateKey, parent));
        domSignContext.setDefaultNamespacePrefix("ds");

        // Create a DOM XMLSignatureFactory that will be used to generate the enveloped signature.
        final XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance("DOM");

        // Create the KeyInfo containing the X509Data.
        final KeyInfoFactory keyInfoFactory = xmlSignatureFactory.getKeyInfoFactory();

        return accumulate(
                Try.f(() -> xmlSignatureFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null))._1().f().map(exception -> nel(failureCanonicalizationMethod(exception))),
                Try.f(() -> xmlSignatureFactory.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null))._1().f().map(exception -> nel(failureSignatureMethod(exception))),
                Try.f(() -> xmlSignatureFactory.newDigestMethod(DigestMethod.SHA256, null))._1().f().map(exception -> nel(failureDigestMethod(exception))),
                Try.f(() -> xmlSignatureFactory.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec) null))._1().f().map(exception -> nel(failureTransform(exception))),
                Try.f(() -> xmlSignatureFactory.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", (TransformParameterSpec) null))._1().f().map(exception -> nel(failureTransform(exception))),
                Try.f(() -> keyInfoFactory.newKeyValue(x509Certificate.getPublicKey()))._1().f().map(exception -> nel(failureKeyValue(exception))),
                (canonicalizationMethod, signatureMethod, digestMethod, transform1, transform2, keyValue) ->

                        // Create the XMLSignature, but don't sign it yet.
                        xmlSignatureFactory.newXMLSignature(
                                xmlSignatureFactory.newSignedInfo(
                                        canonicalizationMethod,
                                        signatureMethod,
                                        singletonList(
                                                xmlSignatureFactory.newReference(
                                                        uri,
                                                        digestMethod,
                                                        arrayList(
                                                                transform1,
                                                                transform2).toJavaList(),
                                                        null,
                                                        null))),
                                keyInfoFactory.newKeyInfo(arrayList(
                                        keyValue,
                                        keyInfoFactory.newX509Data(arrayList(x509Certificate).toJavaList())).toJavaList())))
                // Marshal, generate, and sign the enveloped signature.
                .bind(xmlSignature -> TryEffect.f(() -> xmlSignature.sign(domSignContext))._1().f().map(exception -> nel(failureSign(exception))))
                .map(unit -> document);
    }

    public static Validation<NonEmptyList<XmlSignatureValidatorFailure>, XMLSignature> validate(
            final Document document,
            final KeySelector keySelector) {

        requireNonNull(document);
        requireNonNull(keySelector);

        // unmarshal the xml signature
        final F1<DOMValidateContext, Validation<NonEmptyList<XmlSignatureValidatorFailure>, XMLSignature>> unmarshalXMLSignature =
                (domValidateContext) -> Try.<XMLSignature, MarshalException>f(() -> XMLSignatureFactory.getInstance("DOM").unmarshalXMLSignature(domValidateContext))._1()
                        .f().map(marshalException -> nel(XmlSignatureValidatorFailure.failureUnmarshalXmlSignature(document, marshalException)));

        // validate the xml signature in the given context
        final F2<XMLSignature, DOMValidateContext, Validation<NonEmptyList<XmlSignatureValidatorFailure>, Boolean>> validate =
                (xmlSignature, domValidateContext) -> Try.<Boolean, XMLSignatureException>f(() -> xmlSignature.validate(domValidateContext))._1()
                        .f().map(xmlSignatureException -> nel(XmlSignatureValidatorFailure.failureXMLSignatureException(document, xmlSignatureException)));

        // was the signature cryptographically verified?
        final F2<XMLSignature.SignatureValue, DOMValidateContext, Validation<NonEmptyList<XmlSignatureValidatorFailure>, Boolean>> validateSignatureValue =
                (signatureValue, domValidateContext) -> Try.<Boolean, XMLSignatureException>f(() -> signatureValue.validate(domValidateContext))._1()
                        .f().map(xmlSignatureException -> nel(XmlSignatureValidatorFailure.failureXMLSignatureException(document, xmlSignatureException)));

        // was the digest in the reference verified?
        final F2<Reference, DOMValidateContext, Validation<NonEmptyList<XmlSignatureValidatorFailure>, Boolean>> validateReference =
                (reference, domValidateContext) -> Try.<Boolean, XMLSignatureException>f(() -> reference.validate(domValidateContext))._1()
                        .f().map(xmlSignatureException -> nel(XmlSignatureValidatorFailure.failureXMLSignatureException(document, xmlSignatureException)));

        // accumulate the results of the signature verification and the digest verification
        final F2<XMLSignature, DOMValidateContext, Validation<NonEmptyList<XmlSignatureValidatorFailure>, XMLSignature>> validateAll =
                ((xmlSignature, domValidateContext) -> accumulate(
                        validateSignatureValue.f(xmlSignature.getSignatureValue(), domValidateContext)
                                .bind(valid -> condition(valid, nel(failureSignatureValue(document)), valid)),
                        sequenceValidation(
                                nonEmptyListSemigroup(),
                                iteratorList((Iterator<Reference>) xmlSignature.getSignedInfo().getReferences().iterator())
                                        .map(reference -> validateReference.f(reference, domValidateContext)
                                                .bind(valid -> condition(valid, nel(failureSignatureSignedInfoReference(document, reference)), valid)))),
                        (validSignatureValue, validReferenceList) -> xmlSignature));

        return signature(document)
                .bind(signatureNode -> {

                    final DOMValidateContext domValidateContext = new DOMValidateContext(keySelector, signatureNode);

                    return unmarshalXMLSignature.f(domValidateContext)
                            .bind(xmlSignature -> validate.f(xmlSignature, domValidateContext)
                                    .bind(valid -> validateAll.f(xmlSignature, domValidateContext)));
                });
    }

    public static Validation<NonEmptyList<XmlSignatureValidatorFailure>, Node> signature(
            final Document document) {

        requireNonNull(document);

        final NodeList signatureNodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

        return condition(signatureNodeList.getLength() == 1, nel(failureSignatureNodeList(document)), signatureNodeList)
                .map(signatureNodeListInner -> signatureNodeListInner.item(0));
    }

    public static List<X509Certificate> certificateList(
            final XMLSignature xmlSignature) {

        requireNonNull(xmlSignature);

        return iterableList((java.util.List<XMLStructure>) xmlSignature.getKeyInfo().getContent())
                .filter(xmlStructure -> xmlStructure instanceof X509Data)
                .map(xmlStructure -> (X509Data) xmlStructure)
                .bind(x509Data -> iterableList((java.util.List<Object>) x509Data.getContent()))
                .filter(object -> object instanceof X509Certificate)
                .map(object -> (X509Certificate) object);
    }
}
