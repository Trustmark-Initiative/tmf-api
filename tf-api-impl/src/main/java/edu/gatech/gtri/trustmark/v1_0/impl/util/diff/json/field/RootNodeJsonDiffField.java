package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public class RootNodeJsonDiffField extends JsonDiffField {
    
    ////// Constructors //////
    public RootNodeJsonDiffField(DiffSeverity _severityForNotComparedFields, List<JsonDiffField> _children) {
        super(null, _severityForNotComparedFields, _children);
    }
    
    ////// Instance Methods //////
    @Override
    public void fillIdResolutionMaps(
        Map<String, Map<String, JSONObject>> idResolutionMapsByIdType,
        JsonDiffField parentField,
        JSONObject parentObject
    ) {
        // TODO
    }
    
    @Override
    public void doJsonDiff(
        String fullFieldLocation,
        JsonDiffField parentField,
        AbstractJsonDiff.JsonDiffContext diffContext,
        Object jsonFieldExpected,
        Object jsonFieldActual
    ) {
        // TODO
    }
}
