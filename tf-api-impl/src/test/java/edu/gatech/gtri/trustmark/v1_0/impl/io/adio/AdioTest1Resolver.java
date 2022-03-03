package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractResolverFromURIResolver;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractResolverUtility;
import edu.gatech.gtri.trustmark.v1_0.impl.io.URIIteratorStrategyAcceptXmlAcceptJson;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

/**
 * Created by Nicholas on 01/25/2017.
 */
public class AdioTest1Resolver extends AbstractResolverFromURIResolver<AdioTest1> {

    public AdioTest1Resolver() {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, (string, uri) -> Codec.loadCodecFor(AdioTest1.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJsonIsSupportedVersion(string), string)),
                        p(AbstractResolverUtility::isXml, (string, uri) -> Codec.loadCodecFor(AdioTest1.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string))),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyAcceptXmlAcceptJson());
    }
}
