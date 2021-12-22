package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.antlr.IssuanceCriteriaErrorListener;
import edu.gatech.gtri.trustmark.v1_0.impl.antlr.IssuanceCriteriaExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.impl.antlr.IssuanceCriteriaLexer;
import edu.gatech.gtri.trustmark.v1_0.impl.antlr.IssuanceCriteriaParser;
import edu.gatech.gtri.trustmark.v1_0.impl.io.IOUtils;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResult;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.*;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.*;

/**
 * A simple implementation of TrustmarkDefinitionUtils.  Do not look under the covers of this beast, it is nasty.
 * Implements TrustmarkDefinitionComparator as a delegator to an underlying implementation.
 * <br/><br/>
 * Created by brad on 4/12/16.
 */
public class TrustmarkDefinitionUtilsImpl implements TrustmarkDefinitionUtils {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkDefinitionUtilsImpl.class);

    @Override
    public Boolean isTrustmarkDefinition(File file) {
        try {
            try {
                XMLEventReader xmlEventReader = XMLInputFactory.newFactory().createXMLEventReader(new FileInputStream(file));
                while( xmlEventReader.hasNext() ){
                    XMLEvent event = xmlEventReader.nextEvent();
                    if( event.isStartElement() ){
                        if( ((StartElement) event).getName().getLocalPart().equalsIgnoreCase("TrustmarkDefinition") ){
                            return true;
                        }
                        break;
                    }
                }
                return false; // Since we successfully parsed XML, but first element was NOT TrustmarkDefinition, this is not a TD.
            }catch(XMLStreamException xmlstreame){
                // TODO HANDLE ERROR
                // In this case, the file must not be XML.  I don't think we really care.
            }

            try (FileReader reader = new FileReader(file);) {
                int nextChar = -1;
                while( (nextChar=reader.read()) >= 0 ){
                    char currentChar = (char) nextChar;
                    if( currentChar == '$' ){
                        String next = IOUtils.readNextNChars(reader, 5);
                        if( next.equalsIgnoreCase("type\"") ){
                            String nextDoubleQUotedVal = IOUtils.readNextDoubleQuotedValue(reader);
                            return nextDoubleQUotedVal.equalsIgnoreCase("TrustmarkDefinition");
                        }
                    }
                }
            }

            return false; // neither JSON or XML
        }catch(IOException ioe){
            log.error("Error Reading File["+file+"]!  Cannot determine if it is TD.", ioe);
            // Since we had an error reading the file, it must not be anything of value!
            return false;
        }
    }

    @Override
    public Collection<ValidationResult> validate(TrustmarkDefinition td) {
        ArrayList<ValidationResult> results = new ArrayList<>();
        log.debug(String.format("Performing TD[%s] validation...", td.getMetadata().getIdentifier()));

        boolean executed = false;
        ServiceLoader<TrustmarkDefinitionValidator> validatorLoader = ServiceLoader.load(TrustmarkDefinitionValidator.class);
        Iterator<TrustmarkDefinitionValidator> validatorIterator = validatorLoader.iterator();
        while( validatorIterator.hasNext() ){
            TrustmarkDefinitionValidator validator = validatorIterator.next();
            try{
                executed = true;
                log.debug("Executing TDValidator["+validator.getClass().getName()+"]...");
                Collection<ValidationResult> currentResults = validator.validate(td);
                log.debug(String.format("TDValidator[%s] had %d results.", validator.getClass().getName(), currentResults.size()));
                results.addAll(currentResults);
            }catch(Throwable t){
                log.error("Error validating TD with validator: "+validator.getClass().getName(), t);
                results.add(new ValidationResultImpl(ValidationSeverity.FATAL, "Unexpected error executing validator["+validator.getClass().getSimpleName()+"]: "+t.getMessage()));
            }
        }

        if( !executed )
            results.add(new ValidationResultImpl(ValidationSeverity.WARNING, "There are no TrustmarkDefinitionValidators defined in the system."));

        if( results.size() > 0 ) {
            log.debug(String.format("Validating TD[%s] results in %d results:", td.getMetadata().getIdentifier(), results.size()));
            for( ValidationResult result : results ){
                log.debug("  "+result.toString());
            }
        }else{
            log.debug(String.format("Validating TD[%s] looks clean!", td.getMetadata().getIdentifier()));
        }
        return results;
    }

    @Override
    public Collection<TrustmarkDefinitionDiffResult> diffAgainstSource(TrustmarkDefinition td1) throws ResolveException {
        TrustmarkDefinitionResolver resolver = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class);
        log.debug(String.format("Resolving %s...", td1.getMetadata().getIdentifier().toString()));
        TrustmarkDefinition td2 = resolver.resolve(td1.getMetadata().getIdentifier());
        return diff(td1, td2);
    }

    @Override
    public Collection<TrustmarkDefinitionDiffResult> diff(TrustmarkDefinition td1, TrustmarkDefinition td2) {
        log.debug(String.format("Finding the difference of TD1[%s] and TD2[%s]...", td1.getMetadata().getIdentifier().toString(), td2.getMetadata().getIdentifier().toString()));
        ArrayList<TrustmarkDefinitionDiffResult> results = new ArrayList<>();

        ServiceLoader<TrustmarkDefinitionDiff> loader = ServiceLoader.load(TrustmarkDefinitionDiff.class);
        Iterator<TrustmarkDefinitionDiff> tdDiffIterator = loader.iterator();
        while( tdDiffIterator.hasNext() ){
            TrustmarkDefinitionDiff diffEngine = tdDiffIterator.next();
            try{
                log.debug("Executing "+diffEngine.getClass().getName()+".doDiff('"+td2.getMetadata().getIdentifier()+"', '"+td2.getMetadata().getIdentifier()+"')...");
                Collection<TrustmarkDefinitionDiffResult> currentResults = diffEngine.doDiff(td1, td2);
                log.debug(String.format("Executing TDDifference[%s] results in %d differences.", diffEngine.getClass().getName(), currentResults.size()));
                results.addAll(currentResults);
            }catch(Throwable t){
                log.error("Error executing TrustmarkDefinitionDiff["+diffEngine.getClass().getName()+"]", t);
            }
        }
        if( log.isDebugEnabled() ) {
            String differences = debugDifferences(results);
            log.debug(String.format("Calculated %d differences: \n%s", results.size(), differences));
        }
        return results;
    }//end diff()

    private String debugDifferences(ArrayList<TrustmarkDefinitionDiffResult> results){
        StringBuilder builder = new StringBuilder();
        if( results != null && results.size() > 0 ){
            for( int i = 0; i < results.size(); i++ ){
                TrustmarkDefinitionDiffResult result = results.get(i);
                if( result == null )
                    log.error("ERROR: Unexpected NULL result object in list at position '"+i+"'!");
                builder.append("    ").append("[").append(result.getDiffType());
                builder.append(":").append(result.getSeverity()).append("] ");
                builder.append("{").append(result.getLocation()).append("} - ").append(result.getDescription());
                if( i < (results.size() - 1) ){
                    builder.append("\n");
                }
            }
        }else{
            builder.append("   <NONE FOUND>");
        }
        return builder.toString();
    }


    @Override
    public Boolean checkIssuanceCriteria(TrustmarkDefinition td, Collection<AssessmentStepResult> results) throws IssuanceCriteriaEvaluationException {
        log.debug("Processing issuance criteria for TD["+td.getMetadata().getIdentifier().toString()+"]...");

        ArrayList<AssessmentStepResult> resultsList = new ArrayList<>();
        resultsList.addAll(results);
        Collections.sort(resultsList, new Comparator<AssessmentStepResult>() {
            @Override
            public int compare(AssessmentStepResult o1, AssessmentStepResult o2) {
                return o1.getAssessmentStepNumber().compareTo(o2.getAssessmentStepNumber());
            }
        });

        IssuanceCriteriaExpressionEvaluator evaluator = new IssuanceCriteriaExpressionEvaluator(td.getIssuanceCriteria(), resultsList);
        IssuanceCriteriaErrorListener errorListener = new IssuanceCriteriaErrorListener();

        ANTLRInputStream input = new ANTLRInputStream(td.getIssuanceCriteria());
        IssuanceCriteriaLexer lexer = new IssuanceCriteriaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        IssuanceCriteriaParser parser = new IssuanceCriteriaParser(tokens);
        parser.addErrorListener(errorListener);
        parser.addParseListener(evaluator);
        parser.issuanceCriteriaExpression(); // Causes the parse to occur.

        if(errorListener.hasSyntaxErrors() ){
            throw new IssuanceCriteriaEvaluationException(errorListener.getSyntaxErrors().get(0));
        }

        if( evaluator.isHasError() ){
            throw new IssuanceCriteriaEvaluationException(evaluator.getError());
        }

        return evaluator.getSatisfied();
    }


}//end TrustmarkDefinitionUtilsImpl
