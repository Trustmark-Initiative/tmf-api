/**
 * GTRI copyright
 */

package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

/**
 * Verifies trustworthiness of Trustmarks in accordance with the Trustmark
 * Framework Technical Specification, v1.0, Section 5.5.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TrustmarkVerifier {

	/**
	 * Performs Trustmark trustworthiness verification steps from the Trustmark
	 * Framework Technical Specification, v1.0, Section 5.5. These steps are:
	 * <ol>
	 * <li>Verification of the Trustmark Provider Identifier</li>
	 * <li>Verification of the Trustmark’s Digital Signature</li>
	 * <li>Verification of the Trustmark Signing Certificate’s Common Name</li>
	 * <li>Verification of the Trustmark Signing</li>
	 * <li>Verification of the Trustmark’s Identifier</li>
	 * <li>Verification of Trustmark Non-Expiration</li>
	 * <li>Verification of Trustmark Non-Revocation</li>
	 * <li>Verification of Proper Organizational Scope via the Trustmark
	 * Recipient Identifier</li>
	 * <li>Verification of Proper Operational Scope via the Trustmark Definition
	 * </li>
	 * </ol>
	 * 
	 * This method always performs steps 1 - 6. Only if verifyStatus is true
	 * will this method will perform step 7. Only if verifyScope is true will
	 * this method perform steps 8 and 9.
	 * 
	 * TODO Add parameter for trustmark "context" (string). This context value
	 * will scope the TrustedProviderConfiguration and RecipientConfiguration; a
	 * TRP can have different configurations for different contexts.
	 *
	 * @param trustmark
	 *            The Trustmark to be verified.
	 * @param verifyStatus
	 *            Whether to verify the Trustmark's status.
	 * @param verifyScope
	 *            Whether to verify the Trustmark's organizational and
	 *            operational scope.
	 * @throws TrustVerificationException
	 */
	public void verifyTrustmarkTrustworthiness(Trustmark trustmark,
			String context, Boolean verifyStatus, Boolean verifyScope)
			throws TrustVerificationException;

	/**
	 * TODO use context parameter instead of policy; lookup policy based om context.
	 * 
	 * @param trustmark
	 * @param trustPolicy
	 */
	public void verifyTrustmarkTrustworthiness(Trustmark trustmark,
			Object trustPolicy)
			throws TrustVerificationException;
}
