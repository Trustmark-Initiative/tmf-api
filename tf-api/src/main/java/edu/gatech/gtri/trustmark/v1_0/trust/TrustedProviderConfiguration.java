package edu.gatech.gtri.trustmark.v1_0.trust;

/**
 * Information about a set of trusted trustmark providers.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TrustedProviderConfiguration {

	/**
	 * Gets the TrustmarkProviderTrustManager.
	 * @return
	 */
	public TrustmarkProviderTrustManager getTrustmarkProviderTrustManager();

	/**
	 * Gets the SigningCertificateResolver.
	 * @return
	 */
	public SigningCertificateResolver getSigningCertificateResolver();

	/**
	 * Gets the SigningCertificateValidator.
	 * @return
	 */
	public SigningCertificateValidator getSigningCertificateValidator();

}// end TrustedProviderConfiguration