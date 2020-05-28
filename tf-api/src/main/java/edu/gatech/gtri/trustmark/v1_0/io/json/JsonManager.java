package edu.gatech.gtri.trustmark.v1_0.io.json;

/**
 * Manages serialization for JSON objects.  This is here so you can tie in and specify how the system can serialize your
 * custom JSON objects.
 */
public interface JsonManager {


    /**
     * Finds the producer responsible for the given complex type.
     */
    public JsonProducer findProducer(Class type);

    /**
     * A simple way (around the ServiceManager based way) of registering a custom {@link JsonProducer} in the system.
     * Note that any producer registered this way will take precedence over a ServiceManager loaded instance.
     */
    public void register(JsonProducer producer);

    /**
     * Provided to allow you to remove a JsonProducer.  After this call, NO producer will be registered (even the default).
     */
    public void unregister(JsonProducer producer);


    /**
     * A method which will re-parse and reload the default Json configuration.
     */
    public void reloadDefaults();

}
