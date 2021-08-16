package edu.gatech.gtri.trustmark.v1_0.impl;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.*;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.json.XML;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


/**
 * Created by brad on 3/23/15.
 */
public abstract class AbstractTest {

    protected static final String OUTPUT_DIR = "./target";

    protected static Logger logger = LogManager.getLogger(AbstractTest.class);

    @Before
    public void printStart(){
        logger.info("======================================== STARTING TEST ========================================");
    }
    @After
    public void printStop(){
        logger.info("======================================== STOPPING TEST ========================================\n\n");
    }

    protected Element readXML( String xml ) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xml));
        assertThat(document, notNullValue());

        Element root = document.getRootElement();
        root.addNamespace("xhtml", "http://www.w3.org/1999/xhtml");

        return root;
    }//end readXML()

    protected void toFile( String text, String filepath) throws IOException {
        File output = new File(filepath);
        if( output.exists() ){
            throw new IOException("Cannot write to file["+filepath+"], it exists!");
        }
        output.getParentFile().mkdirs(); // Just in case...

        PrintWriter writer = new PrintWriter(new FileOutputStream(output));
        writer.print(text);
        writer.flush();
        writer.close();

    }//end toFile()

    protected String readStringFromFile(File file) throws IOException {
        FileReader reader = new FileReader(file);
        StringWriter inMemoryString = new StringWriter();
        IOUtils.copy(reader, inMemoryString);
        return inMemoryString.toString();
    }


    protected TrustmarkDefinition readTdFromFile( String path ){
        File file = new File(path);
        if(!file.exists())
            throw new UnsupportedOperationException("Could not find filepath: "+path);

        try {
            return FactoryLoader.getInstance(TrustmarkDefinitionResolver.class).resolve(file);
        }catch(ResolveException re){
            throw new UnsupportedOperationException("Error reading TD from path: "+path, re);
        }
    }

    protected void assertTdFull(TrustmarkDefinition td) throws Exception {

        // Metadata
        assertThat(td.getMetadata().getIdentifier().toString(), equalTo("http://tdo.example/td"));
//        assertThat(td.getMetadata().getTrustmarkReferenceAttributeName().toString(), equalTo("TrustmarkReferenceAttributeName"));
        assertThat(td.getMetadata().getName(), equalTo("Trustmark Definition Name"));
        assertThat(td.getMetadata().getVersion(), equalTo("1.0.0"));
        assertThat(td.getMetadata().getDescription(), equalTo("This is a description of this Trustmark Definition."));
  //      assertThat(td.getMetadata().getPublicationDateTime().getTime(), equalTo(1388552400000L));
        assertThat(td.getMetadata().isDeprecated(), equalTo(false));
        assertThat(td.getMetadata().getTargetStakeholderDescription(), equalTo("TargetStakeholderDescription"));
        assertThat(td.getMetadata().getTargetRecipientDescription(), equalTo("TargetRecipientDescription"));
        assertThat(td.getMetadata().getTargetRelyingPartyDescription(), equalTo("TargetRelyingPartyDescription"));
        assertThat(td.getMetadata().getTargetProviderDescription(), equalTo("TargetProviderDescription"));
        assertThat(td.getMetadata().getProviderEligibilityCriteria(), equalTo("ProviderEligibilityCriteria"));
        assertThat(td.getMetadata().getAssessorQualificationsDescription(), equalTo("AssessorQualificationsDescription"));
        assertThat(td.getMetadata().getTrustmarkRevocationCriteria(), equalTo("CriteriaNecessitatingTrustmarkRevocationAndReissuance"));
        assertThat(td.getMetadata().getExtensionDescription(), equalTo("ExtensionDescription"));
        assertThat(td.getMetadata().getLegalNotice(), equalTo("This is the legal notice."));
        assertThat(td.getMetadata().getNotes(), equalTo("Notes"));

        assertThat(td.getMetadata().getSupersedes(), notNullValue());
        assertThat(td.getMetadata().getSupersedes().size(), equalTo(2));
        assertTFICollectionContainsURI(td.getMetadata().getSupersedes(), "https://example.org/older-td-example");
        assertTFICollectionContainsURI(td.getMetadata().getSupersedes(), "https://example.org/older-td-example2");

        assertThat(td.getMetadata().getSupersededBy(), notNullValue());
        assertThat(td.getMetadata().getSupersededBy().size(), equalTo(2));
        assertTFICollectionContainsURI(td.getMetadata().getSupersededBy(), "https://example.org/newer-td-example");
        assertTFICollectionContainsURI(td.getMetadata().getSupersededBy(), "https://example.org/newer-td-example2");

        assertThat(td.getMetadata().getSatisfies(), notNullValue());
        assertThat(td.getMetadata().getSatisfies().size(), equalTo(2));
        assertTFICollectionContainsURI(td.getMetadata().getSatisfies(), "https://example.org/satisfies-td-example");
        assertTFICollectionContainsURI(td.getMetadata().getSatisfies(), "https://example.org/satisfies-td-example2");

        assertThat(td.getMetadata().getKnownConflicts(), notNullValue());
        assertThat(td.getMetadata().getKnownConflicts().size(), equalTo(1));
        assertTFICollectionContainsURI(td.getMetadata().getKnownConflicts(), "https://example.org/conflict-td-example");


        assertThat(td.getMetadata().getKeywords(), notNullValue());
        assertThat(td.getMetadata().getKeywords(), contains("Keyword1", "Keyword2", "Keyword3"));

        assertThat(td.getMetadata().getTrustmarkDefiningOrganization(), notNullValue());
        assertThat(td.getMetadata().getTrustmarkDefiningOrganization().getIdentifier().toString(), equalTo("http:///tdo.example"));

        Contact c1 = td.getMetadata().getTrustmarkDefiningOrganization().getContacts().get(0);
        assertThat(c1, notNullValue());
        assertThat(c1.getKind(), equalTo(ContactKindCode.PRIMARY));
        assertThat(c1.getResponder(), equalTo("George P. Burdell"));
        assertThat(c1.getEmails(), contains("TrustmarkFeedback@gtri.gatech.edu"));
        assertThat(c1.getTelephones(), contains("404-555-1234", "404-555-2345"));
        assertThat(c1.getWebsiteURLs(), contains( new URL("http://trustmark.gtri.gatech.edu/"), new URL("http://www.gtri.gatech.edu/") ));
        assertThat(c1.getMailingAddresses(), contains("Trustmark Feedback, 75 5th Street NW, Suite 900, Atlanta GA 30308"));
        assertThat(c1.getPhysicalAddresses(), contains("75 5th Street NW, Suite 900, Atlanta GA 30308"));
        assertThat(c1.getNotes(), equalTo("The responder may change."));

        Contact c2 = td.getMetadata().getTrustmarkDefiningOrganization().getContacts().get(1);
        assertThat(c2, notNullValue());
        assertThat(c2.getKind(), equalTo(ContactKindCode.OTHER));
        assertThat(c2.getEmails(), contains("test@123.org"));

        // Terms
        assertThat(td.getTerms(), notNullValue());
        assertThat(td.getTerms().size(), equalTo(2));
        Term term1 = td.getTermsSorted().get(0);
        assertTermEquals(term1, "Example", "This is an example definition.", "E", "Ex");
        Term term2 = td.getTermsSorted().get(1);
        assertTermEquals(term2, "Example2", "This is an example definition 2.", "E2", "Ex2");

        // Sources
        assertThat(td.getSources(), notNullValue());
        assertThat(td.getSources().size(), equalTo(2));
        Source s1 = findSource(td.getSources(), "Example");
        assertThat(s1, notNullValue());
        assertThat(s1.getReference(), equalTo("This is an example description."));
        Source s2 = findSource(td.getSources(), "Example2");
        assertThat(s2, notNullValue());
        assertThat(s2.getReference(), equalTo("This is an example description 2."));


        // Criteria
        assertThat(td.getConformanceCriteriaPreface(), equalTo("This applies to every criterion.  It can <b>Even have markup!</b>"));
        assertThat(td.getConformanceCriteria(), notNullValue());
        assertThat(td.getConformanceCriteria().size(), equalTo(2));

        // Steps
        assertThat(td.getAssessmentStepPreface(), equalTo("This applies to every assessment step."));
        assertThat(td.getAssessmentSteps(), notNullValue());
        assertThat(td.getAssessmentSteps().size(), equalTo(2));

    }

    protected Source findSource(Collection<Source> sources, String name){
        if( sources != null && sources.size() > 0 ){
            for( Source s : sources ){
                if( s.getIdentifier().equals(name) ){
                    return s;
                }
            }
        }
        return null;
    }

    protected void assertTermEquals(Term term, String name, String definition, String ... abbreviations) throws Exception {
        assertThat(term, notNullValue());
        assertThat(term.getName(), equalTo(name));
        assertThat(term.getDefinition(), equalTo(definition));
        if( abbreviations != null && abbreviations.length > 0 ){
            assertThat(term.getAbbreviations(), notNullValue());
            logger.debug("Term abbreviations in memory: "+term.getAbbreviations());
            assertThat(term.getAbbreviations().size(), equalTo(abbreviations.length));
            for( String abbr : abbreviations ){
                assertThat(term.getAbbreviations().contains(abbr), equalTo(true));
            }
        }
    }

    protected void assertTipFull(TrustInteroperabilityProfile tip) throws Exception {
        assertThat(tip.getIdentifier().toString(), equalTo("http://tip.example/tip"));
        assertThat(tip.getName(), equalTo("Trust Interoperability Profile Example"));
        assertThat(tip.getVersion(), equalTo("1.0.0"));
//        assertThat(tip.getPublicationDateTime().getTime(), equalTo(1388552400000L));
        assertThat(tip.isDeprecated(), equalTo(false));
        assertThat(tip.getDescription(), equalTo("This is a description of a Trust Interoperability Profile."));
        assertThat(tip.getLegalNotice(), equalTo("This is the legal notice."));
        assertThat(tip.getNotes(), equalTo("Notes"));

        assertThat(tip.getKeywords(), notNullValue());
        assertThat(tip.getKeywords(), contains("Keyword1", "Keyword2", "Keyword3"));

        assertThat(tip.getIssuer().getIdentifier().toString(), equalTo("http://tip.example/"));
        assertThat(tip.getIssuer().getName(), equalTo("Trust Interoperability Profile Issuer"));
        assertThat(tip.getIssuer().getContacts(), notNullValue());
        assertThat(tip.getIssuer().getContacts().size(), equalTo(2));

        Contact c1 = tip.getIssuer().getContacts().get(0);
        assertThat(c1, notNullValue());
        assertThat(c1.getKind(), equalTo(ContactKindCode.PRIMARY));
        assertThat(c1.getResponder(), equalTo("George P. Burdell"));
        assertThat(c1.getEmails(), contains("TrustmarkFeedback@gtri.gatech.edu"));
        assertThat(c1.getTelephones(), contains("404-555-1234", "404-555-2345"));
        assertThat(c1.getWebsiteURLs(), contains(new URL("http://trustmark.gtri.gatech.edu/"), new URL("http://www.gtri.gatech.edu/")));
        assertThat(c1.getPhysicalAddresses(), contains("75 5th Street NW, Suite 900, Atlanta GA 30308"));
        assertThat(c1.getMailingAddresses(), contains("Trustmark Feedback, 75 5th Street NW, Suite 900, Atlanta GA 30308"));
        assertThat(c1.getNotes(), equalTo("The responder may change."));

        Contact c2 = tip.getIssuer().getContacts().get(1);
        assertThat(c2, notNullValue());
        assertThat(c2.getKind(), equalTo(ContactKindCode.OTHER));
        assertThat(c2.getEmails(), contains("test@example.org", "test2@example.org"));
        assertThat(c2.getTelephones(), contains("404-555-5555"));

        assertThat( tip.getSupersededBy(), notNullValue() );
        assertThat( tip.getSupersededBy().size(), equalTo(2) );
        assertTFICollectionContainsURI(tip.getSupersededBy(), "https://example.org/tip1-supersededBy");
        assertTFICollectionContainsURI(tip.getSupersededBy(), "https://example.org/tip2-supersededBy");

        assertThat( tip.getSupersedes(), notNullValue() );
        assertThat( tip.getSupersedes().size(), equalTo(2) );
        assertTFICollectionContainsURI(tip.getSupersedes(), "https://example.org/tip1-supersedes");
        assertTFICollectionContainsURI(tip.getSupersedes(), "https://example.org/tip2-supersedes");

        assertThat( tip.getSatisfies(), notNullValue() );
        assertThat( tip.getSatisfies().size(), equalTo(2) );
        assertTFICollectionContainsURI(tip.getSatisfies(), "https://example.org/tip1-satisfies");
        assertTFICollectionContainsURI(tip.getSatisfies(), "https://example.org/tip2-satisfies");

        assertThat( tip.getKnownConflicts(), notNullValue());
        assertThat( tip.getKnownConflicts().size(), equalTo(1) );
        assertTFICollectionContainsURI(tip.getKnownConflicts(), "https://example.org/tip1-conflict");

        assertThat(tip.getTrustExpression(), equalTo("tip1 and (tdr1 or tdr2)"));
        assertThat(tip.getReferences(), notNullValue());
        assertThat(tip.getReferences().size(), equalTo(3));

        TrustmarkDefinitionRequirement tdr1 = getTdRequirement(tip.getReferences(), "http://tdo.example/td");
        assertThat(tdr1, notNullValue());
        assertThat(tdr1.getIdentifier().toString(), equalTo("http://tdo.example/td"));
        assertThat(tdr1.getName(), equalTo("Trustmark Definition Name"));
        assertThat(tdr1.getVersion(), equalTo("1.0.0"));
        assertThat(tdr1.getDescription(), equalTo("This is a description of this Trustmark Definition."));
        assertThat(tdr1.getProviderReferences(), notNullValue());
        assertThat(tdr1.getProviderReferences().size(), equalTo(1));
        Entity provider1 = tdr1.getProviderReferences().get(0);
        assertThat(provider1, notNullValue());
        assertThat(provider1.getIdentifier().toString(), equalTo("http://provider.example/"));
        assertThat(provider1.getContacts().size(), equalTo(1));
        assertThat(provider1.getContacts().get(0), notNullValue());

        TrustmarkDefinitionRequirement tdr2 = getTdRequirement(tip.getReferences(), "http://tdo.example/td2");
        assertThat(tdr2, notNullValue());
        assertThat(tdr2.getIdentifier().toString(), equalTo("http://tdo.example/td"));
        assertThat(tdr2.getName(), equalTo("Trustmark Definition Name"));
        assertThat(tdr2.getVersion(), equalTo("1.0.0"));
        assertThat(tdr2.getDescription(), equalTo("This is a description of this Trustmark Definition."));
        assertThat(tdr2.getProviderReferences(), notNullValue());
        assertThat(tdr2.getProviderReferences().size(), equalTo(1));
        Entity provider2 = tdr2.getProviderReferences().get(0);
        assertThat(provider2, notNullValue());
        assertThat(provider2.getIdentifier().toString(), equalTo("http://provider.example/"));
        assertThat(provider2.getContacts().size(), equalTo(1));
        assertThat(provider2.getContacts().get(0), notNullValue());


        TrustInteroperabilityProfileReference tip1 = getTipReference(tip.getReferences(), "http://tip.example/tip");
        assertThat(tip1, notNullValue());

        // Terms
        assertThat(tip.getTerms(), notNullValue());
        assertThat(tip.getTerms().size(), equalTo(1));
        Term term1 = tip.getTermsSorted().get(0);
        assertTermEquals(term1, "Term1", "Term1 Def", "Abbr1", "Abbr2");

        // Sources
        assertThat(tip.getSources(), notNullValue());
        assertThat(tip.getSources().size(), equalTo(1));
        Source s1 = findSource(tip.getSources(), "Source1");
        assertThat(s1, notNullValue());
        assertThat(s1.getReference(), equalTo("Source 1 Reference"));

    }

    private TrustmarkDefinitionRequirement getTdRequirement(Collection<AbstractTIPReference> refs, String id ){
        TrustmarkDefinitionRequirement tdReq = null;
        if( refs != null && !refs.isEmpty() ){
            for( AbstractTIPReference tipRef : refs ){
                if( tipRef.isTrustmarkDefinitionRequirement() ){
                    tdReq = (TrustmarkDefinitionRequirement) tipRef;
                    break;
                }
            }
        }
        return tdReq;
    }

    private TrustInteroperabilityProfileReference getTipReference(Collection<AbstractTIPReference> refs, String id ){
        TrustInteroperabilityProfileReference tipRef = null;
        if( refs != null && !refs.isEmpty() ){
            for( AbstractTIPReference nextRef : refs ){
                if( nextRef.isTrustInteroperabilityProfileReference() ){
                    tipRef = (TrustInteroperabilityProfileReference) nextRef;
                    break;
                }
            }
        }
        return tipRef;
    }


    private void assertTFICollectionContainsURI(Collection<TrustmarkFrameworkIdentifiedObject> tfiCol, String identifier){
        boolean found = false;
        for( TrustmarkFrameworkIdentifiedObject tfi : tfiCol ){
            if( tfi.getIdentifier().toString().equals(identifier) ){
                found = true;
                break;
            }
        }
        if (!found)
            Assert.fail("List does not contain "+identifier);
    }
    
    
    //==================================================================================================================
    //  Test Helper Methods
    //==================================================================================================================
    
    protected String getFileString(String folderName, String name) throws IOException {
        File file = new File(new File("./target/test-classes", folderName), name);
        assertThat(file.exists(), equalTo(Boolean.TRUE));
        return readStringFromFile(file);
    }
    
    protected void writeStringToFile(String folderName, String name, String content) throws IOException {
        this.writeBytesToFile(folderName, name, content.getBytes(StandardCharsets.UTF_8));
    }
    
    protected void writeBytesToFile(String folderName, String name, byte[] content) throws IOException {
        File file = new File(new File("./target/test-classes", folderName), name);
        Files.write(file.toPath(), content);
        logger.debug("Wrote bytes to file: " + file.getAbsolutePath());
    }
    
    protected <T> JsonDiffResultCollection doJsonDiff(Class<T> type, T expected, T actual) {
        // Updated JSON Diff
        JsonDiffManager manager = FactoryLoader.getInstance(JsonDiffManager.class);
        JsonDiff<? super T> diff = manager.getComponent(type);
        JsonDiffResultCollection resultCollection = diff.doDiff("expected", expected, "actual", actual);
        assertThat(resultCollection, notNullValue());
        
        Collection<JsonDiffResult> majorDiffIssues = resultCollection.getResultsForSeverity(DiffSeverity.MAJOR);
        assertThat(majorDiffIssues, notNullValue());
        
        Collection<JsonDiffResult> minorDiffIssues = resultCollection.getResultsForSeverity(DiffSeverity.MINOR);
        assertThat(minorDiffIssues, notNullValue());
        
        for (JsonDiffResult diffResult : majorDiffIssues) {
            logger.warn(diffResult.getDescription());
        }
        
        for (JsonDiffResult diffResult : minorDiffIssues) {
            logger.debug(diffResult.getDescription());
        }
        return resultCollection;
    }

    protected static String getAssertionReason(Collection<JsonDiffResult> diffResults) {
        if (diffResults == null || diffResults.isEmpty()) { return "No differences."; }
        return diffResults.iterator().next().getDescription();
    }

    protected void assertJsonDiffCount(
        JsonDiffResultCollection resultCollection,
        DiffSeverity severity,
        int targetCount
    ) {
        Collection<JsonDiffResult> resultsForSeverity = resultCollection.getResultsForSeverity(severity);
        assertThat(resultsForSeverity, notNullValue());
        String reason = getAssertionReason(resultsForSeverity);
        assertThat(reason, resultsForSeverity.size(), equalTo(targetCount));
    }
    
    protected void assertJsonDiffCount(
        JsonDiffResultCollection resultCollection,
        DiffSeverity severity,
        JsonDiffType diffType,
        int targetCount
    ) {
        Collection<JsonDiffResult> resultsForSeverity = resultCollection.getResultsForSeverity(severity);
        assertThat(resultsForSeverity, notNullValue());
        int count = 0;
        for (JsonDiffResult result : resultsForSeverity) {
            if (result.getJsonDiffType() == diffType) { ++count; }
        }
        String reason = getAssertionReason(resultsForSeverity);
        assertThat(reason, count, equalTo(targetCount));
    }
    
    protected <T> void assertNoJsonDiffIssuesOfType(DiffSeverity severity, Class<T> type, T expected, T actual) {
        logger.debug("======== Started JSON Diff ========");
        JsonDiffResultCollection jsonDiffResultCollection = this.doJsonDiff(type, expected, actual);
        Collection<JsonDiffResult> jsonDiffResultCollectionIssues = jsonDiffResultCollection.getResultsForSeverity(severity);
        logger.debug("======== Finished JSON Diff ========");
        assertThat(jsonDiffResultCollectionIssues, notNullValue());
        String reason = getAssertionReason(jsonDiffResultCollectionIssues);
        assertThat(reason, jsonDiffResultCollectionIssues.size(), equalTo(0));
    }
    
    protected void assertNoXmlDiffIssuesOfType(DiffSeverity severity, String expectedXml, String actualXml) {
        logger.debug("======== Started XML Diff ========");
        JSONObject expectedXmlAsJson = XML.toJSONObject(expectedXml);
        JSONObject actualXmlAsJson = XML.toJSONObject(actualXml);
        JsonDiffResultCollection jsonDiffResultCollection = this.doJsonDiff(JSONObject.class, expectedXmlAsJson, actualXmlAsJson);
        Collection<JsonDiffResult> jsonDiffResultCollectionIssues = jsonDiffResultCollection.getResultsForSeverity(severity);
        logger.debug("======== Finished XML Diff ========");
        assertThat(jsonDiffResultCollectionIssues, notNullValue());
        String reason = getAssertionReason(jsonDiffResultCollectionIssues);
        assertThat(reason, jsonDiffResultCollectionIssues.size(), equalTo(0));
    }
}
