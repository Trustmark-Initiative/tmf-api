package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URL;
import java.util.List;

/**
 * Describes a point of contact for an organization or a business entity.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface Contact {

	/**
	 * Indicates the kind of this point of contact. Guaranteed to be non-null.
	 */
	public ContactKindCode getKind();

	/**
	 * The name of a responder (e.g., a person, department, or job title)
	 * through which this organization or business entity may be contacted, if
	 * any. May be null.
	 */
	public String getResponder();

	/**
	 * List of electronic mailing addresses at which this organization or
	 * business entity may be contacted. Guaranteed to be non-null. Guaranteed
	 * to have at least one element.
	 */
	public List<String> getEmails();

	/**
	 * Returns the first email encountered.  Shortcut for getEmails().get(0)
	 * @return
     */
	public String getDefaultEmail();

	/**
	 * List of telephone number at which this organization or business entity
	 * may be contacted, if any. Guaranteed to be non-null. May be empty.
	 */
	public List<String> getTelephones();

    /**
     * Shortcut for getting the first telephone number.  Same as getTelephones().get(0)
     */
    public String getDefaultTelephone();

	/**
	 * List of full text of the physical addresses at which this organization or
	 * business entity may be contacted, if any. Guaranteed to be non-null. May
	 * be empty.
	 */
	public List<String> getPhysicalAddresses();

    /**
     * Returns the first physical address found, same as getPhysicalAddresses().get(0).
     */
    public String getDefaultPhysicalAddress();

	/**
	 * List of full text of the mailing addresses at which this organization or
	 * business entity may be contacted, if any. Guaranteed to be non-null. May
	 * be empty.
	 */
	public List<String> getMailingAddresses();

    /**
     * Returns the first mailing address found, same as getMailingAddresses().get(0).
     */
    public String getDefaultMailingAddress();

	/**
	 * List of website address at which this organization or business entity may
	 * be contacted, if any. Guaranteed to be non-null. May be empty.
	 */
	public List<URL> getWebsiteURLs();

    /**
     * Returns the first website URL encountered.  Same as getWebsiteURLs().get(0).
     */
    public URL getDefaultWebsiteURL();

	/**
	 * Additional optional text content about this point of contact. May be
	 * null.
	 */
	public String getNotes();
}
