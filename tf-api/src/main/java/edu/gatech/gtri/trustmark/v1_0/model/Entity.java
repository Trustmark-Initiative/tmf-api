package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URI;
import java.util.List;

/**
 * Describes an organization or a business entity, including Trustmark Defining
 * Organizations, Trustmark Providers, Trustmark Recipients, and Trust
 * Interoperability Profile Issuers.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface Entity {

	/**
	 * The globally unique identifier of an organization or a business entity.
	 * Section 5 of the Trustmark Framework Technical Framework contains
	 * normative language about how an organization or business entity must
	 * establish this identifier for various contexts (Trustmark Providers,
	 * Trustmark Recipients, etc.)
	 */
	public URI getIdentifier();

	/**
	 * The name for the organization or business entity.
	 */
	public String getName();

	/**
	 * A list of points of contact for this organization or business entity. At
	 * least one of the Contact instances MUST have a "Kind" element with
	 * the value PRIMARY.
	 */
	public List<Contact> getContacts();

	/**
	 * Returns the default contact, same as getContacts().get(0).
     */
	public Contact getDefaultContact();
}
