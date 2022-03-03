package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;

import java.net.URI;
import java.util.Map;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class AgreementResolverFromMap extends AbstractResolverFromMap<Agreement> implements AgreementResolver {

    public AgreementResolverFromMap(final Map<URI, Agreement> map) {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, (string, uri) -> Codec.loadCodecFor(Agreement.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJson(string), string)),
                        p(AbstractResolverUtility::isXml, (string, uri) -> Codec.loadCodecFor(Agreement.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string))),
                entity -> entity,
                map);
    }
}
