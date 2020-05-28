package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Collection;
import java.util.List;

/**
 * Indicates that this object uses terms that should be well-defined to avoid ambiguity.
 */
public interface TermsUsed {

    /**
     * Returns a collection of terms, as fast as possible.  May not be sorted. Never null but maybe empty.
     */
    public Collection<Term> getTerms();

    /**
     * Returns the list of terms sorted in case-insensitive alphabetic order. Never null but maybe empty.
     */
    public List<Term> getTermsSorted();

}
