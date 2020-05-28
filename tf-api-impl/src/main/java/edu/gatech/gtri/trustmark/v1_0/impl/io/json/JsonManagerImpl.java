package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractManagerImpl;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by brad on 1/7/16.
 */
public class JsonManagerImpl extends AbstractManagerImpl implements JsonManager {
    //==================================================================================================================
    //  Private Static Variables
    //==================================================================================================================
    private static final Logger log = Logger.getLogger(JsonManagerImpl.class);

    //==================================================================================================================
    //  Constructors
    //==================================================================================================================
    public JsonManagerImpl(){
        loadDefaults();
    }

    //==================================================================================================================
    //  Instance Variables
    //==================================================================================================================
    private Boolean producerCacheLock = Boolean.FALSE;
    private Map<Class, JsonProducer> producerCache;
    //==================================================================================================================
    //  Public Interface Methods
    //==================================================================================================================
    @Override
    public JsonProducer findProducer(Class type) {
        synchronized (producerCacheLock) {
            log.debug("Finding JsonProducer for Class["+type.getName()+"]");
            JsonProducer producer = this.producerCache.get(type);
            if( producer == null ){
                List<Class> classesList = buildClassCheckList(type);
                if( classesList != null && !classesList.isEmpty() ){
                    for( Class classPossibility : classesList ){
                        if( this.producerCache.containsKey(classPossibility) ){
                            producer = this.producerCache.get(classPossibility);
                            break;
                        }
                    }
                }
            }
            return producer;
        }
    }

    @Override
    public void register(JsonProducer producer) {
        synchronized (producerCacheLock) {
            log.info("Registering JsonProducer["+producer.getClass().getName()+"] to handle type["+producer.getSupportedType().getName()+"]...");
            this.producerCache.put(producer.getSupportedType(), producer);
        }
    }

    @Override
    public void unregister(JsonProducer producer) {
        synchronized (producerCacheLock){
            JsonProducer cached = this.findProducer(producer.getSupportedType());
            if( cached.equals(producer) ){
                log.info("Unregistering JsonProducer["+producer.getClass().getName()+"]...");
                this.producerCache.remove(producer.getSupportedType());
            }
        }
    }

    @Override
    public void reloadDefaults(){
        this.loadDefaults();
    }
    //==================================================================================================================
    //  Private Helper Methods
    //==================================================================================================================
    private void loadDefaults(){
        log.debug("Loading default JSON Serializers...");
        synchronized (producerCacheLock) {
            this.producerCache = null;
            this.producerCache = new HashMap<Class, JsonProducer>();

            ServiceLoader<JsonProducer> loader = ServiceLoader.load(JsonProducer.class);
            Iterator<JsonProducer> producers = loader.iterator();
            while(producers.hasNext()){
                JsonProducer producer = producers.next();
                log.debug("Assigning JsonProducer["+producer.getClass().getName()+"] to handle Type["+producer.getSupportedType().getName()+"]...");
                this.producerCache.put(producer.getSupportedType(), producer);
            }

        }
    }//end loadDefaults()

}
