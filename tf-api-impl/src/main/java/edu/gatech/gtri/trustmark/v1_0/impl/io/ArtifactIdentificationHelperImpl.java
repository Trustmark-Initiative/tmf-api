package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.*;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by brad on 7/29/16.
 */
public class ArtifactIdentificationHelperImpl implements ArtifactIdentificationHelper {

    private static final Logger log = LoggerFactory.getLogger(ArtifactIdentificationHelperImpl.class);

    /**
     * This particular implementation just tries to run the file through the various resolvers to see if one hits.  There
     * is probably a more efficient way to do this, but it probably takes more code.
     */
    @Override
    public ArtifactIdentification getArtifactIdentification(File file) throws ResolveException {
        TrustmarkResolver tmResolver = FactoryLoader.getInstance(TrustmarkResolver.class);
        try {
            Trustmark trustmark = tmResolver.resolve(file, false);
            return new ArtifactIdentificationImpl(file, ArtifactIdentification.ArtifactType.TRUSTMARK, trustmark.getOriginalSourceType());
        }catch(Exception e){
            log.debug("File["+file.getPath()+"] is not a Trustmark: "+e.toString());
        }

        TrustmarkStatusReportResolver tmStatusReportResolver = FactoryLoader.getInstance(TrustmarkStatusReportResolver.class);
        try {
            TrustmarkStatusReport trustmarkStatusReport = tmStatusReportResolver.resolve(file);
            return new ArtifactIdentificationImpl(file, ArtifactIdentification.ArtifactType.TRUSTMARK_STATUS_REPORT, trustmarkStatusReport.getOriginalSourceType());
        }catch(Exception e){
            log.debug("File["+file.getPath()+"] is not a Trustmark Status Report: "+e.toString());
        }

        TrustInteroperabilityProfileResolver tipResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
        try {
            TrustInteroperabilityProfile tip = tipResolver.resolve(file);
            return new ArtifactIdentificationImpl(file, ArtifactIdentification.ArtifactType.TRUST_INTEROPERABILITY_PROFILE, tip.getOriginalSourceType());
        }catch(Exception e){
            log.debug("File["+file.getPath()+"] is not a Trust Interoperability Profile: "+e.toString());
        }

        TrustmarkDefinitionResolver tdResolver = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class);
        try {
            TrustmarkDefinition td = tdResolver.resolve(file);
            return new ArtifactIdentificationImpl(file, ArtifactIdentification.ArtifactType.TRUSTMARK_DEFINITION, td.getOriginalSourceType());
        }catch(Exception e){
            log.debug("File["+file.getPath()+"] is not a Trustmark Definition: "+e.toString());
        }

        // TODO Any other types to manually try?

        throw new ResolveException("Could not resolve an artifact type for file: "+file.getPath());
    }

    private class ArtifactIdentificationImpl implements ArtifactIdentification {
        private File file;
        private ArtifactType type;
        private String mimeType;

        private ArtifactIdentificationImpl(File file, ArtifactType type, String mimeType){
            this.file = file;
            this.type = type;
            this.mimeType = mimeType;
        }

        @Override
        public File getFile() {
            return this.file;
        }

        @Override
        public ArtifactType getArtifactType() {
            return this.type;
        }

        @Override
        public String getMimeType() {
            return this.mimeType;
        }

        public String toString() {
            return this.type.toString()+"["+this.getMimeType()+"]";
        }
    }


}
