package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ContactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ExtensionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.SourceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TermImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.Try;
import org.gtri.fj.function.Try1;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.single;
import static org.gtri.fj.data.NonEmptyList.fromList;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;

/**
 * Utilities for JSON deserializers.
 *
 * @author GTRI Trustmark Team
 */
public abstract class JsonDeserializerUtility {

    private JsonDeserializerUtility() {
    }

    private static final Logger log = LoggerFactory.getLogger(JsonDeserializerUtility.class);

    /**
     * This method will inspect the given JSON object and make sure it is
     * compatible with the current library version.
     */
    public static void assertSupported(JSONObject json) throws ParseException {
        assertSupportedVersion(json);
        isSupportedType(json);
    }

    public static void assertSupportedVersion(JSONObject json) throws ParseException {
        String tmfVersion = json.optString(ATTRIBUTE_KEY_JSON_TMF_VERSION);
        if (tmfVersion == null || tmfVersion.trim().length() == 0) {
            throw new ParseException("This JSON is not supported.  The JSON given cannot be parsed, because it is missing a '$TMF_VERSION' field to indicate which version of the library it works with.");
        }
        tmfVersion = tmfVersion.trim();
        TrustmarkFramework framework = FactoryLoader.getInstance(TrustmarkFramework.class);
        Double trustmarkFrameworkVersion = Double.parseDouble(framework.getTrustmarkFrameworkVersion());
        Double frameworkVersion = Double.parseDouble((tmfVersion));
        if (trustmarkFrameworkVersion < frameworkVersion) {
            throw new ParseException("The TMF Version in the JSON Object given [" + tmfVersion + "] does not support your API version[" + framework.getTrustmarkFrameworkVersion() + "], and cannot be parsed.  Try converting it first.");
        }
    }

    private static void isSupportedType(JSONObject json) throws ParseException {
        String type = json.optString(ATTRIBUTE_KEY_JSON_TYPE);
        if (type == null || type.trim().length() == 0) {
            throw new ParseException("This JSON is not supported.  The JSON given cannot be parsed, because it is missing a '$Type' field to indicate which object this is.");
        }
        type = type.trim();

        if (!getTypesSupported().contains(type.toLowerCase())) {
            throw new ParseException("Cannot parse object of type '" + type + "'.  This API version does not support that object.");
        }
    }

    private static List<String> getTypesSupported() {
        List<String> typesSupported = new ArrayList<>();
        typesSupported.add("trustmarkdefinition");
        typesSupported.add("trustinteroperabilityprofile");
        typesSupported.add("trustmark");
        typesSupported.add("trustmarkstatusreport");
        typesSupported.add("agreement");
        typesSupported.add("agreementresponsibilitytemplate");
        return typesSupported;
    }

    public static JSONObject readJSONObject(final String string) throws ParseException {
        try {
            return new JSONObject(string);
        } catch (final JSONException jsonException) {
            throw new ParseException(jsonException);
        }
    }

    public static JSONArray readJSONArray(final String string) throws ParseException {
        try {
            return new JSONArray(string);
        } catch (final JSONException jsonException) {
            throw new ParseException(jsonException);
        }
    }

    /**
     * Return the JSONObject associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the JSONObject
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type JSONObject
     */
    public static JSONObject readJSONObject(final JSONObject jsonObject, final String key) throws ParseException {
        return read(jsonObject, key, JSONObject.class, jsonObject::getJSONObject);
    }

    /**
     * Return some JSONObject associated with the key in the json object, if
     * present; otherwise, none.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return some JSONObject, if present; none, if absent
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the value is present and is not of type
     *                              JSONObject
     */
    public static Option<JSONObject> readJSONObjectOption(final JSONObject jsonObject, final String key) throws ParseException {
        return readOption(jsonObject, key, JSONObject.class, jsonObject::getJSONObject);
    }

    /**
     * Return the string associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the string
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type String
     */
    public static String readString(final JSONObject jsonObject, final String key) throws ParseException {
        return read(jsonObject, key, String.class, jsonObject::getString);
    }

