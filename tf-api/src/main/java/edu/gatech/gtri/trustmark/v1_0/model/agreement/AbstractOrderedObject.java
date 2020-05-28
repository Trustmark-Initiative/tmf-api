package edu.gatech.gtri.trustmark.v1_0.model.agreement;

/**
 * Created by Nicholas on 01/27/2017.
 */
public interface AbstractOrderedObject<T extends AbstractOrderedObject<T>> extends Comparable<T> {
    
    /**
     * The sequence number of this object in its ordered listing.
     */
    public int getListIndex();
    
}
