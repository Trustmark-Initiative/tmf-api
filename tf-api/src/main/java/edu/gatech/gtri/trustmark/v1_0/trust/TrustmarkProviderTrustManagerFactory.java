package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;

import java.util.Set;

/**
 * Knows how to create {@link TrustmarkProviderTrustManager} instances.
 */
public interface TrustmarkProviderTrustManagerFactory {

	/**
	 * Creates a TrustmarkProviderTrustManager that trusts all trustmark
	 * providers.
	 * 
	 * @return A TrustmarkProviderTrustManager that trusts all trustmark
	 *         providers.
	 */
	public TrustmarkProviderTrustManager trustAllManager();

	/**
	 * Creates a TrustmarkProviderTrustManager that trusts the provided set of
	 * entities.
	 * 
	 * @param providers
	 * @return
	 */
	public TrustmarkProviderTrustManager trustOnlyManager(
			Set<Entity> providers);

}
