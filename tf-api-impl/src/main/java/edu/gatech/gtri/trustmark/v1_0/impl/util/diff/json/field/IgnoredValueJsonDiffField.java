package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

import java.util.Comparator;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public class IgnoredValueJsonDiffField extends ValueJsonDiffField {
    
    ////// Constructor //////
    public IgnoredValueJsonDiffField(String _location) {
        super(_location, null);
    }
    
    ////// Instance Methods //////
    @Override
    protected boolean ignoreDiff() {
        return true;
    }
    
    @Override
    protected DiffSeverity getDiffSeverity(String stringValueExpected, String stringValueActual) {
        return null;
    }
    
}
