package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by brad on 1/7/16.
 */
public abstract class AbstractJsonProducer {

    private static final Logger log = Logger.getLogger(AbstractJsonProducer.class);

    private static JsonManager getManager(){
        JsonManager manager = FactoryLoader.getInstance(JsonManager.class);
        if( manager == null )
            throw new NullPointerException("Cannot load any JsonManager.class from FactoryLoader.  Is it configured?");
        return manager;
    }

    public static Object toJson(Object thing){
        if( thing == null )
            return null;
        JsonManager manager = getManager();
        JsonProducer producer = manager.findProducer(thing.getClass());
        if( producer == null )
            throw new UnsupportedOperationException("No JsonProducer registered for class: "+thing.getClass().getName());
        return producer.serialize(thing);
    }//end toJson()

    public boolean collectionNotEmpty(Collection c){
        if( c != null )
            return !c.isEmpty();
        return false;
    }

    public JSONObject createJsonReference(TrustmarkFrameworkIdentifiedObject tfi){
        JSONObject json = new JSONObject();
        json.put("Identifier", tfi.getIdentifier().toString());
        if( hasLength(tfi.getName()) )
            json.put("Name", tfi.getName());
        if( hasLength(tfi.getVersion()) )
            json.put("Version", tfi.getVersion());
        if( hasLength(tfi.getDescription()) )
            json.put("Description", tfi.getDescription());
        return json;
    }

    private boolean hasLength(String str){
        return str != null && str.trim().length() > 0;
    }


}
