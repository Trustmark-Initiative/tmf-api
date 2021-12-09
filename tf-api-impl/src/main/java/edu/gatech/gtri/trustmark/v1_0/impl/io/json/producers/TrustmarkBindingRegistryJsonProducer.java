package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public final class TrustmarkBindingRegistryJsonProducer implements JsonProducer<TrustmarkBindingRegistry, JSONObject> {

    private static final Logger log = LogManager.getLogger(TrustmarkBindingRegistryJsonProducer.class);

    @Override
    public Class<TrustmarkBindingRegistry> getSupportedType() {
        return TrustmarkBindingRegistry.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(TrustmarkBindingRegistry trustmarkBindingRegistry) {
        return null;
    }
}//end EntityJsonProducer
