package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;

import java.net.URI;
import java.util.Map;

public class AgreementResolverFromMap extends AbstractResolverFromMap<Agreement> implements AgreementResolver {

    public AgreementResolverFromMap(final Map<URI, Agreement> map) {
        super(
                string -> Codec.loadCodecFor(Agreement.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJson(string), string),
                string -> Codec.loadCodecFor(Agreement.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string),
                entity -> entity,
                map);
    }
}
