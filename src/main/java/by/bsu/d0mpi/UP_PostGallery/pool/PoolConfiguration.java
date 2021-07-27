package by.bsu.d0mpi.UP_PostGallery.pool;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class PoolConfiguration {
    public static final String DATABASE_PROPERTIES_PATH = "database.properties";
    protected final String DB_USER_NAME;
    protected final String DB_PASSWORD;
    protected final String DB_URL;
    protected final String DB_DRIVER;
    protected final int DB_INITIAL_POOL_SIZE;
    protected final int DB_MAX_POOL_SIZE;
    protected final int DB_GROW_SIZE;
    protected final int DB_MAX_WAIT;
    protected final String DB_DATEFORMAT;

    private static PoolConfiguration instance;

    public static PoolConfiguration getInstance() {
        PoolConfiguration localInstance = instance;
        if (localInstance == null) {
            synchronized (PoolConfiguration.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PoolConfiguration();
                }
            }
        }
        return localInstance;
    }

    protected PoolConfiguration() {
        Properties poolProperties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTIES_PATH)) {
            poolProperties.load(inputStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        DB_USER_NAME = poolProperties.getProperty("user");
        DB_PASSWORD = poolProperties.getProperty("password");
        DB_URL = poolProperties.getProperty("url");
        DB_DRIVER = poolProperties.getProperty("driver");
        DB_INITIAL_POOL_SIZE = Integer.parseInt(poolProperties.getProperty("initpoolsize"));
        DB_MAX_POOL_SIZE = Integer.parseInt(poolProperties.getProperty("maxpoolsize"));
        DB_GROW_SIZE = Integer.parseInt(poolProperties.getProperty("growsize"));
        DB_MAX_WAIT = Integer.parseInt(poolProperties.getProperty("maxwait"));
        DB_DATEFORMAT = poolProperties.getProperty("dateformat");
    }

}
