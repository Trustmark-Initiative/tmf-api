package edu.gatech.gtri.trustmark.v1_0.model;

import org.gtri.fj.data.List;

import java.net.URI;

public interface TrustmarkBindingRegistrySystem extends HasSource {

    String getIdentifier();

    URI getMetadata();

    String getName();

    TrustmarkBindingRegistrySystemType getSystemType();

    List<URI> getTrustmarkRecipientIdentifiers();

    List<URI> getTrustmarks();
}
