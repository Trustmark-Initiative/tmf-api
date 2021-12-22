package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

public final class TrustInteroperabilityProfileResolverImpl extends AbstractResolverFromURIResolver<TrustInteroperabilityProfile> implements TrustInteroperabilityProfileResolver {

    public TrustInteroperabilityProfileResolverImpl() {
        super(
                new TrustInteroperabilityProfileJsonDeserializer()::deserialize,
                TrustInteroperabilityProfileXmlDeserializer::deserialize,
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class));
    }
}
