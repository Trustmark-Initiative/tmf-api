package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementations represent a Trustmark Definition from the Trustmark Framework
 * Technical Specification.
 *
 * A Trustmark Definition specifies the conformance criteria that a Trustmark
 * Recipient must meet as well as the formal assessment process that a Trustmark
 * Provider must perform to assess whether a Trustmark Recipient qualifies for
 * the Trustmark.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkDefinition extends TrustmarkFrameworkReference {

    /**
     * Returns Trustmark Definition Metadata; non-null.
     *
     * @return Trustmark Definition Metadata; non-null
     */
    TrustmarkDefinition.Metadata getMetadata();

    /**
     * Returns the value of the tf:id attribute of the document element.
     *
     * @return the value of the tf:id attribute of the document element.
     */
    String getId();

    /**
     * Returns prefatory text that applies to every conformance criterion;
     * nullable.
     *
     * @return prefatory text that applies to every conformance criterion;
     * nullable
     */
    String getConformanceCriteriaPreface();

    /**
     * Returns Trustmark Definition conformance criteria; non-null.
     *
     * @return Trustmark Definition conformance criteria; non-null
     */
    List<ConformanceCriterion> getConformanceCriteria();

    /**
     * Returns prefatory text that applies to every assessment step; nullable.
     *
     * @return prefatory text that applies to every assessment step; nullable
     */
    String getAssessmentStepPreface();

    /**
     * Returns Trustmark Definition assessment steps; non-null.
     *
     * @return Trustmark Definition assessment steps; non-null
     */
    List<AssessmentStep> getAssessmentSteps();

    /**
     * Returns a Boolean expression that indicates whether a Trustmark Provider
     * may issue a Trustmark to a Trustmark Recipient, based on the results of
     * the formal assessment process defined by this Trustark Definition;
     * non-null.
     *
     * If the issuance criteria evaluate to true, the Trustmark Provider MAY
     * issue a Trustmark under this Trustmark Definition to the Trustmark
     * Recipient; if the issuance criteria evaluate to false, the Trustmark
     * Provider MUST NOT issue the Trustmark.
     *
     * Appendix B of the Trustmark Framework Technical Specification describes
     * the syntax and semantics of this Boolean expression. Guaranteed to be
     * non-null for validated objects.
     *
     * @return a Boolean expression that indicates whether a Trustmark Provider
     * may issue a Trustmark to a Trustmark Recipient, based on the results of
     * the formal assessment process defined by this Trustark Definition;
     * non-null
     */
    String getIssuanceCriteria();

    /**
     * Returns the parameters in this Trustmark Definition; non-null.
     *
     * @return the parameters in this Trustmark Definition; non-null
     */
    default Set<TrustmarkDefinitionParameter> getAllParameters() {

        return getAssessmentSteps().stream()
                .flatMap(assessmentStep -> assessmentStep.getParameters().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Implementations represent Trustmark Definition metadata.
     *
     * @author GTRI Trustmark Team
     */
    interface Metadata extends TrustmarkFrameworkReference {

        /**
         * Returns the Trustmark Defining Organization that defined and
         * published this Trustmark Definition; non-null.
         *
         * Section 5.1 of the Trustmark Framework Technical Specification
         * contains normative language pertaining to the selection of Trustmark
         * Defining Organization Identifiers.
         *
         * @return the Trustmark Defining Organization that defined and
         * published this Trustmark Definition; non-null
         */
        Entity getTrustmarkDefiningOrganization();

        /**
         * Returns a description of the intended communities and stakeholder
         * groups to which the Trustmark Definition may apply; nullable.
         *
         * @return a description of the intended communities and stakeholder
         * groups to which the Trustmark Definition may apply; nullable
         */
        String getTargetStakeholderDescription();

        /**
         * Returns a description of the intended organizations to which
         * Trustmarks would be issued under this Trustmark Definition;
         * nullable.
         *
         * @return a description of the intended organizations to which
         * Trustmarks would be issued under this Trustmark Definition; nullable
         */
        String getTargetRecipientDescription();

        /**
         * Returns a description of the intended Trustmark Relying Parties for
         * Trustmarks issued under this Trustmark Definition; nullable.
         *
         * @return a description of the intended Trustmark Relying Parties for
         * Trustmarks issued under this Trustmark Definition; nullable
         */
        String getTargetRelyingPartyDescription();

        /**
         * Returns a description of the intended organizations that would act as
         * Trustmark Providers and issue Trustmarks under this Trustmark
         * Definition; nullable.
         *
         * @return a description of the intended organizations that would act as
         * Trustmark Providers and issue Trustmarks under this Trustmark
         * Definition; nullable
         */
        String getTargetProviderDescription();

        /**
         * Returns a description of the criteria that an organization must meet
         * to become eligible to act as a Trustmark Provider and issue
         * Trustmarks under this Trustmark Definition; nullable.
         *
         * If this element is absent, any organization may act as a Trustmark
         * Provider and issue Trustmarks under this Trustmark Definition.
         *
         * @return a description of the criteria that an organization must meet
         * to become eligible to act as a Trustmark Provider and issue
         * Trustmarks under this Trustmark Definition; nullable
         */
        String getProviderEligibilityCriteria();

        /**
         * Returns a description of the qualifications that an individual must
         * possess to act as an assessor on behalf of a Trustmark Provider that
         * issues Trustmarks under this Trustmark Definition; nullable.
         *
         * If this element is absent, any individual that is an employee or
         * contractor for the Trustmark Provider may act as an assessor on
         * behalf of a Trustmark Provider that issues Trustmarks under this
         * Trustmark Definition.
         *
         * @return a description of the qualifications that an individual must
         * possess to act as an assessor on behalf of a Trustmark Provider that
         * * issues Trustmarks under this Trustmark Definition; nullable
         */
        String getAssessorQualificationsDescription();

        /**
         * Returns a description of the criteria that, if triggered, would
         * require that the Trustmark Provider revoke a Trustmark issued under
         * this Trustmark Definition; nullable.
         *
         * If this property is absent, the Trustmark Provider must revoke a
         * Trustmark issued under this Trustmark Definition upon discovery that
         * the Trustmark Recipient no longer fulfills one or more of the
         * conformance criteria in this Trustmark Definition
         *
         * @return a description of the criteria that, if triggered, would
         * require that the Trustmark Provider revoke a Trustmark issued under
         * this Trustmark Definition; nullable
         */
        String getTrustmarkRevocationCriteria();

        /**
         * Returns a description of the normative requirements for populating
         * the Extension element of a Trustmark issued under this Trustmark
         * Definition; nullable.
         *
         * @return a description of the normative requirements for populating
         * the Extension element of a Trustmark issued under this Trustmark
         * Definition; nullable
         */
        String getExtensionDescription();
    }
}
