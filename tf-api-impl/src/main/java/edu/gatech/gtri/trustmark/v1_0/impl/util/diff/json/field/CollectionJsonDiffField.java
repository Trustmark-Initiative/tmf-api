package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public abstract class CollectionJsonDiffField extends JsonDiffField {
    
    ////// Constructor //////
    public CollectionJsonDiffField(String _location, DiffSeverity _severityForNotComparedFields, List<JsonDiffField> _children) {
        super(_location, _severityForNotComparedFields, _children);
    }
    
    
    ////// Instance Methods - Public //////
    @Override
    public final void fillIdResolutionMaps(
        Map<String, Map<String, JSONObject>> idResolutionMapsByIdType,
        JsonDiffField parentField,
        JSONObject parentObject
    ) {
        Object jsonField = getJsonField(parentObject, this.location);
        JSONArray arrayValue = getJsonArrayOrEmpty(jsonField);
        CollectionJsonDiffField nextDiffField = this.getSelfOrRecursiveCollectionParent(parentField);
        nextDiffField.fillIdResolutionMapsForCollection(idResolutionMapsByIdType, arrayValue);
        for (Object o : arrayValue) {
            JSONObject collectionItem = getJsonObjectOrNull(o);
            if (collectionItem == null) { continue; }
            nextDiffField.fillIdResolutionMapsForChildren(idResolutionMapsByIdType, collectionItem);
        }
    }
    
    @Override
    public void doJsonDiff(
        String fullFieldLocation,
        JsonDiffField parentField,
        AbstractJsonDiff.JsonDiffContext diffContext,
        Object jsonFieldExpected,
        Object jsonFieldActual
    ) {
        JSONArray arrayValueExpected = getJsonArrayOrEmpty(jsonFieldExpected);
        JSONArray arrayValueActual = getJsonArrayOrEmpty(jsonFieldActual);
        boolean areBothEmpty = (arrayValueExpected.length() == 0 && arrayValueActual.length() == 0);
        if (!areBothEmpty) {
            this.doCollectionJsonDiff(fullFieldLocation, parentField, diffContext, arrayValueExpected, arrayValueActual);
        }
    }
    
    
    ////// Instance Methods - Protected //////
    protected CollectionJsonDiffField getSelfOrRecursiveCollectionParent(JsonDiffField parent) {
        // default action: return self
        return this;
    }
    
    protected void fillIdResolutionMapsForCollection(
        Map<String, Map<String, JSONObject>> idResolutionMapsByIdType,
        JSONArray arrayValue
    ) {
        // default action: do nothing
    }
    
    protected abstract void doCollectionJsonDiff(
        String fullFieldLocation,
        JsonDiffField parentField,
        AbstractJsonDiff.JsonDiffContext diffContext,
        JSONArray arrayValueExpected,
        JSONArray arrayValueActual
    );
}
