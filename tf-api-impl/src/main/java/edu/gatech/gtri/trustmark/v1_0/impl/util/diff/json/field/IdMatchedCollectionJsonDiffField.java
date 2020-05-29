package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.IdRefType;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public class IdMatchedCollectionJsonDiffField extends MatchedCollectionJsonDiffField<String> {
    
    ////// Instance Fields //////
    public final String idType;
    public final boolean isRefOnly;
    
    ////// Constructor //////
    public IdMatchedCollectionJsonDiffField(
        String _location,
        DiffSeverity _severityForNotComparedFields,
        List<JsonDiffField> _children,
        String _idType,
        boolean _isRefOnly
    ) {
        super(_location, _severityForNotComparedFields, _children);
        this.idType = _idType;
        this.isRefOnly = _isRefOnly;
    }
    
    ////// Static Inner Classes //////
    public static class Builder extends AbstractBuilder<IdMatchedCollectionJsonDiffField> {
        //// Constructor ////
        protected Builder(IdMatchedCollectionJsonDiffField _partiallyBuiltParent) {
            super(_partiallyBuiltParent);
        }
        
        //// Instance Methods ////
        @Override
        public IdMatchedCollectionJsonDiffField withChildren(DiffSeverity severityForNotComparedFields, List<JsonDiffField> children) {
            return new IdMatchedCollectionJsonDiffField(
                this.partiallyBuiltField.location,
                severityForNotComparedFields,
                children,
                this.partiallyBuiltField.idType,
                this.partiallyBuiltField.isRefOnly
            );
        }
    }
    
    ////// Static Methods //////
    protected static Map.Entry<IdRefType, String> getReferenceInfoFor(JSONObject jsonObject) {
        Map.Entry<IdRefType, String> result;
        try {
            result = AbstractDocumentJsonDeserializer.getReferenceInfoForJsonObject(jsonObject);
        }
        catch (Exception ex) {
            throw new AbstractJsonDiff.JsonDiffException(ex.getMessage(), ex);
        }
        return result;
    }
    
    
    ////// Instance Methods //////
    @Override
    protected final void fillIdResolutionMapsForCollection(
        Map<String, Map<String, JSONObject>> idResolutionMapsByIdType,
        JSONArray arrayValue
    ) {
        Map<String, JSONObject> idResolutionMap = idResolutionMapsByIdType.computeIfAbsent(this.idType, it -> new HashMap<>());
        for (Object o : arrayValue) {
            JSONObject collectionItem = getJsonObjectOrNull(o);
            if (collectionItem == null) { continue; }
            Map.Entry<IdRefType, String> referenceInfo = getReferenceInfoFor(collectionItem);
            IdRefType idRefType = referenceInfo.getKey();
            String id = referenceInfo.getValue();
            if (idRefType == IdRefType.ID) {
                idResolutionMap.put(id, collectionItem);
            }
        }
    }
    
    @Override
    protected List<String> getMatchedLocations() {
        return Arrays.asList(
            ATTRIBUTE_KEY_JSON_ID,
            ATTRIBUTE_KEY_JSON_REF
        );
    }
    
    @Override
    protected String getMatchKey(JSONObject collectionItem) {
        Map.Entry<IdRefType, String> referenceInfo = getReferenceInfoFor(collectionItem);
        return referenceInfo.getValue();
    }
    
    @Override
    protected Map<String, JSONObject> getMapForExpectedNestedComparison(
        Map<String, JSONObject> expectedItemsByMatchKey,
        AbstractJsonDiff.JsonDiffContext diffContext
    ) {
        return diffContext.expectedIdResolutionMapsByIdType.get(this.idType);
    }
    
    @Override
    protected Map<String, JSONObject> getMapForActualNestedComparison(
        Map<String, JSONObject> actualItemsByMatchKey,
        AbstractJsonDiff.JsonDiffContext diffContext
    ) {
        return diffContext.actualIdResolutionMapsByIdType.get(this.idType);
    }
    
    @Override
    protected boolean shouldSkipNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, String matchKey) {
        if (this.isRefOnly) { return true; }
        Map.Entry<String, String> idTypeParentId = new AbstractMap.SimpleEntry<>(this.idType, matchKey);
        return diffContext.idTypeParentIdStack.contains(idTypeParentId);
    }
    
    @Override
    protected void beforeNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, String matchKey) {
        Map.Entry<String, String> idTypeParentId = new AbstractMap.SimpleEntry<>(this.idType, matchKey);
        diffContext.idTypeParentIdStack.push(idTypeParentId);
    }
    
    @Override
    protected String getNestedChildLocation(String fullFieldLocation, String matchKey) {
        return String.format("%s[%s=\"%s\"]", fullFieldLocation, ATTRIBUTE_KEY_JSON_ID, matchKey);
    }
    
    @Override
    protected void afterNestedComparison(AbstractJsonDiff.JsonDiffContext diffContext, String matchKey) {
        diffContext.idTypeParentIdStack.pop();
    }
}
