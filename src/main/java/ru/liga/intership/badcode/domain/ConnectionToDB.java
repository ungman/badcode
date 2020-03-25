package ru.liga.intership.badcode.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.BadcodeApplication;

import java.sql.*;

public class ConnectionToDB {
    private static ConnectionToDB instance = null;
    final Connection connection;
    public static final Logger logger = LoggerFactory.getLogger(ConnectionToDB.class);

    private ConnectionToDB(String url, String name, String password) {
        Connection connection1 = null;
        try {
            connection1 = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            logger.debug("{}",e.getMessage());
            e.printStackTrace();
        }
        connection = connection1;
    }

    public static ConnectionToDB getInstance(String url, String name, String password) {
        logger.debug("Enter to {} {}","ConnectionToDB","getInstance(String url, String name, String password)");
        if (instance == null)
            instance = new ConnectionToDB(url, name, password);
        return instance;
    }

    public static ConnectionToDB getInstance() {
        logger.debug("Enter to {} {}","ConnectionToDB","getInstance()");

        if (instance == null){
            logger.debug("Try getInstance without init");
            throw new RuntimeException("Error! Dont init connection");
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet selectQuery(String query) {
        logger.debug("Enter to {} {}","ConnectionToDB","selectQuery(String query)");
        ResultSet resultSet ;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            resultSet=null;
            logger.debug("{}",e.getMessage());
            e.printStackTrace();
        }
        return resultSet;
    }

    public int updateQuery(String query) {
        logger.debug("Enter to {} {}","ConnectionToDB","updateQuery(String query)");
        int result = -1;
        try (Statement statement = connection.createStatement()) {
            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.debug("{}",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public int deleteQuery(String query) {
        logger.debug("Enter to {} {}","ConnectionToDB","deleteQuery(String query)");
        int result = -1;
        try (Statement statement = connection.createStatement()) {
            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.debug("{}",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
