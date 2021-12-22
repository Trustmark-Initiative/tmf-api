package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionUtils;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationSeverity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class TrustmarkDefinitionUtility {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkDefinitionUtility.class);

    private TrustmarkDefinitionUtility() {
    }

    public static TrustmarkDefinition validate(final TrustmarkDefinition entity) throws ResolveException {
        final Collection<ValidationResult> results = FactoryLoader.getInstance(TrustmarkDefinitionUtils.class).validate(entity);

        if (results != null && results.size() > 0) {
            log.warn("ERROR: Trustmark Definition validation results:");
            ValidationResult firstFatalResult = null;
            for (ValidationResult result : results) {
                log.warn("ERROR:     " + result.getSeverity() + ": " + result.getMessage());
                if (result.getSeverity() == ValidationSeverity.FATAL)
                    firstFatalResult = result;
            }
            if (firstFatalResult != null) {
                log.error("FATAL: " + firstFatalResult);
                throw new ResolveException("Trustmark Definition validation encountered a fatal error: " + firstFatalResult.getMessage());
            }
        }

        return entity;
    }
}
