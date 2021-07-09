package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

public class TrustmarkDefinitionResolverImpl extends AbstractResolverFromURIResolver<TrustmarkDefinition> implements TrustmarkDefinitionResolver {

    public TrustmarkDefinitionResolverImpl() {
        super(
                TrustmarkDefinitionJsonDeserializer::deserialize,
                TrustmarkDefinitionXmlDeserializer::deserialize,
                TrustmarkDefinitionUtility::validate,
                FactoryLoader.getInstance(URIResolver.class));
    }
}
