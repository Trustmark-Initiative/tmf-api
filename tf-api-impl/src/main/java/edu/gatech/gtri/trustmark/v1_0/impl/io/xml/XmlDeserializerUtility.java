package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.ContactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ExtensionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.SourceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TermImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ContactKindCode;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.dom4j.Element;
import org.dom4j.Node;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Utilities for XML deserializers.
 *
 * @author GTRI Trustmark Team
 */
public final class XmlDeserializerUtility {
    private XmlDeserializerUtility() {
    }

    public static Source readSource(Node sourceXml) throws ParseException {
        SourceImpl source = new SourceImpl();
        source.setIdentifier(getString(sourceXml, "./tf:Identifier", true));
        source.setReference(getString(sourceXml, "./tf:Reference", true));
        return source;
    }

    public static Term readTerm(Node termXml) throws ParseException {
        TermImpl term = new TermImpl();
        term.setName(getString(termXml, "./tf:Name", true));
        List<String> abbreviationsList = getListStrings(termXml, "./tf:Abbreviation");
        if (abbreviationsList != null && !abbreviationsList.isEmpty()) {
            for (String abbr : abbreviationsList)
                term.addAbbreviation(abbr);
        }
        term.setDefinition(getString(termXml, "./tf:Definition", true));

        return term;
    }//end readTerm()


    public static TrustmarkFrameworkIdentifiedObjectImpl parseTrustmarkFrameworkIdentifiedObjectImpl(String type, Element dom4j) throws ParseException {
        if (dom4j == null)
            return null;
        TrustmarkFrameworkIdentifiedObjectImpl obj = new TrustmarkFrameworkIdentifiedObjectImpl();
        obj.setTypeName(type);
        obj.setIdentifier(getUri(dom4j, "./tf:Identifier", true));
        if (exists(dom4j, "./tf:Number")) {
            obj.setNumber(getNumber(dom4j, "./tf:Number", false).intValue());
        }
        obj.setName(getString(dom4j, "./tf:Name", false));
        obj.setVersion(getString(dom4j, "./tf:Version", false));
        obj.setDescription(getString(dom4j, "./tf:Description", false));
        return obj;
    }

    public static EntityImpl readEntity(Element entityXml) throws ParseException {
        if (entityXml == null)
            return null;
        EntityImpl entity = new EntityImpl();
        entity.setIdentifier(getUri(entityXml, "./tf:Identifier", true));
        entity.setName(getString(entityXml, "./tf:Name", true));
        List<Node> contacts = entityXml.selectNodes("./tf:Contact");
        for (Node contact : contacts) {
            entity.addContact(readContact((Element) contact));
        }
        return entity;
    }

    public static EntityImpl readEntityReference(Node entityXml) throws ParseException {
        if (entityXml == null)
            return null;
        EntityImpl entity = new EntityImpl();
        entity.setIdentifier(getUri(entityXml, "./tf:Identifier", true));
        entity.setName(getString(entityXml, "./tf:Name", false));
        List<Node> contacts = entityXml.selectNodes("./tf:Contact");
        if (contacts != null && contacts.size() > 0) {
            for (Node contact : contacts) {
                entity.addContact(readContact((Element) contact));
            }
        }
        return entity;
    }

    public static ContactImpl readContact(Element contactXml) throws ParseException {
        if (contactXml == null)
            return null;
        ContactImpl contact = new ContactImpl();

        String kindStr = getString(contactXml, "./tf:Kind", true);
        contact.setKind(ContactKindCode.fromString(kindStr));

        contact.setResponder(getString(contactXml, "./tf:Responder"));
        contact.setNotes(getString(contactXml, "./tf:Notes"));

        contact.setEmails(getListStrings(contactXml, "./tf:Email"));
        contact.setTelephones(getListStrings(contactXml, "./tf:Telephone"));
        contact.setPhysicalAddresses(getListStrings(contactXml, "./tf:PhysicalAddress"));
        contact.setMailingAddresses(getListStrings(contactXml, "./tf:MailingAddress"));
        contact.setWebsiteURLs(getListUrls(contactXml, "./tf:WebsiteURL"));

        return contact;
    }


    public static ExtensionImpl readExtension(Element extensionXml) throws ParseException {
        if (extensionXml == null)
            return null;
        ExtensionImpl extension = new ExtensionImpl();
        List<Node> children = extensionXml.selectNodes("./*");
        if (children != null && !children.isEmpty()) {
            for (Node child : children) {
                extension.addData(child);
            }
        }
        return extension;
    }//end readExtension()

    //==================================================================================================================
    //  Generic Helper Functions to Get Dom4j Data
    //==================================================================================================================
    public static List<URL> getListUrls(Node node, String xpath) throws ParseException {
        List<URL> things = new ArrayList<>();
        List<Node> thingNodes = node.selectNodes(xpath);
        if (thingNodes != null && !thingNodes.isEmpty()) {
            for (Node thingNode : thingNodes) {
                things.add(getUrl(thingNode, ".", true));
            }
        }
        return things;
    }

