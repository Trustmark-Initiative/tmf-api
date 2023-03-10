package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.SessionResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.TipTreeNode;
import edu.gatech.gtri.trustmark.v1_0.util.TrustInteroperabilityProfileUtils;
import edu.gatech.gtri.trustmark.v1_0.util.TrustInteroperabilityProfileValidator;
import edu.gatech.gtri.trustmark.v1_0.util.UrlUtils;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffResult;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.json.JSONObject;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.String.format;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

/**
 * A simple implementation of TrustInteroperabilityProfileUtils.  Do not look under the covers of this beast, it is nasty.
 * Implements TrustmarkDefinitionComparator as a delegator to an underlying implementation.
 * <br/><br/>
 * Created by brad on 4/12/16.
 */
public class TrustInteroperabilityProfileUtilsImpl implements TrustInteroperabilityProfileUtils {

    private static final Logger log = LoggerFactory.getLogger(TrustInteroperabilityProfileUtilsImpl.class);

    Comparator<AbstractTIPReference> orderByNumber = (AbstractTIPReference r1, AbstractTIPReference r2) -> {
        if (r1.getNumber() != null && r2.getNumber() != null) {
            return r1.getNumber().compareTo(r2.getNumber());
        } else {
            return 0;
        }
    };

    @Override
    public Boolean isTrustInteroperabilityProfile(File file) {
        log.debug("Identifying if File[@|cyan " + file.getName() + "|@] is a TIP...");
        try {
            try {
                log.debug("Trying to read the first XML StartElement event...");
                XMLEventReader xmlEventReader = XMLInputFactory.newFactory().createXMLEventReader(new FileInputStream(file));
                while (xmlEventReader.hasNext()) {
                    XMLEvent event = xmlEventReader.nextEvent();
                    if (event.isStartElement()) {
                        StartElement se = (StartElement) event;
                        log.debug("Successfully found start element[" + se.getName() + "]");
                        if (se.getName().getLocalPart().equalsIgnoreCase("TrustInteroperabilityProfile")) {
                            log.debug("Found TrustInteroperabilityProfile!");
                            return true;
                        }
                        break;
                    }
                }
                log.debug("File[" + file.getName() + "] is not a TIP, since we got the first StartElement but it wasn't TrustInteroperabilityProfile!");
                return false; // Since we successfully parsed XML, but first element was NOT TrustInteroperabilityProfile, this is not a TIP.
            } catch (XMLStreamException xmlstreame) {
                // TODO HANDLE ERROR
                // In this case, the file must not be XML.  I don't think we really care.
                log.debug("Received error during XML parse, not XML!");
            }

            log.debug("Reading file[" + file.getName() + "] looking for $type...");
            byte[] bytes = Files.readAllBytes(file.toPath());
            String json = new String(bytes, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(json);
            String typeKey = jsonObject.keySet().stream().filter(ATTRIBUTE_KEY_JSON_TYPE::equalsIgnoreCase).findFirst().orElse(null);
            if (typeKey != null) {
                String typeValue = jsonObject.optString(typeKey, "");
                log.debug("Found $type, next double quoted value is: " + typeValue);
                return "TrustInteroperabilityProfile".equalsIgnoreCase(typeValue);
            }

            return false; // neither JSON or XML
        } catch (IOException ioe) {
            log.error("Error Reading File[" + file + "]!  Cannot determine if it is TIP.", ioe);
            // Since we had an error reading the file, it must not be anything of value!
            return false;
        }
    }

    @Override
    public Collection<ValidationResult> validate(TrustInteroperabilityProfile tip) {
        ArrayList<ValidationResult> results = new ArrayList<>();
        log.debug(format("Performing TIP[%s] validation...", tip.getIdentifier()));

        boolean executed = false;
        ServiceLoader<TrustInteroperabilityProfileValidator> validatorLoader = ServiceLoader.load(TrustInteroperabilityProfileValidator.class);
        Iterator<TrustInteroperabilityProfileValidator> validatorIterator = validatorLoader.iterator();
        while (validatorIterator.hasNext()) {
            TrustInteroperabilityProfileValidator validator = validatorIterator.next();
            try {
                executed = true;
                log.debug("Executing TIPValidator[" + validator.getClass().getName() + "]...");
                Collection<ValidationResult> currentResults = validator.validate(tip);
                log.debug(format("TIPValidator[%s] had %d results.", validator.getClass().getName(), currentResults.size()));
                results.addAll(currentResults);
            } catch (Throwable t) {
                log.error("Error validating TIP with validator: " + validator.getClass().getName(), t);
                results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Unexpected error executing validator[" + validator.getClass().getSimpleName() + "]: " + t.getMessage()));
            }
        }

        if (!executed)
            results.add(new ValidationResultImpl(ValidationSeverity.WARNING, "There are no TrustInteroperabilityProfileValidators defined in the system."));

        if (results.size() > 0) {
            log.debug(format("Validating TIP[%s] results in %d results:", tip.getIdentifier(), results.size()));
            for (ValidationResult result : results) {
                log.debug("  " + result.toString());
            }
        } else {
            log.debug(format("Validating TIP[%s] looks clean!", tip.getIdentifier()));
        }
        return results;
    }

    @Override
    public Collection diff(TrustInteroperabilityProfile tip1, TrustInteroperabilityProfile tip2) {
        log.debug(format("Finding the difference of TIP1[%s] and TIP2[%s]...", tip1.getIdentifier().toString(), tip2.getIdentifier().toString()));
        ArrayList<TrustInteroperabilityProfileDiffResult> results = new ArrayList<>();

        ServiceLoader<TrustInteroperabilityProfileDiff> loader = ServiceLoader.load(TrustInteroperabilityProfileDiff.class);
        Iterator<TrustInteroperabilityProfileDiff> tipDiffIterator = loader.iterator();
        while (tipDiffIterator.hasNext()) {
            TrustInteroperabilityProfileDiff diffEngine = tipDiffIterator.next();
            try {
                log.debug("Executing " + diffEngine.getClass().getName() + ".doDiff('" + tip1.getIdentifier() + "', '" + tip2.getIdentifier() + "')...");
                Collection<TrustInteroperabilityProfileDiffResult> currentResults = diffEngine.doDiff(tip1, tip2);
                log.debug(format("Executing TIP Difference[%s] results in %d differences.", diffEngine.getClass().getName(), currentResults.size()));
                results.addAll(currentResults);
            } catch (Throwable t) {
                log.error("Error executing TrustInteroperabilityProfileDiff[" + diffEngine.getClass().getName() + "]", t);
            }
        }
        if (log.isDebugEnabled()) {
            String differences = debugDifferences(results);
            log.debug(format("Calculated %d differences: \n%s", results.size(), differences));
        }
        return results;
    }//end diff()

    @Override
    public TipTreeNode buildTipTree(URI uri) throws ResolveException {
        ArrayList<String> ancestors = new ArrayList<>();
        Map<String, TipTreeNode> thingsById = new HashMap<>();
        return _buildTipTreeHelper(uri, ancestors, thingsById);
//        return buildParentTipTree(uri);
    }

    /**
     * The recursive part of the TIP tree algorithm, which detects cycles and only downloads things when needed.
     */
    private TipTreeNode _buildTipTreeHelper(URI url, List<String> ancestorIdentifiers, Map<String, TipTreeNode> downloadedThings)
            throws ResolveException {
        URI toDownload = null;
        try {
            toDownload = UrlUtils.ensureFormatParameter(url, "json");
        } catch (Exception e) {
            log.error("Error ensuring format json!", e);
            throw new ResolveException("Unable to build URL with JSON parameter!", e);
        }
        TrustInteroperabilityProfile tip = null;
        try {
            tip = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class).resolve(toDownload);
        } catch (Throwable t) {
            log.warn("Error resolving TIP: " + toDownload, t);
            TipTreeNodeErrorImpl errorNode = new TipTreeNodeErrorImpl(t, toDownload);
            return errorNode;
        }

        Collections.sort((List<AbstractTIPReference>) tip.getReferences(), orderByNumber);

        TipTreeNodeImpl tipTreeNode = new TipTreeNodeImpl(tip);
        List<String> addedAncestor = new ArrayList(ancestorIdentifiers);
        addedAncestor.add(tip.getIdentifier().toString());
        downloadedThings.put(tip.getIdentifier().toString(), tipTreeNode);

        for (AbstractTIPReference tipref : tip.getReferences()) {
            if (ancestorIdentifiers.contains(tipref.getIdentifier().toString())) {
                log.error("Detected cycle in TIP identifier: " + tipref.getIdentifier());
                throw new ResolveException("TIP Cycle detected for Identifier: " + tipref.getIdentifier());
            }

            if (downloadedThings.containsKey(tipref.getIdentifier().toString())) {
                tipTreeNode.addChild(downloadedThings.get(tipref.getIdentifier().toString()));
                continue;
            }

            if (tipref.isTrustInteroperabilityProfileReference()) {
                TipTreeNode subTipTree = _buildTipTreeHelper(tipref.getIdentifier(), addedAncestor, downloadedThings);
                tipTreeNode.addChild(subTipTree);
            } else if (tipref.isTrustmarkDefinitionRequirement()) {
                URI tdUri = null;
                try {
                    tdUri = UrlUtils.ensureFormatParameter(tipref.getIdentifier(), "json");
                } catch (Exception e) {
                    log.error("Error ensuring format json!", e);
                    throw new ResolveException("Unable to build URL with JSON parameter!", e);
                }

                TipTreeNode tdNode = null;
                TrustmarkDefinition td = null;
                try {
                    td = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class).resolve(tdUri);
                    tdNode = new TipTreeNodeImpl(td);
                    downloadedThings.put(tipref.getIdentifier().toString(), tdNode);
                } catch (Throwable t) {
                    log.warn("Error resolving TD: " + tdUri, t);
                    tdNode = new TipTreeNodeErrorImpl(t, tdUri);
                }
                tipTreeNode.addChild(tdNode);

            } else {
                throw new ResolveException("Cannot determine type of object at: " + url.toString());
            }
        }

