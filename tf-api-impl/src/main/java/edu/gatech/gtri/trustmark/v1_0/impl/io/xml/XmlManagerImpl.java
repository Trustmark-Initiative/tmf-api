package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractManagerImpl;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by brad on 1/7/16.
 */
public class XmlManagerImpl extends AbstractManagerImpl implements XmlManager {
    //==================================================================================================================
    //  Private Static Variables
    //==================================================================================================================
    private static final Logger log = Logger.getLogger(XmlManagerImpl.class);

    //==================================================================================================================
    //  Constructors
    //==================================================================================================================
    public XmlManagerImpl(){
        loadDefaults();
    }

    //==================================================================================================================
    //  Instance Variables
    //==================================================================================================================
    private Boolean producerCacheLock = Boolean.FALSE;
    private Map<Class, XmlProducer> producerCache;
    //==================================================================================================================
    //  Public Interface Methods
    //==================================================================================================================
    @Override
    public XmlProducer findProducer(Class type) {
        synchronized (producerCacheLock) {
            log.debug("Finding XmlProducer for Class["+type.getName()+"]");
            XmlProducer producer = this.producerCache.get(type);
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
    public void register(XmlProducer producer) {
        synchronized (producerCacheLock) {
            log.info("Registering XmlProducer["+producer.getClass().getName()+"] to handle type["+producer.getSupportedType().getName()+"]...");
            this.producerCache.put(producer.getSupportedType(), producer);
        }
    }

    @Override
    public void unregister(XmlProducer producer) {
        synchronized (producerCacheLock){
            XmlProducer cached = this.findProducer(producer.getSupportedType());
            if( cached.equals(producer) ){
                log.info("Unregistering XmlProducer["+producer.getClass().getName()+"]...");
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
        log.debug("Loading default XML Serializers...");
        synchronized (producerCacheLock) {
            this.producerCache = null;
            this.producerCache = new HashMap<Class, XmlProducer>();

            ServiceLoader<XmlProducer> loader = ServiceLoader.load(XmlProducer.class);
            Iterator<XmlProducer> producers = loader.iterator();
            while(producers.hasNext()){
                XmlProducer producer = producers.next();
                log.debug("Assigning XmlProducer["+producer.getClass().getName()+"] to handle Type["+producer.getSupportedType().getName()+"]...");
                this.producerCache.put(producer.getSupportedType(), producer);
            }

        }
    }//end loadDefaults()


}
