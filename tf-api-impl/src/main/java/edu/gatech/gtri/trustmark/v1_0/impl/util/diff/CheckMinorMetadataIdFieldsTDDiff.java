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
public class CheckMinorMetadataIdFieldsTDDiff extends AbstractTDDiff {

    private static final Logger log = LoggerFactory.getLogger(CheckMinorMetadataIdFieldsTDDiff.class);

    @Override
    public Collection<TrustmarkDefinitionDiffResult> doDiff(TrustmarkDefinition td1, TrustmarkDefinition td2) {
        ArrayList<TrustmarkDefinitionDiffResult> results = new ArrayList<>();

        checkMinorMetadataIdFields(results, td1.getMetadata(), td2.getMetadata());

        checkStringDifference(results, td1.getConformanceCriteriaPreface(), td2.getConformanceCriteriaPreface(), "conformanceCriteriaPreface");
        checkStringDifference(results, td1.getAssessmentStepPreface(), td2.getAssessmentStepPreface(), "assessmentStepPreface");
        checkStringDifference(results, td1.getIssuanceCriteria(), td2.getIssuanceCriteria(), "issuanceCriteria");

        return results;
    }


    private void checkMinorMetadataIdFields(ArrayList<TrustmarkDefinitionDiffResult> results, TrustmarkDefinition.Metadata m1, TrustmarkDefinition.Metadata m2){
        checkMinorMetadataIdField(results, m1.getAssessorQualificationsDescription(), m2.getAssessorQualificationsDescription(), "assessorQualificationsDescription");
        checkMinorMetadataIdField(results, m1.getDescription(), m2.getDescription(), "description");
        checkMinorMetadataIdField(results, m1.getExtensionDescription(), m2.getExtensionDescription(), "extensionDescription");
        checkMinorMetadataIdField(results, m1.getLegalNotice(), m2.getLegalNotice(), "legalNotice");
        checkMinorMetadataIdField(results, m1.getNotes(), m2.getNotes(), "notes");
        checkMinorMetadataIdField(results, m1.getProviderEligibilityCriteria(), m2.getProviderEligibilityCriteria(), "providerEligibilityCriteria");
        checkMinorMetadataIdField(results, m1.getTargetProviderDescription(), m2.getTargetProviderDescription(), "targetProviderDescription");
        checkMinorMetadataIdField(results, m1.getTargetRecipientDescription(), m2.getTargetRecipientDescription(), "targetRecipientDescription");
        checkMinorMetadataIdField(results, m1.getTargetRelyingPartyDescription(), m2.getTargetRelyingPartyDescription(), "targetRelyingPartyDescription");
        checkMinorMetadataIdField(results, m1.getTargetStakeholderDescription(), m2.getTargetStakeholderDescription(), "targetStakeholderDescription");
//        checkMinorMetadataIdField(results, m1.getTrustmarkReferenceAttributeName().toString(), m2.getTrustmarkReferenceAttributeName().toString(), "trustmarkReferenceAttributeName");
    }//end checkMinorMetadataIdFields()

    private void checkMinorMetadataIdField(ArrayList<TrustmarkDefinitionDiffResult> results, String f1, String f2, String fieldName){
        checkStringDifference(results, f1, f2, "metadata."+fieldName);
    }
    private void checkStringDifference(ArrayList<TrustmarkDefinitionDiffResult> results, String f1, String f2, String fieldName){
        if(isEmpty(f1) && isEmpty(f2) ){
            return;

        }else if( isEmpty(f1) && isNotEmpty(f2) ){
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.NOT_REQUIRED_METADATA_FIELD_MISSING, DiffSeverity.MINOR,
                    fieldName, "TD1 is missing field "+fieldName+", but TD2 has it.");
            result.setData("td1.metadata."+fieldName, f1);
            result.setData("td2.metadata."+fieldName, f2);
            results.add(result);

        }else if( isNotEmpty(f1) && isEmpty(f2) ){
            TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                    TrustmarkDefinitionDiffType.NOT_REQUIRED_METADATA_FIELD_MISSING, DiffSeverity.MINOR,
                    fieldName, "TD2 is missing field "+fieldName+", but TD1 has it.");
            result.setData("td1."+fieldName, f1);
            result.setData("td2."+fieldName, f2);
            results.add(result);

        }else{ // Ok, both f1 && f1 have a value...

            if( f1.equals(f2) )
                return;

            if( f1.trim().equalsIgnoreCase(f2.trim()) ){
                // This case is debate-able.  Probably close enough to being the same to not worry about it for all practical purposes.
                TrustmarkDefinitionDiffResultImpl result = new TrustmarkDefinitionDiffResultImpl(
                        TrustmarkDefinitionDiffType.TEXT_SLIGHTLY_DIFFERENT,  DiffSeverity.MINOR,
                        fieldName, "The field values are the same if trimmed and ignoring case sensitivity.");
                result.setData("td1."+fieldName, f1);
                result.setData("td2."+fieldName, f2);
                results.add(result);

            }else{
                // Now we know they aren't perfectly the same, so perform some distance calculation...
                int distance = loadComparator().getStringDistance(f1, f2);

                TrustmarkDefinitionDiffResultImpl result = null;
                if( !loadComparator().isTextSignficantlyDifferent(distance, f1, f2) ){
                    result = new TrustmarkDefinitionDiffResultImpl(
                            TrustmarkDefinitionDiffType.TEXT_SLIGHTLY_DIFFERENT,  DiffSeverity.MINOR,
                            fieldName, "The values for "+fieldName+" are different, but not completely.");

                }else{  // More than 35 percent of the string has to change, so it is likely completely different text.
                    result = new TrustmarkDefinitionDiffResultImpl(
                            TrustmarkDefinitionDiffType.TEXT_COMPLETELY_DIFFERENT, DiffSeverity.MAJOR,
                            fieldName, "The values for "+fieldName+" are significantly different.");

                }
                result.setData("td1."+fieldName, f1);
                result.setData("td2."+fieldName, f2);
                result.setData("levenshtein", distance);
                results.add(result);
            }
        }
    }//end checkStringDifference()





}
