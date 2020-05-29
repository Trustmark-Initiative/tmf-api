package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.JsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.RootNodeJsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

/**
 * Created by Nicholas Saney on 2017-06-06.
 */
public class AgreementResponsibilityTemplateJsonDiffImpl extends AbstractJsonDiff<AgreementResponsibilityTemplate> {
    
    @Override
    public Class<? extends AgreementResponsibilityTemplate> getSupportedType() {
        return AgreementResponsibilityTemplate.class;
    }
    
    @Override
    public RootNodeJsonDiffField getRootNodeDiffField() {
        return JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.valueExact("$TMF_VERSION"),
            JsonDiffField.valueExact("$Type"),
            JsonDiffField.valueExact("Name"),
            JsonDiffField.valueMinor("Category"),
            JsonDiffField.valueDistance("Definition"),
            JsonDiffField.exactSimpleCollection("TipIdentifiers")
        );
    }
    
}
