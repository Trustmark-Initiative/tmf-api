package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionComparator;
import name.fraser.neil.plaintext.DiffMatchPatch;

/**
 * Created by brad on 4/14/16.
 */
public class TrustmarkDefinitionComparatorImpl implements TrustmarkDefinitionComparator {

    @Override
    public int getCriteriaSimilarity(ConformanceCriterion crit1, ConformanceCriterion crit2) {
        int distance = getStringDistance(crit1.getName(), crit2.getName());
        int avgLen = (int) Math.floor((crit1.getName().length() + crit2.getName().length()) / 2.0d);
        int percent = (int) Math.floor(((double) distance) / ((double) avgLen) * 100.0d);
        return percent;
    }

    @Override
    public int getAssessmentStepSimilarity(AssessmentStep step1, AssessmentStep step2) {
        int distance = getStringDistance(step1.getName(), step2.getName());
        int avgLen = (int) Math.floor((step1.getName().length() + step2.getName().length()) / 2.0d);
        int percent = (int) Math.floor(((double) distance) / ((double) avgLen) * 100.0d);
        return percent;
    }
    
    @Override
    public int getParametersSimilarity(TrustmarkDefinitionParameter param1, TrustmarkDefinitionParameter param2) {
        int distance = getStringDistance(param1.getName(), param2.getName());
        int avgLen = (int) Math.floor((param1.getName().length() + param1.getName().length()) / 2.0d);
        int percent = (int) Math.floor(((double) distance) / ((double) avgLen) * 100.0d);
        return percent;
    }

    @Override
    public boolean isCriteriaSimilar(int distance, ConformanceCriterion crit1, ConformanceCriterion crit2) {
        return distance < 20;
    }

    @Override
    public boolean isAssessmentStepSimilar(int distance, AssessmentStep step1, AssessmentStep step2) {
        return distance < 20;
    }
    
    @Override
    public boolean isParameterSimilar(int distance, TrustmarkDefinitionParameter param1, TrustmarkDefinitionParameter param2) {
        return distance < 20;
    }
    
    
    @Override
    public int getStringDistance(String f1, String f2) {
        DiffMatchPatch dmp = new DiffMatchPatch();
        int distance = dmp.diff_levenshtein(dmp.diff_main(f1, f2));
        return distance;
    }

    /**
     * Levenshetin has some simple bounds that might be useful for answering the "signficantly different" question:
     *    1) distance <= Max(f1.length(), f2.length())
     *    2) distance >= abs(f1.length() - f2.length())
     *
     *
     * The algorithm here is pretty simple, we simply get the average length between f1 & f2, and then we get the percentage
     *   of how much we have to change from that "average length".  Yes, the number may be > 100%, and that's ok.
     *   For our purposes, we determine it to be "mostly" the same if it's less than 35% different on the average string.
     *   I'm not sure that 35% is the right number, but is ok for now.
     */
    @Override
    public boolean isTextSignficantlyDifferent(int distance, String f1, String f2){
        int avgLen = (int) Math.floor((f1.length() + f2.length()) / 2.0d);
        int percent = (int) Math.floor(((double) distance) / ((double) avgLen) * 100.0d);
        return percent > 35; // NOTE this metric is completely made up.  35% may not be what we want here.
    }
}
