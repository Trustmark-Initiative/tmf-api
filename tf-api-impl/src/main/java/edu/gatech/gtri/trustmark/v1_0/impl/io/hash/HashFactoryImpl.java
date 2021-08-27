package edu.gatech.gtri.trustmark.v1_0.impl.io.hash;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkStatusReportImpl;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.io.hash.HashFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.Either.eitherOrd;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.List.sequenceValidation;
import static org.gtri.fj.data.NonEmptyList.nonEmptyListSemigroup;
import static org.gtri.fj.lang.StringUtility.stringOrd;

@MetaInfServices
public final class HashFactoryImpl implements HashFactory {

    private final Ord<URI> uriOrd = ord((uri1, uri2) -> stringOrd.compare(uri1.toString(), uri2.toString()));

    private final MessageDigest digest;
    private final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver;
    private final TrustmarkDefinitionResolver trustmarkDefinitionResolver;
    private final TrustmarkStatusReportResolver trustmarkStatusReportResolver;
    private final TrustmarkResolver trustmarkResolver;
    private final SerializerXml serializerXml;

    public HashFactoryImpl() throws NoSuchAlgorithmException {

        this.digest = MessageDigest.getInstance("SHA-256");
        this.trustInteroperabilityProfileResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
        this.trustmarkDefinitionResolver = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class);
        this.trustmarkStatusReportResolver = FactoryLoader.getInstance(TrustmarkStatusReportResolver.class);
        this.trustmarkResolver = FactoryLoader.getInstance(TrustmarkResolver.class);
        this.serializerXml = new SerializerXml();
    }

    public byte[] hash(final TrustmarkDefinition trustmarkDefinition) throws IOException {

        requireNonNull(trustmarkDefinition);

        return hashHelper(trustmarkDefinition, this::serialize);
    }

    public byte[] hash(final Trustmark trustmark) throws IOException {

        requireNonNull(trustmark);

        return hashHelper(trustmark, this::serialize);
    }

    public byte[] hash(final TrustmarkStatusReport trustmarkStatusReport) throws IOException {

        requireNonNull(trustmarkStatusReport);

        return hashHelper(trustmarkStatusReport, this::serialize);
    }

    public byte[] hash(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException {

        requireNonNull(trustInteroperabilityProfile);

        return hashHelper(trustInteroperabilityProfile, this::serialize);
    }

    @Override
    public byte[] hashWithReference(final TrustInteroperabilityProfile trustInteroperabilityProfile) throws IOException {

        requireNonNull(trustInteroperabilityProfile);

        return hashHelper(trustInteroperabilityProfile, this::serializeWithReference);
    }

    private <T1> byte[] hashHelper(final T1 serializeable, final F2<T1, ByteArrayOutputStream, Validation<NonEmptyList<Exception>, ByteArrayOutputStream>> f) throws IOException {

        final Validation<NonEmptyList<Exception>, byte[]> digestValidation = f.f(serializeable, new ByteArrayOutputStream())
                .map(byteArrayOutputStream -> digest.digest(byteArrayOutputStream.toByteArray()));

        if (digestValidation.isSuccess()) {

            return digestValidation.success();

        } else {

            digestValidation.fail().forEach(exception -> exception.printStackTrace());

            throw new IOException(String.join("\n", digestValidation.fail().map(exception -> exception.getMessage()).toList().toJavaList()));
        }
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final TrustmarkDefinition trustmarkDefinition, final T1 outputStream) {

        return Try.<T1, Exception>f(() -> {
            //
            ((TrustmarkDefinitionImpl) trustmarkDefinition).setId("");
            ((TrustmarkDefinitionImpl) trustmarkDefinition).setOriginalSource(null);
            ((TrustmarkDefinitionImpl) trustmarkDefinition).setOriginalSourceType(null);
            serializerXml.serialize(trustmarkDefinition, outputStream);
            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final Trustmark trustmark, final T1 outputStream) {

        return Try.<T1, Exception>f(() -> {
            ((TrustmarkImpl) trustmark).setId("");
            ((TrustmarkImpl) trustmark).setOriginalSource(null);
            ((TrustmarkImpl) trustmark).setOriginalSourceType(null);
            serializerXml.serialize(trustmark, outputStream);
            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }

    private <T1 extends OutputStream> Validation<NonEmptyList<Exception>, T1> serialize(final TrustmarkStatusReport trustmarkStatusReport, final T1 outputStream) {

        return Try.<T1, Exception>f(() -> {
            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setId("");
            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setOriginalSource(null);
            ((TrustmarkStatusReportImpl) trustmarkStatusReport).setOriginalSourceType(null);
            serializerXml.serialize(trustmarkStatusReport, outputStream);
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
            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setId("");
            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setOriginalSource(null);
            ((TrustInteroperabilityProfileImpl) trustInteroperabilityProfile).setOriginalSourceType(null);
            serializerXml.serialize(trustInteroperabilityProfile, outputStream);
            return outputStream;
        })._1().f().map(NonEmptyList::nel);
    }
}
