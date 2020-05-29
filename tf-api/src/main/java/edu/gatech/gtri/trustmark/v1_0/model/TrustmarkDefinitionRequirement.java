package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.List;

/**
 * Models a Trustmark Definition Requirement which contains a reference to a
 * Trustmark Definition as well as a List of acceptable Trustmark Providers for
 * Trustmarks of the referenced Trustmark Definition.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TrustmarkDefinitionRequirement extends AbstractTIPReference {

	/**
	 * A List of references to Trustmark Providers that issue Trustmarks under
	 * the Trustmark Definition that is the subject of this Trustmark Definition
	 * Requirement. Guaranteed to be non-null. May be empty. An empty List means
	 * that any Provider is acceptable for this Trustmark Definition
	 * Requirement.
	 */
	public List<Entity> getProviderReferences();

}
