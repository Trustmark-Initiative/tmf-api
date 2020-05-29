package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import org.json.JSONArray;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public class RecursiveCollectionJsonDiffField extends CollectionJsonDiffField {
    
    ////// Constructor //////
    public RecursiveCollectionJsonDiffField(String _location) {
        super(_location, null, null);
    }
    
    ////// Instance Methods //////
    @Override
    public CollectionJsonDiffField getSelfOrRecursiveCollectionParent(JsonDiffField parent) {
        return (CollectionJsonDiffField)parent;
    }
    
    @Override
    protected void doCollectionJsonDiff(
        String fullFieldLocation,
        JsonDiffField parentField,
        AbstractJsonDiff.JsonDiffContext diffContext,
        JSONArray arrayValueExpected,
        JSONArray arrayValueActual
    ) {
        CollectionJsonDiffField nextDiffField = this.getSelfOrRecursiveCollectionParent(parentField);
        nextDiffField.doCollectionJsonDiff(fullFieldLocation, parentField, diffContext, arrayValueExpected, arrayValueActual);
    }
}
