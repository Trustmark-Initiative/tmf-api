package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.util.diff.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by brad on 4/15/16.
 */
public class CheckMinorMetadataIdFieldsTIPDiff extends AbstractTIPDiff {

    private static final Logger log = LoggerFactory.getLogger(CheckMinorMetadataIdFieldsTIPDiff.class);

    @Override
    public Collection<TrustInteroperabilityProfileDiffResult> doDiff(TrustInteroperabilityProfile tip1, TrustInteroperabilityProfile tip2) {
        ArrayList<TrustInteroperabilityProfileDiffResult> results = new ArrayList<>();

        checkMinorMetadataIdField(results, tip1.getDescription(), tip2.getDescription(), "description");
        checkMinorMetadataIdField(results, tip1.getLegalNotice(), tip2.getLegalNotice(), "legalNotice");
        checkMinorMetadataIdField(results, tip1.getNotes(), tip2.getNotes(), "notes");

        checkMinorMetadataIdField(results, stringify(tip1.getSupersededBy()), stringify(tip2.getSupersededBy()), "supersededBy");
        checkMinorMetadataIdField(results, stringify(tip1.getSupersedes()), stringify(tip2.getSupersedes()), "supersedes");
        checkMinorMetadataIdField(results, stringify(tip1.getSatisfies()), stringify(tip2.getSatisfies()), "satisfies");
        checkMinorMetadataIdField(results, stringify(tip1.getKeywords(), false), stringify(tip2.getKeywords(), false), "keywords");

        return results;
    }

    private String stringify(Collection<String> values, Boolean caseSensitive){
        StringBuilder builder = new StringBuilder();
        if( values != null && values.size() > 0 ){
            for( String value : values ){
                if( caseSensitive ){
                    builder.append(value).append("|");
                }else {
                    builder.append(value.toLowerCase()).append("|");
                }
            }
        }
        return builder.toString();
    }

    private String stringify(Collection<TrustmarkFrameworkIdentifiedObject> objects){
        StringBuilder builder = new StringBuilder();
        if( objects != null && objects.size() > 0 ){
            for( TrustmarkFrameworkIdentifiedObject tfio : objects ){
                builder.append(tfio.getIdentifier().toString()).append("|");
            }
        }
        return builder.toString();
    }

    private void checkMinorMetadataIdField(ArrayList<TrustInteroperabilityProfileDiffResult> results, String f1, String f2, String fieldName){
        checkStringDifference(results, f1, f2, fieldName);
    }
    private void checkStringDifference(ArrayList<TrustInteroperabilityProfileDiffResult> results, String f1, String f2, String fieldName){
        if(isEmpty(f1) && isEmpty(f2) ){
            return;

        }else if( isEmpty(f1) && isNotEmpty(f2) ){
            TrustInteroperabilityProfileDiffResultImpl result = new TrustInteroperabilityProfileDiffResultImpl(
                    TrustInteroperabilityProfileDiffType.NOT_REQUIRED_FIELD_MISSING, DiffSeverity.MINOR,
                    fieldName, "TD1 is missing field "+fieldName+", but TD2 has it.");
            result.setData("tip1."+fieldName, f1);
            result.setData("tip2."+fieldName, f2);
            results.add(result);

        }else if( isNotEmpty(f1) && isEmpty(f2) ){
            TrustInteroperabilityProfileDiffResultImpl result = new TrustInteroperabilityProfileDiffResultImpl(
                    TrustInteroperabilityProfileDiffType.NOT_REQUIRED_FIELD_MISSING, DiffSeverity.MINOR,
                    fieldName, "TD2 is missing field "+fieldName+", but TD1 has it.");
            result.setData("tip1."+fieldName, f1);
            result.setData("tip2."+fieldName, f2);
            results.add(result);

        }else{ // Ok, both f1 && f1 have a value...

            if( f1.equals(f2) )
                return;

            if( f1.trim().equalsIgnoreCase(f2.trim()) ){
                // This case is debate-able.  Probably close enough to being the same to not worry about it for all practical purposes.
                TrustInteroperabilityProfileDiffResultImpl result = new TrustInteroperabilityProfileDiffResultImpl(
                        TrustInteroperabilityProfileDiffType.TEXT_SLIGHTLY_DIFFERENT,  DiffSeverity.MINOR,
                        fieldName, "The field values are the same if trimmed and ignoring case sensitivity.");
                result.setData("tip1."+fieldName, f1);
                result.setData("tip2."+fieldName, f2);
                results.add(result);

            }else{
                // Now we know they aren't perfectly the same, so perform some distance calculation...
                int distance = loadComparator().getStringDistance(f1, f2);

                TrustInteroperabilityProfileDiffResultImpl result = null;
                if( !loadComparator().isTextSignficantlyDifferent(distance, f1, f2) ){
                    result = new TrustInteroperabilityProfileDiffResultImpl(
                            TrustInteroperabilityProfileDiffType.TEXT_SLIGHTLY_DIFFERENT,  DiffSeverity.MINOR,
                            fieldName, "The values for "+fieldName+" are different, but not completely.");

                }else{  // More than 35 percent of the string has to change, so it is likely completely different text.
                    result = new TrustInteroperabilityProfileDiffResultImpl(
                            TrustInteroperabilityProfileDiffType.TEXT_COMPLETELY_DIFFERENT, DiffSeverity.MAJOR,
                            fieldName, "The values for "+fieldName+" are significantly different.");

                }
                result.setData("tip1."+fieldName, f1);
                result.setData("tip2."+fieldName, f2);
                result.setData("levenshtein", distance);
                results.add(result);
            }
        }
    }//end checkStringDifference()





}
