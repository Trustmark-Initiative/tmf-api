package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.JsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.RootNodeJsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

/**
 * Created by Nicholas on 9/19/2016.
 */
public class TrustmarkDefinitionJsonDiffImpl extends AbstractJsonDiff<TrustmarkDefinition> {
    @Override
    public Class<? extends TrustmarkDefinition> getSupportedType() { return TrustmarkDefinition.class; }
    
    @Override
    public RootNodeJsonDiffField getRootNodeDiffField() {
        return JsonDiffField.rootNode(
            DiffSeverity.MINOR,
            JsonDiffField.valueExact("$TMF_VERSION"),
            JsonDiffField.valueExact("$Type"),
            
            // major metadata ID fields
            JsonDiffField.valueExact("Metadata.Identifier"),
            JsonDiffField.valueExact("Metadata.Name"),
            JsonDiffField.valueExact("Metadata.Version"),
            
            // minor metadata ID fields
            JsonDiffField.valueDistance("Metadata.AssessorQualificationsDescription"),
            JsonDiffField.valueDistance("Metadata.Description"),
            JsonDiffField.valueDistance("Metadata.ExtensionDescription"),
            JsonDiffField.valueDistance("Metadata.LegalNotice"),
            JsonDiffField.valueDistance("Metadata.Notes"),
            JsonDiffField.valueDistance("Metadata.ProviderEligibilityCriteria"),
            JsonDiffField.valueDistance("Metadata.TargetProviderDescription"),
            JsonDiffField.valueDistance("Metadata.TargetRecipientDescription"),
            JsonDiffField.valueDistance("Metadata.TargetRelyingPartyDescription"),
            JsonDiffField.valueDistance("Metadata.TargetStakeholderDescription"),
//            JsonDiffField.valueDistance("Metadata.TrustmarkReferenceAttributeName"),

            // ignored metadata fields
            JsonDiffField.valueIgnore("Metadata.PublicationDateTime"),
            JsonDiffField.valueIgnore("Metadata.TrustmarkDefiningOrganization"),
            JsonDiffField.valueIgnore("Metadata.TrustmarkRevocationCriteria"),
            JsonDiffField.valueIgnore("Metadata.Deprecated"),
            JsonDiffField.valueIgnore("Metadata.Supersessions"),
            JsonDiffField.valueIgnore("Metadata.Satisfies"),
            JsonDiffField.valueIgnore("Metadata.Keywords"),
            
            // minor non metadata id fields
            JsonDiffField.valueDistance("ConformanceCriteriaPreface"),
            JsonDiffField.valueDistance("AssessmentStepsPreface"),
            JsonDiffField.valueDistance("IssuanceCriteria"),
            
            // conformance criteria
            JsonDiffField.fieldsCollection("ConformanceCriteria", "Name").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueIgnore("$id"),
                JsonDiffField.valueMinor("Number"),
                JsonDiffField.valueMinor("Description"),
                JsonDiffField.fieldsCollection("Citations", "Source.$ref").withChildren(
                    DiffSeverity.MINOR,
                    JsonDiffField.valueMinor("Description")
                )
            ),
            
            // assessment steps
            JsonDiffField.fieldsCollection("AssessmentSteps", "Name").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueIgnore("$id"),
                JsonDiffField.valueMinor("Number"),
                JsonDiffField.valueMinor("Description"),
                
                // TODO What about criterion references?  Do we care?
                JsonDiffField.valueIgnore("ConformanceCriteria"),
                
                JsonDiffField.fieldsCollection("Artifacts", "Name").withChildren(
                    DiffSeverity.MINOR,
                    JsonDiffField.valueMinor("Description")
                ),
                
                JsonDiffField.fieldsCollection("ParameterDefinitions", "Name").withChildren(
                    DiffSeverity.MINOR,
                    JsonDiffField.valueIgnore("Identifier"),
                    JsonDiffField.valueMinor("Description"),
                    JsonDiffField.valueExact("ParameterKind"),
                    JsonDiffField.valueExact("Required"),
                    JsonDiffField.exactSimpleCollection("EnumValues")
                )
            ),
            
            // terms
            JsonDiffField.fieldsCollection("Terms", "Name").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.minorSimpleCollection("Abbreviations"),
                JsonDiffField.valueDistance("Definition")
            ),
            
            // sources
            JsonDiffField.idCollection("Sources", "Source").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueExact("Identifier"),
                JsonDiffField.valueDistance("Reference")
            )
            
        );
    }
}
