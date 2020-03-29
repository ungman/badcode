package ru.liga.intership.badcode.domain.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public abstract class ConnectorToDB {

    protected final String nameTable;
    protected final Connection connection;
    public static final Logger logger = LoggerFactory.getLogger(ConnectorToDB.class);

    protected ConnectorToDB(String nameTable,Connection connection) {
        logger.info("Enter to {} {}",this.getClass(), "ConnectorToDB(String nameTable,Connection connection)");
        this.connection=connection;
        this.nameTable = nameTable;
    }

    public abstract Object getByQuery(String query, Object objectTest);

    public abstract Object saveByQuery(String query, Object objectTest);

    public abstract Object updateByQuery(String query, Object objectTest);

    public abstract Object deleteByQuery(String query, Object objectTest);
}
