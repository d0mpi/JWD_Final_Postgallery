package by.bsu.d0mpi.UP_PostGallery.dao;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorDB {
//    private static volatile ConnectorDB instance;
//    @Getter
//    private final Connection connection;
    private static final Logger LOGGER = LogManager.getLogger();

//    private ConnectorDB(){
//        connection = init();
//    }
//
//    public static ConnectorDB getInstance() {
//        ConnectorDB localInstance = instance;
//        if (localInstance == null) {
//            synchronized (ConnectorDB.class) {
//                localInstance = instance;
//                if (localInstance == null) {
//                    instance = localInstance = new ConnectorDB();
//                }
//            }
//        }
//        return localInstance;
//    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            ResourceBundle resource = ResourceBundle.getBundle("database");
            String url = resource.getString("db.url");
            String user = resource.getString("db.user");
            String pass = resource.getString("db.password");
            String dbName = resource.getString("db.name");
            return DriverManager.getConnection(url + dbName, user, pass);
        } catch (SQLException | ClassNotFoundException throwables) {
            LOGGER.fatal("Impossible to initialize connection pool", throwables);
            return null;
        }
    }
}
