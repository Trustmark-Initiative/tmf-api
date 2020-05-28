package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.trust.SigningCertificateResolver;
import edu.gatech.gtri.trustmark.v1_0.trust.SigningCertificateValidator;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkProviderTrustManager;

/**
 * Information about a set of trusted trustmark providers.
 * 
 * @author GTRI Trustmark Team
 *
 */
public class TrustedProviderConfigurationImpl {

	/**
	 * If this constructor is used, then the TrustedProviderConfiguration
	 * components need to be set via the setter methods.
	 */
	public TrustedProviderConfigurationImpl() {
	}

	/**
	 * Takes in all of the TrustedProviderConfiguration components.
	 *
	 * @param trustmarkProviderTrustManager
	 * @param signingCertificateResolver
	 * @param signingCertificateValidator
	 */
	public TrustedProviderConfigurationImpl(
			TrustmarkProviderTrustManager trustmarkProviderTrustManager,
			SigningCertificateResolver signingCertificateResolver,
			SigningCertificateValidator signingCertificateValidator) {
		this.trustmarkProviderTrustManager = trustmarkProviderTrustManager;
		this.signingCertificateResolver = signingCertificateResolver;
		this.signingCertificateValidator = signingCertificateValidator;

	}

	/**
	 * Used for determining trust in trustmark providers.
	 */
	private TrustmarkProviderTrustManager trustmarkProviderTrustManager;

	/**
	 * Used for resolving the trustmark signing certificates of trustmark
	 * providers.
	 */
	private SigningCertificateResolver signingCertificateResolver;

	/**
	 * Used for validating trustmark signing certificates.
	 */
	private SigningCertificateValidator signingCertificateValidator;

	/**
	 * Gets the TrustmarkProviderTrustManager.
	 * @return
	 */
	public TrustmarkProviderTrustManager getTrustmarkProviderTrustManager() {
		return trustmarkProviderTrustManager;
	}

	/**
	 * Gets the SigningCertificateResolver.
	 * @return
	 */
	public SigningCertificateResolver getSigningCertificateResolver() {
		return signingCertificateResolver;
	}

	/**
	 * Gets the SigningCertificateValidator.
	 * @return
	 */
	public SigningCertificateValidator getSigningCertificateValidator() {
		return signingCertificateValidator;
	}

	/**
	 * Sets the SigningCertificateValidator.
	 * @param signingCertificateValidator
	 */
	public void setSigningCertificateValidator(
			SigningCertificateValidator signingCertificateValidator) {
		this.signingCertificateValidator = signingCertificateValidator;
	}

	/**
	 * Sets the SigningCertificateResolver.
	 * @param signingCertificateResolver
	 */
	public void setSigningCertificateResolver(
			SigningCertificateResolver signingCertificateResolver) {
		this.signingCertificateResolver = signingCertificateResolver;
	}

	/**
	 * Sets the TrustmarkProviderTrustManager.
	 * @param trustmarkProviderTrustManager
	 */
	public void setTrustmarkProviderTrustManager(
			TrustmarkProviderTrustManager trustmarkProviderTrustManager) {
		this.trustmarkProviderTrustManager = trustmarkProviderTrustManager;
	}

}// end TrustedProviderConfiguration