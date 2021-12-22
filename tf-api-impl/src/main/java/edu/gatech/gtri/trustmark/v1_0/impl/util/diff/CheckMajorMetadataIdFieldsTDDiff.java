package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by brad on 4/15/16.
 */
public class CheckMajorMetadataIdFieldsTDDiff extends AbstractTDDiff {

    private static final Logger log = LoggerFactory.getLogger(CheckMajorMetadataIdFieldsTDDiff.class);

    @Override
    public Collection<TrustmarkDefinitionDiffResult> doDiff(TrustmarkDefinition td1, TrustmarkDefinition td2) {
        ArrayList<TrustmarkDefinitionDiffResult> results = new ArrayList<>();
        checkMajorMetadataIdField(results, td1.getMetadata().getIdentifier().toString(), td2.getMetadata().getIdentifier().toString(), "identifier");
        checkMajorMetadataIdField(results, td1.getMetadata().getName(), td2.getMetadata().getName(), "name");
        checkMajorMetadataIdField(results, td1.getMetadata().getVersion(), td2.getMetadata().getVersion(), "version");
        return results;
    }


    private void checkMajorMetadataIdField( ArrayList<TrustmarkDefinitionDiffResult> results, String f1, String f2, String idFieldName){
        if( !f1.equals(f2) ){ // TODO Equals ignore case maybe?
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.MAJOR_METADATA_ID_FIELD_DOES_NOT_MATCH, DiffSeverity.MAJOR,
                    "metadata."+idFieldName, "The "+idFieldName+"["+f1+"] does not match the corresponding one["+f2+"]");
            result.setData("td1.metadata."+idFieldName, f1);
            result.setData("td2.metadata."+idFieldName, f2);
            results.add(result);
        }else {
            log.debug(idFieldName+"s are equal");
        }
    }



}
