package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.AbstractDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffType;
import org.skyscreamer.jsonassert.FieldComparisonFailure;

/**
 * Created by Nicholas on 9/19/2016.
 */
public class JsonDiffResultImpl extends AbstractDiffResult implements JsonDiffResult {
    
    // Instance Properties
    private FieldComparisonFailure fieldComparisonFailure;
    public void setFieldComparisonFailure(FieldComparisonFailure comparisonFailure) {
        this.fieldComparisonFailure = comparisonFailure;
    }
    
    private JsonDiffType jsonDiffType;
    public void setJsonDiffType(JsonDiffType jsonDiffType) {
        this.jsonDiffType = jsonDiffType;
    }
    
    // Constructor
    public JsonDiffResultImpl(FieldComparisonFailure comparisonFailure, JsonDiffType jsonDiffType, DiffSeverity severity) {
        this(comparisonFailure, jsonDiffType, severity, null, null);
    }
    
    public JsonDiffResultImpl(
        FieldComparisonFailure comparisonFailure,
        JsonDiffType jsonDiffType,
        DiffSeverity severity,
        String expectedName,
        String actualName
    ) {
        if (expectedName == null) { expectedName = "expected"; }
        if (actualName == null) { actualName = "actual"; }
        this.setFieldComparisonFailure(comparisonFailure);
        this.setJsonDiffType(jsonDiffType);
        this.setSeverity(severity);
        this.setLocation(this.getJsonField());
        switch (this.getJsonDiffType()) {
            case MISMATCHED:
                this.setDescription(String.format(
                    "JSON Compare: %s difference (%s) at '%s': %s was '%s', %s was '%s'",
                    this.getSeverity(),
                    this.getJsonDiffType(),
                    this.getJsonField(),
                    expectedName,
                    this.getJsonExpected(),
                    actualName,
                    this.getJsonActual()
                ));
                break;
            case MISSING:
                this.setDescription(String.format(
                    "JSON Compare: %s difference (%s) at '%s': %s had '%s', %s had nothing",
                    this.getSeverity(),
                    this.getJsonDiffType(),
                    this.getJsonField(),
                    expectedName,
                    this.getJsonExpected(),
                    actualName
                ));
                break;
            case UNEXPECTED:
                this.setDescription(String.format(
                    "JSON Compare: %s difference (%s) at '%s': %s had nothing, %s had '%s'",
                    this.getSeverity(),
                    this.getJsonDiffType(),
                    this.getJsonField(),
                    expectedName,
                    actualName,
                    this.getJsonActual()
                ));
                break;
            case NOT_COMPARED:
                this.setDescription(String.format(
                    "JSON Compare: %s issue (field not compared) at '%s': %s had %s, %s had %s",
                    this.getSeverity(),
                    this.getJsonField(),
                    expectedName,
                    this.getJsonExpected() == null ? "nothing" : String.format("'%s'", this.getJsonExpected()),
                    actualName,
                    this.getJsonActual() == null ? "nothing" : String.format("'%s'", this.getJsonActual())
                ));
                break;
        }
    }
    
    // Instance Methods
    @Override
    public String getJsonField() {
        return this.fieldComparisonFailure == null ? null : this.fieldComparisonFailure.getField();
    }
    
    @Override
    public Object getJsonExpected() {
        return this.fieldComparisonFailure == null ? null : this.fieldComparisonFailure.getExpected();
    }
    
    @Override
    public Object getJsonActual() {
        return this.fieldComparisonFailure == null ? null : this.fieldComparisonFailure.getActual();
    }
    
    @Override
    public JsonDiffType getJsonDiffType() {
        return this.jsonDiffType;
    }
    
}
