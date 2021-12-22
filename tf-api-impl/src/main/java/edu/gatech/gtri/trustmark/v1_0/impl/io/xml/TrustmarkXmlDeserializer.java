package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.List;

/**
 * Deserializes XML strings into Trustmark objects.
 * <br/><br/>
 * Created by brad on 12/9/15.
 */
public class TrustmarkXmlDeserializer extends AbstractDeserializer {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkXmlDeserializer.class);

    public static Trustmark deserialize( String xml ) throws ParseException {
        log.debug("Request to deserialize Trustmark XML...");

        XmlHelper.validateXml(xml);

        Element root = XmlHelper.readWithDom4j(xml);
        if( !root.getQName().getName().equals("Trustmark") ){
            throw new ParseException("Root element of a Trustmark must be tf:Trustmark, you have: "+root.getName());
        }

        Trustmark tm = fromDom4j(root, xml);
        return tm;
    }//end deserialize()


    public static Trustmark fromDom4j( Element trustmarkXml, String originalSource ) throws ParseException {
        log.debug("Constructing new Trustmark from element["+trustmarkXml.getName()+"]...");
        TrustmarkImpl trustmarkImpl = new TrustmarkImpl();

        trustmarkImpl.setOriginalSource(originalSource);
        trustmarkImpl.setOriginalSourceType("text/xml");
        trustmarkImpl.setId(getString(trustmarkXml, "./@tf:id", false));

        trustmarkImpl.setIdentifier(getUri(trustmarkXml, "string(./tf:Identifier)", true));
        trustmarkImpl.setTrustmarkDefinitionReference(
                parseTrustmarkFrameworkIdentifiedObjectImpl(
                        "TrustmarkDefinitionReference",
                        (Element) trustmarkXml.selectSingleNode("./tf:TrustmarkDefinitionReference")));

        trustmarkImpl.setIssueDateTime(getDate(trustmarkXml, "./tf:IssueDateTime", true));
        trustmarkImpl.setExpirationDateTime(getDate(trustmarkXml, "./tf:ExpirationDateTime", true));

        trustmarkImpl.setPolicyURL(getUrl(trustmarkXml, "./tf:PolicyURL", true));
        trustmarkImpl.setRelyingPartyAgreementURL(getUrl(trustmarkXml, "./tf:RelyingPartyAgreementURL", true));
        trustmarkImpl.setStatusURL(getUrl(trustmarkXml, "./tf:StatusURL", true));

        trustmarkImpl.setProvider(readEntity( (Element) trustmarkXml.selectSingleNode("./tf:Provider")));
        trustmarkImpl.setRecipient(readEntity( (Element) trustmarkXml.selectSingleNode("./tf:Recipient")));

        List exceptionInfoList = trustmarkXml.selectNodes("./tf:ExceptionInfo");
        if( exceptionInfoList != null && !exceptionInfoList.isEmpty() ){
            for( int i = 0; i < exceptionInfoList.size(); i++ ){
                Element exceptionInfoElement = (Element) exceptionInfoList.get(i);
                String exceptionInfo = (String) exceptionInfoElement.selectObject("string(.)");
                if( exceptionInfo != null && exceptionInfo.trim().length() > 0 ){
                    exceptionInfo = exceptionInfo.trim();
                    trustmarkImpl.addExceptionInfo(exceptionInfo);
                }
            }
        }

        List<Node> paramBindingsList = trustmarkXml.selectNodes("./tf:ParameterBindings/tf:ParameterBinding");
        if( paramBindingsList != null && !paramBindingsList.isEmpty() ){
            for( Node paramBindingElement : paramBindingsList ){
                TrustmarkParameterBindingImpl bindingImpl = new TrustmarkParameterBindingImpl();
                bindingImpl.setIdentifier(getString(paramBindingElement, "./@*[local-name()='identifier']", true));
                bindingImpl.setParameterKind(ParameterKind.fromString(getString(paramBindingElement, "./@*[local-name()='kind']", true)));
                bindingImpl.setValue(getString(paramBindingElement, ".", true));
                log.debug("Adding parameter binding: "+bindingImpl.getIdentifier());
                trustmarkImpl.addParameterBinding(bindingImpl);
            }
        }
        log.debug("There are "+trustmarkImpl.getParameterBindings().size()+" parameter bindings");

        trustmarkImpl.setDefinitionExtension(readExtension( (Element) trustmarkXml.selectSingleNode("./tf:DefinitionExtension")));
        trustmarkImpl.setProviderExtension(readExtension( (Element) trustmarkXml.selectSingleNode("./tf:ProviderExtension")));

        return trustmarkImpl;
    }//end fromDom4()







}//end TrustmarkXmlDeserializer
