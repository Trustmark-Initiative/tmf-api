package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.net.URI;
import java.util.Map;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class TrustmarkDefinitionResolverFromMap extends AbstractResolverFromMap<TrustmarkDefinition> implements TrustmarkDefinitionResolver {

    public TrustmarkDefinitionResolverFromMap(final Map<URI, TrustmarkDefinition> map) {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, new TrustmarkDefinitionJsonDeserializer()::deserialize),
                        p(AbstractResolverUtility::isXml, new TrustmarkDefinitionXmlDeserializer()::deserialize)),
                TrustmarkDefinitionUtility::validate,
                map);
    }
}
