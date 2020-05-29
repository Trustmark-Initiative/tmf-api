package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Objects implementing this interface have an underlying "Source" representation that they are created from.  This
 * could be an XML string, or JSON String.  This method returns the actual source.
 * <br/><br/>
 * Created by brad on 12/7/15.
 */
public interface HasSource {

    /**
     * Returns the java String representation of the original source for this object.  This could be XML, JSON or some
     * other format.
     */
    public String getOriginalSource();

    /**
     * An indication of what the content type of the original source is.  Should be 'text/xml' or 'application/json' for
     * most calls.
     */
    public String getOriginalSourceType();

}//end HasSource()