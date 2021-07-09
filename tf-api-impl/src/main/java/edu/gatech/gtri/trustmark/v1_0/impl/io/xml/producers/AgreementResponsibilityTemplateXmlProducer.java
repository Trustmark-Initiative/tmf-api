package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentXmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

/**
 * Created by Nicholas on 2017-06-06.
 */
public class AgreementResponsibilityTemplateXmlProducer extends AbstractDocumentXmlProducer<AgreementResponsibilityTemplate> {
    @Override
    public Class<AgreementResponsibilityTemplate> getSupportedType() {
        return AgreementResponsibilityTemplate.class;
    }
}
