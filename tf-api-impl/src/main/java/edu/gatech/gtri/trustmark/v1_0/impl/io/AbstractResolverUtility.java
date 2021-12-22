package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.dom4j.Element;
import org.json.JSONObject;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.assertSupported;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.assertSupportedVersion;

public final class AbstractResolverUtility {

    private static final Logger log = LoggerFactory.getLogger(AbstractResolverUtility.class);

    private AbstractResolverUtility() {
    }

    /**
     * Simple function to check for XML data.  Merely looks for a '<' at the first non-whitespace character location.
     */
    public static boolean isXml(String potentialXml) {
        for (int i = 0; i < potentialXml.length(); i++) {
            char nextChar = potentialXml.charAt(i);
            if (Character.isWhitespace(nextChar)) {
                continue;
            } else {
                return nextChar == '<';
            }
        }
        return false;
    }

    /**
     * A relatively simple JSON resolution function.  Simply checks the first letter as valid JSON or not.
     */
    public static boolean isJson(String potentialJson) {
        for (int i = 0; i < potentialJson.length(); i++) {
            char nextChar = potentialJson.charAt(i);
            if (Character.isWhitespace(nextChar)) {
                continue;
            } else {
                return nextChar == '{' || nextChar == '[';
            }
        }
        return false;
    }

    public static Element getValidatedXml(String xmlString) throws ParseException {
        XmlHelper.validateXml(xmlString);
        return XmlHelper.readWithDom4j(xmlString);
    }

    public static Element getUnvalidatedXml(String xmlString) throws ParseException {
        log.warn("Entity has not been added to the schema.");
        return XmlHelper.readWithDom4j(xmlString);
    }

    public static JSONObject getValidatedJson(String jsonString) throws ParseException {
        JSONObject result = new JSONObject(jsonString);
        assertSupported(result);
        return result;
    }

    public static JSONObject getValidatedJsonIsSupportedVersion(String jsonString) throws ParseException {
        JSONObject result = new JSONObject(jsonString);
        assertSupportedVersion(result);
        return result;
    }
}
