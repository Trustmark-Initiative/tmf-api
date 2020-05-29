package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffType;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by brad on 4/15/16.
 */
public class ConformanceCritTDDiff extends AbstractTDDiff {

    private static final Logger log = Logger.getLogger(ConformanceCritTDDiff.class);

    @Override
    public Collection<TrustmarkDefinitionDiffResult> doDiff(TrustmarkDefinition td1, TrustmarkDefinition td2) {
        ArrayList<TrustmarkDefinitionDiffResult> results = new ArrayList<>();

        if( td1.getConformanceCriteria().size() != td2.getConformanceCriteria().size() ){
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.CRITERIA_COUNT_MISMATCH, DiffSeverity.MAJOR,
                    "conformanceCriteria", String.format("The conformance criteria count is different (%d vs %d).", td1.getConformanceCriteria().size(), td2.getConformanceCriteria().size()));
            result.setData("td1.conformanceCriteria.size()", td1.getConformanceCriteria().size());
            result.setData("td2.conformanceCriteria.size()", td2.getConformanceCriteria().size());
            results.add(result);

        }else{
            log.debug("The ConformanceCriteria count is identical.");
        }

        log.debug("Calculating difference sets for criteria...");
        Set<ConformanceCriterion> inTd1ButNotTd2 = getCriteriaDifference(td1.getConformanceCriteria(), td2.getConformanceCriteria());
        log.debug("There are "+inTd1ButNotTd2.size()+" criteria in td1 but not in td2.");
        Set<ConformanceCriterion> inTd2ButNotTd1 = getCriteriaDifference(td2.getConformanceCriteria(), td1.getConformanceCriteria());
        log.debug("There are "+inTd2ButNotTd1.size()+" criteria in td2 but not in td1.");
        Set<ConformanceCriterion> inBoth = getCriteriaOverlap(td1.getConformanceCriteria(), td2.getConformanceCriteria());
        log.debug("There are "+inBoth.size()+" criteria which overlap.");

