package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganization;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import java.net.URI;

import static org.gtri.fj.lang.StringUtility.stringOrd;

@MetaInfServices
public final class TrustmarkBindingRegistryOrganizationJsonProducer implements JsonProducer<TrustmarkBindingRegistryOrganization, JSONObject> {

    @Override
    public Class<TrustmarkBindingRegistryOrganization> getSupportedType() {
        return TrustmarkBindingRegistryOrganization.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    public static final String PROPERTY_NAME_IDENTIFIER = "siteUrl";
    public static final String PROPERTY_NAME_NAME = "name";
    public static final String PROPERTY_NAME_NAME_LONG = "displayName";
    public static final String PROPERTY_NAME_DESCRIPTION = "description";
    public static final String PROPERTY_NAME_TRUSTMARK_MAP = "trustmarksApiUrl";
    public static final String PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST = "trustmarkRecipientIdentifiers";

    @Override
    public JSONObject serialize(final TrustmarkBindingRegistryOrganization trustmarkBindingRegistryOrganization) {

        return new JSONObject(new java.util.HashMap<String, Object>() {{
            put(PROPERTY_NAME_IDENTIFIER, trustmarkBindingRegistryOrganization.getIdentifier() == null ? null : trustmarkBindingRegistryOrganization.getIdentifier().toString());
            put(PROPERTY_NAME_NAME, trustmarkBindingRegistryOrganization.getName() == null ? null : trustmarkBindingRegistryOrganization.getName());
            put(PROPERTY_NAME_NAME_LONG, trustmarkBindingRegistryOrganization.getDisplayName() == null ? null : trustmarkBindingRegistryOrganization.getDisplayName());
            put(PROPERTY_NAME_DESCRIPTION, trustmarkBindingRegistryOrganization.getDescription() == null ? null : trustmarkBindingRegistryOrganization.getDescription());
            put(PROPERTY_NAME_TRUSTMARK_MAP, trustmarkBindingRegistryOrganization.getOrganizationTrustmarkMapURI() == null ? null : trustmarkBindingRegistryOrganization.getOrganizationTrustmarkMapURI().toString());
            put(PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST, new JSONArray(trustmarkBindingRegistryOrganization.getTrustmarkRecipientIdentifiers().map(URI::toString).sort(stringOrd).toCollection()));
        }});
    }
}
