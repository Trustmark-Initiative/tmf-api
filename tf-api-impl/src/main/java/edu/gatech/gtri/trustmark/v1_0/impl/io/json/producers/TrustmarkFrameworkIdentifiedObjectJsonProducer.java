package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public final class TrustmarkFrameworkIdentifiedObjectJsonProducer implements JsonProducer<TrustmarkFrameworkIdentifiedObject, JSONObject> {


    @Override
    public Class<TrustmarkFrameworkIdentifiedObject> getSupportedType() {
        return TrustmarkFrameworkIdentifiedObject.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(TrustmarkFrameworkIdentifiedObject tfiObject) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Identifier", tfiObject.getIdentifier());
        jsonObject.put("Name", tfiObject.getName());
        jsonObject.put("Number", tfiObject.getNumber());
        jsonObject.put("Version", tfiObject.getVersion());
        jsonObject.put("Description", tfiObject.getDescription());
        return jsonObject;
    }
}
