package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

public class TestTrustExpressionEvaluatorFailureJsonProducer {

    private static final Logger log = LoggerFactory.getLogger(TrustInteroperabilityProfileJsonProducer.class);
    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);

    @Test
    public void test() {

        JsonProducer<TrustExpressionEvaluatorFailure, JSONObject> jsonProducer = jsonManager.findProducerStrict(TrustExpressionEvaluatorFailure.class, JSONObject.class).some();

        log.info(jsonProducer.serialize(TrustExpressionEvaluatorFailure.evaluatorFailureURI("|", new URISyntaxException("", ""))).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionEvaluatorFailure.evaluatorFailureResolve(URI.create("uri"), new ResolveException())).toString(2));
    }
}
