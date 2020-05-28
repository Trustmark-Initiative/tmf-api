package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Describes an authoritative source, or a part of an authoritative source, for
 * a conformance criterion in a Trustmark Definition, including a reference to
 * the authoritative source, an optional identifier for the part of the
 * authoritative source, and a description of the location of the part in the
 * authoritative source.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface Citation {

	/**
	 * A reference to the authoritative source for a conformance criterion.
	 * Guaranteed to be non-null.
	 */
	public Source getSource();

	/**
	 * The description of the location of the part in the authoritative source,
	 * such as a page number. May be null.
	 */
	public String getDescription();
}
