package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlUtils;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.UUID;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class TrustmarkXmlProducer extends AbstractXmlProducer implements XmlProducer {


    @Override
    public Class getSupportedType() {
        return Trustmark.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof Trustmark) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Trustmark trustmark = (Trustmark) instance;

        String uuidIdAttribute = "TM_"+System.currentTimeMillis()+"_"+ UUID.randomUUID().toString().toUpperCase().replace("-", "");
        xmlWriter.writeAttribute(NAMESPACE_URI, "id", uuidIdAttribute);

        xmlWriter.writeStartElement(NAMESPACE_URI, "Identifier");
        xmlWriter.writeCharacters(trustmark.getIdentifier().toString());
        xmlWriter.writeEndElement(); //end "Identifier"

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkDefinitionReference");
        writeXml(trustmark.getTrustmarkDefinitionReference(), xmlWriter);
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
        writeXml(trustmark.getProvider(), xmlWriter);
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Recipient");
        writeXml(trustmark.getRecipient(), xmlWriter);
        xmlWriter.writeEndElement();

        if( trustmark.hasExceptions() ){
            for( String s : trustmark.getExceptionInfo() ) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "ExceptionInfo");
                xmlWriter.writeCharacters(s);
                xmlWriter.writeEndElement();
            }
        }

        if( trustmark.getParameterBindings() != null &&  !trustmark.getParameterBindings().isEmpty() ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "ParameterBindings");
            for(TrustmarkParameterBinding binding : trustmark.getParameterBindings() ){
                xmlWriter.writeStartElement(NAMESPACE_URI, "ParameterBinding");
                xmlWriter.writeAttribute(NAMESPACE_URI, "identifier", binding.getIdentifier());
                xmlWriter.writeAttribute(NAMESPACE_URI, "kind", binding.getParameterKind().toString());
                xmlWriter.writeCharacters(binding.getStringValue());
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement(); //end tf:ParameterBindings
        }

        if( trustmark.getDefinitionExtension() != null ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "DefinitionExtension");
            writeXml(trustmark.getDefinitionExtension(), xmlWriter);
            xmlWriter.writeEndElement();
        }

        if( trustmark.getProviderExtension() != null ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "ProviderExtension");
            writeXml(trustmark.getProviderExtension(), xmlWriter);
            xmlWriter.writeEndElement();
        }


    }//end serialize()



}
