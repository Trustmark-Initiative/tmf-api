package edu.gatech.gtri.trustmark.v1_0.service;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a Page as returned by a web service (ie, page 1 of 10).  Note that a page is an {@link Iterator} for
 * pages, so you can call hasNext() and next() to iterate, but for subsequent pages and not for the actual paged object
 * type.
 */
public interface Page<E> {

    /**
     * Returns the type of object being paged.  Ie, you can get a list of this kind of object.
     */
    public Class<E> getClassType();

    /**
     * How many objects there are total.
     */
    public long getTotalCount();

    /**
     * The starting offset number of this page.
     */
    public long getOffset();

    /**
     * How many objects are in this page.
     */
    public long getCount();

    /**
     * Returns a List of the actual paged objects.  Never returns null.
     */
    public List<E> getObjects();

    /**
     * Returns true if there is a next page.
     */
    public Boolean hasNext();

    /**
     * Returns the next page in the series, as given by the server.
     */
    public Page<E> next() throws RemoteException;

}//end interface Page<E>