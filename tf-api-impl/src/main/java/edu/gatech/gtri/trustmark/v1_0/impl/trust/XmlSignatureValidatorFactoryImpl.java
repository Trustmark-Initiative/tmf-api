package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidator;
import edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidatorFactory;

public class XmlSignatureValidatorFactoryImpl implements XmlSignatureValidatorFactory {

    @Override
    public XmlSignatureValidator createDefaultValidator() {
        return new XmlSignatureValidatorImpl();
    }
}
