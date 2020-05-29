package edu.gatech.gtri.trustmark.v1_0.trust;

/**
 * Knows how to create instances of {@link SigningCertificateValidator}.
 *  
 * @author GTRI Trustmark Team
 *
 */
public interface SigningCertificateValidatorFactory {

	/**
	 * Responsible for building the {@link SigningCertificateValidator} object.
	 * 
	 */
	public SigningCertificateValidator createSigningCertificateValidator();

}
