package edu.gatech.gtri.trustmark.v1_0.impl.util.validation;

import edu.gatech.gtri.trustmark.v1_0.impl.antlr.TrustExpressionUtils;
import edu.gatech.gtri.trustmark.v1_0.impl.util.ValidationResultImpl;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.util.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Validates the Trust Expression on a TIP.
 * <br/><br/>
 * Created by brad on 3/9/17.
 */
public class TipTrustExpressionValidator implements TrustInteroperabilityProfileValidator {

    @Override
    public Collection<ValidationResult> validate(TrustInteroperabilityProfile tip) {
        ArrayList<ValidationResult> results = new ArrayList<>();

        ArrayList<String> bindings = new ArrayList<>();
        for(AbstractTIPReference ref : tip.getReferences() ){
            bindings.add(ref.getId());
        }
        try {
            TrustExpressionUtils.validateWithBindings(tip.getTrustExpression(), bindings);
        }catch(TrustExpressionSyntaxException tese){
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Trust Expression Syntax Error on (line: "+tese.getLine()+", col: "+tese.getColumn()+"): "+tese.getOffendingSymbol().toString(), "TrustExpression"));
        }catch(TrustExpressionHasUndeclaredIdException uide){
            results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "The Trust Expression contains the following ids which were not defined: "+uide.getIdsNotDeclared(), "TrustExpression"));
        }

        return results;
    }



}//end TdEmptyCheckValidator