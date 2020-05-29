package edu.gatech.gtri.trustmark.v1_0.impl.util.validation;

import edu.gatech.gtri.trustmark.v1_0.impl.util.ValidationResultImpl;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionValidator;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationSeverity;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by brad on 3/9/17.
 */
public class TdEmptyCheckValidator implements TrustmarkDefinitionValidator {

    @Override
    public Collection<ValidationResult> validate(TrustmarkDefinition td) {
        ArrayList<ValidationResult> results = new ArrayList<>();

        if( td.getMetadata() == null ) {
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Missing Metadata - all TDs require metadata.", "Metadata"));
            return results; // Terminate this check early, since many other checks are predicated on this.
        }

        if( td.getMetadata().getIdentifier() == null || StringUtils.isBlank(td.getMetadata().getIdentifier().toString()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definitions require an Identifier.", "Metadata.Identifier"));

        if(StringUtils.isBlank(td.getMetadata().getName()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definitions require a Name.", "Metadata.Name"));

        if( td.getMetadata().getTrustmarkReferenceAttributeName() == null || StringUtils.isBlank(td.getMetadata().getTrustmarkReferenceAttributeName().toString()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definitions require a Reference Attribute Name.", "Metadata.TrustmarkReferenceAttributeName"));

        if(StringUtils.isBlank(td.getMetadata().getVersion()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definitions require a Version.", "Metadata.Version"));

        if(StringUtils.isBlank(td.getMetadata().getDescription()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definitions require a Description.", "Metadata.Description"));

        if( td.getMetadata().getPublicationDateTime() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definitions require a Publication Date Time.", "Metadata.PublicationDateTime"));

        if( td.getMetadata().getTrustmarkDefiningOrganization() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definitions require a Defining Organization.", "Metadata.TrustmarkDefiningOrganization"));
        if( StringUtils.isBlank(td.getMetadata().getTrustmarkDefiningOrganization().getName()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Defining organization MUST provide a name.", "Metadata.TrustmarkDefiningOrganization.Name"));
        if( td.getMetadata().getTrustmarkDefiningOrganization().getIdentifier() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Defining organization MUST provide an Identifier.", "Metadata.TrustmarkDefiningOrganization.Identifier"));

        if( td.getMetadata().isDeprecated() && td.getMetadata().getSupersededBy() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.WARNING, "If a Trustmark Definition is deprecated, then it should also have a superseded by value.", "Metadata.SupersededBy"));

        if( td.getAssessmentSteps() == null || td.getAssessmentSteps().size() == 0 )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definitions require at least 1 assessment step.", "AssessmentSteps"));


        for(int i = 0; i < td.getAssessmentSteps().size(); i++){
            AssessmentStep step = td.getAssessmentSteps().get(i);
            if( StringUtils.isBlank(step.getName()) ){
                results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definition Assessment Steps require a valid name.", "AssessmentSteps["+i+"].Name"));
            }
            if( StringUtils.isBlank(step.getDescription()) ){
                results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definition Assessment Steps require a valid description.", "AssessmentSteps["+i+"].Description"));
            }
            if( step.getNumber() == null && step.getNumber() <= 0){
                results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definition Assessment Steps require a valid number.", "AssessmentSteps["+i+"].Number"));
            }
        }


        if( td.getConformanceCriteria() != null && td.getConformanceCriteria().size() > 0 ) {
            for (int i = 0; i < td.getConformanceCriteria().size(); i++) {
                ConformanceCriterion criterion = td.getConformanceCriteria().get(i);
                if (StringUtils.isBlank(criterion.getName())) {
                    results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definition Conformance Criteria require a valid name.", "ConformanceCriteria[" + i + "].Name"));
                }
                if (StringUtils.isBlank(criterion.getDescription())) {
                    results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definition Conformance Criteria require a valid description.", "ConformanceCriteria[" + i + "].Description"));
                }
                if (criterion.getNumber() == null && criterion.getNumber() <= 0) {
                    results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trustmark Definition Conformance Criteria require a valid number.", "ConformanceCriteria[" + i + "].Number"));
                }
            }
        }


        return results;
    }



}//end TdEmptyCheckValidator