package edu.gatech.gtri.trustmark.v1_0;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Uses {@link ServiceLoader} to load Trustmark Framework API factories.
 * 
 * @author GTRI Trustmark Team
 */
public class FactoryLoader {

	// ==================================================================================================================
	// Get Instance Capability...
	// ==================================================================================================================
	private static Boolean syncVar = Boolean.FALSE;
	
	/**
	 * Cache of singleton factory instances.
	 */
	private static Map<Class<?>, Object> instanceCache = new HashMap<Class<?>, Object>();

	/**
	 * Loads an instance of this factory, using java's service loader mechanism.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> classType) {
		synchronized (syncVar) {
			if (instanceCache.get(classType) != null) {
				return (T) instanceCache.get(classType);
			} else {
				ServiceLoader<T> loader = ServiceLoader.load(classType);
				Iterator<T> iterator = loader.iterator();
				while (iterator.hasNext()) {
					T factory = iterator.next();
					instanceCache.put(classType, factory);
					break;
				}
				return (T) instanceCache.get(classType);
			}
		}
	}// end getInstance()


	/**
	 * Allows any operater in the system to register their own implementations of important classes used by the system.
     */
	public static <T> void register(Class<T> classType, T object){
		synchronized (syncVar){
			if( object != null ){
				instanceCache.put(classType, object);
			}else{
				throw new NullPointerException("This system does not support registering a null class.");
			}
		}
	}//end register()

	public static void unregister(Class classType){
		synchronized (syncVar) {
			if( instanceCache.containsKey(classType) )
				instanceCache.remove(classType);
		}
	}

}
