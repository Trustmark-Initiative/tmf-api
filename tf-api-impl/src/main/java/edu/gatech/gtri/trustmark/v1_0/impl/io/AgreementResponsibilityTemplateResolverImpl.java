package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResponsibilityTemplateResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

public class AgreementResponsibilityTemplateResolverImpl extends AbstractResolverFromURIResolver<AgreementResponsibilityTemplate> implements AgreementResponsibilityTemplateResolver {

    public AgreementResponsibilityTemplateResolverImpl() {
        super(
                string -> Codec.loadCodecFor(AgreementResponsibilityTemplate.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJson(string), string),
                string -> Codec.loadCodecFor(AgreementResponsibilityTemplate.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class));
    }
}
