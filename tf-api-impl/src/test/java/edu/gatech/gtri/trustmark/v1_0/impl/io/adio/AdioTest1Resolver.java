package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.AbstractDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import org.dom4j.Element;
import org.json.JSONObject;

/**
 * Created by Nicholas on 01/25/2017.
 */
public class AdioTest1Resolver extends AbstractDocumentResolver<AdioTest1> {
    @Override
    public Class<AdioTest1> getSupportedType() {
        return AdioTest1.class;
    }
    
    @Override
    public Element getValidatedXml(String xmlString) throws ParseException {
        // not validating against TF schema
        return XmlHelper.readWithDom4j(xmlString);
    }
    
    @Override
    public JSONObject getValidatedJson(String jsonString) throws ParseException {
        JSONObject result = new JSONObject(jsonString);
        AbstractDeserializer.isSupportedVersion(result); // only checking for version, not supported type
        return result;
    }
}
