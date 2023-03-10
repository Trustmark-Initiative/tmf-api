package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.impl.util.ConformanceCriterionUtils;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Artifact;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;
import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class AssessmentStepXmlProducer implements XmlProducer<AssessmentStep> {

    private static final Logger log = LoggerFactory.getLogger(AssessmentStepXmlProducer.class);

    @Override
    public Class<AssessmentStep> getSupportedType() {
        return AssessmentStep.class;
    }

    @Override
    public void serialize(AssessmentStep step, XMLStreamWriter xmlWriter) throws XMLStreamException {
        log.debug("Writing XML for AssessmentStep #" + step.getNumber() + ": " + step.getName());

        String id = step.getId();
        xmlWriter.writeAttribute("tf", NAMESPACE_URI, ATTRIBUTE_KEY_JSON_ID, id);

        xmlWriter.writeStartElement(NAMESPACE_URI, "Number");
        xmlWriter.writeCharacters(step.getNumber().toString());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(step.getName());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Description");
        xmlWriter.writeCharacters(step.getDescription());
        xmlWriter.writeEndElement();

        for (ConformanceCriterion crit : step.getConformanceCriteria()) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "ConformanceCriterion");
            String critId = ConformanceCriterionUtils.CRITERION_ID_PREFIX + crit.getNumber();
            xmlWriter.writeAttribute("tf", NAMESPACE_URI, "ref", critId);
            xmlWriter.writeAttribute("xsi", XmlProducerUtility.XSI_NS_URI, "nil", "true");
            xmlWriter.writeEndElement();
        }

        if (step.getArtifacts() != null && step.getArtifacts().size() > 0) {
            for (Artifact artifact : step.getArtifacts()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "Artifact");
                XmlProducerUtility.writeXml(artifact, xmlWriter);
                xmlWriter.writeEndElement();
            }
        }

        if (!step.getParameters().isEmpty()) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "ParameterDefinitions");
            for (TrustmarkDefinitionParameter param : step.getParameters()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "ParameterDefinition");
                XmlProducerUtility.writeString(xmlWriter, "Identifier", param.getIdentifier());
                XmlProducerUtility.writeString(xmlWriter, "Name", param.getName());
                XmlProducerUtility.writeCDataString(xmlWriter, "Description", param.getDescription());
                XmlProducerUtility.writeString(xmlWriter, "ParameterKind", param.getParameterKind().toString());
                if (param.getParameterKind().equals(ParameterKind.ENUM) || param.getParameterKind().equals(ParameterKind.ENUM_MULTI)) {
                    xmlWriter.writeStartElement(NAMESPACE_URI, "EnumValues");
                    for (String enumValue : param.getEnumValues()) {
                        XmlProducerUtility.writeString(xmlWriter, "EnumValue", enumValue);
                    }
                    xmlWriter.writeEndElement();
                }
                XmlProducerUtility.writeString(xmlWriter, "Required", param.isRequired().toString());

                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();
        }


        log.debug("Finished writing XML for Step #" + step.getNumber());

    }//end serialize()

}//end class EntityXmlProducer
