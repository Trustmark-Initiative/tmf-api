package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import org.apache.log4j.Logger;
import org.gtri.fj.data.HashMap;
import org.gtri.fj.data.Option;

import java.util.ServiceLoader;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.ManagerUtility.getClassAndAncestorList;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.HashMap.hashMap;
import static org.gtri.fj.data.List.iteratorList;
import static org.gtri.fj.data.Option.somes;

public class XmlManagerImpl implements XmlManager {

    private static final Logger log = Logger.getLogger(XmlManagerImpl.class);

    private HashMap<Class, XmlProducer> xmlProducerCache;

    public XmlManagerImpl() {
        loadDefaults();
    }

    private synchronized void loadDefaults() {
        log.debug("Loading XmlProducer ...");

        xmlProducerCache = hashMap();

        iteratorList(ServiceLoader.load(XmlProducer.class).iterator()).forEach(xmlProducer -> {
            log.debug("Loading XmlProducer " + xmlProducer.getClass().getName() + " (" + xmlProducer.getSupportedType().getName() + ") ...");

            xmlProducerCache.set(xmlProducer.getSupportedType(), xmlProducer);
        });
    }

    @Override
    public synchronized XmlProducer<?> findProducer(
            final Class supportedType) {

        requireNonNull(supportedType);

        log.debug("Finding XmlProducer for (" + supportedType.getName() + ") ...");

        return somes(getClassAndAncestorList(supportedType)
                .map(key -> xmlProducerCache.get(key)))
                .headOption()
                .toNull();
    }

    @Override
    public synchronized <INPUT> Option<XmlProducer<INPUT>> findProducerStrict(
            final Class<INPUT> supportedType) {

        requireNonNull(supportedType);

        log.debug("Finding XmlProducer for (" + supportedType.getName() + ") ...");

        return xmlProducerCache.get(supportedType)
                .map(xmlProducer -> (XmlProducer<INPUT>) xmlProducer);
    }

    @Override
    public synchronized <INPUT> void register(
            final XmlProducer<INPUT> xmlProducer) {

        requireNonNull(xmlProducer);

        log.debug("Registering XmlProducer for " + xmlProducer.getClass().getName() + " (" + xmlProducer.getSupportedType().getName() + ") ...");

        this.xmlProducerCache.set(xmlProducer.getSupportedType(), xmlProducer);
    }

    @Override
    public synchronized <INPUT> void unregister(
            final XmlProducer<INPUT> xmlProducer) {

        requireNonNull(xmlProducer);

        this.findProducerStrict(xmlProducer.getSupportedType()).forEach(xmlProducerCached -> {
            if (xmlProducerCached.equals(xmlProducer)) {
                log.info("Unregistering XmlProducer for " + xmlProducer.getClass().getName() + " (" + xmlProducer.getSupportedType().getName() + ") ...");

                this.xmlProducerCache.delete(xmlProducer.getSupportedType());
            }
        });
    }

    @Override
    public synchronized void reloadDefaults() {
        this.loadDefaults();
    }
}
