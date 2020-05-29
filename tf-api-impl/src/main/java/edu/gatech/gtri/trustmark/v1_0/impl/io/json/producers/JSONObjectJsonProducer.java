package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class JSONObjectJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return JSONObject.class;
    }

    @Override
    public Object serialize(Object instance) {
        return instance;
    }
}
