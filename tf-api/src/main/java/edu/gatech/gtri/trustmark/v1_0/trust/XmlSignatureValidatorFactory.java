package edu.gatech.gtri.trustmark.v1_0.trust;

/**
 * Knows how to create instances of {@link XmlSignatureValidator}.
 *
 * @author GTRI Trustmark Team
 */
public interface XmlSignatureValidatorFactory {

    /**
     * Responsible for building the {@link XmlSignatureValidator} object.
     */
    XmlSignatureValidator createDefaultValidator();
}
