package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class TrustmarkDefinitionResolverImplWithoutTerms extends AbstractResolverFromURIResolver<TrustmarkDefinition> implements TrustmarkDefinitionResolver {

    public TrustmarkDefinitionResolverImplWithoutTerms() {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, new TrustmarkDefinitionXmlDeserializer(false)::deserialize),
                        p(AbstractResolverUtility::isXml, new TrustmarkDefinitionXmlDeserializer(false)::deserialize)),
                TrustmarkDefinitionUtility::validate,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyAcceptXmlAcceptJson());
    }
}
