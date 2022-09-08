package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P2.p2Ord1;

@MetaInfServices
public final class TrustmarkBindingRegistryOrganizationMapJsonProducer implements JsonProducer<TrustmarkBindingRegistryOrganizationMap, JSONObject> {

    private static final TrustmarkBindingRegistryOrganizationJsonProducer trustmarkBindingRegistryOrganizationJsonProducer = new TrustmarkBindingRegistryOrganizationJsonProducer();

    @Override
    public Class<TrustmarkBindingRegistryOrganizationMap> getSupportedType() {
        return TrustmarkBindingRegistryOrganizationMap.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    public static final String PROPERTY_NAME_LIST = "organizations";

    @Override
    public JSONObject serialize(final TrustmarkBindingRegistryOrganizationMap trustmarkBindingRegistryOrganizationMap) {

        return new JSONObject(new java.util.HashMap<String, Object>() {{
            put(PROPERTY_NAME_LIST, trustmarkBindingRegistryOrganizationMap.getOrganizationMap().toList()
                    .sort(p2Ord1(stringOrd))
                    .map(p -> p._2())
                    .map(trustmarkBindingRegistryOrganization -> trustmarkBindingRegistryOrganizationJsonProducer.serialize(trustmarkBindingRegistryOrganization)).toCollection());
        }});
    }
}
