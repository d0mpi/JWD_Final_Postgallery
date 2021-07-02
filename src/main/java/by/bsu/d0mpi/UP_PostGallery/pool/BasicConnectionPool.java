package by.bsu.d0mpi.UP_PostGallery.pool;

import by.bsu.d0mpi.UP_PostGallery.exception.CouldNotInitConnectionPoolException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BasicConnectionPool implements ConnectionPool{
    private List<ProxyConnection> connectionPool;
    private final List<ProxyConnection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 10;
    private static int MAX_POOL_SIZE;
    private static  int MAX_TIME_OUT;
    private static BasicConnectionPool instance;

    public static BasicConnectionPool getInstance() {
        BasicConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (BasicConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BasicConnectionPool();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void releaseConnection(Connection connection) {

    }

    @Override
    public void init() throws CouldNotInitConnectionPoolException {

    }

    @Override
    public void destroy() {

    }

    private static String getUrl(){
        return ResourceBundle.getBundle("database").getString("db.url");
    }

    private String getUser(){
        return ResourceBundle.getBundle("database").getString("db.user");
    }

    private String getPassword(){
        return ResourceBundle.getBundle("database").getString("db.password");
    }

    private String getDriver(){
        return ResourceBundle.getBundle("database").getString("db.driver");
    }

    private String getName(){
        return ResourceBundle.getBundle("database").getString("db.name");
    }
}
