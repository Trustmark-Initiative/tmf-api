package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

/**
 * Can validate trustmark signatures.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface SignatureValidator {

	/**
	 * Validates the signature on the supplied trustmark.
	 * 
	 * @param trustmark
	 *            The trustmark whose signature is to be validated.
	 * @throws SignatureValidationException
	 */
	public void validateSignature(Trustmark trustmark)
			throws SignatureValidationException;
}
