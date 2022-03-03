package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistryOrganizationMapJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import org.kohsuke.MetaInfServices;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

@MetaInfServices(TrustmarkBindingRegistryOrganizationMapResolver.class)
public final class TrustmarkBindingRegistryOrganizationMapResolverImpl extends AbstractResolverFromURIResolver<TrustmarkBindingRegistryOrganizationMap> implements TrustmarkBindingRegistryOrganizationMapResolver {

    public TrustmarkBindingRegistryOrganizationMapResolverImpl() {
        super(
                arrayList(p(AbstractResolverUtility::isJson, new TrustmarkBindingRegistryOrganizationMapJsonDeserializer()::deserialize)),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyDefault());
    }
}
