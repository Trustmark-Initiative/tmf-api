package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import org.gtri.fj.product.P2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.lang.StringUtility.stringOrd;

@MetaInfServices
public final class TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer implements JsonProducer<TrustmarkBindingRegistryOrganizationTrustmarkMap, JSONObject> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkBindingRegistryOrganizationTrustmarkMap.class);

    @Override
    public Class<TrustmarkBindingRegistryOrganizationTrustmarkMap> getSupportedType() {
        return TrustmarkBindingRegistryOrganizationTrustmarkMap.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    public static final String PROPERTY_NAME_LIST = "trustmarks";
    public static final String PROPERTY_NAME_COMMENT = "assessorComments";
    public static final String PROPERTY_NAME_NAME = "name";
    public static final String PROPERTY_NAME_PROVISIONAL = "provisional";
    public static final String PROPERTY_NAME_STATUS = "status";
    public static final String PROPERTY_NAME_TRUSTMARK_DEFINITION_IDENTIFIER = "trustmarkDefinitionURL";
    public static final String PROPERTY_NAME_TRUSTMARK_IDENTIFIER = "url";

    @Override
    public JSONObject serialize(final TrustmarkBindingRegistryOrganizationTrustmarkMap trustmarkBindingRegistryOrganizationTrustmarkMap) {
        return new JSONObject(new java.util.HashMap<String, JSONArray>() {{
            put(PROPERTY_NAME_LIST, new JSONArray(trustmarkBindingRegistryOrganizationTrustmarkMap.getTrustmarkMap()
                    .toList()
                    .sort(ord((o1, o2) -> stringOrd.compare(o1._1().toString(), o2._1().toString())))
                    .map(P2::_2)
                    .map(trustmarkBindingRegistryOrganizationTrustmark -> new JSONObject(new java.util.HashMap<String, String>() {{
                        put(PROPERTY_NAME_COMMENT, trustmarkBindingRegistryOrganizationTrustmark.getComment() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getComment());
                        put(PROPERTY_NAME_NAME, trustmarkBindingRegistryOrganizationTrustmark.getName() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getName());
                        put(PROPERTY_NAME_PROVISIONAL, String.valueOf(trustmarkBindingRegistryOrganizationTrustmark.isProvisional()));
                        put(PROPERTY_NAME_STATUS, trustmarkBindingRegistryOrganizationTrustmark.getStatus() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getStatus().toString());
                        put(PROPERTY_NAME_TRUSTMARK_DEFINITION_IDENTIFIER, trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkDefinitionIdentifier() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkDefinitionIdentifier().toString());
                        put(PROPERTY_NAME_TRUSTMARK_IDENTIFIER, trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkIdentifier() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkIdentifier().toString());
                    }}))
                    .toCollection()));
        }});
    }
}//end EntityJsonProducer
