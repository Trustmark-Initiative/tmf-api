package edu.gatech.gtri.trustmark.v1_0.impl.util.validation;

import edu.gatech.gtri.trustmark.v1_0.impl.util.ValidationResultImpl;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionValidator;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationSeverity;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Checks fields that can have duplicates for duplicated information.  For example, you shouldn't have the same keyword
 * in your TD twice.  Two assessment steps (or conformance criteria) should not have the same name or number.
 * <br/><br/>
 * @author brad
 * @date 3/14/17
 */
public class TdDuplicateCheckValidator implements TrustmarkDefinitionValidator {

    @Override
    public Collection<ValidationResult> validate(TrustmarkDefinition td) {
        ArrayList<ValidationResult> results = new ArrayList<>();

        if( td.getMetadata().getKeywords() != null && td.getMetadata().getKeywords().size() > 1 ){
            List<String> duplicates = findDuplicates(td.getMetadata().getKeywords());
            if( !duplicates.isEmpty() ){
                results.add(new ValidationResultImpl(ValidationSeverity.WARNING, "Found duplicate keywords: "+duplicates, "Metadata.Keywords"));
            }
        }

        if( td.getMetadata().getSupersededBy() != null && !td.getMetadata().getSupersededBy().isEmpty() ){
            List<String> duplicateSupersededBy = findDuplicatesFromTmis(td.getMetadata().getSupersededBy());
            if( !duplicateSupersededBy.isEmpty() ){
                results.add(new ValidationResultImpl(ValidationSeverity.WARNING, "Found duplicate Superseded By URIs: "+duplicateSupersededBy, "Metadata.SupersededBy"));
            }
        }

        if( td.getMetadata().getSupersedes() != null && !td.getMetadata().getSupersedes().isEmpty() ){
            List<String> duplicateSupersedes = findDuplicatesFromTmis(td.getMetadata().getSupersedes());
            if( !duplicateSupersedes.isEmpty() ){
                results.add(new ValidationResultImpl(ValidationSeverity.WARNING, "Found duplicate Supersedes URIs: "+duplicateSupersedes, "Metadata.Supersedes"));
            }
        }

        if( td.getMetadata().getSatisfies() != null && !td.getMetadata().getSatisfies().isEmpty() ){
            List<String> duplicateSatisfies = findDuplicatesFromTmis(td.getMetadata().getSatisfies());
            if( !duplicateSatisfies.isEmpty() ){
                results.add(new ValidationResultImpl(ValidationSeverity.WARNING, "Found duplicate Satisfies URIs: "+duplicateSatisfies, "Metadata.Satisfies"));
            }
        }


        if( td.getAssessmentSteps().size() > 1 ){
            appendDuplicateCheck(results, td.getAssessmentSteps(),
                    (step1, step2) -> ((AssessmentStep) step1).getName().trim().equalsIgnoreCase(((AssessmentStep) step2).getName().trim()),
                    (step1) -> ((AssessmentStep) step1).getName(),
                    "Assessment Steps", "Name"
                );

            appendDuplicateCheck(results, td.getAssessmentSteps(),
                    (step1, step2) -> ((AssessmentStep) step1).getNumber().equals(((AssessmentStep) step2).getNumber()),
                    (step1) -> ((AssessmentStep) step1).getNumber().toString(),
                    "Assessment Steps", "Number"
            );


            for( int stepIdx = 0; stepIdx < td.getAssessmentSteps().size(); stepIdx++ ){
                AssessmentStep step = td.getAssessmentSteps().get(stepIdx);
                if( step.getArtifacts() != null && step.getArtifacts().size() > 0 ){
                    List<Artifact> artifacts = new ArrayList<>(step.getArtifacts());
                    appendDuplicateCheck(results, artifacts,
                            (a1, a2) -> ((Artifact) a1).getName().trim().equalsIgnoreCase(((Artifact) a2).getName().trim()),
                            (a1) -> ((Artifact) a1).getName(),
                        "AssessmentStep["+stepIdx+"].Artifact", "Name");
                }
            }
        }

        if( td.getConformanceCriteria().size() > 1 ){
            appendDuplicateCheck(results, td.getConformanceCriteria(),
                    (crit1, crit2) -> ((ConformanceCriterion) crit1).getName().trim().equalsIgnoreCase(((ConformanceCriterion) crit2).getName().trim()),
                    (crit1) -> ((ConformanceCriterion) crit1).getName(),
                    "Conformance Criteria", "Name"
            );

            appendDuplicateCheck(results, td.getConformanceCriteria(),
                    (crit1, crit2) -> ((ConformanceCriterion) crit1).getNumber().equals(((ConformanceCriterion) crit2).getNumber()),
                    (crit1) -> ((ConformanceCriterion) crit1).getNumber().toString(),
                    "Conformance Criteria", "Number"
            );
        }

        List<Source> sources = new ArrayList<>(td.getSources());
        appendDuplicateCheck(results, sources,
                (s1, s2) -> ((Source) s1).getIdentifier().trim().equalsIgnoreCase(((Source) s2).getIdentifier().trim()),
                (s1) -> ((Source) s1).getIdentifier(),
                "Sources", "Identifier");

        List<Term> terms = new ArrayList<>(td.getTerms());
        appendDuplicateCheck(results, terms,
                (t1, t2) -> ((Term) t1).getName().trim().equalsIgnoreCase(((Term) t2).getName().trim()),
                (t1) -> ((Term) t1).getName(),
                "Terms", "Name");

        // TODO Should we check to see if 2 terms have conflicting abbreviations?

        ArrayList<TrustmarkDefinitionParameter> params = new ArrayList<>(td.getAllParameters());
        appendDuplicateCheck(results, params,
                (p1, p2) -> ((TrustmarkDefinitionParameter) p1).getIdentifier().trim().equalsIgnoreCase(((TrustmarkDefinitionParameter) p2).getIdentifier().trim()),
                (t1) -> ((TrustmarkDefinitionParameter) t1).getIdentifier(),
                "TrustmarkDefinitionParameters", "Identifier");
        appendDuplicateCheck(results, params,
                (p1, p2) -> ((TrustmarkDefinitionParameter) p1).getName().trim().equalsIgnoreCase(((TrustmarkDefinitionParameter) p2).getName().trim()),
                (t1) -> ((TrustmarkDefinitionParameter) t1).getName(),
                "TrustmarkDefinitionParameters", "Name");


        return results;
    }

