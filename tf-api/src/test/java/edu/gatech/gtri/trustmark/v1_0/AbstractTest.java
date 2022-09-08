package edu.gatech.gtri.trustmark.v1_0;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by brad on 3/23/15.
 */
public abstract class AbstractTest {

    private static Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeEach
    public void printStart() {
        logger.info("======================================== STARTING TEST ========================================");
    }

    @AfterEach
    public void printStop() {
        logger.info("======================================== STOPPING TEST ========================================\n\n");
    }
}
