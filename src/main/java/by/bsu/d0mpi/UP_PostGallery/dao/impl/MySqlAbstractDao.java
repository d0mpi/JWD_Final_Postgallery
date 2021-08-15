package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.Dao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * An abstract class that provides basic operations on entities contained in the MySQL database
 * Database connections are taken from the {@link BasicConnectionPool}
 *
 * @param <K> entity id type
 * @param <T> database entity type
 * @author d0mpi
 * @version 1.0
 * @see BasicConnectionPool
 * @see ResultSet
 * @see by.bsu.d0mpi.UP_PostGallery.pool.ProxyConnection
 * @see DAOException
 * @see PreparedStatement
 * @see Statement
 */
public abstract class MySqlAbstractDao<K extends Number, T extends DatabaseEntity> implements Dao<K, T> {

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_ALL = "SELECT * FROM %s";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM %s WHERE %s = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM %s WHERE %s = ?";
    private static final String SQL_COUNT_ALL = "SELECT COUNT(%s) FROM %s";

    private final String sqlFindAll;
    private final String sqlFindById;
    private final String sqlDeleteById;
    private final String sqlCountAll;

    /**
     * constructor inserts the necessary data into the query strings
     *
     * @param tableName the name of the table to which requests are made
     * @param idColumn  the name of id column in the table with provided tableName
     */
    protected MySqlAbstractDao(String tableName, String idColumn) {
        this.sqlFindAll = String.format(SQL_SELECT_ALL, tableName);
        this.sqlFindById = String.format(SQL_SELECT_BY_ID, tableName, idColumn);
        this.sqlDeleteById = String.format(SQL_DELETE_BY_ID, tableName, idColumn);
        this.sqlCountAll = String.format(SQL_COUNT_ALL, idColumn, tableName);
    }

    /**
     * Takes a connection from {@link BasicConnectionPool}, executes
     * {@link PreparedStatement} according to provided consumer and returns result of this statement.
     *
     * @param sql      database query string
     * @param consumer enters the necessary data in the statement
     * @return {@link List} of the specified database entities
     */
    protected List<T> findPreparedEntities(String sql, Consumer<PreparedStatement> consumer) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(sql)) {
            consumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();
            return getRequestResult(connection, resultSet);
        } catch (SQLException | DAOException e) {
            logger.error("Dao connection exception");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected abstract List<T> getRequestResult(Connection connection, ResultSet resultSet) throws SQLException;

    protected abstract void setCreateStatementArgs(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void setUpdateStatementArgs(PreparedStatement statement, T entity) throws SQLException;

    /**
     * Takes a connection from {@link BasicConnectionPool}, executes
     * {@link PreparedStatement} and returns result of this statement.
     *
     * @return {@link List} of all the specified entities contained in the database
     */
    @Override
    public List<T> findAll() {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(sqlFindAll)) {
            ResultSet resultSet = statement.executeQuery();
            return getRequestResult(connection, resultSet);
        } catch (SQLException | DAOException e) {
            logger.error("Dao connection exception");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Takes a connection from {@link BasicConnectionPool}, executes
     * {@link PreparedStatement} and returns result of this statement.
     *
     * @param id entity ID
     * @return {@link List} of all the specified entities contained in the database
     * with specified id.
     */
    @Override
    public T findEntityById(K id) {
        return findPreparedEntities(sqlFindById, statement -> {
            try {
                statement.setInt(1, (Integer) id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).stream().findFirst().orElse(null);
    }

    /**
     * Takes a connection from {@link BasicConnectionPool}, executes
     * {@link PreparedStatement} and deletes entity with specified id from the database
     *
     * @return true - if the specified entity was deleted, otherwise returns false
     */
    @Override
    public boolean delete(K id) {
        boolean deleted = false;
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlDeleteById)) {
            statement.setInt(1, (Integer) id);
            statement.executeUpdate();
            deleted = true;
        } catch (SQLException | DAOException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    /**
     * Takes a connection from {@link BasicConnectionPool}, executes
     * {@link PreparedStatement} and deletes specified entity from the database
     *
     * @return true - if the specified entity was deleted, otherwise returns false
     */
    @Override
    public boolean delete(T entity) {
        boolean deleted = false;
        try (Connection connection = BasicConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlDeleteById)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            deleted = true;
        } catch (SQLException | DAOException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public abstract T create(T entity);

    /**
     * Takes a connection from {@link BasicConnectionPool} and creates an entity with all the parameters
     *
     * @param entity    the entity to be added to the database
     * @param insertSQL the required query string for adding the specified entity to the database
     * @return created entities or null if something goes wrong
     */
    @Override
    public T createEntityWithoutDependencies(T entity, String insertSQL) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            setCreateStatementArgs(statement, entity);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                entity.setId(id);
                return entity;
            } else {
                logger.error("No autoincremented index after trying to add record into table user");
                return null;
            }
        } catch (SQLException | DAOException e) {
            logger.error("DB connection error", e);
            return null;
        }
    }

    @Override
    public abstract T update(T entity);

    /**
     * Takes a connection from {@link BasicConnectionPool} and returns count of all entities
     * with the specified type
     *
     * @return count of all entities with the specified type
     */
    @Override
    public int getEntriesCount() {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sqlCountAll);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException | DAOException e) {
            logger.error("DB connection error", e);
            return 0;
        }
    }
}
