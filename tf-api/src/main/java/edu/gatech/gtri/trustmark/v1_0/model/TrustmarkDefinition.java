package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import edu.gatech.gtri.trustmark.v1_0.model.expression.ExpressionTree;

/**
 * Representation of a Trustmark Definition of the Trustmark Framework. A
 * Trustmark Definition specifies the conformance criteria that a Trustmark
 * Recipient must meet, as well as the formal assessment process that a
 * Trustmark Provider must perform to assess whether a Trustmark Recipient
 * qualifies for the Trustmark.
 *
 * @author erin.reddick
 * @author brad.lee (2015-12-07, Reworked to not have inner classes for simplicity of implementation).
 */
public interface TrustmarkDefinition extends HasSource, Sourced, TermsUsed {

    /**
     * A container element for Trustmark Definition Metadata. Guaranteed to be
     * non-null for validated objects.
     */
    public TrustmarkDefinition.Metadata getMetadata();

    /**
     * Prefatory text that applies to every conformance criterion. May be null.
     */
    public String getConformanceCriteriaPreface();

    /**
     * A container element for Trustmark Definition conformance criteria.
     * Guaranteed to be non-null for validated objects.
     */
    public List<ConformanceCriterion> getConformanceCriteria();


    /**
     * Prefatory text that applies to every assessment step. May be null.
     */
    public String getAssessmentStepPreface();

    /**
     * A container element for Trustmark Definition assessment steps. Guaranteed
     * to be non-null for validated objects.
     */
    public List<AssessmentStep> getAssessmentSteps();

    /**
     * A Boolean expression that indicates whether a Trustmark Provider may
     * issue a Trustmark to a Trustmark Recipient, based on the results of the
     * formal assessment process defined by this Trustark Definition. If the
     * issuance criteria evaluate to true, the Trustmark Provider MAY issue a
     * Trustmark under this Trustmark Definition to the Trustmark Recipient; if
     * the issuance criteria evaluate to false, the Trustmark Provider MUST NOT
     * issue the Trustmark. Appendix B of the Trustmark Framework Technical
     * Specification describes the syntax and semantics of this Boolean
     * expression. Guaranteed to be non-null for validated objects.
     */
    public String getIssuanceCriteria();

    /**
     * A container element for Trustmark Definition metadata.
     *
     * @author GTRI Trustmark Team
     */
    public interface Metadata extends TrustmarkFrameworkIdentifiedObject, Supersedable, Categorized, LegallyPublished {

        /**
         * The name of the attribute that refers to a Trustmark issued under
         * this Trustmark Definition. Guaranteed to be non-null for validated
         * objects.
         */
//        public URI getTrustmarkReferenceAttributeName();

        /**
         * The Trustmark Defining Organization that defined and published this
         * Trustmark Definition. Section 5.1 of the Trustmark Framework
         * Technical Specification contains normative language pertaining to the
         * selection of Trustmark Defining Organization Identifiers. Guaranteed
         * to be non-null for validated objects.
         */
        public Entity getTrustmarkDefiningOrganization();

        /**
         * A description of the intended communities and stakeholder groups to
         * which the Trustmark Definition may apply. May be null.
         */
        public String getTargetStakeholderDescription();

        /**
         * A description of the intended organizations to which Trustmarks would
         * be issued under this Trustmark Definition. May be null.
         */
        public String getTargetRecipientDescription();

        /**
         * A description of the intended Trustmark Relying Parties for
         * Trustmarks issued under this Trustmark Definition. May be null.
         */
        public String getTargetRelyingPartyDescription();

        /**
         * A description of the intended organizations that would act as
         * Trustmark Providers and issue Trustmarks under this Trustmark
         * Definition. May be null.
         */
        public String getTargetProviderDescription();

        /**
         * A description of the criteria that an organization must meet to
         * become eligible to act as a Trustmark Provider and issue Trustmarks
         * under this Trustmark Definition. If this element is absent, any
         * organization may act as a Trustmark Provider and issue Trustmarks
         * under this Trustmark Definition. May be null.
         */
        public String getProviderEligibilityCriteria();

        /**
         * A description of the qualifications that an individual must possess
         * to act as an assessor on behalf of a Trustmark Provider that issues
         * Trustmarks under this Trustmark Definition. If this element is
         * absent, any individual that is an employee or contractor for the
         * Trustmark Provider may act as an assessor on behalf of a Trustmark
         * Provider that issues Trustmarks under this Trustmark Definition. May
         * be null.
         */
        public String getAssessorQualificationsDescription();

        /**
         * A description of the criteria that, if triggered, would require that
         * the Trustmark Provider revoke a Trustmark issued under this Trustmark
         * Definition. If this property is absent, the Trustmark Provider must
         * revoke a Trustmark issued under this Trustmark Definition upon
         * discovery that the Trustmark Recipient no longer fulfills one or more
         * of the conformance criteria in this Trustmark Definition. May be
         * null.
         */
        public String getTrustmarkRevocationCriteria();

        /**
         * A description of the normative requirements for populating the
         * Extension element of a Trustmark issued under this Trustmark
         * Definition. May be null.
         */
        public String getExtensionDescription();

    }

    /**
     * Iterates all assessment steps and returns the set of all parameters in this Trustmark Definition.
     */
    public Set<TrustmarkDefinitionParameter> getAllParameters();

}//end TrustmarkDefinition