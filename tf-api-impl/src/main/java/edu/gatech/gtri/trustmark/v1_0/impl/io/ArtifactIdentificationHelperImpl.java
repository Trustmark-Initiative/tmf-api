package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkStatusReportJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkStatusReportXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.ArtifactIdentification;
import edu.gatech.gtri.trustmark.v1_0.io.ArtifactIdentification.ArtifactType;
import edu.gatech.gtri.trustmark.v1_0.io.ArtifactIdentificationHelper;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class ArtifactIdentificationHelperImpl implements ArtifactIdentificationHelper {

    private static final Logger log = LoggerFactory.getLogger(ArtifactIdentificationHelperImpl.class);

    private static final TrustInteroperabilityProfileJsonDeserializer trustInteroperabilityProfileJsonDeserializer = new TrustInteroperabilityProfileJsonDeserializer(true);
    private static final TrustInteroperabilityProfileXmlDeserializer trustInteroperabilityProfileXmlDeserializer = new TrustInteroperabilityProfileXmlDeserializer(true);

    private static final TrustmarkJsonDeserializer trustmarkJsonDeserializer = new TrustmarkJsonDeserializer();
    private static final TrustmarkXmlDeserializer trustmarkXmlDeserializer = new TrustmarkXmlDeserializer();

    private static final TrustmarkStatusReportJsonDeserializer trustmarkStatusReportJsonDeserializer = new TrustmarkStatusReportJsonDeserializer();
    private static final TrustmarkStatusReportXmlDeserializer trustmarkStatusReportXmlDeserializer = new TrustmarkStatusReportXmlDeserializer();

    private static final TrustmarkDefinitionJsonDeserializer trustmarkDefinitionJsonDeserializer = new TrustmarkDefinitionJsonDeserializer(true);
    private static final TrustmarkDefinitionXmlDeserializer trustmarkDefinitionXmlDeserializer = new TrustmarkDefinitionXmlDeserializer(true);

    @Override
    public ArtifactIdentification getArtifactIdentification(final File file) throws ResolveException {

        try {
            final StringWriter stringWriter = new StringWriter();
            IOUtils.copy(new FileReader(file), stringWriter);
            final String string = stringWriter.toString();

            if (AbstractResolverUtility.isXml(string)) {
                try {
                    trustInteroperabilityProfileXmlDeserializer.deserialize(string, null);
                    return new ArtifactIdentificationImpl(
                            file,
                            ArtifactType.TRUST_INTEROPERABILITY_PROFILE,
                            MediaType.TEXT_XML.getMediaType());
                } catch (final ParseException parseException) {
                    log.debug(parseException.toString());
                }

                try {
                    trustmarkXmlDeserializer.deserialize(string, null);
                    return new ArtifactIdentificationImpl(
                            file,
                            ArtifactType.TRUSTMARK,
                            MediaType.TEXT_XML.getMediaType());
                } catch (final ParseException parseException) {
                    log.debug(parseException.toString());
                }

                try {
                    trustmarkStatusReportXmlDeserializer.deserialize(string, null);
                    return new ArtifactIdentificationImpl(
                            file,
                            ArtifactType.TRUSTMARK_STATUS_REPORT,
                            MediaType.TEXT_XML.getMediaType());
                } catch (final ParseException parseException) {
                    log.debug(parseException.toString());
                }

                try {
                    trustmarkDefinitionXmlDeserializer.deserialize(string, null);
                    return new ArtifactIdentificationImpl(
                            file,
                            ArtifactType.TRUSTMARK_DEFINITION,
                            MediaType.TEXT_XML.getMediaType());
                } catch (final ParseException parseException) {
                    log.debug(parseException.toString());
                }
            } else if (AbstractResolverUtility.isJson(string)) {

                try {
                    trustInteroperabilityProfileJsonDeserializer.deserialize(string, null);
                    return new ArtifactIdentificationImpl(
                            file,
                            ArtifactType.TRUST_INTEROPERABILITY_PROFILE,
                            MediaType.APPLICATION_JSON.getMediaType());
                } catch (final ParseException parseException) {
                    log.debug(parseException.toString());
                }

                try {
                    trustmarkJsonDeserializer.deserialize(string, null);
                    return new ArtifactIdentificationImpl(
                            file,
                            ArtifactType.TRUSTMARK,
                            MediaType.APPLICATION_JSON.getMediaType());
                } catch (final ParseException parseException) {
                    log.debug(parseException.toString());
                }

                try {
                    trustmarkStatusReportJsonDeserializer.deserialize(string, null);
                    return new ArtifactIdentificationImpl(
                            file,
                            ArtifactType.TRUSTMARK_STATUS_REPORT,
                            MediaType.APPLICATION_JSON.getMediaType());
                } catch (final ParseException parseException) {
                    log.debug(parseException.toString());
                }

                try {
                    trustmarkDefinitionJsonDeserializer.deserialize(string, null);
                    return new ArtifactIdentificationImpl(
                            file,
                            ArtifactType.TRUSTMARK_DEFINITION,
                            MediaType.APPLICATION_JSON.getMediaType());
                } catch (final ParseException parseException) {
                    log.debug(parseException.toString());
                }
            }
        } catch (final FileNotFoundException fileNotFoundException) {
            throw new ResolveException(fileNotFoundException);
        } catch (final IOException ioException) {
            throw new ResolveException(ioException);
        }

        throw new ResolveException(format("The system could not identify the type of the file: '%s'.", file.getAbsolutePath()));
    }

    private class ArtifactIdentificationImpl implements ArtifactIdentification {

        private final File file;
        private final ArtifactType artifactType;
        private final String mimeType;

        public ArtifactIdentificationImpl(
                final File file,
                final ArtifactType artifactType,
                final String mimeType) {

            requireNonNull(file);
            requireNonNull(artifactType);
            requireNonNull(mimeType);

            this.file = file;
            this.artifactType = artifactType;
            this.mimeType = mimeType;
        }

        @Override
        public File getFile() {
            return file;
        }

        public ArtifactType getArtifactType() {
            return artifactType;
        }

        @Override
        public String getMimeType() {
            return mimeType;
        }

        @Override
        public String toString() {
            return "ArtifactIdentificationImpl{" +
                    "file=" + file +
                    ", type=" + artifactType +
                    ", mimeType='" + mimeType + '\'' +
                    '}';
        }
    }
}
