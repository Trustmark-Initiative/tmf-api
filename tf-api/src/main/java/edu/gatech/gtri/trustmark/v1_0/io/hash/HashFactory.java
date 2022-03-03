package edu.gatech.gtri.trustmark.v1_0.io.hash;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganization;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmark;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;

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
     * @param trustmarkBindingRegistrySystemMap the Trustmark Binding Registry System Map
     * @return the hash for the Trustmark Binding Registry System Map
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] hash(final TrustmarkBindingRegistrySystemMap trustmarkBindingRegistrySystemMap) throws IOException;

    /**
     * Return the hash for the Trustmark Binding Registry System.
     *
     * @param trustmarkBindingRegistrySystem the Trustmark Binding Registry System
     * @return the hash the Trustmark Binding Registry System
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] hash(final TrustmarkBindingRegistrySystem trustmarkBindingRegistrySystem) throws IOException;

    /**
     * Return the hash for the Trustmark Binding Registry Organization Map
     *
     * @param trustmarkBindingRegistryOrganizationMap the Trustmark Binding Registry Organization Map
     * @return the hash for the Trustmark Binding Registry Organization Map
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] hash(final TrustmarkBindingRegistryOrganizationMap trustmarkBindingRegistryOrganizationMap) throws IOException;

    /**
     * Return the hash for the Trustmark Binding Registry Organization
     *
     * @param trustmarkBindingRegistryOrganization the Trustmark Binding Registry Organization
     * @return the hash for the Trustmark Binding Registry Organization
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] hash(final TrustmarkBindingRegistryOrganization trustmarkBindingRegistryOrganization) throws IOException;

    /**
     * Return the hash for the Trustmark Binding Registry Organization Trustmark Map
     *
     * @param trustmarkBindingRegistryOrganizationTrustmarkMap the Trustmark Binding Registry Organization Trustmark Map
     * @return the hash for the Trustmark Binding Registry Organization Trustmark Map
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] hash(final TrustmarkBindingRegistryOrganizationTrustmarkMap trustmarkBindingRegistryOrganizationTrustmarkMap) throws IOException;

    /**
     * Return the hash for the Trustmark Binding Registry Organization Trustmark
     *
     * @param trustmarkBindingRegistryOrganizationTrustmark the Trustmark Binding Registry Organization Trustmark
     * @return the hash for the Trustmark Binding Registry Organization Trustmark
     * @throws IOException if an exception occurs during JSON serialization
     */
    byte[] hash(final TrustmarkBindingRegistryOrganizationTrustmark trustmarkBindingRegistryOrganizationTrustmark) throws IOException;
}
