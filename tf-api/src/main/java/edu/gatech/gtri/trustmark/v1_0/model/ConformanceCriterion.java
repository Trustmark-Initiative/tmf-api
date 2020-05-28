package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Set;

/**
 * Describes a conformance criterion: a normative requirement that a Trustmark
 * Recipient must meet to receive a Trustmark issued under the parent Trustmark
 * Definition. It includes a number for the requirement, a name for the
 * requirement, a description of the requirement, and zero or more citations of
 * the authoritative source(s) of the requirement.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface ConformanceCriterion {

	/**
	 * A positive integer; it is the sequence number of this normative
	 * requirement. Guaranteed to be non-null.
	 */
	public Integer getNumber();

	/**
	 * A short descriptor for this normative requirement. Guaranteed to be
	 * non-null.
	 */
	public String getName();

	/**
	 * A statement of the normative requirement that a Trustmark Recipient must
	 * meet to receive a Trustmark issued under the parent Trustmark Definition.
	 * Guaranteed to be non-null.
	 */
	public String getDescription();

	/**
	 * A citation of an authoritative source of this normative requirement.
	 * Guaranteed to be non-null. May be empty.
	 */
	public Set<Citation> getCitations();

	/**
	 * The structural element identifier for this conformance criterion. May be null.
	 */
	public String getId();
}
