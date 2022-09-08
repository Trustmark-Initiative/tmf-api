package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.ArtifactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.AssessmentStepImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.CitationImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ConformanceCriterionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Artifact;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getDate;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getNumber;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getString;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getUri;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readEntity;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readSource;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readTerm;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readTrustmarkFrameworkIdentifiedObject;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Implementations deserialize XML and, optionally, a URI into a Trustmark
 * Definition.
 *
 * @author GTRI Trustmark Team
 */
public class TrustmarkDefinitionXmlDeserializer implements XmlDeserializer<TrustmarkDefinition> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkDefinitionXmlDeserializer.class);

    private final boolean withTerms;

    public TrustmarkDefinitionXmlDeserializer(final boolean withTerms) {
        this.withTerms = withTerms;
    }

    public TrustmarkDefinition deserialize(final String xml, final URI uri) throws ParseException {

        requireNonNull(xml);

        log.debug("Deserializing Trustmark Definition XML . . .");

        final Element element = XmlHelper.readWithDom4j(xml);
        final String elementName = "TrustmarkDefinition";

        if (!element.getQName().getName().equals(elementName)) {
            throw new ParseException(format("The document element of a Trustmark Definition must be '%s'; it was '%s'.", elementName, element.getName()));

        }

        return fromDom4j(element, uri, withTerms);
    }

    public static TrustmarkDefinition fromDom4j(final Element element, final URI uri, final boolean withTerms) throws ParseException {

        final TrustmarkDefinitionImpl trustmarkDefinition = new TrustmarkDefinitionImpl();

        trustmarkDefinition.setId(getString(element, "./@tf:id", false));

        Element metadataXml = (Element) element.selectSingleNode("./tf:Metadata");

        trustmarkDefinition.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUSTMARK_DEFINITION);
        trustmarkDefinition.setIdentifier(getUri(metadataXml, "./tf:Identifier", true));

        trustmarkDefinition.setName(getString(metadataXml, "./tf:Name", true));
        trustmarkDefinition.setVersion(getString(metadataXml, "./tf:Version", true));
        trustmarkDefinition.setDescription(getString(metadataXml, "./tf:Description", true));
        trustmarkDefinition.setPublicationDateTime(getDate(metadataXml, "./tf:PublicationDateTime", true));
        trustmarkDefinition.setTrustmarkDefiningOrganization(readEntity((Element) metadataXml.selectSingleNode("./tf:TrustmarkDefiningOrganization")));

        trustmarkDefinition.setTargetStakeholderDescription(getString(metadataXml, "./tf:TargetStakeholderDescription", false));
        trustmarkDefinition.setTargetRecipientDescription(getString(metadataXml, "./tf:TargetRecipientDescription", false));
        trustmarkDefinition.setTargetRelyingPartyDescription(getString(metadataXml, "./tf:TargetRelyingPartyDescription", false));
        trustmarkDefinition.setTargetProviderDescription(getString(metadataXml, "./tf:TargetProviderDescription", false));
        trustmarkDefinition.setProviderEligibilityCriteria(getString(metadataXml, "./tf:ProviderEligibilityCriteria", false));
        trustmarkDefinition.setAssessorQualificationsDescription(getString(metadataXml, "./tf:AssessorQualificationsDescription", false));
        trustmarkDefinition.setTrustmarkRevocationCriteria(getString(metadataXml, "./tf:TrustmarkRevocationCriteria", false));
        trustmarkDefinition.setExtensionDescription(getString(metadataXml, "./tf:ExtensionDescription", false));
        trustmarkDefinition.setLegalNotice(getString(metadataXml, "./tf:LegalNotice", false));
        trustmarkDefinition.setNotes(getString(metadataXml, "./tf:Notes", false));

        if (Boolean.TRUE.equals(metadataXml.selectObject("count(./tf:Supersessions/*) > 0"))) {
            List<Node> supersedesList = metadataXml.selectNodes("./tf:Supersessions/tf:Supersedes");
            if (supersedesList != null && !supersedesList.isEmpty()) {
                for (Node supersedesElement : supersedesList) {
                    trustmarkDefinition.addToSupersedes(readTrustmarkFrameworkIdentifiedObject(supersedesElement));
                }
            }
            List<Node> supersededByList = metadataXml.selectNodes("./tf:Supersessions/tf:SupersededBy");
            if (supersededByList != null && !supersededByList.isEmpty()) {
                for (Node supersededByElement : supersededByList) {
                    trustmarkDefinition.addToSupersededBy(readTrustmarkFrameworkIdentifiedObject(supersededByElement));
                }
            }
        }

        if ("true".equalsIgnoreCase((String) metadataXml.selectObject("string(./tf:Deprecated)"))) {
            trustmarkDefinition.setDeprecated(Boolean.TRUE);
        } else {
            trustmarkDefinition.setDeprecated(Boolean.FALSE);
        }

        List<Node> satisfiesList = metadataXml.selectNodes("./tf:Satisfactions/tf:Satisfies");
        if (satisfiesList != null && !satisfiesList.isEmpty()) {
            for (Node satisfiesElement : satisfiesList) {
                trustmarkDefinition.addToSatisfies(readTrustmarkFrameworkIdentifiedObject(satisfiesElement));
            }
        }


        List<Node> knownConflictsList = metadataXml.selectNodes("./tf:KnownConflicts/tf:KnownConflict");
        if (knownConflictsList != null && !knownConflictsList.isEmpty()) {
            for (Node knownConflictElement : knownConflictsList) {
                trustmarkDefinition.addToKnownConflict(readTrustmarkFrameworkIdentifiedObject(knownConflictElement));
            }
        }

        if (Boolean.TRUE.equals(metadataXml.selectObject("count(./tf:Keywords/*) > 0"))) {
            List<Node> keywordsList = metadataXml.selectNodes("./tf:Keywords/tf:Keyword");
            if (keywordsList != null && !keywordsList.isEmpty()) {
                for (Node keywordElement : keywordsList) {
                    trustmarkDefinition.addToKeywords(getString(keywordElement, ".", true));
                }
            }
        }

        parseComments(element, trustmarkDefinition);

        if (withTerms) {
            List<Node> termsXmlList = element.selectNodes("./tf:Terms/tf:Term");
            if (termsXmlList != null && !termsXmlList.isEmpty()) {
                for (Node term : termsXmlList) {
                    trustmarkDefinition.addTerm(readTerm(term));
                }
            }
        }

        HashMap<String, Source> sourceMap = new HashMap<>();
        List<Node> sourcesXmlList = element.selectNodes("./tf:Sources/tf:Source");
        if (sourcesXmlList != null && !sourcesXmlList.isEmpty()) {
            for (Node sourceXml : sourcesXmlList) {
                Source source = readSource(sourceXml);
                trustmarkDefinition.addSource(source);
                String id = getString(sourceXml, "./@tf:id", true);
                sourceMap.put(id, source);
            }
        }

        trustmarkDefinition.setConformanceCriteriaPreface(getString(element, "./tf:ConformanceCriteria/tf:Preface", false));
        HashMap<String, ConformanceCriterion> criteriaMap = new HashMap<>();
        List<Node> criteriaXmlList = element.selectNodes("./tf:ConformanceCriteria/tf:ConformanceCriterion");
        if (criteriaXmlList != null && !criteriaXmlList.isEmpty()) {
            for (Node critXml : criteriaXmlList) {
                ConformanceCriterion crit = readConformanceCriterion(critXml, sourceMap);
                trustmarkDefinition.addConformanceCriterion(crit);
                criteriaMap.put(crit.getId(), crit);
            }
        }


        trustmarkDefinition.setAssessmentStepPreface(getString(element, "./tf:AssessmentSteps/tf:Preface", false));
        List<Node> assStepXmlList = element.selectNodes("./tf:AssessmentSteps/tf:AssessmentStep");
        if (assStepXmlList != null && !assStepXmlList.isEmpty()) {
            for (Node assStepXml : assStepXmlList) {
                trustmarkDefinition.addAssessmentStep(readAssessmentStep(assStepXml, criteriaMap));
            }
        }

        trustmarkDefinition.setIssuanceCriteria(getString(element, "./tf:IssuanceCriteria", false));

        return trustmarkDefinition;
    }

    private static void parseComments(Element tdXml, TrustmarkDefinitionImpl metadata) throws ParseException {
        // FIXME Do we remove importing XML comments for supersedes and keywords from 1.1 functionality?
        List<Node> comments = tdXml.getDocument().selectNodes("//comment()");
        if (comments != null && comments.size() > 0) {
            for (Node comment : comments) {
                String commentText = comment.getText();
                if (commentText != null)
                    commentText = commentText.trim();
                else
                    commentText = "";
                if (commentText.contains("Supersedes ")) {
                    String supersedesId = commentText.split(Pattern.quote("Supersedes "))[1];
                    TrustmarkFrameworkIdentifiedObjectImpl tfi = new TrustmarkFrameworkIdentifiedObjectImpl();
                    try {
                        tfi.setIdentifier(new URI(supersedesId));
                    } catch (URISyntaxException urise) {
                        throw new ParseException("Invalid Trustmark Definition Reference Identifier: " + supersedesId, urise);
                    }
                    tfi.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUSTMARK_DEFINITION_REQUIREMENT);
                    metadata.addToSupersedes(tfi);
                } else if (commentText.startsWith("KEYWORD: ")) {
                    String keyword = commentText.substring("KEYWORD: ".length());
                    metadata.addToKeywords(keyword);
                } else {
                    log.debug("Skipping unknown comment found in TD [" + comment.getText() + "]");
                }
            }
        }
    }

    protected static TrustmarkDefinitionParameter readTrustmarkDefinitionParameter(Node asXml) throws ParseException {
        TrustmarkDefinitionParameterImpl paramImpl = new TrustmarkDefinitionParameterImpl();
        paramImpl.setIdentifier(getString(asXml, "./tf:Identifier", true));
        paramImpl.setName(getString(asXml, "./tf:Name", true));
        paramImpl.setDescription(getString(asXml, "./tf:Description", true));
        paramImpl.setParameterKind(ParameterKind.fromString(getString(asXml, "./tf:ParameterKind", true)));
        if (Boolean.TRUE.equals(asXml.selectObject("count(./tf:EnumValues/tf:EnumValue) > 0"))) {
            List<Node> enumValueList = asXml.selectNodes("./tf:EnumValues/tf:EnumValue");
            if (enumValueList != null && !enumValueList.isEmpty()) {
                for (Node enumValueXml : enumValueList) {
                    paramImpl.addEnumValue((String) enumValueXml.selectObject("string(.)"));
                }
            }
        }
        String requiredStringVal = (String) asXml.selectObject("string(./tf:Required)");
        if (requiredStringVal != null && requiredStringVal.trim().length() > 0) {
            paramImpl.setRequired(Boolean.parseBoolean(requiredStringVal.trim()));
        } else {
            paramImpl.setRequired(false);
        }
        return paramImpl;
    }

    protected static AssessmentStep readAssessmentStep(Node asXml, HashMap<String, ConformanceCriterion> criteriaMap) throws ParseException {
        AssessmentStepImpl assStep = new AssessmentStepImpl();
        assStep.setId(getString(asXml, "./@tf:id", true));
        assStep.setNumber(getNumber(asXml, "./tf:Number", true).intValue());
        assStep.setName(getString(asXml, "./tf:Name", true));
        assStep.setDescription(getString(asXml, "./tf:Description", true));
        List<Node> criteriaRefElements = asXml.selectNodes("./tf:ConformanceCriterion");
        if (criteriaRefElements != null && !criteriaRefElements.isEmpty()) {
            for (Node critRefXml : criteriaRefElements) {
                String refVal = getString(critRefXml, "./@tf:ref", false);
                if (StringUtils.isBlank(refVal))
                    throw new ParseException("For AssessmentStep[" + assStep.getNumber() + "]: '" + assStep.getName() + "', a conformance criteria reference is missing it's @tf:ref attribute.");
                ConformanceCriterion crit = criteriaMap.get(refVal);
                if (crit == null)
                    throw new ParseException("For AssessmentStep[" + assStep.getNumber() + "]: '" + assStep.getName() + "', the referenced Conformance Criteria '" + refVal + "' does not exist.");
                assStep.addConformanceCriterion(crit);
            }
        }

        List<Node> artifactXmlList = asXml.selectNodes("./tf:Artifact");
        if (artifactXmlList != null && !artifactXmlList.isEmpty()) {
            for (Node artifactXml : artifactXmlList) {
                assStep.addArtifact(readArtifact(artifactXml));
            }
        }

        HashMap<String, TrustmarkDefinitionParameter> paramMap = new HashMap<>();
        List<Node> parameterDefElements = asXml.selectNodes("./tf:ParameterDefinitions/tf:ParameterDefinition");
        if (parameterDefElements != null && !parameterDefElements.isEmpty()) {
            for (Node paramDefElement : parameterDefElements) {
                TrustmarkDefinitionParameter param = readTrustmarkDefinitionParameter(paramDefElement);
                assStep.addParameter(param);
            }
        }

        return assStep;
    }//end readAssessmentStep

    protected static Artifact readArtifact(Node artifactXml) throws ParseException {
        ArtifactImpl artifact = new ArtifactImpl();
        artifact.setName(getString(artifactXml, "./tf:Name", true));
        artifact.setDescription(getString(artifactXml, "./tf:Description", true));
        return artifact;
    }

    protected static ConformanceCriterion readConformanceCriterion(Node critXml, HashMap<String, Source> sources) throws ParseException {
        ConformanceCriterionImpl crit = new ConformanceCriterionImpl();
        crit.setNumber(getNumber(critXml, "./tf:Number", true).intValue());
        crit.setName(getString(critXml, "./tf:Name", true));
        crit.setDescription(getString(critXml, "./tf:Description", true));
        List<Node> citationsXmlList = critXml.selectNodes("./tf:Citation");
        if (citationsXmlList != null && !citationsXmlList.isEmpty()) {
            for (Node citationXml : citationsXmlList) {
                crit.addCitation(readCitation(citationXml, sources));
            }
        }
        String id = getString(critXml, "./@tf:id", true);
        crit.setId(id);
        return crit;
    }//end readConformanceCriterion()

    protected static Citation readCitation(Node citationXml, HashMap<String, Source> sources) throws ParseException {
        CitationImpl citation = new CitationImpl();
        String sourceRef = citationXml.selectObject("string(./tf:Source/@tf:ref)").toString();
        if (StringUtils.isBlank(sourceRef))
            throw new ParseException("Citation has an empty Source/@ref value.  This is not allowed.");
        Source source = sources.get(sourceRef);
        if (source == null)
            throw new ParseException("Citation references Source[" + sourceRef + "], which cannot be found.");
        citation.setSource(source);
        citation.setDescription(getString(citationXml, "./tf:Description", false));
        return citation;
    }//end readCitation()

}