        return tipTreeNode;
    }

    /**
     * takes a URI for a TIP and builds out the underlying tree, another nasty beast
     *
     * @param uri
     * @return
     * @throws ResolveException
     * @Override
     */

    public TipTreeNode buildParentTipTree(URI uri) throws ResolveException {
        List<URI> ancestors = new ArrayList<>();
        Map<URI, TipTreeNode> priorDownloads = new HashMap<>();

        TipTreeNodeImpl tipTreeNode = new TipTreeNodeImpl();
        try {
            TrustInteroperabilityProfile tip = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class).resolve(UrlUtils.ensureFormatParameter(uri, "json"));

            tipTreeNode.setTrustInteropProfile(tip);    //  set the head TIP of the tree

            buildTipTreeHelper(tip, tipTreeNode, ancestors, priorDownloads);   // get the rest of the tree

        } catch (MalformedURLException | URISyntaxException ee) {
            log.warn("Error resolving TIP: " + uri.toString(), ee);
            return new TipTreeNodeErrorImpl(ee, uri);
        }
        return tipTreeNode;
    }

    /**
     * retrieve the TIPs and TDs associated with the TIP argument
     * in a threaded fashion as well as recursive
     *
     * @param tip
     * @param tipTreeNode
     * @param ancestors
     * @param priorDownloads
     * @return
     * @throws ResolveException
     */
    private TipTreeNode buildTipTreeHelper(TrustInteroperabilityProfile tip, TipTreeNodeImpl tipTreeNode, List<URI> ancestors, Map<URI, TipTreeNode> priorDownloads) {
        Map<URI, Future<TrustInteroperabilityProfile>> tipFutures = new LinkedHashMap<>();      // preserve order of insertion
        Map<URI, Future<TrustmarkDefinition>> tdFutures = new LinkedHashMap<>();                // preserve order of insertion

        ancestors.add(tip.getIdentifier());  // checks for cyclic redundencies in the TIP

        priorDownloads.put(tip.getIdentifier(), tipTreeNode);  // ensures we don't download the same thing twice

        ((List<AbstractTIPReference>) tip.getReferences()).sort(orderByNumber);  // sort references by number

        final String sessionId = SessionResolver.getSessionResolver().getSessionId();  // preserves the session in the newly created threads

        tip.getReferences().forEach(t -> {      // spin through the references retrieving the ones we don't already have

            if (!ancestors.contains(t.getIdentifier())) {       // check that a child doesn't reference it's parent in an endless cycle

                if (!priorDownloads.containsKey(t.getIdentifier())) {   // check that we haven't downloaded already

                    if (t.isTrustInteroperabilityProfileReference()) {  //  is it a TIP?
                        tipFutures.put(t.getIdentifier(), ThreadUtils.submit(() -> {
                                    SessionResolver.setSessionResolver(sessionId);
                                    return FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class).resolve(UrlUtils.ensureFormatParameter(t.getIdentifier(), "json"));
                                }
                        ));
                    } else if (t.isTrustmarkDefinitionRequirement()) {      // is it a TD?
                        tdFutures.put(t.getIdentifier(), ThreadUtils.submit(() -> {
                            SessionResolver.setSessionResolver(sessionId);
                            return FactoryLoader.getInstance(TrustmarkDefinitionResolver.class).resolve(UrlUtils.ensureFormatParameter(t.getIdentifier(), "json"));
                        }));
                    } else {        // don't know what it is, shouldn't ever get here
                        log.error("THREAD! Cannot determine type of object at: %s" + t.getIdentifier().toString());
                    }
                } else {
                    tipTreeNode.addChild(priorDownloads.get(t.getIdentifier()));   //  already got this one, no need to download
                }
            } else {
                log.error("THREAD! Detected cycle in TIP identifier: %s" + t.getIdentifier().toString());   // bad news
            }
        });

        tdFutures.forEach((k, v) -> {  // load the TDs
            try {
                TipTreeNodeImpl ttn = new TipTreeNodeImpl(v.get());
                tipTreeNode.addChild(ttn);
            } catch (InterruptedException | ExecutionException ee) {
                log.error("THREAD! Error resolving TD: %s" + k.toString());
                tipTreeNode.addChild(new TipTreeNodeErrorImpl(ee, k));
            }
        });

        tipFutures.forEach((k, v) -> {    // load the TIPs and recurse on them
            try {
                TrustInteroperabilityProfile tprof = v.get();
                TipTreeNodeImpl ttn = new TipTreeNodeImpl(tprof);
                tipTreeNode.addChild(ttn);
                buildTipTreeHelper(tprof, ttn, ancestors, priorDownloads);  //  retrieve tips and tds for child TIP
            } catch (InterruptedException | ExecutionException ee) {
                log.error("THREAD! Error retrieving TIP: " + k.toString());
                tipTreeNode.addChild(new TipTreeNodeErrorImpl(ee, k));
            }
        });

        return tipTreeNode;
    }

    private String debugDifferences(ArrayList<TrustInteroperabilityProfileDiffResult> results) {
        StringBuilder builder = new StringBuilder();
        if (results != null && results.size() > 0) {
            for (int i = 0; i < results.size(); i++) {
                TrustInteroperabilityProfileDiffResult result = results.get(i);
                if (result == null)
                    log.error("ERROR: Unexpected NULL result object in list at position '" + i + "'!");
                builder.append("    ").append("[").append(result.getDiffType());
                builder.append(":").append(result.getSeverity()).append("] ");
                builder.append("{").append(result.getLocation()).append("} - ").append(result.getDescription());
                if (i < (results.size() - 1)) {
                    builder.append("\n");
                }
            }
        } else {
            builder.append("   <NONE FOUND>");
        }
        return builder.toString();
    }

}//end TrustmarkDefinitionUtilsImpl
