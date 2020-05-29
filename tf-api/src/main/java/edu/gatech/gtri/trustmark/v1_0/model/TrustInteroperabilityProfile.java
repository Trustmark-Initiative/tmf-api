package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Representation of a Trust Interoperability Profile (TIP) from the Trustmark
 * Framework Technical Specification. A Trustmark Relying Party (TRP) can create
 * a TIP that expresses a trust and interoperability policy in terms of a set of
 * Trustmarks that a Trustmark Recipient must possess, in order to meet its
 * trust and interoperability requirements.
 * 
 * @author GTRI Trustmark Team
 */
public interface TrustInteroperabilityProfile
extends TrustmarkFrameworkIdentifiedObject, HasSource, Supersedable, Categorized, LegallyPublished, Sourced, TermsUsed, Primary, Moniker {


	/**
	 * The Trust Interoperability Profile Issuer Identifier for the Trust
	 * Interoperability Profile Issuer that issued this Trust Interoperability
	 * Profile. Section 5.6 of the Trustmark Framework Technical Specification
	 * contains normative language pertaining to the selection of Trust
	 * Interoperability Profile Issuer Identifiers.
	 */
	Entity getIssuer();


	/**
	 * A set of references to the Trustmark Definition Requirements and Trust
	 * Interoperability Profiles used by the trust expression for this Trust
	 * Interoperability Profile. Guaranteed to be non-null and non-empty.
	 */
	Collection<AbstractTIPReference> getReferences();

	/**
	 * A Boolean expression that indicates whether an entity satisfies this
	 * Trust Interoperability Profile based on its possession of specific
	 * Trustmarks issued under the referenced Trustmark Definitions and on its
	 * satisfaction of the referenced Trust Interoperability Profiles. If the
	 * trust expression evaluates to true, the entity satisfies the Trust
	 * Interoperability Profile; if the trust expression evaluates to false, the
	 * entity does not satisfy the Trust Interoperability Profile. Appendix C of
	 * the Trustmark Framework Technical Specification describes the syntax and
	 * semantics of this Boolean expression. Guranteed to be non-null and
	 * non-empty.
	 */
	String getTrustExpression();

}// end TrustInteroperabilityProfile