        if( inBoth.size() == 0 ){
            log.info("Since there are no criteria which overlap, we prematurely end criteria diff calculations and throw a Completely Different message.");
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.CRITERIA_COMPLETELY_DIFFERENT, DiffSeverity.MAJOR,
                    "conformanceCriteria", "The conformance criteria between TD1 and TD2 have no recognized similarities at all.");
            results.add(result);
            return results;
        }

        log.debug("Adding criteria which don't seem to overlap...");
        // Enumerate those criteria on one side but not the other.
        addCritFromOneSide(results, inTd1ButNotTd2, "td1", "td2");
        addCritFromOneSide(results, inTd2ButNotTd1, "td2", "td1");

        log.debug("Calculating overlapping differences...");
        // Now sub-diff each criteria that is in both.
        for( ConformanceCriterion td1Crit : inBoth ){
            ConformanceCriterion td2Crit = getSimilarCriteria(td1Crit, td2.getConformanceCriteria());

            log.debug(String.format("Comparing TD1Criteria[%d: %s] to TD2Criteria[%d: %s]...", td1Crit.getNumber(), td1Crit.getName(), td2Crit.getNumber(), td2Crit.getName()));

            if( !td1Crit.getNumber().equals(td2Crit.getNumber()) ){
                TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                        TrustmarkDefinitionDiffType.CRITERIA_NUMBER_MISALIGNED, DiffSeverity.MINOR,
                        "td1.conformanceCriteria["+td1Crit.getNumber()+"]", "The criteria numbers are not aligned.  In TD1 it is "+td1Crit.getNumber()+", but in TD2 it is "+td2Crit.getNumber());
                result.setData("crit1Number", td1Crit.getNumber());
                result.setData("crit2Number", td2Crit.getNumber());
                results.add(result);
            }
            diffCriteriaField(results, td1Crit, td2Crit, td1Crit.getName(), td2Crit.getName(), "name", DiffSeverity.MAJOR);
            diffCriteriaField(results, td1Crit, td2Crit, td1Crit.getDescription(), td2Crit.getDescription(), "description", DiffSeverity.MINOR);

            List<String> citationSet1 = citationsSet(td1Crit);
            List<String> citationSet2 = citationsSet(td2Crit);

            if( !setEquality(citationSet1, citationSet2) ){
                TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                        TrustmarkDefinitionDiffType.CRITERIA_CITATIONS_MISMATCH, DiffSeverity.MINOR,
                        "td1.conformanceCriteria["+td1Crit.getNumber()+"].citations",
                        "There is a mismatch in the citations for the criteria.");
                result.setData("crit1Number", td1Crit.getNumber());
                result.setData("crit2Number", td2Crit.getNumber());
                results.add(result);
            }

        }

        return results;
    }

    /**
     * Returns a list of strings from list1 which are NOT found in list2, based on the "similarity".
     */
    private List<String> getUnderlap(List<String> list1, List<String> list2 ){
        List<String> different = new ArrayList<>();
        different.addAll(list1);
        for( String cur : list1 ){
            boolean found = false;
            for( String cur2 : list2 ){
                int distance = loadComparator().getStringDistance(cur, cur2);
                if( !loadComparator().isTextSignficantlyDifferent(distance, cur, cur2) ){
                    different.remove(cur);
                    found = true;
                    break;
                }
            }
            if( found )
                break;
        }
        return different;
    }

    /**
     * Returns a list of strings from list1 which are found in list2, based on the "similarity".
     */
    private List<String> getOverlap(List<String> list1, List<String> list2 ){
        List<String> similar = new ArrayList<>();
        for( String cur : list1 ){
            boolean found = false;
            for( String cur2 : list2 ){
                int distance = loadComparator().getStringDistance(cur, cur2);
                if( !loadComparator().isTextSignficantlyDifferent(distance, cur, cur2) ){
                    similar.add(cur);
                    found = true;
                    break;
                }
            }
            if( found )
                break;
        }
        return similar;
    }

    private List<String> citationsSet(ConformanceCriterion crit){
        List<String> citations = new ArrayList<>();
        if( crit.getCitations() != null && !crit.getCitations().isEmpty() ){
            for( Citation citation : crit.getCitations() )
                citations.add(citationToString(citation));
        }
        return citations;
    }

    /**
     * Creates a string representing this citation.  Two equal citations should have equal strings.
     */
    private String citationToString(Citation citation){
        StringBuilder builder = new StringBuilder();
        builder.append(citation.getSource().getIdentifier().trim().toLowerCase());
        builder.append(" - ");
        if( citation.getDescription() != null )
            builder.append(citation.getDescription().trim().toLowerCase());
        return builder.toString();
    }


    /**
     * Given 2 criteria, and field values under them (along with which name), this method will determine what differences
     * occur in the String field values and add diff results if necessary.
     * <br/><br/>
     * @param results The collection of results to add to
     * @param td1Crit The {@link ConformanceCriterion} from TD1
     * @param td2Crit The {@link ConformanceCriterion} from TD2
     * @param f1 The field value from TD1
     * @param f2 The field value from TD2
     * @param fieldName the name of the field (generally a path from the criteria down)
     * @param severityIfSignificantlyDiff the severity to report if the fields are completely different.
     */
    private void diffCriteriaField(ArrayList<TrustmarkDefinitionDiffResult> results, ConformanceCriterion td1Crit, ConformanceCriterion td2Crit, String f1, String f2, String fieldName, DiffSeverity severityIfSignificantlyDiff){
        TrustmarkDefinitionDiffResultImpl result = null;
        if( f1.equals(f2) ){
            return; // Nothing to do here, these guys are the same.
        }
        if( f1.trim().equalsIgnoreCase(f2.trim()) ){
            result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.TEXT_SLIGHTLY_DIFFERENT, DiffSeverity.MINOR,
                    "td1.conformanceCriteria["+td1Crit.getNumber()+"]."+fieldName, "Criteria Fields for '"+fieldName+"' differ, but only by vase sensitivity and/or leading and trailing whitespace.");
        }else{
            int fieldDistance = loadComparator().getStringDistance(f1, f2);
            if( loadComparator().isTextSignficantlyDifferent(fieldDistance, f1, f2 ) ) {
                result = new TrustmarkDefinitionDiffResultImpl(
                        TrustmarkDefinitionDiffType.TEXT_COMPLETELY_DIFFERENT, severityIfSignificantlyDiff,
                        "td1.conformanceCriteria["+td1Crit.getNumber()+"]."+fieldName, "Criteria Fields for '"+fieldName+"' are not the same.");
            }else{
                result = new TrustmarkDefinitionDiffResultImpl(
                        TrustmarkDefinitionDiffType.TEXT_SLIGHTLY_DIFFERENT, DiffSeverity.MINOR,
                        "td1.conformanceCriteria["+td1Crit.getNumber()+"]."+fieldName, "Criteria Fields for '"+fieldName+"' are slightly different.");
            }
            result.setData("distance", fieldDistance);
        }
        if( result != null ) {
            result.setData("criterion1", td1Crit);
            result.setData("criterion2", td2Crit);
            result.setData("name1", td1Crit.getName());
            result.setData("name2", td2Crit.getName());
            results.add(result);
        }
    }


    /**
     * Performs a simple function of taking a list of criteria and creating a diff result for each of them - indicates
     * that the criteria exists in one TD but not in the other.
     */
    private void addCritFromOneSide(ArrayList<TrustmarkDefinitionDiffResult> results, Set<ConformanceCriterion> criteria, String tdFrom, String tdNotIn){
        for( ConformanceCriterion crit : criteria ){
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.CRITERIA_NOT_FOUND, DiffSeverity.MAJOR,
                    tdFrom+".conformanceCriteria["+crit.getNumber()+"]",
                    "Conformance criterion #"+crit.getNumber()+" entitled '"+crit.getName()+"' exists in "+tdFrom+" and not in "+tdNotIn+".");
            result.setData("criterion", crit);
            result.setData("criterion.number", crit.getNumber().toString());
            result.setData("criterion.name", crit.getName());
            result.setData("criterion.description", crit.getDescription());
            result.setData("exists.in", tdFrom);
            result.setData("not.exists.in", tdNotIn);
            results.add(result);
        }
    }


    /**
     * Given a criterion and a list of them, it will find the first one in the list which is similar to the one given to
     * match.
     */
    private ConformanceCriterion getSimilarCriteria( ConformanceCriterion critToMatch, List<ConformanceCriterion> criteriaList ) {
        int originalDistance = Integer.MAX_VALUE;
        ConformanceCriterion matchingCrit = null;
        for( ConformanceCriterion current : criteriaList ){
            int distance = loadComparator().getCriteriaSimilarity(critToMatch, current);
            if( originalDistance > distance ){
                matchingCrit = current;
                originalDistance = distance;
            }
        }
        if( matchingCrit != null && loadComparator().isCriteriaSimilar(originalDistance, critToMatch, matchingCrit) ){
            return matchingCrit;
        }else{
            // In this case, we've found either nothing or a distance greater than our threshold function will allow.
            return null;
        }
    }

    /**
     * Determines the overlapping set of all criteria that exist in list 1 and also in list 2.
     */
    private Set<ConformanceCriterion> getCriteriaOverlap(List<ConformanceCriterion> criteria1List, List<ConformanceCriterion> criteria2List){
        Set<ConformanceCriterion> overlap = new HashSet<>();
        for(ConformanceCriterion td1Crit : criteria1List ){
            for( ConformanceCriterion td2Crit : criteria2List ){
                int distance = loadComparator().getCriteriaSimilarity(td1Crit, td2Crit);
                if( loadComparator().isCriteriaSimilar(distance, td1Crit, td2Crit) ){
                    overlap.add(td1Crit);
                    break;
                }
            }
        }
        return overlap;
    }

    /**
     * Determines the underlapping set of criteria that exist in list 1 that do NOT exist in list 2.
     */
    private Set<ConformanceCriterion> getCriteriaDifference(List<ConformanceCriterion> criteria1List, List<ConformanceCriterion> criteria2List){
        Set<ConformanceCriterion> difference = new HashSet<>();
        for(ConformanceCriterion td1Crit : criteria1List ){
            if( getSimilarCriteria(td1Crit, criteria2List) == null ){
                difference.add(td1Crit);
            }
        }
        return difference;
    }


}/* End ConformanceCritTDDiff */