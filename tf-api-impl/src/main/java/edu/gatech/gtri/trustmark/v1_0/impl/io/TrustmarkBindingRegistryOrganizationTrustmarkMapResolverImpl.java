package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationTrustmarkMapResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import org.kohsuke.MetaInfServices;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

@MetaInfServices(TrustmarkBindingRegistryOrganizationTrustmarkMapResolver.class)
public final class TrustmarkBindingRegistryOrganizationTrustmarkMapResolverImpl extends AbstractResolverFromURIResolver<TrustmarkBindingRegistryOrganizationTrustmarkMap> implements TrustmarkBindingRegistryOrganizationTrustmarkMapResolver {

    public TrustmarkBindingRegistryOrganizationTrustmarkMapResolverImpl() {
        super(
                arrayList(p(AbstractResolverUtility::isJson, new TrustmarkBindingRegistryOrganizationTrustmarkMapJsonDeserializer()::deserialize)),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyDefault());
    }
}
