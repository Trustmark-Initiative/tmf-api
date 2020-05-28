package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluatorFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by brad on 5/20/16.
 */
@RunWith(Parameterized.class)
public class TestExpressionBindingTIPEvaluator extends AbstractTest {


    public static final FileFilter XML_FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith(".xml");
        }
    };
    public static final FileFilter DIRECTORY_FILTER = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.isDirectory();
        }
    };

    @Parameterized.Parameters(name = "{index} {1}")
    public static Collection<Object[]> data() throws Exception {
        Collection<Object[]> data = new ArrayList<>();

        TrustInteroperabilityProfileResolver tipResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
        TrustmarkResolver trustmarkResolver = FactoryLoader.getInstance(TrustmarkResolver.class);

        logger.info("Loading parameterized test information...");

        File baseFolder = new File("./src/test/resources/TIPs/trustExpression");
        File[] subdirs = baseFolder.listFiles(DIRECTORY_FILTER);

        for( File subdir : subdirs ){
            File tipsDir = new File(subdir, "tips");
            File trustmarksDir = new File(subdir, "trustmarks");

            Set<Trustmark> trustmarks = new HashSet<>();

            for( File trustmarkFile : trustmarksDir.listFiles(XML_FILE_FILTER) ){
                Trustmark trustmark = trustmarkResolver.resolve(trustmarkFile, false);
                trustmarks.add(trustmark);
            }

            for( File tipFile : tipsDir.listFiles(XML_FILE_FILTER) ){
                TrustInteroperabilityProfile tip = tipResolver.resolve(tipFile, false);
                Map parameterSet = new HashMap();
                parameterSet.put("trustmarks", trustmarks);
                parameterSet.put("tip", tip);
                parameterSet.put("testDir", subdir);
                parameterSet.put("tipsDir", tipsDir);
                parameterSet.put("trustmarksDir", trustmarksDir);

                Boolean expectedResult = Boolean.TRUE;
                File failFile = new File(tipFile.getParentFile(), tipFile.getName()+".fail");
                if( failFile.exists() )
                    expectedResult = Boolean.FALSE;

                parameterSet.put("expectedResult", expectedResult);
                data.add(new Object[]{parameterSet, subdir.getName()});
            }

        }

        return data;
    }


    private Map data; // For each run, contains the current set of parameters.
    public TestExpressionBindingTIPEvaluator(Map data, String name) {
        this.data = data;
    }



    @Test
    public void testTIPTrustExpression() throws Exception{
        logger.info("Testing trust expression...");
        Set<Trustmark> trustmarks = (Set) this.data.get("trustmarks");
        TrustInteroperabilityProfile tip = (TrustInteroperabilityProfile) this.data.get("tip");
        logger.debug("There are "+trustmarks.size()+" trustmarks in this test...");

        TIPEvaluator evaluator = FactoryLoader.getInstance(TIPEvaluatorFactory.class).createDefaultEvaluator();
        assertThat(evaluator, notNullValue());

        TIPEvaluation evaluation = evaluator.evaluateTIPAgainstTrustmarks(tip, trustmarks);
        assertThat(evaluation, notNullValue());

        assertThat(evaluation.getTIP(), notNullValue());
        assertThat(tip.getIdentifier(), equalTo(evaluation.getTIP().getIdentifier()));

        Boolean expectedResult = (Boolean) this.data.get("expectedResult");
        if( expectedResult == null )
            expectedResult = Boolean.TRUE;
        assertThat(evaluation.isSatisfied(), equalTo(expectedResult));
    }

}
