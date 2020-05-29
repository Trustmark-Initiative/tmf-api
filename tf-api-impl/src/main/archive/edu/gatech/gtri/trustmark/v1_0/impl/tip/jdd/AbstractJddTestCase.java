package edu.gatech.gtri.trustmark.v1_0.impl.tip.jdd;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.InMemoryMapTIPResolver;
import edu.gatech.gtri.trustmark.v1_0.impl.io.antlr.AntlrTrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.impl.model.jaxb.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ReferencesTypeChoice;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.net.URI;
import java.util.*;

/**
 * Created by brad on 12/4/15.
 */
public class AbstractJddTestCase extends AbstractTest {


    protected static final Map<String, ReferencesTypeChoiceImpl> testReferenceChoiceMap;
    protected static final Map<URI, TrustInteroperabilityProfile> tipMap;
    protected static final Map<String, Trustmark> trustmarkMap;

    public static ReferencesTypeChoice getTestReferenceTypeChoice(String id) {
        return testReferenceChoiceMap.get(id);
    }

    public static TrustInteroperabilityProfile getTestTIP(URI id) {
        return tipMap.get(id);
    }

    public static Map<URI, TrustInteroperabilityProfile> getTIPMap() {
        return tipMap;
    }

    protected TIPEvaluatorJDD getTIPEvaluatorJDD() {
        TIPEvaluatorJDD evaluator = new TIPEvaluatorJDD(new InMemoryMapTIPResolver(this.getTIPMap()));
        return evaluator;
    }


    protected void runMatchersOnEvaluations(TIPEvaluation expectedEvaluation, TIPEvaluation actualEvaluation) {
        MatcherAssert.assertThat(expectedEvaluation.getTIP(),
                Matchers.equalTo(actualEvaluation.getTIP()));
        MatcherAssert.assertThat(expectedEvaluation.getInputTDRequirements(),
                Matchers.equalTo(actualEvaluation.getInputTDRequirements()));
        // TODO MatcherAssert.assertThat(expectedEvaluation.getInputType(),
        // Matchers.equalTo(actualEvaluation.getInputType()));
        MatcherAssert.assertThat(expectedEvaluation.isInputWasTDRequirements(),
                Matchers.equalTo(actualEvaluation.isInputWasTDRequirements()));
        MatcherAssert.assertThat(expectedEvaluation.isInputWasTrustmarks(),
                Matchers.equalTo(actualEvaluation.isInputWasTrustmarks()));
        MatcherAssert.assertThat(expectedEvaluation.isSatisfied(),
                Matchers.equalTo(actualEvaluation.isSatisfied()));
        MatcherAssert.assertThat(expectedEvaluation.getSatisfactionGap(),
                Matchers.equalTo(actualEvaluation.getSatisfactionGap()));

    }

    static {
        // //////// CREATE TEST TD REFERENCE CHOICES /////////

        // initialize and populate testReferenceChoices
        testReferenceChoiceMap = new TreeMap<String, ReferencesTypeChoiceImpl>();

        {
            // TD Reference Choice 1

            // TD Reference
            URI tdID = URI.create("http://trustmark.org/TD1");
            String name = "TD1";
            String version = "1.0";
            String description = null;

            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdID);
            tdRef.setName(name);
            tdRef.setVersion(version);
            tdRef.setDescription(description);

            // List of Provider Entity References
            List<EntityReferenceTypeImpl> providerReferences = new ArrayList<EntityReferenceTypeImpl>();
            // No Provider requirement for this TD Requirement

            String referenceId = "TD1Any";

            TrustmarkDefinitionRequirementTypeImpl tdReq = new TrustmarkDefinitionRequirementTypeImpl();
            tdReq.setId(referenceId);
            tdReq.setTrustmarkDefinitionReference(tdRef);
            tdReq.getProviderReference().addAll(providerReferences);
            ReferencesTypeChoiceImpl rc = new ReferencesTypeChoiceImpl(tdReq);

            testReferenceChoiceMap.put(referenceId, rc);
        }

