package edu.gatech.gtri.trustmark.v1_0.impl.util.validation;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.util.ValidationResultImpl;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionStringParserFactory;
import edu.gatech.gtri.trustmark.v1_0.util.TrustInteroperabilityProfileValidator;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationSeverity;
import org.gtri.fj.data.List;

import java.util.Collection;

import static org.gtri.fj.data.List.arrayList;

public class TrustExpressionValidator implements TrustInteroperabilityProfileValidator {

    private static final TrustExpressionStringParserFactory trustExpressionStringParserFactory = FactoryLoader.getInstance(TrustExpressionStringParserFactory.class);

    @Override
    public Collection<ValidationResult> validate(TrustInteroperabilityProfile trustInteroperabilityProfile) {

        try {
            trustExpressionStringParserFactory.createDefaultParser().parse(trustInteroperabilityProfile.getTrustExpression());
            return List.<ValidationResult>nil().toJavaList();
        } catch (final RuntimeException runtimeException) {
            return arrayList((ValidationResult) new ValidationResultImpl(ValidationSeverity.FATAL, runtimeException.getMessage())).toJavaList();
        }
    }
}
