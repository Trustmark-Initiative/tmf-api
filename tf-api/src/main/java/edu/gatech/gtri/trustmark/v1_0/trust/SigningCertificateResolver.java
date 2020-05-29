package edu.gatech.gtri.trustmark.v1_0.trust;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.Set;

/**
 * Can resolve trustmark signing certificates for trustmark providers.
 * 
 * @author GTRI Trustmark Team
 */
public interface SigningCertificateResolver {

	/**
	 * Resolves the certificate set for the given trustmark provider.
	 * 
	 * @param providerID
	 *            The URI of the trustmark provider.
	 * @return The set of trustmark signing certificates for the given trustmark
	 *         provider.
	 */
	public Set<X509Certificate> getCertificates(URI providerID);

}// end SigningCertificateResolver