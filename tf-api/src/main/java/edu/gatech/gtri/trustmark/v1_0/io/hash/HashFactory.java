package edu.gatech.gtri.trustmark.v1_0.io.hash;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

import java.io.IOException;

public interface HashFactory {

    /**
     * Return the hash for the Trustmark Definition: calculate the hash from the XML representation of the Trustmark Definition.
     *
     * @param trustmarkDefinition the Trustmark Definition
     * @return the hash for the Trustmark Definition
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] hash(final TrustmarkDefinition trustmarkDefinition) throws IOException;

    /**
     * Return the hash for the Trustmark: calculate the hash from the XML representation of the Trustmark.
     *
     * @param trustmark the Trustmark
     * @return the hash for the Trustmark
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] hash(final Trustmark trustmark) throws IOException;

    /**
     * Return the hash for the Trustmark Status Report: calculate the hash from the XML representation of the Status Report.
     *
     * @param trustmarkStatusReport the Trustmark Status Report
     * @return the hash for the Trustmark Status Report
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] hash(final TrustmarkStatusReport trustmarkStatusReport) throws IOException;

    /**
     * Return the hash for the Trust Interoperability Profile: calculate the hash from the XML representation of the Trust Interoperability Profile.
     *
     * @param trustInteroperabilityProfile the Trust Interoperability Profile
     * @return the hash for the Trust Interoperability Profile
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] hash(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException;

    /**
     * Return the hash for the Trust Interoperability Profile and its referenced Trust Interoperability Profiles.
     *
     * @param trustInteroperabilityProfile the Trust Interoperability Profile
     * @return the hash for the Trust Interoperability Profile and its referenced Trust Interoperability Profiles.
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] hashWithReference(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException;

    /**
     * Return the hash for the Trustmark Binding Registry.
     *
     * @param trustmarkBindingRegistry the Trustmark Binding Registry
     * @return the hash for the Trust Interoperability Profile
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] hash(final TrustmarkBindingRegistry trustmarkBindingRegistry) throws IOException;

    /**
     * Return the hash for the Trustmark Binding Registry System.
     *
     * @param trustmarkBindingRegistrySystem the Trustmark Binding Registry System
     * @return the canonical representation the Trustmark Binding Registry System
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] hash(final TrustmarkBindingRegistrySystem trustmarkBindingRegistrySystem) throws IOException;
}
