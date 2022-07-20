package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationTrustmarkMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganization;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmark;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObject;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURI;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURIList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURIOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationJsonProducer.PROPERTY_NAME_DESCRIPTION;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationJsonProducer.PROPERTY_NAME_IDENTIFIER;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationJsonProducer.PROPERTY_NAME_NAME;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationJsonProducer.PROPERTY_NAME_NAME_LONG;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationJsonProducer.PROPERTY_NAME_TRUSTMARK_MAP;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationJsonProducer.PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationMapJsonProducer.PROPERTY_NAME_LIST;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.TreeMap.iterableTreeMap;
import static org.gtri.fj.lang.StringUtility.stringOrd;


/**
 * Implementations deserialize JSON and, optionally, a URI into a Trust
 * Interoperability Profile.
 *
 * @author GTRI Trustmark Team
 */
public final class TrustmarkBindingRegistryOrganizationMapJsonDeserializer implements JsonDeserializer<TrustmarkBindingRegistryOrganizationMap> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkBindingRegistryOrganizationMapJsonDeserializer.class);

    public TrustmarkBindingRegistryOrganizationMap deserialize(final String jsonString, final URI uri) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark Binding Registry Organization Map JSON . . .");

        final List<JSONObject> jsonObjectList = readJSONObjectList(readJSONObject(jsonString), PROPERTY_NAME_LIST);
        final TreeMap<String, TrustmarkBindingRegistryOrganization> organizationMap = iterableTreeMap(stringOrd, jsonObjectList.mapException(TrustmarkBindingRegistryOrganizationMapJsonDeserializer::readTrustmarkBindingRegistryOrganization)
                .groupBy(trustmarkBindingRegistryOrganization -> trustmarkBindingRegistryOrganization.getIdentifier().toString(), stringOrd)
                .toList()
                .map(p -> p.map2(List::head)));

        return new TrustmarkBindingRegistryOrganizationMap() {

            @Override
            public URI getIdentifier() {
                return uri;
            }

            @Override
            public TreeMap<String, TrustmarkBindingRegistryOrganization> getOrganizationMap() {
                return organizationMap;
            }
        };
    }

    private static TrustmarkBindingRegistryOrganization readTrustmarkBindingRegistryOrganization(final JSONObject jsonObject) throws ParseException {

        final URI siteUrl = readURIOption(jsonObject, PROPERTY_NAME_IDENTIFIER).toNull();
        final String name = readStringOption(jsonObject, PROPERTY_NAME_NAME).toNull();
        final String displayName = readStringOption(jsonObject, PROPERTY_NAME_NAME_LONG).toNull();
        final String description = readStringOption(jsonObject, PROPERTY_NAME_DESCRIPTION).toNull();
        final List<URI> trustmarkRecipientIdentifiers = readURIList(jsonObject, PROPERTY_NAME_TRUSTMARK_RECIPIENT_IDENTIFIER_LIST);
        final URI organizationTrustmarkMapURI = readURI(jsonObject, PROPERTY_NAME_TRUSTMARK_MAP);
        final TrustmarkBindingRegistryOrganizationTrustmarkMap organizationTrustmarkMap = readTrustmarkBindingRegistryOrganizationTrustmarkMap(jsonObject);

        return new TrustmarkBindingRegistryOrganization() {

            @Override
            public URI getIdentifier() {
                return siteUrl;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getDisplayName() {
                return displayName;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public List<URI> getTrustmarkRecipientIdentifiers() {
                return trustmarkRecipientIdentifiers;
            }

            @Override
            public URI getOrganizationTrustmarkMapURI() {
                return organizationTrustmarkMapURI;
            }

            @Override
            public TreeMap<URI, TrustmarkBindingRegistryOrganizationTrustmark> getOrganizationTrustmarkMap() {
                return organizationTrustmarkMap.getTrustmarkMap();
            }
        };
    }

    private static final TrustmarkBindingRegistryOrganizationTrustmarkMapResolver resolver = FactoryLoader.getInstance(TrustmarkBindingRegistryOrganizationTrustmarkMapResolver.class);

    private static final TrustmarkBindingRegistryOrganizationTrustmarkMap readTrustmarkBindingRegistryOrganizationTrustmarkMap(final JSONObject jsonObject) throws ParseException {
        try {

            return resolver.resolve(readURI(jsonObject, PROPERTY_NAME_TRUSTMARK_MAP));

        } catch (final ResolveException resolveException) {
            throw new ParseException(resolveException);
        }
    }
}
