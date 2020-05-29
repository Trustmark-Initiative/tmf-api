package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlUtils;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class TrustInteroperabilityProfileXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(TrustInteroperabilityProfileXmlProducer.class);

    @Override
    public Class getSupportedType() {
        return TrustInteroperabilityProfile.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof TrustInteroperabilityProfile) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        TrustInteroperabilityProfile tip = (TrustInteroperabilityProfile) instance;
        log.debug("Writing XML for TD["+tip.getIdentifier()+"]...");

        String uuidIdAttribute = "TIP_"+System.currentTimeMillis()+"_"+ UUID.randomUUID().toString().toUpperCase().replace("-", "");
        xmlWriter.writeAttribute(NAMESPACE_URI, "id", uuidIdAttribute);

        writeMainIdentifyingInformation(xmlWriter, tip);

        xmlWriter.writeStartElement(NAMESPACE_URI, "Primary");
        if(tip.isPrimary())  {
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

        writeLegalEase(xmlWriter, tip);

        xmlWriter.writeStartElement(NAMESPACE_URI, "Issuer");
        writeXml(tip.getIssuer(), xmlWriter);
        xmlWriter.writeEndElement(); // end "Issuer"

        writeSupersessionInfo(xmlWriter, tip);
        writeDeprecated(xmlWriter, tip);
        writeSatisfies(xmlWriter, tip);
        writeKnownConflicts(xmlWriter, tip);
        writeKeywords(xmlWriter, tip);

        xmlWriter.writeStartElement(NAMESPACE_URI, "References");
        HashMap<String, String> encounteredEntities = new HashMap<>();
        for(AbstractTIPReference abstractTIPReference : tip.getReferences() ){
            if( abstractTIPReference.isTrustInteroperabilityProfileReference() ){
                TrustInteroperabilityProfileReference tipRef = (TrustInteroperabilityProfileReference) abstractTIPReference;
                xmlWriter.writeStartElement(NAMESPACE_URI, "TrustInteroperabilityProfileReference");
                xmlWriter.writeAttribute("tf", NAMESPACE_URI, "id", tipRef.getId());
                writeXml(tipRef, xmlWriter); // Should match up with 'TrustmarkFrameworkIdentifiedObject'
                xmlWriter.writeEndElement();
            }else if( abstractTIPReference.isTrustmarkDefinitionRequirement() ){
                TrustmarkDefinitionRequirement tdReqRef = (TrustmarkDefinitionRequirement) abstractTIPReference;
                tdRequirementHelper(encounteredEntities, tdReqRef, xmlWriter);
            }else{
                log.error("Unknown kind of TIP reference: "+abstractTIPReference.getClass().getName());
                throw new UnsupportedOperationException("Unknown TIP Reference encountered: "+abstractTIPReference.getClass().getName());
            }
        }
        xmlWriter.writeEndElement(); // end "References"

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustExpression");
        xmlWriter.writeCData(tip.getTrustExpression());
        xmlWriter.writeEndElement();



        if( tip.getSources() != null && tip.getSources().size() > 0 ){
          log.debug("Writing "+tip.getSources().size()+" sources...");
          xmlWriter.writeStartElement(NAMESPACE_URI, "Sources");
          for( Source source : tip.getSources() ){
            log.debug("Writing source["+source.getIdentifier()+"]...");
            xmlWriter.writeStartElement(NAMESPACE_URI, "Source");
            writeXml(source, xmlWriter);
            xmlWriter.writeEndElement();
          }
          log.debug("Writing end sources element...");
          xmlWriter.writeEndElement();
        }

        if( tip.getTerms() != null && tip.getTerms().size() > 0 ) {
            log.debug("Writing " + tip.getTerms().size() + " terms...");
            xmlWriter.writeStartElement(NAMESPACE_URI, "Terms");
            for (Term term : tip.getTermsSorted()) {
                log.debug("Writing term[" + term.getName() + "]...");
                xmlWriter.writeStartElement(NAMESPACE_URI, "Term");
                writeXml(term, xmlWriter);
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
        }


    }//end serialize()


    private void tdRequirementHelper(HashMap<String, String> encounteredEntities, TrustmarkDefinitionRequirement tdReq, XMLStreamWriter xmlWriter ) throws XMLStreamException {

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkDefinitionRequirement");
        xmlWriter.writeAttribute("tf", NAMESPACE_URI, "id", tdReq.getId());

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkDefinitionReference");
        writeXml(tdReq, xmlWriter); // Should match up with 'TrustmarkFrameworkIdentifiedObject'
        xmlWriter.writeEndElement(); //end "TrustmarkDefinitionReference"

        List<Entity> providers = tdReq.getProviderReferences();
        if( providers != null && providers.size() > 0 ){
            for( Entity provider : providers ){
                if( encounteredEntities.containsKey(provider.getIdentifier().toString()) ){

                    xmlWriter.writeStartElement(NAMESPACE_URI, "ProviderReference");
                    String theProviderId = encounteredEntities.get(provider.getIdentifier().toString());
                    xmlWriter.writeAttribute("tf", NAMESPACE_URI, "ref", theProviderId);
                    xmlWriter.writeAttribute("xsi", XSI_NS_URI, "nil", "true");
                    xmlWriter.writeEndElement();

                }else{
                    String theProviderId = "provider"+provider.getIdentifier().toString().hashCode();
                    encounteredEntities.put(provider.getIdentifier().toString(), theProviderId);
                    xmlWriter.writeStartElement(NAMESPACE_URI, "ProviderReference");
                    xmlWriter.writeAttribute("tf", NAMESPACE_URI, "id", theProviderId);
                    writeXml(provider, xmlWriter);
                    xmlWriter.writeEndElement(); // End "ProviderReference"
                }
            }
        }

        xmlWriter.writeEndElement(); //end "TrustmarkDefinitionRequirement"

    }//end marshalHelper()



}//end TrustInteroperabilityProfileXmlProducer
