package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidator;
import edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidatorFailure;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import javax.xml.crypto.dsig.XMLSignature;

import static edu.gatech.gtri.trustmark.v1_0.impl.trust.TrustmarkUtility.documentFor;

public class XmlSignatureValidatorImpl implements XmlSignatureValidator {

    @Override
    public Validation<NonEmptyList<XmlSignatureValidatorFailure>, XMLSignature> validate(final Trustmark trustmark) {

        return XmlSignatureUtility.validate(documentFor(trustmark), new XmlSignatureKeySelector());
    }
}
