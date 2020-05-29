package edu.gatech.gtri.trustmark.v1_0.impl.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 1/7/16.
 */
public abstract class AbstractManagerImpl {


    protected List<Class> buildClassCheckList(Class baseType){
        List<Class> classes = new ArrayList<>();
        Class[] interfaces = baseType.getInterfaces();
        if( interfaces != null && interfaces.length > 0 ) {
            for (Class theInterface : interfaces) {
                classes.add(theInterface);
            }
        }
        Class superClass = baseType.getSuperclass();
        if( superClass != null && !superClass.getName().equals("java.lang.Object")){
            classes.add(superClass);
            List<Class> superClassClasses = this.buildClassCheckList(superClass);
            classes.addAll(superClassClasses);
        }
        return classes;
    }

}
