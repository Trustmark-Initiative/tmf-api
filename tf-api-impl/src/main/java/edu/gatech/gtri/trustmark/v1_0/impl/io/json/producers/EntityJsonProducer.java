package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class EntityJsonProducer extends AbstractJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return Entity.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof Entity) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Entity entity = (Entity) instance;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Identifier", entity.getIdentifier().toString());
        jsonObject.put("Name", entity.getName());

        if( entity.getContacts().size() == 1 ){
            jsonObject.put("PrimaryContact", toJson(entity.getContacts().get(0)));
        }else  if( entity.getContacts().size() > 1 ){
            jsonObject.put("PrimaryContact", toJson(entity.getDefaultContact()));
            int counter = 0;
            JSONArray additionalContacts = new JSONArray();
            for( Contact next : entity.getContacts()){
                if( counter > 0 )
                    additionalContacts.put(toJson(next));
                counter++;
            }
            jsonObject.put("OtherContacts", additionalContacts);
        }

        return jsonObject;
    }


}//end EntityJsonProducer