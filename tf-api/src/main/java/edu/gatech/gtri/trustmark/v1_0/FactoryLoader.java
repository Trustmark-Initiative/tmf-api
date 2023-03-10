package edu.gatech.gtri.trustmark.v1_0;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import static java.util.Objects.requireNonNull;

/**
 * Use ServiceLoader to instantiate implementations of Trustmark Framework API
 * classes.
 *
 * @author GTRI Trustmark Team
 */
public class FactoryLoader {

    private static final Map<Class<?>, Object> cache = new HashMap<>();

    /**
     * If registered, return the instance for the type; if not registered and if
     * constructable, construct, register, and return the constructed instance
     * for the type; otherwise, return null.
     *
     * @param type the type
     * @param <T1> the type of the instance
     * @return if registered, the instance for the type; if not registered and
     * if constructable, the constructed instance for the type; otherwise,
     * return null
     * @throws NullPointerException if type is null
     */
    public static synchronized <T1> T1 getInstance(final Class<T1> type) {
        requireNonNull(type, "type");

        if (cache.containsKey(type)) {
            return (T1) cache.get(type);
        } else {
            final Iterator<T1> iterator = ServiceLoader.load(type).iterator();
            if (iterator.hasNext()) {
                T1 factory = iterator.next();
                cache.put(type, factory);
            }
            return (T1) cache.get(type);
        }
    }

    /**
     * Register the instance for the type.
     *
     * @param type     the type
     * @param instance the instance
     * @param <T1>     the type of the instance
     * @throws NullPointerException if type is null
     * @throws NullPointerException if instance is null
     */
    public static synchronized <T1> void register(
            final Class<T1> type,
            final T1 instance) {

        requireNonNull(type, "type");
        requireNonNull(instance, "instance");

        cache.put(type, instance);
    }

    /**
     * Unregister the instance for the type, if any.
     *
     * @param type the type
     * @param <T1> the type of the instance
     * @throws NullPointerException if type is null
     */
    public static synchronized <T1> void unregister(
            final Class<T1> type) {

        requireNonNull(type, "type");

        cache.remove(type);
    }
}
