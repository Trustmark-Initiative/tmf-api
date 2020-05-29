package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.apache.commons.collections4.SetUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Nicholas Saney on 2017-05-05.
 */
public abstract class MatchedCollectionJsonDiffField<T> extends CollectionJsonDiffField {
    
    ////// Constructor //////
    protected MatchedCollectionJsonDiffField(String _location, DiffSeverity _severityForNotComparedFields, List<JsonDiffField> _children) {
        super(_location, _severityForNotComparedFields, _children);
    }
    
    
    ////// Static Inner Classes //////
    public static abstract class AbstractBuilder<MC extends MatchedCollectionJsonDiffField> {
        //// Instance Fields ////
        public final MC partiallyBuiltField;
        
        //// Constructor ////
        protected AbstractBuilder(MC _partiallyBuiltField) {
            this.partiallyBuiltField = _partiallyBuiltField;
        }
        
        //// Instance Methods - Abstract ////
        /**
         * Builds a matched-collection field of a JSON diff that has some number of child fields to compare.
         * @param severityForNotComparedFields the severity to report for collection-item child fields that are not compared
         * @param children the child fields of the collection items in this collection node
         * @return a matched-collection JSON diff field
         */
        public abstract MC withChildren(DiffSeverity severityForNotComparedFields, List<JsonDiffField> children);
        
        //// Instance Methods - Concrete ////
        
        /**
         * Builds a matched-collection field of a JSON diff that has no child fields to compare.
         * @return a matched-collection JSON diff field
         */
        public MC withoutChildren() {
            return this.withChildren(DiffSeverity.MAJOR);
        }
        
        /**
         * Builds a matched-collection field of a JSON diff that has some number of child fields to compare.
         * @param severityForNotComparedFields the severity to report for collection-item child fields that are not compared
         * @param children the child fields of the collection items in this collection node
         * @return a matched-collection JSON diff field
         */
        public MC withChildren(DiffSeverity severityForNotComparedFields, JsonDiffField... children) {
            return this.withChildren(
                severityForNotComparedFields,
                children == null ? null : Arrays.asList(children)
            );
        }
        
        /**
         * Builds a matched-collection field of a JSON diff that has the same severity for not-compared fields
         * and child fields as the given JSON diff field
         * @param otherField the JSON diff field whose severity for not-compared fields and child fields to copy
         * @return a matched-collection JSON diff field
         */
        public MC withChildrenFrom(JsonDiffField otherField) {
            return this.withChildren(otherField.severityForNotComparedFields, otherField.children);
        }
    }
    
    
    ////// Instance Methods - Protected //////
    @Override
    protected void doCollectionJsonDiff(
        String fullFieldLocation,
        JsonDiffField parentField,
        AbstractJsonDiff.JsonDiffContext diffContext,
        JSONArray arrayValueExpected,
        JSONArray arrayValueActual
    ) {
        String matchedFieldLocation = fullFieldLocation + "[...]";
        // expected
        Map<T, JSONObject> mapExpectedByMatchKey = this.getCollectionItemsByMatchKey(arrayValueExpected);
        // actual
        Map<T, JSONObject> mapActualByMatchKey = this.getCollectionItemsByMatchKey(arrayValueActual);
        // missing
        Set<T> setMissingKeys = SetUtils.difference(mapExpectedByMatchKey.keySet(), mapActualByMatchKey.keySet());
        Map<T, JSONObject> mapMissing = filterMap(mapExpectedByMatchKey, setMissingKeys);
        diffContext.putAllMissing(matchedFieldLocation, mapMissing.values(), DiffSeverity.MAJOR);
        // unexpected
        Set<T> setUnexpectedKeys = SetUtils.difference(mapActualByMatchKey.keySet(), mapExpectedByMatchKey.keySet());
        Map<T, JSONObject> mapUnexpected = filterMap(mapActualByMatchKey, setUnexpectedKeys);
        diffContext.putAllUnexpected(matchedFieldLocation, mapUnexpected.values(), DiffSeverity.MAJOR);
        // possibly matched
        Set<T> setPossiblyMatched = SetUtils.intersection(mapExpectedByMatchKey.keySet(), mapActualByMatchKey.keySet());
        Map<T, JSONObject> mapExpectedForNestedComparison = this.getMapForExpectedNestedComparison(mapExpectedByMatchKey, diffContext);
        Map<T, JSONObject> mapActualForNestedComparison = this.getMapForActualNestedComparison(mapActualByMatchKey, diffContext);
        for (T matchKey : setPossiblyMatched) {
            boolean shouldSkip = this.shouldSkipNestedComparison(diffContext, matchKey);
            if (shouldSkip) { continue; }
            this.beforeNestedComparison(diffContext, matchKey);
            String childLocation = this.getNestedChildLocation(fullFieldLocation, matchKey);
            JSONObject jsonExpected = mapExpectedForNestedComparison.get(matchKey);
            JSONObject jsonActual = mapActualForNestedComparison.get(matchKey);
            this.doJsonDiffForChildren(childLocation, diffContext, jsonExpected, jsonActual);
            this.afterNestedComparison(diffContext, matchKey);
        }
    }
    
    protected Map<T, JSONObject> getCollectionItemsByMatchKey(JSONArray jsonArray) {
        Map<T, JSONObject> result = new HashMap<>();
        for (Object o : jsonArray) {
            JSONObject collectionItem = getJsonObjectOrNull(o);
            if (collectionItem == null) { continue; }
            T matchKey = this.getMatchKey(collectionItem);
            result.put(matchKey, collectionItem);
        }
        return result;
    }
    
    protected abstract List<String> getMatchedLocations();
    
    protected abstract T getMatchKey(JSONObject collectionItem);
    
    protected abstract Map<T, JSONObject> getMapForExpectedNestedComparison(
        Map<T, JSONObject> expectedItemsByMatchKey,
        AbstractJsonDiff.JsonDiffContext diffContext
    );
    
    protected abstract Map<T, JSONObject> getMapForActualNestedComparison(
        Map<T, JSONObject> actualItemsByMatchKey,
        AbstractJsonDiff.JsonDiffContext diffContext
    );
    
    protected abstract boolean shouldSkipNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, T matchKey);
    
    protected abstract void beforeNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, T matchKey);
    
    protected abstract String getNestedChildLocation(String fullFieldLocation, T matchKey);
    
    protected abstract void afterNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, T matchKey);

}

