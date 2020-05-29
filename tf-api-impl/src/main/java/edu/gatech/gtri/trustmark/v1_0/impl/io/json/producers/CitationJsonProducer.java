package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class CitationJsonProducer extends AbstractJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return Citation.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof Citation) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Citation citation = (Citation) instance;
        JSONObject jsonObject = new JSONObject();

        JSONObject sourceRef = new JSONObject();
        sourceRef.put("$ref", "#source"+citation.getSource().getIdentifier().hashCode());
        jsonObject.put("Source", sourceRef);

        jsonObject.put("Description", citation.getDescription());

        return jsonObject;
    }



}
