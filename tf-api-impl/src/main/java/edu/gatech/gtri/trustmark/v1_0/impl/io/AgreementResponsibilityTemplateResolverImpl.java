package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResponsibilityTemplateResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class AgreementResponsibilityTemplateResolverImpl extends AbstractResolverFromURIResolver<AgreementResponsibilityTemplate> implements AgreementResponsibilityTemplateResolver {

    public AgreementResponsibilityTemplateResolverImpl() {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, (string, uri) -> Codec.loadCodecFor(AgreementResponsibilityTemplate.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJson(string), string)),
                        p(AbstractResolverUtility::isXml, (string, uri) -> Codec.loadCodecFor(AgreementResponsibilityTemplate.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string))),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyAcceptXmlAcceptJson());
    }
}
