package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public final class SourceJsonProducer implements JsonProducer<Source, JSONObject> {

    @Override
    public Class<Source> getSupportedType() {
        return Source.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(Source source) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("$id", "source" + source.getIdentifier().hashCode());
        jsonObject.put("Identifier", source.getIdentifier());
        jsonObject.put("Reference", source.getReference());
        return jsonObject;
    }
}
