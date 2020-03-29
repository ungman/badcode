package ru.liga.intership.badcode.domain.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorFactory {
    public static final Logger logger = LoggerFactory.getLogger(ConnectorFactory.class);
    private static ConnectorFactory connectorFactory = null;
    private final Connection connection;
    private String url;
    private String name;
    private String password;

    private ConnectorFactory() {
        logger.info("Enter to {} {}", this.getClass(), "ConnectorFactory()");
        readConfigure();
        try {
            connection = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            logger.trace(e.getStackTrace().toString());
            throw new RuntimeException("Error in connect to DB");
        }
    }

    public static ConnectorFactory getInstance() {
        logger.debug("Enter to {} {}", "ConnectorFactory", "ConnectorFactory getInstance()");
        if (connectorFactory == null)
            connectorFactory = new ConnectorFactory();
        return connectorFactory;
    }

    private void readConfigure() {
        logger.debug("Enter to {} {}", this.getClass(), "void readConfigure()");
        this.url = "jdbc:hsqldb:mem:test";
        this.name = "sa";
        this.password = "";
    }

    public Connection getConnection() {
        logger.debug("Enter to {} {}", this.getClass(), "Connection getConnection()");
        if (connectorFactory == null)
            getInstance();
        return connection;
    }
}
