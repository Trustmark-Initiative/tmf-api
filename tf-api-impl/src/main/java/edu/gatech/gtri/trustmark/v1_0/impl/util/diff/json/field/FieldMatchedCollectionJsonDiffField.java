package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public class FieldMatchedCollectionJsonDiffField
    extends MatchedCollectionJsonDiffField<FieldMatchedCollectionJsonDiffField.FieldsKey>
{
    
    ////// Instance Fields //////
    public final List<String> collectionMatchFields;
    
    ////// Constructors //////
    public FieldMatchedCollectionJsonDiffField(
        String _location,
        DiffSeverity _severityForNotComparedFields,
        List<JsonDiffField> _children,
        List<String> _collectionMatchFields
    ) {
        super(_location, _severityForNotComparedFields, _children);
        this.collectionMatchFields = _collectionMatchFields == null
                                     ? Collections.emptyList()
                                     : Collections.unmodifiableList(_collectionMatchFields)
        ;
    }
    
    public FieldMatchedCollectionJsonDiffField(
        String _location,
        DiffSeverity _severityForNotComparedFields,
        List<JsonDiffField> _children,
        String... _collectionMatchFields
    ) {
        this(
            _location,
            _severityForNotComparedFields,
            _children,
            _collectionMatchFields == null ? null : Arrays.asList(_collectionMatchFields)
        );
    }
    
    ////// Static Inner Classes //////
    public static class Builder extends AbstractBuilder<FieldMatchedCollectionJsonDiffField> {
        //// Constructor ////
        protected Builder(FieldMatchedCollectionJsonDiffField _partiallyBuiltParent) {
            super(_partiallyBuiltParent);
        }
    
        //// Instance Methods ////
        @Override
        public FieldMatchedCollectionJsonDiffField withChildren(DiffSeverity severityForNotComparedFields, List<JsonDiffField> children) {
            return new FieldMatchedCollectionJsonDiffField(
                this.partiallyBuiltField.location,
                severityForNotComparedFields,
                children,
                this.partiallyBuiltField.collectionMatchFields
            );
        }
    }
    
    public static class FieldsKey extends TreeMap<String, Object> {
        public FieldsKey(FieldMatchedCollectionJsonDiffField collectionDiffField, JSONObject collectionItem) {
            for (String matchFieldLocation : collectionDiffField.collectionMatchFields) {
                Object matchFieldValue = getJsonField(collectionItem, matchFieldLocation);
                this.put(matchFieldLocation, matchFieldValue);
            }
        }
    }
    
    ////// Instance Methods //////
    @Override
    protected List<String> getMatchedLocations() {
        return this.collectionMatchFields;
    }
    
    @Override
    protected FieldsKey getMatchKey(JSONObject collectionItem) {
        return new FieldsKey(this, collectionItem);
    }
    
    @Override
    protected Map<FieldsKey, JSONObject> getMapForExpectedNestedComparison(
        Map<FieldsKey, JSONObject> expectedItemsByMatchKey,
        AbstractJsonDiff.JsonDiffContext diffContext
    ) {
        return expectedItemsByMatchKey;
    }
    
    @Override
    protected Map<FieldsKey, JSONObject> getMapForActualNestedComparison(
        Map<FieldsKey, JSONObject> actualItemsByMatchKey,
        AbstractJsonDiff.JsonDiffContext diffContext
    ) {
        return actualItemsByMatchKey;
    }
    
    @Override
    protected boolean shouldSkipNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, FieldsKey matchKey) {
        return false;
    }
    
    @Override
    protected void beforeNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, FieldsKey matchKey) {
        // do nothing
    }
    
    @Override
    protected String getNestedChildLocation(String fullFieldLocation, FieldsKey matchKey) {
        return fullFieldLocation + matchKey.entrySet().toString();
    }
    
    @Override
    protected void afterNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, FieldsKey matchKey) {
        // do nothing
    }
}
