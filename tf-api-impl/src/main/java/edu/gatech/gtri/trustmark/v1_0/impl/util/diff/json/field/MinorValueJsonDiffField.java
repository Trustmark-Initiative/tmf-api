package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

import java.util.Comparator;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public class MinorValueJsonDiffField extends ValueJsonDiffField {
    
    ////// Constructor //////
    public MinorValueJsonDiffField(String _location, Comparator<String> _comparator) {
        super(_location, _comparator);
    }
    
    ////// Instance Methods //////
    @Override
    protected DiffSeverity getDiffSeverity(String stringValueExpected, String stringValueActual) {
        return DiffSeverity.MINOR;
    }
    
}
