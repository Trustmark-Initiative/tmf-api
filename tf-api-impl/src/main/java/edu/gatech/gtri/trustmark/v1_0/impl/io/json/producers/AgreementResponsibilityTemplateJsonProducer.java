package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

/**
 * Created by Nicholas on 2017-06-06.
 */
public class AgreementResponsibilityTemplateJsonProducer extends AbstractDocumentJsonProducer<AgreementResponsibilityTemplate> {
    @Override
    public Class<AgreementResponsibilityTemplate> getSupportedType() {
        return AgreementResponsibilityTemplate.class;
    }
}
