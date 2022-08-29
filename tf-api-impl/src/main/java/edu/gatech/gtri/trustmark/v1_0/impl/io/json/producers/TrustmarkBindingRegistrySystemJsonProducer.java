package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import java.net.URI;

import static org.gtri.fj.lang.StringUtility.stringOrd;

@MetaInfServices
public final class TrustmarkBindingRegistrySystemJsonProducer implements JsonProducer<TrustmarkBindingRegistrySystem, JSONObject> {

    @Override
    public Class<TrustmarkBindingRegistrySystem> getSupportedType() {
        return TrustmarkBindingRegistrySystem.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    public static final String PROPERTY_NAME_IDENTIFYER_FOR_OIDC = "uniqueId";
    public static final String PROPERTY_NAME_IDENTIFIER_FOR_CERTIFICATE = "systemCertificateUrl";
    public static final String PROPERTY_NAME_IDENTIFIER_FOR_SAML = "entityId";
    public static final String PROPERTY_NAME_NAME = "name";
    public static final String PROPERTY_NAME_SYSTEM_TYPE = "providerType";
    public static final String PROPERTY_NAME_METADATA_URL_FOR_SAML = "saml2MetadataUrl";
    public static final String PROPERTY_NAME_METADATA_URL_FOR_OIDC = "oidcMetadataUrl";
    public static final String PROPERTY_NAME_ORGANIZATION = "organization";
    public static final String PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST = "trustmarkRecipientIdentifiers";
    public static final String PROPERTY_NAME_TRUSTMARK = "trustmarks";
    public static final String PROPERTY_NAME_TRUSTMARK_URI = "url";

    @Override
    public JSONObject serialize(final TrustmarkBindingRegistrySystem trustmarkBindingRegistrySystem) {
        return new JSONObject(new java.util.HashMap<String, Object>() {{
            trustmarkBindingRegistrySystem.getSystemType().match(
                    ignore -> put(PROPERTY_NAME_IDENTIFIER_FOR_SAML, trustmarkBindingRegistrySystem.getIdentifier() == null ? null : trustmarkBindingRegistrySystem.getIdentifier()),
                    ignore -> put(PROPERTY_NAME_IDENTIFIER_FOR_SAML, trustmarkBindingRegistrySystem.getIdentifier() == null ? null : trustmarkBindingRegistrySystem.getIdentifier()),
                    ignore -> put(PROPERTY_NAME_IDENTIFYER_FOR_OIDC, trustmarkBindingRegistrySystem.getIdentifier() == null ? null : trustmarkBindingRegistrySystem.getIdentifier()),
                    ignore -> put(PROPERTY_NAME_IDENTIFYER_FOR_OIDC, trustmarkBindingRegistrySystem.getIdentifier() == null ? null : trustmarkBindingRegistrySystem.getIdentifier()),
                    ignore -> put(PROPERTY_NAME_IDENTIFIER_FOR_CERTIFICATE, trustmarkBindingRegistrySystem.getIdentifier() == null ? null : trustmarkBindingRegistrySystem.getIdentifier()),
                    ignore -> put(PROPERTY_NAME_IDENTIFIER_FOR_SAML, trustmarkBindingRegistrySystem.getIdentifier() == null ? null : trustmarkBindingRegistrySystem.getIdentifier()));
            put(PROPERTY_NAME_NAME, trustmarkBindingRegistrySystem.getName() == null ? null : trustmarkBindingRegistrySystem.getName());
            put(PROPERTY_NAME_SYSTEM_TYPE, trustmarkBindingRegistrySystem.getSystemType() == null ? null : trustmarkBindingRegistrySystem.getSystemType().getNameForTrustmarkBindingRegistry());
            trustmarkBindingRegistrySystem.getSystemType().match(
                    ignore -> put(PROPERTY_NAME_METADATA_URL_FOR_SAML, trustmarkBindingRegistrySystem.getMetadata() == null ? null : trustmarkBindingRegistrySystem.getMetadata().toString()),
                    ignore -> put(PROPERTY_NAME_METADATA_URL_FOR_SAML, trustmarkBindingRegistrySystem.getMetadata() == null ? null : trustmarkBindingRegistrySystem.getMetadata().toString()),
                    ignore -> put(PROPERTY_NAME_METADATA_URL_FOR_OIDC, trustmarkBindingRegistrySystem.getMetadata() == null ? null : trustmarkBindingRegistrySystem.getMetadata().toString()),
                    ignore -> put(PROPERTY_NAME_METADATA_URL_FOR_OIDC, trustmarkBindingRegistrySystem.getMetadata() == null ? null : trustmarkBindingRegistrySystem.getMetadata().toString()),
                    ignore -> null,
                    ignore -> null);
            put(PROPERTY_NAME_ORGANIZATION, new JSONObject(new java.util.HashMap<String, Object>() {{
                put(PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST, new JSONArray(trustmarkBindingRegistrySystem.getTrustmarkRecipientIdentifiers().map(URI::toString).sort(stringOrd).toCollection()));
            }}));
            put(PROPERTY_NAME_TRUSTMARK, new JSONArray(trustmarkBindingRegistrySystem.getTrustmarks().map(URI::toString).sort(stringOrd).map(uriString -> new JSONObject(new java.util.HashMap<String, String>() {{
                put(PROPERTY_NAME_TRUSTMARK_URI, uriString);
            }})).toCollection()));
        }});
    }
}
