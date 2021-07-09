package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

import java.net.URI;
import java.util.Map;

public class TrustmarkResolverFromMap extends AbstractResolverFromMap<Trustmark> implements TrustmarkResolver {

    public TrustmarkResolverFromMap(final Map<URI, Trustmark> map) {
        super(
                TrustmarkJsonDeserializer::deserialize,
                TrustmarkXmlDeserializer::deserialize,
                entity -> entity,
                map);
    }
}
