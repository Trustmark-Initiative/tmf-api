package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionMetadataImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadContext;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadListener;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadResult;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReader;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.TrustInteroperabilityProfileUtils;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Reads trustmark artifacts from a collection of XML or JSON files (note - does not read from mixed input to avoid
 * collisions).
 * <br/><br/>
 * @author brad
 * @date 5/3/17
 */
public class XmlJsonBulKReader implements BulkReader {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = Logger.getLogger(XmlJsonBulKReader.class);
    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================
    protected static List<String> collectUniqueExtensions(List<File> files){
        List<String> extensions = new ArrayList<>();
        for( File f : files ){
            String extension = getExtension(f);
            if( extension != null ){
                if( !extensions.contains(extension) ){
                    extensions.add(extension);
                }
            }
        }
        return extensions;
    }

    protected static String getExtension(File f){
        return getExtension(f.getName());
    }
    protected static String getExtension(String filename){
        int lastDotIndex = filename.lastIndexOf('.');
        if( lastDotIndex >= 0 ){
            String extension = filename.substring(lastDotIndex+1);
            if( extension != null && extension.length() > 0 ) {
                return extension.toLowerCase();
            }
        }
        return null;
    }

    protected static int getPercent(int a, int b){
        return (int) Math.floor( (double) ( ((double) a / (double) b) * 100.0d) );
    }
    //==================================================================================================================
    //  Instance Variables
    //==================================================================================================================
    private BulkReadListenerDelegatorImpl bulkReadListenerDelegator = new BulkReadListenerDelegatorImpl();
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================
    @Override
    public void addListener(BulkReadListener listener) {
        bulkReadListenerDelegator.addListener(listener);
    }

    @Override
    public BulkReadResult readBulkFrom(BulkReadContext context, File... inputFiles) throws Exception {
        return readBulkFrom(context, Arrays.asList(inputFiles));
    }

