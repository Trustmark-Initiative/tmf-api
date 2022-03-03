package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Implementations may have a source representation, such as an XML document or
 * a JSON document.
 *
 * @author GTRI Trustmark Team
 */
public interface HasSource {

    /**
     * Returns the source representation; nullable.
     *
     * @return the source representation; nullable
     */
    String getOriginalSource();

    /**
     * Returns the source type; nullable.
     *
     * @return the source type; nullable
     */
    String getOriginalSourceType();
}
