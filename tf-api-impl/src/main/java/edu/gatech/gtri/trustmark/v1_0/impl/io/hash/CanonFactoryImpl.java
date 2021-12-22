package edu.gatech.gtri.trustmark.v1_0.impl.io.hash;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkStatusReportImpl;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.CanonFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystem;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.gtri.fj.Ord;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.Try;
import org.kohsuke.MetaInfServices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.Either.eitherOrd;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.sequenceValidation;
import static org.gtri.fj.data.NonEmptyList.nonEmptyListSemigroup;
import static org.gtri.fj.lang.StringUtility.stringOrd;

@MetaInfServices
public final class CanonFactoryImpl implements CanonFactory {

    private final Ord<URI> uriOrd = ord((uri1, uri2) -> stringOrd.compare(uri1.toString(), uri2.toString()));
    private final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver;
    private final TrustmarkDefinitionResolver trustmarkDefinitionResolver;
    private final TrustmarkStatusReportResolver trustmarkStatusReportResolver;
    private final TrustmarkResolver trustmarkResolver;
    private final SerializerXml serializerXml;

    public CanonFactoryImpl() {

        this.trustInteroperabilityProfileResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
        this.trustmarkDefinitionResolver = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class);
        this.trustmarkStatusReportResolver = FactoryLoader.getInstance(TrustmarkStatusReportResolver.class);
        this.trustmarkResolver = FactoryLoader.getInstance(TrustmarkResolver.class);
        this.serializerXml = new SerializerXml();
    }

    @Override
    public byte[] canon(final TrustmarkDefinition trustmarkDefinition) throws IOException {

        requireNonNull(trustmarkDefinition);

        return canonHelper(trustmarkDefinition, this::serialize);
    }

    @Override
    public byte[] canon(final Trustmark trustmark) throws IOException {

        requireNonNull(trustmark);

        return canonHelper(trustmark, this::serialize);
    }

    @Override
    public byte[] canon(final TrustmarkStatusReport trustmarkStatusReport) throws IOException {

        requireNonNull(trustmarkStatusReport);

        return canonHelper(trustmarkStatusReport, this::serialize);
    }

    @Override
    public byte[] canon(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException {

        requireNonNull(trustInteroperabilityProfile);

        return canonHelper(trustInteroperabilityProfile, this::serialize);
    }

    @Override
    public byte[] canonWithReference(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException {

        requireNonNull(trustInteroperabilityProfile);

        return canonHelper(trustInteroperabilityProfile, this::serializeWithReference);
    }

    @Override
    public byte[] canon(final TrustmarkBindingRegistry trustmarkBindingRegistry) throws IOException {

        requireNonNull(trustmarkBindingRegistry);

        return canonHelper(trustmarkBindingRegistry, this::serialize);
    }

    @Override
    public byte[] canon(final TrustmarkBindingRegistrySystem trustmarkBindingRegistrySystem) throws IOException {

        requireNonNull(trustmarkBindingRegistrySystem);

        return canonHelper(trustmarkBindingRegistrySystem, this::serialize);
    }

    private <T1> byte[] canonHelper(final T1 serializeable, final F2<T1, ByteArrayOutputStream, Validation<NonEmptyList<Exception>, ByteArrayOutputStream>> f) throws IOException {

        final Validation<NonEmptyList<Exception>, byte[]> validation = f.f(serializeable, new ByteArrayOutputStream())
                .map(byteArrayOutputStream -> byteArrayOutputStream.toByteArray());

        if (validation.isSuccess()) {

            return validation.success();

        } else {

            validation.fail().forEach(exception -> exception.printStackTrace());

            throw new IOException(String.join("\n", validation.fail().map(exception -> exception.getMessage()).toList().toJavaList()));
        }
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final TrustmarkDefinition trustmarkDefinition, final T1 outputStream) {

        return Try.<T1, Exception>f(() -> {
            // TODO: Currently, artifacts ids change every time they're requested; when that changes, the setters below can be removed as well.
            final String id = trustmarkDefinition.getId();
            final String originalSource = trustmarkDefinition.getOriginalSource();
            final String originalSourceType = trustmarkDefinition.getOriginalSourceType();

            ((TrustmarkDefinitionImpl) trustmarkDefinition).setId("ID");
            ((TrustmarkDefinitionImpl) trustmarkDefinition).setOriginalSource(null);
            ((TrustmarkDefinitionImpl) trustmarkDefinition).setOriginalSourceType(null);

            serializerXml.serialize(trustmarkDefinition, outputStream);

            ((TrustmarkDefinitionImpl) trustmarkDefinition).setId(id);
            ((TrustmarkDefinitionImpl) trustmarkDefinition).setOriginalSource(originalSource);
            ((TrustmarkDefinitionImpl) trustmarkDefinition).setOriginalSourceType(originalSourceType);

            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final Trustmark trustmark, final T1 outputStream) {

        return Try.<T1, Exception>f(() -> {
            serializerXml.serialize(trustmark, outputStream);
            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final TrustmarkStatusReport trustmarkStatusReport, final T1 outputStream) {

        return Try.<T1, Exception>f(() -> {
            // TODO: Currently, artifacts ids change every time they're requested; when that changes, the setters below can be removed as well.
            final String id = trustmarkStatusReport.getId();
            final String originalSource = trustmarkStatusReport.getOriginalSource();
            final String originalSourceType = trustmarkStatusReport.getOriginalSourceType();

            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setId("ID");
            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setOriginalSource(null);
            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setOriginalSourceType(null);

            serializerXml.serialize(trustmarkStatusReport, outputStream);

            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setId(id);
            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setOriginalSource(originalSource);
            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setOriginalSourceType(originalSourceType);

            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serializeWithReference(final TrustInteroperabilityProfile trustInteroperabilityProfile, final T1 outputStream) {

        return sequenceValidation(nonEmptyListSemigroup(), iterableList(trustInteroperabilityProfile.getReferences())
                .map(abstractTIPReference -> abstractTIPReference.match(
                        trustmarkDefinitionRequirement -> Either.<URI, URI>left(trustmarkDefinitionRequirement.getIdentifier()),
                        trustInteroperabilityProfileReference -> Either.<URI, URI>right(trustInteroperabilityProfileReference.getIdentifier())))
                .sort(eitherOrd(uriOrd, uriOrd))
                .map(either -> reduce(either.bimap(
                        left -> Try.<TrustmarkDefinition, Exception>f(() -> trustmarkDefinitionResolver.resolve(left))._1()
                                .f().map(NonEmptyList::nel)
                                .map(reference -> Either.<TrustmarkDefinition, TrustInteroperabilityProfile>left(reference)),
                        right -> Try.<TrustInteroperabilityProfile, Exception>f(() -> trustInteroperabilityProfileResolver.resolve(right))._1()
                                .f().map(NonEmptyList::nel)
                                .map(reference -> Either.<TrustmarkDefinition, TrustInteroperabilityProfile>right(reference))))))
                .bind(list -> list.foldLeft(
                        (outputStreamValidation, either) -> outputStreamValidation.bind(outputStreamInner -> reduce(either.bimap(
                                left -> serialize(left, outputStreamInner),
                                right -> serializeWithReference(right, outputStreamInner)))),
                        serialize(trustInteroperabilityProfile, outputStream)));
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final T1 outputStream) {

        return Try.<T1, Exception>f(() -> {
            // TODO: Currently, artifacts ids change every time they're requested; when that changes, the setters below can be removed as well.
            final String id = trustInteroperabilityProfile.getId();
            final String originalSource = trustInteroperabilityProfile.getOriginalSource();
            final String originalSourceType = trustInteroperabilityProfile.getOriginalSourceType();

            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setId("ID");
            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setOriginalSource(null);
            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setOriginalSourceType(null);

            serializerXml.serialize(trustInteroperabilityProfile, outputStream);

            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setId(id);
            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setOriginalSource(originalSource);
            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setOriginalSourceType(originalSourceType);

            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final TrustmarkBindingRegistry trustmarkBindingRegistry, final T1 outputStream) {
        return Try.f(() -> {
            outputStream.write(trustmarkBindingRegistry.getOriginalSource().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final TrustmarkBindingRegistrySystem trustmarkBindingRegistrySystem, final T1 outputStream) {
        return Try.f(() -> {
            outputStream.write(trustmarkBindingRegistrySystem.getOriginalSource().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }
}
