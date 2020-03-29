package ru.liga.intership.badcode.domain.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectorToDBPerson extends ConnectorToDB {
    public static final Logger logger = LoggerFactory.getLogger(ConnectorToDBPerson.class);

    public ConnectorToDBPerson(String nameTable, Connection connection) {
        super(nameTable, connection);
        logger.info("Enter to {} {}", this.getClass(), "ConnectorToDBPerson((String nameTable, Connection connection)");

    }

    @Override
    public Object getByQuery(String query, Object objectTest) {
        logger.debug("Enter to {} {}", this.getClass(), "Object getByQuery(String query, Object objectTest)");
        String query1 = "SELECT * FROM " + nameTable + " " + query;
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query1);
        } catch (SQLException e) {
            logger.trace(e.getStackTrace().toString());
            throw new RuntimeException("Error in working DB");
        }
        return resultSet;
    }

    @Override
    public Object saveByQuery(String query, Object objectTest) {
        logger.debug("Enter to {} {}", this.getClass(), "Object saveByQuery(String query, Object objectTest)");

        String query1 = "INSERT INTO " + nameTable + query;
        int executeUpdate = 0;
        try (Statement statement = connection.createStatement()) {
            executeUpdate = statement.executeUpdate(query1);
        } catch (SQLException e) {
            logger.trace(e.getStackTrace().toString());
            throw new RuntimeException("Error in working DB");
        }
        return executeUpdate;
    }

    @Override
    public Object updateByQuery(String query, Object objectTest) {
        logger.debug("Enter to {} {}", this.getClass(), "Object updateByQuery(String query, Object objectTest)");
        String query1 = "UPDATE " + nameTable + " " + query;
        int executeUpdate = 0;
        try (Statement statement = connection.createStatement()) {
            executeUpdate = statement.executeUpdate(query1);
        } catch (SQLException e) {
            logger.trace(e.getStackTrace().toString());
            throw new RuntimeException("Error in working DB");
        }
        return executeUpdate;
    }

    @Override
    public Object deleteByQuery(String query, Object objectTest) {
        logger.debug("Enter to {} {}", this.getClass(), "Object deleteByQuery(String query, Object objectTest)");
        String query1 = "DELETE  FROM " + nameTable + query;
        int executeUpdate = 0;
        try (Statement statement = connection.createStatement()) {
            executeUpdate = statement.executeUpdate(query1);
        } catch (SQLException e) {
            logger.trace(e.getStackTrace().toString());
            throw new RuntimeException("Error in working DB");
        }
        return executeUpdate;
    }
}
