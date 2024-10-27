package com.stepashka.bd.storage;

import com.stepashka.bd.entity.ClothingBrands;
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
public class ClothingBrandStorageImpl extends DatabaseConnector implements ObjectStorage<ClothingBrands> {
    private String SAVE_SQL = "INSERT INTO clothing_brands (brand_code, brand_note) VALUES(?, ?)";
    private String UPDATE_SQL = "UPDATE clothing_brands SET brand_code=?, brand_note=? WHERE brand_id=?";
    private String DELETE_SQL = "DELETE FROM clothing_brands WHERE brand_id=?";
    private String SELECT_ALL_SQL = "SELECT * FROM clothing_brands";

    @Override
    public List<ClothingBrands> getAll() throws StorageException {
        List<ClothingBrands> list = new LinkedList<>();

        try(Connection connection = setConnection()){
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL_SQL);
            while(result.next()) {
                var item = ClothingBrands.builder()
                        .id(result.getLong("brand_id"))
                        .code(result.getString("brand_code"))
                        .note(result.getString("brand_note"))
                        .build();
                list.add(item);
            }
        } catch (SQLException ex){
            var message = "Ошибка извлечения всех полей таблицы clothing_brands.";
            handleException(ex.getMessage(), message);
        }
        return list;
    }

    @Override
    public Long save(ClothingBrands entity) throws StorageException {
        Long id = 0L;

        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getNote());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()){
                    id = resultSet.getLong(1);
                }
            }
        } catch (SQLException ex){
            var message = "Ошибка добавления объекта clothing_brand (id=%s).".formatted(entity.getId());
            handleException(ex.getMessage(), message);
        }

        return id;
    }

    @Override
    public void update(ClothingBrands entity) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getNote());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка обновления объекта clothing_brand (id=%s).".formatted(entity.getId());
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
            var message = "Ошибка удаления объекта clothing_brand (id=%s).".formatted(id);
            handleException(ex.getMessage(), message);
        }
    }

    private void handleException(String errorMessage, String message) throws StorageException {
        log.error(errorMessage);
        throw new StorageException(message);
    }
}
