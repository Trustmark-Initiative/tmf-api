package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffType;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by brad on 4/15/16.
 */
public class AssessmentStepTDDiff extends AbstractTDDiff {

    private static final Logger log = Logger.getLogger(AssessmentStepTDDiff.class);

    @Override
    public Collection<TrustmarkDefinitionDiffResult> doDiff(TrustmarkDefinition td1, TrustmarkDefinition td2) {
        ArrayList<TrustmarkDefinitionDiffResult> results = new ArrayList<>();

        if( td1.getAssessmentSteps().size() != td2.getAssessmentSteps().size() ){
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.STEP_COUNT_MISMATCH, DiffSeverity.MAJOR,
                    "assessmentSteps", String.format("The assessment step count is different (%d vs %d).", td1.getAssessmentSteps().size(), td2.getAssessmentSteps().size()));
            result.setData("td1.assessmentSteps.size()", td1.getAssessmentSteps().size());
            result.setData("td2.assessmentSteps.size()", td2.getAssessmentSteps().size());
            results.add(result);

        }else{
            log.debug("The Assessment Step count is identical.");
        }

        log.debug("Calculating difference sets for assessment steps...");
        List<AssessmentStep> inTd1ButNotTd2 = getStepDifference(td1.getAssessmentSteps(), td2.getAssessmentSteps());
        log.debug("There are "+inTd1ButNotTd2.size()+" steps in td1 but not in td2.");
        List<AssessmentStep> inTd2ButNotTd1 = getStepDifference(td2.getAssessmentSteps(), td1.getAssessmentSteps());
        log.debug("There are "+inTd2ButNotTd1.size()+" steps in td2 but not in td1.");
        List<AssessmentStep> inBoth = getStepOverlap(td1.getAssessmentSteps(), td2.getAssessmentSteps());
        log.debug("There are "+inBoth.size()+" steps which overlap.");


        log.debug("Adding difference items for steps which don't seem to overlap...");
        // Enumerate those criteria on one side but not the other.
        addStepDiffFromOneSide(results, inTd1ButNotTd2, "td1", "td2");
        addStepDiffFromOneSide(results, inTd2ButNotTd1, "td2", "td1");



        log.debug("Calculating overlapping differences...");
        // Now sub-diff each criteria that is in both.
        for( AssessmentStep td1Step : inBoth ){
            AssessmentStep td2Step = getSimilarStep(td1Step, td2.getAssessmentSteps());

            log.debug(String.format("Comparing TD1AssessmentStep[%d: %s] to TD2AssessmentStep[%d: %s]...", td1Step.getNumber(), td1Step.getName(), td2Step.getNumber(), td2Step.getName()));

            if( !td1Step.getNumber().equals(td2Step.getNumber()) ){
                TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                        TrustmarkDefinitionDiffType.STEP_NUMBER_MISALIGNED, DiffSeverity.MINOR,
                        "td1.assessmentStep["+ td1Step.getNumber()+"]", "The assessment step numbers are not aligned.  In TD1 it is "+ td1Step.getNumber()+", but in TD2 it is "+td2Step.getNumber());
                result.setData("step1Number", td1Step.getNumber());
                result.setData("step2Number", td2Step.getNumber());
                results.add(result);
            }
            diffAssessmentStepField(results, td1Step, td2Step, td1Step.getName(), td2Step.getName(), "name", DiffSeverity.MAJOR);
            diffAssessmentStepField(results, td1Step, td2Step, td1Step.getDescription(), td2Step.getDescription(), "description", DiffSeverity.MINOR);

            // TODO What about criterion references?  Do we care?

            List<String> artifactsStep1 = artifactListToString(td1Step);
            List<String> artifactsStep2 = artifactListToString(td2Step);

