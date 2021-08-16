package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkDefinitionResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.model.AssessmentStepImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkParameterBindingImpl;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;
import org.gtri.fj.data.List;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.TreeMap;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.somes;
import static org.gtri.fj.product.P.p;

public class TrustExpressionEnvironment {

    private final Map<String, TrustmarkDefinitionRequirement> trustmarkDefinitionRequirementMap = new HashMap<>();

    private final Map<URI, TrustInteroperabilityProfile> trustInteroperabilityProfileMap = new HashMap<>();
    private final Map<URI, TrustmarkDefinition> trustmarkDefinitionResolverMap = new HashMap<>();

    private final TrustInteroperabilityProfileResolverFromMap trustInteroperabilityProfileResolverFromMap = new TrustInteroperabilityProfileResolverFromMap(trustInteroperabilityProfileMap);
    private final TrustmarkDefinitionResolverFromMap trustmarkDefinitionResolverFromMap = new TrustmarkDefinitionResolverFromMap(trustmarkDefinitionResolverMap);

    public TrustInteroperabilityProfileResolver getTrustInteroperabilityProfileResolver() {
        return trustInteroperabilityProfileResolverFromMap;
    }

    public TrustmarkDefinitionResolver getTrustmarkDefinitionResolver() {
        return trustmarkDefinitionResolverFromMap;
    }

    public TrustInteroperabilityProfile getTrustInteroperabilityProfile(
            final String trustExpression) {

        final URI trustInteroperabilityProfileURI = URI.create("trust-interoperability-profile");

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfileImpl = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfileImpl.setIdentifier(trustInteroperabilityProfileURI);
        trustInteroperabilityProfileImpl.setTrustExpression(trustExpression);
        trustInteroperabilityProfileImpl.setReferences(trustmarkDefinitionRequirementMap.values().stream().map(trustmarkDefinitionRequirement -> (AbstractTIPReference) trustmarkDefinitionRequirement).collect(Collectors.toList()));

        trustInteroperabilityProfileMap.put(trustInteroperabilityProfileURI, trustInteroperabilityProfileImpl);

        return trustInteroperabilityProfileImpl;
    }

    public void setTrustmarkDefinitionRequirement(
            final String trustmarkDefinitionRequirementIdentifier) {

        if (!trustmarkDefinitionRequirementMap.containsKey(trustmarkDefinitionRequirementIdentifier)) {
            final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirementImpl = createTrustmarkDefinitionRequirementImpl(trustmarkDefinitionRequirementIdentifier);
            final TrustmarkDefinitionImpl trustmarkDefinitionImpl = createTrustmarkDefinitionImpl();

            trustmarkDefinitionRequirementMap.put(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionRequirementImpl);
            trustmarkDefinitionResolverMap.put(trustmarkDefinitionRequirementImpl.getIdentifier(), trustmarkDefinitionImpl);
        }
    }

    public Option<TrustmarkDefinitionRequirement> getTrustmarkDefinitionRequirement(
            final String trustmarkDefinitionRequirementIdentifier) {

        return fromNull(trustmarkDefinitionRequirementMap.get(trustmarkDefinitionRequirementIdentifier));
    }

    public void setTrustmarkDefinitionParameter(
            final String trustmarkDefinitionRequirementIdentifier,
            final String trustmarkDefinitionParameterIdentifier,
            final ParameterKind trustmarkDefinitionParameterKind) {

        setTrustmarkDefinitionRequirement(trustmarkDefinitionRequirementIdentifier);

        getTrustmarkDefinitionRequirement(trustmarkDefinitionRequirementIdentifier)
                .bind(trustmarkDefinitionRequirement -> fromNull(trustmarkDefinitionResolverMap.get(trustmarkDefinitionRequirement.getIdentifier())))
                .forEach(trustmarkDefinition -> {

                    final List<AssessmentStep> assessmentStepList = iterableList(trustmarkDefinition.getAssessmentSteps());

                    ((TrustmarkDefinitionImpl) trustmarkDefinition).setAssessmentSteps(assessmentStepList
                            .foldLeft(assessmentStepListInner -> assessmentStep ->
                                            iterableList(assessmentStep.getParameters()).exists(trustmarkDefinitionParameter ->
                                                    trustmarkDefinitionParameter.getIdentifier().equals(trustmarkDefinitionParameterIdentifier)) ?
                                                    assessmentStepListInner :
                                                    assessmentStepListInner.snoc(assessmentStep),
                                    List.<AssessmentStep>nil())
                            .snoc(createAssessmentStepImpl(trustmarkDefinitionParameterIdentifier, trustmarkDefinitionParameterKind))
                            .toJavaList());
                });
    }

    public Option<TrustmarkDefinitionParameter> getTrustmarkDefinitionParameter(
            final String trustmarkDefinitionRequirementIdentifier,
            final String trustmarkDefinitionParameterIdentifier) {

        return fromNull(trustmarkDefinitionRequirementMap.get(trustmarkDefinitionRequirementIdentifier))
                .bind(trustmarkDefinitionRequirement -> fromNull(trustmarkDefinitionResolverMap.get(trustmarkDefinitionRequirement.getIdentifier())))
                .map(trustmarkDefinition -> iterableList(trustmarkDefinition.getAllParameters()))
                .bind(trustmarkDefinitionParameterList -> trustmarkDefinitionParameterList.find(trustmarkDefinitionParameter -> trustmarkDefinitionParameter.getIdentifier().equals(trustmarkDefinitionParameterIdentifier)));
    }

