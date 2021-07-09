package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;

public class AgreementResolverImpl extends AbstractResolverFromURIResolver<Agreement> implements AgreementResolver {

    public AgreementResolverImpl() {
        super(
                string -> Codec.loadCodecFor(Agreement.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJson(string), string),
                string -> Codec.loadCodecFor(Agreement.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class));
    }
}
