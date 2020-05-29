package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

/**
 * Created by Nicholas on 01/25/2017.
 */
public class AdioTestXmlProducer extends AbstractDocumentXmlProducer<AdioTest1> {
    @Override
    public Class<AdioTest1> getSupportedType() {
        return AdioTest1.class;
    }
}
