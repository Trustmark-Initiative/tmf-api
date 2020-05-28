package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AbstractOrderedObject;

/**
 * Created by Nicholas Saney on 2017-08-31.
 */
public abstract class AbstractOrderedObjectAdapter<T extends AbstractOrderedObject<T>>
    implements AbstractOrderedObject<T>
{
    
    ////// Instance Fields //////
    protected int listIndex;
    
    
    ////// Instance Methods //////
    @Override
    public int compareTo(T that) { return Integer.compare(this.getListIndex(), that.getListIndex()); }
    
    @Override
    public int getListIndex() { return this.listIndex; }
    public void setListIndex(int listIndex) { this.listIndex = listIndex; }
}
