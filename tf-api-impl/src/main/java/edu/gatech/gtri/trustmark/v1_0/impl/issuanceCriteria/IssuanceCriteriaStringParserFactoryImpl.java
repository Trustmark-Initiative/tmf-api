package edu.gatech.gtri.trustmark.v1_0.impl.issuanceCriteria;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaParserFactoryJParsec;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaStringParser;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaStringParserFactory;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class IssuanceCriteriaStringParserFactoryImpl implements IssuanceCriteriaStringParserFactory {

    @Override
    public IssuanceCriteriaStringParser createDefaultParser() {

        return new IssuanceCriteriaStringParserImpl(IssuanceCriteriaParserFactoryJParsec.parser());
    }
}
