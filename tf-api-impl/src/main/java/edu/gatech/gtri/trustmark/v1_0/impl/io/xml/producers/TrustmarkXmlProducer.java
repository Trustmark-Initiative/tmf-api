package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlUtils;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;
import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;
import static java.util.Objects.requireNonNull;

/**
 * Created by brad on 1/7/16.
 */
public class TrustmarkXmlProducer implements XmlProducer<Trustmark> {

    @Override
    public Class<Trustmark> getSupportedType() {
        return Trustmark.class;
    }

    @Override
    public void serialize(Trustmark trustmark, XMLStreamWriter xmlWriter) throws XMLStreamException {

        requireNonNull(trustmark);
        requireNonNull(trustmark.getId());

        xmlWriter.writeAttribute(NAMESPACE_URI, ATTRIBUTE_KEY_JSON_ID, trustmark.getId());

        xmlWriter.writeStartElement(NAMESPACE_URI, "Identifier");
        xmlWriter.writeCharacters(trustmark.getIdentifier().toString());
        xmlWriter.writeEndElement(); //end "Identifier"

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkDefinitionReference");
        XmlProducerUtility.writeXml(trustmark.getTrustmarkDefinitionReference(), xmlWriter);
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "IssueDateTime");
        xmlWriter.writeCharacters(XmlUtils.toDateTimeString(trustmark.getIssueDateTime()));
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "ExpirationDateTime");
        xmlWriter.writeCharacters(XmlUtils.toDateTimeString(trustmark.getExpirationDateTime()));
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "PolicyURL");
        xmlWriter.writeCharacters(trustmark.getPolicyURL().toString());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "RelyingPartyAgreementURL");
        xmlWriter.writeCharacters(trustmark.getRelyingPartyAgreementURL().toString());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "StatusURL");
        xmlWriter.writeCharacters(trustmark.getStatusURL().toString());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Provider");
        XmlProducerUtility.writeXml(trustmark.getProvider(), xmlWriter);
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Recipient");
        XmlProducerUtility.writeXml(trustmark.getRecipient(), xmlWriter);
        xmlWriter.writeEndElement();

        if (trustmark.hasExceptions()) {
            for (String s : trustmark.getExceptionInfo()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "ExceptionInfo");
                xmlWriter.writeCharacters(s);
                xmlWriter.writeEndElement();
            }
        }

        if (trustmark.getParameterBindings() != null && !trustmark.getParameterBindings().isEmpty()) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "ParameterBindings");
            for (TrustmarkParameterBinding binding : trustmark.getParameterBindings()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "ParameterBinding");
                xmlWriter.writeAttribute(NAMESPACE_URI, "identifier", binding.getIdentifier());
                xmlWriter.writeAttribute(NAMESPACE_URI, "kind", binding.getParameterKind().toString());
                xmlWriter.writeCharacters(binding.getStringValue());
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement(); //end tf:ParameterBindings
        }

        if (trustmark.getDefinitionExtension() != null) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "DefinitionExtension");
            XmlProducerUtility.writeXml(trustmark.getDefinitionExtension(), xmlWriter);
            xmlWriter.writeEndElement();
        }

        if (trustmark.getProviderExtension() != null) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "ProviderExtension");
            XmlProducerUtility.writeXml(trustmark.getProviderExtension(), xmlWriter);
            xmlWriter.writeEndElement();
        }


    }//end serialize()


}
