package edu.gatech.gtri.trustmark.v1_0.impl.issuanceCriteria;


import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaStringParser;
import org.jparsec.Parser;

import static java.util.Objects.requireNonNull;

public class IssuanceCriteriaStringParserImpl implements IssuanceCriteriaStringParser {

    private final Parser<IssuanceCriteria<IssuanceCriteriaData>> parser;

    public IssuanceCriteriaStringParserImpl(final Parser<IssuanceCriteria<IssuanceCriteriaData>> parser) {

        requireNonNull(parser);

        this.parser = parser;
    }

    @Override
    public IssuanceCriteria<IssuanceCriteriaData> parse(final String issuanceCriteria) {

        requireNonNull(issuanceCriteria);

        return parser.parse(issuanceCriteria);
    }
}
