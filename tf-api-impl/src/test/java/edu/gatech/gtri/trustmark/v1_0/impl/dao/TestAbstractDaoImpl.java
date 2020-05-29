package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;
import java.util.Map;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 9/15/16
 */
public class TestAbstractDaoImpl extends AbstractTest {


    @Test
    public void testTermsRegular() {
        Map<String, List<String>> termsMap = AbstractDaoImpl.parseTerms("regular day");
        assertThat(termsMap, notNullValue());
        assertThat(termsMap.get("exact"), notNullValue());
        assertThat(termsMap.get("exact").size(), equalTo(0));
        assertThat(termsMap.get("regular"), notNullValue());
        assertThat(termsMap.get("regular").size(), equalTo(2));
        assertThat(termsMap.get("regular"), contains("regular", "day"));
    }


    @Test
    public void testTermsExactAndRegular() {
        Map<String, List<String>> termsMap = AbstractDaoImpl.parseTerms("today \"Regular dAy\"");
        assertThat(termsMap, notNullValue());
        assertThat(termsMap.get("exact"), notNullValue());
        assertThat(termsMap.get("exact").size(), equalTo(1));
        assertThat(termsMap.get("exact"), contains("Regular dAy"));
        assertThat(termsMap.get("regular"), notNullValue());
        assertThat(termsMap.get("regular").size(), equalTo(1));
        assertThat(termsMap.get("regular"), contains("today"));
    }


    @Test
    public void testTermsMultipleExact() {
        Map<String, List<String>> termsMap = AbstractDaoImpl.parseTerms("\"bossman Bohemoth\" \"Regular dAy\"");
        assertThat(termsMap, notNullValue());
        assertThat(termsMap.get("exact"), notNullValue());
        assertThat(termsMap.get("exact").size(), equalTo(2));
        assertThat(termsMap.get("exact"), contains("bossman Bohemoth", "Regular dAy"));
        assertThat(termsMap.get("regular"), notNullValue());
        assertThat(termsMap.get("regular").size(), equalTo(0));
    }

    @Test
    public void testSimpleTermsRemoved() {
        Map<String, List<String>> termsMap = AbstractDaoImpl.parseTerms("a an the today \"Regular dAy\"");
        assertThat(termsMap, notNullValue());
        assertThat(termsMap.get("exact"), notNullValue());
        assertThat(termsMap.get("exact").size(), equalTo(1));
        assertThat(termsMap.get("exact"), contains("Regular dAy"));
        assertThat(termsMap.get("regular"), notNullValue());
        assertThat(termsMap.get("regular").size(), equalTo(1));
        assertThat(termsMap.get("regular"), contains("today"));
    }


}/* end TestAbstractDaoImpl */