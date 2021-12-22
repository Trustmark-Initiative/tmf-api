package edu.gatech.gtri.trustmark.v1_0.impl.io.hash;

import edu.gatech.gtri.trustmark.v1_0.io.hash.CanonFactory;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.gtri.fj.function.Try1;
import org.kohsuke.MetaInfServices;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.util.Objects.requireNonNull;

@MetaInfServices
public final class HashFactoryImpl implements HashFactory {

    private final CanonFactory canonFactory = new CanonFactoryImpl();

    public HashFactoryImpl() {
    }

    @Override
    public byte[] hash(final TrustmarkDefinition trustmarkDefinition) throws IOException {

        requireNonNull(trustmarkDefinition);

        return hashHelper(trustmarkDefinition, canonFactory::canon);
    }

    @Override
    public byte[] hash(final Trustmark trustmark) throws IOException {

        requireNonNull(trustmark);

        return hashHelper(trustmark, canonFactory::canon);
    }

    @Override
    public byte[] hash(final TrustmarkStatusReport trustmarkStatusReport) throws IOException {

        requireNonNull(trustmarkStatusReport);

        return hashHelper(trustmarkStatusReport, canonFactory::canon);
    }

    @Override
    public byte[] hash(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException {

        requireNonNull(trustInteroperabilityProfile);

        return hashHelper(trustInteroperabilityProfile, canonFactory::canon);
    }

    @Override
    public byte[] hashWithReference(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException {

        requireNonNull(trustInteroperabilityProfile);

        return hashHelper(trustInteroperabilityProfile, canonFactory::canonWithReference);
    }

    @Override
    public byte[] hash(final TrustmarkBindingRegistry trustmarkBindingRegistry) throws IOException {

        requireNonNull(trustmarkBindingRegistry);

        return hashHelper(trustmarkBindingRegistry, canonFactory::canon);
    }

    @Override
    public byte[] hash(final TrustmarkBindingRegistrySystem trustmarkBindingRegistrySystem) throws IOException {

        requireNonNull(trustmarkBindingRegistrySystem);

        return hashHelper(trustmarkBindingRegistrySystem, canonFactory::canon);
    }

    private <T1> byte[] hashHelper(final T1 serializeable, final Try1<T1, byte[], IOException> f) throws IOException {

        try {

            return MessageDigest.getInstance("SHA-256").digest(f.f(serializeable));

        } catch (final NoSuchAlgorithmException noSuchAlgorithmException) {

            throw new IOException(noSuchAlgorithmException);

        }
    }
}
