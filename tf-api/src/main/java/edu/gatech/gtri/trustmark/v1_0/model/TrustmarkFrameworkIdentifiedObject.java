package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Implementations represent an entity that has a type name, name, number,
 * version, identifier, and description.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkFrameworkIdentifiedObject extends HasIdentifier {

    /**
     * Returns the type name; non-null.
     *
     * @return the type name; non-null
     */
    String getTypeName();

    /**
     * Returns the name; nullable.
     *
     * @return the name; nullable
     */
    String getName();

    /**
     * Returns the number; nullable.
     *
     * @return the number; nullable
     */
    Integer getNumber();

    /**
     * Returns the version; nullable.
     *
     * @return the version; nullable
     */
    String getVersion();

    /**
     * Returns the description; nullable.
     *
     * @return the description; nullable
     */
    String getDescription();
}
