package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Collection;

/**
 * Indicates that the object was taken from a source of content, such as a technical specification.
 */
public interface Sourced {

    /**
     * A collection of Sources for this object.  Never null but maybe empty.
     */
    public Collection<Source> getSources();

}
