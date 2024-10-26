package com.stepashka.bd.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final String JDBC_URL = "jdbc:postgresql://localhost:5432/lab";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "weber";

    public Connection setConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}