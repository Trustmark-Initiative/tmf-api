package edu.gatech.gtri.trustmark.v1_0.dao;

import java.util.List;

/**
 * Created by brad on 9/12/16.
 */
public interface SearchResult<T> {

    /**
     * Returns the list of matching result objects.
     * <br/><br/>
     * @return a {@link List} of results of the appropriate type.
     */
    public List<T> getResults();

    /**
     * How many results actually matched in the entire system.
     */
    public Integer getTotalCount();

    /**
     * The offset of this particular page of results.  Should always be a multiple of getCount().
     */
    public Integer getOffset();

    /**
     * The number of results that could have been returned.  Note that the actually size of getResults() may be smaller,
     * but will never be larger than, this number.
     */
    public Integer getCount();

    /**
     * What page you are on.  Essentially getOffset() / getCount() .
     */
    public Integer getPage();


    /**
     * A link to the next set of results (ie, the next page).
     */
    public SearchResult<T> nextPage();

}
