package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import org.json.JSONArray;

/**
 * Created by brad on 1/7/16.
 */
public final class JSONArrayJsonProducer implements JsonProducer<JSONArray, JSONArray> {

    @Override
    public Class<JSONArray> getSupportedType() {
        return JSONArray.class;
    }

    @Override
    public Class<JSONArray> getSupportedTypeOutput() {
        return JSONArray.class;
    }

    @Override
    public JSONArray serialize(JSONArray jsonArray) {
        return jsonArray;
    }
}
