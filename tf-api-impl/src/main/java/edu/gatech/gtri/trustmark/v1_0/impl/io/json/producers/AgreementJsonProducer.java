package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;

/**
 * Created by Nicholas on 02/03/2017.
 */
public class AgreementJsonProducer extends AbstractDocumentJsonProducer<Agreement> {
    @Override
    public Class<Agreement> getSupportedType() {
        return Agreement.class;
    }
}
