package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileReferenceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.exists;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getDate;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getNumber;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getString;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getUri;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readEntity;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readEntityReference;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readSource;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readTerm;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readTrustmarkFrameworkIdentifiedObject;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Implementations deserialize XML and, optionally, a URI into a Trust
 * Interoperability Profile.
 *
 * @author GTRI Trustmark Team
 */
public class TrustInteroperabilityProfileXmlDeserializer implements XmlDeserializer<TrustInteroperabilityProfile> {

    private static final Logger log = LoggerFactory.getLogger(TrustInteroperabilityProfileXmlDeserializer.class);

    private final boolean withTerms;

    public TrustInteroperabilityProfileXmlDeserializer(final boolean withTerms) {
        this.withTerms = withTerms;
    }

    public TrustInteroperabilityProfile deserialize(final String xml, final URI uri) throws ParseException {

        requireNonNull(xml);

        log.debug("Deserializing Trust Interoperability Profile XML . . .");

        XmlHelper.validateXml(xml);

        final Element element = XmlHelper.readWithDom4j(xml);
        final String elementName = "TrustInteroperabilityProfile";

        if (!element.getQName().getName().equals(elementName)) {
            throw new ParseException(format("The document element of a Trust Interoperability Profile must be '%s'; it was '%s'.", elementName, element.getName()));

        }

        return fromDom4j(element, uri, withTerms);
    }

    public static TrustInteroperabilityProfile fromDom4j(final Element element, final URI uri, final boolean withTerms) throws ParseException {

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();

        trustInteroperabilityProfile.setId(getString(element, "./@tf:id", false));

        trustInteroperabilityProfile.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUST_INTEROPERABILITY_PROFILE);
        trustInteroperabilityProfile.setIdentifier(getUri(element, "tf:Identifier", true));
        trustInteroperabilityProfile.setName(getString(element, "tf:Name", true));
        trustInteroperabilityProfile.setVersion(getString(element, "tf:Version", true));
        trustInteroperabilityProfile.setDescription(getString(element, "tf:Description", true));

        String primaryTip = getString(element, "tf:Primary", false);
        if (primaryTip != null && primaryTip.equals("true")) {
            trustInteroperabilityProfile.setPrimary(Boolean.TRUE);
        }
        String moniker = getString(element, "tf:Moniker", false);
        if (moniker != null) {
            trustInteroperabilityProfile.setMoniker(moniker);
        }
        trustInteroperabilityProfile.setPublicationDateTime(getDate(element, "tf:PublicationDateTime", true));

        trustInteroperabilityProfile.setLegalNotice(getString(element, "tf:LegalNotice", false));
        trustInteroperabilityProfile.setNotes(getString(element, "tf:Notes", false));

        trustInteroperabilityProfile.setIssuer(readEntity((Element) element.selectSingleNode("./tf:Issuer")));

        if (Boolean.TRUE.equals(element.selectObject("count(./tf:Supersessions/*) > 0"))) {
            List<Node> supersedesList = element.selectNodes("./tf:Supersessions/tf:Supersedes");
            if (supersedesList != null && !supersedesList.isEmpty()) {
                for (Node supersedesElement : supersedesList) {
                    trustInteroperabilityProfile.addToSupersedes(readTrustmarkFrameworkIdentifiedObject(supersedesElement));
                }
            }
            List<Node> supersededByList = element.selectNodes("./tf:Supersessions/tf:SupersededBy");
            if (supersededByList != null && !supersededByList.isEmpty()) {
                for (Node supersededByElement : supersededByList) {
                    trustInteroperabilityProfile.addToSupersededBy(readTrustmarkFrameworkIdentifiedObject(supersededByElement));
                }
            }
        }

        if (Boolean.TRUE.equals(element.selectObject("count(./tf:Deprecated) > 0")) &&
                Boolean.TRUE.equals(Boolean.parseBoolean((String) element.selectObject("string(./tf:Deprecated)")))) {
            trustInteroperabilityProfile.setDeprecated(Boolean.TRUE);
        }

        List<Node> satisfiesList = element.selectNodes("./tf:Satisfactions/tf:Satisfies");
        if (satisfiesList != null && !satisfiesList.isEmpty()) {
            for (Node satisfiesElement : satisfiesList) {
                trustInteroperabilityProfile.addToSatisfies(readTrustmarkFrameworkIdentifiedObject(satisfiesElement));
            }
        }


        List<Node> knownConflictsList = element.selectNodes("./tf:KnownConflicts/tf:KnownConflict");
        if (knownConflictsList != null && !knownConflictsList.isEmpty()) {
            for (Node knownConflictElement : knownConflictsList) {
                trustInteroperabilityProfile.addToKnownConflict(readTrustmarkFrameworkIdentifiedObject(knownConflictElement));
            }
        }


        if (Boolean.TRUE.equals(element.selectObject("count(./tf:Keywords/*) > 0"))) {
            List<Node> keywordsList = element.selectNodes("./tf:Keywords/tf:Keyword");
            if (keywordsList != null && !keywordsList.isEmpty()) {
                for (Node keywordElement : keywordsList) {
                    trustInteroperabilityProfile.addToKeywords(getString(keywordElement, ".", true));
                }
            }
        }