    /**
     * Return some string associated with the key in the json object, if
     * present; otherwise, none.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return some string, if present; none, if absent
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the value is present and is not of type
     *                              String
     */
    public static Option<String> readStringOption(final JSONObject jsonObject, final String key) throws ParseException {
        return readOption(jsonObject, key, String.class, jsonObject::getString);
    }

    /**
     * Return the int associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the int
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type int
     */
    public static Integer readInt(final JSONObject jsonObject, final String key) throws ParseException {
        return read(jsonObject, key, Integer.class, jsonObject::getInt);
    }

    /**
     * Return some int associated with the key in the json object, if present;
     * otherwise, none.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return some int, if present; none, if absent
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the value is present and is not of type
     *                              int
     */
    public static Option<Integer> readIntOption(final JSONObject jsonObject, final String key) throws ParseException {
        return readOption(jsonObject, key, Integer.class, jsonObject::getInt);
    }

    /**
     * Return the boolean associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the boolean
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type int
     */
    public static boolean readBoolean(final JSONObject jsonObject, final String key) throws ParseException {
        return read(jsonObject, key, Boolean.class, jsonObject::getBoolean);
    }

    /**
     * Return some boolean associated with the key in the json object, if
     * present; otherwise, none.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return some boolean, if present; none, if absent
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the value is present and is not of type
     *                              boolean
     */
    public static Option<Boolean> readBooleanOption(final JSONObject jsonObject, final String key) throws ParseException {
        return readOption(jsonObject, key, Boolean.class, jsonObject::getBoolean);
    }

    /**
     * Return the URI associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the URI
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type String
     * @throws ParseException       if the system cannot parse the value as a
     *                              URI
     */
    public static URI readURI(final JSONObject jsonObject, final String key) throws ParseException {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return read(readString(jsonObject, key), string -> new URI(string), key, URI.class.getName());
    }

    /**
     * Return some URL associated with the key in the json object, if present;
     * otherwise, none
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the URL
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type String
     * @throws ParseException       if the system cannot parse the value as a
     *                              URL
     */
    public static Option<URI> readURIOption(final JSONObject jsonObject, final String key) throws ParseException {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return read(readStringOption(jsonObject, key), option -> option.mapException(string -> new URI(string)), key, URI.class.getName());
    }

    /**
     * Return the URL associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the URL
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type String
     * @throws ParseException       if the system cannot parse the value as a
     *                              URL
     */
    public static URL readURL(final JSONObject jsonObject, final String key) throws ParseException {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return read(readString(jsonObject, key), string -> new URL(string), key, URL.class.getName());
    }

    /**
     * Return some URL associated with the key in the json object, if present;
     * otherwise, none
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the URL
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type String
     * @throws ParseException       if the system cannot parse the value as a
     *                              URL
     */
    public static Option<URL> readURLOption(final JSONObject jsonObject, final String key) throws ParseException {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return read(readStringOption(jsonObject, key), option -> option.mapException(string -> new URL(string)), key, URL.class.getName());
    }

    /**
     * Return the date associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the date
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws ParseException       if the key is absent
     * @throws ParseException       if the value is not of type String or long
     * @throws ParseException       if the system cannot parse the value as a
     *                              Date
     */
    public static Date readDate(final JSONObject jsonObject, final String key) throws ParseException {
        if (jsonObject.has(key)) {
            final Object object = jsonObject.get(key);
            if (object instanceof String) {
                try {
                    return DatatypeConverter.parseDateTime((String) object).getTime();
                } catch (final RuntimeException runtimeException) {
                    throw new ParseException(format("The property '%s' must be a Date; it is '%s'.", key, object));
                }
            } else if (object instanceof Long) {
                return new Date((Long) object); // I assume the default is GMT timezone?
            } else {
                throw new ParseException(format("If the json object has the property '%s', it must be of type String or long.", key));
            }
        } else {
            throw new ParseException(format("The json object must have the String or long property '%s'.", key));
        }
    }

