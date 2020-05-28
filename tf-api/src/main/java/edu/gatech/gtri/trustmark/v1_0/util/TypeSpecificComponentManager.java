package edu.gatech.gtri.trustmark.v1_0.util;

/**
 * Manages type specific components.  This is a base for any component manager with which you you can tie in and
 * specify how the system can use type-specific components of the specified class.
 * Created by Nicholas on 9/19/2016.
 */
public interface TypeSpecificComponentManager<C extends TypeSpecificComponent> {
    
    /**
     * Returns the component class that this manager is specific to.
     */
    public Class<? extends C> getComponentType();
    
    /**
     * Finds the component responsible for the given type.
     */
    public C findComponent(Class type);
    
    /**
     * Gets the component responsible for the given type, but as a generic reference.
     * The returned object should be taken from the result of findComponent(), i.e. it should
     * be of class C and also match TypeSpecificComponent[? super T].
     * Subinterfaces should override this method with the specific return type that makes sense for them.
     */
    public <T> TypeSpecificComponent<? super T> getComponent(Class<T> type);
    
    /**
     * A simple way (around the ServiceManager based way) of registering a custom {@link TypeSpecificComponent} in the system.
     * Note that any component registered this way will take precedence over a ServiceManager loaded instance.
     */
    public void register(C component);
    
    /**
     * Provided to allow you to remove a component.  After this call, NO component will be registered (even the default).
     */
    public void unregister(C component);
    
    /**
     * A method which will re-parse and reload the default configuration.
     */
    public void reloadDefaults();
}
