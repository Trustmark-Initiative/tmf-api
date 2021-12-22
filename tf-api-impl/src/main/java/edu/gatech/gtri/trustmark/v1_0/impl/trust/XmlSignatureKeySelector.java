package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.sequenceValidation;
import static org.gtri.fj.data.NonEmptyList.fromList;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.NonEmptyList.nonEmptyListSemigroup;
import static org.gtri.fj.product.P.p;

/**
 * KeySelector which retrieves the public key out of the
 * KeyValue element and returns it.
 * NOTE: If the key algorithm doesn't match signature algorithm,
 * then the public key will be ignored.
 */
public final class XmlSignatureKeySelector extends KeySelector {

    private static final Logger log = LoggerFactory.getLogger(XmlSignatureKeySelector.class);

    private static final class XmlSignatureKeySelectorResult implements KeySelectorResult {

        private final PublicKey publicKey;

        XmlSignatureKeySelectorResult(
                final PublicKey publicKey) {

            requireNonNull(publicKey);

            this.publicKey = publicKey;
        }

        public Key getKey() {
            return publicKey;
        }
    }

    private enum XmlSignatureAlgorithm {
        DSA("DSA", "http://www.w3.org/2009/xmldsig11#dsa-sha256"),
        RSA("RSA", "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"),
        ;

        private final String name;
        private final String uri;

        XmlSignatureAlgorithm(
                final String name,
                final String uri) {

            requireNonNull(name);
            requireNonNull(uri);

            this.name = name;
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public String getUri() {
            return uri;
        }

        public <T1> T1 match(
                final F1<XmlSignatureAlgorithm, T1> fDSA,
                final F1<XmlSignatureAlgorithm, T1> fRSA,
                final F1<XmlSignatureAlgorithm, T1> fOther) {

            requireNonNull(fDSA);
            requireNonNull(fRSA);
            requireNonNull(fOther);

            return this == DSA ? fDSA.f(this) :
                    this == RSA ? fRSA.f(this) :
                            fOther.f(this);
        }

        public static Option<XmlSignatureAlgorithm> valueOf(final String name, final String uri) {

            requireNonNull(name);
            requireNonNull(uri);

            return arrayList(XmlSignatureAlgorithm.values())
                    .filter(xmlSignatureAlgorithm -> xmlSignatureAlgorithm.getName().equals(name) && xmlSignatureAlgorithm.getUri().equals(uri))
                    .headOption();
        }
    }

    public KeySelectorResult select(
            final KeyInfo keyInfo,
            final Purpose purpose,
            final AlgorithmMethod algorithmMethod,
            final XMLCryptoContext xmlCryptoContext)
            throws KeySelectorException {

        if (keyInfo == null) throw new KeySelectorException();
        requireNonNull(algorithmMethod);

        final SignatureMethod signatureMethod = (SignatureMethod) algorithmMethod;
        final List<XMLStructure> xmlStructureList = iterableList((java.util.List<XMLStructure>) keyInfo.getContent());

        // filter the keyInfo content to include only KeyValue instances
        // from KeyValue, get PublicKey (fail if KeyException)
        // from PublicKey, get XmlSignatureAlgorithm (fail if unrecognized)
        // from list, take head (fail if empty)

        final Validation<NonEmptyList<KeySelectorException>, XmlSignatureKeySelectorResult> validation =
                sequenceValidation(
                        nonEmptyListSemigroup(),
                        xmlStructureList
                                .filter(xmlStructure -> xmlStructure instanceof KeyValue)
                                .map(xmlStructure -> (KeyValue) xmlStructure)
                                .map(keyValue -> Try.<PublicKey, KeyException>f(() -> keyValue.getPublicKey())._1().f().map(keyException -> nel(new KeySelectorException(keyException)))))
                        .bind(publicKeyList -> sequenceValidation(
                                nonEmptyListSemigroup(),
                                publicKeyList
                                        .map(publicKey -> XmlSignatureAlgorithm.valueOf(publicKey.getAlgorithm(), signatureMethod.getAlgorithm())
                                                .toValidation(nel(new KeySelectorException(format("Unrecognized algorithm ('%s', '%s').", publicKey.getAlgorithm(), signatureMethod.getAlgorithm()))))
                                                .map(xmlSignatureAlgorithm -> p(publicKey, xmlSignatureAlgorithm))))
                                .bind(list -> fromList(list).toValidation(nel(new KeySelectorException("The system did not find a key with a recognized algorithm."))))
                                .map(NonEmptyList::head)
                                .map(P2::_1)
                                .map(XmlSignatureKeySelectorResult::new));

        if (validation.isFail()) {
            validation.fail().forEach(keySelectorException -> log.error(keySelectorException.getMessage()));

            throw validation.fail().head();
        } else {

            return validation.success();
        }
    }
}
