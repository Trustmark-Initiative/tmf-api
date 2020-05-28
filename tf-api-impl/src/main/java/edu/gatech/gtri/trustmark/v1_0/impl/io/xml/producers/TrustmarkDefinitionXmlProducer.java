package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlUtils;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.UUID;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;


/**
 * Created by brad on 1/7/16.
 */
public class TrustmarkDefinitionXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(TrustmarkDefinitionXmlProducer.class);


    @Override
    public Class getSupportedType() {
        return TrustmarkDefinition.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof TrustmarkDefinition) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        TrustmarkDefinition td = (TrustmarkDefinition) instance;

        log.debug("Writing XML for TD["+td.getMetadata().getIdentifier()+"]...");

        String uuidIdAttribute = "TD_"+System.currentTimeMillis()+"_"+ UUID.randomUUID().toString().toUpperCase().replace("-", "");
        xmlWriter.writeAttribute(NAMESPACE_URI, "id", uuidIdAttribute);

        writeMetadata(td, td.getMetadata(), xmlWriter);


        if( td.getTerms() != null && td.getTerms().size() > 0 ) {
            log.debug("Writing " + td.getTerms().size() + " terms...");
            xmlWriter.writeStartElement(NAMESPACE_URI, "Terms");
            for (Term term : td.getTermsSorted()) {
                log.debug("Writing term[" + term.getName() + "]...");
                xmlWriter.writeStartElement(NAMESPACE_URI, "Term");
                writeXml(term, xmlWriter);
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
        }


        if( td.getSources() != null && td.getSources().size() > 0 ){
          log.debug("Writing "+td.getSources().size()+" sources...");
          xmlWriter.writeStartElement(NAMESPACE_URI, "Sources");
          for( Source source : td.getSources() ){
            log.debug("Writing source["+source.getIdentifier()+"]...");
            xmlWriter.writeStartElement(NAMESPACE_URI, "Source");
            writeXml(source, xmlWriter);
            xmlWriter.writeEndElement();
          }
          log.debug("Writing end sources element...");
          xmlWriter.writeEndElement();
        }

        xmlWriter.writeStartElement(NAMESPACE_URI, "ConformanceCriteria");
        if( td.getConformanceCriteriaPreface() != null ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "Preface");
            xmlWriter.writeCData(td.getConformanceCriteriaPreface());
            xmlWriter.writeEndElement();
        }
        for( ConformanceCriterion crit : td.getConformanceCriteria() ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "ConformanceCriterion");
            writeXml(crit, xmlWriter);
            xmlWriter.writeEndElement();
        }
        xmlWriter.writeEndElement();


        xmlWriter.writeStartElement(NAMESPACE_URI, "AssessmentSteps");
        if( td.getAssessmentStepPreface() != null ){
            writeCDataString(xmlWriter, "Preface", td.getAssessmentStepPreface());
        }
        for( AssessmentStep step : td.getAssessmentSteps() ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "AssessmentStep");
            writeXml(step, xmlWriter);
            xmlWriter.writeEndElement();
        }
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "IssuanceCriteria");
        if( td.getIssuanceCriteria() != null ){
            xmlWriter.writeCData(td.getIssuanceCriteria());
        }else {
            xmlWriter.writeCData("yes(all)");
        }
        xmlWriter.writeEndElement(); //end "IssuanceCriteria"

        log.debug("Successfully wrote TD XML!");
    }//end serialize()

    private void writeMetadata(TrustmarkDefinition td, TrustmarkDefinition.Metadata metadata, XMLStreamWriter xmlWriter) throws XMLStreamException {
        xmlWriter.writeStartElement(NAMESPACE_URI, "Metadata");

        writeMainIdentifyingInformation(xmlWriter, metadata);

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkDefiningOrganization");
        writeXml(metadata.getTrustmarkDefiningOrganization(), xmlWriter);
        xmlWriter.writeEndElement(); //end "TrustmarkDefiningOrganization"

        if( metadata.getTargetStakeholderDescription() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "TargetStakeholderDescription");
            xmlWriter.writeCharacters(metadata.getTargetStakeholderDescription());
            xmlWriter.writeEndElement(); //end "TargetStakeholderDescription"
        }

        if( metadata.getTargetRecipientDescription() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "TargetRecipientDescription");
            xmlWriter.writeCharacters(metadata.getTargetRecipientDescription());
            xmlWriter.writeEndElement(); //end "TargetRecipientDescription"
        }

        if( metadata.getTargetRelyingPartyDescription() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "TargetRelyingPartyDescription");
            xmlWriter.writeCharacters(metadata.getTargetRelyingPartyDescription());
            xmlWriter.writeEndElement(); //end "TargetRelyingPartyDescription"
        }

        if( metadata.getTargetProviderDescription() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "TargetProviderDescription");
            xmlWriter.writeCharacters(metadata.getTargetProviderDescription());
            xmlWriter.writeEndElement(); //end "TargetProviderDescription"
        }

        if( metadata.getProviderEligibilityCriteria() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "ProviderEligibilityCriteria");
            xmlWriter.writeCharacters(metadata.getProviderEligibilityCriteria());
            xmlWriter.writeEndElement(); //end "ProviderEligibilityCriteria"
        }

        if( metadata.getAssessorQualificationsDescription() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "AssessorQualificationsDescription");
            xmlWriter.writeCharacters(metadata.getAssessorQualificationsDescription());
            xmlWriter.writeEndElement(); //end "AssessorQualificationsDescription"
        }

        if( metadata.getTrustmarkRevocationCriteria() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkRevocationCriteria");
            xmlWriter.writeCharacters(metadata.getTrustmarkRevocationCriteria());
            xmlWriter.writeEndElement(); //end "TrustmarkRevocationCriteria"
        }

        if( metadata.getExtensionDescription() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "ExtensionDescription");
            xmlWriter.writeCharacters(metadata.getExtensionDescription());
            xmlWriter.writeEndElement(); //end "ExtensionDescription"
        }

        writeLegalEase(xmlWriter, metadata);

        writeSupersessionInfo(xmlWriter, metadata);
        writeDeprecated(xmlWriter, metadata);
        writeSatisfies(xmlWriter, metadata);
        writeKnownConflicts(xmlWriter, metadata);
        writeKeywords(xmlWriter, metadata);

        xmlWriter.writeEndElement(); // end "Metadata"
    }


}//end TrustmarkStatusReportXmlProducer
