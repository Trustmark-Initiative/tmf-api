package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmark;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import org.gtri.fj.Ord;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readBooleanOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURIOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer.PROPERTY_NAME_COMMENT;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer.PROPERTY_NAME_LIST;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer.PROPERTY_NAME_NAME;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer.PROPERTY_NAME_PROVISIONAL;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer.PROPERTY_NAME_STATUS;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer.PROPERTY_NAME_TRUSTMARK_DEFINITION_IDENTIFIER;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonProducer.PROPERTY_NAME_TRUSTMARK_IDENTIFIER;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.TreeMap.iterableTreeMap;
import static org.gtri.fj.lang.StringUtility.stringOrd;


/**
 * Implementations deserialize JSON and, optionally, a URI into a Trust
 * Interoperability Profile.
 *
 * @author GTRI Trustmark Team
 */
public final class TrustmarkBindingRegistryOrganizationTrustmarkMapJsonDeserializer implements JsonDeserializer<TrustmarkBindingRegistryOrganizationTrustmarkMap> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkBindingRegistryOrganizationTrustmarkMapJsonDeserializer.class);

    public TrustmarkBindingRegistryOrganizationTrustmarkMap deserialize(final String jsonString, final URI uri) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark Binding Registry Organization Trustmark Map JSON . . .");

        final Ord<URI> uriOrd = ord((uri1, uri2) -> stringOrd.compare(uri1.toString(), uri2.toString()));
        final List<JSONObject> jsonObjectList = readJSONObjectList(new JSONObject(jsonString), PROPERTY_NAME_LIST);
        final TreeMap<URI, TrustmarkBindingRegistryOrganizationTrustmark> trustmarkMap = iterableTreeMap(uriOrd, jsonObjectList.mapException(TrustmarkBindingRegistryOrganizationTrustmarkMapJsonDeserializer::readTrustmarkBindingRegistryOrganizationTrustmark)
                .groupBy(trustmarkBindingRegistryOrganizationTrustmark -> trustmarkBindingRegistryOrganizationTrustmark.getTrustmarkIdentifier(), uriOrd)
                .toList()
                .map(p -> p.map2(List::head)));

        return new TrustmarkBindingRegistryOrganizationTrustmarkMap() {

            @Override
            public URI getIdentifier() {
                return uri;
            }

            @Override
            public TreeMap<URI, TrustmarkBindingRegistryOrganizationTrustmark> getTrustmarkMap() {
                return trustmarkMap;
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

    private static final TrustmarkBindingRegistryOrganizationTrustmark readTrustmarkBindingRegistryOrganizationTrustmark(final JSONObject jsonObject) throws ParseException {

        final String comment = readStringOption(jsonObject, PROPERTY_NAME_COMMENT).toNull();
        final String name = readStringOption(jsonObject, PROPERTY_NAME_NAME).toNull();
        final Boolean provisional = readBooleanOption(jsonObject, PROPERTY_NAME_PROVISIONAL).toNull();
        final TrustmarkStatusCode status = readStringOption(jsonObject, PROPERTY_NAME_STATUS).map(TrustmarkStatusCode::fromString).toNull();
        final URI trustmarkDefinitionIdentifier = readURIOption(jsonObject, PROPERTY_NAME_TRUSTMARK_DEFINITION_IDENTIFIER).toNull();
        final URI trustmarkIdentifier = readURIOption(jsonObject, PROPERTY_NAME_TRUSTMARK_IDENTIFIER).toNull();

        return new TrustmarkBindingRegistryOrganizationTrustmark() {

            @Override
            public String getComment() {
                return comment;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public boolean isProvisional() {
                return provisional;
            }

            @Override
            public TrustmarkStatusCode getStatus() {
                return status;
            }

            @Override
            public URI getTrustmarkDefinitionIdentifier() {
                return trustmarkDefinitionIdentifier;
            }

            @Override
            public URI getTrustmarkIdentifier() {
                return trustmarkIdentifier;
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
