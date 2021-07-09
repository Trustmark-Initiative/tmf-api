package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;

/**
 * Created by brad on 1/7/16.
 */
public final class BooleanJsonProducer implements JsonProducer<Boolean, Boolean> {

    @Override
    public Class getSupportedType() {
        return Boolean.class;
    }

    @Override
    public Class<Boolean> getSupportedTypeOutput() {
        return Boolean.class;
    }

    @Override
    public Boolean serialize(Boolean b) {
        return b;
    }
}
