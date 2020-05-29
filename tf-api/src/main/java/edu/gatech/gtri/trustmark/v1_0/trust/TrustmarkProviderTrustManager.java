package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

/**
 * Determines trust in trustmark providers.
 * 
 * @author GTRI Trustmark Team
 */
public interface TrustmarkProviderTrustManager {

	/**
	 * Returns TRUE if the given provider is trusted in general, and false
	 * otherwise.
	 * 
	 * TODO update documentation
	 */
	public Boolean isProviderTrusted(Entity provider);

	/**
	 * Is Provider trusted to issue this Trustmark?
	 * 
	 * @param trustmark
	 * @return
	 */
	public Boolean isProviderTrusted(Trustmark trustmark);
}
