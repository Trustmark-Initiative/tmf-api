package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmark;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public final class TrustmarkBindingRegistryOrganizationTrustmarkJsonProducer implements JsonProducer<TrustmarkBindingRegistryOrganizationTrustmark, JSONObject> {

    @Override
    public Class<TrustmarkBindingRegistryOrganizationTrustmark> getSupportedType() {
        return TrustmarkBindingRegistryOrganizationTrustmark.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    public static final String PROPERTY_NAME_COMMENT = "assessorComments";
    public static final String PROPERTY_NAME_NAME = "name";
    public static final String PROPERTY_NAME_PROVISIONAL = "provisional";
    public static final String PROPERTY_NAME_STATUS = "status";
    public static final String PROPERTY_NAME_TRUSTMARK_DEFINITION_IDENTIFIER = "trustmarkDefinitionURL";
    public static final String PROPERTY_NAME_TRUSTMARK_IDENTIFIER = "url";

    @Override
    public JSONObject serialize(final TrustmarkBindingRegistryOrganizationTrustmark trustmarkBindingRegistryOrganizationTrustmark) {
        return new JSONObject(new java.util.HashMap<String, String>() {{
            put(PROPERTY_NAME_COMMENT, trustmarkBindingRegistryOrganizationTrustmark.getComment() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getComment());
            put(PROPERTY_NAME_NAME, trustmarkBindingRegistryOrganizationTrustmark.getName() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getName());
            put(PROPERTY_NAME_PROVISIONAL, String.valueOf(trustmarkBindingRegistryOrganizationTrustmark.isProvisional()));
            put(PROPERTY_NAME_STATUS, trustmarkBindingRegistryOrganizationTrustmark.getStatus() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getStatus().toString());
            put(PROPERTY_NAME_TRUSTMARK_DEFINITION_IDENTIFIER, trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkDefinitionIdentifier() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkDefinitionIdentifier().toString());
            put(PROPERTY_NAME_TRUSTMARK_IDENTIFIER, trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkIdentifier() == null ? null : trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkIdentifier().toString());
        }});
    }
}
