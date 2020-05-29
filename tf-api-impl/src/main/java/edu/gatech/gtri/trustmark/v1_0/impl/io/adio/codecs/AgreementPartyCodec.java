package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.AgreementPartyImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementParty;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibility;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementSignatory;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.dom4j.QName;

import java.util.*;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AgreementPartyCodec extends Codec<AgreementParty> {
    @Override
    public Class<AgreementParty> getSupportedType() { return AgreementParty.class; }
    
    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() { return null; }
    
    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() { return null; }
    
    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() {
        BidiMap<String, Class<?>> result = new DualHashBidiMap<>();
        result.put("PartyResponsibilities", AgreementResponsibility.class);
        result.put("PartySignatories", AgreementSignatory.class);
        return result;
    }
    
    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() { return null; }
    
    @Override
    public Set<String> getCdataValueNames() {
        return new HashSet<>(Arrays.asList(
            "Notes"
        ));
    }
    
    @Override
    public String getIdFor(AgreementParty obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AgreementParty obj,
        AbstractDocumentOutputSerializer<AgreementParty, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex
    {
        ados.serializeNonCodecObjectInObject(context, "Organization", obj.getOrganization());
        ados.serializeValueInObject(context, "AbbreviatedName", obj.getAbbreviatedName());
        ados.serializeObjectSequenceInObject(context, AgreementResponsibility.class, obj.getResponsibilities());
        ados.serializeObjectSequenceInObject(context, AgreementSignatory.class, obj.getSignatories());
    }
    
    @Override
    public <O> AgreementParty deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AgreementParty, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        AgreementPartyImpl result = new AgreementPartyImpl();
        result.setOrganization(adid.deserializeEntityInObject(node, true, "Organization"));
        result.setAbbreviatedName(adid.deserializeStringValueInObject(node, false, "AbbreviatedName"));
        result.setResponsibilities(adid.deserializeObjectSequenceInObject(node, context, true, AgreementResponsibility.class));
        result.setSignatories(adid.deserializeObjectSequenceInObject(node, context, true, AgreementSignatory.class));
        return result;
    }
}
