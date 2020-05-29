package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.RootNodeJsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffResultCollection;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffType;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.FieldComparisonFailure;

import java.util.*;

/**
 * Performs a JSON-based Diff using a custom specification.
 */
public abstract class AbstractJsonDiff<T> implements JsonDiff<T> {
    
    
    ////// Instance Methods - Abstract //////
    
    /**
     * Returns a root node with a hierarchy of fields to compare, and the default severity of a not-compared field.
     *
     * In order for this diff engine to report a difference, its location must be described the returned node's
     * hierarchy. By the same token, if a field is not included in this list, then no difference will be reported for it,
     * even if the field is different in the JSON for the expected vs the actual compared entities.
     */
    public abstract RootNodeJsonDiffField getRootNodeDiffField();
    
    
    ////// Instance Methods - Concrete //////
    
    @Override
    public JsonDiffResultCollection doDiff(String expectedName, T expected, String actualName, T actual) {
        JSONObject jsonExpected = this.getAsJson(expected);
        JSONObject jsonActual = this.getAsJson(actual);
    
        RootNodeJsonDiffField rootNodeJsonDiffField = this.getRootNodeDiffField();
        return doJsonDiff(expectedName, jsonExpected, actualName, jsonActual, rootNodeJsonDiffField);
    }
    
    protected final JSONObject getAsJson(T value) {
        JsonManager manager = FactoryLoader.getInstance(JsonManager.class);
        JsonProducer producer = manager.findProducer(this.getSupportedType());
        return (JSONObject)producer.serialize(value);
    }
    
    
    ////// Static Methods //////
    
    public static JsonDiffResultCollection doJsonDiff(
        String expectedName,
        JSONObject jsonExpected,
        String actualName,
        JSONObject jsonActual,
        RootNodeJsonDiffField rootNodeJsonDiffField
    ) {
        JsonDiffContext diffContext = new JsonDiffContext(expectedName, actualName);
        // first pass (build ID maps)
        rootNodeJsonDiffField.fillIdResolutionMapsForChildren(diffContext.expectedIdResolutionMapsByIdType, jsonExpected);
        rootNodeJsonDiffField.fillIdResolutionMapsForChildren(diffContext.actualIdResolutionMapsByIdType, jsonActual);
        // second pass (find differences)
        rootNodeJsonDiffField.doJsonDiffForChildren(null, diffContext, jsonExpected, jsonActual);
        return diffContext.resultCollection;
    }
    
    
    ////// Static Inner Classes //////
    
    public static class JsonDiffException extends RuntimeException {
        public JsonDiffException(String message) { super(message); }
        public JsonDiffException(String message, Throwable cause) { super(message, cause); }
    }
    
    public static class JsonDiffContext {
        //// Instance Properties ////
        public final JsonDiffResultCollectionImpl resultCollection = new JsonDiffResultCollectionImpl();
        public final Stack<Map.Entry<String, String>> idTypeParentIdStack = new Stack<>();
        public final Map<String, Map<String, JSONObject>> expectedIdResolutionMapsByIdType = new HashMap<>();
        public final Map<String, Map<String, JSONObject>> actualIdResolutionMapsByIdType = new HashMap<>();
        public final String expectedName;
        public final String actualName;
        
        //// Constructor ////
        public JsonDiffContext(String _expectedName, String _actualName) {
            this.expectedName = _expectedName;
            this.actualName = _actualName;
        }
        
        //// Instance Methods ////
        public void putOne(String fullFieldLocation, Object expected, Object actual, JsonDiffType diffType, DiffSeverity severity) {
            FieldComparisonFailure comparisonFailure = new FieldComparisonFailure(fullFieldLocation, expected, actual);
            JsonDiffResultImpl result = new JsonDiffResultImpl(
                comparisonFailure,
                diffType,
                severity,
                this.expectedName,
                this.actualName
            );
            this.resultCollection.put(result);
        }
        
        public void putAllMissing(String fullFieldLocation, Collection<?> setMissing, DiffSeverity severity) {
            for (Object expected : setMissing) {
                this.putOne(fullFieldLocation, expected, null, JsonDiffType.MISSING, severity);
            }
        }
    
        public void putAllUnexpected(String fullFieldLocation, Collection<?> setUnexpected, DiffSeverity severity) {
            for (Object actual : setUnexpected) {
                this.putOne(fullFieldLocation, null, actual, JsonDiffType.UNEXPECTED, severity);
            }
        }
    }
    
}
