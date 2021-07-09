package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.net.URI;
import java.util.Map;

public class TrustmarkDefinitionResolverFromMap extends AbstractResolverFromMap<TrustmarkDefinition> implements TrustmarkDefinitionResolver {

    public TrustmarkDefinitionResolverFromMap(final Map<URI, TrustmarkDefinition> map) {
        super(
                TrustmarkDefinitionJsonDeserializer::deserialize,
                TrustmarkDefinitionXmlDeserializer::deserialize,
                TrustmarkDefinitionUtility::validate,
                map);
    }
}
