package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import org.apache.commons.collections4.SetUtils;
import org.json.JSONArray;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public class SimpleCollectionJsonDiffField extends CollectionJsonDiffField {
    
    ////// Instance Fields //////
    public final DiffSeverity missingOrUnexpectedSeverity;
    public final Comparator<String> comparator;
    
    ////// Constructor //////
    protected SimpleCollectionJsonDiffField(
        String _location,
        DiffSeverity _missingOrUnexpectedSeverity,
        Comparator<String> _comparator
    ) {
        super(_location, null, null);
        this.missingOrUnexpectedSeverity = _missingOrUnexpectedSeverity;
        this.comparator = _comparator;
    }
    
    ////// Instance Methods //////
    @Override
    protected void doCollectionJsonDiff(
        String fullFieldLocation,
        JsonDiffField parentField,
        AbstractJsonDiff.JsonDiffContext diffContext,
        JSONArray arrayValueExpected,
        JSONArray arrayValueActual
    ) {
        // expected
        Set<String> setExpected = new TreeSet<>(this.comparator);
        addAllStandardStrings(setExpected, arrayValueExpected);
        // actual
        Set<String> setActual = new TreeSet<>(this.comparator);
        addAllStandardStrings(setActual, arrayValueActual);
        // missing
        Set<String> setMissing = SetUtils.difference(setExpected, setActual);
        diffContext.putAllMissing(fullFieldLocation, setMissing, this.missingOrUnexpectedSeverity);
        // unexpected
        Set<String> setUnexpected = SetUtils.difference(setActual, setExpected);
        diffContext.putAllUnexpected(fullFieldLocation, setUnexpected, this.missingOrUnexpectedSeverity);
    }
    
    
    ////// Static Methods - Helpers //////
    protected static void addAllStandardStrings(Set<String> set, JSONArray jsonArray) {
        boolean isAllStrings = StreamSupport.stream(jsonArray.spliterator(), true).allMatch(o -> o instanceof String);
        for (Object o : jsonArray) {
            String standardString = isAllStrings ? (String)o : convertJsonToStandardString(o);
            set.add(standardString);
        }
    }
}
