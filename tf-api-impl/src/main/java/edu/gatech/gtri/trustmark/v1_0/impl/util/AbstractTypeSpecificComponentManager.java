package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.util.TypeSpecificComponent;
import edu.gatech.gtri.trustmark.v1_0.util.TypeSpecificComponentManager;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Nicholas on 09/19/2016.
 */
public abstract class AbstractTypeSpecificComponentManager<C extends TypeSpecificComponent> implements TypeSpecificComponentManager<C> {
    
    /////////////////////
    // Instance Fields //
    /////////////////////
    
    private final Logger log = Logger.getLogger(this.getClass());
    private final Object componentCacheLock = Boolean.FALSE;
    private Map<Class, C> componentCache;
    
    
    /////////////////
    // Constructor //
    /////////////////
    
    protected AbstractTypeSpecificComponentManager() {
        this.loadDefaults();
    }
    
    
    ///////////////////////////////
    // Instance Methods - Public //
    ///////////////////////////////
    
    @Override
    public C findComponent(Class type) {
        synchronized (this.componentCacheLock) {
            //log.debug("Finding " + this.getComponentType().getSimpleName() + " for Class[" + type.getName() + "]");
            C component = this.componentCache.get(type);
            if (component == null) {
                Set<Class> classesList = this.buildClassCheckList(type);
                if (classesList != null && !classesList.isEmpty()) {
                    for (Class classPossibility : classesList) {
                        if (this.componentCache.containsKey(classPossibility)) {
                            component = this.componentCache.get(classPossibility);
                            break;
                        }
                    }
                }
            }
            return component;
        }
    }
    
    @Override
    public void register(C component) {
        synchronized (this.componentCacheLock) {
            log.info("Registering component[" + component.getClass().getName() + "] to handle type[" + component.getSupportedType().getName() + "]...");
            this.componentCache.put(component.getSupportedType(), component);
        }
    }
    
    @Override
    public void unregister(C component) {
        synchronized (this.componentCacheLock) {
            C cached = this.findComponent(component.getSupportedType());
            if (cached.equals(component)) {
                log.info("Unregistering component[" + component.getClass().getName() + "]...");
                this.componentCache.remove(component.getSupportedType());
            }
        }
    }
    
    @Override
    public void reloadDefaults() {
        this.loadDefaults();
    }
    
    
    ////////////////////////////////
    // Instance Methods - Private //
    ////////////////////////////////
    
    private Set<Class> buildClassCheckList(Class baseType) {
        Set<Class> classes = new HashSet<>();
        Class[] interfaces = baseType.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            Collections.addAll(classes, interfaces);
        }
        Class superClass = baseType.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class)) {
            classes.add(superClass);
            Set<Class> superClassChecklist = this.buildClassCheckList(superClass);
            classes.addAll(superClassChecklist);
        }
        return classes;
    }
    
    private void loadDefaults() {
        log.debug("Loading default components of type " + this.getComponentType().getSimpleName() + "...");
        synchronized (this.componentCacheLock) {
            this.componentCache = null;
            this.componentCache = new HashMap<>();
            
            ServiceLoader<? extends C> loader = ServiceLoader.load(this.getComponentType());
            for (C component : loader) {
                log.debug("Assigning component[" + component.getClass().getName() + "] to handle type[" + component.getSupportedType().getName() + "]...");
                this.componentCache.put(component.getSupportedType(), component);
            }
        }
    }//end loadDefaults()
}
