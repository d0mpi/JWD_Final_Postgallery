package by.bsu.d0mpi.UP_PostGallery.dao.impl;

import by.bsu.d0mpi.UP_PostGallery.dao.Dao;
import by.bsu.d0mpi.UP_PostGallery.exception.DAOException;
import by.bsu.d0mpi.UP_PostGallery.model.DatabaseEntity;
import by.bsu.d0mpi.UP_PostGallery.pool.BasicConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class MySqlAbstractDao<K extends Number, T extends DatabaseEntity> implements Dao<K, T> {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_SELECT_ALL = "SELECT * FROM %s";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM %s WHERE %s = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM %s WHERE %s = ?";

    private final String sqlFindAll;
    private final String sqlFindById;
    private final String sqlDeleteById;

    protected MySqlAbstractDao(String tableName, String idColumn) {
        this.sqlFindAll = String.format(SQL_SELECT_ALL, tableName);
        this.sqlFindById = String.format(SQL_SELECT_BY_ID, tableName, idColumn);
        this.sqlDeleteById = String.format(SQL_DELETE_BY_ID, tableName, idColumn);
    }

    protected List<T> findPreparedEntities(String sql, Consumer<PreparedStatement> consumer) {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(sql)) {
            consumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();
            return mapResultSet(connection, resultSet);
        } catch (SQLException | DAOException e) {
            LOGGER.error("Dao connection exception");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected abstract List<T> mapResultSet(Connection connection, ResultSet resultSet) throws SQLException;

    protected abstract void setDefaultStatementArgs(PreparedStatement statement, T entity) throws SQLException;

    @Override
    public List<T> findAll() {
        try (final Connection connection = BasicConnectionPool.getInstance().getConnection();
             final PreparedStatement statement = connection.prepareStatement(sqlFindAll)) {
            ResultSet resultSet = statement.executeQuery();
            return mapResultSet(connection, resultSet);
        } catch (SQLException | DAOException e) {
            LOGGER.error("Dao connection exception");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

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
    public boolean create(T entity) {
        return false;
    }

    @Override
    public void update(T entity) {
    }
}
