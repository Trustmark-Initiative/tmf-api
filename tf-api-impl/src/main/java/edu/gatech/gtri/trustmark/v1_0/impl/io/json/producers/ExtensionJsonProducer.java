package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Extension;
import org.json.JSONArray;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.JsonProducerUtility.toJson;

/**
 * Created by brad on 1/7/16.
 */
public final class ExtensionJsonProducer implements JsonProducer<Extension, JSONArray> {


    @Override
    public Class<Extension> getSupportedType() {
        return Extension.class;
    }

    @Override
    public Class<JSONArray> getSupportedTypeOutput() {
        return JSONArray.class;
    }

    @Override
    public JSONArray serialize(Extension extension) {
        JSONArray array = new JSONArray();
        if (!extension.getData().isEmpty()) {
            for (Object data : extension.getData()) {
                array.put(toJson(data));
            }
        }
        return array;
    }


}