    public Option<Trustmark> getTrustmark(
            final String trustmarkDefinitionRequirementIdentifier,
            final TreeMap<String, String> trustmarkParameterValueMap) {

        return getTrustmarkDefinitionRequirement(trustmarkDefinitionRequirementIdentifier)
                .bind(trustmarkDefinitionRequirement -> fromNull(trustmarkDefinitionResolverMap.get(trustmarkDefinitionRequirement.getIdentifier()))
                        .map(trustmarkDefinition -> createTrustmarkImpl(
                                trustmarkDefinitionRequirement,
                                trustmarkDefinition,
                                new HashSet<>(somes(trustmarkParameterValueMap.toList()
                                        .map(p -> p(getTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, p._1()), p._2()))
                                        .map(p -> p._1().map(trustmarkDefinitionParameter -> createTrustmarkParameterBindingImpl(
                                                trustmarkDefinitionParameter.getIdentifier(),
                                                trustmarkDefinitionParameter.getParameterKind(),
                                                p._2())))).toJavaList()))));
    }

    private static final TrustmarkParameterBindingImpl createTrustmarkParameterBindingImpl(
            final String trustmarkDefinitionParameterIdentifier,
            final ParameterKind trustmarkDefinitionParameterKind,
            final String trustmarkDefinitionParameterValue) {

        final TrustmarkParameterBindingImpl trustmarkParameterBindingImpl = new TrustmarkParameterBindingImpl();

        trustmarkParameterBindingImpl.setIdentifier(trustmarkDefinitionParameterIdentifier);
        trustmarkParameterBindingImpl.setParameterKind(trustmarkDefinitionParameterKind);
        trustmarkParameterBindingImpl.setValue(trustmarkDefinitionParameterValue);

        return trustmarkParameterBindingImpl;
    }

    private static final TrustmarkImpl createTrustmarkImpl(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final TrustmarkDefinition trustmarkDefinition,
            final HashSet<TrustmarkParameterBinding> trustmarkParameterBindingHashSet) {

        final TrustmarkImpl trustmark = new TrustmarkImpl();

        trustmark.setIdentifier(URI.create(format("trustmark-%s", UUID.randomUUID())));
        trustmark.setTrustmarkDefinitionReference(trustmarkDefinitionRequirement);
        iterableList(trustmarkDefinitionRequirement.getProviderReferences()).headOption().forEach(entity -> trustmark.setProvider((EntityImpl) entity));
        trustmark.setParameterBindings(trustmarkParameterBindingHashSet);

        return trustmark;
    }

    private static final AssessmentStepImpl createAssessmentStepImpl(
            final String trustmarkDefinitionParameterIdentifier,
            final ParameterKind trustmarkDefinitionParameterKind) {

        final AssessmentStepImpl assessmentStep = new AssessmentStepImpl();
        assessmentStep.setParameters(new HashSet<>(singletonList(createTrustmarkDefinitionParameterImpl(trustmarkDefinitionParameterIdentifier, trustmarkDefinitionParameterKind))));

        return assessmentStep;
    }

    private static final TrustmarkDefinitionParameterImpl createTrustmarkDefinitionParameterImpl(
            final String trustmarkDefinitionParameterIdentifier,
            final ParameterKind trustmarkDefinitionParameterKind) {

        final TrustmarkDefinitionParameterImpl trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        trustmarkDefinitionParameter.setIdentifier(trustmarkDefinitionParameterIdentifier);
        trustmarkDefinitionParameter.setName(trustmarkDefinitionParameterIdentifier);
        trustmarkDefinitionParameter.setParameterKind(trustmarkDefinitionParameterKind);

        return trustmarkDefinitionParameter;
    }

    private static final TrustmarkDefinitionImpl createTrustmarkDefinitionImpl() {

        final TrustmarkDefinitionImpl trustmarkDefinition = new TrustmarkDefinitionImpl();

        return trustmarkDefinition;
    }

    private static final TrustmarkDefinitionRequirementImpl createTrustmarkDefinitionRequirementImpl(
            final String trustmarkDefinitionRequirementIdentifier) {

        final URI trustmarkDefinitionRequirementURI = URI.create(format("trustmark-definition-requirement-%s", trustmarkDefinitionRequirementIdentifier));

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setIdentifier(trustmarkDefinitionRequirementURI);
        trustmarkDefinitionRequirement.setId(trustmarkDefinitionRequirementIdentifier);
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(createEntityImpl()));

        return trustmarkDefinitionRequirement;
    }

    private static final EntityImpl createEntityImpl() {

        final EntityImpl entity = new EntityImpl();
        entity.setIdentifier(URI.create(format("entity-%s", UUID.randomUUID())));

        return entity;
    }

    private final URI uriForTrustmarkDefinitionRequirementIdentifier(
            final String trustmarkDefinitionRequirementIdentifier) {

        return URI.create(format("trustmark-definition-requirement-%s", trustmarkDefinitionRequirementIdentifier));
    }
}
