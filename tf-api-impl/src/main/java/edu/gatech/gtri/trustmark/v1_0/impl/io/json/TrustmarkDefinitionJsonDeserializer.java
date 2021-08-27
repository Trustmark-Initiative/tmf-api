package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkDefinitionJsonDeserializer extends AbstractDeserializer {

    private static final Logger log = LogManager.getLogger(TrustmarkDefinitionJsonDeserializer.class);


    public static TrustmarkDefinition deserialize(String jsonString ) throws ParseException {
        log.debug("Deserializing TrustmarkDefinition JSON...");

        JSONObject jsonObject = new JSONObject(jsonString);
        isSupported(jsonObject);

        TrustmarkDefinitionImpl td = new TrustmarkDefinitionImpl();

        td.setOriginalSource(jsonString);
        td.setOriginalSourceType("application/json");
        td.setId(getString(jsonObject, "$id", false));

        JSONObject metadataJson = jsonObject.optJSONObject("Metadata");
        if( metadataJson == null )
            throw new ParseException("A TrustmarkDefinition JSON object is required to have a sub-object named 'Metadata'.");


        TrustmarkDefinitionMetadataImpl metadata = new TrustmarkDefinitionMetadataImpl();
        metadata.setTypeName("TrustmarkDefinition");
        metadata.setIdentifier(getUri(metadataJson, "Identifier", true));
        //metadata.setTrustmarkReferenceAttributeName(getUri(metadataJson, "TrustmarkReferenceAttributeName", true));
        metadata.setName(getString(metadataJson, "Name", true));
        metadata.setVersion(getString(metadataJson, "Version", true));
        metadata.setDescription(getString(metadataJson, "Description", true));
        metadata.setPublicationDateTime(getDate(metadataJson, "PublicationDateTime", true));
        metadata.setTrustmarkDefiningOrganization(readEntity(metadataJson, "TrustmarkDefiningOrganization", true));

        metadata.setTargetStakeholderDescription(getString(metadataJson, "TargetStakeholderDescription", false));
        metadata.setTargetRecipientDescription(getString(metadataJson, "TargetRecipientDescription", false));
        metadata.setTargetRelyingPartyDescription(getString(metadataJson, "TargetRelyingPartyDescription", false));
        metadata.setTargetProviderDescription(getString(metadataJson, "TargetProviderDescription", false));
        metadata.setProviderEligibilityCriteria(getString(metadataJson, "ProviderEligibilityCriteria", false));
        metadata.setAssessorQualificationsDescription(getString(metadataJson, "AssessorQualificationsDescription", false));
        metadata.setTrustmarkRevocationCriteria(getString(metadataJson, "TrustmarkRevocationCriteria", false));
        metadata.setExtensionDescription(getString(metadataJson, "ExtensionDescription", false));
        metadata.setLegalNotice(getString(metadataJson, "LegalNotice", false));
        metadata.setNotes(getString(metadataJson, "Notes", false));
        if( metadataJson.has("Deprecated") ){
            log.debug("Setting Deprecated to: " + metadataJson.getBoolean("Deprecated"));
            metadata.setDeprecated(metadataJson.getBoolean("Deprecated"));
        }else{
            log.debug("Setting Deprecated to false");
            metadata.setDeprecated(false);
        }

        td.setMetadata(metadata);

        JSONObject supersessionObj = metadataJson.optJSONObject("Supersessions");
        if( supersessionObj != null ){
            log.debug("Encountered Metadata -> Supersessions...");
            JSONArray supersedesArrayJson = supersessionObj.optJSONArray("Supersedes");
            if( supersedesArrayJson != null ){
                for( int supersedesIdx = 0; supersedesIdx < supersedesArrayJson.length(); supersedesIdx++ ){
                    log.debug("Encountered Metadata -> Supersessions -> Supersedes["+supersedesIdx+"]...");
                    metadata.addToSupersedes(readTFIFDirectly(supersedesArrayJson.optJSONObject(supersedesIdx)));
                }
            }
            JSONArray supersededByArrayJson = supersessionObj.optJSONArray("SupersededBy");
            if( supersededByArrayJson != null ){
                for( int supersededByIdx = 0; supersededByIdx < supersededByArrayJson.length(); supersededByIdx++ ){
                    log.debug("Encountered Metadata -> Supersessions -> SupersededBy["+supersededByIdx+"]...");
                    metadata.addToSupersededBy(readTFIFDirectly(supersededByArrayJson.optJSONObject(supersededByIdx)));
                }
            }
        }
        log.debug("There are "+metadata.getSupersededBy().size()+" superseded by references and "+metadata.getSupersedes().size()+" supersedes references");


        JSONArray satisfiesArray = metadataJson.optJSONArray("Satisfies");
        if( satisfiesArray == null && metadataJson.has("Satisfies") ){
            satisfiesArray = new JSONArray();
            satisfiesArray.put(metadataJson.getJSONObject("Satisfies"));
        }
        if( satisfiesArray != null && satisfiesArray.length() > 0 ){
            for( int i = 0; i < satisfiesArray.length(); i++ ){
                JSONObject satisfiesRefJSON = satisfiesArray.getJSONObject(i);
                metadata.addToSatisfies(readTFIFDirectly(satisfiesRefJSON));
            }
        }
        log.debug("There are "+metadata.getSatisfies().size()+" satisfies references.");

        JSONArray knownConflictsArray = metadataJson.optJSONArray("KnownConflicts");
        if( knownConflictsArray != null && knownConflictsArray.length() > 0 ){
            for( int i = 0; i < knownConflictsArray.length(); i++ ){
                JSONObject knownConclitsRefJSON = knownConflictsArray.optJSONObject(i);
                metadata.addToKnownConflicts(readTFIFDirectly(knownConclitsRefJSON));
            }
        }

        JSONArray keywordsArrayJson = metadataJson.optJSONArray("Keywords");
        if( keywordsArrayJson != null ){
            for( int keywordIndex = 0; keywordIndex < keywordsArrayJson.length(); keywordIndex++ ){
                String keyword = (String) keywordsArrayJson.get(keywordIndex);
                metadata.addToKeywords(keyword);
            }
        }
        log.debug("There are "+metadata.getKeywords().size()+" keywords");

        JSONArray termsArrayJson = jsonObject.optJSONArray("Terms");
        if( termsArrayJson != null ){
            for( int termIndex = 0; termIndex < termsArrayJson.length(); termIndex++ ){
                JSONObject termJson = (JSONObject) termsArrayJson.get(termIndex);
                td.addTerm(readTerm(termJson));
            }
        }

        HashMap<String, SourceImpl> sourceMap = new HashMap<String, SourceImpl>();
        JSONArray sourcesArrayJson = jsonObject.optJSONArray("Sources");
        if( sourcesArrayJson != null ){
            for( int sourcesIndex = 0; sourcesIndex < sourcesArrayJson.length(); sourcesIndex++ ){
                JSONObject sourceJson = sourcesArrayJson.getJSONObject(sourcesIndex);
                SourceImpl source = readSource(sourceJson);
                td.addSource( source );
                String id = getString(sourceJson, "$id", true);
                sourceMap.put(id, source);
            }
        }

        td.setConformanceCriteriaPreface(getString(jsonObject, "ConformanceCriteriaPreface", false));
        HashMap<String, ConformanceCriterionImpl> criteriaMap = new HashMap<String, ConformanceCriterionImpl>();
        JSONArray critArrayJson = jsonObject.optJSONArray("ConformanceCriteria");
        if( critArrayJson != null ){
            for( int i = 0; i < critArrayJson.length(); i++ ){
                JSONObject critJson = critArrayJson.getJSONObject(i);
                ConformanceCriterionImpl crit = readConformanceCriterion(critJson, sourceMap);
                td.addConformanceCriterion( crit );
                criteriaMap.put(crit.getId(), crit);
            }
        }

        td.setAssessmentStepPreface(getString(jsonObject, "AssessmentStepsPreface", false));
        JSONArray stepArrayJson = jsonObject.optJSONArray("AssessmentSteps");
        if( stepArrayJson != null ){
            for( int i = 0; i < stepArrayJson.length(); i++ ){
                JSONObject stepJson = stepArrayJson.getJSONObject(i);
                AssessmentStep step = readAssessmentStep(stepJson, criteriaMap);
                td.addAssessmentStep(step);
            }
        }

        td.setIssuanceCriteria(getString(jsonObject, "IssuanceCriteria", true));

        log.info("The JSON TD was successfully deserialized.");
        return td;
    }//end deserialize


    protected static AssessmentStepImpl readAssessmentStep(JSONObject stepJson, HashMap<String, ConformanceCriterionImpl> criteriaMap) throws ParseException {
        AssessmentStepImpl assessmentStep = new AssessmentStepImpl();

        assessmentStep.setId(getString(stepJson, "$id", true));
        assessmentStep.setNumber(getNumber(stepJson, "Number", true).intValue());
        assessmentStep.setName(getString(stepJson, "Name", true));
        assessmentStep.setDescription(getString(stepJson, "Description", true));

        if( stepJson.has("ConformanceCriteria") ){
            JSONArray critReferences = stepJson.getJSONArray("ConformanceCriteria");
            for( int i = 0; i < critReferences.length(); i++ ){
                JSONObject critRefObj = critReferences.getJSONObject(i);
                String critRef = critRefObj.optString("$ref");
                if( critRef != null && critRef.startsWith("#") )
                    critRef = critRef.substring(1);
                ConformanceCriterionImpl crit = criteriaMap.get(critRef);
                if( crit == null )
                    throw new ParseException("Coult not find any ConformanceCriterion with ref '"+critRef+"', as referenced by step '"+ assessmentStep.getName()+"'");
                assessmentStep.addConformanceCriterion(crit);
            }
        }

        if( stepJson.has("Artifacts") ){
            JSONArray artifactJsonArray = stepJson.getJSONArray("Artifacts");
            for( int i = 0; i < artifactJsonArray.length(); i++ ){
                JSONObject artifactJson = artifactJsonArray.getJSONObject(i);
                ArtifactImpl artifact = new ArtifactImpl();
                artifact.setName(getString(artifactJson, "Name", true));
                artifact.setDescription(getString(artifactJson, "Description", true));
                assessmentStep.addArtifact(artifact);
            }
        }

        if( stepJson.has("ParameterDefinitions") ){
            JSONArray paramDefinitionsArray = stepJson.getJSONArray("ParameterDefinitions");
            for( int i = 0; i < paramDefinitionsArray.length(); i++ ){
                JSONObject paramJSON = paramDefinitionsArray.optJSONObject(i);
                TrustmarkDefinitionParameterImpl param = new TrustmarkDefinitionParameterImpl();
                param.setIdentifier(getString(paramJSON, "Identifier", true));
                param.setName(getString(paramJSON, "Name", true));
                param.setDescription(getString(paramJSON, "Description", true));
                param.setParameterKind(ParameterKind.fromString(getString(paramJSON, "ParameterKind", true)));
                if( paramJSON.has("Required") ) {
                    param.setRequired(paramJSON.optBoolean("Required"));
                }else{
                    param.setRequired(false);
                }
                if( paramJSON.has("EnumValues") ){
                    JSONArray enumValuesArray = paramJSON.getJSONArray("EnumValues");
                    for( int j = 0; j < enumValuesArray.length(); j++ ){
                        String enumVal = enumValuesArray.getString(j);
                        param.addEnumValue(enumVal);
                    }
                }
                assessmentStep.addParameter(param);
            }
        }

        return assessmentStep;
    }//end readConformanceCriterion()

    protected static ConformanceCriterionImpl readConformanceCriterion(JSONObject critJson, HashMap<String, SourceImpl> sourceMap) throws ParseException {
        ConformanceCriterionImpl crit = new ConformanceCriterionImpl();

        crit.setId(getString(critJson, "$id", true));
        crit.setNumber(getNumber(critJson, "Number", true).intValue());
        crit.setName(getString(critJson, "Name", true));
        crit.setDescription(getString(critJson, "Description", true));

        if( critJson.has("Citations") ){
            JSONArray citationsJSON = critJson.getJSONArray("Citations");
            for( int i = 0; i < citationsJSON.length(); i++ ){
                JSONObject citationJson = citationsJSON.getJSONObject(i);
                CitationImpl citation = new CitationImpl();
                JSONObject sourceRef = citationJson.getJSONObject("Source");
                String refVal = sourceRef.getString("$ref");
                if( refVal.startsWith("#") )
                    refVal = refVal.substring(1);
                SourceImpl source = sourceMap.get(refVal);
                if( source == null )
                    throw new ParseException("Could not find any Source with ID '"+refVal+"', as referenced by criterion '"+crit.getName()+"'");
                citation.setSource(source);
                citation.setDescription(getString(citationJson, "Description", true));
                crit.addCitation(citation);
            }
        }

        return crit;
    }//end readConformanceCriterion()


}//end TrustmarkJsonDeserializer()
