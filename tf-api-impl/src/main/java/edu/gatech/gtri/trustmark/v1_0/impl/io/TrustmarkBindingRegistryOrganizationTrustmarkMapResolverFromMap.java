package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistryOrganizationTrustmarkMapJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationTrustmarkMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;

import java.net.URI;
import java.util.Map;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class TrustmarkBindingRegistryOrganizationTrustmarkMapResolverFromMap extends AbstractResolverFromMap<TrustmarkBindingRegistryOrganizationTrustmarkMap> implements TrustmarkBindingRegistryOrganizationTrustmarkMapResolver {

    public TrustmarkBindingRegistryOrganizationTrustmarkMapResolverFromMap(final Map<URI, TrustmarkBindingRegistryOrganizationTrustmarkMap> map) {
        super(
                arrayList(p(AbstractResolverUtility::isJson, new TrustmarkBindingRegistryOrganizationTrustmarkMapJsonDeserializer()::deserialize)),
                entity -> entity,
                map);
    }
}
