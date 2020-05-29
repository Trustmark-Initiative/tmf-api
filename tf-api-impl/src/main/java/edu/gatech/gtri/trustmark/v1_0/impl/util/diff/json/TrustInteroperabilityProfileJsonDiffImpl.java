package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.JsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.RootNodeJsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

/**
 * Created by Nicholas on 9/19/2016.
 */
public class TrustInteroperabilityProfileJsonDiffImpl extends AbstractJsonDiff<TrustInteroperabilityProfile> {
    @Override
    public Class<? extends TrustInteroperabilityProfile> getSupportedType() { return TrustInteroperabilityProfile.class; }
    
    @Override
    public RootNodeJsonDiffField getRootNodeDiffField() {
        return JsonDiffField.rootNode(
            DiffSeverity.MINOR,
            JsonDiffField.valueExact("$TMF_VERSION"),
            JsonDiffField.valueExact("$Type"),

            // major metadata ID fields
            JsonDiffField.valueExact("Identifier"),
            JsonDiffField.valueExact("Name"),
            JsonDiffField.valueExact("Version"),
            JsonDiffField.valueExact("Deprecated"),
            JsonDiffField.valueExact("TrustExpression"),

            // minor metadata ID fields
            JsonDiffField.valueDistance("Description"),
            JsonDiffField.valueDistance("LegalNotice"),
            JsonDiffField.valueDistance("Notes"),
            JsonDiffField.minorSimpleCollection("Supersessions.SupersededBy"),
            JsonDiffField.minorSimpleCollection("Supersessions.Supersedes"),
            JsonDiffField.minorSimpleCollection("Satisfies"),
            JsonDiffField.minorSimpleCollection("Keywords", String::compareToIgnoreCase),
            JsonDiffField.valueIgnore("Primary"),
            JsonDiffField.valueIgnore("Moniker"),

            // issuer fields
            JsonDiffField.valueExact("Issuer.Identifier"),
            JsonDiffField.valueDistance("Issuer.Name"),
            JsonDiffField.valueMinor("Issuer.PrimaryContact", String::compareToIgnoreCase),
            JsonDiffField.minorSimpleCollection("Issuer.OtherContacts", String::compareToIgnoreCase),
            
            // reference fields
            JsonDiffField.fieldsCollection("References.TrustmarkDefinitionRequirements", "Identifier").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueIgnore("Name"),
                JsonDiffField.valueIgnore("Number"),
                JsonDiffField.valueIgnore("Version"),
                JsonDiffField.valueIgnore("Description"),
                JsonDiffField.valueIgnore("$id"),
                JsonDiffField.valueIgnore("$Type"),
                JsonDiffField.valueIgnore("TrustmarkDefinitionReference"),
                JsonDiffField.valueIgnore("ProviderReferences")
            ),
            JsonDiffField.fieldsCollection("References.TrustInteroperabilityProfileReferences", "Identifier").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueIgnore("Name"),
                JsonDiffField.valueIgnore("Number"),
                JsonDiffField.valueIgnore("Version"),
                JsonDiffField.valueIgnore("Description"),
                JsonDiffField.valueIgnore("$id"),
                JsonDiffField.valueIgnore("$Type")
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
            ),
            
            // ignored fields
            JsonDiffField.valueIgnore("PublicationDateTime")
        );
    }
}
