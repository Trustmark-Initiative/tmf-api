package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistrySystemMapJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistrySystemMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import org.kohsuke.MetaInfServices;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

@MetaInfServices(TrustmarkBindingRegistrySystemMapResolver.class)
public final class TrustmarkBindingRegistrySystemMapResolverImpl extends AbstractResolverFromURIResolver<TrustmarkBindingRegistrySystemMap> implements TrustmarkBindingRegistrySystemMapResolver {

    public TrustmarkBindingRegistrySystemMapResolverImpl() {
        super(
                arrayList(p(AbstractResolverUtility::isJson, new TrustmarkBindingRegistrySystemMapJsonDeserializer()::deserialize)),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyDefault());
    }
}
