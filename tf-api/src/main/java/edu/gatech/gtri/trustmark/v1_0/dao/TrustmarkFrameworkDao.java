package edu.gatech.gtri.trustmark.v1_0.dao;

import java.util.List;

/**
 * Creates a data access layer for objects.  Note that the concurrency is
 * managed by the underlying connection implementation details (ie, mysql connections or postgres).
 * <br/><br/>
 * @author brad
 * @date 2016-09-12
 */
public interface TrustmarkFrameworkDao<T> {

    /**
     * The sort fields on an object that we support sorting.
     */
    public static enum SortField {
        IDENTIFIER,
        NAME,
        PUBLICATION_DATE,
        DEFINING_ORG_NAME
    }

    /**
     * Returns the type of object supported by this Dao.  (ie, {@link edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition} or {@link edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile}).
     */
    public Class<T> getType();

    /**
     * Returns a count of all objects the system knows about of this type T.
     */
    public Integer count();

    /**
     * Returns the given object with the identifier.  Note that it is potentially dirty (the remote object may not match the local one).
     * <br/><br/>
     * @param identifier the URL Identifier for this object
     * @return the object, or null if not found.
     */
    public T get(String identifier);

    /**
     * Returns the given object with the given name and version.  Note that it is potentially dirty.
     * Note that two different organizations may conflict with name/version, if this happens an error will be raised (for now).
     * <br/><br/>
     * @param name the name for this object
     * @param version the version for this object
     * @return the object, or null if not found.
     */
    public T get(String name, String version) throws NameVersionConflictException;

    /**
     * Returns a listing of 'count' objects, starting at the given offset value.
     * The objects are sorted according to their URL identifier values.
     */
    public List<T> list(Integer offset, Integer count);

    /**
     * Returns a listing of 'count' objects, starting at the given offset value.
     * The objects are sorted according to the parameters.
     */
    public List<T> list(Integer offset, Integer count, SortField sortField, Boolean ascending);

    /**
     * Performs a search of all objects based on the given, unprocessed, search text. Same as calling doSearch(text, 25).
     * <br/><br/>
     * @param searchText what someone has typed (perhaps unsafe) into a search text box.
     * @return a {@link SearchResult} of matching objects.
     */
    public SearchResult<T> doSearch(String searchText);

    /**
     * Performs a search of all objects based on the given, unprocessed, search text.
     * <br/><br/>
     * @param searchText what someone has typed (perhaps unsafe) into a search text box.
     * @param count The number of results on each page.  ie, 10, 25, 50, etc.
     * @return a {@link SearchResult} of matching objects.
     */
    public SearchResult<T> doSearch(String searchText, Integer count);


    /**
     * Returns a set of objects in which the local cache does not match the remote entry. This method will return the
     * locally cached version, not the remote version (which can be obtained using the URL identifier).
     */
    public List<T> listCacheErrors();


}//end TrustmarkFrameworkDao