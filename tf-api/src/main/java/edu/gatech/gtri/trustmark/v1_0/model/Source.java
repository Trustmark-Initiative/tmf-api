package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * 
 * Describes the authoritative source for a conformance criterion in a Trustmark
 * Definition, including the identifier and the bibliographic information for
 * the authoritative source.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface Source {

	/**
	 * An identifier for the authoritative source. The text of the Trustmark
	 * Definition, such as the conformance criteria and the assessment steps,
	 * may use this identifier to refer to the source itself.
	 */
	public String getIdentifier();

	/**
	 * The bibliographic information for the authoritative source.
	 */
	public String getReference();
}
