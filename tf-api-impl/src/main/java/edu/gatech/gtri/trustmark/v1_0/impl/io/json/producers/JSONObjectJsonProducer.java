package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public final class JSONObjectJsonProducer implements JsonProducer<JSONObject, JSONObject> {

    @Override
    public Class getSupportedType() {
        return JSONObject.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(JSONObject jsonObject) {
        return jsonObject;
    }
}
