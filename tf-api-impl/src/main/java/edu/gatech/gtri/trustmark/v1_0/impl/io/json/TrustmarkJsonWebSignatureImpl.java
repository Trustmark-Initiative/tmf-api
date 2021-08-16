package edu.gatech.gtri.trustmark.v1_0.impl.io.json;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.*;

import org.jose4j.jws.*;

/**
 * Generate the JSON Web Signature for the Trustmark JSON string.
 *
 * Created by robert on 11/19/2020.
 */
public class TrustmarkJsonWebSignatureImpl {

    private static final Logger log = LogManager.getLogger(TrustmarkJsonWebSignatureImpl.class);

    public TrustmarkJsonWebSignatureImpl() throws Exception {
    }


    String generateJsonWebSignature(PrivateKey privateKey, String json) throws Exception {

        // Create a new JsonWebSignature
        JsonWebSignature jws = new JsonWebSignature();

        // Set the payload, or signed content, on the JWS object
        jws.setPayload(json);

        // Set the signature algorithm on the JWS that will protect the integrity of the payload
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // Set the signing key on the JWS
        jws.setKey(privateKey);

        // Sign the JWS and produce the compact serialization or complete JWS representation, which
        // is a string consisting of three dot ('.') separated base64url-encoded
        // parts in the form Header.Payload.Signature
        String jwsCompactSerialization = jws.getCompactSerialization();

        return jwsCompactSerialization;
    }

    boolean validateJsonWebSignature(X509Certificate x509Certificate,  String signedJsonString) throws Exception {

        PublicKey publicKey = x509Certificate.getPublicKey();

        // Create a new JsonWebSignature
        JsonWebSignature jws = new JsonWebSignature();

        jws.setCompactSerialization(signedJsonString);
        jws.setKey(publicKey);

        // Check the signature
        boolean signatureVerified = jws.verifySignature();

        // Check core validation status
        if (!signatureVerified) {
            log.warn("Signature failed core validation");
        } else {
            log.warn("Signature passed core validation");
        }

        return signatureVerified;
    }

}//end TrustmarkXmlSignature