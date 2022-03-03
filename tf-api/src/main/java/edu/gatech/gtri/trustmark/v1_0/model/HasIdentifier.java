package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URI;

/**
 * Implementations may have an identifier.
 *
 * @author GTRI Trustmark Team
 */
public interface HasIdentifier {

    /**
     * Return the globally-unique identifier.
     *
     * @return the globally-unique identifier.
     */
    URI getIdentifier();
}
