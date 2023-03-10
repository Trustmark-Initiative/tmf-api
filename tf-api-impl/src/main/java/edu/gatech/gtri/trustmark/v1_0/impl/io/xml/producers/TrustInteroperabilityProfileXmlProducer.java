package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.HashMap;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

/**
 * Created by brad on 1/7/16.
 */
public class TrustInteroperabilityProfileXmlProducer implements XmlProducer<TrustInteroperabilityProfile> {

    private static final Logger log = LoggerFactory.getLogger(TrustInteroperabilityProfileXmlProducer.class);

    @Override
    public Class<TrustInteroperabilityProfile> getSupportedType() {
        return TrustInteroperabilityProfile.class;
    }

    @Override
    public void serialize(TrustInteroperabilityProfile tip, XMLStreamWriter xmlWriter) throws XMLStreamException {
        log.debug("Writing XML for TIP[" + tip.getIdentifier() + "]...");

        if (tip.getId() != null) {
            xmlWriter.writeAttribute(NAMESPACE_URI, ATTRIBUTE_KEY_JSON_ID, tip.getId());
        }

        XmlProducerUtility.writeMainIdentifyingInformation(xmlWriter, tip);

        xmlWriter.writeStartElement(NAMESPACE_URI, "Primary");
        if (tip.isPrimary()) {
            xmlWriter.writeCharacters("true");
        } else {
            xmlWriter.writeCharacters("false");
        }
        xmlWriter.writeEndElement(); //end "Primary"

/*
        xmlWriter.writeStartElement(NAMESPACE_URI, "Moniker");
        if(tip.getMoniker() != null)  {
            xmlWriter.writeCharacters(tip.getMoniker());
        }
        xmlWriter.writeEndElement(); //end "Moniker"
*/

        XmlProducerUtility.writeLegalEase(xmlWriter, tip);

        xmlWriter.writeStartElement(NAMESPACE_URI, "Issuer");
        XmlProducerUtility.writeXml(tip.getIssuer(), xmlWriter);
        xmlWriter.writeEndElement(); // end "Issuer"

        XmlProducerUtility.writeSupersessionInfo(xmlWriter, tip);
        XmlProducerUtility.writeDeprecated(xmlWriter, tip);
        XmlProducerUtility.writeSatisfies(xmlWriter, tip);
        XmlProducerUtility.writeKnownConflicts(xmlWriter, tip);
        XmlProducerUtility.writeKeywords(xmlWriter, tip);

        xmlWriter.writeStartElement(NAMESPACE_URI, "References");
        HashMap<String, String> encounteredEntities = new HashMap<>();
        for (AbstractTIPReference abstractTIPReference : tip.getReferences()) {
            if (abstractTIPReference.isTrustInteroperabilityProfileReference()) {
                TrustInteroperabilityProfileReference tipRef = (TrustInteroperabilityProfileReference) abstractTIPReference;
                xmlWriter.writeStartElement(NAMESPACE_URI, "TrustInteroperabilityProfileReference");
                xmlWriter.writeAttribute("tf", NAMESPACE_URI, ATTRIBUTE_KEY_JSON_ID, tipRef.getId());
                XmlProducerUtility.writeXml(tipRef, xmlWriter); // Should match up with 'TrustmarkFrameworkIdentifiedObject'
                xmlWriter.writeEndElement();
            } else if (abstractTIPReference.isTrustmarkDefinitionRequirement()) {
                TrustmarkDefinitionRequirement tdReqRef = (TrustmarkDefinitionRequirement) abstractTIPReference;
                tdRequirementHelper(encounteredEntities, tdReqRef, xmlWriter);
            } else {
                log.error("Unknown kind of TIP reference: " + abstractTIPReference.getClass().getName());
                throw new UnsupportedOperationException("Unknown TIP Reference encountered: " + abstractTIPReference.getClass().getName());
            }
        }
        xmlWriter.writeEndElement(); // end "References"

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustExpression");
        xmlWriter.writeCData(tip.getTrustExpression());
        xmlWriter.writeEndElement();

        if(tip.getRequiredProviders() != null && tip.getRequiredProviders().size()>0) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "RequiredProviders");

            for (Entity trustmarkProvider : tip.getRequiredProviders()) {
                String theProviderId = "provider" + trustmarkProvider.getIdentifier().hashCode();
                xmlWriter.writeStartElement(NAMESPACE_URI, "ProviderReference");
                xmlWriter.writeAttribute("tf", NAMESPACE_URI, ATTRIBUTE_KEY_JSON_ID, theProviderId);
                XmlProducerUtility.writeXml(trustmarkProvider, xmlWriter);
                xmlWriter.writeEndElement(); // End "ProviderReference"

            }
            xmlWriter.writeEndElement(); // end "RequiredProviders"
        }

        if (tip.getSources() != null && tip.getSources().size() > 0) {
            log.debug("Writing " + tip.getSources().size() + " sources...");
            xmlWriter.writeStartElement(NAMESPACE_URI, "Sources");
            for (Source source : tip.getSources()) {
                log.debug("Writing source[" + source.getIdentifier() + "]...");
                xmlWriter.writeStartElement(NAMESPACE_URI, "Source");
                XmlProducerUtility.writeXml(source, xmlWriter);
                xmlWriter.writeEndElement();
            }
            log.debug("Writing end sources element...");
            xmlWriter.writeEndElement();
        }

        if (tip.getTerms() != null && tip.getTerms().size() > 0) {
            log.debug("Writing " + tip.getTerms().size() + " terms...");
            xmlWriter.writeStartElement(NAMESPACE_URI, "Terms");
            for (Term term : tip.getTermsSorted()) {
                log.debug("Writing term[" + term.getName() + "]...");
                xmlWriter.writeStartElement(NAMESPACE_URI, "Term");
                XmlProducerUtility.writeXml(term, xmlWriter);
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
        }


    }//end serialize()


    private void tdRequirementHelper(HashMap<String, String> encounteredEntities, TrustmarkDefinitionRequirement tdReq, XMLStreamWriter xmlWriter) throws XMLStreamException {

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkDefinitionRequirement");
        xmlWriter.writeAttribute("tf", NAMESPACE_URI, ATTRIBUTE_KEY_JSON_ID, tdReq.getId());

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkDefinitionReference");
        XmlProducerUtility.writeXml(tdReq, xmlWriter); // Should match up with 'TrustmarkFrameworkIdentifiedObject'
        xmlWriter.writeEndElement(); //end "TrustmarkDefinitionReference"

        xmlWriter.writeEndElement(); //end "TrustmarkDefinitionRequirement"

    }//end marshalHelper()


}//end TrustInteroperabilityProfileXmlProducer
