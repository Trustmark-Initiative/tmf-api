package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;

/**
 * Created by brad on 1/7/16.
 */
public final class StringJsonProducer implements JsonProducer<String, String> {

    @Override
    public Class<String> getSupportedType() {
        return String.class;
    }

    @Override
    public Class<String> getSupportedTypeOutput() {
        return String.class;
    }

    @Override
    public String serialize(String string) {
        return string;
    }
}
