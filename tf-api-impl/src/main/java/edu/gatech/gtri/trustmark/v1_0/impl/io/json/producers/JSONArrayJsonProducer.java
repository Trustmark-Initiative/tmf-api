package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class JSONArrayJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return JSONArray.class;
    }

    @Override
    public Object serialize(Object instance) {
        return instance;
    }
}
