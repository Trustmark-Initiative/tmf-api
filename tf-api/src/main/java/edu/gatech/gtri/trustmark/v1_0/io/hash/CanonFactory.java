package edu.gatech.gtri.trustmark.v1_0.io.hash;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganization;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmark;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystem;
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
     * @param trustmarkBindingRegistrySystemMap the Trustmark Binding Registry
     * @return the canonical representation the Trustmark Binding Registry
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] canon(final TrustmarkBindingRegistrySystemMap trustmarkBindingRegistrySystemMap) throws IOException;

    /**
     * Return the canonical representation for the Trustmark Binding Registry System.
     *
     * @param trustmarkBindingRegistrySystem the Trustmark Binding Registry System
     * @return the canonical representation the Trustmark Binding Registry System
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] canon(final TrustmarkBindingRegistrySystem trustmarkBindingRegistrySystem) throws IOException;

    /**
     * Return the canonical representation for the Trustmark Binding Registry Organization Map
     *
     * @param trustmarkBindingRegistryOrganizationMap the Trustmark Binding Registry Organization Map
     * @return the canonical representation for the Trustmark Binding Registry Organization Map
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] canon(final TrustmarkBindingRegistryOrganizationMap trustmarkBindingRegistryOrganizationMap) throws IOException;

    /**
     * Return the canonical representation for the Trustmark Binding Registry Organization
     *
     * @param trustmarkBindingRegistryOrganization the Trustmark Binding Registry Organization
     * @return the canonical representation for the Trustmark Binding Registry Organization
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] canon(final TrustmarkBindingRegistryOrganization trustmarkBindingRegistryOrganization) throws IOException;

    /**
     * Return the canonical representation for the Trustmark Binding Registry Organization Trustmark Map
     *
     * @param trustmarkBindingRegistryOrganizationTrustmarkMap the Trustmark Binding Registry Organization Trustmark Map
     * @return the canonical representation for the Trustmark Binding Registry Organization Trustmark Map
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] canon(final TrustmarkBindingRegistryOrganizationTrustmarkMap trustmarkBindingRegistryOrganizationTrustmarkMap) throws IOException;

    /**
     * Return the canonical representation for the Trustmark Binding Registry Organization Trustmark
     *
     * @param trustmarkBindingRegistryOrganizationTrustmark the Trustmark Binding Registry Organization Trustmark
     * @return the canonical representation for the Trustmark Binding Registry Organization Trustmark
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] canon(final TrustmarkBindingRegistryOrganizationTrustmark trustmarkBindingRegistryOrganizationTrustmark) throws IOException;
}
