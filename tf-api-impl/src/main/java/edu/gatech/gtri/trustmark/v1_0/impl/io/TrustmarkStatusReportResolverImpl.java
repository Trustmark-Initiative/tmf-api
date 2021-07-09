package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkStatusReportJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkStatusReportXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

public class TrustmarkStatusReportResolverImpl extends AbstractResolverFromURIResolver<TrustmarkStatusReport> implements TrustmarkStatusReportResolver {

    public TrustmarkStatusReportResolverImpl() {
        super(
                TrustmarkStatusReportJsonDeserializer::deserialize,
                TrustmarkStatusReportXmlDeserializer::deserialize,
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class));
    }

    @Override
    public TrustmarkStatusReport resolve(Trustmark trustmark) throws ResolveException {
        return TrustmarkStatusReportUtility.resolve(trustmark, this);
    }
}
