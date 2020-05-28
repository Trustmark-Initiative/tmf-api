package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class TrustmarkFrameworkIdentifiedObjectJsonProducer extends AbstractJsonProducer implements JsonProducer {


    @Override
    public Class getSupportedType() {
        return TrustmarkFrameworkIdentifiedObject.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof TrustmarkFrameworkIdentifiedObject) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        TrustmarkFrameworkIdentifiedObject tfiObj = (TrustmarkFrameworkIdentifiedObject) instance;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Identifier", tfiObj.getIdentifier());
        jsonObject.put("Name", tfiObj.getName());
        jsonObject.put("Number", tfiObj.getNumber());
        jsonObject.put("Version", tfiObj.getVersion());
        jsonObject.put("Description", tfiObj.getDescription());
        return jsonObject;
    }
}
