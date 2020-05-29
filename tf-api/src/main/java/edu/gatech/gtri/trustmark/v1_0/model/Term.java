package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Set;

/**
 * Describes a term used by a Trustmark Definition, including the name of the
 * term, any abbreviations for the term, and the meaning of the term within the
 * context of the Trustmark Definition.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface Term {

	/**
	 * The name of this term.
	 */
	public String getName();

	/**
	 * A set of case-sensitive abbreviations of this term.
	 */
	public Set<String> getAbbreviations();

	/**
	 * The meaning of this term in the context of this Trustmark Definition.
	 */
	public String getDefinition();
}
