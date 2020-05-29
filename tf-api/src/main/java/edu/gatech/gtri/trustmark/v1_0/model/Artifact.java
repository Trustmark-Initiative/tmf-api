package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Describes an artifact that serves as evidence that the Trustmark Recipient
 * meets the conformance criteria of the parent Trustmark Definition. It
 * includes the name and the description of the artifact.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface Artifact {

	/**
	 * The name of the artifact that serves as evidence that the Trustmark
	 * Recipient meets a normative requirement of the parent Trustmark
	 * Definition.
	 */
	public String getName();

	/**
	 * The description of this artifact, including its content and its file
	 * type.
	 */
	public String getDescription();
}