            if( !setEquality(artifactsStep1, artifactsStep2) ){
                TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                        TrustmarkDefinitionDiffType.STEP_ARTIFACTS_MISMATCH, DiffSeverity.MINOR,
                        "td1.assessmentStep["+td1Step.getNumber()+"].artifacts",
                        String.format("There is a mismatch in the artifacts for assessment steps TD1[%d] and TD2[%d].", td1Step.getNumber(), td2Step.getNumber()));
                result.setData("step1Number", td1Step.getNumber());
                result.setData("step2Number", td2Step.getNumber());
                results.add(result);
            }
            
            
            // Parameters
            doParametersDiff(results, td1Step, td2Step);
        }


        return results;
    }//end doDiff()
    
    private void doParametersDiff(ArrayList<TrustmarkDefinitionDiffResult> results, AssessmentStep td1Step, AssessmentStep td2Step) {
        int stepNumber = td1Step.getNumber();
        
        if( td1Step.getParameters().size() != td2Step.getParameters().size() ){
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                TrustmarkDefinitionDiffType.STEP_PARAMETER_COUNT_MISMATCH,
                DiffSeverity.MAJOR,
                "assessmentSteps.parameters",
                String.format("The parameter count for assessment step #%d is different (%d vs %d).",
                    stepNumber,
                    td1Step.getParameters().size(),
                    td2Step.getParameters().size()
                )
            );
            result.setData("td1Step.getParameters.size()", td1Step.getParameters().size());
            result.setData("td2Step.getParameters.size()", td2Step.getParameters().size());
            results.add(result);
        
        }else{
            log.debug("The Assessment Step Parameter count is identical.");
        }
    
    
        log.debug("Calculating difference sets for assessment step parameters...");
        List<TrustmarkDefinitionParameter> inTd1ButNotTd2 = getParameterDifference(td1Step.getParameters(), td2Step.getParameters());
        log.debug("There are "+inTd1ButNotTd2.size()+" step parameters in td1 but not in td2. Step #" + stepNumber);
        List<TrustmarkDefinitionParameter> inTd2ButNotTd1 = getParameterDifference(td2Step.getParameters(), td1Step.getParameters());
        log.debug("There are "+inTd2ButNotTd1.size()+" step parameters in td2 but not in td1. Step #" + stepNumber);
        List<TrustmarkDefinitionParameter> inBoth = getParameterOverlap(td1Step.getParameters(), td2Step.getParameters());
        log.debug("There are "+inBoth.size()+" step parameters which overlap. Step #" + stepNumber);
    
    
        log.debug("Adding difference items for step parameters which don't seem to overlap...");
        // Enumerate those criteria on one side but not the other.
        addParameterDiffFromOneSide(results, inTd1ButNotTd2, stepNumber, "td1", "td2");
        addParameterDiffFromOneSide(results, inTd2ButNotTd1, stepNumber, "td2", "td1");
        
    
        for (TrustmarkDefinitionParameter td1Param : inBoth) {
            TrustmarkDefinitionParameter td2Param = getSimilarParameter(td1Param, td2Step.getParameters());
            
            String paramLocation = "td1.assessmentStep[" + stepNumber + "].parameter[" + td1Param.getName() + "]";
    
            log.debug(String.format("Comparing TD1Parameter[%s] to TD2Parameter[%s]...", td1Param.getName(), td2Param.getName()));
    
            if (td1Param.getParameterKind() != td2Param.getParameterKind()) {
                TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.STEP_PARAMETERS_KIND_MISMATCH,
                    DiffSeverity.MAJOR,
                    paramLocation + ".parameterKind",
                    "The parameters do not have the same kind.  In TD1 it is " + td1Param.getParameterKind() + ", but in TD2 it is " + td2Param.getParameterKind()
                );
                result.setData("stepNumber", stepNumber);
                result.setData("param1Name", td1Param.getName());
                result.setData("param2Name", td2Param.getName());
                results.add(result);
            }
    
            diffParameterField(results, stepNumber, td1Param, td2Param, td1Param.getName(), td2Param.getName(), "name", DiffSeverity.MAJOR);
            diffParameterField(results, stepNumber, td1Param, td2Param, td1Param.getDescription(), td2Param.getDescription(), "description", DiffSeverity.MINOR);
    
            if (!setEquality(td1Param.getEnumValues(), td2Param.getEnumValues())) {
                String locationDescription = String.format("TD1[Step#%d][Param=%s] and TD2[Step#%d][Param=%s].", stepNumber, td1Param.getName(), stepNumber, td2Param.getName());
                TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.STEP_PARAMETERS_ENUM_VALUES_MISMATCH,
                    DiffSeverity.MAJOR,
                    paramLocation + ".enumValues",
                    "There is a mismatch in the enum values for assessment step parameters " + locationDescription
                );
                result.setData("stepNumber", stepNumber);
                result.setData("param1Name", td1Param.getName());
                result.setData("param2Name", td2Param.getName());
                results.add(result);
            }
    
            if (td1Param.isRequired() != td2Param.isRequired()) {
                String locationDescription = String.format("TD1[Step#%d][Param=%s] and TD2[Step#%d][Param=%s].", stepNumber, td1Param.getName(), stepNumber, td2Param.getName());
                TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.STEP_PARAMETERS_REQUIREMENT_MISMATCH,
                    DiffSeverity.MAJOR,
                    paramLocation + ".isRequired",
                    "There is a mismatch in the requirement level for assessment step parameters " + locationDescription
                );
                result.setData("stepNumber", stepNumber);
                result.setData("param1Name", td1Param.getName());
                result.setData("param2Name", td2Param.getName());
                results.add(result);
            }
        }
    }

    private List<String> artifactListToString(AssessmentStep step){
        List<String> stringList = new ArrayList<>();
        if( step.getArtifacts() != null && !step.getArtifacts().isEmpty() ){
            for( Artifact artifact : step.getArtifacts() )
                stringList.add(artifactToString(artifact));
        }
        return stringList;
    }

    /**
     * Creates a string representing this citation.  Two equal citations should have equal strings.
     */
    private String artifactToString(Artifact artifact){
        StringBuilder builder = new StringBuilder();
        builder.append(artifact.getName().trim().toLowerCase());
        builder.append(" - ");
        if( artifact.getDescription() != null )
            builder.append(artifact.getDescription().trim().toLowerCase());
        return builder.toString();
    }
    

    /**
     * Given 2 assessment steps, and field values under them (along with which name), this method will determine what differences
     * occur in the String field values and add diff results if necessary.
     * <br/><br/>
     * @param results The collection of results to add to
     * @param td1Step The {@link AssessmentStep} from TD1
     * @param td2Step The {@link AssessmentStep} from TD2
     * @param f1 The field value from TD1
     * @param f2 The field value from TD2
     * @param fieldName the name of the field (generally a path from the criteria down)
     * @param severityIfSignificantlyDiff the severity to report if the fields are completely different.
     */
    private void diffAssessmentStepField(
        ArrayList<TrustmarkDefinitionDiffResult> results,
        AssessmentStep td1Step,
        AssessmentStep td2Step,
        String f1,
        String f2,
        String fieldName,
        DiffSeverity severityIfSignificantlyDiff
    ) {
        TrustmarkDefinitionDiffResultImpl diff = diffObjectField(
            "td1.assessmentStep[" + td1Step.getNumber() + "]",
            "Assessment Step",
            f1,
            f2,
            fieldName,
            severityIfSignificantlyDiff
        );
        if (diff != null) {
            diff.setData("step1", td1Step);
            diff.setData("step2", td2Step);
            diff.setData("name1", td1Step.getName());
            diff.setData("name2", td2Step.getName());
            results.add(diff);
        }
    }

    /**
     * Given 2 parameters, and field values under them (along with which name), this method will determine what differences
     * occur in the String field values and add diff results if necessary.
     * <br/><br/>
     * @param results The collection of results to add to
     * @param td1Param The {@link TrustmarkDefinitionParameter} from TD1
     * @param td2Param The {@link TrustmarkDefinitionParameter} from TD2
     * @param f1 The field value from TD1
     * @param f2 The field value from TD2
     * @param fieldName the name of the field (generally a path from the criteria down)
     * @param severityIfSignificantlyDiff the severity to report if the fields are completely different.
     */
    private void diffParameterField(
        ArrayList<TrustmarkDefinitionDiffResult> results,
        int stepNumber,
        TrustmarkDefinitionParameter td1Param,
        TrustmarkDefinitionParameter td2Param,
        String f1,
        String f2,
        String fieldName,
        DiffSeverity severityIfSignificantlyDiff
    ) {
        TrustmarkDefinitionDiffResultImpl diff = diffObjectField(
            "td1.assessmentStep[" + stepNumber + "].parameter[" + td1Param.getName() + "]",
            "TD Parameter",
            f1,
            f2,
            fieldName,
            severityIfSignificantlyDiff
        );
        if (diff != null) {
            diff.setData("param1", td1Param);
            diff.setData("param2", td2Param);
            diff.setData("name1", td1Param.getName());
            diff.setData("name2", td2Param.getName());
            results.add(diff);
        }
    }
    
    private TrustmarkDefinitionDiffResultImpl diffObjectField(
        String objectLocation,
        String objectTypeName,
        String f1,
        String f2,
        String fieldName,
        DiffSeverity severityIfSignificantlyDiff
    ) {
        TrustmarkDefinitionDiffResultImpl result = null;
        if (f1.equals(f2)) {
            return null; // Nothing to do here, these guys are the same.
        }
        if (f1.trim().equalsIgnoreCase(f2.trim())) {
            result = new TrustmarkDefinitionDiffResultImpl(
                TrustmarkDefinitionDiffType.TEXT_SLIGHTLY_DIFFERENT, DiffSeverity.MINOR,
                objectLocation + "." + fieldName, objectTypeName + " Fields for '" + fieldName + "' differ, but only by vase sensitivity and/or leading and trailing whitespace.");
        } else {
            int fieldDistance = loadComparator().getStringDistance(f1, f2);
            if (loadComparator().isTextSignficantlyDifferent(fieldDistance, f2, f2)) {
                result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.TEXT_COMPLETELY_DIFFERENT, severityIfSignificantlyDiff,
                    objectLocation + "." + fieldName, objectTypeName + " Fields for '" + fieldName + "' are not the same.");
            } else {
                result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.TEXT_SLIGHTLY_DIFFERENT, DiffSeverity.MINOR,
                    objectLocation + "." + fieldName, objectTypeName + " Fields for '" + fieldName + "' are slightly different.");
            }
            result.setData("distance", fieldDistance);
        }
        return result;
    }

    
    /**
     * Performs a simple function of taking a list of assessment steps and creating a diff result for each of them - indicates
     * that the steps exist in one TD but not in the other.
     */
    private void addStepDiffFromOneSide(ArrayList<TrustmarkDefinitionDiffResult> results, List<AssessmentStep> assessmentSteps, String tdFrom, String tdNotIn){
        for( AssessmentStep step : assessmentSteps ){
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.STEP_NOT_FOUND, DiffSeverity.MAJOR,
                    tdFrom+".assessmentSteps["+step.getNumber()+"]",
                    "Assessment Step #"+step.getNumber()+" entitled '"+step.getName()+"' exists in "+tdFrom+" and not in "+tdNotIn+".");
            result.setData("step", step);
            result.setData("step.number", step.getNumber().toString());
            result.setData("step.name", step.getName());
            result.setData("step.description", step.getDescription());
            result.setData("exists.in", tdFrom);
            result.setData("not.exists.in", tdNotIn);
            results.add(result);
        }
    }

    /**
     * Given an AssessmentStep and a list of them, it will find the first one in the list which is similar to the one given.
     */
    private AssessmentStep getSimilarStep(AssessmentStep stepToMatch, List<AssessmentStep> stepList ) {
        int originalDistance = Integer.MAX_VALUE;
        AssessmentStep matchingStep = null;
        for( AssessmentStep current : stepList ){
            int distance = loadComparator().getAssessmentStepSimilarity(stepToMatch, current);
            if( originalDistance > distance ){
                matchingStep = current;
                originalDistance = distance;
            }
        }
        if( matchingStep != null && loadComparator().isAssessmentStepSimilar(originalDistance, stepToMatch, matchingStep) ){
            return matchingStep;
        }else{
            // In this case, we've found either nothing or a distance greater than our threshold function will allow.
            return null;
        }
    }

    /**
     * Determines the overlapping set of all steps that exist in list 1 and also in list 2.
     */
    private List<AssessmentStep> getStepOverlap(List<AssessmentStep> step1List, List<AssessmentStep> step2List){
        List<AssessmentStep> overlap = new ArrayList<>();
        for(AssessmentStep td1Step : step1List ){
            for( AssessmentStep td2Step : step2List ){
                int distance = loadComparator().getAssessmentStepSimilarity(td1Step, td2Step);
                if( loadComparator().isAssessmentStepSimilar(distance, td1Step, td2Step) ){
                    overlap.add(td1Step);
                    break;
                }
            }
        }
        return overlap;
    }

    /**
     * Determines the underlapping set of steps that exist in list 1 that do NOT exist in list 2.
     */
    private List<AssessmentStep> getStepDifference(List<AssessmentStep> step1List, List<AssessmentStep> step2List){
        List<AssessmentStep> difference = new ArrayList<>();
        for(AssessmentStep td1Step : step1List ){
            if( getSimilarStep(td1Step, step2List) == null ){
                difference.add(td1Step);
            }
        }
        return difference;
    }
    
    
    
    
    
    /**
     * Performs a simple function of taking a list of parameters and creating a diff result for each of them - indicates
     * that the parameters exist in one TD but not in the other.
     */
    private void addParameterDiffFromOneSide(
        ArrayList<TrustmarkDefinitionDiffResult> results,
        Collection<? extends TrustmarkDefinitionParameter> paramCollection,
        int stepNumber,
        String tdFrom,
        String tdNotIn
    ) {
        for( TrustmarkDefinitionParameter param : paramCollection ){
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                TrustmarkDefinitionDiffType.STEP_PARAMETER_NOT_FOUND,
                DiffSeverity.MAJOR,
                tdFrom+".assessmentSteps["+stepNumber+"]",
                "Assessment Step #"+stepNumber+"'s parameter entitled '"+param.getName()+"' exists in "+tdFrom+" and not in "+tdNotIn+"."
            );
            result.setData("step", tdFrom+".assessmentSteps["+stepNumber+"]");
            result.setData("step.number", stepNumber);
            result.setData("step.parameter", param);
            result.setData("step.parameter.name", param.getName());
            result.setData("step.parameter.description", param.getDescription());
            result.setData("exists.in", tdFrom);
            result.setData("not.exists.in", tdNotIn);
            results.add(result);
        }
    }
    
    /**
     * Given a TD parameter and a collection of them, it will find the first one in the list which is similar to the one given.
     */
    private TrustmarkDefinitionParameter getSimilarParameter(TrustmarkDefinitionParameter paramToMatch, Collection<? extends TrustmarkDefinitionParameter> paramCollection ) {
        int originalDistance = Integer.MAX_VALUE;
        TrustmarkDefinitionParameter matchingStep = null;
        for( TrustmarkDefinitionParameter current : paramCollection ){
            int distance = loadComparator().getParametersSimilarity(paramToMatch, current);
            if( originalDistance > distance ){
                matchingStep = current;
                originalDistance = distance;
            }
        }
        if( matchingStep != null && loadComparator().isParameterSimilar(originalDistance, paramToMatch, matchingStep) ){
            return matchingStep;
        }else{
            // In this case, we've found either nothing or a distance greater than our threshold function will allow.
            return null;
        }
    }
    
    /**
     * Determines the overlappng set of all parameters that exist in list 1 and also in list 2.
     */
    private List<TrustmarkDefinitionParameter> getParameterOverlap(
        Collection<? extends TrustmarkDefinitionParameter> param1Collection,
        Collection<? extends TrustmarkDefinitionParameter> param2Collection
    ) {
        List<TrustmarkDefinitionParameter> overlap = new ArrayList<>();
        for(TrustmarkDefinitionParameter td1Param : param1Collection ){
            for( TrustmarkDefinitionParameter td2Param : param2Collection ){
                int distance = loadComparator().getParametersSimilarity(td1Param, td2Param);
                if( loadComparator().isParameterSimilar(distance, td1Param, td2Param) ){
                    overlap.add(td1Param);
                    break;
                }
            }
        }
        return overlap;
    }
    
    /**
     * Determines the underlapping set of parameters that exist in list 1 that do NOT exist in list 2.
     */
    private List<TrustmarkDefinitionParameter> getParameterDifference(
        Collection<? extends TrustmarkDefinitionParameter> param1Collection,
        Collection<? extends TrustmarkDefinitionParameter> param2Collection
    ) {
        List<TrustmarkDefinitionParameter> difference = new ArrayList<>();
        for(TrustmarkDefinitionParameter td1Param : param1Collection ){
            if( getSimilarParameter(td1Param, param2Collection) == null ){
                difference.add(td1Param);
            }
        }
        return difference;
    }


}/* End AssessmentStepTDDiff */