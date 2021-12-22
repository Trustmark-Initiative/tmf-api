package edu.gatech.gtri.trustmark.v1_0.model;

import org.gtri.fj.data.TreeMap;

public interface TrustmarkBindingRegistry extends HasSource {

    TreeMap<String, TrustmarkBindingRegistrySystem> getSystemMap();
}
