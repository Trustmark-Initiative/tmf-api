package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

public class TrustmarkResolverImpl extends AbstractResolverFromURIResolver<Trustmark> implements TrustmarkResolver {

    public TrustmarkResolverImpl() {
        super(
                new TrustmarkJsonDeserializer()::deserialize,
                TrustmarkXmlDeserializer::deserialize,
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class));
    }
}
