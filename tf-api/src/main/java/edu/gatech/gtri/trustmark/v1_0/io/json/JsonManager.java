package edu.gatech.gtri.trustmark.v1_0.io.json;

import org.gtri.fj.data.Option;

/**
 * Manages serialization for JSON objects.  This is here so you can tie in and specify how the system can serialize your
 * custom JSON objects.
 */
public interface JsonManager {

    /**
     * Finds the producer responsible for the given complex type.
     */
    JsonProducer findProducer(Class supportedType);

    /**
     * Finds the producer responsible for the given complex object type.
     */
    <INPUT, OUTPUT> Option<JsonProducer<INPUT, OUTPUT>> findProducerStrict(Class<INPUT> supportedType, Class<OUTPUT> supportedTypeOutput);

    /**
     * A simple way (around the ServiceLoader based way) of registering a custom {@link JsonProducer} in the system.
     * Note that any producer registered this way will take precedence over a ServiceLoader loaded instance.
     */
    <INPUT, OUTPUT> void register(JsonProducer<INPUT, OUTPUT> producer);

    /**
     * Provided to allow you to remove a JsonProducer.  After this call, NO producer will be registered (even the default).
     */
    <INPUT, OUTPUT> void unregister(JsonProducer<INPUT, OUTPUT> producer);

    /**
     * A method which will re-parse and reload the default Json Producer configuration.
     */
    void reloadDefaults();
}