    /**
     * Return the list of Strings associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the list of Strings
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     */
    public static org.gtri.fj.data.List<String> readStringList(final JSONObject jsonObject, final String key) {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return readList(jsonObject, key, String.class);
    }

    /**
     * Return the list of JSONObjects associated with the key in the json
     * object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the list of JSONObjects
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     */
    public static org.gtri.fj.data.List<JSONObject> readJSONObjectList(final JSONObject jsonObject, final String key) {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return readList(jsonObject, key, JSONObject.class);
    }

    /**
     * Return the list of objects associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the list of objects
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     */
    public static org.gtri.fj.data.List<Object> readObjectList(final JSONObject jsonObject, final String key) {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return readList(jsonObject, key, Object.class);
    }

    /**
     * Return the list of URLs associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the list of URLs
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     */
    public static org.gtri.fj.data.List<URL> readURLList(final JSONObject jsonObject, final String key) throws ParseException {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return read(readStringList(jsonObject, key), list -> list.mapException(URL::new), key, URL.class.getName());
    }

    /**
     * Return the list of URIs associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return the list of URIs
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     */
    public static org.gtri.fj.data.List<URI> readURIList(final JSONObject jsonObject, final String key) throws ParseException {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return read(readStringList(jsonObject, key), list -> list.mapException(URI::new), key, URI.class.getName());
    }

    /**
     * Return the value associated with the key in the json object
     *
     * @param jsonObject the json object
     * @param key        the key
     * @param clazz      the type of the value
     * @param get        the getter for the value
     * @param <T1>       the type of the value
     * @return the value
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws NullPointerException if clazz is null
     * @throws NullPointerException if get is null
     * @throws ParseException       if the key is absent
     */
    private static <T1> T1 read(final JSONObject jsonObject, final String key, Class<T1> clazz, F1<String, T1> get) throws ParseException {
        requireNonNull(jsonObject);
        requireNonNull(key);
        requireNonNull(clazz);
        requireNonNull(get);

        try {
            final T1 value = get.f(key);
            log.debug(format("'%s' = '%s' (%s)", key, value, clazz));
            return value;
        } catch (final JSONException jsonException) {
            throw new ParseException(format("The json object must have the '%s' property '%s'.", clazz, key), jsonException);
        }
    }

    /**
     * Convert the value.
     *
     * @param value     the value
     * @param key       the key
     * @param className the name of the type of the value
     * @param <T1>      the type of the value
     * @return the value
     * @throws NullPointerException if value is null
     * @throws NullPointerException if convert is null
     * @throws NullPointerException if key is null
     * @throws NullPointerException if className is null
     * @throws ParseException       if the system cannot convert the value to
     *                              the given type
     */
    public static <T1, T2, E extends Exception> T2 read(final T1 value, final Try1<T1, T2, E> convert, final String key, final String className) throws ParseException {
        requireNonNull(value);
        requireNonNull(convert);
        requireNonNull(key);
        requireNonNull(className);

        final Validation<E, T2> validation = Try.f(() -> convert.f(value))._1();

        if (validation.isSuccess()) {
            return validation.success();
        } else {
            throw new ParseException(format("The property '%s' must be a '%s'; it is '%s'.", key, className, value), validation.fail());
        }
    }

    /**
     * Return the value associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @param clazz      the type of the value
     * @param get        the getter for the value
     * @param <T1>       the type of the value
     * @return the value
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws NullPointerException if clazz is null
     * @throws NullPointerException if get is null
     * @throws ParseException       if the key is present but the value is not
     *                              of the type
     */
    private static <T1> Option<T1> readOption(final JSONObject jsonObject, final String key, Class<T1> clazz, F1<String, T1> get) throws ParseException {
        requireNonNull(jsonObject);
        requireNonNull(key);
        requireNonNull(clazz);
        requireNonNull(get);

        if (jsonObject.has(key)) {
            try {
                if (jsonObject.isNull(key)) {
                    return none();
                } else {
                    final T1 value = get.f(key);
                    log.debug(format("'%s' = '%s' (%s)", key, value, clazz));
                    return some(value);
                }
            } catch (final JSONException jsonException) {
                throw new ParseException(format("If the json object has the property '%s', it must be of type '%s'.", key, clazz), jsonException);
            }
        } else {
            return none();
        }
    }

