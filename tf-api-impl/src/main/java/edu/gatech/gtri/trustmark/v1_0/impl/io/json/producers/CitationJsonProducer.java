package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public final class CitationJsonProducer implements JsonProducer<Citation, JSONObject> {

    @Override
    public Class<Citation> getSupportedType() {
        return Citation.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(Citation citation) {
        JSONObject jsonObject = new JSONObject();

        JSONObject sourceRef = new JSONObject();
        sourceRef.put("$ref", "#source" + citation.getSource().getIdentifier().hashCode());
        jsonObject.put("Source", sourceRef);

        jsonObject.put("Description", citation.getDescription());

        return jsonObject;
    }


}
