package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import org.gtri.fj.product.P2;
import org.json.JSONArray;
import org.kohsuke.MetaInfServices;

import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.lang.StringUtility.stringOrd;

@MetaInfServices
public final class TrustmarkBindingRegistrySystemMapJsonProducer implements JsonProducer<TrustmarkBindingRegistrySystemMap, JSONArray> {

    private static final TrustmarkBindingRegistrySystemJsonProducer trustmarkBindingRegistrySystemJsonProducer = new TrustmarkBindingRegistrySystemJsonProducer();

    @Override
    public Class<TrustmarkBindingRegistrySystemMap> getSupportedType() {
        return TrustmarkBindingRegistrySystemMap.class;
    }

    @Override
    public Class<JSONArray> getSupportedTypeOutput() {
        return JSONArray.class;
    }

    @Override
    public JSONArray serialize(final TrustmarkBindingRegistrySystemMap trustmarkBindingRegistrySystemMap) {
        return new JSONArray(trustmarkBindingRegistrySystemMap.getSystemMap()
                .toList()
                .sort(ord((o1, o2) -> stringOrd.compare(o1._1(), o2._1())))
                .map(P2::_2)
                .map(trustmarkBindingRegistrySystem -> trustmarkBindingRegistrySystemJsonProducer.serialize(trustmarkBindingRegistrySystem)).toCollection());
    }
}
