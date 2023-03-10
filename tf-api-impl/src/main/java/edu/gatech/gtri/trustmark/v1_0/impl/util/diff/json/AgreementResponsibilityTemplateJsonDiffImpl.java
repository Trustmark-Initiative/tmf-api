package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.JsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.RootNodeJsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

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
            JsonDiffField.valueExact(ATTRIBUTE_KEY_JSON_TMF_VERSION),
            JsonDiffField.valueExact(ATTRIBUTE_KEY_JSON_TYPE),
            JsonDiffField.valueExact("Name"),
            JsonDiffField.valueMinor("Category"),
            JsonDiffField.valueDistance("Definition"),
            JsonDiffField.exactSimpleCollection("TipIdentifiers")
        );
    }
    
}
