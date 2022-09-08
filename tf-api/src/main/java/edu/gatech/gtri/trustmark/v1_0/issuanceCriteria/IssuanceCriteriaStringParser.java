package edu.gatech.gtri.trustmark.v1_0.issuanceCriteria;

public interface IssuanceCriteriaStringParser {

    IssuanceCriteria<IssuanceCriteriaData> parse(final String issuanceCriteria);
}
