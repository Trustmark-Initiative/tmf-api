package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkBindingRegistryJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkBindingRegistryResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import org.kohsuke.MetaInfServices;

@MetaInfServices(TrustmarkBindingRegistryResolver.class)
public final class TrustmarkBindingRegistryResolverImpl extends AbstractResolverFromURIResolver<TrustmarkBindingRegistry> implements TrustmarkBindingRegistryResolver {

    public TrustmarkBindingRegistryResolverImpl() {
        super(
                new TrustmarkBindingRegistryJsonDeserializer()::deserialize,
                uriString -> {
                    throw new ResolveException("The system cannot resolve an XML representation of a trustmark binding registry.");
                },
                entity -> entity,
                FactoryLoader.getInstance(URIResolver.class));
    }
}