    @Override
    public BulkReadResult readBulkFrom(BulkReadContext context, List<File> inputFiles) throws Exception {
        bulkReadListenerDelegator.start();
        System.out.println("XmlJsonBulkReader.readBulkFrom !");
        List<TrustmarkDefinition> tds = new ArrayList<>();
        List<TrustInteroperabilityProfile> tips = new ArrayList<>();
        List<String> invalidParameters = new ArrayList<>();

        bulkReadListenerDelegator.checkingFiles(inputFiles);
        bulkReadListenerDelegator.setMessage("Reading files...");
        bulkReadListenerDelegator.setPercentage(0);

        for( int i = 0; i < inputFiles.size(); i++ ){
            File file = inputFiles.get(i);
            bulkReadListenerDelegator.setPercentage(getPercent(i, inputFiles.size()));
            bulkReadListenerDelegator.startReadingFile(file);
            readFile(file, tds, tips);
            bulkReadListenerDelegator.finishedReadingFile(file);
        }

        if( tds.size() == 0 && tips.size() == 0 ){
            bulkReadListenerDelegator.errorDuringBulkRead(new Exception("Unable to resolve any valid TDs or TIPs from the list of input files."));
            bulkReadListenerDelegator.finished();
            return null;
        }

        bulkReadListenerDelegator.startProcessingRawTDs();
        if( context != null ) {
            bulkReadListenerDelegator.setMessage("Processing TDs...");
            bulkReadListenerDelegator.setPercentage(0);
            log.info("Context is not null, so we are updating all TDs...");
            for (int tdIndex = 0; tdIndex < tds.size(); tdIndex++ ) {
                TrustmarkDefinition td = tds.get(tdIndex);
                log.debug("TD["+td.getMetadata().getName()+", v"+td.getMetadata().getVersion()+"] is having values overridden using context...");
                bulkReadListenerDelegator.setPercentage(getPercent(tdIndex, tds.size()));

                Map<String, String> monikerAndVersion = getMonikerAndVersion(td.getMetadata().getIdentifier());
                String moniker = monikerAndVersion.get("moniker");
                if( moniker == null )
                    moniker = BulkImportUtils.cleanseMoniker(BulkImportUtils.generateMoniker(td.getMetadata().getName()));

                if( monikerAndVersion.get("version") != null ){
                    if( !monikerAndVersion.get("version").equalsIgnoreCase(td.getMetadata().getVersion()) ){
                        log.error("Found a TD["+td.getMetadata().getIdentifier()+"] where there is a version mismatch in the URI!  ["+monikerAndVersion.get("version")+"] != ["+td.getMetadata().getVersion()+"]");
                    }
                }

                URI identifier = context.generateIdentifierForTrustmarkDefinition(moniker, td.getMetadata().getVersion());
                if( identifier != null ) {
                    log.debug("  -> Setting identifier to "+identifier);
                    ((TrustmarkDefinitionMetadataImpl) td.getMetadata()).setIdentifier(identifier);
                    ((TrustmarkDefinitionMetadataImpl) td.getMetadata()).setTrustmarkReferenceAttributeName(new URI(identifier.toString()+"/trustmark-reference/"));
                }

                if (context.getTrustmarkDefiningOrganization() != null) {
                    log.debug("  -> Setting defining org to: "+context.getTrustmarkDefiningOrganization().getName());
                    ((TrustmarkDefinitionMetadataImpl) td.getMetadata()).setTrustmarkDefiningOrganization(context.getTrustmarkDefiningOrganization());
                }

                // TODO As per Matt decision, we should just overwrite them all.
                ((TrustmarkDefinitionMetadataImpl) td.getMetadata()).setTrustmarkRevocationCriteria(context.getDefaultRevocationCriteria());

                ((TrustmarkDefinitionMetadataImpl) td.getMetadata()).setLegalNotice(context.getDefaultLegalNotice());

                if (StringUtils.isEmpty(td.getMetadata().getNotes())) {
                    ((TrustmarkDefinitionMetadataImpl) td.getMetadata()).setNotes(context.getDefaultNotes());
                }
            }
        }
        bulkReadListenerDelegator.finishedProcessingRawTDs(tds);

        bulkReadListenerDelegator.startProcessingRawTIPs();
        if( context != null ) {
            Map<URI, List<URI>> notLocalUrisByTip = new HashMap<>();
            List<URI> notLocalUris = new ArrayList<>();
            bulkReadListenerDelegator.setMessage("Processing TIPs...");
            bulkReadListenerDelegator.setPercentage(0);
            log.info("Context is not null, so we are updating all TIPs...");
            for (int tipIndex = 0; tipIndex < tips.size(); tipIndex++ ) {
                TrustInteroperabilityProfile tip = tips.get(tipIndex);
                log.debug("TIP["+tip.getName()+", v"+tip.getVersion()+"] is having values overridden using context...");
                bulkReadListenerDelegator.setPercentage(getPercent(tipIndex, tds.size()));

                Map<String, String> monikerAndVersion = getMonikerAndVersion(tip.getIdentifier());
                String moniker = monikerAndVersion.get("moniker");
                if( moniker == null )
                    moniker = BulkImportUtils.cleanseMoniker(BulkImportUtils.generateMoniker(tip.getName()));

                if( monikerAndVersion.get("version") != null ){
                    if( !monikerAndVersion.get("version").equalsIgnoreCase(tip.getVersion()) ){
                        log.error("Found a TIP["+tip.getIdentifier()+"] where there is a version mismatch in the URI!  ["+monikerAndVersion.get("version")+"] != ["+tip.getVersion()+"]");
                    }
                }

                URI identifier = context.generateIdentifierForTrustInteroperabilityProfile(moniker, tip.getVersion());
                if( identifier != null ) {
                    log.debug("  -> Setting identifier to "+identifier);
                    ((TrustInteroperabilityProfileImpl) tip).setIdentifier(identifier);
                }

                if (context.getTrustInteroperabilityProfileIssuer() != null) {
                    log.debug("  -> Setting issuer to "+context.getTrustInteroperabilityProfileIssuer().getName());
                    ((TrustInteroperabilityProfileImpl) tip).setIssuer(context.getTrustInteroperabilityProfileIssuer());
                }

                ((TrustInteroperabilityProfileImpl) tip).setLegalNotice(context.getDefaultLegalNotice());

                if (StringUtils.isEmpty(tip.getNotes())) {
                    ((TrustInteroperabilityProfileImpl) tip).setNotes(context.getDefaultNotes());
                }

            }

            for( int tipIndex = 0; tipIndex < tips.size(); tipIndex++ ){
                TrustInteroperabilityProfile tip = tips.get(tipIndex);
                log.debug("TIP["+tip.getName()+", v"+tip.getVersion()+"] is having references overridden using context...");
                for(AbstractTIPReference reference : tip.getReferences() ){
                    URI refId = reference.getIdentifier();
                    Map<String, String> refMonikerAndVersion = getMonikerAndVersion(refId);
                    URI newRefId = null;
                    if( reference.isTrustmarkDefinitionRequirement() ){
                        log.debug("  -> Reference["+refMonikerAndVersion.get("moniker")+"] is a @|green TD|@");
                        newRefId = context.generateIdentifierForTrustmarkDefinition(refMonikerAndVersion.get("moniker"), refMonikerAndVersion.get("version"));
                    }else{
                        log.debug("  -> Reference["+refMonikerAndVersion.get("moniker")+"] is a @|cyan TIP|@");
                        newRefId = context.generateIdentifierForTrustInteroperabilityProfile(refMonikerAndVersion.get("moniker"), refMonikerAndVersion.get("version"));
                    }

                    if( isLocal(newRefId, tds, tips) ) {
                        log.debug("  -> Rewriting URI[" + refId + "] in Reference to [" + newRefId + "]");
                        ((TrustmarkFrameworkIdentifiedObjectImpl) reference).setIdentifier(newRefId);
                    }else{
                        log.warn("   -> Cannot find URI["+refId+"] in local set [localref="+newRefId+"]!");
                        List<URI> notLocalUrisForThisTIP = notLocalUrisByTip.get(tip.getIdentifier());
                        if( notLocalUrisForThisTIP == null )
                            notLocalUrisForThisTIP = new ArrayList<>();
                        notLocalUrisForThisTIP.add(refId);
                        notLocalUrisByTip.put(tip.getIdentifier(), notLocalUrisForThisTIP);
                        if( !notLocalUris.contains(refId) )
                            notLocalUris.add(refId);

                        log.error("TIP["+tip.getIdentifier().toString()+"] contains remote reference: @|yellow "+refId+"|@");
                    }

                }
            }

            if( notLocalUrisByTip.size() > 0 ) {
                log.info("Found @|green "+notLocalUris.size()+"|@ Remote URIs:");
                for( URI containingTipUri : notLocalUrisByTip.keySet() ){
                    List<URI> notLocalUrisForThisTIP = notLocalUrisByTip.get(containingTipUri);
                    log.info("  -> Containing TIP[@|cyan "+containingTipUri+"|@] has @|green "+notLocalUrisForThisTIP.size()+"|@ remote references: ");
                    for( URI uri : notLocalUrisForThisTIP ) {
                        log.info("    -> URI: @|yellow " + uri + "|@");
                    }
                }
            }
        }
        bulkReadListenerDelegator.setPercentage(-1);
        bulkReadListenerDelegator.finishedProcessingRawTIPs(tips);

        bulkReadListenerDelegator.setMessage("Finished processing "+tds.size()+" trustmark definitions and "+tips.size()+" trust interop profiles.");
        bulkReadListenerDelegator.finished();
        return new BulkReadResultImpl(tds, tips, invalidParameters);
    }