    /**
     * This complex little piece of functional garbage will basically append an error for any duplicates
     * found on a list of objects.  The "biPredicate" parameter defines the test function that will be a conflict,
     * and the getValueFunc is used to extract the value in question that is duplicated.  Past that, you have to give
     * the name of the collection as "@things" and the name of the conflicting field as "@field" in the event that there
     * are conflicts they can be correctly identified.
     */
    public void appendDuplicateCheck(List<ValidationResult> results, List objects, BiPredicate<Object, Object> biPredicate, Function<Object, String> getValueFunc, String things, String field) {

        List<Integer> toSkip = new ArrayList<>();
        for( int i = 0; i < objects.size(); i++ ){
            if( toSkip.contains(i) )
                continue;
            Object object = objects.get(i);
            Map<Integer, Object> conflicts = findConflicts(object, objects, i, biPredicate);
            if( !conflicts.isEmpty() ){
                results.add(new ValidationResultImpl(ValidationSeverity.FATAL,
                        "Found duplicate "+things+" "+field+" ["+getValueFunc.apply(object)+"] at indexes: "+buildIndexList(i, conflicts),
                        buildLocation(things, i, conflicts, field)));
            }
        }
    }

    /**
     * Given an object (and it's index), a list of objects it's from, and a comparison function, this method will
     * determine if there are conflicts in the list with the given object and index.
     */
    public Map<Integer, Object> findConflicts(Object obj, List objects, Integer index, BiPredicate<Object, Object> predicate){
        Map<Integer, Object> conflicts = new HashMap<>();
        if( objects != null && objects.size() > 1 ){
            for( int j = 0; j < objects.size(); j++ ){
                if( j != index ){
                    Object current = objects.get(j);
                    if( predicate.test(obj, current) ){
                        conflicts.put(j, current);
                    }
                }
            }
        }
        return conflicts;
    }


    /**
     * Builds an index list from the given integer (index of a thing) and a Map of integer/things that conflict. The
     * result is a list of i + (each key) separated by commas.  Example:
     * buildIndexList(1, [(0, a), (2, b), (3, c)] => [1, 0, 3, 4]
     * <br/><br/>
     * Note that the order is pulled from the @conflictingNumberSteps' keyset, which may be randomly ordered (so no
     * guarantee on order, only formatting).
     */
    public String buildIndexList(Integer i, Map conflictingNumberSteps) {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(i).append(", ");
        if (!conflictingNumberSteps.isEmpty()){
            Set keys = conflictingNumberSteps.keySet();
            List keyList = new ArrayList(keys);
            for( int j = 0; j < keyList.size(); j++ ){
                builder.append(keyList.get(j).toString());
                if( j < (keyList.size() - 1) )
                    builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Builds a location string.  Similar to buildIndexList(), but instead uses the parentTag and Field to build something
     * like: parentTag[0].field, parentTag[1].field, ...
     */
    public String buildLocation(String parentTag, Integer startIndex, Map conflicts, String field){
        StringBuilder builder = new StringBuilder();
        builder.append(parentTag).append("[").append(startIndex).append("].").append(field).append(",");
        if (!conflicts.isEmpty()){
            Set keys = conflicts.keySet();
            List keyList = new ArrayList(keys);
            for( int j = 0; j < keyList.size(); j++ ){
                builder.append(parentTag).append("[").append(keyList.get(j).toString()).append("].").append(field);
                if( j < (keyList.size() - 1) )
                    builder.append(",");
            }
        }
        return builder.toString();
    }


    public List<String> findDuplicatesFromTmis(Collection<TrustmarkFrameworkIdentifiedObject> tmis){
        List<String> urisAsStrings = new ArrayList<>();
        for( TrustmarkFrameworkIdentifiedObject tmi : tmis ){
            urisAsStrings.add(tmi.getIdentifier().toString());
        }
        return findDuplicates(urisAsStrings);
    }

    public List<String> findDuplicates(Collection<String> keywords){
        List<String> uniques = new ArrayList<>();
        List<String> duplicates = new ArrayList<>();
        for (String k : keywords ){
            if( !uniques.contains(k.toLowerCase()) ){
                uniques.add(k.toLowerCase());
            }else{
                duplicates.add(k);
            }
        }
        return duplicates;
    }

}/* end TdDuplicateCheckValidator */