    /**
     * Return the list associated with the key in the json object.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @param clazz      the type of the value
     * @param <T1>       the type of the value
     * @return the value
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     * @throws NullPointerException if clazz is null
     */
    public static <T1> org.gtri.fj.data.List<T1> readList(final JSONObject jsonObject, final String key, final Class<T1> clazz) {
        requireNonNull(jsonObject);
        requireNonNull(key);
        requireNonNull(clazz);

        if (jsonObject.has(key)) {
            final Object object = jsonObject.get(key);

            if (object instanceof JSONArray) {
                final JSONArray jsonArray = (JSONArray) object;
                return org.gtri.fj.data.List.range(0, jsonArray.length()).map(index -> jsonArray.get(index))
                        .filter(Objects::nonNull)
                        .filter(objectInner -> clazz.isInstance(objectInner))
                        .map(objectInner -> (T1) objectInner);
            } else if (clazz.isInstance(jsonObject)) {
                return single((T1) object);
            } else {
                return nil();
            }
        } else {
            return nil();
        }
    }

    /**
     * Return a function that returns the value associated with the key in the
     * map.
     *
     * @param map  the map
     * @param key  the function that returns the key for the json object
     * @param <T1> the type of the value
     * @return the value
     * @throws NullPointerException if map is null
     * @throws NullPointerException if key is null
     */
    public static <T1> Try1<JSONObject, T1, ParseException> readFromMap(final TreeMap<String, T1> map, final Try1<JSONObject, String, ParseException> key) {
        requireNonNull(map);
        requireNonNull(key);

        return jsonObject -> {
            requireNonNull(jsonObject);

            final Option<T1> object = map.get(key.f(jsonObject));

            if (object.isSome()) {
                return object.some();
            } else {
                throw new ParseException(format("The key '%s' does not exist in the map.", key.f(jsonObject)));
            }
        };
    }

    /**
     * Return a trustmark framework identified object.
     *
     * @param jsonObject the json object
     * @return the trustmark framework identified object
     * @throws NullPointerException if jsonObject is null
     * @throws ParseException       if Identifier is null
     * @throws ParseException       if Identifier is not a string
     * @throws ParseException       if Number is present and not an int
     * @throws ParseException       if Description is present and not a string
     * @throws ParseException       if Name is present and not a string
     * @throws ParseException       if Version is present and not a string
     */
    public static TrustmarkFrameworkIdentifiedObject readTrustmarkDefinitionReference(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final TrustmarkFrameworkIdentifiedObjectImpl trustmarkFrameworkIdentifiedObject = new TrustmarkFrameworkIdentifiedObjectImpl();

        trustmarkFrameworkIdentifiedObject.setIdentifier(readURI(jsonObject, "Identifier"));
        trustmarkFrameworkIdentifiedObject.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUSTMARK_DEFINITION_REFERENCE);

        readIntOption(jsonObject, "Number").forEach(trustmarkFrameworkIdentifiedObject::setNumber);
        readStringOption(jsonObject, "Description").forEach(trustmarkFrameworkIdentifiedObject::setDescription);
        readStringOption(jsonObject, "Name").forEach(trustmarkFrameworkIdentifiedObject::setName);
        readStringOption(jsonObject, "Version").forEach(trustmarkFrameworkIdentifiedObject::setVersion);

