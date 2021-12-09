package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

import java.net.URI;
import java.util.Map;

public class TrustInteroperabilityProfileResolverFromMap extends AbstractResolverFromMap<TrustInteroperabilityProfile> implements TrustInteroperabilityProfileResolver {

    public TrustInteroperabilityProfileResolverFromMap(final Map<URI, TrustInteroperabilityProfile> map) {
        super(
                new TrustInteroperabilityProfileJsonDeserializer()::deserialize,
                TrustInteroperabilityProfileXmlDeserializer::deserialize,
                entity -> entity,
                map);
    }
}
