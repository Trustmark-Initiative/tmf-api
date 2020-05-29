package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.JsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.RootNodeJsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

/**
 * Created by Nicholas on 02/09/2017.
 */
public class AgreementJsonDiffImpl extends AbstractJsonDiff<Agreement> {
    
    @Override
    public Class<? extends Agreement> getSupportedType() { return Agreement.class; }
    
    @Override
    public RootNodeJsonDiffField getRootNodeDiffField() {
        return JsonDiffField.rootNode(
            DiffSeverity.MINOR,
            JsonDiffField.valueExact("$TMF_VERSION"),
            JsonDiffField.valueExact("$Type"),
            JsonDiffField.valueExact("Identifier"),
            JsonDiffField.valueExact("Title"),
            JsonDiffField.valueExact("Version"),
            JsonDiffField.valueDistance("Description"),
            JsonDiffField.valueMinor("CreationDateTime"),
            JsonDiffField.valueExact("Duration.EffectiveDateTime"),
            JsonDiffField.valueExact("Duration.TerminationDateTime"),
            JsonDiffField.valueMinor("LegalSectionListStyleType"),

            JsonDiffField.fieldsCollection("AgreementNonBindingSections", "Title").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueMinor("Index"),
                JsonDiffField.valueMinor("Text")
            ),

            JsonDiffField.fieldsCollection("AgreementLegalSections", "Title", "Text").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueExact("Index"),
                JsonDiffField.valueMinor("ListStyleType"),
                JsonDiffField.recursiveCollection("SubSections")
            ),

            JsonDiffField.fieldsCollection("AgreementTerms", "Name").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueDistance("Definition"),
                JsonDiffField.exactSimpleCollection("Abbreviations")
            ),

            JsonDiffField.idCollection("AgreementResponsibilities", "AgreementResponsibility").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueExact("Name"),
                JsonDiffField.valueMinor("Category"),
                JsonDiffField.valueDistance("Definition"),
                JsonDiffField.idCollection("SupplementedTIPSnapshots", "SupplementedTIPSnapshot").withChildren(
                    DiffSeverity.MINOR,
                    JsonDiffField.valueExact("SupplementalLegalText"),
                    JsonDiffField.valueMinor("TrustInteroperabilityProfileSnapshot.SnapshotDateTime"),
                    JsonDiffField.valueExact("TrustInteroperabilityProfileSnapshot.Index"),
                    JsonDiffField.valueExact("TrustInteroperabilityProfileSnapshot.ExpressionId"),
                    JsonDiffField.valueIgnore("TrustInteroperabilityProfileSnapshot.TrustInteroperabilityProfile"),
                    JsonDiffField.idCollection("TrustmarkDefinitionSnapshots", "TrustmarkDefinitionReference.Identifier").withChildren(
                        DiffSeverity.MINOR,
                        JsonDiffField.valueMinor("SnapshotDateTime"),
                        JsonDiffField.valueExact("Index"),
                        JsonDiffField.valueExact("ExpressionId"),
                        JsonDiffField.valueExact("TrustmarkDefinitionReference.Name"),
                        JsonDiffField.valueExact("TrustmarkDefinitionReference.Version")
                    ),
                    JsonDiffField.recursiveCollection("SupplementedTIPSnapshots")
                )
            ),

            JsonDiffField.fieldsCollection("AgreementParties", "Organization.Identifier").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueMinor("AbbreviatedName"),
                JsonDiffField.valueExact("Organization.Name"),
                JsonDiffField.valueIgnore("Organization.PrimaryContact.Kind"),
                JsonDiffField.valueDistance("Organization.PrimaryContact.Responder"),
                JsonDiffField.valueMinor("Organization.PrimaryContact.Email"),
                JsonDiffField.valueMinor("Organization.PrimaryContact.Telephone"),
                JsonDiffField.valueDistance("Organization.PrimaryContact.PhysicalAddress"),
                JsonDiffField.valueDistance("Organization.PrimaryContact.MailingAddress"),
                JsonDiffField.valueMinor("Organization.PrimaryContact.Notes"),
                JsonDiffField.refOnlyCollection("PartyResponsibilities", "AgreementResponsibility"),
                JsonDiffField.fieldsCollection("PartySignatories", "IndividualName").withChildren(
                    DiffSeverity.MINOR,
                    JsonDiffField.valueExact("IndividualTitle"),
                    JsonDiffField.valueExact("DivisionName"),
                    JsonDiffField.valueExact("AuxiliaryText")
                )
            ),

            JsonDiffField.valueExact("SignaturePageText"),
            
            JsonDiffField.fieldsCollection("AgreementAttachments", "Name").withChildren(
                DiffSeverity.MINOR,
                JsonDiffField.valueExact("Index"),
                JsonDiffField.valueDistance("Description"),
                JsonDiffField.valueExact("MimeType"),
                JsonDiffField.valueExact("DataBase64")
            )
        );
    }
    
}
