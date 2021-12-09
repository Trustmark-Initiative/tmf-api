package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystemType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURI;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURIList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURIOption;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.range;
import static org.gtri.fj.data.TreeMap.iterableTreeMap;
import static org.gtri.fj.lang.StringUtility.stringOrd;

public class TrustmarkBindingRegistryJsonDeserializer implements JsonDeserializer<TrustmarkBindingRegistry> {

    private static final Logger log = LogManager.getLogger(TrustmarkBindingRegistryJsonDeserializer.class);

    public TrustmarkBindingRegistry deserialize(final String jsonString) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark Binding Registry JSON . . .");

        final JSONArray jsonArray = new JSONArray(jsonString);
        final List<JSONObject> jsonObjectList = range(0, jsonArray.length()).map(index -> jsonArray.get(index)).filter(object -> object instanceof JSONObject).map(object -> (JSONObject) object);
        final TreeMap<String, TrustmarkBindingRegistrySystem> systemMap = iterableTreeMap(stringOrd, jsonObjectList.mapException(TrustmarkBindingRegistryJsonDeserializer::readTrustmarkBindingRegistrySystem)
                .groupBy(trustmarkBindingRegistrySystem -> trustmarkBindingRegistrySystem.getIdentifier(), stringOrd)
                .toList()
                .map(p -> p.map2(List::head)));

        return new TrustmarkBindingRegistry() {

            @Override
            public TreeMap<String, TrustmarkBindingRegistrySystem> getSystemMap() {
                return systemMap;
            }

            @Override
            public String getOriginalSource() {
                return jsonString;
            }

            @Override
            public String getOriginalSourceType() {
                return SerializerJson.APPLICATION_JSON;
            }
        };
    }

    private static final TrustmarkBindingRegistrySystem readTrustmarkBindingRegistrySystem(final JSONObject jsonObject) throws ParseException {

        final String entityId = readStringOption(jsonObject, "entityId").toNull();
        final URI metadata = readURIOption(jsonObject, "saml2MetadataUrl").toNull();
        final String name = readStringOption(jsonObject, "name").toNull();
        final TrustmarkBindingRegistrySystemType systemType = readStringOption(jsonObject, "providerType").map(TrustmarkBindingRegistrySystemType::fromNameForTrustmarkBindingRegistry).toNull();
        final List<URI> trustmarks = readJSONObjectList(jsonObject, "trustmarks").mapException(jsonObjectInner -> readURI(jsonObjectInner, "url"));
        final List<URI> trustmarkRecipientIdentifiers = readJSONObjectOption(jsonObject, "organization").mapException(jsonObjectInner -> readURIList(jsonObjectInner, "trustmarkRecipientIdentifiers")).orSome(nil());

        return new TrustmarkBindingRegistrySystem() {

            @Override
            public String getIdentifier() {
                return entityId;
            }

            @Override
            public URI getMetadata() {
                return metadata;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public TrustmarkBindingRegistrySystemType getSystemType() {
                return systemType;
            }

            @Override
            public List<URI> getTrustmarkRecipientIdentifiers() {
                return trustmarkRecipientIdentifiers;
            }

            @Override
            public List<URI> getTrustmarks() {
                return trustmarks;
            }

            @Override
            public String getOriginalSource() {
                return jsonObject.toString();
            }

            @Override
            public String getOriginalSourceType() {
                return SerializerJson.APPLICATION_JSON;
            }
        };
    }
}
