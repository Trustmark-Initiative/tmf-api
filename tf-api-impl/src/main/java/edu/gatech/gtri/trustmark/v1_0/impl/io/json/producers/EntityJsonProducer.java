package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import org.json.JSONArray;
import org.json.JSONObject;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.JsonProducerUtility.toJson;

/**
 * Created by brad on 1/7/16.
 */
public final class EntityJsonProducer implements JsonProducer<Entity, JSONObject> {

    @Override
    public Class<Entity> getSupportedType() {
        return Entity.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(Entity entity) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Identifier", entity.getIdentifier().toString());
        jsonObject.put("Name", entity.getName());

        if (entity.getContacts().size() == 1) {
            jsonObject.put("PrimaryContact", toJson(entity.getContacts().get(0)));
        } else if (entity.getContacts().size() > 1) {
            jsonObject.put("PrimaryContact", toJson(entity.getDefaultContact()));
            int counter = 0;
            JSONArray additionalContacts = new JSONArray();
            for (Contact next : entity.getContacts()) {
                if (counter > 0)
                    additionalContacts.put(toJson(next));
                counter++;
            }
            jsonObject.put("OtherContacts", additionalContacts);
        }

        return jsonObject;
    }


}//end EntityJsonProducer
