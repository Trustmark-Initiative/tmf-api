package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Set;

/**
 * Representation of a Trustmark of the Trustmark Framework. A Trustmark is a
 * machine-readable, cryptographically signed digital artifact that represents a
 * statement of conformance to a well-scoped set of trust and/or
 * interoperability requirements.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface Trustmark extends HasSource {

	/**
	 * The globally unique Trustmark Identifier for this Trustmark. Section 5.3
	 * of the Trustmark Framework Technical Specification further describes the
	 * normative requirements pertaining to Trustmark Identifiers.
	 */
	public URI getIdentifier();

	/**
	 * A reference to the Trustmark Definition under which this Trustmark was
	 * issued.
	 */
	public TrustmarkFrameworkIdentifiedObject getTrustmarkDefinitionReference();

	/**
	 * The date and time at which the Trustmark Provider issued this Trustmark
	 * to the Trustmark Recipient.
	 */
	public Date getIssueDateTime();

	/**
	 * The date and time at which this Trustmark expires.
	 */
	public Date getExpirationDateTime();

	/**
	 * The URL of the Trustmark Policy under which this Trustmark was issued.
	 */
	public URL getPolicyURL();

	/**
	 * The URL of the Trustmark Relying Party Agreement under which this
	 * Trustmark was issued.
	 */
	public URL getRelyingPartyAgreementURL();

	/**
	 * The URL at which the Trustmark Relying Party may query the Trustmark
	 * Provider for the current status of this Trustmark.
	 */
	public URL getStatusURL();

	/**
	 * A handle to the Trustmark Provider that issued this Trustmark. Note that
	 * the content of the <Identifier> of this Entity is the Trustmark
	 * Provider Identifier, as described in Section 5.2.1 of the Trustmark
	 * Framework Technical Specification.
	 */
	public Entity getProvider();

	/**
	 * A handle to the the Trustmark Recipient to which and about which this
	 * Trustmark was issued. Note that the content of the <Identifier> of this
	 * Entity is the Trustmark Recipient Identifier, as described in Section
	 * 5.2.4 of the Trustmark Framework Technical Specification.
	 */
	public Entity getRecipient();

	/**
	 * Additional content about this Trustmark, as normatively defined by the
	 * Trustmark Definition under which this Trustmark was issued.
	 */
	 public Extension getDefinitionExtension();

	/**
	 * Additional scontent about this Trustmark, provided at the discretion of
	 * the Trustmark Provider that issued this Trustmark.
	 */
	public Extension getProviderExtension();

    /**
     * A field implied by the existence of a value in getExceptionInfo()
     */
    public Boolean hasExceptions();

	/**
	 * An optional string value which provides information about any exceptions that pertain to this trustmark.  Note
     * that if this field exists, then this Trustmark has exceptions and if this field does not exist then it does not.
     */
	public Set<String> getExceptionInfo();

    /**
     * Returns the set of parameter bindings that apply to this Trustmark.
     */
    public Set<TrustmarkParameterBinding> getParameterBindings();

    /**
     * Retursn the individual TrustmarkParameterBinding which apples to the given identifier, or null if it is not found.
     */
    public TrustmarkParameterBinding getParameterBinding(String identifier);

}
