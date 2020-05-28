package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Set;

/**
 * Describes an assessment step: a step in the formal assessment process that
 * the Trustmark Provider must perform to assess whether a Trustmark Recipient
 * qualifies for a Trustmark. It includes a number for the step, a name for the
 * step, a question, and an artifact. The answer to the question helps determine
 * whether the Trustmark Recipient meets the conformance criteria for the
 * Trustmark, and the artifact serves as evidence in support of the answer
 * provided by the Trustmark Provider
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface AssessmentStep {

	/**
	 * A positive integer; it is the sequence number of this assessment step.
	 * Guaranteed to be non-null.
	 */
	public Integer getNumber();

	/**
	 * A short descriptor of this assessment step. Guaranteed to be non-null.
	 */
	public String getName();

	/**
	 * A question to the Trustmark Provider about conformance of the Trustmark
	 * Recipient to the referenced conformance criteria. The text of the
	 * question MUST be structured such that the answer is exactly one of “yes”,
	 * “no”, and “not applicable”. This question MAY include instructions that
	 * the Trustmark Provider must follow when completing the assessment step.
	 * Guaranteed to be non-null.
	 */
	public String getDescription();

	/**
	 * A set of references to conformance criteria that motivates this
	 * assessment step. Guaranteed to be non-null and non-empty.
	 */
	public Set<ConformanceCriterion> getConformanceCriteria();

	/**
	 * A set of artifacts that serve as evidence that the Trustmark Recipient
	 * meets the referenced conformance criteria of the parent Trustmark
	 * Definition. Guaranteed to be non-null. May be empty.
	 */
	public Set<Artifact> getArtifacts();

	/**
	 * Contains the Set of TrustmarkDefinitionParameter objects relevant to this AssessmentStep.  IE, these should
	 * be displayed for collection when this step is displayed.
     */
	public Set<TrustmarkDefinitionParameter> getParameters();

	/**
	 * The structural element identifier. This id MUST appear in the Issuance
	 * Critera ({@link TrustmarkDefinition#getIssuanceCriteria()}) property of
	 * the parent Trustmark Definition. The value of the id attribute MUST NOT
	 * be "ALL" or "NONE", as these are reserved words within the Issuance
	 * Criteria expression language. Appendix B of the Trustmark Framework
	 * Technical Specification describes the syntax and semantics of the
	 * contents of the Issuance Criteria property. Guaranteed to be non-null.
	 */
	public String getId();
}
