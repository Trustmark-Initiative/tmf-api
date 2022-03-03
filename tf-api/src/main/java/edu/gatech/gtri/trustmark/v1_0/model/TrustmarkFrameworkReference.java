package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Implementations represent a trustmark framework reference: either a Trust
 * Interoperability Profile or a Trustmark Definition.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkFrameworkReference extends TrustmarkFrameworkIdentifiedObject, HasSource {

    /**
     * Returns the terms; non-null.
     *
     * @return the terms; non-null
     */
    Collection<Term> getTerms();

    /**
     * Returns the terms in order; non-null.
     *
     * @return the terms in order; non-null
     */
    List<Term> getTermsSorted();

    /**
     * Returns the sources.
     *
     * @return the sources
     */
    Collection<Source> getSources();

    /**
     * Return the trustmark framework objects this entity supersedes.
     *
     * @return the trustmark framework objects this entity supersedes
     */
    Collection<TrustmarkFrameworkIdentifiedObject> getSupersedes();

    /**
     * Return the trustmark framework objects superseded by this entity.
     *
     * @return the trustmark framework objects superseded by this entity
     */
    Collection<TrustmarkFrameworkIdentifiedObject> getSupersededBy();

    /**
     * Return whether this entity is deprecated.
     *
     * @return whether this entity is deprecated
     */
    boolean isDeprecated();

    /**
     * Returns the trustmark framework objects that this entity satisfies.
     *
     * @return the trustmark framework objects that this entity satisfies
     */
    Collection<TrustmarkFrameworkIdentifiedObject> getSatisfies();

    /**
     * Returns the trustmark framework objects that conflict with this entity.
     *
     * @return the trustmark framework objects that conflict with this entity
     */
    Collection<TrustmarkFrameworkIdentifiedObject> getKnownConflicts();

    /**
     * Returns keywords.
     *
     * @return keywords
     */
    Collection<String> getKeywords();

    /**
     * Returns the publication date and time; non-null.
     *
     * @return the publication date and time; non-null
     */
    Date getPublicationDateTime();

    /**
     * Returns the legal notice; nullable.
     *
     * @returns the legal notice; nullable
     */
    String getLegalNotice();

    /**
     * Returns additional optional text content; nullable.
     *
     * @return additional optional text content; nullable
     */
    String getNotes();
}
