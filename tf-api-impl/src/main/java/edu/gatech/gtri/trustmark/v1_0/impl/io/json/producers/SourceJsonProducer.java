package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class SourceJsonProducer extends AbstractJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return Source.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof Source) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Source source = (Source) instance;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("$id", "source"+source.getIdentifier().hashCode());
        jsonObject.put("Identifier", source.getIdentifier());
        jsonObject.put("Reference", source.getReference());
        return jsonObject;
    }
}