        if (withTerms) {
            List<Node> termsXmlList = element.selectNodes("./tf:Terms/tf:Term");
            if (termsXmlList != null && !termsXmlList.isEmpty()) {
                for (Node term : termsXmlList) {
                    trustInteroperabilityProfile.addTerm(readTerm(term));
                }
            }
        }

        List<Node> sourcesXmlList = element.selectNodes("./tf:Sources/tf:Source");
        if (sourcesXmlList != null && !sourcesXmlList.isEmpty()) {
            for (Node sourceXml : sourcesXmlList) {
                Source source = readSource(sourceXml);
                trustInteroperabilityProfile.addSource(source);
            }
        }


        trustInteroperabilityProfile.setTrustExpression(getString(element, "./tf:TrustExpression", true));

        HashMap<String, Entity> providerReferenceMap = new HashMap<>();
        List<Node> allFullProviderEntries = element.selectNodes(".//tf:ProviderReference[string-length(string(./@tf:id)) > 0]");
        if (allFullProviderEntries != null && !allFullProviderEntries.isEmpty()) {
            for (Node providerXml : allFullProviderEntries) {
                Entity entity = readEntityReference(providerXml);
                String id = ((String) providerXml.selectObject("string(./@tf:id)")).trim();
                providerReferenceMap.put(id, entity);
            }
        }

        List<Node> refsList = element.selectNodes("./tf:References/*");
        if (refsList != null && !refsList.isEmpty()) {
            for (Node ref : refsList) {
                if (ref.getName().contains("TrustInteroperabilityProfileReference")) {
                    TrustInteroperabilityProfileReferenceImpl tipRefImpl = new TrustInteroperabilityProfileReferenceImpl();
                    tipRefImpl.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUST_INTEROPERABILITY_PROFILE_REFERENCE);
                    tipRefImpl.setId(getString(ref, "./@tf:id", true));
                    tipRefImpl.setIdentifier(getUri(ref, "./tf:Identifier", true));
                    if (exists(ref, "./tf:Number")) {
                        tipRefImpl.setNumber(getNumber(ref, "./tf:Number", false).intValue());
                    }
                    tipRefImpl.setName(getString(ref, "./tf:Name", false));
                    tipRefImpl.setVersion(getString(ref, "./tf:Version", false));
                    tipRefImpl.setDescription(getString(ref, "./tf:Description", false));
                    trustInteroperabilityProfile.addReference(tipRefImpl);
                } else if (ref.getName().contains("TrustmarkDefinitionRequirement")) {
                    trustInteroperabilityProfile.addReference(readTDRequirement(ref, providerReferenceMap));
                }
            }
        }


        return trustInteroperabilityProfile;
    }//end fromDom4()

    private static TrustmarkDefinitionRequirement readTDRequirement(Node ref, HashMap<String, Entity> providerReferenceMap) throws ParseException {
        TrustmarkDefinitionRequirementImpl tdReq = new TrustmarkDefinitionRequirementImpl();
        tdReq.setTypeName(TrustmarkFrameworkIdentifiedObject.TYPE_NAME_TRUSTMARK_DEFINITION_REQUIREMENT);
        tdReq.setId(getString(ref, "./@tf:id", true));
        tdReq.setIdentifier(getUri(ref, "./tf:TrustmarkDefinitionReference/tf:Identifier", true));
        tdReq.setName(getString(ref, "./tf:TrustmarkDefinitionReference/tf:Name", false));
        if (exists(ref, "./tf:TrustmarkDefinitionReference/tf:Number")) {
            tdReq.setNumber(getNumber(ref, "./tf:TrustmarkDefinitionReference/tf:Number", false).intValue());
        }
        tdReq.setVersion(getString(ref, "./tf:TrustmarkDefinitionReference/tf:Version", false));
        tdReq.setDescription(getString(ref, "./tf:TrustmarkDefinitionReference/tf:Description", false));
        List<Node> providerRefs = ref.selectNodes("./tf:ProviderReference");
        if (providerRefs != null && !providerRefs.isEmpty()) {
            for (Node providerXml : providerRefs) {
                Entity provider = null;
                String id = (String) providerXml.selectObject("string(./@tf:id)");
                if (StringUtils.isBlank(id))
                    id = (String) providerXml.selectObject("string(./@tf:ref)");
                if (StringUtils.isBlank(id)) {
                    log.debug("For TD Requirement[" + tdReq.getIdentifier() + "], a ProviderReference is missing either id or ref value.  Assuming this is an anonymous inner one.");
                    provider = readEntityReference(providerXml);
                } else {
                    provider = providerReferenceMap.get(id);
                    if (provider == null) {
                        throw new ParseException("For TD Requirement[" + tdReq.getIdentifier() + "], Could not find ProviderReference with id '" + id + "'");
                    }
                }
                tdReq.addProviderReference(provider);
            }
        }
        return tdReq;
    }


}//end TrustmarkXmlDeserializer
