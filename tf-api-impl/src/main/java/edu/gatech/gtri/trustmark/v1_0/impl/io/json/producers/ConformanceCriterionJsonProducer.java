package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import org.json.JSONArray;
import org.json.JSONObject;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.JsonProducerUtility.toJson;

/**
 * Created by brad on 1/7/16.
 */
public final class ConformanceCriterionJsonProducer implements JsonProducer<ConformanceCriterion, JSONObject> {

    @Override
    public Class<ConformanceCriterion> getSupportedType() {
        return ConformanceCriterion.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(ConformanceCriterion crit) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("$id", "criterion" + crit.getNumber());
        jsonObject.put("Number", crit.getNumber());
        jsonObject.put("Name", crit.getName());
        jsonObject.put("Description", crit.getDescription());

        JSONArray citationsArray = new JSONArray();
        for (Citation citation : crit.getCitations()) {
            citationsArray.put(toJson(citation));
        }
        jsonObject.put("Citations", citationsArray);

        return jsonObject;
    }


}