    private boolean isLocal(URI uri, List<TrustmarkDefinition> tds, List<TrustInteroperabilityProfile> tips){
        for( int i = 0; i < tds.size(); i++ ){
            TrustmarkDefinition td = tds.get(i);
            if( td.getMetadata().getIdentifier().equals(uri) )
                return true;
        }
        for( int i = 0; i < tips.size(); i++ ){
            TrustInteroperabilityProfile tip = tips.get(i);
            if( tip.getIdentifier().equals(uri) )
                return true;
        }
        return false;
    }

    @Override
    public Boolean supports(File... inputFiles) {
        return supports(Arrays.asList(inputFiles));
    }

    @Override
    public Boolean supports(List<File> inputFiles) {
        List<String> extensions = collectUniqueExtensions(inputFiles);
        if( extensions.size() > 1 ){
            log.info("There is more than 1 extension used by these files, so they are @|yellow not supported|@ for import by "+this.getClass().getSimpleName()+".  You must have all files be either JSON or XML.");
            return false;
        }

        if( extensions.get(0).equalsIgnoreCase("xml") || extensions.get(0).equalsIgnoreCase("json") ){
            log.info("@|green Supported|@!  This collection of files is exclusively @|cyan "+extensions.get(0)+"|@, so "+this.getClass().getSimpleName()+" can import them.");
            return true;
        }

        log.info("These files are @|yellow not supported|@ because extension @|red "+extensions.get(0)+"|@ is not something "+this.getClass().getSimpleName()+" knows how to import.");
        return false;
    }

    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================

