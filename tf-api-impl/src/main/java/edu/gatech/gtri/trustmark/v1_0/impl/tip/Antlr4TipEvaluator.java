package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluationException;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluator;

import java.util.Set;

/**
 * Created by brad on 5/20/16.
 */
public class Antlr4TipEvaluator implements TIPEvaluator {

    @Override
    public TIPEvaluation evaluateTIPAgainstTrustmarks(TrustInteroperabilityProfile tip, Set<Trustmark> trustmarks) throws TIPEvaluationException {

        for(AbstractTIPReference reference : tip.getReferences() ){
            if(reference.isTrustInteroperabilityProfileReference()){
                TrustInteroperabilityProfileReference tipRef = (TrustInteroperabilityProfileReference) reference;

            }
        }

        return null;
    }

    @Override
    public TIPEvaluation evaluateTIPAgainstTDRequirements(TrustInteroperabilityProfile tip, Set<TrustmarkDefinitionRequirement> tdRequirements) throws TIPEvaluationException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Boolean calculatesSatisfactionGap() {
        return false;
    }
}
