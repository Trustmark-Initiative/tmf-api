package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;

import java.net.URI;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

/**
 * Knows how to create instances of {@link SigningCertificateResolver}.
 * 
 * @author GTRI Trustmark Team
 */
public interface SigningCertificateResolverFactory {

	/**
	 * Creates a SigningCertificateResolver from the provided map.
	 * 
	 * @param certificateMappings
	 *            Mapping from trustmark provider ID to the provider's set of
	 *            trustmark signing certificates.
	 * @return An appropriate SigningCertificateResolver.
	 */
	public SigningCertificateResolver createFromMap(
			Map<URI, Set<X509Certificate>> certificateMappings);

	// TODO This might exist in the far future
	/**
	 * Creates a SigningCertificateResolver that uses web service at
	 * webserviceURL for certificate resolution.
	 * 
	 * @param webserviceURL
	 *            The location of the webserver to use for resolution.
	 * @param uriResolver
	 *            A URIResolver, capable of caching resources as is necessary.
	 * @return An appropriate SigningCertificateResolver.
	 */
	public SigningCertificateResolver createFromOnlineRegistry(URL webserviceURL, URIResolver uriResolver);

}// end SigningCertificateResolverFactory