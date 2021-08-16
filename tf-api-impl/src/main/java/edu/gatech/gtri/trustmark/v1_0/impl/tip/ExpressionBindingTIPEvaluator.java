package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluationException;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * Performs TIP Evaluation by trying to execute the TIP Expression as though it was a supported by whatever configured
 * {@link javax.script.ScriptEngine} there is.  The Trustmarks are bound as variables to the script.
 * <br/><br/>
 * Created by brad on 5/31/16.
 */
public class ExpressionBindingTIPEvaluator implements TIPEvaluator {
    //==================================================================================================================
    //  Static Variables
    //==================================================================================================================
    private static final Logger log = LogManager.getLogger(ExpressionBindingTIPEvaluator.class);

    //==================================================================================================================
    //  Interface Implementation
    //==================================================================================================================
    @Override
    public TIPEvaluation evaluateTIPAgainstTrustmarks(TrustInteroperabilityProfile tip, Set<Trustmark> trustmarks) throws TIPEvaluationException {
        TIPEvaluationImpl tipEvaluation = new TIPEvaluationImpl();
        tipEvaluation.setEvaluationDate(Calendar.getInstance().getTime());
        tipEvaluation.setTip(tip);

        log.info("Evaluating TIP[" + tip.getIdentifier().toString() + "] trust expression[" + tip.getTrustExpression() + "]...");
        log.info("This Org has " + trustmarks.size() + " trustmarks: ");
        for (Trustmark tm : trustmarks) {
            log.info("   [" + tm.getIdentifier().toString() + "] : TD=[" + tm.getTrustmarkDefinitionReference().getIdentifier().toString() + "] : Issuer[" + tm.getProvider().getIdentifier().toString() + "]");
        }

        Map<String, TIPEvaluation> subTipEvaluations = new HashMap<>();

        for (AbstractTIPReference abstractTIPReference : tip.getReferences()) {
            if (abstractTIPReference.isTrustInteroperabilityProfileReference()) {
                TrustInteroperabilityProfileReference tipRef = (TrustInteroperabilityProfileReference) abstractTIPReference;

                TrustInteroperabilityProfileResolver resolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
                TrustInteroperabilityProfile subTip = null;
                try {
                    subTip = resolver.resolve(tipRef.getIdentifier());
                } catch (ResolveException re) {
                    log.warn("Cannot evaluate TIP[" + tip.getIdentifier().toString() + "] because a subTip[" + tipRef.getIdentifier().toString() + "] could not be resolved: " + re.toString());
                    throw new TIPEvaluationException(re);
                }

                log.debug("Evaluating subTIP: " + subTip.getIdentifier().toString());
                TIPEvaluation evaluation = evaluateTIPAgainstTrustmarks(subTip, trustmarks);
                subTipEvaluations.put(subTip.getIdentifier().toString(), evaluation);
                tipEvaluation.getSubTipEvaluations().put(subTip, evaluation);
            }
        }


        // Now that all sub-Tips have been identified, we create the current binding context...
        Map<String, Object> binding = doBinding(tip, trustmarks, subTipEvaluations);

        ExpressionBindingLanguageProvider provider = getExpressionBindingLanguageProvider();

        // Now we execute the expression against the binding context...
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName(provider.getExpressionBindingLanguage());
        Bindings bindings = new SimpleBindings();
        bindings.putAll(binding);
        String translatedExpression = provider.transformExpression(tip.getTrustExpression());
        log.debug("Performing expression evaluation on: " + translatedExpression);
        try {
            Object resultObj = engine.eval(translatedExpression, bindings);
            log.debug("Successfully evaluated expression: " + resultObj);
            if (resultObj instanceof Boolean) {
                tipEvaluation.setSatisfied((Boolean) resultObj);
            } else {
                throw new UnsupportedOperationException("Expected boolean result from TIP Expression, but instead got: " + resultObj);
            }
        } catch (Exception e) {
            log.error("Error evaluating TIP[" + tip.getIdentifier().toString() + "] expression: " + tip.getTrustExpression(), e);
            throw new TIPEvaluationException(e);
        }
        return tipEvaluation;
    }

    @Override
    public TIPEvaluation evaluateTIPAgainstTDRequirements(TrustInteroperabilityProfile tip, Set<TrustmarkDefinitionRequirement> tdRequirements) throws TIPEvaluationException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Boolean calculatesSatisfactionGap() {
        return false;
    }

