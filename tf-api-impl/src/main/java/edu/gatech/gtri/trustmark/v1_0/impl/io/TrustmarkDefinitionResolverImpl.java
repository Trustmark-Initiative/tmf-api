package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class TrustmarkDefinitionResolverImpl extends AbstractResolverFromURIResolver<TrustmarkDefinition> implements TrustmarkDefinitionResolver {

    public TrustmarkDefinitionResolverImpl() {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, new TrustmarkDefinitionJsonDeserializer(true)::deserialize),
                        p(AbstractResolverUtility::isXml, new TrustmarkDefinitionXmlDeserializer(true)::deserialize)),
                TrustmarkDefinitionUtility::validate,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyAcceptXmlAcceptJson());
    }
}
