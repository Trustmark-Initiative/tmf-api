package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import org.apache.log4j.Logger;
import org.gtri.fj.data.HashMap;
import org.gtri.fj.data.Option;
import org.gtri.fj.product.P2;

import java.util.ServiceLoader;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.ManagerUtility.getClassAndAncestorList;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.HashMap.hashMap;
import static org.gtri.fj.data.List.iteratorList;
import static org.gtri.fj.data.List.list;
import static org.gtri.fj.product.P.p;

public class JsonManagerImpl implements JsonManager {

    private static final Logger log = Logger.getLogger(JsonManagerImpl.class);

    private HashMap<P2<Class, Class>, JsonProducer> jsonProducerCache;

    public JsonManagerImpl() {
        loadDefaults();
    }

    private synchronized void loadDefaults() {
        log.debug("Loading JsonProducer ...");

        jsonProducerCache = hashMap();

        iteratorList(ServiceLoader.load(JsonProducer.class).iterator()).forEach(jsonProducer -> {
            log.debug("Loading JsonProducer " + jsonProducer.getClass().getName() + " (" + jsonProducer.getSupportedType().getName() + ", " + jsonProducer.getSupportedTypeOutput().getName() + ") ...");

            jsonProducerCache.set(p(jsonProducer.getSupportedType(), jsonProducer.getSupportedTypeOutput()), jsonProducer);
        });
    }

    @Override
    public synchronized JsonProducer findProducer(
            final Class supportedType) {

        requireNonNull(supportedType);

        log.debug("Finding JsonProducer for (" + supportedType.getName() + ") ...");

        return getClassAndAncestorList(supportedType)
                .bind(key -> jsonProducerCache.toList().filter(p -> p._1()._1().equals(key)))
                .headOption()
                .map(entry -> entry._2())
                .toNull();
    }

    @Override
    public synchronized <INPUT, OUTPUT> Option<JsonProducer<INPUT, OUTPUT>> findProducerStrict(
            final Class<INPUT> supportedType,
            final Class<OUTPUT> supportedTypeOutput) {

        requireNonNull(supportedType);
        requireNonNull(supportedTypeOutput);

        log.debug("Finding JsonProducer for (" + supportedType.getName() + ", " + supportedTypeOutput.getName() + ") ...");

        return jsonProducerCache.get(p(supportedType, supportedTypeOutput))
                .map(jsonProducer -> (JsonProducer<INPUT, OUTPUT>) jsonProducer);
    }

    @Override
    public synchronized <INPUT, OUTPUT> void register(
            final JsonProducer<INPUT, OUTPUT> jsonProducer) {

        requireNonNull(jsonProducer);

        log.debug("Registering JsonProducer for " + jsonProducer.getClass().getName() + " (" + jsonProducer.getSupportedType().getName() + ", " + jsonProducer.getSupportedTypeOutput().getName() + ") ...");

        this.jsonProducerCache.set(p(jsonProducer.getSupportedType(), jsonProducer.getSupportedTypeOutput()), jsonProducer);
    }

    @Override
    public synchronized <INPUT, OUTPUT> void unregister(
            final JsonProducer<INPUT, OUTPUT> jsonProducer) {

        requireNonNull(jsonProducer);

        this.findProducerStrict(jsonProducer.getSupportedType(), jsonProducer.getSupportedTypeOutput()).forEach(jsonProducerCached -> {
            if (jsonProducerCached.equals(jsonProducer)) {
                log.info("Unregistering JsonProducer for " + jsonProducer.getClass().getName() + " (" + jsonProducer.getSupportedType().getName() + ", " + jsonProducer.getSupportedTypeOutput().getName() + ") ...");

                this.jsonProducerCache.delete(p(jsonProducer.getSupportedType(), jsonProducer.getSupportedTypeOutput()));
            }
        });
    }

    @Override
    public synchronized void reloadDefaults() {
        this.loadDefaults();
    }
}
