package edu.gatech.gtri.trustmark.v1_0.impl.io;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentResolver;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResolver;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import org.apache.log4j.Logger;
import org.dom4j.Element;

/**
 * Created by Nicholas on 02/03/2017.
 */
public class AgreementResolverImpl extends AbstractDocumentResolver<Agreement> implements AgreementResolver {
    @Override
    public Class<Agreement> getSupportedType() { return Agreement.class; }
    
    @Override
    public Element getValidatedXml(String xmlString) throws ParseException {
        // TODO: add Agreement to the schema, then remove this overridden method (the super method will call validateXml)
        //XmlHelper.validateXml(xmlString);
        Logger.getLogger(this.getClass()).warn("Agreement is not yet added to the XML Schema!!");
        return XmlHelper.readWithDom4j(xmlString);
    }
}