    public static final String URI_MARKER_FOR_TDS = "/trustmark-definitions/";
    public static final String URI_MARKER_FOR_TIPS = "/trust-interoperability-profiles/";

    /**
     * Takes a TMI Formatted URI, and pulls out the moniker and the version, returning them in a Map.
     */
    private Map<String, String> getMonikerAndVersion(URI uri){
        Map<String, String> data = new HashMap<>();

        String uriStr = uri.toString();
        if( uriStr.contains(URI_MARKER_FOR_TDS) ){
            populateMoniker(uriStr, URI_MARKER_FOR_TDS, data);
        }else if( uriStr.contains(URI_MARKER_FOR_TIPS) ){
            populateMoniker(uriStr, URI_MARKER_FOR_TIPS, data);
        }else{
            log.warn("Cannot find any specialized identifier string (such as trustmark-definitions) in the given URI["+uriStr+"], so moniker and version cannot be parsed.");
        }

        return data;
    }

    private void populateMoniker(String uriStr, String token, Map<String, String> data){
        Integer tdIndex = uriStr.indexOf(token);
        String endString = uriStr.substring(tdIndex+token.length());
        if( endString.endsWith("/") )
            endString = endString.substring(0, endString.length() - 1);

        String[] parts = endString.split(Pattern.quote("/"));
        if( parts.length != 2 ){
            log.error("Invalid URI["+uriStr+"], cannot parse moniker and version from it!");
        }else {
            data.put("moniker", parts[0]);
            data.put("version", parts[1]);
            log.debug("From URI["+uriStr+"], Found Moniker = ["+parts[0]+"] and Version = ["+parts[1]+"]");
        }
    }


    private void readFile(File file, List<TrustmarkDefinition> tds, List<TrustInteroperabilityProfile> tips){
        if( isTrustmarkDefinition(file) ){
            try{
                TrustmarkDefinition td = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class).resolve(file);
                tds.add(td);
                log.debug("Successfully Resolved File[@|cyan "+file.getName()+"|@] to a @|green TD|@!");
            }catch(ResolveException re){
                log.error("Error reading File["+file.getName()+"] as a TD!", re);
                bulkReadListenerDelegator.fileNotSupported(file, new Exception("File["+file.getName()+"] was recognized as a Trustmark Definition, but cannot be parsed!", re));
            }
        }else if( isTrustProfile(file) ){
            try{
                TrustInteroperabilityProfile trustProfile = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class).resolve(file);
                tips.add(trustProfile);
                log.debug("Successfully Resolved File[@|cyan "+file.getName()+"|@] to a @|yellow TIP|@!");
            }catch(ResolveException re){
                log.error("Error reading File["+file.getName()+"] as a TIP!", re);
                bulkReadListenerDelegator.fileNotSupported(file, new Exception("File["+file.getName()+"] was recognized as a TrustInteroperabilityProfile, but cannot be parsed!", re));
            }
        }else{
            log.error("Error!  File @|red "+file.getName()+"|@ is not a TD nor is it a TIP!");
            bulkReadListenerDelegator.fileNotSupported(file, new Exception("File["+file.getName()+"] is neither a Trustmark Definition nor a Trust Interop Profile."));
        }
    }

    public static boolean isTrustmarkDefinition(File file){
        return FactoryLoader.getInstance(TrustmarkDefinitionUtils.class).isTrustmarkDefinition(file);
    }

    public static boolean isTrustProfile(File file){
        return FactoryLoader.getInstance(TrustInteroperabilityProfileUtils.class).isTrustInteroperabilityProfile(file);
    }

}/* end XmlJsonBulKReader */