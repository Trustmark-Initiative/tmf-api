package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;

/**
 * Created by brad on 1/7/16.
 */
public final class NumberJsonProducer implements JsonProducer<Number, Double> {

    @Override
    public Class<Number> getSupportedType() {
        return Number.class;
    }

    @Override
    public Class<Double> getSupportedTypeOutput() {
        return Double.class;
    }

    @Override
    public Double serialize(Number number) {
        return number.doubleValue();
    }
}
