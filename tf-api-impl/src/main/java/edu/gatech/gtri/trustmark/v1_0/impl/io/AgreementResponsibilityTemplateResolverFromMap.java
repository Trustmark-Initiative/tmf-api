package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResponsibilityTemplateResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import java.net.URI;
import java.util.Map;

public class AgreementResponsibilityTemplateResolverFromMap extends AbstractResolverFromMap<AgreementResponsibilityTemplate> implements AgreementResponsibilityTemplateResolver {

    public AgreementResponsibilityTemplateResolverFromMap(final Map<URI, AgreementResponsibilityTemplate> map) {
        super(
                string -> Codec.loadCodecFor(AgreementResponsibilityTemplate.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJson(string), string),
                string -> Codec.loadCodecFor(AgreementResponsibilityTemplate.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string),
                entity -> entity,
                map);
    }
}
