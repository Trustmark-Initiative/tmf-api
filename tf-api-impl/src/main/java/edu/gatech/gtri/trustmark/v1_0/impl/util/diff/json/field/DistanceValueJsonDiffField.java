package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionComparator;

import java.util.Comparator;

/**
 * Created by Nicholas Saney on 2017-05-02.
 */
public class DistanceValueJsonDiffField extends ValueJsonDiffField {
    
    ////// Constructor //////
    public DistanceValueJsonDiffField(String _location, Comparator<String> _comparator) {
        super(_location, _comparator);
    }
    
    ////// Instance Methods //////
    @Override
    protected DiffSeverity getDiffSeverity(String stringValueExpected, String stringValueActual) {
        TrustmarkDefinitionComparator comparator = FactoryLoader.getInstance(TrustmarkDefinitionComparator.class);
        int stringDistance = comparator.getStringDistance(stringValueExpected, stringValueActual);
        boolean isSignificantlyDifferent = comparator.isTextSignficantlyDifferent(
            stringDistance,
            stringValueExpected,
            stringValueActual
        );
        return isSignificantlyDifferent ? DiffSeverity.MAJOR : DiffSeverity.MINOR;
    }
}