    public static List<String> getListStrings(Node node, String xpath) throws ParseException {
        List<String> things = new ArrayList<>();
        List<Node> thingNodes = node.selectNodes(xpath);
        if (thingNodes != null && !thingNodes.isEmpty()) {
            for (Node thingNode : thingNodes) {
                things.add(getString(thingNode, ".", true));
            }
        }
        return things;
    }


    public static Date getDate(Node dom4j, String xpath, Boolean required) throws ParseException {
        String dateString = getString(dom4j, xpath, required);
        if (dateString != null) {
            Calendar calendar = DatatypeConverter.parseDateTime(dateString);
            return calendar.getTime();
        }
        return null;
    }

    public static Date getDate(Node dom4j, String xpath) throws ParseException {
        return getDate(dom4j, xpath, false);
    }

    public static Number getNumber(Node dom4j, String xpath, Boolean required) throws ParseException {
        String numString = getString(dom4j, xpath, required);
        if (numString != null && !numString.equals("")) {
            try {
                return Double.parseDouble(numString);
            } catch (Exception e) {
                throw new ParseException(numString + " is not a valid number: " + e.getMessage(), e);
            }
        }
        return null;
    }

    public static Number getNumber(Node dom4j, String xpath) throws ParseException {
        return getNumber(dom4j, xpath, false);
    }

    public static String getString(Node dom4j, String xpath, Boolean required) throws ParseException {
        Object dom4jSelection = dom4j.selectObject("string(" + xpath + ")");
        if (dom4jSelection != null) {
            return dom4jSelection.toString().trim();
        } else if (required) {
            throw new ParseException("Expecting to find a value for Xpath[" + xpath + "], but was null.");
        }
        return null;
    }

    public static String getString(Node dom4j, String xpath) throws ParseException {
        return getString(dom4j, xpath, false);
    }

    public static URI getUri(Node dom4j, String xpath, Boolean required) throws ParseException {
        Object dom4jSelection = dom4j.selectObject("string(" + xpath + ")");
        if (dom4jSelection != null) {
            String uriStr = dom4jSelection.toString().trim();
            try {
                return new URI(uriStr);
            } catch (Throwable t) {
                throw new ParseException("Unable to parse xpath[" + xpath + "] into URI, bad format", t);
            }
        } else if (required) {
            throw new ParseException("Expecting to find XPath[" + xpath + "], but could not.");
        }
        return null;
    }

    public static URI getUri(Node dom4j, String xpath) throws ParseException {
        return getUri(dom4j, xpath, false);
    }

    public static URL getUrl(Node dom4j, String xpath) throws ParseException {
        return getUrl(dom4j, xpath, false);
    }

    public static URL getUrl(Node dom4j, String xpath, Boolean required) throws ParseException {
        Object dom4jSelection = dom4j.selectObject("string(" + xpath + ")");
        if (dom4jSelection != null) {
            String uriStr = dom4jSelection.toString().trim();
            try {
                return new URL(uriStr);
            } catch (Throwable t) {
                throw new ParseException("Unable to parse xpath[" + xpath + "] into URL, bad format", t);
            }
        } else if (required) {
            throw new ParseException("Expecting to find XPath[" + xpath + "], but could not.");
        }
        return null;
    }

    public static TrustmarkFrameworkIdentifiedObject readTrustmarkFrameworkIdentifiedObject(Node tfiXml) throws ParseException {
        TrustmarkFrameworkIdentifiedObjectImpl identifiedObject = new TrustmarkFrameworkIdentifiedObjectImpl();
        try {
            identifiedObject.setIdentifier(new URI(getString(tfiXml, "tf:Identifier", true)));
        } catch (URISyntaxException urise) {
            throw new ParseException("Invalid Trustmark Definition URI: " + getString(tfiXml, "tf:Identifier", true), urise);
        }
        identifiedObject.setTypeName("TrustmarkDefinitionReference");
        identifiedObject.setName(getString(tfiXml, "Name", false));
        identifiedObject.setVersion(getString(tfiXml, "Version", false));
        if (exists(tfiXml, "Number")) {
            identifiedObject.setNumber(getNumber(tfiXml, "Number", false).intValue());
        }
        identifiedObject.setDescription(getString(tfiXml, "Description", false));
        return identifiedObject;
    }

    public static boolean exists(Node dom4j, String xpath) {
        Object dom4jSelection = dom4j.selectObject("string(" + xpath + ")");
        if (dom4jSelection != null && !dom4jSelection.equals("")) {
            return true;
        }
        return false;
    }
}
