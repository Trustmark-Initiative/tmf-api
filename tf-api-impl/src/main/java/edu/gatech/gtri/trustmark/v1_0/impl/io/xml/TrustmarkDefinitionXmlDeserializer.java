package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Comment;
import org.dom4j.Element;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkDefinitionXmlDeserializer extends AbstractDeserializer {

    private static final Logger log = Logger.getLogger(TrustmarkDefinitionXmlDeserializer.class);

    public static TrustmarkDefinition deserialize(String xml ) throws ParseException {
        log.debug("Request to deserialize TrustmarkDefinition XML...");

        Element root = XmlHelper.readWithDom4j(xml);
        if( !root.getQName().getName().equals("TrustmarkDefinition") ){
            throw new ParseException("Root element of a TrustmarkDefinition must be tf:TrustmarkDefinition, you have: "+root.getName());
        }

        TrustmarkDefinition td = fromDom4j(root, xml);
        return td;
    }//end deserialize()



    public static TrustmarkDefinition fromDom4j( Element tdXml, String originalSource ) throws ParseException {
        TrustmarkDefinitionImpl td = new TrustmarkDefinitionImpl();
        td.setOriginalSource(originalSource);
        td.setOriginalSourceType("text/xml");


        Element metadataXml = (Element) tdXml.selectSingleNode("./tf:Metadata");
        TrustmarkDefinitionMetadataImpl metadata = new TrustmarkDefinitionMetadataImpl();
        metadata.setTypeName("TrustmarkDefinition");
        metadata.setIdentifier(getUri(metadataXml, "./tf:Identifier", true));
        metadata.setTrustmarkReferenceAttributeName(getUri(metadataXml, "./tf:TrustmarkReferenceAttributeName", true));
        metadata.setName(getString(metadataXml, "./tf:Name", true));
        metadata.setVersion(getString(metadataXml, "./tf:Version", true));
        metadata.setDescription(getString(metadataXml, "./tf:Description", true));
        metadata.setPublicationDateTime(getDate(metadataXml, "./tf:PublicationDateTime", true));
        metadata.setTrustmarkDefiningOrganization(readEntity((Element) metadataXml.selectSingleNode("./tf:TrustmarkDefiningOrganization")));

        metadata.setTargetStakeholderDescription(getString(metadataXml, "./tf:TargetStakeholderDescription", false));
        metadata.setTargetRecipientDescription(getString(metadataXml, "./tf:TargetRecipientDescription", false));
        metadata.setTargetRelyingPartyDescription(getString(metadataXml, "./tf:TargetRelyingPartyDescription", false));
        metadata.setTargetProviderDescription(getString(metadataXml, "./tf:TargetProviderDescription", false));
        metadata.setProviderEligibilityCriteria(getString(metadataXml, "./tf:ProviderEligibilityCriteria", false));
        metadata.setAssessorQualificationsDescription(getString(metadataXml, "./tf:AssessorQualificationsDescription", false));
        metadata.setTrustmarkRevocationCriteria(getString(metadataXml, "./tf:TrustmarkRevocationCriteria", false));
        metadata.setExtensionDescription(getString(metadataXml, "./tf:ExtensionDescription", false));
        metadata.setLegalNotice(getString(metadataXml, "./tf:LegalNotice", false));
        metadata.setNotes(getString(metadataXml, "./tf:Notes", false));

        if( Boolean.TRUE.equals(metadataXml.selectObject("count(./tf:Supersessions/*) > 0")) ){
            List<Element> supersedesList = metadataXml.selectNodes("./tf:Supersessions/tf:Supersedes");
            if( supersedesList != null && !supersedesList.isEmpty() ){
                for( Element supersedesElement : supersedesList ){
                    metadata.addToSupersedes(readTrustmarkFrameworkIdentifiedObject(supersedesElement));
                }
            }
            List<Element> supersededByList = metadataXml.selectNodes("./tf:Supersessions/tf:SupersededBy");
            if( supersededByList != null && !supersededByList.isEmpty() ){
                for( Element supersededByElement : supersededByList ){
                    metadata.addToSupersededBy(readTrustmarkFrameworkIdentifiedObject(supersededByElement));
                }
            }
        }

        if( "true".equalsIgnoreCase( (String) metadataXml.selectObject("string(./tf:Deprecated)") ) ){
            metadata.setDeprecated(Boolean.TRUE);
        }else{
            metadata.setDeprecated(Boolean.FALSE);
        }

        List<Element> satisfiesList = metadataXml.selectNodes("./tf:Satisfactions/tf:Satisfies");
        if( satisfiesList != null && !satisfiesList.isEmpty() ){
            for( Element satisfiesElement : satisfiesList ){
                metadata.addToSatisfies(readTrustmarkFrameworkIdentifiedObject(satisfiesElement));
            }
        }


        List<Element> knownConflictsList = metadataXml.selectNodes("./tf:KnownConflicts/tf:KnownConflict");
        if( knownConflictsList != null && !knownConflictsList.isEmpty() ){
            for( Element knownConflictElement : knownConflictsList ){
                metadata.addToKnownConflicts(readTrustmarkFrameworkIdentifiedObject(knownConflictElement));
            }
        }

        if( Boolean.TRUE.equals(metadataXml.selectObject("count(./tf:Keywords/*) > 0")) ){
            List<Element> keywordsList = metadataXml.selectNodes("./tf:Keywords/tf:Keyword");
            if( keywordsList != null && !keywordsList.isEmpty() ){
                for( Element keywordElement : keywordsList ){
                    metadata.addToKeywords(getString(keywordElement, ".", true));
                }
            }
        }

        parseComments(tdXml, metadata);

        td.setMetadata(metadata);



        List<Element> termsXmlList = tdXml.selectNodes("./tf:Terms/tf:Term");
        if( termsXmlList != null && !termsXmlList.isEmpty() ){
            for( Element term : termsXmlList ){
                td.addTerm(readTerm(term));
            }
        }

        HashMap<String, Source> sourceMap = new HashMap<>();
        List<Element> sourcesXmlList = tdXml.selectNodes("./tf:Sources/tf:Source");
        if( sourcesXmlList != null && !sourcesXmlList.isEmpty() ){
            for( Element sourceXml : sourcesXmlList ){
                Source source = readSource(sourceXml);
                td.addSource(source);
                String id = getString(sourceXml, "./@tf:id", true);
                sourceMap.put(id, source);
            }
        }

        td.setConformanceCriteriaPreface(getString(tdXml, "./tf:ConformanceCriteria/tf:Preface", false));
        HashMap<String, ConformanceCriterion> criteriaMap = new HashMap<>();
        List<Element> criteriaXmlList = tdXml.selectNodes("./tf:ConformanceCriteria/tf:ConformanceCriterion");
        if( criteriaXmlList != null && !criteriaXmlList.isEmpty() ){
            for( Element critXml : criteriaXmlList ){
                ConformanceCriterion crit = readConformanceCriterion(critXml, sourceMap);
                td.addConformanceCriterion(crit);
                criteriaMap.put(crit.getId(), crit);
            }
        }


        td.setAssessmentStepPreface(getString(tdXml, "./tf:AssessmentSteps/tf:Preface", false));
        List<Element> assStepXmlList = tdXml.selectNodes("./tf:AssessmentSteps/tf:AssessmentStep");
        if( assStepXmlList != null && !assStepXmlList.isEmpty() ){
            for( Element assStepXml : assStepXmlList ){
                td.addAssessmentStep(readAssessmentStep(assStepXml, criteriaMap));
            }
        }

        td.setIssuanceCriteria(getString(tdXml, "./tf:IssuanceCriteria", false));

        return td;
    }//end fromDom4j()


    private static void parseComments(Element tdXml, TrustmarkDefinitionMetadataImpl metadata) throws ParseException {
        // FIXME Do we remove importing XML comments for supersedes and keywords from 1.1 functionality?
        List<Comment> comments = tdXml.getDocument().selectNodes("//comment()");
        if( comments != null && comments.size() > 0 ){
            for( Comment comment : comments ){
                String commentText = comment.getText();
                if( commentText != null )
                    commentText = commentText.trim();
                else
                    commentText = "";
                if( commentText.contains("Supersedes ") ){
                    String supersedesId = commentText.split(Pattern.quote("Supersedes "))[1];
                    TrustmarkFrameworkIdentifiedObjectImpl tfi = new TrustmarkFrameworkIdentifiedObjectImpl();
                    try {
                        tfi.setIdentifier(new URI(supersedesId));
                    }catch(URISyntaxException urise){
                        throw new ParseException("Invalid Trustmark Definition Reference Identifier: "+supersedesId, urise);
                    }
                    tfi.setTypeName("TrustmarkDefinitionReference");
                    metadata.addToSupersedes(tfi);
                }else if( commentText.startsWith("KEYWORD: ") ){
                    String keyword = commentText.substring("KEYWORD: ".length());
                    metadata.addToKeywords(keyword);
                }else {
                    log.debug("Skipping unknown comment found in TD [" + comment.getText() + "]");
                }
            }
        }
    }

    protected static TrustmarkDefinitionParameter readTrustmarkDefinitionParameter(Element asXml ) throws ParseException {
        TrustmarkDefinitionParameterImpl paramImpl = new TrustmarkDefinitionParameterImpl();
        paramImpl.setIdentifier(getString(asXml, "./tf:Identifier", true));
        paramImpl.setName(getString(asXml, "./tf:Name", true));
        paramImpl.setDescription(getString(asXml, "./tf:Description", true));
        paramImpl.setParameterKind(ParameterKind.fromString(getString(asXml, "./tf:ParameterKind", true)));
        if( Boolean.TRUE.equals(asXml.selectObject("count(./tf:EnumValues/tf:EnumValue) > 0")) ){
            List<Element> enumValueList = asXml.selectNodes("./tf:EnumValues/tf:EnumValue");
            if( enumValueList != null && !enumValueList.isEmpty() ){
                for( Element enumValueXml : enumValueList ){
                    paramImpl.addEnumValue( (String) enumValueXml.selectObject("string(.)"));
                }
            }
        }
        String requiredStringVal = (String) asXml.selectObject("string(./tf:Required)");
        if( requiredStringVal != null && requiredStringVal.trim().length() > 0 ) {
            paramImpl.setRequired(Boolean.parseBoolean(requiredStringVal.trim()));
        }else{
            paramImpl.setRequired(false);
        }
        return paramImpl;
    }

    protected static AssessmentStep readAssessmentStep( Element asXml, HashMap<String, ConformanceCriterion> criteriaMap ) throws ParseException {
        AssessmentStepImpl assStep = new AssessmentStepImpl();
        assStep.setId(getString(asXml, "./@tf:id", true));
        assStep.setNumber(getNumber(asXml, "./tf:Number", true).intValue());
        assStep.setName(getString(asXml, "./tf:Name", true));
        assStep.setDescription(getString(asXml, "./tf:Description", true));
        List<Element> criteriaRefElements = asXml.selectNodes("./tf:ConformanceCriterion");
        if( criteriaRefElements != null && !criteriaRefElements.isEmpty() ){
            for( Element critRefXml : criteriaRefElements ){
                String refVal = getString(critRefXml, "./@tf:ref", false);
                if( StringUtils.isBlank(refVal) )
                    throw new ParseException("For AssessmentStep["+assStep.getNumber()+"]: '"+assStep.getName()+"', a conformance criteria reference is missing it's @tf:ref attribute.");
                ConformanceCriterion crit = criteriaMap.get(refVal);
                if( crit == null )
                    throw new ParseException("For AssessmentStep["+assStep.getNumber()+"]: '"+assStep.getName()+"', the referenced Conformance Criteria '"+refVal+"' does not exist.");
                assStep.addConformanceCriterion(crit);
            }
        }

        List<Element> artifactXmlList = asXml.selectNodes("./tf:Artifact");
        if( artifactXmlList != null && !artifactXmlList.isEmpty() ){
            for( Element artifactXml : artifactXmlList ){
                assStep.addArtifact(readArtifact(artifactXml));
            }
        }

        HashMap<String, TrustmarkDefinitionParameter> paramMap = new HashMap<>();
        List<Element> parameterDefElements = asXml.selectNodes("./tf:ParameterDefinitions/tf:ParameterDefinition");
        if( parameterDefElements != null && !parameterDefElements.isEmpty() ){
            for( Element paramDefElement : parameterDefElements ){
                TrustmarkDefinitionParameter param = readTrustmarkDefinitionParameter(paramDefElement);
                assStep.addParameter(param);
            }
        }

        return assStep;
    }//end readAssessmentStep

    protected static Artifact readArtifact(Element artifactXml) throws ParseException {
        ArtifactImpl artifact = new ArtifactImpl();
        artifact.setName(getString(artifactXml, "./tf:Name", true));
        artifact.setDescription(getString(artifactXml, "./tf:Description", true));
        return artifact;
    }

    protected static ConformanceCriterion readConformanceCriterion( Element critXml, HashMap<String, Source> sources ) throws ParseException {
        ConformanceCriterionImpl crit = new ConformanceCriterionImpl();
        crit.setNumber(getNumber(critXml, "./tf:Number", true).intValue());
        crit.setName(getString(critXml, "./tf:Name", true));
        crit.setDescription(getString(critXml, "./tf:Description", true));
        List<Element> citationsXmlList = critXml.selectNodes("./tf:Citation");
        if( citationsXmlList != null && !citationsXmlList.isEmpty() ){
            for( Element citationXml : citationsXmlList ){
                crit.addCitation(readCitation(citationXml, sources));
            }
        }
        String id = getString(critXml, "./@tf:id", true);
        crit.setId(id);
        return crit;
    }//end readConformanceCriterion()

    protected static Citation readCitation( Element citationXml, HashMap<String, Source> sources ) throws ParseException {
        CitationImpl citation = new CitationImpl();
        String sourceRef = citationXml.selectObject("string(./tf:Source/@tf:ref)").toString();
        if(StringUtils.isBlank(sourceRef) )
            throw new ParseException("Citation has an empty Source/@ref value.  This is not allowed.");
        Source source = sources.get(sourceRef);
        if( source == null )
            throw new ParseException("Citation references Source["+sourceRef+"], which cannot be found.");
        citation.setSource(source);
        citation.setDescription(getString(citationXml, "./tf:Description", false));
        return citation;
    }//end readCitation()

}
