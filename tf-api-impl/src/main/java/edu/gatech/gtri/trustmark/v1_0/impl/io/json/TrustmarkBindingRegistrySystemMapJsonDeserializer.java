package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemMapJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemType;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURI;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURIList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURIOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemMapJsonProducer.PROPERTY_NAME_IDENTIFIER;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemMapJsonProducer.PROPERTY_NAME_METADATA;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemMapJsonProducer.PROPERTY_NAME_NAME;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemMapJsonProducer.PROPERTY_NAME_ORGANIZATION;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemMapJsonProducer.PROPERTY_NAME_SYSTEM_TYPE;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemMapJsonProducer.PROPERTY_NAME_TRUSTMARK;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.range;
import static org.gtri.fj.data.TreeMap.iterableTreeMap;
import static org.gtri.fj.lang.StringUtility.stringOrd;


/**
 * Implementations deserialize JSON and, optionally, a URI into a Trust
 * Interoperability Profile.
 *
 * @author GTRI Trustmark Team
 */
public final class TrustmarkBindingRegistrySystemMapJsonDeserializer implements JsonDeserializer<TrustmarkBindingRegistrySystemMap> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkBindingRegistrySystemMapJsonDeserializer.class);

    public TrustmarkBindingRegistrySystemMap deserialize(final String jsonString, final URI uri) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark Binding Registry System Map JSON . . .");

        final JSONArray jsonArray = new JSONArray(jsonString);
        final List<JSONObject> jsonObjectList = range(0, jsonArray.length()).map(index -> jsonArray.get(index)).filter(object -> object instanceof JSONObject).map(object -> (JSONObject) object);
        final TreeMap<String, TrustmarkBindingRegistrySystem> systemMap = iterableTreeMap(stringOrd, jsonObjectList.mapException(TrustmarkBindingRegistrySystemMapJsonDeserializer::readTrustmarkBindingRegistrySystem)
                .groupBy(trustmarkBindingRegistrySystem -> trustmarkBindingRegistrySystem.getIdentifier(), stringOrd)
                .toList()
                .map(p -> p.map2(List::head)));

        return new TrustmarkBindingRegistrySystemMap() {

            @Override
            public URI getIdentifier() {
                return uri;
            }

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
                return MediaType.APPLICATION_JSON.getMediaType();
            }
        };
    }

    private static final TrustmarkBindingRegistrySystem readTrustmarkBindingRegistrySystem(final JSONObject jsonObject) throws ParseException {

        final String entityId = readStringOption(jsonObject, PROPERTY_NAME_IDENTIFIER).toNull();
        final URI metadata = readURIOption(jsonObject, PROPERTY_NAME_METADATA).toNull();
        final String name = readStringOption(jsonObject, PROPERTY_NAME_NAME).toNull();
        final TrustmarkBindingRegistrySystemType systemType = readStringOption(jsonObject, PROPERTY_NAME_SYSTEM_TYPE).map(TrustmarkBindingRegistrySystemType::fromNameForTrustmarkBindingRegistry).toNull();
        final List<URI> trustmarks = readJSONObjectList(jsonObject, PROPERTY_NAME_TRUSTMARK).mapException(jsonObjectInner -> readURI(jsonObjectInner, TrustmarkBindingRegistrySystemMapJsonProducer.PROPERTY_NAME_TRUSTMARK_URI));
        final List<URI> trustmarkRecipientIdentifiers = readJSONObjectOption(jsonObject, PROPERTY_NAME_ORGANIZATION).mapException(jsonObjectInner -> readURIList(jsonObjectInner, TrustmarkBindingRegistrySystemMapJsonProducer.PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST)).orSome(nil());

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
                return MediaType.APPLICATION_JSON.getMediaType();
            }
        };
    }
}
