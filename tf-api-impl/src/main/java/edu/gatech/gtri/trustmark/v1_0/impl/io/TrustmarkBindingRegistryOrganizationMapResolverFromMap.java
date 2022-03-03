package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistryOrganizationMapJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryOrganizationMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;

import java.net.URI;
import java.util.Map;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class TrustmarkBindingRegistryOrganizationMapResolverFromMap extends AbstractResolverFromMap<TrustmarkBindingRegistryOrganizationMap> implements TrustmarkBindingRegistryOrganizationMapResolver {

    public TrustmarkBindingRegistryOrganizationMapResolverFromMap(final Map<URI, TrustmarkBindingRegistryOrganizationMap> map) {
        super(
                arrayList(p(AbstractResolverUtility::isJson, new TrustmarkBindingRegistryOrganizationMapJsonDeserializer()::deserialize)),
                entity -> entity,
                map);
    }
}
