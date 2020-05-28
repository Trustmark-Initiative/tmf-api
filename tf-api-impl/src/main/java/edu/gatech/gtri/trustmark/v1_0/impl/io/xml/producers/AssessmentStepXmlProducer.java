package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class AssessmentStepXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(AssessmentStepXmlProducer.class);

    @Override
    public Class getSupportedType() {
        return AssessmentStep.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof AssessmentStep) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        AssessmentStep step = (AssessmentStep) instance;

        log.debug("Writing XML for AssessmentStep #"+step.getNumber()+": "+step.getName());

        String id = "Step"+step.getNumber();
        xmlWriter.writeAttribute("tf", NAMESPACE_URI, "id", id);

        xmlWriter.writeStartElement(NAMESPACE_URI, "Number");
        xmlWriter.writeCharacters(step.getNumber().toString());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(step.getName());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Description");
        xmlWriter.writeCharacters(step.getDescription());
        xmlWriter.writeEndElement();

        for( ConformanceCriterion crit : step.getConformanceCriteria() ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "ConformanceCriterion");
            String critId = "Criterion"+crit.getNumber();
            xmlWriter.writeAttribute("tf", NAMESPACE_URI, "ref", critId);
            xmlWriter.writeAttribute("xsi", XSI_NS_URI, "nil", "true");
            xmlWriter.writeEndElement();
        }

        if( step.getArtifacts() != null && step.getArtifacts().size() > 0 ){
            for( Artifact artifact : step.getArtifacts() ){
                xmlWriter.writeStartElement(NAMESPACE_URI, "Artifact");
                writeXml(artifact, xmlWriter);
                xmlWriter.writeEndElement();
            }
        }

        if( !step.getParameters().isEmpty() ){
            xmlWriter.writeStartElement(NAMESPACE_URI, "ParameterDefinitions");
            for(TrustmarkDefinitionParameter param : step.getParameters()){
                xmlWriter.writeStartElement(NAMESPACE_URI, "ParameterDefinition");
                writeString(xmlWriter, "Identifier", param.getIdentifier());
                writeString(xmlWriter, "Name", param.getName());
                writeCDataString(xmlWriter, "Description", param.getDescription());
                writeString(xmlWriter, "ParameterKind", param.getParameterKind().toString());
                if( param.getParameterKind().equals(ParameterKind.ENUM) || param.getParameterKind().equals(ParameterKind.ENUM_MULTI) ){
                    xmlWriter.writeStartElement(NAMESPACE_URI, "EnumValues");
                    for( String enumValue : param.getEnumValues() ){
                        writeString(xmlWriter, "EnumValue", enumValue);
                    }
                    xmlWriter.writeEndElement();
                }
                writeString(xmlWriter, "Required", param.isRequired().toString());

                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
        }


        log.debug("Finished writing XML for Step #"+step.getNumber());

    }//end serialize()

}//end class EntityXmlProducer