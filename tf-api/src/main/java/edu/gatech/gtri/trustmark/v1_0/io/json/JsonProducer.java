package edu.gatech.gtri.trustmark.v1_0.io.json;

public interface JsonProducer<INPUT, OUTPUT> {

    /**
     * This is how the system determines to use your custom producer, based on the type of object being serialized.
     */
    Class<INPUT> getSupportedType();

    Class<OUTPUT> getSupportedTypeOutput();

    /**
     * Performs the serialization.  Although this method returns a generic type, the value is expected to be one of:
     * <ul>
     *     <li>JSONObject - from org.json's java library, for complex objects</li>
     *     <li>JSONArray - from org.json's java library, for arrays of objects</li>
     *     <li>simple types - normal JSON types, like strings, integers, booleans, etc.</li>
     * </ul>
     * <p>
     * Note that dates should be serialized as ISO8601 strings, please see {@link JsonUtils}
     */
    OUTPUT serialize(INPUT instance);
}
