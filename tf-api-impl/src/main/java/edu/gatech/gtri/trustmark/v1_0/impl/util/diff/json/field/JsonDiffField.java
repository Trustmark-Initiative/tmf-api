package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;


import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffType;
import org.apache.commons.collections4.SetUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public abstract class JsonDiffField {
    
    ////// Constants //////
    public static final String LOCATION_SEPARATOR = ".";
    public static final Pattern LOCATION_SEPARATOR_PATTERN = Pattern.compile(Pattern.quote(LOCATION_SEPARATOR));
    
    
    ////// Instance Fields //////
    public final String location;
    public final DiffSeverity severityForNotComparedFields;
    public final List<JsonDiffField> children;
    
    
    ////// Constructor //////
    public JsonDiffField(String _location, DiffSeverity _severityForNotComparedFields, List<JsonDiffField> _children) {
        this.location = _location;
        this.severityForNotComparedFields = _severityForNotComparedFields;
        this.children = _children == null
                        ? Collections.emptyList()
                        : Collections.unmodifiableList(_children)
        ;
    }
    
    
    ////// Static Methods - Public //////
    
    /**
     * Creates the root node of a JSON diff, containing all of the fields to be compared.
     * @param severityForNotComparedFields the severity to report for fields that are not compared
     * @param children the child fields of this root node
     * @return a root JSON diff field
     */
    public static RootNodeJsonDiffField rootNode(DiffSeverity severityForNotComparedFields, JsonDiffField... children) {
        return new RootNodeJsonDiffField(
            severityForNotComparedFields,
            children == null ? null : Arrays.asList(children)
        );
    }
    
    /**
     * Creates an exact single-value field of a JSON diff, indicating that any mismatch should be reported as MAJOR.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @return a single-value JSON diff field
     */
    public static ExactValueJsonDiffField valueExact(String location) {
        return new ExactValueJsonDiffField(location, String::compareTo);
    }
    
    /**
     * Creates a minor single-value field of a JSON diff, indicating that any mismatch should be reported as MINOR,
     * and the default comparator should be used.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @return a single-value JSON diff field
     */
    public static MinorValueJsonDiffField valueMinor(String location) {
        return new MinorValueJsonDiffField(location, String::compareTo);
    }
    
    /**
     * Creates a minor single-value field of a JSON diff, indicating that any mismatch should be reported as MINOR,
     * and the comparison should be made with the given comparator.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @param comparator the String comparator to use
     * @return a single-value JSON diff field
     */
    public static MinorValueJsonDiffField valueMinor(String location, Comparator<String> comparator) {
        return new MinorValueJsonDiffField(location, comparator);
    }
    
    /**
     * Creates a distance-based single-value field of a JSON diff, indicating that any mismatch should be reported
     * as MAJOR if the distance between the compared values is past a particular threshold, or MINOR otherwise.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @return a single-value JSON diff field
     */
    public static DistanceValueJsonDiffField valueDistance(String location) {
        return new DistanceValueJsonDiffField(location, String::compareTo);
    }
    
    /**
     * Creates an ignored-value field of a JSON diff, indicating that any mismatch should be ignored.
     * This can be used to ignore any location at all, including collections or nested objects.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @return an ignored-value JSON diff field
     */
    public static IgnoredValueJsonDiffField valueIgnore(String location) {
        return new IgnoredValueJsonDiffField(location);
    }
    
    /**
     * Creates an exact-value simple-collection field of a JSON diff, indicating that any missing or unexpected
     * value in the compared collection should be reported as MAJOR, and the default comparator should be used.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @return a simple-collection JSON diff field
     */
    public static SimpleCollectionJsonDiffField exactSimpleCollection(String location) {
        return new SimpleCollectionJsonDiffField(location, DiffSeverity.MAJOR, String::compareTo);
    }
    
    /**
     * Creates an exact-value simple-collection field of a JSON diff, indicating that any missing or unexpected
     * value in the compared collection should be reported as MAJOR, and the comparisons should be made with
     * the given comparator.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @param comparator the String comparator to use
     * @return a simple-collection JSON diff field
     */
    public static SimpleCollectionJsonDiffField exactSimpleCollection(String location, Comparator<String> comparator) {
        return new SimpleCollectionJsonDiffField(location, DiffSeverity.MAJOR, comparator);
    }
    
    /**
     * Creates a exact-value simple-collection field of a JSON diff, indicating that any missing or unexpected
     * value in the compared collection should be reported as MINOR, and the default comparator should be used.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @return a simple-collection JSON diff field
     */
    public static SimpleCollectionJsonDiffField minorSimpleCollection(String location) {
        return new SimpleCollectionJsonDiffField(location, DiffSeverity.MINOR, String::compareTo);
    }
    
    /**
     * Creates a exact-value simple-collection field of a JSON diff, indicating that any missing or unexpected
     * value in the compared collection should be reported as MINOR, and the comparisons should be made with
     * the given comparator.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @param comparator the String comparator to use
     * @return a simple-collection JSON diff field
     */
    public static SimpleCollectionJsonDiffField minorSimpleCollection(String location, Comparator<String> comparator) {
        return new SimpleCollectionJsonDiffField(location, DiffSeverity.MINOR, comparator);
    }
    
    /**
     * Creates a recursive-collection field of a JSON diff, indicating that the collection at the given location
     * in its parent should be treated like the parent object itself for comparisons.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @return a recursive-collection JSON diff field
     */
    public static RecursiveCollectionJsonDiffField recursiveCollection(String location) {
        return new RecursiveCollectionJsonDiffField(location);
    }
    
    /**
     * Creates a builder for a field-based matched-collection field of a JSON diff, indicating that the collection of
     * objects should be matched based on the given match fields within each object.
     * The returned builder can then be used for building an actual JSON diff field by giving the child fields of
     * the collection objects to be compared after successful matching.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @param matchFields the fields to match on within the objects of the collection
     * @return a builder for a matched-collection JSON diff field
     */
    public static FieldMatchedCollectionJsonDiffField.Builder fieldsCollection(String location, String... matchFields) {
        return new FieldMatchedCollectionJsonDiffField.Builder(new FieldMatchedCollectionJsonDiffField(
            location,
            null,
            null,
            matchFields
        ));
    }
    
    /**
     * Creates a builder for an ID-based matched-collection field of a JSON diff, indicating that the collection of
     * objects should be matched based on either an ID or a REF on each object, so that the order of the items in
     * a collection (or location of first appearance in a document with nested references) does not affect the diff.
     * When the diff runs, a map from ID to object will be kept for both compared documents, and it is this map that
     * will allow comparisons between two encountered objects that have the same ID in both documents and one or
     * both are a REF to a previously occurring object in the document.
     * The returned builder can then be used for building an actual JSON diff field by giving the child fields of
     * the collection objects to be compared after successful matching.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @param idType the type-name of the objects in the collection, which should be reused in different JSON diff fields
     *               if the same type of object can be referenced in another part of the document
     * @return a builder for a matched-collection JSON diff field
     */
    public static IdMatchedCollectionJsonDiffField.Builder idCollection(String location, String idType) {
        return new IdMatchedCollectionJsonDiffField.Builder(new IdMatchedCollectionJsonDiffField(
            location,
            null,
            null,
            idType,
            false
        ));
    }
    
    /**
     * Creates an ID-based matched-collection field of a JSON diff, indicating that the collection of
     * objects should be matched based on only the REF on each object. This is useful when the collection is known to
     * only include references to objects in another part of the document, so that the diff does not re-do comparisons
     * unnecessarily.
     * @param location the location of the JSON diff field in its parent (nesting can be indicated with . notation)
     * @param idType the type-name of the objects in the collection, which should be reused in different JSON diff fields
     *               if the same type of object can be referenced in another part of the document
     * @return a matched-collection JSON diff field
     */
    public static IdMatchedCollectionJsonDiffField refOnlyCollection(String location, String idType) {
        return new IdMatchedCollectionJsonDiffField(
            location,
            DiffSeverity.MAJOR,
            null,
            idType,
            true
        );
    }
    
    
    ////// Static Methods - Helpers //////
    protected static void addAllDirectObjectDescendentLocations(Set<String> results, JSONObject object, String objectLocation) {
        if (object == null) { return; }
        for (String childKey : object.keySet()) {
            String childLocation = getFullFieldLocation(objectLocation, childKey);
            results.add(childLocation);
            JSONObject child = object.optJSONObject(childKey);
            addAllDirectObjectDescendentLocations(results, child, childLocation);
        }
    }
    
    protected static Set<String> getDirectObjectAncestorLocations(String location) {
        Set<String> result = new TreeSet<>();
        String[] locationParts = getLocationParts(location);
        for (int len = 1; len <= locationParts.length; ++len) {
            String ancestor = Stream.of(locationParts).limit(len).collect(Collectors.joining(LOCATION_SEPARATOR));
            result.add(ancestor);
        }
        return result;
    }
    
    protected static String[] getLocationParts(String location) {
        return location == null ? new String[0] : LOCATION_SEPARATOR_PATTERN.split(location);
    }
    
    protected static String getFullFieldLocation(String parentLocation, String childLocation) {
        return parentLocation == null ? childLocation : (parentLocation + LOCATION_SEPARATOR + childLocation);
    }
    
    protected static Object getJsonField(JSONObject parent, String location) {
        Object result = null;
        String[] locationParts = getLocationParts(location);
        if (locationParts.length > 0) {
            String[] lastParentLocationParts = Arrays.copyOf(locationParts, locationParts.length - 1);
            JSONObject currentParent = parent;
            for (String locationPart : lastParentLocationParts) {
                if (currentParent == null) { break; }
                currentParent = currentParent.optJSONObject(locationPart);
            }
            if (currentParent != null) {
                String lastFieldName = locationParts[locationParts.length - 1];
                result = currentParent.opt(lastFieldName);
            }
        }
        return result;
    }
    
    protected static JSONArray getJsonArrayOrEmpty(Object obj) {
        return obj instanceof JSONArray ? (JSONArray)obj : new JSONArray();
    }
    
    protected static JSONObject getJsonObjectOrNull(Object obj) {
        return obj instanceof JSONObject ? (JSONObject)obj : null;
    }
    
    protected static <K, V> Map<K, V> filterMap(Map<K, V> original, Set<K> filterKeys) {
        Map<K, V> result = new HashMap<>();
        for (K key : filterKeys) {
            V value = original.get(key);
            result.put(key, value);
        }
        return result;
    }
    
    protected static String convertJsonToStandardStringIfNotAlreadyString(Object json) {
        return json instanceof String ? (String)json : convertJsonToStandardString(json);
    }
    
    protected static String convertJsonToStandardString(Object json) {
        if (JSONObject.NULL.equals(json)) { return "null"; }
        if (json instanceof CharSequence) { return "\"" + json.toString().replace("\"", "\\\"") + "\""; }
        if (json instanceof Number) { return String.valueOf(json); }
        if (json.getClass().isPrimitive()) { return String.valueOf(json); }
        if (json instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject)json;
            Stream<String> sortedKeys = jsonObject.keySet().stream().sorted();
            Stream<String> sortedEntries = sortedKeys.map(key -> convertJsonToStandardString(key) + ":" + convertJsonToStandardString(jsonObject.get(key)));
            String joinedJsonMap = sortedEntries.collect(Collectors.joining(","));
            return "{" + joinedJsonMap + "}";
        }
        if (json instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)json;
            Stream<String> elements = StreamSupport.stream(jsonArray.spliterator(), false).map(JsonDiffField::convertJsonToStandardString);
            String joinedJsonArray = elements.collect(Collectors.joining(","));
            return "[" + joinedJsonArray + "]";
        }
        String unrecognizedObjectString = String.format("unrecognized_type=%s;to_string=%s", json.getClass(), json);
        return convertJsonToStandardString(unrecognizedObjectString);
    }
    
    
    ////// Instance Methods - Public //////
    public void fillIdResolutionMapsForChildren(
        Map<String, Map<String, JSONObject>> idResolutionMapsByIdType,
        JSONObject object
    ) {
        for (JsonDiffField diffField : this.children) {
            diffField.fillIdResolutionMaps(idResolutionMapsByIdType, this, object);
        }
    }
    
    public void doJsonDiffForChildren(
        String location,
        AbstractJsonDiff.JsonDiffContext diffContext,
        JSONObject jsonExpected,
        JSONObject jsonActual
    ) {
        // deal with compared fields
        for (JsonDiffField diffField : this.children) {
            String fullFieldLocation = getFullFieldLocation(location, diffField.location);
            Object jsonFieldExpected = getJsonField(jsonExpected, diffField.location);
            Object jsonFieldActual = getJsonField(jsonActual, diffField.location);
            diffField.doJsonDiff(fullFieldLocation, this, diffContext, jsonFieldExpected, jsonFieldActual);
        }
        
        // deal with not-compared fields
        Set<String> comparedLocations = new TreeSet<>();
        if (this instanceof MatchedCollectionJsonDiffField) {
            MatchedCollectionJsonDiffField<?> self = (MatchedCollectionJsonDiffField<?>)this;
            List<String> matchedLocations = self.getMatchedLocations();
            for (String matchedLocation : matchedLocations) {
                Set<String> ancestorMatchedLocations = getDirectObjectAncestorLocations(matchedLocation);
                comparedLocations.addAll(ancestorMatchedLocations);
            }
        }
        for (JsonDiffField diffField : this.children) {
            Set<String> ancestorLocations = getDirectObjectAncestorLocations(diffField.location);
            comparedLocations.addAll(ancestorLocations);
            if (diffField instanceof ValueJsonDiffField) {
                // when a nested object is treated like a value, its entire sub-tree is compared as one big string
                JSONObject jsonFieldExpected = getJsonObjectOrNull(getJsonField(jsonExpected, diffField.location));
                JSONObject jsonFieldActual = getJsonObjectOrNull(getJsonField(jsonActual, diffField.location));
                Set<String> ignoredLocations = new TreeSet<>();
                addAllDirectObjectDescendentLocations(ignoredLocations, jsonFieldExpected, diffField.location);
                addAllDirectObjectDescendentLocations(ignoredLocations, jsonFieldActual, diffField.location);
                comparedLocations.addAll(ignoredLocations);
            }
        }
        
        Set<String> allLocations = new TreeSet<>();
        addAllDirectObjectDescendentLocations(allLocations, jsonExpected, null);
        addAllDirectObjectDescendentLocations(allLocations, jsonActual, null);
        
        Set<String> notComparedLocations = SetUtils.difference(allLocations, comparedLocations);
        for (String notComparedLocation : notComparedLocations) {
            String fullFieldLocation = getFullFieldLocation(location, notComparedLocation);
            Object jsonFieldExpected = getJsonField(jsonExpected, notComparedLocation);
            String stringValueExpected = convertJsonToStandardString(jsonFieldExpected);
            Object jsonFieldActual = getJsonField(jsonActual, notComparedLocation);
            String stringValueActual = convertJsonToStandardString(jsonFieldActual);
            // put diff result
            diffContext.putOne(
                fullFieldLocation,
                stringValueExpected,
                stringValueActual,
                JsonDiffType.NOT_COMPARED,
                this.severityForNotComparedFields
            );
        }
    }
    
    
    ////// Instance Methods - Protected //////
    protected abstract void fillIdResolutionMaps(
        Map<String, Map<String, JSONObject>> idResolutionMapsByIdType,
        JsonDiffField parentField,
        JSONObject parentObject
    );
    
    protected abstract void doJsonDiff(
        String fullFieldLocation,
        JsonDiffField parentField,
        AbstractJsonDiff.JsonDiffContext diffContext,
        Object jsonFieldExpected,
        Object jsonFieldActual
    );
}
