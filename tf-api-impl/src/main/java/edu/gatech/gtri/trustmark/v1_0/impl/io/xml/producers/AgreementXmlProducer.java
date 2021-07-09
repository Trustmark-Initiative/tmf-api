package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentXmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;

/**
 * Created by Nicholas on 02/03/2017.
 */
public class AgreementXmlProducer extends AbstractDocumentXmlProducer<Agreement> {
    @Override
    public Class<Agreement> getSupportedType() {
        return Agreement.class;
    }
}
