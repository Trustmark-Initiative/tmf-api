package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Extension;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;


/**
 * Created by brad on 1/7/16.
 */
public class TermXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(TermXmlProducer.class);

    @Override
    public Class getSupportedType() {
        return Term.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof Term) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Term term = (Term) instance;

        log.debug("Writing XML for Term["+term.getName()+"]...");

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(term.getName());
        xmlWriter.writeEndElement();

        if( term.getAbbreviations() != null && term.getAbbreviations().size() > 0 ){
            for( String abbr : term.getAbbreviations() ){
                xmlWriter.writeStartElement(NAMESPACE_URI, "Abbreviation");
                xmlWriter.writeCharacters(abbr);
                xmlWriter.writeEndElement();
            }
        }

        xmlWriter.writeStartElement(NAMESPACE_URI, "Definition");
        xmlWriter.writeCData(term.getDefinition());
        xmlWriter.writeEndElement();

        log.debug("Successfully Wrote XML for Term!");
    }//end serialize()


}//end TrustmarkStatusReportXmlProducer