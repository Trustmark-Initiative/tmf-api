package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Collection;

/**
 * Indicates that an object can be grouped according to keywords.
 * <br/><br/>
 * @user brad
 * @date 10/12/16
 */
public interface Categorized {

    /**
     * The object can have a list of keywords associated with it, for the purposes of grouping and sorting.
     */
    public Collection<String> getKeywords();

}/* end Categorized */