package com.stepashka.bd.storage;

import com.stepashka.bd.entity.ClothingTypes;
import com.stepashka.bd.error.StorageException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ClothingTypeStorageImpl extends DatabaseConnector implements ObjectStorage<ClothingTypes> {
    private String SAVE_SQL = "INSERT INTO clothing_types (type_code, type_note) VALUES(?, ?)";
    private String UPDATE_SQL = "UPDATE clothing_types SET type_code=?, type_note=? WHERE type_id=?";
    private String DELETE_SQL = "DELETE FROM clothing_types WHERE type_id=?";
    private String SELECT_ALL_SQL = "SELECT * FROM clothing_types";

    @Override
    public List<ClothingTypes> getAll() throws StorageException {
        List<ClothingTypes> list = new LinkedList<>();

        try(Connection connection = setConnection()){
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL_SQL);
            while(result.next()) {
                var item = ClothingTypes.builder()
                        .id(result.getLong("type_id"))
                        .code(result.getString("type_code"))
                        .note(result.getString("type_note"))
                        .build();
                list.add(item);
            }
        } catch (SQLException ex){
            var message = "Ошибка извлечения всех полей таблицы clothing_types.";
            handleException(ex.getMessage(), message);
        }
        return list;
    }

    @Override
    public Long save(ClothingTypes entity) throws StorageException {
        Long id = 0L;

        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getNote());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()){
                    id = resultSet.getLong(1);
                }
            }
        } catch (SQLException ex){
            var message = "Ошибка добавления объекта clothing_types (id=%s).".formatted(entity.getId());
            handleException(ex.getMessage(), message);
        }

        return id;
    }

    @Override
    public void update(ClothingTypes entity) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getNote());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка обновления объекта clothing_types (id=%s).".formatted(entity.getId());
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
            var message = "Ошибка удаления объекта clothing_types (id=%s).".formatted(id);
            handleException(ex.getMessage(), message);
        }
    }

    private void handleException(String errorMessage, String message) throws StorageException {
        log.error(errorMessage);
        throw new StorageException(message);
    }
}
