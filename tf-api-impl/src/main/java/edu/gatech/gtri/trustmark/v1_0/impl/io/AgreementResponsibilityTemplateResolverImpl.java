package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentResolver;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.AgreementResponsibilityTemplateResolver;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import org.apache.log4j.Logger;
import org.dom4j.Element;

/**
 * Created by Nicholas on 2017-06-06.
 */
public class AgreementResponsibilityTemplateResolverImpl
    extends AbstractDocumentResolver<AgreementResponsibilityTemplate>
    implements AgreementResponsibilityTemplateResolver
{
    @Override
    public Class<AgreementResponsibilityTemplate> getSupportedType() { return AgreementResponsibilityTemplate.class; }
    
    @Override
    public Element getValidatedXml(String xmlString) throws ParseException {
        // TODO: add AgreementResponsibilityTemplate to the schema, then remove this overridden method (the super method will call validateXml)
        //XmlHelper.validateXml(xmlString);
        Logger.getLogger(this.getClass()).warn("AgreementResponsibilityTemplate is not yet added to the XML Schema!!");
        return XmlHelper.readWithDom4j(xmlString);
    }
}
