package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class ConformanceCriterionJsonProducer extends AbstractJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return ConformanceCriterion.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof ConformanceCriterion) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        ConformanceCriterion crit = (ConformanceCriterion) instance;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("$id", "criterion"+crit.getNumber());
        jsonObject.put("Number", crit.getNumber());
        jsonObject.put("Name", crit.getName());
        jsonObject.put("Description", crit.getDescription());

        JSONArray citationsArray = new JSONArray();
        for( Citation citation : crit.getCitations() ){
            citationsArray.put(toJson(citation));
        }
        jsonObject.put("Citations", citationsArray);

        return jsonObject;
    }



}
