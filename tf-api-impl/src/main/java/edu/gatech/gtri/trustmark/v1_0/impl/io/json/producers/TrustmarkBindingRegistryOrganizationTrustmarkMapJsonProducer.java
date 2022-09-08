package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import org.gtri.fj.product.P2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import java.net.URI;

import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P2.p2Ord1;

@MetaInfServices
public final class TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer implements JsonProducer<TrustmarkBindingRegistryOrganizationTrustmarkMap, JSONObject> {

    private static final TrustmarkBindingRegistryOrganizationTrustmarkJsonProducer trustmarkBindingRegistryOrganizationTrustmarkJsonProducer = new TrustmarkBindingRegistryOrganizationTrustmarkJsonProducer();

    @Override
    public Class<TrustmarkBindingRegistryOrganizationTrustmarkMap> getSupportedType() {
        return TrustmarkBindingRegistryOrganizationTrustmarkMap.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    public static final String PROPERTY_NAME_LIST = "trustmarks";

    @Override
    public JSONObject serialize(final TrustmarkBindingRegistryOrganizationTrustmarkMap trustmarkBindingRegistryOrganizationTrustmarkMap) {
        return new JSONObject(new java.util.HashMap<String, JSONArray>() {{
            put(PROPERTY_NAME_LIST, new JSONArray(trustmarkBindingRegistryOrganizationTrustmarkMap.getTrustmarkMap()
                    .toList()
                    .sort(p2Ord1(ord((URI o1, URI o2) -> stringOrd.compare(o1.toString(), o2.toString()))))
                    .map(P2::_2)
                    .map(trustmarkBindingRegistryOrganizationTrustmark -> trustmarkBindingRegistryOrganizationTrustmarkJsonProducer.serialize(trustmarkBindingRegistryOrganizationTrustmark))
                    .toCollection()));
        }});
    }
}
