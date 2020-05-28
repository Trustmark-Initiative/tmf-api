package edu.gatech.gtri.trustmark.v1_0.impl.util.validation;

import edu.gatech.gtri.trustmark.v1_0.impl.util.ValidationResultImpl;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.util.TrustInteroperabilityProfileValidator;
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
public class TipEmptyCheckValidator implements TrustInteroperabilityProfileValidator {

    @Override
    public Collection<ValidationResult> validate(TrustInteroperabilityProfile tip) {
        ArrayList<ValidationResult> results = new ArrayList<>();

        if( tip.getIdentifier() == null || StringUtils.isBlank(tip.getIdentifier().toString()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trust Interoperability Profiles require an Identifier.", "Identifier"));

        if(StringUtils.isBlank(tip.getName()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trust Interoperability Profiles require a Name.", "Name"));
        
        if(StringUtils.isBlank(tip.getVersion()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trust Interoperability Profiles require a Version.", "Version"));

        if(StringUtils.isBlank(tip.getDescription()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trust Interoperability Profiles require a Description.", "Description"));

        if( tip.getPublicationDateTime() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trust Interoperability Profiles require a Publication Date Time.", "PublicationDateTime"));

        if( tip.getIssuer() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trust Interoperability Profiles require an Issuing Organization.", "Issuer"));
        if( StringUtils.isBlank(tip.getIssuer().getName()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Issuing organization MUST provide a name.", "Issuer.Name"));
        if( tip.getIssuer().getIdentifier() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Issuing organization MUST provide an Identifier.", "Issuer.Identifier"));
        if( tip.getIssuer().getDefaultContact() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Issuing organization MUST provide a contact.", "Issuer.Contacts"));

        if( tip.isDeprecated() && tip.getSupersededBy() == null )
            results.add(new ValidationResultImpl(ValidationSeverity.WARNING, "If a Trust Interoperabilty Profile is deprecated, then it should also have a superseded by value.", "SupersededBy"));

        if( StringUtils.isBlank(tip.getTrustExpression()) )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trust Interoperability Profiles require a Trust Expression.", "TrustExpression"));

        if( tip.getReferences() == null || tip.getReferences().size() == 0 )
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All Trust Interoperability Profiles require at least 1 reference.", "References"));
        else {
            List<AbstractTIPReference> tipReferenceList = new ArrayList<>(tip.getReferences());
            for (int i = 0; i < tipReferenceList.size(); i++) {
                AbstractTIPReference tipReference = tipReferenceList.get(i);
                if( StringUtils.isBlank(tipReference.getId()) ){
                    results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All TIP References require a unique identifier.", "References["+i+"].id"));
                }
                if( !tipReference.isTrustInteroperabilityProfileReference() && !tipReference.isTrustmarkDefinitionRequirement() ){
                    results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "TIP Reference #"+i+" is claiming to be both a TIP Reference and TD Requirement - this is not possible.", "References["+i+"]"));
                }else if( tipReference.isTrustInteroperabilityProfileReference() && tipReference.isTrustmarkDefinitionRequirement() ){
                    results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "TIP Reference #"+i+" is claiming to be neither a TIP Reference nor a TD Requirement - this is not possible.", "References["+i+"]"));
                }
                if( tipReference.getIdentifier() == null )
                    results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "All TIP References require a valid Identifier value.", "References["+i+"].Identifier"));
            }
        }


        return results;
    }



}//end TdEmptyCheckValidator