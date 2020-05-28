package edu.gatech.gtri.trustmark.v1_0.trust;

/**
 * Knows how to create instances of {@link SignatureValidator}.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface SignatureValidatorFactory {

	/**
	 * Responsible for building the {@link SignatureValidator} object.
	 * 
	 */
	public SignatureValidator createSignatureValidator();

}
