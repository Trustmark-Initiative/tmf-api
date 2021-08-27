package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileReferenceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkStatusReportImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.HashMap;
import java.util.List;

/**
 * Deserializes XML strings into Trustmark objects.
 * <br/><br/>
 * Created by brad on 12/9/15.
 */
public class TrustInteroperabilityProfileXmlDeserializer extends AbstractDeserializer {

    private static final Logger log = LogManager.getLogger(TrustInteroperabilityProfileXmlDeserializer.class);

    public static TrustInteroperabilityProfile deserialize(String xml ) throws ParseException {
        log.debug("Request to deserialize TrustInteroperabilityProfile XML...");

        XmlHelper.validateXml(xml);

        Element root = XmlHelper.readWithDom4j(xml);
        if( !root.getQName().getName().equals("TrustInteroperabilityProfile") ){
            throw new ParseException("Root element of a TrustInteroperabilityProfile must be tf:TrustInteroperabilityProfile, you have: "+root.getName());
        }

        TrustInteroperabilityProfile tip = fromDom4j(root, xml);
        return tip;
    }//end deserialize()


    public static TrustInteroperabilityProfile fromDom4j( Element tipXml, String originalSource ) throws ParseException {
        log.debug("Constructing new TrustInteroperabilityProfile from element["+tipXml.getName()+"]...");
        TrustInteroperabilityProfileImpl tip = new TrustInteroperabilityProfileImpl();

        tip.setOriginalSource(originalSource);
        tip.setOriginalSourceType("text/xml");
        tip.setId(getString(tipXml, "/@tf:id", false));

        tip.setTypeName("TrustInteroperabilityProfile");
        tip.setIdentifier(getUri(tipXml, "tf:Identifier", true));
        tip.setName(getString(tipXml, "tf:Name", true));
        tip.setVersion(getString(tipXml, "tf:Version", true));
        tip.setDescription(getString(tipXml, "tf:Description", true));

        String primaryTip = getString(tipXml, "tf:Primary", false);
        if(primaryTip != null && primaryTip.equals("true")) {
            tip.setPrimary(Boolean.TRUE);
        }
        String moniker = getString(tipXml, "tf:Moniker", false);
        if(moniker != null) {
            tip.setMoniker(moniker);
        }
        tip.setPublicationDateTime(getDate(tipXml, "tf:PublicationDateTime", true));

        tip.setLegalNotice(getString(tipXml, "tf:LegalNotice", false));
        tip.setNotes(getString(tipXml, "tf:Notes", false));

        tip.setIssuer(readEntity((Element) tipXml.selectSingleNode("./tf:Issuer")));

        if( Boolean.TRUE.equals(tipXml.selectObject("count(./tf:Supersessions/*) > 0")) ){
            List<Node> supersedesList = tipXml.selectNodes("./tf:Supersessions/tf:Supersedes");
            if( supersedesList != null && !supersedesList.isEmpty() ){
                for( Node supersedesElement : supersedesList ){
                    tip.addToSupersedes(readTrustmarkFrameworkIdentifiedObject(supersedesElement));
                }
            }
            List<Node> supersededByList = tipXml.selectNodes("./tf:Supersessions/tf:SupersededBy");
            if( supersededByList != null && !supersededByList.isEmpty() ){
                for( Node supersededByElement : supersededByList ){
                    tip.addToSupersededBy(readTrustmarkFrameworkIdentifiedObject(supersededByElement));
                }
            }
        }

        if( Boolean.TRUE.equals(tipXml.selectObject("count(./tf:Deprecated) > 0")) &&
                Boolean.TRUE.equals(Boolean.parseBoolean( (String) tipXml.selectObject("string(./tf:Deprecated)")))){
            tip.setDeprecated(Boolean.TRUE);
        }

        List<Node> satisfiesList = tipXml.selectNodes("./tf:Satisfactions/tf:Satisfies");
        if( satisfiesList != null && !satisfiesList.isEmpty() ){
            for( Node satisfiesElement : satisfiesList ){
                tip.addToSatisfies(readTrustmarkFrameworkIdentifiedObject(satisfiesElement));
            }
        }


        List<Node> knownConflictsList = tipXml.selectNodes("./tf:KnownConflicts/tf:KnownConflict");
        if( knownConflictsList != null && !knownConflictsList.isEmpty() ){
            for( Node knownConflictElement : knownConflictsList ){
                tip.addToKnownConflict(readTrustmarkFrameworkIdentifiedObject(knownConflictElement));
            }
        }


        if( Boolean.TRUE.equals(tipXml.selectObject("count(./tf:Keywords/*) > 0")) ){
            List<Node> keywordsList = tipXml.selectNodes("./tf:Keywords/tf:Keyword");
            if( keywordsList != null && !keywordsList.isEmpty() ){
                for( Node keywordElement : keywordsList ){
                    tip.addToKeywords(getString(keywordElement, ".", true));
                }
            }
        }


        List<Node> termsXmlList = tipXml.selectNodes("./tf:Terms/tf:Term");
        if( termsXmlList != null && !termsXmlList.isEmpty() ){
            for( Node term : termsXmlList ){
                tip.addTerm(readTerm(term));
            }
        }

        HashMap<String, Source> sourceMap = new HashMap<>();
        List<Node> sourcesXmlList = tipXml.selectNodes("./tf:Sources/tf:Source");
        if( sourcesXmlList != null && !sourcesXmlList.isEmpty() ){
            for( Node sourceXml : sourcesXmlList ){
                Source source = readSource(sourceXml);
                tip.addSource(source);
                String id = getString(sourceXml, "./@tf:id", true);
                sourceMap.put(id, source);
            }
        }



        tip.setTrustExpression(getString(tipXml, "./tf:TrustExpression", true));

        HashMap<String, Entity> providerReferenceMap = new HashMap<>();
        List<Node> allFullProviderEntries = tipXml.selectNodes(".//tf:ProviderReference[string-length(string(./@tf:id)) > 0]");
        if( allFullProviderEntries != null && !allFullProviderEntries.isEmpty() ){
            for( Node providerXml : allFullProviderEntries ){
                Entity entity = readEntityReference(providerXml);
                String id = ((String) providerXml.selectObject("string(./@tf:id)")).trim();
                providerReferenceMap.put(id, entity);
            }
        }

        List<Node> refsList = tipXml.selectNodes("./tf:References/*");
        if( refsList != null && !refsList.isEmpty() ){
            for( Node ref : refsList ){
                if( ref.getName().contains("TrustInteroperabilityProfileReference") ){
                    TrustInteroperabilityProfileReferenceImpl tipRefImpl = new TrustInteroperabilityProfileReferenceImpl();
                    tipRefImpl.setTypeName("TrustInteroperabilityProfileReference");
                    tipRefImpl.setId(getString(ref, "./@tf:id", true));
                    tipRefImpl.setIdentifier(getUri(ref, "./tf:Identifier", true));
                    if (exists(ref, "./tf:Number")) {
                        tipRefImpl.setNumber(getNumber(ref, "./tf:Number", false).intValue());
                    }
                    tipRefImpl.setName(getString(ref, "./tf:Name", false));
                    tipRefImpl.setVersion(getString(ref, "./tf:Version", false));
                    tipRefImpl.setDescription(getString(ref, "./tf:Description", false));
                    tip.addReference(tipRefImpl);
                }else if( ref.getName().contains("TrustmarkDefinitionRequirement") ){
                    tip.addReference(readTDRequirement(ref, providerReferenceMap));
                }
            }
        }


        return tip;
    }//end fromDom4()

