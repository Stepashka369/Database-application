package com.stepashka.bd.storage;

import com.stepashka.bd.model.ClothingSize;
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
public class ClothingSizeStorageImpl extends DatabaseConnector implements ObjectStorage<ClothingSize>{
    private String SAVE_SQL = "INSERT INTO clothing_sizes (size_code, size_note) VALUES(?, ?)";
    private String UPDATE_SQL = "UPDATE clothing_sizes SET size_code=?, size_note=? WHERE size_id=?";
    private String DELETE_SQL = "DELETE FROM clothing_sizes WHERE size_id=?";
    private String SELECT_ALL_SQL = "SELECT * FROM clothing_sizes";

    @Override
    public List<ClothingSize> getAll() throws StorageException {
        List<ClothingSize> list = new LinkedList<>();

        try(Connection connection = setConnection()){
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL_SQL);
            while(result.next()) {
                var item = ClothingSize.builder()
                        .id(result.getLong("size_id"))
                        .code(result.getString("size_code"))
                        .note(result.getString("size_note"))
                        .build();
                list.add(item);
            }
        } catch (SQLException ex){
            var message = "Ошибка извлечения всех полей таблицы clothing_sizes";
            handleException(ex.getMessage(), message);
        }
        return list;
    }

    @Override
    public void save(ClothingSize entity) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getNote());
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка добавления объекта clothing_size (id=%s).".formatted(entity.getId());
            handleException(ex.getMessage(), message);
        }
    }

    @Override
    public void update(ClothingSize entity) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getNote());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка обновления объекта clothing_size (id=%s).".formatted(entity.getId());
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
            var message = "Ошибка удаления объекта clothing_size (id=%s).".formatted(id);
            handleException(ex.getMessage(), message);
        }
    }

    private void handleException(String errorMessage, String message) throws StorageException {
        log.error(errorMessage);
        throw new StorageException(message);
    }
}
