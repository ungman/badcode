package ru.liga.intership.badcode.domain;

import java.sql.*;

public class ConnectionToDB {
    private static ConnectionToDB instance = null;
    final Connection connection;

    private ConnectionToDB(String url, String name, String password) {
        Connection connection1 = null;
        try {
            connection1 = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = connection1;
    }

    public static ConnectionToDB getInstance(String url, String name, String password) {
        if (instance == null)
            instance = new ConnectionToDB(url, name, password);
        return instance;
    }

    public static ConnectionToDB getInstance() {
        if (instance == null)
            throw new RuntimeException("Error! Dont init connection");
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet selectQuery(String query) {
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int updateQuery(String query) {
        int result = -1;
        try (Statement statement = connection.createStatement()) {
            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int deleteQuery(String query) {
        int result = -1;
        try (Statement statement = connection.createStatement()) {
            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
