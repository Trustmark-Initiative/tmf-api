package edu.gatech.gtri.trustmark.v1_0.io.hash;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

import java.io.IOException;

public interface CanonFactory {

    /**
     * Return the canonical representation for the Trustmark Definition.
     *
     * @param trustmarkDefinition the Trustmark Definition
     * @return the canonical representation for the Trustmark Definition
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] canon(final TrustmarkDefinition trustmarkDefinition) throws IOException;

    /**
     * Return the canonical representation for the Trustmark.
     *
     * @param trustmark the Trustmark
     * @return the canonical representation for the Trustmark
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] canon(final Trustmark trustmark) throws IOException;

    /**
     * Return the canonical representation for the Trustmark Status Report.
     *
     * @param trustmarkStatusReport the Trustmark Status Report
     * @return the canonical representation for the Trustmark Status Report
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] canon(final TrustmarkStatusReport trustmarkStatusReport) throws IOException;

    /**
     * Return the canonical representation for the Trust Interoperability Profile.
     *
     * @param trustInteroperabilityProfile the Trust Interoperability Profile
     * @return the canonical representation for the Trust Interoperability Profile
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] canon(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException;

    /**
     * Return the canonical representation for the Trust Interoperability Profile, its referenced Trust Interoperability Profiles, and its Trustmark Definitions.
     *
     * @param trustInteroperabilityProfile the Trust Interoperability Profile
     * @return the canonical representation for the Trust Interoperability Profile, and its referenced Trust Interoperability Profiles, and its referenced Trustmark Definitions.
     * @throws IOException if an exception occurs during XML serialization
     */
    byte[] canonWithReference(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException;

    /**
     * Return the canonical representation for the Trustmark Binding Registry.
     *
     * @param trustmarkBindingRegistry the Trustmark Binding Registry
     * @return the canonical representation the Trustmark Binding Registry
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] canon(final TrustmarkBindingRegistry trustmarkBindingRegistry) throws IOException;

    /**
     * Return the canonical representation for the Trustmark Binding Registry System.
     *
     * @param trustmarkBindingRegistrySystem the Trustmark Binding Registry System
     * @return the canonical representation the Trustmark Binding Registry System
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] canon(final TrustmarkBindingRegistrySystem trustmarkBindingRegistrySystem) throws IOException;
}
