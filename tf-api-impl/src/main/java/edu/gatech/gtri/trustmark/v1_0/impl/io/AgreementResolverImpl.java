package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public class AgreementResolverImpl extends AbstractResolverFromURIResolver<Agreement> implements AgreementResolver {

    public AgreementResolverImpl() {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, (string, uri) -> Codec.loadCodecFor(Agreement.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJson(string), string)),
                        p(AbstractResolverUtility::isXml, (string, uri) -> Codec.loadCodecFor(Agreement.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string))),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyAcceptXmlAcceptJson());
    }
}
