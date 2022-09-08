package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class TrustInteroperabilityProfileResolverImplWithoutTerms extends AbstractResolverFromURIResolver<TrustInteroperabilityProfile> implements TrustInteroperabilityProfileResolver {

    public TrustInteroperabilityProfileResolverImplWithoutTerms() {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, new TrustInteroperabilityProfileJsonDeserializer(false)::deserialize),
                        p(AbstractResolverUtility::isXml, new TrustInteroperabilityProfileXmlDeserializer(false)::deserialize)),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyAcceptXmlAcceptJson());
    }
}