    private static TrustmarkDefinitionRequirement readTDRequirement(Node ref, HashMap<String, Entity> providerReferenceMap) throws ParseException {
        TrustmarkDefinitionRequirementImpl tdReq = new TrustmarkDefinitionRequirementImpl();
        tdReq.setTypeName("TrustmarkDefinitionRequirement");
        tdReq.setId(getString(ref, "./@tf:id", true));
        tdReq.setIdentifier(getUri(ref, "./tf:TrustmarkDefinitionReference/tf:Identifier", true));
        tdReq.setName(getString(ref, "./tf:TrustmarkDefinitionReference/tf:Name", false));
        if(exists(ref, "./tf:TrustmarkDefinitionReference/tf:Number")) {
            tdReq.setNumber(getNumber(ref, "./tf:TrustmarkDefinitionReference/tf:Number", false).intValue());
        }
        tdReq.setVersion(getString(ref, "./tf:TrustmarkDefinitionReference/tf:Version", false));
        tdReq.setDescription(getString(ref, "./tf:TrustmarkDefinitionReference/tf:Description", false));
        List<Node> providerRefs = ref.selectNodes("./tf:ProviderReference");
        if( providerRefs != null && !providerRefs.isEmpty() ){
            for( Node providerXml : providerRefs ){
                Entity provider = null;
                String id = (String) providerXml.selectObject("string(./@tf:id)");
                if(StringUtils.isBlank(id) )
                    id = (String) providerXml.selectObject("string(./@tf:ref)");
                if(StringUtils.isBlank(id) ){
                    log.debug("For TD Requirement["+tdReq.getIdentifier()+"], a ProviderReference is missing either id or ref value.  Assuming this is an anonymous inner one.");
                    provider = readEntityReference(providerXml);
                }else{
                    provider = providerReferenceMap.get(id);
                    if( provider == null ){
                        throw new ParseException("For TD Requirement["+tdReq.getIdentifier()+"], Could not find ProviderReference with id '"+id+"'");
                    }
                }
                tdReq.addProviderReference(provider);
            }
        }
        return tdReq;
    }


}//end TrustmarkXmlDeserializer
