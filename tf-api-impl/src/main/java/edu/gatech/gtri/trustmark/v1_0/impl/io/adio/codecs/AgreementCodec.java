package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentInputContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentInputDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentOutputContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentOutputSerializer;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.AgreementImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementAttachment;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementLegalSection;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementNonBindingSection;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementParty;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibility;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.ListStyleType;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.dom4j.QName;

import java.util.*;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AgreementCodec extends Codec<Agreement> {

    @Override
    public Class<Agreement> getSupportedType() {
        return Agreement.class;
    }

    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() {
        return null;
    }

    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() {
        return null;
    }

    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() {
        BidiMap<String, Class<?>> result = new DualHashBidiMap<>();
        result.put("AgreementNonBindingSections", AgreementNonBindingSection.class);
        result.put("AgreementLegalSections", AgreementLegalSection.class);
        result.put("AgreementTerms", Term.class);
        result.put("AgreementResponsibilities", AgreementResponsibility.class);
        result.put("AgreementParties", AgreementParty.class);
        result.put("AgreementAttachments", AgreementAttachment.class);
        return result;
    }

    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() {
        return null;
    }

    @Override
    public Set<String> getCdataValueNames() {
        return new LinkedHashSet<>(Arrays.asList(
                "Title",
                "Description",
                "SignaturePageText"
        ));
    }

    @Override
    public String getIdFor(Agreement obj) {
        return null;
    }

    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
            Agreement obj,
            AbstractDocumentOutputSerializer<Agreement, W, Ex> ados,
            AbstractDocumentOutputContext<W> context
    )
            throws Ex {
        ados.serializeValueInObject(context, "Identifier", obj.getIdentifier());
        ados.serializeValueInObject(context, "Title", obj.getTitle());
        ados.serializeValueInObject(context, "Version", obj.getVersion());
        ados.serializeValueInObject(context, "Description", obj.getDescription());
        ados.serializeValueInObject(context, "CreationDateTime", obj.getCreationDateTime());
        Map<String, Object> durationMap = new HashMap<>();
        durationMap.put("EffectiveDateTime", obj.getEffectiveDateTime());
        durationMap.put("TerminationDateTime", obj.getTerminationDateTime());
        ados.serializeMapLiteralObjectInObject(context, "Duration", durationMap);
        ados.serializeObjectSequenceInObject(context, AgreementNonBindingSection.class, obj.getNonBindingSections());
        ados.serializeValueInObject(context, "LegalSectionListStyleType", obj.getListStyleType());
        ados.serializeObjectSequenceInObject(context, AgreementLegalSection.class, obj.getLegalSections());
        ados.serializeNonCodecObjectSequenceInObject(context, Term.class, obj.getTerms());
        ados.serializeObjectSequenceInObject(context, AgreementResponsibility.class, obj.getResponsibilities());
        ados.serializeObjectSequenceInObject(context, AgreementParty.class, obj.getParties());
        ados.serializeValueInObject(context, "SignaturePageText", obj.getSignaturePageText());
        ados.serializeObjectSequenceInObject(context, AgreementAttachment.class, obj.getAttachments());
    }

    @Override
    public <O> Agreement deserializeFullObject(
            O node,
            String id,
            String sourceString,
            String sourceType,
            AbstractDocumentInputDeserializer<Agreement, O, ?, ?> adid,
            AbstractDocumentInputContext context
    )
            throws ParseException {
        AgreementImpl result = new AgreementImpl();
        result.setIdentifier(adid.deserializeUriValueInObject(node, true, "Identifier"));
        result.setTitle(adid.deserializeStringValueInObject(node, true, "Title"));
        result.setVersion(adid.deserializeStringValueInObject(node, true, "Version"));
        result.setDescription(adid.deserializeStringValueInObject(node, false, "Description"));
        result.setCreationDateTime(adid.deserializeDateValueInObject(node, true, "CreationDateTime"));
        result.setEffectiveDateTime(adid.deserializeDateValueInObject(node, false, "Duration/EffectiveDateTime"));
        result.setTerminationDateTime(adid.deserializeDateValueInObject(node, false, "Duration/TerminationDateTime"));
        result.setNonBindingSections(adid.deserializeObjectSequenceInObject(node, context, true, AgreementNonBindingSection.class));
        result.setListStyleType(adid.deserializeEnumValueInObject(node, false, ListStyleType.class, "LegalSectionListStyleType"));
        result.setLegalSections(adid.deserializeObjectSequenceInObject(node, context, true, AgreementLegalSection.class));
        result.setTerms(adid.deserializeTermSequenceInObject(node, context, true));
        result.setResponsibilities(adid.deserializeObjectSequenceInObject(node, context, true, AgreementResponsibility.class));
        result.setParties(adid.deserializeObjectSequenceInObject(node, context, true, AgreementParty.class));
        result.setSignaturePageText(adid.deserializeStringValueInObject(node, false, "SignaturePageText"));
        result.setAttachments(adid.deserializeObjectSequenceInObject(node, context, false, AgreementAttachment.class));
        return result;
    }

}
