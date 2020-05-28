package edu.gatech.gtri.trustmark.v1_0.impl.util;

import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * utility class for thread pool, currently used to thread requests for downloading TIPs and TDs
 */
public class ThreadUtils {

    static ResourceBundle tfApiImplResources = ResourceBundle.getBundle("tfapi_impl");

    static final String initPoolSize = "tfapi_impl_init_pool_size";
    static final String maxPoolSize = "tfapi_impl_maxpool_size";
    static final String queueCapacity = "tfapi_impl_queue_capacity";
    static final String keepAlive = "tfapi_impl_keep_alive";

    static private ThreadPoolExecutor executor;

    /**
     * static thread pool configurable through the properties file
     */
    static {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(Integer.parseInt(tfApiImplResources.getString(queueCapacity)), false);
        executor = new ThreadPoolExecutor(
                Integer.parseInt(tfApiImplResources.getString(initPoolSize))
                , Integer.parseInt(tfApiImplResources.getString(maxPoolSize))
                , Integer.parseInt(tfApiImplResources.getString(keepAlive))
                , TimeUnit.MILLISECONDS, queue);
    }

    /**
     * older thread call
     * @param r
     */
    static public void execute(Runnable r)  {
        executor.execute(r);
    }

    /**
     * newer lambda friendly method
     * @param t
     * @param <T>
     * @return
     */

    static public <T> Future<T> submit(Callable t)  {
        return executor.submit(t);
    }

}
