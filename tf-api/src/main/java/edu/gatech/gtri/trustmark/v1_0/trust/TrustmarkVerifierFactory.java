package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;

/**
 * Knows how to create instances of {@link TrustmarkVerifier}.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TrustmarkVerifierFactory {

	/**
	 * Creates a TrustmarkVerifier that
	 * <ol>
	 * <ul>
	 * Trusts all trustmark providers
	 * </ul>
	 * <ul>
	 * Does not have trustmark recipient configuration
	 * </ul>
	 * <ul>
	 * Uses a simple, no-cache TSRProxy
	 * </ul>
	 * </ol>
	 * Since the TrustmarkVerifier will not have trustmark recipient
	 * configuration, a call to
	 * {@link TrustmarkVerifier#verifyTrustmarkTrustworthiness(edu.gatech.gtri.trustmark.v1_0.model.Trustmark, Boolean, Boolean)
	 * with a verifyScope value of true will fail.
	 */
	public TrustmarkVerifier createTrustmarkValidator();

	/**
	 * Creates a TrustmarkVerifier with the specified components.
	 * 
	 * TODO Scope via "context" string. 
	 * 
	 * @param tpConfig
	 * @param recipientConfiguration
	 * @param statusReportProxy
	 * @return
	 */
	public TrustmarkVerifier createTrustmarkValidator(
			TrustedProviderConfiguration tpConfig,
			RecipientConfiguration recipientConfiguration,
			TrustmarkStatusReportResolver statusReportProxy);

}
