package edu.gatech.gtri.trustmark.v1_0;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by brad on 3/30/15.
 */
public class TestFactoryLoader extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(TestFactoryLoader.class);

    @Test
    public void testGetInstance() {
        ExampleService service = FactoryLoader.getInstance(ExampleService.class);
        assertThat(service, notNullValue());
        assertThat(service, instanceOf(ExampleServiceImpl.class));
        logger.info("Successfully validated that we can load using the ServiceLoader mechanism.");
    }

    /**
     * Makes sure the caching mechanism doesn't break anything.
     */
    @Test
    public void testGetInstanceTwice() {
        ExampleService service = FactoryLoader.getInstance(ExampleService.class);
        assertThat(service, notNullValue());
        assertThat(service, instanceOf(ExampleServiceImpl.class));

        service = null;

        service = FactoryLoader.getInstance(ExampleService.class);
        assertThat(service, notNullValue());
        assertThat(service, instanceOf(ExampleServiceImpl.class));

        logger.info("Successfully validated that we can load using the ServiceLoader mechanism with cached items.");
    }


    @Test
    public void testRegistrationFailsOnNull() {
        logger.info("Expecting the registration of a null class to fail.");
        assertThrows(NullPointerException.class, () -> FactoryLoader.register(ExampleService.class, null));
    }

    @Test
    public void testRegistration() {
        logger.info("Testing that we can overload the ServiceLoader mechanism and configure our own classes...");
        FactoryLoader.register(ExampleService.class, new ExampleServiceImpl2());

        logger.debug("Testing registered class...");
        ExampleService service = FactoryLoader.getInstance(ExampleService.class);
        assertThat(service, notNullValue());
        assertThat(service, instanceOf(ExampleServiceImpl2.class));

        logger.debug("Resetting for other tests...");
        FactoryLoader.register(ExampleService.class, new ExampleServiceImpl());
    }

}
