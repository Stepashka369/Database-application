package com.stepashka.bd.storage;

import com.stepashka.bd.error.StorageException;
import com.stepashka.bd.entity.Client;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ClientStorageImpl extends DatabaseConnector implements ObjectStorage<Client> {
    private String SAVE_SQL = "INSERT INTO clients (client_name, client_surname, client_email) VALUES(?, ?, ?)";
    private String UPDATE_SQL = "UPDATE clients SET client_name=?, client_surname=?, client_email=? WHERE client_id=?";
    private String DELETE_SQL = "DELETE FROM clients WHERE client_id=?";
    private String SELECT_ALL_SQL = "SELECT * FROM clients";

    @Override
    public List<Client> getAll() throws StorageException {
        List<Client> list = new LinkedList<>();

        try(Connection connection = setConnection()){
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL_SQL);
            while(result.next()) {
                var item = Client.builder()
                        .id(result.getLong("client_id"))
                        .name(result.getString("client_name"))
                        .surname(result.getString("client_surname"))
                        .email(result.getString("client_email"))
                        .build();
                list.add(item);
            }
        } catch (SQLException ex){
            var message = "Ошибка извлечения всех полей таблицы clients.";
            handleException(ex.getMessage(), message);
        }
        return list;
    }

    @Override
    public Long save(Client entity) throws StorageException {
        Long id = 0L;

        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getEmail());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()){
                    id = resultSet.getLong(1);
                }
            }
        } catch (SQLException ex){
            var message = "Ошибка добавления объекта client (id=%s).".formatted(entity.getId());
            handleException(ex.getMessage(), message);
        }

        return id;
    }

    @Override
    public void update(Client entity) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getEmail());
            statement.setLong(4, entity.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка обновления объекта client (id=%s).".formatted(entity.getId());
            handleException(ex.getMessage(), message);
        }
    }

    @Override
    public void delete(Long id) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка удаления объекта client (id=%s).".formatted(id);
            handleException(ex.getMessage(), message);
        }
    }

    private void handleException(String errorMessage, String message) throws StorageException {
        log.error(errorMessage);
        throw new StorageException(message);
    }
}
