package by.bsu.d0mpi.UP_PostGallery.pool;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimerTask;

/**
 * A class containing all the necessary information for the connection pool configuration.
 *
 * @author d0mpi
 * @version 1.0
 * @see BasicConnectionPool
 * @see Properties
 */
public class PoolConfiguration {
    protected final int DB_TIME_OUT;
    protected final String DB_DRIVER;
    protected final String DB_URL;
    protected final String DB_USER_NAME;
    protected final String DB_PASSWORD;
    protected final int DB_INIT_POOL_SIZE;
    protected final int DB_MAX_POOL_SIZE;
    protected final int DB_GROW_SIZE;
    protected final int DB_INTERVAL;

    private static PoolConfiguration instance;

    /**
     * Provide a global access point to the instance of the {@link PoolConfiguration} class.
     *
     * @return the only instance of the {@link PoolConfiguration} class
     */
    public static PoolConfiguration getInstance(String propertyFile) {
        PoolConfiguration localInstance = instance;
        if (localInstance == null) {
            synchronized (PoolConfiguration.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PoolConfiguration(propertyFile);
                }
            }
        }
        return localInstance;
    }

    /**
     * Using {@link Properties}, it gets the configuration information from the file
     * and writes it to the corresponding fields of the class.
     */
    protected PoolConfiguration(String propertyFile) {
        Properties poolProperties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile)) {
            poolProperties.load(inputStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        DB_USER_NAME = poolProperties.getProperty("user");
        DB_PASSWORD = poolProperties.getProperty("password");
        DB_URL = poolProperties.getProperty("url");
        DB_DRIVER = poolProperties.getProperty("driver");
        DB_INIT_POOL_SIZE = Integer.parseInt(poolProperties.getProperty("init_pool_size"));
        DB_MAX_POOL_SIZE = Integer.parseInt(poolProperties.getProperty("max_pool_size"));
        DB_GROW_SIZE = Integer.parseInt(poolProperties.getProperty("grow_size"));
        DB_INTERVAL = Integer.parseInt(poolProperties.getProperty("update_interval"));
        DB_TIME_OUT = Integer.parseInt(poolProperties.getProperty("pool_timeout"));
    }

}