        {
            // TD Reference Choice 2

            // TD Reference
            URI tdID = URI.create("http://trustmark.org/TD2");
            String name = "TD2";
            String version = "1.0";
            String description = null;

            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdID);
            tdRef.setName(name);
            tdRef.setVersion(version);
            tdRef.setDescription(description);

            // List of Provider Entity References
            List<EntityReferenceTypeImpl> providerReferences = new ArrayList<EntityReferenceTypeImpl>();
            // No Provider requirement for this TD Requirement

            String referenceId = "TD2Any";

            TrustmarkDefinitionRequirementTypeImpl tdReq = new TrustmarkDefinitionRequirementTypeImpl();
            tdReq.setId(referenceId);
            tdReq.setTrustmarkDefinitionReference(tdRef);
            tdReq.getProviderReference().addAll(providerReferences);
            ReferencesTypeChoiceImpl rc = new ReferencesTypeChoiceImpl(tdReq);

            testReferenceChoiceMap.put(referenceId, rc);
        }

        {
            // TD Reference Choice 3

            // TD Reference
            URI tdID = URI.create("http://trustmark.org/TD3");
            String name = "TD3";
            String version = "1.0";
            String description = null;

            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdID);
            tdRef.setName(name);
            tdRef.setVersion(version);
            tdRef.setDescription(description);

            // List of Provider Entity References
            List<EntityReferenceTypeImpl> providerReferences = new ArrayList<EntityReferenceTypeImpl>();
            {
                URI providerID = URI.create("http://trustmark.org/NIEF");
                String providerName = "NIEF";
                EntityReferenceTypeImpl providerReference = new EntityReferenceTypeImpl();
                providerReference.setIdentifier(providerID);
                providerReference.setName(providerName);
                providerReferences.add(providerReference);
            }

            String referenceId = "TD3NIEF";

            TrustmarkDefinitionRequirementTypeImpl tdReq = new TrustmarkDefinitionRequirementTypeImpl();
            tdReq.setId(referenceId);
            tdReq.setTrustmarkDefinitionReference(tdRef);
            tdReq.getProviderReference().addAll(providerReferences);
            ReferencesTypeChoiceImpl rc = new ReferencesTypeChoiceImpl(tdReq);

            testReferenceChoiceMap.put(referenceId, rc);
        }

        {
            // TD Reference Choice 4

            // TD Reference
            URI tdID = URI.create("http://trustmark.org/TD3");
            String name = "TD3";
            String version = "1.0";
            String description = null;

            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdID);
            tdRef.setName(name);
            tdRef.setVersion(version);
            tdRef.setDescription(description);

            // List of Provider Entity References
            List<EntityReferenceTypeImpl> providerReferences = new ArrayList<EntityReferenceTypeImpl>();
            {
                URI providerID = URI.create("http://trustmark.org/GTRI");
                String providerName = "GTRI";
                EntityReferenceTypeImpl providerReference = new EntityReferenceTypeImpl();
                providerReference.setIdentifier(providerID);
                providerReference.setName(providerName);
                providerReferences.add(providerReference);
            }

            String referenceId = "TD3GTRI";

            TrustmarkDefinitionRequirementTypeImpl tdReq = new TrustmarkDefinitionRequirementTypeImpl();
            tdReq.setId(referenceId);
            tdReq.setTrustmarkDefinitionReference(tdRef);
            tdReq.getProviderReference().addAll(providerReferences);
            ReferencesTypeChoiceImpl rc = new ReferencesTypeChoiceImpl(tdReq);

            testReferenceChoiceMap.put(referenceId, rc);
        }

        {
            // TD Reference Choice 5

            // TD Reference
            URI tdID = URI.create("http://trustmark.org/TD4");
            String name = "TD4";
            String version = "1.0";
            String description = null;

            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdID);
            tdRef.setName(name);
            tdRef.setVersion(version);
            tdRef.setDescription(description);

            // List of Provider Entity References
            List<EntityReferenceTypeImpl> providerReferences = new ArrayList<EntityReferenceTypeImpl>();
            {
                URI providerID = URI.create("http://trustmark.org/NIEF");
                String providerName = "GTRI";
                EntityReferenceTypeImpl providerReference = new EntityReferenceTypeImpl();
                providerReference.setIdentifier(providerID);
                providerReference.setName(providerName);
                providerReferences.add(providerReference);
            }

            String referenceId = "TD4NIEF";

            TrustmarkDefinitionRequirementTypeImpl tdReq = new TrustmarkDefinitionRequirementTypeImpl();
            tdReq.setId(referenceId);
            tdReq.setTrustmarkDefinitionReference(tdRef);
            tdReq.getProviderReference().addAll(providerReferences);
            ReferencesTypeChoiceImpl rc = new ReferencesTypeChoiceImpl(tdReq);

            testReferenceChoiceMap.put(referenceId, rc);
        }

        {
            // TD Reference Choice 6

            // TD Reference
            URI tdID = URI.create("http://trustmark.org/TD4");
            String name = "TD4";
            String version = "1.0";
            String description = null;

            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdID);
            tdRef.setName(name);
            tdRef.setVersion(version);
            tdRef.setDescription(description);

            // List of Provider Entity References
            List<EntityReferenceTypeImpl> providerReferences = new ArrayList<EntityReferenceTypeImpl>();
            {
                URI providerID = URI.create("http://trustmark.org/GTRI");
                String providerName = "GTRI";
                EntityReferenceTypeImpl providerReference = new EntityReferenceTypeImpl();
                providerReference.setIdentifier(providerID);
                providerReference.setName(providerName);
                providerReferences.add(providerReference);
            }

            String referenceId = "TD4GTRI";

            TrustmarkDefinitionRequirementTypeImpl tdReq = new TrustmarkDefinitionRequirementTypeImpl();
            tdReq.setId(referenceId);
            tdReq.setTrustmarkDefinitionReference(tdRef);
            tdReq.getProviderReference().addAll(providerReferences);
            ReferencesTypeChoiceImpl rc = new ReferencesTypeChoiceImpl(tdReq);

            testReferenceChoiceMap.put(referenceId, rc);
        }

        {
            // TD Reference Choice 7

            // TD Reference
            URI tdID = URI.create("http://trustmark.org/TD4");
            String name = "TD4";
            String version = "1.0";
            String description = null;

            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdID);
            tdRef.setName(name);
            tdRef.setVersion(version);
            tdRef.setDescription(description);

            // List of Provider Entity References
            List<EntityReferenceTypeImpl> providerReferences = new ArrayList<EntityReferenceTypeImpl>();
            {
                URI providerID = URI.create("http://trustmark.org/NIEF");
                String providerName = "NIEF";
                EntityReferenceTypeImpl providerReference = new EntityReferenceTypeImpl();
                providerReference.setIdentifier(providerID);
                providerReference.setName(providerName);
                providerReferences.add(providerReference);
            }
            {
                URI providerID = URI.create("http://trustmark.org/GTRI");
                String providerName = "GTRI";
                EntityReferenceTypeImpl providerReference = new EntityReferenceTypeImpl();
                providerReference.setIdentifier(providerID);
                providerReference.setName(providerName);
                providerReferences.add(providerReference);
            }

            String referenceId = "TD4NIEFGTRI";

            TrustmarkDefinitionRequirementTypeImpl tdReq = new TrustmarkDefinitionRequirementTypeImpl();
            tdReq.setId(referenceId);
            tdReq.setTrustmarkDefinitionReference(tdRef);
            tdReq.getProviderReference().addAll(providerReferences);
            ReferencesTypeChoiceImpl rc = new ReferencesTypeChoiceImpl(tdReq);

            testReferenceChoiceMap.put(referenceId, rc);
        }
        // ////// END CREATE TEST TD REFERENCE CHOICES ///////



        // ////////// CREATE TEST TIPs AND TIP REFERENCE CHOICES //////////////
        AntlrTrustExpressionParser aTrustExprParser = new AntlrTrustExpressionParser();
        // Initialize and populate testTIPMap
        tipMap = new HashMap<URI, TrustInteroperabilityProfile>();

        {
            // TIP A
            URI tipID = URI.create("http://trustmark.org/TIPA");

            // TIP References
            ReferencesTypeImpl references = new ReferencesTypeImpl();
            references.addReferencesTypeChoice(testReferenceChoiceMap
                    .get("TD1Any"));

            String trustExpr = "TD1Any";

            TrustInteroperabilityProfileImpl tip = new TrustInteroperabilityProfileImpl();
            tip.setIdentifier(tipID);
            tip.setReferences(references);
            tip.setTrustExpression(trustExpr);
            try {
                tip.setTrustExpressionTree(aTrustExprParser.parseTrustExpressionString(trustExpr));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            tipMap.put(tipID, tip);

            // create TIP reference choice so it can be referenced by other TIPs
            String referenceId = "TIPA";
            TrustInteroperabilityProfileReferenceTypeImpl tipRef = new TrustInteroperabilityProfileReferenceTypeImpl();
            tipRef.setId(referenceId);
            tipRef.setIdentifier(tipID);
            ReferencesTypeChoiceImpl referenceChoice = new ReferencesTypeChoiceImpl(
                    tipRef);
            testReferenceChoiceMap.put(referenceId, referenceChoice);
        }

        {
            // TIP B
            URI tipID = URI.create("http://trustmark.org/TIPB");

            // TIP References
            ReferencesTypeImpl references = new ReferencesTypeImpl();
            references.addReferencesTypeChoice(testReferenceChoiceMap
                    .get("TD1Any"));
            references.addReferencesTypeChoice(testReferenceChoiceMap
                    .get("TD2Any"));

            String trustExpr = "TD1Any and TD2Any";

            TrustInteroperabilityProfileImpl tip = new TrustInteroperabilityProfileImpl();
            tip.setIdentifier(tipID);
            tip.setReferences(references);
            tip.setTrustExpression(trustExpr);
            try {
                tip.setTrustExpressionTree(aTrustExprParser.parseTrustExpressionString(trustExpr));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            tipMap.put(tipID, tip);

            // create TIP reference choice so it can be referenced by other TIPs
            String referenceId = "TIPB";
            TrustInteroperabilityProfileReferenceTypeImpl tipRef = new TrustInteroperabilityProfileReferenceTypeImpl();
            tipRef.setId(referenceId);
            tipRef.setIdentifier(tipID);
            ReferencesTypeChoiceImpl referenceChoice = new ReferencesTypeChoiceImpl(
                    tipRef);
            testReferenceChoiceMap.put(referenceId, referenceChoice);
        }

        {
            // TIP C
            URI tipID = URI.create("http://trustmark.org/TIPC");

            // TIP References
            ReferencesTypeImpl references = new ReferencesTypeImpl();
            references.addReferencesTypeChoice(testReferenceChoiceMap
                    .get("TD1Any"));
            references.addReferencesTypeChoice(testReferenceChoiceMap
                    .get("TD3GTRI"));
            references.addReferencesTypeChoice(testReferenceChoiceMap
                    .get("TD4NIEFGTRI"));

            String trustExpr = "TD1Any or TD3GTRI and TD4NIEFGTRI";

            TrustInteroperabilityProfileImpl tip = new TrustInteroperabilityProfileImpl();
            tip.setIdentifier(tipID);
            tip.setReferences(references);
            tip.setTrustExpression(trustExpr);
            try {
                tip.setTrustExpressionTree(aTrustExprParser.parseTrustExpressionString(trustExpr));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            tipMap.put(tipID, tip);

            // create TIP reference choice so it can be referenced by other TIPs
            String referenceId = "TIPC";
            TrustInteroperabilityProfileReferenceTypeImpl tipRef = new TrustInteroperabilityProfileReferenceTypeImpl();
            tipRef.setId(referenceId);
            tipRef.setIdentifier(tipID);
            ReferencesTypeChoiceImpl referenceChoice = new ReferencesTypeChoiceImpl(
                    tipRef);
            testReferenceChoiceMap.put(referenceId, referenceChoice);
        }

        {
            // TIP D
            URI tipID = URI.create("http://trustmark.org/TIPD");

            // TIP References
            ReferencesTypeImpl references = new ReferencesTypeImpl();
            references.addReferencesTypeChoice(testReferenceChoiceMap
                    .get("TD2Any"));
            references.addReferencesTypeChoice(testReferenceChoiceMap
                    .get("TIPC"));

            String trustExpr = "TD2Any and TIPC";

            TrustInteroperabilityProfileImpl tip = new TrustInteroperabilityProfileImpl();
            tip.setIdentifier(tipID);
            tip.setReferences(references);
            tip.setTrustExpression(trustExpr);
            try {
                tip.setTrustExpressionTree(aTrustExprParser.parseTrustExpressionString(trustExpr));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            tipMap.put(tipID, tip);

            // create TIP reference choice so it can be referenced by other TIPs
            String referenceId = "TIPD";
            TrustInteroperabilityProfileReferenceTypeImpl tipRef = new TrustInteroperabilityProfileReferenceTypeImpl();
            tipRef.setId(referenceId);
            tipRef.setIdentifier(tipID);
            ReferencesTypeChoiceImpl referenceChoice = new ReferencesTypeChoiceImpl(
                    tipRef);
            testReferenceChoiceMap.put(referenceId, referenceChoice);

        }
        // ///// END CREATE TEST TIPs AND TIP REFERENCE CHOICES //////////

        // //////////// CREATE TEST TRUSTMARKS //////////////
        trustmarkMap = new HashMap<String, Trustmark>();

        {
            // TD1NIEF
            String trustmarkId = "TD1NIEF";

            TrustmarkImpl trustmark = new TrustmarkImpl();

            // Create and set TD Reference
            URI tdId = URI.create("http://trustmark.org/TD1");
            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdId);
            trustmark.setTrustmarkDefinitionReference(tdRef);

            // Create Provider EntityType
            URI providerId = URI.create("http://trustmark.org/NIEF");
            EntityTypeImpl providerEntity = new EntityTypeImpl();
            providerEntity.setIdentifier(providerId);
            trustmark.setProvider(providerEntity);

            trustmarkMap.put(trustmarkId, trustmark);
        }

        {
            // TD2NIEF
            String trustmarkId = "TD2NIEF";

            TrustmarkImpl trustmark = new TrustmarkImpl();

            // Create and set TD Reference
            URI tdId = URI.create("http://trustmark.org/TD2");
            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdId);
            trustmark.setTrustmarkDefinitionReference(tdRef);

            // Create Provider EntityType
            URI providerId = URI.create("http://trustmark.org/NIEF");
            EntityTypeImpl providerEntity = new EntityTypeImpl();
            providerEntity.setIdentifier(providerId);
            trustmark.setProvider(providerEntity);

            trustmarkMap.put(trustmarkId, trustmark);
        }

        {
            // TD3NIEF
            String trustmarkId = "TD3NIEF";

            TrustmarkImpl trustmark = new TrustmarkImpl();

            // Create and set TD Reference
            URI tdId = URI.create("http://trustmark.org/TD3");
            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdId);
            trustmark.setTrustmarkDefinitionReference(tdRef);

            // Create Provider EntityType
            URI providerId = URI.create("http://trustmark.org/NIEF");
            EntityTypeImpl providerEntity = new EntityTypeImpl();
            providerEntity.setIdentifier(providerId);
            trustmark.setProvider(providerEntity);

            trustmarkMap.put(trustmarkId, trustmark);
        }

        {
            // TD2NIEF
            String trustmarkId = "TD3GTRI";

            TrustmarkImpl trustmark = new TrustmarkImpl();

            // Create and set TD Reference
            URI tdId = URI.create("http://trustmark.org/TD3");
            TrustmarkDefinitionReferenceTypeImpl tdRef = new TrustmarkDefinitionReferenceTypeImpl();
            tdRef.setIdentifier(tdId);
            trustmark.setTrustmarkDefinitionReference(tdRef);

            // Create Provider EntityType
            URI providerId = URI.create("http://trustmark.org/GTRI");
            EntityTypeImpl providerEntity = new EntityTypeImpl();
            providerEntity.setIdentifier(providerId);
            trustmark.setProvider(providerEntity);

            trustmarkMap.put(trustmarkId, trustmark);
        }

        // ///////// END CREATE TEST TRUSTMARKS ///////////

    }

}
