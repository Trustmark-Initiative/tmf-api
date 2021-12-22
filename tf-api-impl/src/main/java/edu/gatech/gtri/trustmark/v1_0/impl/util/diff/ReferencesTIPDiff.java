package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Compares the References and creates differences based on that.
 * <br/><br/>
 * Created by brad on 12/6/16.
 */
public class ReferencesTIPDiff extends AbstractTIPDiff {

    private static final Logger log = LoggerFactory.getLogger(ReferencesTIPDiff.class);

    @Override
    public Collection<TrustInteroperabilityProfileDiffResult> doDiff(TrustInteroperabilityProfile tip1, TrustInteroperabilityProfile tip2) {
        ArrayList<TrustInteroperabilityProfileDiffResult> results = new ArrayList<>();

        Collection<AbstractTIPReference> references1 = tip1.getReferences();
        for( AbstractTIPReference reference : references1 ){
            if( !hasReference(reference.getIdentifier().toString(), reference.isTrustmarkDefinitionRequirement(), tip2.getReferences()) ){
                log.warn("Did not find TIP1 reference["+reference.getIdentifier()+"] in TIP 2.");
                append(results, reference.getIdentifier().toString(), "", "references", TrustInteroperabilityProfileDiffType.REFERENCE_MISMATCH, DiffSeverity.MAJOR);
            }
        }

        Collection<AbstractTIPReference> references2 = tip2.getReferences();
        for( AbstractTIPReference reference : references2 ){
            if( !hasReference(reference.getIdentifier().toString(), reference.isTrustmarkDefinitionRequirement(), tip1.getReferences()) ){
                log.warn("Did not find TIP2 reference["+reference.getIdentifier()+"] in TIP 1.");
                append(results, "", reference.getIdentifier().toString(), "references", TrustInteroperabilityProfileDiffType.REFERENCE_MISMATCH, DiffSeverity.MAJOR);
            }
        }

        if( results.isEmpty() ){
            String referencesString1 = stringify(tip1.getReferences());
            String referencesString2 = stringify(tip2.getReferences());
            if( referencesString1 == null )
                referencesString1 = "";
            if( referencesString2 == null )
                referencesString2 = "";

            if( !referencesString1.equalsIgnoreCase(referencesString2) ){
                // TODO We can figure out where they don't match, in theory, and display that in the result if we wanted to.
                append(results, "<references1>", "<references2>", "references", TrustInteroperabilityProfileDiffType.FIELD_DOES_NOT_MATCH, DiffSeverity.MAJOR);
            }
        }

        return results;
    }

    protected String stringify(Collection<AbstractTIPReference> tipReferences){
        StringBuilder builder = new StringBuilder();
        if( tipReferences != null && tipReferences.size() > 0 ){
            for( AbstractTIPReference reference : tipReferences ){
                builder.append(stringify(reference));
            }
        }
        return builder.toString();
    }

    protected String stringify(AbstractTIPReference reference) {
        StringBuilder builder = new StringBuilder();
        if( reference.isTrustmarkDefinitionRequirement() ){
            builder.append("TrustmarkDefinitionRequirement").append("|");
        }else{
            builder.append("TrustInteroperabilityProfileReference").append("|");
        }

        builder.append(reference.getId()).append("|");
        stringify(builder, reference);

        if( reference.isTrustmarkDefinitionRequirement() ){
            TrustmarkDefinitionRequirement tdr = (TrustmarkDefinitionRequirement) reference;
            stringifyEntities(builder, tdr.getProviderReferences());
        }

        return builder.toString();
    }

    /**
     * Iterates the references, and returns true if the reference is in the list of references, and matches the td or tip
     * boolean.  False otherwise.
     */
    protected boolean hasReference(String identifier, Boolean tdNotTip, Collection<AbstractTIPReference> references){
        if( references != null && !references.isEmpty() ){
            for( AbstractTIPReference ref : references ){
                if( ref.getIdentifier().toString().equalsIgnoreCase(identifier) ){
                    if( tdNotTip && ref.isTrustmarkDefinitionRequirement() ){
                        return true;
                    }else if(!tdNotTip && ref.isTrustInteroperabilityProfileReference() ){
                        return true;
                    }else{
                        log.warn("WARNING - found identifier, but type has switched.  Ref is "+(tdNotTip ? "TD" : "TIP")+" but match is "+(ref.isTrustInteroperabilityProfileReference() ? "TIP" : "TD"));
                    }
                }
            }
        }
        return false;
    }


}
