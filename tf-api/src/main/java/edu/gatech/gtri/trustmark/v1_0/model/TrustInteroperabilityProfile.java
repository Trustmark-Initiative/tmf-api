package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Collection;

/**
 * Implementations represent a Trust Interoperability Profile (TIP) from the
 * Trustmark Framework Technical Specification.
 *
 * A Trustmark Relying Party (TRP) creates a Trust Interoperability Profile to
 * express a trust interoperability policy in terms of a set of Trustmarks that
 * a Trustmark Recipient must possess in order to meet its trust
 * interoperability requirements.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustInteroperabilityProfile extends TrustmarkFrameworkReference {

    /**
     * Returns the value of the tf:id attribute of the document element.
     *
     * @return the value of the tf:id attribute of the document element.
     */
    String getId();

    /**
     * Returns the Trust Interoperability Profile Issuer that issued this Trust
     * Interoperability Profile.
     *
     * Section 5.6 of the Trustmark Framework Technical Specification contains
     * normative language pertaining to the selection of Trust Interoperability
     * Profile Issuer Identifiers.
     *
     * @return the Trust Interoperability Profile Issuer.
     */
    Entity getIssuer();

    /**
     * Returns a set of references to the Trustmark Definition Requirements and
     * Trust Interoperability Profiles the trust expression uses; non-null and
     * non-empty.
     *
     * @return a set of references to the Trustmark Definition Requirements and
     * Trust Interoperability Profiles the trust expression references; non-null
     * and non-empty
     */
    Collection<AbstractTIPReference> getReferences();

    /**
     * Returns a boolean expression that indicates whether an entity satisfies
     * this Trust Interoperability Profile based on whether the entity possesses
     * specific Trustmarks issued under the referenced Trustmark Definitions and
     * satisfies the referenced Trust Interoperability Profiles; non-null and
     * non-empty.
     *
     * If the trust expression evaluates to true, the entity satisfies the Trust
     * Interoperability Profile; if the trust expression evaluates to false, the
     * entity does not satisfy the Trust Interoperability Profile. Appendix C of
     * the Trustmark Framework Technical Specification describes the syntax and
     * semantics of this Boolean expression.
     *
     * @return a boolean expression that indicates whether an entity satisfies
     * this Trust Interoperability Profile based on whether the entity possesses
     * specific Trustmarks issued under the referenced Trustmark Definitions and
     * satisfies the referenced Trust Interoperability Profiles; non-null and
     * non-empty
     */
    String getTrustExpression();

    /**
     * Returns whether primary.
     *
     * @return whether primary
     */
    boolean isPrimary();

    /**
     * Returns the moniker.
     *
     * @return the moniker
     */
    String getMoniker();
}
