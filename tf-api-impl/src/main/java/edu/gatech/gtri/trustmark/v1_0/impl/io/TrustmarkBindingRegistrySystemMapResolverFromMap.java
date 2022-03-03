package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistrySystemMapJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistrySystemMapResolver;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;

import java.net.URI;
import java.util.Map;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class TrustmarkBindingRegistrySystemMapResolverFromMap extends AbstractResolverFromMap<TrustmarkBindingRegistrySystemMap> implements TrustmarkBindingRegistrySystemMapResolver {

    public TrustmarkBindingRegistrySystemMapResolverFromMap(final Map<URI, TrustmarkBindingRegistrySystemMap> map) {
        super(
                arrayList(p(AbstractResolverUtility::isJson, new TrustmarkBindingRegistrySystemMapJsonDeserializer()::deserialize)),
                entity -> entity,
                map);
    }
}