    //==================================================================================================================
    //  Helper Methods
    //==================================================================================================================
    private ExpressionBindingLanguageProvider getExpressionBindingLanguageProvider() {
        ServiceLoader<ExpressionBindingLanguageProvider> loader = ServiceLoader.load(ExpressionBindingLanguageProvider.class);
        Iterator<ExpressionBindingLanguageProvider> providerIterator = loader.iterator();
        ExpressionBindingLanguageProvider provider = null;
        while (providerIterator.hasNext()) {
            ExpressionBindingLanguageProvider cur = providerIterator.next();
            if (provider == null) {
                provider = cur;
            } else if (provider.getPriority() < cur.getPriority()) {
                provider = cur;
            }
        }
        return provider;
    }


    /**
     * Converts the given trustmarks to a binding that the expression engine will use.
     */
    private Map<String, Object> doBinding(TrustInteroperabilityProfile tip, Set<Trustmark> trustmarks, Map<String, TIPEvaluation> previousEvaluations)
            throws TIPEvaluationException {
        log.debug("Performing Binding for TIP[" + tip.getIdentifier().toString() + "]:");
        Map<String, Object> bindings = new HashMap<>();
        for (AbstractTIPReference abstractTIPReference : tip.getReferences()) {
            if (abstractTIPReference.isTrustInteroperabilityProfileReference()) {
                TrustInteroperabilityProfileReference tipRef = (TrustInteroperabilityProfileReference) abstractTIPReference;
                TIPEvaluation previousEvaluation = previousEvaluations.get(tipRef.getIdentifier().toString());
                if (previousEvaluation == null) {
                    log.error("While binding TIP Reference[" + tipRef.getId() + "] for TIP[" + tip.getIdentifier().toString() + "], could not find the previous evaluation [TIPID = " + tipRef.getIdentifier().toString() + "]!");
                    throw new MissingPreviousEvaluationException("Could not find previous evaluation for TIP: " + tipRef.getIdentifier().toString());
                }
                String variableId = tipRef.getId();
                log.debug("  Binding[" + variableId + "] => [" + previousEvaluation.isSatisfied() + "]");
                bindings.put(variableId, previousEvaluation.isSatisfied());
            } else if (abstractTIPReference.isTrustmarkDefinitionRequirement()) {
                TrustmarkDefinitionRequirement tdReq = (TrustmarkDefinitionRequirement) abstractTIPReference;
                TrustmarkUtility.satisfyingTrustmarkList(tdReq, org.gtri.fj.data.List.iterableList(trustmarks)).headOption()
                        .map(tm -> {
                            if (tm.getParameterBindings() == null || tm.getParameterBindings().isEmpty()) {
                                log.debug("  Binding[" + tdReq.getId() + "] => true");
                                bindings.put(tdReq.getId(), true); // Indicates there is a trustmark here, that's all.
                            } else {
                                Map<String, Object> paramMap = createTrustmarkParameterBindingMap(tm.getParameterBindings());
                                log.debug("  Binding[" + tdReq.getId() + "] => " + paramMap);
                                bindings.put(tdReq.getId(), paramMap);
                            }

                            return null;
                        })
                        .orSome(() ->
                        {
                            log.warn("TIP[" + tip.getIdentifier().toString() + "] calls out TD[" + tdReq.getIdentifier().toString() + "] as ID[" + tdReq.getId() + "], but no Trustmark against that TD Requirement exists in this set.  Assuming false...");
                            log.debug("  Binding[" + tdReq.getId() + "] => false");
                            bindings.put(tdReq.getId(), false);

                            return null;
                        });
            } else {
                log.error("Abstract TIP Reference[" + abstractTIPReference.getId() + "] in TIP[" + tip.getIdentifier().toString() + "] is not a TD Requirement nor a TIP!");
                throw new TIPEvaluationException("Abstract TIP Reference[" + abstractTIPReference.getId() + "] in TIP[" + tip.getIdentifier().toString() + "] is not a TD Requirement nor a TIP!");
            }
        }
        return bindings;
    }//end doBinding()

    private Map<String, Object> createTrustmarkParameterBindingMap(Set<TrustmarkParameterBinding> bindings) {
        Map<String, Object> binding = new HashMap<>();
        for (TrustmarkParameterBinding param : bindings) {
            if (param.getParameterKind() == ParameterKind.BOOLEAN) {
                binding.put(param.getIdentifier(), param.getBooleanValue());
            } else if (param.getParameterKind() == ParameterKind.NUMBER) {
                binding.put(param.getIdentifier(), param.getNumericValue());
            } else if (param.getParameterKind() == ParameterKind.DATETIME) {
                binding.put(param.getIdentifier(), param.getDateTimeValue());
            } else if (param.getParameterKind() == ParameterKind.ENUM_MULTI) {
                binding.put(param.getIdentifier(), param.getStringListValue());
            } else {
                binding.put(param.getIdentifier(), param.getStringValue());
            }
        }
        return binding;
    }
}/* end ExpressionBindingTIPEvaluator */
