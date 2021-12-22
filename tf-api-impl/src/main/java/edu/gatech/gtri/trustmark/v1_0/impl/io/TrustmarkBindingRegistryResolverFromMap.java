package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistryJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;

import java.net.URI;
import java.util.Map;

public class TrustmarkBindingRegistryResolverFromMap extends AbstractResolverFromMap<TrustmarkBindingRegistry> implements TrustmarkBindingRegistryResolver {

    public TrustmarkBindingRegistryResolverFromMap(final Map<URI, TrustmarkBindingRegistry> map) {
        super(
                new TrustmarkBindingRegistryJsonDeserializer()::deserialize,
                uriString -> { throw new ResolveException("The system cannot resolve an XML representation of a trustmark binding registry."); },
                entity -> entity,
                map);
    }
}
