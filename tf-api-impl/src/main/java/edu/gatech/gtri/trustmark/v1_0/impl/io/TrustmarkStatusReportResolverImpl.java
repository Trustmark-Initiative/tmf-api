package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkStatusReportJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkStatusReportXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.product.P.p;

public final class TrustmarkStatusReportResolverImpl extends AbstractResolverFromURIResolver<TrustmarkStatusReport> implements TrustmarkStatusReportResolver {

    public TrustmarkStatusReportResolverImpl() {
        super(
                arrayList(
                        p(AbstractResolverUtility::isJson, new TrustmarkStatusReportJsonDeserializer()::deserialize),
                        p(AbstractResolverUtility::isXml, new TrustmarkStatusReportXmlDeserializer()::deserialize)),
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class),
                new URIIteratorStrategyAcceptXmlAcceptJson());
    }

    @Override
    public TrustmarkStatusReport resolve(Trustmark trustmark) throws ResolveException {
        return TrustmarkStatusReportUtility.resolve(trustmark, this);
    }
}
