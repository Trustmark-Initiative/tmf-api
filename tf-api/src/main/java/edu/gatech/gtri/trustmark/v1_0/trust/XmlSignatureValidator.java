package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import javax.xml.crypto.dsig.XMLSignature;

/**
 * Can validate trustmark signatures.
 *
 * @author GTRI Trustmark Team
 */
public interface XmlSignatureValidator {

    /**
     * Validates the signature on the supplied trustmark.
     *
     * @param trustmark The trustmark whose signature is to be validated.
     */
    Validation<NonEmptyList<XmlSignatureValidatorFailure>, XMLSignature> validate(final Trustmark trustmark);
}
