package edu.gatech.gtri.trustmark.v1_0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.Before;

/**
 * Created by brad on 3/23/15.
 */
public abstract class AbstractTest {

    private static Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    @Before
    public void printStart(){
        logger.info("======================================== STARTING TEST ========================================");
    }
    @After
    public void printStop(){
        logger.info("======================================== STOPPING TEST ========================================\n\n");
    }

}
