package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData;
import org.jparsec.Parser;
import org.junit.jupiter.api.Test;

import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.and;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.na;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.no;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.not;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.or;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.terminal;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.yes;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataLiteralAll;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataLiteralNone;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataReferenceIdentifierList;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataReferenceIdentifierRange;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestIssuanceCriteriaParserFactoryJParsec {

    @Test
    public void test() {

        final Parser<IssuanceCriteria<IssuanceCriteriaData>> parser = IssuanceCriteriaParserFactoryJParsec.parser();

        assertNotNull(parser);

        assertEquals(
                yes(terminal(dataLiteralAll()), dataNonTerminal()),
                parser.parse("yes(ALL)"));

        assertEquals(
                no(terminal(dataLiteralAll()), dataNonTerminal()),
                parser.parse("no(ALL)"));

        assertEquals(
                na(terminal(dataLiteralAll()), dataNonTerminal()),
                parser.parse("na(ALL)"));

        assertEquals(
                yes(terminal(dataLiteralNone()), dataNonTerminal()),
                parser.parse("yes(NONE)"));

        assertEquals(
                no(terminal(dataLiteralNone()), dataNonTerminal()),
                parser.parse("no(NONE)"));

        assertEquals(
                na(terminal(dataLiteralNone()), dataNonTerminal()),
                parser.parse("na(NONE)"));

        assertEquals(
                yes(terminal(dataReferenceIdentifierList(nel("step"))), dataNonTerminal()),
                parser.parse("yes(step)"));

        assertEquals(
                no(terminal(dataReferenceIdentifierList(nel("step"))), dataNonTerminal()),
                parser.parse("no(step)"));

        assertEquals(
                na(terminal(dataReferenceIdentifierList(nel("step"))), dataNonTerminal()),
                parser.parse("na(step)"));

        assertEquals(
                yes(terminal(dataReferenceIdentifierList(nel("step1", "step2"))), dataNonTerminal()),
                parser.parse("yes(step1, step2)"));

        assertEquals(
                no(terminal(dataReferenceIdentifierList(nel("step1", "step2"))), dataNonTerminal()),
                parser.parse("no(step1, step2)"));

        assertEquals(
                na(terminal(dataReferenceIdentifierList(nel("step1", "step2"))), dataNonTerminal()),
                parser.parse("na(step1, step2)"));

        assertEquals(
                yes(terminal(dataReferenceIdentifierRange("step1", "step2")), dataNonTerminal()),
                parser.parse("yes(step1 ... step2)"));

        assertEquals(
                no(terminal(dataReferenceIdentifierRange("step1", "step2")), dataNonTerminal()),
                parser.parse("no(step1 ... step2)"));

        assertEquals(
                na(terminal(dataReferenceIdentifierRange("step1", "step2")), dataNonTerminal()),
                parser.parse("na(step1 ... step2)"));

        assertEquals(
                and(
                        not(
                                yes(terminal(dataReferenceIdentifierList(nel("step1", "step2"))), dataNonTerminal()),
                                dataNonTerminal()),
                        yes(terminal(dataReferenceIdentifierRange("step1", "step2")), dataNonTerminal()),
                        dataNonTerminal()),
                parser.parse("NOT yes(step1, step2) AND yes(step1 ... step2)"));

        assertEquals(
                or(
                        not(
                                yes(terminal(dataReferenceIdentifierList(nel("step1", "step2"))), dataNonTerminal()),
                                dataNonTerminal()),
                        yes(terminal(dataReferenceIdentifierRange("step1", "step2")), dataNonTerminal()),
                        dataNonTerminal()),
                parser.parse("NOT yes(step1, step2) OR yes(step1 ... step2)"))
        ;
    }
}
