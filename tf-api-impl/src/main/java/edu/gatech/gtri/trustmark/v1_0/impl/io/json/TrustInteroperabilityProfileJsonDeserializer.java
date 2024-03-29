package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileReferenceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.function.Try1;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.assertSupported;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readBooleanOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readDate;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readEntity;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readEntityReference;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readFromMap;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readIntOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObject;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readString;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURI;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Option.somes;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

/**
 * Implementations deserialize JSON and, optionally, a URI into a Trust
 * Interoperability Profile.
 *
 * @author GTRI Trustmark Team
 */
public final class TrustInteroperabilityProfileJsonDeserializer implements JsonDeserializer<TrustInteroperabilityProfile> {

    private static final Logger log = LoggerFactory.getLogger(TrustInteroperabilityProfileJsonDeserializer.class);

    private final boolean withTerms;

    public TrustInteroperabilityProfileJsonDeserializer(final boolean withTerms) {
        this.withTerms = withTerms;
    }

    public TrustInteroperabilityProfile deserialize(final String jsonString, final URI uri) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trust Interoperability Profile JSON . . .");

        final JSONObject jsonObject = readJSONObject(jsonString);
        assertSupported(jsonObject);

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();

        trustInteroperabilityProfile.setDeprecated(readBooleanOption(jsonObject, "Deprecated").orSome(false));
        trustInteroperabilityProfile.setDescription(readString(jsonObject, "Description"));
        trustInteroperabilityProfile.setIdentifier(readURI(jsonObject, "Identifier"));
        trustInteroperabilityProfile.setIssuer(readEntity(readJSONObject(jsonObject, "Issuer")));
        trustInteroperabilityProfile.setName(readString(jsonObject, "Name"));
        trustInteroperabilityProfile.setPublicationDateTime(readDate(jsonObject, "PublicationDateTime"));
        trustInteroperabilityProfile.setTrustExpression(readString(jsonObject, "TrustExpression"));
        trustInteroperabilityProfile.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUST_INTEROPERABILITY_PROFILE);
        trustInteroperabilityProfile.setVersion(readString(jsonObject, "Version"));

        readStringOption(jsonObject, "$id").forEach(trustInteroperabilityProfile::setId);
        readStringOption(jsonObject, "Primary").filter(primary -> primary.equals("true")).forEach(primary -> trustInteroperabilityProfile.setPrimary(TRUE));
        readStringOption(jsonObject, "Moniker").forEach(trustInteroperabilityProfile::setMoniker);
        readStringOption(jsonObject, "LegalNotice").forEach(trustInteroperabilityProfile::setLegalNotice);
        readStringOption(jsonObject, "Notes").forEach(trustInteroperabilityProfile::setNotes);

        readJSONObjectOption(jsonObject, "Supersessions").foreachDoEffectException(jsonObjectInner -> {
            readJSONObjectList(jsonObjectInner, "Supersedes").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustInteroperabilityProfile::addToSupersedes);
            readJSONObjectList(jsonObjectInner, "SupersededBy").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustInteroperabilityProfile::addToSupersededBy);
        });

        readJSONObjectList(jsonObject, "KnownConflicts").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustInteroperabilityProfile::addToKnownConflict);
        readJSONObjectList(jsonObject, "Satisfies").mapException(JsonDeserializerUtility::readTrustmarkDefinitionReference).forEach(trustInteroperabilityProfile::addToSatisfies);
        readJSONObjectList(jsonObject, "RequiredProviders").mapException(JsonDeserializerUtility::readEntityReference).forEach(trustInteroperabilityProfile::addRequiredProvider);
        readJSONObjectList(jsonObject, "Sources").mapException(JsonDeserializerUtility::readSource).forEach(trustInteroperabilityProfile::addSource);
        if (withTerms) {
            readJSONObjectList(jsonObject, "Terms").mapException(JsonDeserializerUtility::readTerm).forEach(trustInteroperabilityProfile::addTerm);
        }

        readStringList(jsonObject, "Keywords").foreachDoEffectException(trustInteroperabilityProfile::addToKeywords);

        readReferences(readJSONObject(jsonObject, "References")).forEach(trustInteroperabilityProfile::addReference);

        return trustInteroperabilityProfile;
    }

    private static List<AbstractTIPReference> readReferences(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        if (!jsonObject.has("TrustInteroperabilityProfileReferences") && !jsonObject.has("TrustmarkDefinitionRequirements"))
            throw new ParseException("The entity must have at least one of the following: TrustInteroperabilityProfileReferences, TrustmarkDefinitionRequirements.");

        //ProviderReferences | ProviderReferences TODO review why are we searching for both
        final List<JSONObject> jsonObjectProviderReference1 = readJSONObjectList(jsonObject, "TrustmarkDefinitionRequirements")
                .bindException(jsonObjectTrustmarkDefinitionRequirement -> readJSONObjectList(jsonObjectTrustmarkDefinitionRequirement, "ProviderReference"));

        final List<JSONObject> jsonObjectProviderReference2 = readJSONObjectList(jsonObject, "TrustmarkDefinitionRequirements")
                .bindException(jsonObjectTrustmarkDefinitionRequirement -> readJSONObjectList(jsonObjectTrustmarkDefinitionRequirement, "ProviderReferences"));

        final TreeMap<String, Entity> map = somes(jsonObjectProviderReference1
                .append(jsonObjectProviderReference2)
                .mapException(jsonObjectProviderReference -> readStringOption(jsonObjectProviderReference, "$id")
                        .mapException(id -> p(id, readEntityReference(jsonObjectProviderReference)))))
                .foldLeft(tree -> p -> tree.set(p._1(), p._2()), TreeMap.empty(stringOrd));

        final List<TrustInteroperabilityProfileReferenceImpl> trustInteroperabilityProfileReferenceList = readJSONObjectList(jsonObject, "TrustInteroperabilityProfileReferences").mapException(TrustInteroperabilityProfileJsonDeserializer::readTrustInteroperabilityProfileReference);
        final List<TrustmarkDefinitionRequirementImpl> trustmarkDefinitionRequirementList = readJSONObjectList(jsonObject, "TrustmarkDefinitionRequirements").mapException(readTrustmarkDefinitionRequirement(map));

        return trustInteroperabilityProfileReferenceList.map(trustInteroperabilityProfileReference -> (AbstractTIPReference) trustInteroperabilityProfileReference)
                .append(trustmarkDefinitionRequirementList.map(trustmarkDefinitionRequirement -> trustmarkDefinitionRequirement));
    }

    private static Try1<JSONObject, TrustmarkDefinitionRequirementImpl, ParseException> readTrustmarkDefinitionRequirement(TreeMap<String, Entity> map) {
        requireNonNull(map);

        return jsonObject -> {
            requireNonNull(jsonObject);

            JSONObject jsonObjectTrustmarkDefinitionReference = readJSONObject(jsonObject, "TrustmarkDefinitionReference");

            TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();

            trustmarkDefinitionRequirement.setId(readString(jsonObject, "$id"));
            trustmarkDefinitionRequirement.setIdentifier(readURI(jsonObjectTrustmarkDefinitionReference, "Identifier"));
            trustmarkDefinitionRequirement.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUSTMARK_DEFINITION_REQUIREMENT);

            readIntOption(jsonObjectTrustmarkDefinitionReference, "Number").forEach(trustmarkDefinitionRequirement::setNumber);

            readStringOption(jsonObjectTrustmarkDefinitionReference, "Description").forEach(trustmarkDefinitionRequirement::setDescription);
            readStringOption(jsonObjectTrustmarkDefinitionReference, "Name").forEach(trustmarkDefinitionRequirement::setName);
            readStringOption(jsonObjectTrustmarkDefinitionReference, "Version").forEach(trustmarkDefinitionRequirement::setVersion);

            //ProviderReference | ProviderReferences TODO review why are we searching for both
            readJSONObjectList(jsonObject, "ProviderReference").mapException(readFromMap(map, TrustInteroperabilityProfileJsonDeserializer::readIdOrRef)).forEach(trustmarkDefinitionRequirement::addProviderReference);
            readJSONObjectList(jsonObject, "ProviderReferences").mapException(readFromMap(map, TrustInteroperabilityProfileJsonDeserializer::readIdOrRef)).forEach(trustmarkDefinitionRequirement::addProviderReference);

            return trustmarkDefinitionRequirement;
        };
    }

    private static TrustInteroperabilityProfileReferenceImpl readTrustInteroperabilityProfileReference(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final TrustInteroperabilityProfileReferenceImpl trustInteroperabilityProfileReference = new TrustInteroperabilityProfileReferenceImpl();

        trustInteroperabilityProfileReference.setId(readString(jsonObject, "$id"));
        trustInteroperabilityProfileReference.setIdentifier(readURI(jsonObject, "Identifier"));
        trustInteroperabilityProfileReference.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUST_INTEROPERABILITY_PROFILE_REFERENCE);

        readIntOption(jsonObject, "Number").forEach(trustInteroperabilityProfileReference::setNumber);

        readStringOption(jsonObject, "Description").forEach(trustInteroperabilityProfileReference::setDescription);
        readStringOption(jsonObject, "Name").forEach(trustInteroperabilityProfileReference::setName);
        readStringOption(jsonObject, "Version").forEach(trustInteroperabilityProfileReference::setVersion);

        return trustInteroperabilityProfileReference;
    }

    private static String readIdOrRef(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        if (jsonObject.has("$id")) { //TODO use constant from edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*
            return readString(jsonObject, "$id"); //TODO use constant from edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*
        } else if (jsonObject.has(ATTRIBUTE_KEY_JSON_REF)) {
            if (readString(jsonObject, ATTRIBUTE_KEY_JSON_REF).startsWith("#")) {
                return readString(jsonObject, ATTRIBUTE_KEY_JSON_REF).substring(1);
            } else {
                return readString(jsonObject, ATTRIBUTE_KEY_JSON_REF);
            }
        } else {
            throw new ParseException();
        }
    }
}
