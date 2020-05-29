package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Extension;
import org.json.JSONArray;

/**
 * Created by brad on 1/7/16.
 */
public class ExtensionJsonProducer extends AbstractJsonProducer implements JsonProducer {


    @Override
    public Class getSupportedType() {
        return Extension.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof Extension) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Extension extension = (Extension) instance;

        JSONArray array = new JSONArray();
        if( !extension.getData().isEmpty() ){
            for( Object data : extension.getData() ){
                array.put(toJson(data));
            }
        }
        return array;
    }



}
