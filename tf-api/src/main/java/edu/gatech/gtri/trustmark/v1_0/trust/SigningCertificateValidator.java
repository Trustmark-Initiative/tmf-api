package edu.gatech.gtri.trustmark.v1_0.trust;

import java.net.URI;
import java.security.cert.X509Certificate;

/**
 * Can validate trustmark signing certificates.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface SigningCertificateValidator {

	/**
	 * Validates a trustmark signing certificate. Answers: Is the cert bound to
	 * the trustmark provider, and is it still valid (i.e., not revoked or
	 * expired)?
	 * 
	 * @param providerID
	 *            The trustmark provider ID.
	 * @param certificate
	 *            The signing certificate to validate.
	 * @throws SigningCertificateValidationException
	 *             If the certificate is not valid or if a condition occurred
	 *             that prevented validation to complete successfully.
	 */
	void validateSigningCertificate(URI providerID, X509Certificate certificate)
			throws SigningCertificateValidationException;

}
