package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffType;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public abstract class ValueJsonDiffField extends JsonDiffField {
    
    ////// Instance Fields //////
    public final Comparator<String> comparator;
    
    ////// Constructor //////
    public ValueJsonDiffField(String _location, Comparator<String> _comparator) {
        super(_location, null, null);
        this.comparator = _comparator;
    }
    
    ////// Instance Methods - Public //////
    @Override
    public final void fillIdResolutionMaps(
        Map<String, Map<String, JSONObject>> idResolutionMapsByIdType,
        JsonDiffField parentField,
        JSONObject parentObject
    ) {
        // do nothing
    }
    
    @Override
    public void doJsonDiff(
        String fullFieldLocation,
        JsonDiffField parentField,
        AbstractJsonDiff.JsonDiffContext diffContext,
        Object jsonFieldExpected,
        Object jsonFieldActual
    ) {
        if (this.ignoreDiff()) { return; }
    
        // do diff based on nullity and then string value
        String stringValueExpected = convertJsonToStandardStringIfNotAlreadyString(jsonFieldExpected);
        String stringValueActual = convertJsonToStandardStringIfNotAlreadyString(jsonFieldActual);
        boolean areSame = JSONObject.NULL.equals(jsonFieldExpected)
                          ? JSONObject.NULL.equals(jsonFieldActual)
                          : (0 == comparator.compare(stringValueExpected, stringValueActual));
        if (!areSame) {
            // determine diff severity
            DiffSeverity severity = this.getDiffSeverity(stringValueExpected, stringValueActual);
            
            // put diff result
            diffContext.putOne(
                fullFieldLocation,
                stringValueExpected,
                stringValueActual,
                JsonDiffType.MISMATCHED,
                severity
            );
        }
    }
    
    ////// Instance Methods - Protected //////
    protected boolean ignoreDiff() {
        return false;
    }
    
    protected abstract DiffSeverity getDiffSeverity(String stringValueExpected, String stringValueActual);
    
}
