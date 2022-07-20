package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.lang.StringUtility.stringOrd;

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
    public static final String PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST = "trustmarkRecipientIdentifiers";

    @Override
    public JSONObject serialize(final TrustmarkBindingRegistryOrganizationMap trustmarkBindingRegistryOrganizationMap) {

        return new JSONObject(new java.util.HashMap<String, Object>() {{
            put(PROPERTY_NAME_LIST, trustmarkBindingRegistryOrganizationMap.getOrganizationMap().toList()
                    .sort(ord((o1, o2) -> stringOrd.compare(o1._1(), o2._1())))
                    .map(p -> p._2())
                    .map(trustmarkBindingRegistryOrganization -> trustmarkBindingRegistryOrganizationJsonProducer.serialize(trustmarkBindingRegistryOrganization)).toCollection());
        }});
    }
}
