package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import java.util.Collection;
import java.util.Iterator;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 12/6/16
 */
public class AbstractDiff {

    protected boolean isEmpty(String val){
        return val == null || val.trim().length() == 0;
    }
    protected boolean isNotEmpty(String val){
        return !isEmpty(val);
    }
    /**
     * Checks to see if everything in set1 is in set2, and vice versa.
     */
    protected boolean setEquality(Collection<String> set1, Collection<String> set2){
        Iterator<String> iter = set1.iterator();
        while( iter.hasNext() ){
            boolean found = set2.contains(iter.next());
            if( !found ){
                return false;
            }
        }
        Iterator<String> iter2 = set2.iterator();
        while( iter2.hasNext() ){
            boolean found = set1.contains(iter2.next());
            if( !found ){
                return false;
            }
        }

        return true;
    }
    
}/* end AbstractDiff */