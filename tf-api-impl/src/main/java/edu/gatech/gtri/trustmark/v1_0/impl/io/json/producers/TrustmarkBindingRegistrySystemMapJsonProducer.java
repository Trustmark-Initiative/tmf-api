package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import org.gtri.fj.product.P2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.lang.StringUtility.stringOrd;

@MetaInfServices
public final class TrustmarkBindingRegistrySystemMapJsonProducer implements JsonProducer<TrustmarkBindingRegistrySystemMap, JSONArray> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkBindingRegistrySystemMapJsonProducer.class);

    @Override
    public Class<TrustmarkBindingRegistrySystemMap> getSupportedType() {
        return TrustmarkBindingRegistrySystemMap.class;
    }

    @Override
    public Class<JSONArray> getSupportedTypeOutput() {
        return JSONArray.class;
    }

    public static final String PROPERTY_NAME_IDENTIFIER = "entityId";
    public static final String PROPERTY_NAME_NAME = "name";
    public static final String PROPERTY_NAME_SYSTEM_TYPE = "providerType";
    public static final String PROPERTY_NAME_METADATA = "saml2MetadataUrl";
    public static final String PROPERTY_NAME_ORGANIZATION = "organization";
    public static final String PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST = "trustmarkRecipientIdentifiers";
    public static final String PROPERTY_NAME_TRUSTMARK = "trustmarks";
    public static final String PROPERTY_NAME_TRUSTMARK_URI = "url";

    @Override
    public JSONArray serialize(final TrustmarkBindingRegistrySystemMap trustmarkBindingRegistrySystemMap) {
        return new JSONArray(trustmarkBindingRegistrySystemMap.getSystemMap()
                .toList()
                .sort(ord((o1, o2) -> stringOrd.compare(o1._1(), o2._1())))
                .map(P2::_2)
                .map(trustmarkBindingRegistrySystem -> new JSONObject(new java.util.HashMap<String, Object>() {{
                    put(PROPERTY_NAME_IDENTIFIER, trustmarkBindingRegistrySystem.getIdentifier() == null ? null: trustmarkBindingRegistrySystem.getIdentifier());
                    put(PROPERTY_NAME_NAME, trustmarkBindingRegistrySystem.getName() == null ? null : trustmarkBindingRegistrySystem.getName());
                    put(PROPERTY_NAME_SYSTEM_TYPE, trustmarkBindingRegistrySystem.getSystemType() == null ? null : trustmarkBindingRegistrySystem.getSystemType().getNameForTrustmarkBindingRegistry());
                    put(PROPERTY_NAME_METADATA, trustmarkBindingRegistrySystem.getMetadata() == null ? null :  trustmarkBindingRegistrySystem.getMetadata().toString());
                    put(PROPERTY_NAME_ORGANIZATION, new JSONObject(new java.util.HashMap<String, Object>() {{
                        put(PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST, new JSONArray(trustmarkBindingRegistrySystem.getTrustmarkRecipientIdentifiers().map(URI::toString).toCollection()));
                    }}));
                    put(PROPERTY_NAME_TRUSTMARK, new JSONArray(trustmarkBindingRegistrySystem.getTrustmarks().map(uri -> new JSONObject(new java.util.HashMap<String, String>() {{
                        put(PROPERTY_NAME_TRUSTMARK_URI, uri.toString());
                    }})).toCollection()));
                }})).toCollection());
    }
}
