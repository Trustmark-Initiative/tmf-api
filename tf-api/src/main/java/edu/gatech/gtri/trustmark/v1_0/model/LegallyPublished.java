package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Date;

/**
 * Provides fields to those trustmark artifacts which are published in the wild in a "legal" sense.
 * <br/><br/>
 * @user brad
 * @date 10/12/16
 */
public interface LegallyPublished {

    /**
     * The date and time at which the object was published. Guaranteed to be non-null.
     */
    public Date getPublicationDateTime();

    /**
     * The legal notice for this object, if any. May be null.
     */
    public String getLegalNotice();

    /**
     * Additional optional text content about this object. May be null.
     */
    public String getNotes();

}/* end LegallyPublished */