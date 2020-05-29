package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffResultCollection;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffType;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.FieldComparisonFailure;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nicholas on 01/26/2017.
 */
public class JSONObjectJsonDiffImpl implements JsonDiff<JSONObject> {
    @Override
    public Class<? extends JSONObject> getSupportedType() { return JSONObject.class; }
    
    @Override
    public JsonDiffResultCollection doDiff(String expectedName, JSONObject expected, String actualName, JSONObject actual) {
        JSONCompareResult jsonCompareResult = JSONCompare.compareJSON(expected, actual, JSONCompareMode.NON_EXTENSIBLE);
        JsonDiffResultCollectionImpl resultCollection = new JsonDiffResultCollectionImpl();
        resultCollection.putAll(this.getDiffResults(JsonDiffType.MISMATCHED, jsonCompareResult.getFieldFailures(), expectedName, actualName));
        resultCollection.putAll(this.getDiffResults(JsonDiffType.MISSING, jsonCompareResult.getFieldMissing(), expectedName, actualName));
        resultCollection.putAll(this.getDiffResults(JsonDiffType.UNEXPECTED, jsonCompareResult.getFieldUnexpected(), expectedName, actualName));
        return resultCollection;
    }
    
    protected List<JsonDiffResult> getDiffResults(
        JsonDiffType diffType,
        List<FieldComparisonFailure> failedFields,
        String expectedName,
        String actualName
    ) {
        List<JsonDiffResult> results = new ArrayList<>();
        for (FieldComparisonFailure failedField : failedFields) {
            JsonDiffResultImpl defaultResult = new JsonDiffResultImpl(failedField, diffType, DiffSeverity.MAJOR, expectedName, actualName);
            results.add(defaultResult);
        }
        return results;
    }
}

/*
 */