        return trustmarkFrameworkIdentifiedObject;
    }

    /**
     * Return a term.
     *
     * @param jsonObject the json object
     * @return a term
     * @throws NullPointerException if jsonObject is null
     * @throws ParseException       if Definition is absent
     * @throws ParseException       if Definition is not a string
     * @throws ParseException       if Name is absent
     * @throws ParseException       if Name is not a string
     */
    public static TermImpl readTerm(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final TermImpl term = new TermImpl();

        term.setDefinition(readString(jsonObject, "Definition"));
        term.setName(readString(jsonObject, "Name"));

        readStringList(jsonObject, "Abbreviations").forEach(term::addAbbreviation);

        return term;
    }

    /**
     * Return a source.
     *
     * @param jsonObject
     * @return a source
     * @throws NullPointerException if jsonObject is null
     * @throws ParseException       if Identifier is absent
     * @throws ParseException       if Identifier is not a string
     * @throws ParseException       if Reference is absent
     * @throws ParseException       if Reference is not a string
     */
    public static SourceImpl readSource(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final SourceImpl source = new SourceImpl();

        source.setIdentifier(readString(jsonObject, "Identifier"));
        source.setReference(readString(jsonObject, "Reference"));

        return source;
    }

    /**
     * Return some extension, if the list associated with the key is non-empty;
     * otherwise, return none.
     *
     * @param jsonObject the json object
     * @param key        the key
     * @return some extension, or none
     * @throws NullPointerException if jsonObject is null
     * @throws NullPointerException if key is null
     */
    public static Option<ExtensionImpl> readExtensionOption(final JSONObject jsonObject, final String key) {
        requireNonNull(jsonObject);
        requireNonNull(key);

        return fromList(readObjectList(jsonObject, key))
                .map(objectList -> {
                    final ExtensionImpl extension = new ExtensionImpl();
                    extension.setData(objectList.toList().toJavaList());
                    return extension;
                });
    }

    /**
     * Return an entity.
     *
     * @param jsonObject the json object
     * @return the entity
     * @throws NullPointerException if jsonObject is null
     * @throws ParseException       if Identifier is null
     * @throws ParseException       if Identifier is not a string
     * @throws ParseException       if Name is null
     * @throws ParseException       if Name is not a string
     * @throws ParseException       if PrimaryContact is present and not a
     *                              contact
     * @throws ParseException       if Contact is present and not a contact
     * @throws ParseException       if OtherContacts is present and not a
     *                              contact
     * @throws ParseException       if Contacts is present and not a contact
     * @throws ParseException       if none of PrimaryContact, Contact,
     *                              OtherContacts, and Contacts is present
     */
    public static EntityImpl readEntity(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final EntityImpl entity = new EntityImpl();

        entity.setIdentifier(readURI(jsonObject, "Identifier"));
        entity.setName(readString(jsonObject, "Name"));

        // the default contact is the first one that appears in the list, so the order of these lines is important
        readJSONObjectOption(jsonObject, "PrimaryContact").mapException(JsonDeserializerUtility::readContact).forEach(entity::addContact);
        readJSONObjectOption(jsonObject, "Contact").mapException(JsonDeserializerUtility::readContact).forEach(entity::addContact);
        readJSONObjectList(jsonObject, "OtherContacts").mapException(JsonDeserializerUtility::readContact).forEach(entity::addContact);
        readJSONObjectList(jsonObject, "Contacts").mapException(JsonDeserializerUtility::readContact).forEach(entity::addContact);

        if (entity.getDefaultContact() == null) {
            throw new ParseException("The entity must have at least one of the following: PrimaryContact, Contact, OtherContacts, Contacts.");
        }

        return entity;
    }

    /**
     * Return an entity reference
     *
     * @param jsonObject the json object
     * @return the entity reference
     * @throws NullPointerException if jsonObject is null
     * @throws ParseException       if Identifier is null
     * @throws ParseException       if Identifier is not a string
     * @throws ParseException       if Name is present and not a string
     * @throws ParseException       if PrimaryContact is present and not a
     *                              contact
     * @throws ParseException       if Contact is present and not a contact
     * @throws ParseException       if OtherContacts is present and not a
     *                              contact
     * @throws ParseException       if Contacts is present and not a contact
     * @throws ParseException       if none of PrimaryContact, Contact,
     *                              OtherContacts, and Contacts is present
     */
    public static EntityImpl readEntityReference(JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final EntityImpl entity = new EntityImpl();

        entity.setIdentifier(readURI(jsonObject, "Identifier"));
        readStringOption(jsonObject, "Name").forEach(entity::setName);

        // the default contact is the first one that appears in the list, so the order of these lines is important
        readJSONObjectOption(jsonObject, "PrimaryContact").mapException(JsonDeserializerUtility::readContact).forEach(entity::addContact);
        readJSONObjectOption(jsonObject, "Contact").mapException(JsonDeserializerUtility::readContact).forEach(entity::addContact);
        readJSONObjectList(jsonObject, "OtherContacts").mapException(JsonDeserializerUtility::readContact).forEach(entity::addContact);
        readJSONObjectList(jsonObject, "Contacts").mapException(JsonDeserializerUtility::readContact).forEach(entity::addContact);

        return entity;
    }

    /**
     * Return a contact.
     *
     * @param jsonObject the json object
     * @return the contact
     * @throws NullPointerException if jsonObject is null
     * @throws ParseException       if WebsiteURL is present and not a URL
     * @throws ParseException       if WebsiteURLs is present and not a URL
     * @throws ParseException       if none of Email, Emails, MailingAddress,
     *                              MailingAddresses, Notes, PhysicalAddress,
     *                              PhysicalAddresses, Responder, Telephone,
     *                              Telephones, WebsiteURL, and WebsiteURLs is
     *                              present
     */
    public static ContactImpl readContact(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final ContactImpl contact = new ContactImpl();

        contact.setKind(readStringOption(jsonObject, "Kind").bind(JsonDeserializerUtility::readContactKindCode).orSome(ContactKindCode.OTHER));

        // the default contact is the first one that appears in the list, so the order of these lines is important
        readStringOption(jsonObject, "Email").forEach(contact::addEmail);
        readStringOption(jsonObject, "MailingAddress").forEach(contact::addMailingAddress);
        readStringOption(jsonObject, "Notes").forEach(contact::setNotes);
        readStringOption(jsonObject, "PhysicalAddress").forEach(contact::addPhysicalAddress);
        readStringOption(jsonObject, "Responder").forEach(contact::setResponder);
        readStringOption(jsonObject, "Telephone").forEach(contact::addTelephone);

        readURLOption(jsonObject, "WebsiteURL").forEach(contact::addWebsiteURL);

        readStringList(jsonObject, "Emails").forEach(contact::addEmail);
        readStringList(jsonObject, "MailingAddresses").forEach(contact::addMailingAddress);
        readStringList(jsonObject, "PhysicalAddresses").forEach(contact::addPhysicalAddress);
        readStringList(jsonObject, "Telephones").forEach(contact::addTelephone);

        readURLList(jsonObject, "WebsiteURLs").forEach(contact::addWebsiteURL);

        if (contact.getEmails().isEmpty() &&
                contact.getMailingAddresses().isEmpty() &&
                contact.getNotes().isEmpty() &&
                contact.getPhysicalAddresses().isEmpty() &&
                contact.getResponder().isEmpty() &&
                contact.getTelephones().isEmpty() &&
                contact.getWebsiteURLs().isEmpty()) {
            throw new ParseException("The entity must have at least one of the following: Email, Emails, MailingAddress, MailingAddresses, Notes, PhysicalAddress, PhysicalAddresses, Responder, Telephone, Telephones, WebsiteURL, WebsiteURLs.");
        }

        return contact;
    }

    /**
     * Return some ContactKindCode, if present; otherwise, none.
     *
     * @param string the string representation of the ContactKindCode
     * @return some ContactKindCode, if present; otherwise, none
     */
    private static Option<ContactKindCode> readContactKindCode(final String string) {
        return fromNull(ContactKindCode.fromString(string));
    }
}
