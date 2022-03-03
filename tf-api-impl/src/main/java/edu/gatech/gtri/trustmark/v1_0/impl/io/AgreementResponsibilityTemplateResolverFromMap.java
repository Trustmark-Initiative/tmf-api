package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResponsibilityTemplateResolver;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import java.net.URI;
import java.util.Map;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class AgreementResponsibilityTemplateResolverFromMap extends AbstractResolverFromMap<AgreementResponsibilityTemplate> implements AgreementResponsibilityTemplateResolver {

    public AgreementResponsibilityTemplateResolverFromMap(final Map<URI, AgreementResponsibilityTemplate> map) {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, (string, uri) -> Codec.loadCodecFor(AgreementResponsibilityTemplate.class).jsonDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getValidatedJson(string), string)),
                        p(AbstractResolverUtility::isXml, (string, uri) -> Codec.loadCodecFor(AgreementResponsibilityTemplate.class).xmlDeserializer.deserializeRootObjectNode(AbstractResolverUtility.getUnvalidatedXml(string), string))),
                entity -> entity,
                map);
    }
}
