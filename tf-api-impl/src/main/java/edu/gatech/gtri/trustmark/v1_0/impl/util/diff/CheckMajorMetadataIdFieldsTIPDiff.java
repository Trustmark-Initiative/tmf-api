package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.util.diff.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by brad on 12/6/16.
 */
public class CheckMajorMetadataIdFieldsTIPDiff extends AbstractTIPDiff {

    private static final Logger log = LoggerFactory.getLogger(CheckMajorMetadataIdFieldsTIPDiff.class);

    @Override
    public Collection<TrustInteroperabilityProfileDiffResult> doDiff(TrustInteroperabilityProfile tip1, TrustInteroperabilityProfile tip2) {
        ArrayList<TrustInteroperabilityProfileDiffResult> results = new ArrayList<>();
        checkMajorMetadataIdField(results, tip1.getIdentifier().toString(), tip2.getIdentifier().toString(), "identifier");
        checkMajorMetadataIdField(results, tip1.getName(), tip2.getName(), "name");
        checkMajorMetadataIdField(results, tip1.getVersion(), tip2.getVersion(), "version");
        checkMajorMetadataIdField(results, tip1.getPublicationDateTime().toString(), tip2.getPublicationDateTime().toString(), "publicationDateTime");
        checkMajorMetadataIdField(results, tip1.isDeprecated().toString(), tip2.isDeprecated().toString(), "deprecated");
        checkMajorMetadataIdField(results, tip1.getTrustExpression(), tip2.getTrustExpression(), "trustExpression");
        return results;
    }


    private void checkMajorMetadataIdField( ArrayList<TrustInteroperabilityProfileDiffResult> results, String f1, String f2, String idFieldName){
        if( !f1.equals(f2) ){ // TODO Equals ignore case maybe?
            TrustInteroperabilityProfileDiffResultImpl result = new TrustInteroperabilityProfileDiffResultImpl(
                    TrustInteroperabilityProfileDiffType.FIELD_DOES_NOT_MATCH, DiffSeverity.MAJOR,
                    idFieldName, "The "+idFieldName+"["+f1+"] does not match the corresponding one["+f2+"]");
            result.setData("tip1."+idFieldName, f1);
            result.setData("tip2."+idFieldName, f2);
            results.add(result);
        }else {
            log.debug(idFieldName+"s are equal");
        }
    }



}
