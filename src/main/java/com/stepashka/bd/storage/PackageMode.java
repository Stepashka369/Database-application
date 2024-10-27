package com.stepashka.bd.storage;

import com.stepashka.bd.error.StorageException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class PackageMode extends DatabaseConnector {

    public void executeSQL(String sqlQuery) throws StorageException{
        try (Connection connection = setConnection()) {
             Statement statement = connection.createStatement();

            if (sqlQuery.trim().toUpperCase().startsWith("SELECT")) {
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                resultSet.next();
            } else {
                statement.executeUpdate(sqlQuery);
            }
        } catch (SQLException e) {
            log.error("Ошибка при выполнении Sql запроса. {}", e.getMessage());
            throw new StorageException("Ошибка при выполнении Sql запроса.");
        }
    }
}
