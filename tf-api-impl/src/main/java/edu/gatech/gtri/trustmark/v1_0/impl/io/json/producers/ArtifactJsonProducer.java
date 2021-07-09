package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Artifact;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public final class ArtifactJsonProducer implements JsonProducer<Artifact, JSONObject> {

    @Override
    public Class<Artifact> getSupportedType() {
        return Artifact.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(Artifact artifact) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", artifact.getName());
        jsonObject.put("Description", artifact.getDescription());
        return jsonObject;
    }
}
