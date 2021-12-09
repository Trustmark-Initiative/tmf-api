package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileSyntaxException;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ArtifactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.AssessmentStepImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.CitationImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ConformanceCriterionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.SourceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TermImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileReferenceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionMetadataImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadContext;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionStringParser;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionStringParserFactory;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Nicholas on 9/6/2016.
 */
public final class BulkReadRawData {

    //////////////////////
    // Static Constants //
    //////////////////////

    private static final Logger logger = LogManager.getLogger(BulkReadRawData.class);
    private static final String XML_NAMESPACE_TF_NAME = TrustmarkFrameworkConstants.NAMESPACE_PREFIX;
    private static final String XML_NAMESPACE_TF_VALUE = TrustmarkFrameworkConstants.NAMESPACE_URI;
    private static final String XML_TAG_NAME_TD = "TrustmarkDefinition";
    private static final String XML_TAG_NAME_TIP = "TrustInteroperabilityProfile";
    private static final String XML_EMPTY_TD = String.format("<%1$s:%3$s xmlns:%1$s=\"%2$s\"></%1$s:%3$s>", XML_NAMESPACE_TF_NAME, XML_NAMESPACE_TF_VALUE, XML_TAG_NAME_TD);
    private static final String XML_EMPTY_TIP = String.format("<%1$s:%3$s xmlns:%1$s=\"%2$s\"></%1$s:%3$s>", XML_NAMESPACE_TF_NAME, XML_NAMESPACE_TF_VALUE, XML_TAG_NAME_TIP);
    public static final Pattern TIP_REFERENCE_PATTERN = Pattern.compile("TIP\\s*\\{\\s*(.*?)\\s*\\}", Pattern.DOTALL + Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
    public static final Pattern TD_REFERENCE_PATTERN = Pattern.compile("TD\\s*\\{\\s*(.*?)\\s*\\}", Pattern.DOTALL + Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
    public static final Pattern STEP_REFERENCE_PATTERN = Pattern.compile("\\{([^\\}]*)\\}", Pattern.DOTALL | Pattern.MULTILINE);
    public static final Pattern TE_VAR_BLACKLIST_PATTERN = Pattern.compile("[^A-Za-z0-9]");
    public static final int TE_VAR_MAX_LENGTH = Integer.MAX_VALUE; //was 30
    private static final String TRUST_SPLIT_REFERENCE = "}([^{]*)AND\\s*|}([^{]*)and\\s*|}([^{]*)OR\\s*|}([^{]*)or\\s*";
    private static final String TRUST_SPLIT_REFERENCE_WITH_PARMS = "\\s+AND\\s+|\\s*and\\s+|\\s+OR\\s+|\\s+or\\s+";
    private static final String TD_PARAMETER = "}\\.";
    private static final String TD_OPERATOR = ",|<|>|!=|=";


    private static final TrustExpressionStringParserFactory trustExpressionStringParserFactory = FactoryLoader.getInstance(TrustExpressionStringParserFactory.class);
    private static final TrustExpressionStringParser trustExpressionStringParser = trustExpressionStringParserFactory.createDefaultParser();

    ////////////////////////
    // Instance Constants //
    ////////////////////////

    public final BulkReadContext context;


    //////////////////////////////////////////////
    // Instance Fields/Properties - Parse Flags //
    //////////////////////////////////////////////

    private boolean parsedTdsAreValid = false;
    private boolean parsedTipsAreValid = false;

    private boolean isTransientDataCollectionEnabled = false;

    public boolean getIsTransientDataCollectionEnabled() {
        return this.isTransientDataCollectionEnabled;
    }

    public void setIsTransientDataCollectionEnabled(boolean value) {
        this.isTransientDataCollectionEnabled = value;
    }


    ///////////////////////////////////
    // Instance Fields - Cached Data //
    ///////////////////////////////////

    private final Map<String, Element> cachedTdsByUri = new HashMap<>();
    private final Map<String, String> cachedSources = new HashMap<>();


    /////////////////////////////////////
    // Instance Properties - Listeners //
    /////////////////////////////////////

    private BulkReadListenerCollection listenerCollection;

    public void setListenerCollection(BulkReadListenerCollection _listenerCollection) {
        this.listenerCollection = _listenerCollection;
    }


    ////////////////////////////////////
    // Instance Properties - Raw Data //
    ////////////////////////////////////

    private final MultiValuedMap<String, TermImpl> rawTermsByFileName = new ArrayListValuedHashMap<>();

    public void addTerms(String fileName, List<TermImpl> _terms) {
        this.makeParseInvalid();
        this.rawTermsByFileName.putAll(fileName, _terms);
    }

    private final List<RawTrustmarkDefinition> rawTds = new ArrayList<>();

    public void addRawTds(List<RawTrustmarkDefinition> _rawTds) {
        this.makeParseInvalid();
        this.rawTds.addAll(_rawTds);
    }

    private final List<RawTrustInteroperabilityProfile> rawTips = new ArrayList<>();

    public void addRawTips(List<RawTrustInteroperabilityProfile> _rawTips) {
        this.makeParseInvalid();
        this.rawTips.addAll(_rawTips);
    }


    ////////////////////////////////////////////
    // Instance Properties - Parsed Artifacts //
    ////////////////////////////////////////////

    private List<TrustmarkDefinition> parsedTds = null;

    public List<TrustmarkDefinition> getParsedTds() throws Exception {
        this.ensureRawTdsAreParsed();
        return Collections.unmodifiableList(this.parsedTds);
    }

    private List<TrustInteroperabilityProfile> parsedTips = null;

    public List<TrustInteroperabilityProfile> getParsedTips() throws Exception {
        this.ensureRawTipsAreParsed();
        return Collections.unmodifiableList(this.parsedTips);
    }

    private List<String> invalidParameters = new ArrayList<>();

    public List<String> getInvalidParameters() throws Exception {
        return Collections.unmodifiableList(this.invalidParameters);
    }

    /////////////////
    // Constructor //
    /////////////////

    public BulkReadRawData(BulkReadContext _context) {
        this.context = _context;
    }


    ///////////////////////////////
    // Instance Methods - Public //
    ///////////////////////////////

    public void makeParseInvalid() {
        this.parsedTdsAreValid = false;
        this.parsedTipsAreValid = false;
        this.parsedTds = null;
        this.parsedTips = null;
    }

    public void ensureRawTdsAreParsed() throws Exception {
        if (!this.parsedTdsAreValid) {
            this.parseRawTds();
            this.parsedTdsAreValid = true;
        }
    }

    public void ensureRawTipsAreParsed() throws Exception {
        if (!this.parsedTipsAreValid) {
            this.parseRawTips();
            this.parsedTipsAreValid = true;
        }
    }


    //////////////////////////////////////////
    // Instance Methods - Private - Lookups //
    //////////////////////////////////////////

    /**
     * Given the URI identifier for a TD (currently assumed to be a URL), this method will pull the value from the web
     * and cache the XML of it.
     */
    private Element getRemoteArtifact(String uri, RawArtifact artifact) throws Exception {
        Element result = this.cachedTdsByUri.get(uri); //FIXME
        if (result == null) {
            logger.debug("Downloading " + artifact.getArtifactAbbr() + " XML for URI[" + uri + "]...");
            String xmlUri = uri + (uri.contains("?") ? "&" : "?") + "format=xml";
            try {
                SAXReader reader = new SAXReader();
                Document doc = reader.read(new URL(xmlUri));
                result = doc.getRootElement();
                result.addNamespace(XML_NAMESPACE_TF_NAME, XML_NAMESPACE_TF_VALUE);
                logger.debug("Downloaded " + artifact.getArtifactAbbr() + " XML: " + result.asXML());
            } catch (Exception ex) {
                result = (new SAXReader()).read(new StringReader(artifact.getEmptyXml())).getRootElement();
                logger.warn("Download unsuccessful (using empty " + artifact.getArtifactAbbr() + ") for URI[" + uri + "]");
            }

            this.cachedTdsByUri.put(uri, result);
        }
        return result;
    }

    private List<Element> getSupersededArtifactElements(RawArtifact artifact) throws Exception {
        List<Element> supersededTdElements = new ArrayList<>();
        for (String uri : artifact.supersedesUris) {
            supersededTdElements.add(this.getRemoteArtifact(uri, artifact));
        }
        return supersededTdElements;
    }

    private List<Element> getSupersededByArtifactElements(RawArtifact artifact) throws Exception {
        List<Element> supersededTdElements = new ArrayList<>();
        for (String uri : artifact.supersedesUris) {
            supersededTdElements.add(this.getRemoteArtifact(uri, artifact));
        }
        return supersededTdElements;
    }

    /**
     * Resolves the source reference from external sources.  Looks at the assessment tool for data.
     */
    private Map<String, String> lookupSource(String sourceIdentifier, RawTdMetadata metadata) throws Exception {
        Map<String, String> ref = null;

        List<Element> supersededTds = this.getSupersededArtifactElements(metadata);
        //TODO this.getSupersededByArtifactElements(metadata);

        Map<String, String> sourcesInSupersededTds = new HashMap<>();
        for (Element td : supersededTds) {
            List sourceNodes = td.selectNodes("//tf:Sources/tf:Source");
            for (Object node : sourceNodes) {
                Element sourceElement = (Element) node;
                String id = BulkImportUtils.selectStringTrimmed(sourceElement, "string(./@tf:id)");
                String identifier = BulkImportUtils.selectStringTrimmed(sourceElement, "string(./tf:Identifier)");
                String reference = BulkImportUtils.selectStringTrimmed(sourceElement, "string(./tf:Reference)");
                if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(identifier)) {
                    sourcesInSupersededTds.put(id, reference);
                    sourcesInSupersededTds.put(identifier, reference);
                }
            }
        }
        this.cachedSources.putAll(sourcesInSupersededTds);

        if (sourcesInSupersededTds.containsKey(sourceIdentifier)) {
            ref = new HashMap<>();
            ref.put(sourceIdentifier, sourcesInSupersededTds.get(sourceIdentifier));
        }
        return ref;
    }

    /**
     * Gets all superseded TD information, and then creates a list of all terms from those superseded TDs.
     */
    private Set<TermImpl> lookupTerms(RawArtifact artifact) throws Exception {
        List<Element> supersededArtifacs = this.getSupersededArtifactElements(artifact);

        Set<TermImpl> alreadyFoundTerms = new HashSet<>(); // TermImpl already compares on name, case-insensitive

        for (Element supersededArtifact : supersededArtifacs) {
            for (Object node : supersededArtifact.selectNodes("//tf:Term")) {
                Element termNode = (Element) node;
                TermImpl term = new TermImpl();
                term.setName(BulkImportUtils.selectStringTrimmed(termNode, "string(./tf:Name)"));

                if (!alreadyFoundTerms.contains(term)) {
                    term.setDefinition(BulkImportUtils.selectStringTrimmed(termNode, "string(./tf:Definition)"));
                    for (Object abbrNode : termNode.selectNodes("./tf:Abbreviation")) {
                        term.addAbbreviation(BulkImportUtils.selectStringTrimmed((Element) abbrNode, "string(.)"));
                    }

                    alreadyFoundTerms.add(term);
                }
            }
        }

        return alreadyFoundTerms;
    }


    //////////////////////////////////////////////////
    // Instance Methods - Private - Parsing Raw TDs //
    //////////////////////////////////////////////////

    private void parseRawTds() throws Exception {
        logger.debug("Creating TD information from raw data...");

        Map<String, RawTdMetadata> monikerToMetadataMap = new HashMap<>(); // Maps TD monikers to metadata
        MultiValuedMap<String, RawTrustmarkDefinition> monikerToCritStepMultiMap = new ArrayListValuedHashMap<>(); // Moniker -> List of <Criteria, Step> tuples. (either may be blank)
        Set<String> monikers = monikerToCritStepMultiMap.keySet(); // This is backed by the map, so they change together.
        this.populateMonikerMaps(monikerToMetadataMap, monikerToCritStepMultiMap);

        int rawTdCount = monikers.size();
        int rawTdCurrentIndex = 0;

        this.listenerCollection.fireSetPercentage(0);
        this.parsedTds = new ArrayList<>(rawTdCount);
        for (String moniker : monikers) {
            RawTdMetadata metadata = monikerToMetadataMap.get(moniker);
            Collection<RawTrustmarkDefinition> criteriaStepPairs = monikerToCritStepMultiMap.get(moniker);

            // Validate the supersedes information.  An error is raised if the TD URI cannot be found in the Assessment Tool.
            for (String supersedesUri : metadata.supersedesUris) {
                Element supersedesTdXml = this.getRemoteArtifact(supersedesUri, metadata);
                logger.debug("Successfully cashed remote artifact " + supersedesUri);
            }
            for (String supersededByUri : metadata.supersededByUris) {
                Element supersededByTdXml = this.getRemoteArtifact(supersededByUri, metadata);
                logger.debug("Successfully cashed remote artifact " + supersededByUri);
            }


            this.processMetadata(metadata);
            this.validateMetadata(metadata);

            Map<String, String> sources = metadata.sources;
            this.cachedSources.putAll(sources);

            Set<String> sourceReferences = new HashSet<>();
            List<RawTdCriterion> criteria = new ArrayList<>();
            List<RawTdAssessmentStep> assessmentSteps = new ArrayList<>();
            MultiValuedMap<String, String> stepCriteriaMap = new ArrayListValuedHashMap<>(); // Maps which assessment step pertains to which criteria.
            this.populateCriteriaStepPairs(criteriaStepPairs, sourceReferences, criteria, assessmentSteps, stepCriteriaMap);

            Map<String, String> culledSources = this.cullSources(sourceReferences, sources, metadata);
            SortedSet<TermImpl> terms = this.gatherTerms(metadata);

            this.resolveSupersededTds(metadata);
            this.validateRawTdParse(metadata, culledSources, criteria, assessmentSteps);

            TrustmarkDefinitionImpl parsedTd = this.assembleTrustmarkDefinition(
                    metadata,
                    terms,
                    culledSources,
                    criteria,
                    assessmentSteps,
                    stepCriteriaMap
            );

            logger.debug(String.format(
                    "    TD %s contains %s criteria, %s assessment steps, and %s sources.",
                    parsedTd.getMetadata().getName(),
                    parsedTd.getConformanceCriteria().size(),
                    parsedTd.getAssessmentSteps().size(),
                    parsedTd.getSources().size()
            ));
            this.parsedTds.add(parsedTd);

            ++rawTdCurrentIndex;
            this.listenerCollection.fireSetMessage(String.format(
                    "Parsed %s of %s raw Trustmark Definitions",
                    rawTdCurrentIndex,
                    rawTdCount
            ));
            this.listenerCollection.fireSetPercentage(100 * rawTdCurrentIndex / rawTdCount);
        }

        logger.debug("Checking that there are no ID or Name/Version collisions...");
        for (int i = 0; i < this.parsedTds.size(); i++) {
            TrustmarkDefinition td1 = this.parsedTds.get(i);
            for (int j = 0; j < this.parsedTds.size() && j < i; j++) {
                TrustmarkDefinition td2 = this.parsedTds.get(j);

                if (td1.getMetadata().getIdentifier().equals(td2.getMetadata().getIdentifier())) {
                    String msg = "Identifier Collision Error.  TD[" + td1.getMetadata().getName() + ", v" + td1.getMetadata().getVersion() + "] and TD[" + td2.getMetadata().getName() + ", v" + td2.getMetadata().getVersion() + "] have the same id: " + td1.getMetadata().getIdentifier() + ".  This is a problem that must be corrected before the Excel files can be parsed.";
                    logger.error(msg);
                    throw new Exception(msg);
                } else if (td1.getMetadata().getName().equalsIgnoreCase(td2.getMetadata().getName()) && td1.getMetadata().getVersion().equalsIgnoreCase(td2.getMetadata().getVersion())) {
                    String msg = "Name/Version Collision Error.  They are Name=[" + td1.getMetadata().getName() + "] and Version=[" + td1.getMetadata().getVersion() + "].  This is a problem that must be corrected before the Excel files can be parsed.";
                    logger.error(msg);
                    throw new Exception(msg);
                }

            }
        }

        this.listenerCollection.fireSetPercentage(100);
    }

    private void populateMonikerMaps(
            Map<String, RawTdMetadata> monikerToMetadataMap,
            MultiValuedMap<String, RawTrustmarkDefinition> monikerToCritStepMultiMap
    ) {
        String previousMoniker = null;
        for (RawTrustmarkDefinition rawTd : this.rawTds) {
            int displayRowNum = rawTd.rowIndex + 1;
            if (displayRowNum == 1) {
                previousMoniker = null;
            } // Since we may be parsing multiple input files

            String currentMoniker = rawTd.metadata.moniker;
            if (!StringUtils.isBlank(currentMoniker)) {
                if (!monikerToMetadataMap.containsKey(currentMoniker)) {
                    // This moniker has not been encountered before, so add it.
                    monikerToMetadataMap.put(currentMoniker, rawTd.metadata);
                }
            } else {
                // We assume they wanted to use data from the previously encountered moniker (ie, this is a blank row under a not-blank row).
                currentMoniker = previousMoniker;

                // But make sure all the sources are saved from this raw TD's metadata, in case new sources are introduced on a secondary row
                RawTdMetadata previousMetadata = monikerToMetadataMap.get(previousMoniker);
                previousMetadata.sources.putAll(rawTd.metadata.sources);
            }

            if (StringUtils.isBlank(currentMoniker)) {
                logger.warn("Could not find any valid moniker for Row #" + displayRowNum + ": " + rawTd);
                throw new UnsupportedOperationException("Cannot group Row #" + displayRowNum + "!! No moniker or previous moniker!");
            }

            monikerToCritStepMultiMap.put(currentMoniker, rawTd);

            previousMoniker = currentMoniker;
        }
    }

    private void processMetadata(RawTdMetadata metadata) throws Exception {
        URI identifier = this.context.generateIdentifierForTrustmarkDefinition(metadata.moniker, metadata.version);
        metadata.identifier = identifier;

        if (StringUtils.isNotBlank(metadata.extensionDesc) && metadata.extensionDesc.contains("Parameter Name") && metadata.extensionDesc.contains("Parameter Type")) {
            /**
             * This Trustmark Definition includes the following parameterized extensions in the format:<br>     &ltParameter Name&gt:&ltParameter Type&gt |<br> SecurityControlAssessmentFrequencyInDays:Integer
             */
            logger.debug(String.format("Processing metadata extensionDesc from TD '%s': %s", metadata.name, metadata.extensionDesc));

            String[] parts = metadata.extensionDesc.split("<br\\/?>");
            String lastPart = parts[parts.length - 1];
            List<RawNameDescPair> paramParts = BulkImportUtils.parseColonPipeFormat(lastPart);

            StringBuilder sb = new StringBuilder();
            sb.append(parts[0].trim())
                    .append("<br/>" + "\n");
            sb.append("<ul style=\"list-style: none; padding: 0; margin: 0;\">" + "\n");
            for (RawNameDescPair paramData : paramParts) {
                String name = paramData.name;
                String type = paramData.desc;

                String idFromName = BulkImportUtils.idFromName(name);
                sb.append("\n");
                sb.append("    <li style=\"overflow: scroll;\">" + "\n");
                sb.append("        <pre style=\"margin-left: 1em;\">&lt;tfe:")
                        .append(idFromName)
                        .append(" xmlns:tfe=\"https://trustmark.gtri.gatech.edu/#extension\"&gt;<em>")
                        .append(type)
                        .append("</em>&lt;tfe:")
                        .append(idFromName)
                        .append("&gt;</pre>" + "\n");
                sb.append("    </li>" + "\n");
            }
            sb.append("</ul>" + "\n");

            metadata.extensionDescOriginal = metadata.extensionDesc;
            metadata.extensionDesc = sb.toString();
        }
    }

    private void validateMetadata(RawTdMetadata metadata) {
        // TODO: perform some checks on the metadata...
    }

    private void populateCriteriaStepPairs(
            Collection<RawTrustmarkDefinition> criteriaStepPairs,
            Set<String> sourceReferences,
            List<RawTdCriterion> criteria,
            List<RawTdAssessmentStep> assessmentSteps,
            MultiValuedMap<String, String> stepCriteriaMap
    ) {
        String lastCriterionUUID = null;
        String lastStepUUID = null;
        for (RawTrustmarkDefinition rawTdData : criteriaStepPairs) {
            RawTdCriterion criterion = rawTdData.criterion;
            RawTdAssessmentStep assessmentStep = rawTdData.assessmentStep;
            if (StringUtils.isNotBlank(criterion.id) && StringUtils.isNotBlank(assessmentStep.id)) {
                criteria.add(criterion);
                assessmentSteps.add(assessmentStep);
                stepCriteriaMap.put(assessmentStep.id, criterion.id);
                lastCriterionUUID = criterion.id;
                lastStepUUID = assessmentStep.id;
            } else if (StringUtils.isNotBlank(criterion.id)) {
                criteria.add(criterion);
                if (lastStepUUID == null) {
                    throw new UnsupportedOperationException("Uh-oh - missing required previous step UUID for criterion name[" + criterion.name + "]!");
                }
                stepCriteriaMap.put(lastStepUUID, criterion.id);
                lastCriterionUUID = criterion.id;
            } else if (StringUtils.isNotBlank(assessmentStep.id)) {
                assessmentSteps.add(assessmentStep);
                if (lastCriterionUUID == null) {
                    throw new UnsupportedOperationException("Uh-oh - missing required previous criterion UUID for step name[" + assessmentStep.name + "]!");
                }
                stepCriteriaMap.put(assessmentStep.id, lastCriterionUUID);
                lastStepUUID = assessmentStep.id;
            }

            for (RawNameDescPair citation : criterion.citations) {
                sourceReferences.add(citation.name);
            }
        }
    }

    private Map<String, String> cullSources(
            Set<String> sourceReferences,
            Map<String, String> sources,
            RawTdMetadata metadata
    ) throws Exception {
        Map<String, String> culledSources = new HashMap<>(); // those sources actually referenced from the criteria data.
        for (String source : sourceReferences) {
            if (sources.containsKey(source)) {
                culledSources.put(source, sources.get(source));
                continue;
            }
            if (this.cachedSources.containsKey(source)) {
                culledSources.put(source, this.cachedSources.get(source));
                continue;
            }

            Map<String, String> fullSource = lookupSource(source, metadata);
            if (fullSource != null) {
                culledSources.putAll(fullSource);
                continue;
            }

            // We could not find this source anywhere, so show an error message.

            String errorMessage = String.format(
                    "ERROR - Unable to find citation [%s] as referenced by TD[%s : %s]",
                    source,
                    metadata.moniker,
                    metadata.name
            );
            logger.warn(errorMessage);
            for (Map.Entry<String, String> sourceEntry : sources.entrySet()) {
                logger.warn("   Possible source: [" + sourceEntry.getKey() + "]");
            }
            throw new UnsupportedOperationException(errorMessage);
        }//end
        return culledSources;
    }

    private SortedSet<TermImpl> gatherTerms(RawArtifact artifact) throws Exception {

        SortedSet<TermImpl> terms = new TreeSet<>(); // TermImpl already compares on name, case-insensitive

        // Add from artifact Terms column
        for (RawNameDescPair term : artifact.terms) {
            TermImpl termImpl = new TermImpl();
            termImpl.setName(term.name);
            if (terms.contains(termImpl)) {
                continue;
            }
            // note: currently, inline terms on the TD or TIP sheets can't include abbreviations, only definitions
            termImpl.setDefinition(term.desc);
            terms.add(termImpl);
        }

        // Add from Terms sheet
        Collection<TermImpl> termsSheetTerms = this.rawTermsByFileName.get(artifact.excelFile);
        for (TermImpl term : termsSheetTerms) {
            if (this.shouldIncludeTerm(term, artifact)) {
                terms.add(term);
            }
        }

        // Add from superceded artifact
        logger.debug(String.format(
                "For %s[%s : %s], downloading terms from superseded %ss...",
                artifact.getArtifactAbbr(),
                artifact.moniker,
                artifact.name,
                artifact.getArtifactAbbr()
        ));
        Collection<TermImpl> supersededTerms = this.lookupTerms(artifact);
        if (terms.isEmpty()) {
            for (TermImpl term : supersededTerms) {
                if (this.shouldIncludeTerm(term, artifact)) {
                    terms.add(term);
                }
            }
        } else {
            for (TermImpl term : terms) {
                if (StringUtils.isBlank(term.getDefinition())) {
                    for (Term supersededTerm : supersededTerms) {
                        if (supersededTerm.getName().trim().equalsIgnoreCase(term.getName().trim())) {
                            term.setDefinition(supersededTerm.getDefinition());
                            term.setAbbreviations(supersededTerm.getAbbreviations());
                            break;
                        }
                    }
                    if (StringUtils.isBlank(term.getDefinition())) {
                        throw new UnsupportedOperationException(String.format(
                                "For %s[%s] at location [%s], found term '%s' but no definition.  Does it exist in the Terms page, Terms Column or superseded %s?",
                                artifact.getArtifactAbbr(),
                                artifact.name,
                                artifact.debugLocation,
                                term.getName(),
                                artifact.getArtifactAbbr()
                        ));
                    }
                }
            }
        }

        return terms;
    }

    private boolean shouldIncludeTerm(Term term, RawArtifact artifact) {
        if (artifact.termsInclude.size() > 0) {
            return artifact.termsInclude.contains(term.getName().toLowerCase());
        }
        if (artifact.termsExclude.size() > 0) {
            return !artifact.termsExclude.contains(term.getName().toLowerCase());
        }

        // If they give us nothing, it's like giving us termsInclude = []
        return true;
    }

    private void resolveSupersededTds(RawTdMetadata metadata) throws Exception {
        List<Element> supersededTdElements = this.getSupersededArtifactElements(metadata);

        for (Element tdElement : supersededTdElements) {
            String identifier = this.getTdId(tdElement);
            metadata.supersedesUrisResolved.add(identifier);
        }

        List<Element> supersededByTdElements = this.getSupersededByArtifactElements(metadata);
        for (Element tdElement : supersededByTdElements) {
            String identifier = this.getTdId(tdElement);
            metadata.supersededByUrisResolved.add(identifier);
        }
    }

    private String getTdId(Element tdXml) {
        return BulkImportUtils.selectStringTrimmed(tdXml, "string(/tf:TrustmarkDefinition/tf:Metadata/tf:Identifier)");
    }

    private void validateRawTdParse(
            RawTdMetadata metadata,
            Map<String, String> sources,
            List<RawTdCriterion> criteria,
            List<RawTdAssessmentStep> assessmentSteps
    ) {
        if (assessmentSteps == null || assessmentSteps.isEmpty()) {
            logger.warn("TD " + metadata.name + " has no assessment steps!");
            throw new UnsupportedOperationException("TD " + metadata.name + " has no assessment steps!");
        }
        if (criteria == null || criteria.isEmpty()) {
            logger.warn("TD " + metadata.name + " has no criteria!");
            throw new UnsupportedOperationException("TD " + metadata.name + " has no criteria!");
        }

        Set<String> stepIds = new HashSet<>();
        for (RawTdAssessmentStep step : assessmentSteps) {
            if (stepIds.contains(step.id)) {
                logger.warn("TD " + metadata.name + " has 2 steps with ID " + step.id + "!");
                throw new UnsupportedOperationException("TD " + metadata.name + " has 2 steps with ID " + step.id + "!");
            } else {
                stepIds.add(step.id);
            }
        }
        Set<String> critIds = new HashSet<>();
        for (RawTdCriterion crit : criteria) {
            if (critIds.contains(crit.id)) {
                logger.warn("TD " + metadata.name + " has 2 criteria with ID " + crit.id + "!");
                throw new UnsupportedOperationException("TD " + metadata.name + " has 2 criteria with ID " + crit.id + "!");
            } else {
                critIds.add(crit.id);
            }
        }

    }

    private TrustmarkDefinitionImpl assembleTrustmarkDefinition(
            RawTdMetadata metadata,
            SortedSet<TermImpl> terms,
            Map<String, String> sources,
            List<RawTdCriterion> criteria,
            List<RawTdAssessmentStep> assessmentSteps,
            MultiValuedMap<String, String> stepCriteriaMap
    ) throws Exception {
        TrustmarkDefinitionImpl parsedTd = new TrustmarkDefinitionImpl();

        // Metadata
        TrustmarkDefinitionMetadataImpl parsedMetadata = this.assembleMetadata(metadata);
        parsedTd.setMetadata(parsedMetadata);
        this.putAllArtifactTransientDataIfEnabled(metadata, parsedTd);

        // Terms
        for (TermImpl term : terms) {
            parsedTd.addTerm(term);
        }

        // Sources
        BidiMap<String, SourceImpl> parsedSourcesById = this.assembleSourcesById(sources);
        for (SourceImpl source : parsedSourcesById.values()) {
            parsedTd.addSource(source);
        }

        // Conformance Criteria
        parsedTd.setConformanceCriteriaPreface(BulkImportUtils.trimOrNull(metadata.criteriaPreface));
        BidiMap<String, ConformanceCriterionImpl> parsedCriteriaById = new DualHashBidiMap<>();
        Set<ConformanceCriterionImpl> parsedCriteria = parsedCriteriaById.values();
        int critCount = 0;
        for (RawTdCriterion crit : criteria) {
            ConformanceCriterionImpl parsedCriterion = this.assembleConformanceCriterion(crit, ++critCount, parsedSourcesById);
            if (parsedCriteria.contains(parsedCriterion)) {
                continue;
            }
            parsedCriteriaById.put(parsedCriterion.getId(), parsedCriterion);
            parsedTd.addConformanceCriterion(parsedCriterion);
        }

        // Assessment Steps
        parsedTd.setAssessmentStepPreface(BulkImportUtils.trimOrNull(metadata.assessmentPreface));
        int stepCount = 0;
        for (RawTdAssessmentStep step : assessmentSteps) {
            AssessmentStepImpl parsedAssessmentStep = this.assembleAssessmentStep(step, ++stepCount, parsedCriteriaById, stepCriteriaMap);
            parsedTd.addAssessmentStep(parsedAssessmentStep);
        }

        // Issuance Criteria
        String issuanceCriteria = resolveIssuanceCriteria(metadata, parsedTd.getAssessmentSteps());
        parsedTd.setIssuanceCriteria(issuanceCriteria);

        return parsedTd;
    }

    private TrustmarkFrameworkIdentifiedObjectImpl assembleTrustmarkDefinitionReference(String trustmarkFrameworkIdentifiedObjectUri) throws Exception {
        // Taken from TrustmarkDefinitionXmlDeserializer's method of deserializing Supersedes comments
        TrustmarkFrameworkIdentifiedObjectImpl tmfio = new TrustmarkFrameworkIdentifiedObjectImpl();
        tmfio.setIdentifier(new URI(trustmarkFrameworkIdentifiedObjectUri));
        tmfio.setTypeName("TrustmarkDefinitionReference");
        return tmfio;
    }

    private TrustmarkDefinitionMetadataImpl assembleMetadata(RawTdMetadata metadata) throws Exception {
        TrustmarkDefinitionMetadataImpl parsedMetadata = new TrustmarkDefinitionMetadataImpl();

        metadata.version = BulkImportUtils.defaultTrim(metadata.version, this.context.getDefaultVersion());

        parsedMetadata.setIdentifier(metadata.identifier);
        //parsedMetadata.setTrustmarkReferenceAttributeName(new URI(metadata.identifier + "trustmark-reference/"));
        parsedMetadata.setName(metadata.name);
        parsedMetadata.setVersion(metadata.version);
        parsedMetadata.setDescription(BulkImportUtils.defaultTrim(metadata.description));

        // If we parsed a time, set it, otherwise set the time to now
        if (metadata.publicationDateTime != null)
            try {
                parsedMetadata.setPublicationDateTime(new Date(metadata.publicationDateTime));
            } catch (java.lang.IllegalArgumentException e) {
                // No date or invalidly formatted date, default to now
                parsedMetadata.setPublicationDateTime(this.getPublicationDateTime());
            }
        else
            parsedMetadata.setPublicationDateTime(this.getPublicationDateTime());

        parsedMetadata.setTrustmarkDefiningOrganization(this.context.getTrustmarkDefiningOrganization());

        parsedMetadata.setTargetStakeholderDescription(BulkImportUtils.defaultTrim(metadata.stakeholderDesc, this.context.getDefaultTargetStakeholderDescription()));
        parsedMetadata.setTargetRecipientDescription(BulkImportUtils.defaultTrim(metadata.recipientDesc, this.context.getDefaultTargetRecipientDescription()));
        parsedMetadata.setTargetRelyingPartyDescription(BulkImportUtils.defaultTrim(metadata.relyingPartyDesc, this.context.getDefaultTargetRelyingPartyDescription()));
        parsedMetadata.setTargetProviderDescription(BulkImportUtils.defaultTrim(metadata.providerDesc, this.context.getDefaultTargetProviderDescription()));
        parsedMetadata.setProviderEligibilityCriteria(BulkImportUtils.defaultTrim(metadata.providerEligibilityCriteria, this.context.getDefaultProviderEligibilityCriteria()));
        parsedMetadata.setAssessorQualificationsDescription(BulkImportUtils.defaultTrim(metadata.assessorQualificationsDesc, this.context.getDefaultAssessorQualificationsDescription()));
        parsedMetadata.setExtensionDescription(BulkImportUtils.defaultTrim(metadata.extensionDesc, this.context.getDefaultExtensionDescription()));

        parsedMetadata.setTrustmarkRevocationCriteria(BulkImportUtils.defaultTrim(metadata.revocationCriteria, this.context.getDefaultRevocationCriteria()));
        parsedMetadata.setLegalNotice(BulkImportUtils.defaultTrim(metadata.legalNotice, this.context.getDefaultTdLegalNotice()));
        parsedMetadata.setNotes(BulkImportUtils.defaultTrim(metadata.notes, this.context.getDefaultTdNotes()));

        // TD Metadata Supersedes
        //for (String supersedesUri : metadata.supersedesUrisResolved) {
        for (String supersedesUri : metadata.supersedesUris) {
            TrustmarkFrameworkIdentifiedObjectImpl supersedesTmfio = this.assembleTrustmarkDefinitionReference(supersedesUri);
            parsedMetadata.addToSupersedes(supersedesTmfio);
        }

        // TD Metadata SupersededBy
        //for (String supersededByUri : metadata.supersededByUrisResolved) {
        for (String supersededByUri : metadata.supersededByUris) {
            TrustmarkFrameworkIdentifiedObjectImpl supersededByTmfio = this.assembleTrustmarkDefinitionReference(supersededByUri);
            parsedMetadata.addToSupersededBy(supersededByTmfio);
        }

        parsedMetadata.setDeprecated(metadata.deprecated);

        // Keywords
        parsedMetadata.setKeywords(metadata.keywords);

        return parsedMetadata;
    }


    private Date getPublicationDateTime() throws Exception {
        return Calendar.getInstance().getTime();
    }

    private SourceImpl assembleSource(String identifier, String reference) {
        SourceImpl parsedSource = new SourceImpl();
        parsedSource.setIdentifier(identifier);
        parsedSource.setReference(reference);
        return parsedSource;
    }

    private BidiMap<String, SourceImpl> assembleSourcesById(Map<String, String> sources) {
        BidiMap<String, SourceImpl> parsedSourcesById = new DualHashBidiMap<>();
        Set<SourceImpl> parsedSources = parsedSourcesById.values();
        for (Map.Entry<String, String> sourceEntry : sources.entrySet()) {
            SourceImpl parsedSource = this.assembleSource(sourceEntry.getKey(), sourceEntry.getValue());
            if (parsedSources.contains(parsedSource)) {
                continue;
            }
            parsedSourcesById.put(parsedSource.getIdentifier(), parsedSource);
        }
        return parsedSourcesById;
    }

    private ConformanceCriterionImpl assembleConformanceCriterion(
            RawTdCriterion crit,
            int critNumber,
            Map<String, SourceImpl> parsedSourcesById
    ) {
        ConformanceCriterionImpl critImpl = new ConformanceCriterionImpl();

        critImpl.setId(crit.id);
        critImpl.setNumber(critNumber);
        critImpl.setName(crit.name);
        critImpl.setDescription(crit.description);

        for (RawNameDescPair citation : crit.citations) {
            CitationImpl parsedCitation = new CitationImpl();
            parsedCitation.setSource(parsedSourcesById.get(citation.name));
            parsedCitation.setDescription(citation.desc);
            critImpl.addCitation(parsedCitation);
        }

        return critImpl;
    }

    private AssessmentStepImpl assembleAssessmentStep(
            RawTdAssessmentStep step,
            int stepNumber,
            Map<String, ConformanceCriterionImpl> parsedCriteriaById,
            MultiValuedMap<String, String> stepCriteriaMap
    ) {
        AssessmentStepImpl parsedStep = new AssessmentStepImpl();

        parsedStep.setId(step.id);
        parsedStep.setNumber(stepNumber);
        parsedStep.setName(step.name);
        parsedStep.setDescription(step.description);

        Collection<String> criterionIds = stepCriteriaMap.get(step.id);
        for (String criterionId : criterionIds) {
            ConformanceCriterionImpl parsedCriterion = parsedCriteriaById.get(criterionId);
            parsedStep.addConformanceCriterion(parsedCriterion);
        }

        for (RawNameDescPair artifact : step.artifacts) {
            ArtifactImpl parsedArtifact = new ArtifactImpl();
            parsedArtifact.setName(artifact.name);
            parsedArtifact.setDescription(artifact.desc);
            parsedStep.addArtifact(parsedArtifact);
        }

        for (RawTdParameter parameter : step.parameters) {
            TrustmarkDefinitionParameterImpl parsedParameter = new TrustmarkDefinitionParameterImpl();
            parsedParameter.setIdentifier(parameter.id);
            parsedParameter.setRequired(parameter.isRequired);
            parsedParameter.setName(parameter.name);
            parsedParameter.setDescription(parameter.description);
            parsedParameter.setParameterKind(ParameterKind.fromString(parameter.kindName));
            parsedParameter.setEnumValues(parameter.enumValues);
            parsedStep.addParameter(parsedParameter);
        }

        return parsedStep;
    }

    private String resolveIssuanceCriteria(RawTdMetadata metadata, List<AssessmentStep> assessmentSteps) {
        if (StringUtils.isBlank(metadata.issuanceCriteria)) {
            return this.context.getDefaultIssuanceCriteria();
        }
        String issuanceCriteria = metadata.issuanceCriteria.trim();
        logger.debug("    Resolving step names to IDs in issuance criteria: [" + issuanceCriteria + "]");

        StringBuffer resolvedIssanceCriteria = new StringBuffer();
        Matcher m = STEP_REFERENCE_PATTERN.matcher(issuanceCriteria);
        while (m.find()) {
            String stepReference = m.group(1);
            String stepId = null;
            for (AssessmentStep step : assessmentSteps) {
                if (step == null || step.getName() == null) {
                    continue;
                }
                if (step.getName().equalsIgnoreCase(stepReference)) {
                    //stepId = "Step" + step.getNumber();
                    stepId = step.getId();
                    break;
                }
            }

            if (stepId != null) {
                m.appendReplacement(resolvedIssanceCriteria, Matcher.quoteReplacement(stepId));
                logger.debug(String.format("        Found '%s', replacing with '%s'", m.group(), stepId));
                continue;
            }

            // Error out if unable to find step by name
            AssessmentStep closestStep = null;
            int closestDistance = Integer.MAX_VALUE;
            for (AssessmentStep step : assessmentSteps) {
                if (step == null || step.getName() == null) {
                    continue;
                }
                int distance = BulkImportUtils.levenshteinDistance(stepReference.toLowerCase(), step.getName().toLowerCase());
                if (closestStep == null || distance < closestDistance) {
                    closestStep = step;
                    closestDistance = distance;
                }
            }
            String errorMessage = String.format(
                    "Could not find Step[%s] as referenced from TD[%s] in File[%s], Line #%s. Closest match was: [%s]",
                    stepReference,
                    metadata.name,
                    metadata.excelFile,
                    metadata.rowIndex,
                    closestStep == null ? "NONE FOUND" : String.format("Step%s:%s", closestStep.getNumber(), closestStep.getName())
            );
            logger.error("**** ERROR - " + errorMessage);
            throw new UnsupportedOperationException(errorMessage);
        }
        m.appendTail(resolvedIssanceCriteria);
        return resolvedIssanceCriteria.toString();
    }


    ///////////////////////////////////////////////////
    // Instance Methods - Private - Parsing Raw TIPs //
    ///////////////////////////////////////////////////

    private void parseRawTips() throws Exception {
        this.ensureRawTdsAreParsed();

        int rawTipCount = this.rawTips.size();
        int rawTipCurrentIndex = 0;

        logger.debug("Processing " + rawTipCount + " raw TIP rows...");
        // Fix moniker, version, and ID for Raw TIPs first, so that forward references can use the correct ID/Name/Version info
        for (RawTrustInteroperabilityProfile rawTip : this.rawTips) {
            rawTip.version = BulkImportUtils.defaultTrim(rawTip.version, this.context.getDefaultVersion());
            rawTip.id = this.context.generateIdentifierForTrustInteroperabilityProfile(rawTip.moniker, rawTip.version);
        }

        // Then parse all TIPs
        this.listenerCollection.fireSetPercentage(0);
        this.parsedTips = new ArrayList<>(rawTipCount);
        for (int i = 0; i < rawTips.size(); i++) {
            RawTrustInteroperabilityProfile rawTip = rawTips.get(i);
            logger.debug(String.format("    Creating TIP{%s} from Row #%s in file[%s]...", rawTip.name, rawTip.rowIndex, rawTip.excelFile));

            SortedSet<TermImpl> terms = this.gatherTerms(rawTip);
            TrustInteroperabilityProfileImpl parsedTip = this.assembleTrustInteroperabilityProfile(rawTip, terms);
            this.parsedTips.add(parsedTip);

            ++rawTipCurrentIndex;
            this.listenerCollection.fireSetMessage(String.format(
                    "Parsed %s of %s raw Trust Interoperability Profiles",
                    rawTipCurrentIndex,
                    rawTipCount
            ));
            this.listenerCollection.fireSetPercentage(100 * rawTipCurrentIndex / rawTipCount);
        }
        this.listenerCollection.fireSetPercentage(100);

        logger.debug("Checking that there are no ID or Name/Version collisions...");
        for (int i = 0; i < parsedTips.size(); i++) {
            TrustInteroperabilityProfile tip1 = parsedTips.get(i);

            // Do some data set sanity checks (ie, make sure there are no collisions just yet)
            for (int j = 0; j < parsedTips.size() && j < i; j++) {
                TrustInteroperabilityProfile tip2 = parsedTips.get(j);

                if (tip1.getIdentifier().equals(tip2.getIdentifier())) {
                    String msg = "Identifier Collision Error.  TIP[" + tip1.getName() + ", v" + tip1.getVersion() + "] and TIP[" + tip2.getName() + ", v" + tip2.getVersion() + "] generate the same id: " + tip1.getIdentifier() + ".  This is a problem that must be corrected before the Excel files can be parsed.";
                    logger.error(msg);
                    throw new Exception(msg);
                } else if (tip1.getName().equalsIgnoreCase(tip2.getName()) && tip1.getVersion().equalsIgnoreCase(tip2.getVersion())) {
                    String msg = "Name/Version Collision Error.  They are Name=[" + tip1.getName() + "] and Version=[" + tip1.getVersion() + "].  This is a problem that must be corrected before the Excel files can be parsed.";
                    logger.error(msg);
                    throw new Exception(msg);
                }
            }
        }

        logger.debug("Successfully processed " + this.parsedTips.size() + " tips.");
    }

    private TrustInteroperabilityProfileImpl assembleTrustInteroperabilityProfile(
            RawTrustInteroperabilityProfile rawTip,
            Collection<? extends Term> terms
    ) throws Exception {
        TrustInteroperabilityProfileImpl parsedTip = new TrustInteroperabilityProfileImpl();

        parsedTip.setIdentifier(rawTip.id);
        parsedTip.setName(rawTip.name);
        parsedTip.setVersion(rawTip.version);
        parsedTip.setDescription(rawTip.description);
        if (rawTip.primary != null
                && (rawTip.primary.toLowerCase().equals("y")
                || rawTip.primary.toLowerCase().equals("x")
                || rawTip.primary.toLowerCase().equals("yes")
                || rawTip.primary.toLowerCase().equals("true"))
        ) {
            parsedTip.setPrimary(Boolean.TRUE);
        }
        parsedTip.setMoniker(rawTip.moniker);
        // If we parsed a time, set it, otherwise set the time to now
        if (rawTip.publicationDateTime != null)
            try {
                parsedTip.setPublicationDateTime(new Date(rawTip.publicationDateTime));
            } catch (java.lang.IllegalArgumentException e) {
                // No date or invalidly formatted date, default to now
                parsedTip.setPublicationDateTime(this.getPublicationDateTime());
            }
        else
            parsedTip.setPublicationDateTime(this.getPublicationDateTime());

        parsedTip.setNotes(BulkImportUtils.defaultTrim(rawTip.notes, this.context.getDefaultTipNotes()));
        parsedTip.setLegalNotice(BulkImportUtils.defaultTrim(rawTip.legalNotice, this.context.getDefaultTipLegalNotice()));
        parsedTip.setIssuer(this.context.getTrustInteroperabilityProfileIssuer());

        for (Term term : terms) {
            parsedTip.addTerm(term);
        }

        parsedTip.setKeywords(rawTip.keywords);

        BidiMap<String, SourceImpl> parsedSourcesById = this.assembleSourcesById(rawTip.sources);
        for (SourceImpl source : parsedSourcesById.values()) {
            parsedTip.addSource(source);
        }
        // Supersedes
        //for (String supersedesUri : rawTip.supersedesUrisResolved) {
        for (String supersedesUri : rawTip.supersedesUris) {
            TrustmarkFrameworkIdentifiedObjectImpl supersedesByTmfio = this.assembleTrustmarkDefinitionReference(supersedesUri);
            parsedTip.addToSupersedes(supersedesByTmfio);
        }

        // SupersededBy
        //for (String supersededByUri : rawTip.supersededByUrisResolved) {
        for (String supersededByUri : rawTip.supersededByUris) {
            TrustmarkFrameworkIdentifiedObjectImpl supersededByTmfio = this.assembleTrustmarkDefinitionReference(supersededByUri);
            parsedTip.addToSupersededBy(supersededByTmfio);
        }

        parsedTip.setDeprecated(rawTip.deprecated);

        this.processTipReferencesAndTrustExpression(rawTip, parsedTip);
        this.putAllArtifactTransientDataIfEnabled(rawTip, parsedTip);

        return parsedTip;
    }

    private void processTipReferencesAndTrustExpression(
            RawTrustInteroperabilityProfile rawTip,
            TrustInteroperabilityProfileImpl parsedTip
    ) throws TrustInteroperabilityProfileSyntaxException {
        logger.debug(String.format("    Processing TIP References for TIP[%s] %s %s...",
                rawTip.name, rawTip.id.toString(), rawTip.trustExpression));
        if (StringUtils.isBlank(rawTip.trustExpression)) {
            // TODO Fill in from "TIP" columns on other pages.
            throw new UnsupportedOperationException(String.format(
                    "BLANK TRUST EXPRESSION NOT YET SUPPORTED: Line #%s in file %s",
                    rawTip.rowIndex,
                    rawTip.excelFile
            ));
        }

        // Collect any exceptions
        List<TrustInteroperabilityProfileSyntaxException> exceptionList = new ArrayList<>();

        // validate the TD parameters in the trust expression
        Arrays.asList(rawTip.trustExpression.split(TRUST_SPLIT_REFERENCE_WITH_PARMS)).forEach(s -> {
            Matcher tdMatch = TD_REFERENCE_PATTERN.matcher(s);
            if (tdMatch.find()) {
                String referencedTdName = tdMatch.group(1);
                String[] parms = s.split(TD_PARAMETER);
                if (parms.length > 1) {
                    String[] parmNm = BulkImportUtils.defaultTrim(parms[1]).split(TD_OPERATOR);
                    if (!doesTDParameterExist(referencedTdName, BulkImportUtils.defaultTrim(parmNm[0]), this.rawTds)) {
                        logger.debug(String.format("INVALID-PARAMETER-FOUND for TD: [%s] referenced in [%s] , file -> %s\n", parmNm[0], referencedTdName, rawTip.excelFile));
                        invalidParameters.add(String.format("Invalid Parameter Found [%s] referenced in TD [%s] TIP [%s}, file: %s\n", parmNm[0], referencedTdName, rawTip.name, rawTip.excelFile));
                    }
                }
            }
        });

        //  Split the trust expression into chunks based around the and / ors , preserving the order
        List<String> expressionList = Arrays.asList(rawTip.trustExpression.split(TRUST_SPLIT_REFERENCE))
                .stream()
                .map(s -> s.endsWith("}") ? s : s + "}")
                .filter(TIP_REFERENCE_PATTERN.asPredicate().or(TD_REFERENCE_PATTERN.asPredicate()))
                .collect(Collectors.toList());

        // TIP references
        Set<URI> referencedTipUris = new HashSet<>();
        List<TrustInteroperabilityProfileReferenceImpl> referencedTips = new ArrayList<>();
        // TD references
        Set<URI> referencedTdUris = new HashSet<>();
        List<TrustmarkDefinitionRequirementImpl> referencedTds = new ArrayList<>();

        expressionList.forEach(s -> {
            Matcher tipMatch = TIP_REFERENCE_PATTERN.matcher(s);
            if (tipMatch.find()) {
                String referencedTipName = tipMatch.group(1);
                if (referencedTipName.equals(rawTip.name)) {
                    exceptionList.add(new TrustInteroperabilityProfileSyntaxException(
                            String.format("Self referenced TIP Cannot reference itself. TIP: [%s] in [%s]",
                                    referencedTipName, rawTip.name),
                            rawTip.excelFile));
                }
                TrustInteroperabilityProfileReferenceImpl referencedTip = this.findReferencedTip(referencedTipName, rawTip);
                if (referencedTip.getIdentifier() == null && referencedTip.getName() == null) {
                    exceptionList.add(new TrustInteroperabilityProfileSyntaxException(
                            String.format("Cannot find reference for TIP: [%s] in [%s]",
                                    referencedTipName, rawTip.name),
                            rawTip.excelFile));
                } else {
                    if (doesCyclicalNameReferenceExist(rawTip.name, referencedTip.getName(), this.rawTips)) {
                        exceptionList.add(new TrustInteroperabilityProfileSyntaxException(
                                String.format("Cyclical Reference Found for TIP: [%s] referenced in [%s]",
                                        referencedTipName, rawTip.name),
                                rawTip.excelFile));
                    }
                    if (doesCyclicalURIReferenceExist(rawTip.id, referencedTip.getIdentifier(), this.rawTips)) {
                        exceptionList.add(new TrustInteroperabilityProfileSyntaxException(
                                String.format("Cyclical Reference Found for TIP: [%s] referenced in [%s]",
                                        referencedTipName, rawTip.name),
                                rawTip.excelFile));
                    }
                    if (referencedTipUris.add(referencedTip.getIdentifier())) {
                        referencedTip.setNumber(referencedTipUris.size() + referencedTdUris.size());
                        if (BulkImportUtils.isValidUri(referencedTipName)) {
                            referencedTip.setId(referencedTipName);
                        }
                        referencedTips.add(referencedTip);
                    }
                }
            } else {
                Matcher tdMatch = TD_REFERENCE_PATTERN.matcher(s);
                if (tdMatch.find()) {
                    String referencedTdName = tdMatch.group(1);
                    TrustmarkDefinitionRequirementImpl referencedTd = this.findReferencedTd(referencedTdName, rawTip);
                    if (referencedTd.getIdentifier() == null && referencedTd.getName() == null) {
                        String errorMsssage = String.format(
                                "Could not find a reference for TD: [%s] as referenced from TIP: [%s] in File: [%s], Line #%s.",
                                referencedTdName,
                                rawTip.name,
                                rawTip.excelFile,
                                rawTip.rowIndex
                        );
                        logger.error("**** ERROR - " + errorMsssage);
                        exceptionList.add(new TrustInteroperabilityProfileSyntaxException(
                                errorMsssage, rawTip.excelFile));
                    } else {
                        logger.debug(String.format("    TD_REF_REMOTE [ identifier = %s , name = %s, version = %s, description = %s ]",
                                referencedTd.getIdentifier(),
                                referencedTd.getName(),
                                referencedTd.getVersion(),
                                referencedTd.getDescription()));
                        if (referencedTdUris.add(referencedTd.getIdentifier())) {
                            referencedTd.setNumber(referencedTipUris.size() + referencedTdUris.size());
                            if (BulkImportUtils.isValidUri(referencedTdName)) {
                                referencedTd.setId(referencedTdName);
                            }
                            referencedTds.add(referencedTd);
                        }
                    }
                }
            }
        });
        if (!exceptionList.isEmpty()) {
            throw exceptionList.get(0);
        }

        // Add all
        BidiMap<String, String> TD_TO_XMLID_MAPPING = new DualHashBidiMap<>();
        Set<String> TD_XMLID_SET = TD_TO_XMLID_MAPPING.values();
        int nextRef = 1;
        for (TrustmarkDefinitionRequirementImpl referencedTd : referencedTds) {
            String tdName = BulkImportUtils.defaultTrim(referencedTd.getName(), null);
            String tdVarName = tdName;
            if (referencedTd.getId() != null) {
                tdName = referencedTd.getId();
                tdVarName = "ref_" + (nextRef++);
            }
            String tdReqId = BulkImportUtils.getUniqueVariableName(
                    "TD_", tdVarName, TE_VAR_BLACKLIST_PATTERN, TE_VAR_MAX_LENGTH, TD_XMLID_SET
            );
            referencedTd.setId(tdReqId);
            TD_TO_XMLID_MAPPING.put(tdName.toLowerCase(), referencedTd.getId());
            parsedTip.addReference(referencedTd);
        }

        BidiMap<String, String> TIP_TO_XMLID_MAPPING = new DualHashBidiMap<>();
        Set<String> TIP_XMLID_SET = TIP_TO_XMLID_MAPPING.values();
        for (TrustInteroperabilityProfileReferenceImpl referencedTip : referencedTips) {
            String tipName = BulkImportUtils.defaultTrim(referencedTip.getName(), null);
            String tipVarName = tipName;
            if (referencedTip.getId() != null) {
                tipName = referencedTip.getId();
                tipVarName = "ref_" + (nextRef++);
            }
            String tipRefId = BulkImportUtils.getUniqueVariableName(
                    "TIP_", tipVarName, TE_VAR_BLACKLIST_PATTERN, TE_VAR_MAX_LENGTH, TIP_XMLID_SET
            );
            referencedTip.setId(tipRefId);
            TIP_TO_XMLID_MAPPING.put(tipName.toLowerCase(), referencedTip.getId());
            parsedTip.addReference(referencedTip);
        }

        String te = this.assembleTrustExpression(rawTip.trustExpression, TD_TO_XMLID_MAPPING, TIP_TO_XMLID_MAPPING);

        try {
            TrustExpression<TrustExpressionData> trustExpressionDataTrustExpression =
                    trustExpressionStringParser.parse(te);
        } catch (org.jparsec.error.ParserException pe) {
            String errorMsssage = String.format(
                    "Could not parse Trust Expression in TIP: [%s] in File: [%s], Line #%s.<br/>" +
                            "Trust Expression: <b>%s</b><br/>" +
                            "Parse Exception: <i>%s</i>",
                    rawTip.name,
                    rawTip.excelFile,
                    rawTip.rowIndex,
                    te,
                    pe.getLocalizedMessage().split(", edu.gatech")[0]);
            logger.error("**** ERROR - " + errorMsssage);
            exceptionList.add(new TrustInteroperabilityProfileSyntaxException(
                    errorMsssage, rawTip.excelFile));
            throw exceptionList.get(0);
        }

        parsedTip.setTrustExpression(te);

        // check the grammar of the Trust Expression
//        try {
//            TrustExpressionUtils.validate(te);
//        }  catch (TrustExpressionSyntaxException tese)  {
//            String msg = String.format("Trust Expression Error - TIP: %s Character %d, %s <br> %s\n ", rawTip.name, tese.getColumn(), tese.getMessage(), te);
//            throw new TrustExpressionSyntaxException(null, tese.getOffendingSymbol(), tese.getLine(), tese.getColumn(), msg, tese);
//        }

        // Finished
        logger.debug("    TIP[" + rawTip.moniker + " : " + rawTip.name + "] has " + referencedTds.size() + " TD references.");
        logger.debug("    TIP[" + rawTip.moniker + " : " + rawTip.name + "] has " + referencedTips.size() + " TIP references.");
    }

    /**
     * checks for references to the parent tip name in the referenced tip, indicating a cyclical refereence
     *
     * @param parentTip
     * @param referenceTip
     * @param tips
     * @return
     */
    private boolean doesCyclicalNameReferenceExist(String parentTip, String referenceTip, List<RawTrustInteroperabilityProfile> tips) {
        List<RawTrustInteroperabilityProfile> list = tips.stream().filter(tip -> {
            return tip.name.equals(referenceTip);
        }).collect(Collectors.toList());

        for (RawTrustInteroperabilityProfile tip : list) {
            Matcher tipMatch = TIP_REFERENCE_PATTERN.matcher(tip.trustExpression);
            if (tipMatch.find()) {
                if (parentTip.equals(tipMatch.group(1)))
                    return true;
            }
        }
        return false;
    }

    /**
     * checks for references to the parent tip URI in the referenced TIP, indicating a cyclical reference
     *
     * @param parentTip
     * @param referenceTip
     * @param tips
     * @return
     */
    private boolean doesCyclicalURIReferenceExist(URI parentTip, URI referenceTip, List<RawTrustInteroperabilityProfile> tips) {
        List<RawTrustInteroperabilityProfile> list = tips.stream().filter(tip -> {
            return tip.id.toString().equals(referenceTip);
        }).collect(Collectors.toList());
        List<RawTrustInteroperabilityProfile> cyclicalList = list.stream().filter(tip -> {
            return tip.trustExpression.contains(parentTip.toString());
        }).collect(Collectors.toList());
        return !cyclicalList.isEmpty();
    }

    /**
     * checks that a TD has the referenced parameter name
     *
     * @param tdName
     * @param parameterName
     * @return
     */
    private boolean doesTDParameterExist(String tdName, String parameterName, List<RawTrustmarkDefinition> tds) {
        boolean rc[] = new boolean[2];
        rc[0] = false;

        List<RawTrustmarkDefinition> tdList = tds.stream().filter(td -> {
            return tdName.equals(td.metadata.name);
        }).collect(Collectors.toList());

        tdList.forEach(td -> {
            td.assessmentStep.parameters.forEach(parm -> {
                if (parameterName.equalsIgnoreCase(parm.id)) {
                    rc[0] = true;
                }
//                System.out.printf("TD PARAMETERS %s %s %s\n", parm.name, parm.kindName, parm.id);
            });
        });
        return rc[0];
    }

    private <T extends AbstractTIPReference, O extends RawObject> T findReferencedArtifact(
            String referencedArtifactName,
            RawTrustInteroperabilityProfile referencingTip,
            List<O> rawObjectList,
            Function<O, RawArtifact> rawArtifactGetterFromRawObject,
            Function<O, T> artifactGetterFromRaw,
            Function<TrustmarkFrameworkIdentifiedObject, T> artifactGetterFromUri,
            Function<String, TrustmarkFrameworkIdentifiedObject> uriResolverFromArtifactName
    ) {
        // look for this artifact in the current set of raw objects
        referencedArtifactName = BulkImportUtils.defaultTrim(referencedArtifactName);
        for (O rawObject : rawObjectList) {
            RawArtifact rawArtifact = rawArtifactGetterFromRawObject.apply(rawObject);
            if (BulkImportUtils.defaultTrim(rawArtifact.moniker).equals(referencedArtifactName)
                    || BulkImportUtils.defaultTrim(rawArtifact.name).equalsIgnoreCase(referencedArtifactName)
            ) {
                return artifactGetterFromRaw.apply(rawObject);
            }
            if (BulkImportUtils.isValidUri(referencedArtifactName)) {
                try {
                    String urlString = this.context.generateIdentifierForTrustmarkDefinition(rawArtifact.moniker, rawArtifact.version).toString();
                    if (referencedArtifactName.equals(urlString)) {
                        return artifactGetterFromRaw.apply(rawObject);
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
        // If we get here, the reference is not an internal name
        TrustmarkFrameworkIdentifiedObject artfact = uriResolverFromArtifactName.apply(referencedArtifactName);
        if (artfact != null) {
            return artifactGetterFromUri.apply(artfact);
        }

        // If we get here, the reference could not be resolved
        O closestMatch = null;
        int closestDistance = Integer.MAX_VALUE;
        for (O rawObject : rawObjectList) {
            RawArtifact rawArtifact = rawArtifactGetterFromRawObject.apply(rawObject);
            if (rawObject == null || rawArtifact.name == null) {
                continue;
            }
            int distance = BulkImportUtils.levenshteinDistance(referencedArtifactName.toLowerCase(), rawArtifact.name.toLowerCase());
            if (closestMatch == null || distance < closestDistance) {
                closestMatch = rawObject;
                closestDistance = distance;
            }
        }
        RawArtifact closestMatchArtifact = closestMatch == null ? null : rawArtifactGetterFromRawObject.apply(closestMatch);
        String errorMessage = String.format(
                "Could not find a reference for %s: [%s] as referenced from TIP: [%s] in File: [%s], Line #%s. Closest match was: [%s]",
                closestMatch == null ? "" : closestMatchArtifact.getArtifactAbbr(),
                referencedArtifactName,
                referencingTip.name,
                referencingTip.excelFile,
                referencingTip.rowIndex,
                closestMatch == null ? "NONE FOUND" : closestMatchArtifact.name
        );
        logger.error("**** ERROR - " + errorMessage);
        throw new UnsupportedOperationException(errorMessage);
    }

    private TrustmarkDefinitionRequirementImpl findReferencedTd(String referencedTdName, RawTrustInteroperabilityProfile referencingTip) {
        return this.findReferencedArtifact(
                referencedTdName,
                referencingTip,
                this.rawTds,
                rawTd -> rawTd.metadata,
                this::assembleTdRequirement,
                this::assembleTdRequirement,
                this.context::resolveReferencedExternalTrustmarkDefinition
        );
    }

    private TrustmarkDefinitionRequirementImpl assembleTdRequirement(TrustmarkFrameworkIdentifiedObject td) {
        TrustmarkDefinitionRequirementImpl parsedTdRequirement = new TrustmarkDefinitionRequirementImpl();
        parsedTdRequirement.setIdentifier(td.getIdentifier());
        parsedTdRequirement.setVersion(td.getVersion());
        parsedTdRequirement.setDescription(td.getDescription());
        parsedTdRequirement.setName(td.getName());
        return parsedTdRequirement;
    }

    private TrustmarkDefinitionRequirementImpl assembleTdRequirement(RawTrustmarkDefinition rawTd) {
        TrustmarkDefinitionRequirementImpl parsedTdRequirement = new TrustmarkDefinitionRequirementImpl();
        parsedTdRequirement.setIdentifier(rawTd.metadata.identifier);
        parsedTdRequirement.setName(rawTd.metadata.name);
        parsedTdRequirement.setVersion(rawTd.metadata.version);
        parsedTdRequirement.setDescription(rawTd.metadata.description);
        parsedTdRequirement.setProviderReferences(this.context.getTrustmarkProviderReferences());
        return parsedTdRequirement;
    }

    private TrustInteroperabilityProfileReferenceImpl findReferencedTip(String referencedTipName, RawTrustInteroperabilityProfile referencingTip) {
        return this.findReferencedArtifact(
                referencedTipName,
                referencingTip,
                this.rawTips,
                rawTip -> rawTip,
                this::assembleTipReference,
                this::assembleTipReference,
                this.context::resolveReferencedExternalTrustInteroperabilityProfile
        );
    }

    private TrustInteroperabilityProfileReferenceImpl assembleTipReference(TrustmarkFrameworkIdentifiedObject tip) {
        TrustInteroperabilityProfileReferenceImpl parsedTipReference = new TrustInteroperabilityProfileReferenceImpl();
        parsedTipReference.setIdentifier(tip.getIdentifier());
        parsedTipReference.setDescription(tip.getDescription());
        parsedTipReference.setVersion(tip.getVersion());
        parsedTipReference.setName(tip.getName());
        return parsedTipReference;
    }

    private TrustInteroperabilityProfileReferenceImpl assembleTipReference(RawTrustInteroperabilityProfile rawTip) {
        TrustInteroperabilityProfileReferenceImpl parsedTipReference = new TrustInteroperabilityProfileReferenceImpl();
        parsedTipReference.setIdentifier(rawTip.id);
        parsedTipReference.setName(rawTip.name);
        parsedTipReference.setVersion(rawTip.version);
        parsedTipReference.setDescription(rawTip.description);
        return parsedTipReference;
    }

    private String assembleTrustExpression(String te, Map<String, String> TD_TO_XMLID_MAPPING, Map<String, String> TIP_TO_XMLID_MAPPING) {
        Map<String, String> replacements = new HashMap<>();
        Matcher tdMatcher = TD_REFERENCE_PATTERN.matcher(te);
        while (tdMatcher.find()) {
            String toReplace = tdMatcher.group(0);
            String tdName = BulkImportUtils.defaultTrim(tdMatcher.group(1));
            String refId = TD_TO_XMLID_MAPPING.get(tdName.toLowerCase());
            replacements.put(toReplace, refId);
        }

        Matcher tipMatcher = TIP_REFERENCE_PATTERN.matcher(te);
        while (tipMatcher.find()) {
            String toReplace = tipMatcher.group(0);
            String tipName = BulkImportUtils.defaultTrim(tipMatcher.group(1));
            String refId = TIP_TO_XMLID_MAPPING.get(tipName.toLowerCase());
            replacements.put(toReplace, refId);
        }

        for (String toReplace : replacements.keySet()) {
            String replacement = replacements.get(toReplace);
            if (replacement != null) {
                te = te.replace(toReplace, replacement);
            }
        }

        te = te.replaceAll("\\s+", " ").trim();  // Normalize this thing.
        return te;
    }


    ////////////////////////////////////////////////////////
    // Instance Methods - Private - Parsing Any Artifacts //
    ////////////////////////////////////////////////////////

    private void putAllArtifactTransientDataIfEnabled(RawArtifact rawArtifact, BulkReadArtifact parsedArtifact) {
        if (!this.getIsTransientDataCollectionEnabled()) {
            return;
        }
        parsedArtifact.getTransientDataMap().putAll(rawArtifact.transientDataMap);
    }


    ////////////////////////////////////////////////////////////////////
    // Static Fields - Defaults for Immutable Values on all Raw types //
    ////////////////////////////////////////////////////////////////////

    public static final boolean DEFAULT_BOOLEAN = false;
    public static final int DEFAULT_INT = -1;
    public static final String DEFAULT_STRING = null;
    public static final URI DEFAULT_URI = null;


    //////////////////////////////////////////////////////////////////
    // Static Methods - Default for Mutable Values on all Raw types //
    //////////////////////////////////////////////////////////////////

    public static <T> List<T> DEFAULT_LIST() {
        return new ArrayList<>();
    }

    public static <K, V> Map<K, V> DEFAULT_MAP() {
        return new HashMap<>();
    }

    public static <T extends RawObject> RawCollection<T> DEFAULT_RAW_LIST() {
        return new RawArrayList<>();
    }


    ///////////////////////////////////////////
    // Static Inner Classes - Raw Data Types //
    ///////////////////////////////////////////

    public static abstract class RawObject {
        @Override
        public String toString() {
            return this.toJson().toString();
        }

        public JSONObject toJson() {
            JSONObject result = new JSONObject();

            Field[] fields = this.getClass().getFields();
            for (Field field : fields) {
                Class<?> clazz = field.getType();
                boolean isRawObject = RawObject.class.isAssignableFrom(clazz);
                boolean isRawCollection = RawCollection.class.isAssignableFrom(clazz);
                int modifiers = field.getModifiers();
                boolean isPublic = Modifier.isPublic(modifiers);
                boolean isNonStatic = !Modifier.isStatic(modifiers);
                boolean isNonTransient = !Modifier.isTransient(modifiers);
                if (isPublic && isNonStatic && isNonTransient) {
                    try {
                        Object fieldValue = field.get(this);
                        boolean isNull = fieldValue == null;
                        if (isNull) {
                            fieldValue = JSONObject.NULL;
                        } else if (isRawObject) {
                            RawObject rawObjectValue = (RawObject) fieldValue;
                            fieldValue = rawObjectValue.toJson();
                        } else if (isRawCollection) {
                            RawCollection rawCollectionValue = (RawCollection) fieldValue;
                            fieldValue = rawCollectionValue.toJson();
                        }
                        String jsonKey = field.getName();
                        Object jsonValue = JSONObject.wrap(fieldValue);
                        result.put(jsonKey, jsonValue);
                    } catch (Exception ex) {
                    }
                }
            }

            return result;
        }
    }

    public static abstract class RawArtifact extends RawObject {
        public final transient Map<String, Object> transientDataMap = DEFAULT_MAP();
        public String debugLocation = DEFAULT_STRING;
        public String excelFile = DEFAULT_STRING;
        public int rowIndex = DEFAULT_INT;

        public String moniker = DEFAULT_STRING;
        public String name = DEFAULT_STRING;
        public String version = DEFAULT_STRING;

        public Boolean deprecated = DEFAULT_BOOLEAN;

        public final List<String> supersedesUris = DEFAULT_LIST();
        public final List<String> supersedesUrisResolved = DEFAULT_LIST();

        public final List<String> supersededByUris = DEFAULT_LIST();
        public final List<String> supersededByUrisResolved = DEFAULT_LIST();

        public final RawCollection<RawNameDescPair> terms = DEFAULT_RAW_LIST();
        public final List<String> termsInclude = DEFAULT_LIST();
        public final List<String> termsExclude = DEFAULT_LIST();
        public final Map<String, String> sources = DEFAULT_MAP();

        public abstract String getArtifactAbbr();

        public abstract String getEmptyXml();
    }

    public static interface RawCollection<T extends RawObject> extends Collection<T> {
        public JSONArray toJson();
    }

    public static class RawArrayList<T extends RawObject> extends ArrayList<T> implements RawCollection<T> {
        @Override
        public JSONArray toJson() {
            JSONArray result = new JSONArray();
            for (RawObject rawObject : this) {
                result.put(rawObject.toJson());
            }
            return result;
        }
    }

    public static class RawNameDescPair extends RawObject {
        public String name = DEFAULT_STRING;
        public String desc = DEFAULT_STRING;

        public RawNameDescPair(String n, String d) {
            this.name = n;
            this.desc = d;
        }
    }

    public static class RawTdParameter extends RawObject {
        public String id = DEFAULT_STRING;
        public String name = DEFAULT_STRING;
        public String description = DEFAULT_STRING;
        public String kindName = DEFAULT_STRING;
        public final List<String> enumValues = DEFAULT_LIST();
        public boolean isRequired = DEFAULT_BOOLEAN;
    }

    public static class RawTrustmarkDefinition extends RawObject {
        public int rowIndex = DEFAULT_INT;
        public final RawTdAssessmentStep assessmentStep = new RawTdAssessmentStep();
        public final RawTdCriterion criterion = new RawTdCriterion();
        public final RawTdMetadata metadata = new RawTdMetadata();
    }

    public static class RawTdAssessmentStep extends RawObject {
        public String id = DEFAULT_STRING;
        public String name = DEFAULT_STRING;
        public String description = DEFAULT_STRING;
        public final RawCollection<RawNameDescPair> artifacts = DEFAULT_RAW_LIST();
        public final RawCollection<RawTdParameter> parameters = DEFAULT_RAW_LIST();
    }

    public static class RawTdCriterion extends RawObject {
        public String id = DEFAULT_STRING;
        public String name = DEFAULT_STRING;
        public String description = DEFAULT_STRING;
        public final RawCollection<RawNameDescPair> citations = DEFAULT_RAW_LIST();
    }

    public static class RawTdMetadata extends RawArtifact {
        public String id = DEFAULT_STRING;
        public URI identifier = DEFAULT_URI;
        public String originalMoniker = DEFAULT_STRING;
        public String description = DEFAULT_STRING;
        public String publicationDateTime = DEFAULT_STRING;
        public String stakeholderDesc = DEFAULT_STRING;
        public String recipientDesc = DEFAULT_STRING;
        public String relyingPartyDesc = DEFAULT_STRING;
        public String providerDesc = DEFAULT_STRING;
        public String providerEligibilityCriteria = DEFAULT_STRING;
        public String assessorQualificationsDesc = DEFAULT_STRING;
        public String revocationCriteria = DEFAULT_STRING;
        public String extensionDesc = DEFAULT_STRING;
        public String extensionDescOriginal = DEFAULT_STRING;
        public String notes = DEFAULT_STRING;
        public String legalNotice = DEFAULT_STRING;
        public String criteriaPreface = DEFAULT_STRING;
        public String assessmentPreface = DEFAULT_STRING;
        public String issuanceCriteria = DEFAULT_STRING;

        public final List<String> keywords = DEFAULT_LIST();
        public final List<String> tips = DEFAULT_LIST();

        @Override
        public String getArtifactAbbr() {
            return "TD";
        }

        @Override
        public String getEmptyXml() {
            return XML_EMPTY_TD;
        }
    }

    public static class RawTrustInteroperabilityProfile extends RawArtifact {
        public String category = DEFAULT_STRING;
        public URI    id = DEFAULT_URI;
        public String description = DEFAULT_STRING;
        public String trustExpression = DEFAULT_STRING;
        public String primary = DEFAULT_STRING;
        public String publicationDateTime = DEFAULT_STRING;
        public String notes = DEFAULT_STRING;
        public String legalNotice = DEFAULT_STRING;
        public final List<String> keywords = DEFAULT_LIST();

        @Override
        public String getArtifactAbbr() {
            return "TIP";
        }

        @Override
        public String getEmptyXml() {
            return XML_EMPTY_TIP;
        }
    }
}
