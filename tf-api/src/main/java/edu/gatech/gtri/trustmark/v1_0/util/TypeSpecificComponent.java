package edu.gatech.gtri.trustmark.v1_0.util;

/**
 * Created by Nicholas on 9/19/2016.
 */
public interface TypeSpecificComponent<T> {
    
    /**
     * Returns the class that this component is specific to.
     */
    public Class<? extends T> getSupportedType();
    
}
