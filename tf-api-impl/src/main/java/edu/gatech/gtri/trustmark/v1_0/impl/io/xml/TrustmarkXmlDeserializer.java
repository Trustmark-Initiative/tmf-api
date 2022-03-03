package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkParameterBindingImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getDate;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getString;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getUri;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getUrl;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.parseTrustmarkFrameworkIdentifiedObjectImpl;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readEntity;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readExtension;
import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.TEXT_XML;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Implementations deserialize XML and, optionally, a URI into a Trustmark.
 *
 * @author GTRI Trustmark Team
 */
public class TrustmarkXmlDeserializer implements XmlDeserializer<Trustmark> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkXmlDeserializer.class);

    public Trustmark deserialize(final String xml, final URI uri) throws ParseException {

        requireNonNull(xml);

        log.debug("Deserializing Trustmark XML . . .");

        XmlHelper.validateXml(xml);

        final Element element = XmlHelper.readWithDom4j(xml);
        final String elementName = "Trustmark";

        if (!element.getQName().getName().equals(elementName)) {
            throw new ParseException(format("The document element of a Trustmark must be '%s'; it was '%s'.", elementName, element.getName()));

        }
        return fromDom4j(element, xml, uri);
    }

    public static Trustmark fromDom4j(final Element element, final String originalSource, final URI uri) throws ParseException {

        final TrustmarkImpl trustmark = new TrustmarkImpl();

        trustmark.setOriginalSource(originalSource);
        trustmark.setOriginalSourceType(TEXT_XML.getMediaType());
        trustmark.setId(getString(element, "./@tf:id", false));

        trustmark.setIdentifier(getUri(element, "string(./tf:Identifier)", true));
        trustmark.setTrustmarkDefinitionReference(
                parseTrustmarkFrameworkIdentifiedObjectImpl(
                        "TrustmarkDefinitionReference",
                        (Element) element.selectSingleNode("./tf:TrustmarkDefinitionReference")));

        trustmark.setIssueDateTime(getDate(element, "./tf:IssueDateTime", true));
        trustmark.setExpirationDateTime(getDate(element, "./tf:ExpirationDateTime", true));

        trustmark.setPolicyURL(getUrl(element, "./tf:PolicyURL", true));
        trustmark.setRelyingPartyAgreementURL(getUrl(element, "./tf:RelyingPartyAgreementURL", true));
        trustmark.setStatusURL(getUrl(element, "./tf:StatusURL", true));

        trustmark.setProvider(readEntity((Element) element.selectSingleNode("./tf:Provider")));
        trustmark.setRecipient(readEntity((Element) element.selectSingleNode("./tf:Recipient")));

        List exceptionInfoList = element.selectNodes("./tf:ExceptionInfo");
        if (exceptionInfoList != null && !exceptionInfoList.isEmpty()) {
            for (int i = 0; i < exceptionInfoList.size(); i++) {
                Element exceptionInfoElement = (Element) exceptionInfoList.get(i);
                String exceptionInfo = (String) exceptionInfoElement.selectObject("string(.)");
                if (exceptionInfo != null && exceptionInfo.trim().length() > 0) {
                    exceptionInfo = exceptionInfo.trim();
                    trustmark.addExceptionInfo(exceptionInfo);
                }
            }
        }

        List<Node> paramBindingsList = element.selectNodes("./tf:ParameterBindings/tf:ParameterBinding");
        if (paramBindingsList != null && !paramBindingsList.isEmpty()) {
            for (Node paramBindingElement : paramBindingsList) {
                TrustmarkParameterBindingImpl bindingImpl = new TrustmarkParameterBindingImpl();
                bindingImpl.setIdentifier(getString(paramBindingElement, "./@*[local-name()='identifier']", true));
                bindingImpl.setParameterKind(ParameterKind.fromString(getString(paramBindingElement, "./@*[local-name()='kind']", true)));
                bindingImpl.setValue(getString(paramBindingElement, ".", true));
                log.debug("Adding parameter binding: " + bindingImpl.getIdentifier());
                trustmark.addParameterBinding(bindingImpl);
            }
        }
        log.debug("There are " + trustmark.getParameterBindings().size() + " parameter bindings");

        trustmark.setDefinitionExtension(readExtension((Element) element.selectSingleNode("./tf:DefinitionExtension")));
        trustmark.setProviderExtension(readExtension((Element) element.selectSingleNode("./tf:ProviderExtension")));

        return trustmark;
    }
}
