package by.bsu.d0mpi.UP_PostGallery.pool;

import lombok.Getter;

import java.util.ResourceBundle;

public class PoolConfiguration {
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

    protected PoolConfiguration(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        DB_USER_NAME = resourceBundle.getString("user");
        DB_PASSWORD = resourceBundle.getString("password");
        DB_URL = resourceBundle.getString("url");
        DB_DRIVER = resourceBundle.getString("driver");
        DB_INITIAL_POOL_SIZE = Integer.parseInt(resourceBundle.getString("initpoolsize"));
        DB_MAX_POOL_SIZE = Integer.parseInt(resourceBundle.getString("maxpoolsize"));
        DB_GROW_SIZE = Integer.parseInt(resourceBundle.getString("growsize"));
        DB_MAX_WAIT = Integer.parseInt(resourceBundle.getString("maxwait"));
        DB_DATEFORMAT = resourceBundle.getString("dateformat");
    }

}
